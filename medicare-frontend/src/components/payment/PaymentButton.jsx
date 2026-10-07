import {
    useState
} from "react";

import {
    useAuth
} from "../../context/AuthContext";

import paymentService from "../../services/paymentService";


function PaymentButton({
    appointment,
    onPaymentSuccess,
    className =
        "btn btn-success w-100"
}) {

    const {
        user
    } = useAuth();


    const [
        paying,
        setPaying
    ] = useState(false);


    const [
        error,
        setError
    ] = useState("");


    const handlePayment =
        async () => {

            setError("");


            if (!window.Razorpay) {

                setError(
                    "Razorpay Checkout could not be loaded."
                );

                return;
            }


            try {

                setPaying(true);


                // ----------------------------------
                // CREATE ORDER ON BACKEND
                // ----------------------------------

                const order =
                    await paymentService
                        .createOrder(
                            appointment.id
                        );


                let verificationStarted =
                    false;


                const options = {

                    key:
                        order.keyId,

                    amount:
                        order.amount,

                    currency:
                        order.currency,

                    name:
                        "Medicare Healthcare",

                    description:
                        `Consultation with ${appointment.doctorName}`,

                    order_id:
                        order.razorpayOrderId,


                    // ----------------------------------
                    // PAYMENT SUCCESS
                    // ----------------------------------

                    handler:
                        async (response) => {

                            verificationStarted =
                                true;

                            setError("");


                            try {

                                const updatedAppointment =
                                    await paymentService
                                        .verifyPayment(
                                            appointment.id,
                                            {
                                                razorpayPaymentId:
                                                    response
                                                        .razorpay_payment_id,

                                                razorpaySignature:
                                                    response
                                                        .razorpay_signature
                                            }
                                        );


                                if (
                                    onPaymentSuccess
                                ) {

                                    onPaymentSuccess(
                                        updatedAppointment
                                    );
                                }

                            } catch (error) {

                                setError(
                                    error.response
                                        ?.data
                                        ?.message
                                    ||
                                    "Payment verification failed"
                                );

                            } finally {

                                setPaying(
                                    false
                                );
                            }
                        },


                    // ----------------------------------
                    // PREFILL
                    // ----------------------------------

                    prefill: {

                        name:
                            user?.name
                            || "",

                        email:
                            user?.email
                            || ""
                    },


                    // ----------------------------------
                    // MODAL SETTINGS
                    // ----------------------------------

                    modal: {

                        confirm_close:
                            true,

                        ondismiss:
                            () => {

                                if (
                                    !verificationStarted
                                ) {

                                    setPaying(
                                        false
                                    );
                                }
                            }
                    }
                };


                const razorpay =
                    new window.Razorpay(
                        options
                    );


                razorpay.on(
                    "payment.failed",
                    (response) => {

                        setError(
                            response.error
                                ?.description
                            ||
                            "Payment failed. Please try again."
                        );
                    }
                );


                razorpay.open();


            } catch (error) {

                setPaying(false);


                setError(
                    error.response
                        ?.data
                        ?.message
                    ||
                    "Unable to start payment"
                );
            }
        };


    return (

        <div>

            <button
                type="button"
                className={
                    className
                }
                onClick={
                    handlePayment
                }
                disabled={
                    paying
                }
            >

                {
                    paying
                        ? "Processing Payment..."
                        : "Pay Now"
                }

            </button>


            {
                error
                && (

                    <div
                        className="
                            alert
                            alert-danger
                            mt-2
                            mb-0
                        "
                    >

                        {error}

                    </div>

                )
            }

        </div>
    );
}


export default PaymentButton;