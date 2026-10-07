import PaymentButton from "../payment/PaymentButton";


function AppointmentCard({
    appointment,
    onCancel,
    cancellingId,
    onViewPrescription,
    onPaymentSuccess
}) {


    const formatTimeSlot =
        (timeSlot) => {

            switch (timeSlot) {

                case "MORNING_10_AM":
                    return "10:00 AM";

                case "AFTERNOON_2_PM":
                    return "2:00 PM";

                case "EVENING_5_PM":
                    return "5:00 PM";

                default:
                    return timeSlot;
            }
        };


    const getAppointmentBadge =
        (status) => {

            switch (status) {

                case "PENDING":
                    return "text-bg-warning";

                case "CONFIRMED":
                    return "text-bg-primary";

                case "COMPLETED":
                    return "text-bg-success";

                case "CANCELLED":
                    return "text-bg-secondary";

                default:
                    return "text-bg-light";
            }
        };


    const getPaymentBadge =
        (status) => {

            switch (status) {

                case "PAID":
                    return "text-bg-success";

                case "FAILED":
                    return "text-bg-danger";

                case "UNPAID":
                    return "text-bg-warning";

                default:
                    return "text-bg-light";
            }
        };


    const canPay =

        appointment
            .appointmentStatus ===
            "PENDING"

        &&

        appointment
            .paymentStatus ===
            "UNPAID";


    /*
     * Refunds are not part
     * of the project.
     *
     * So paid appointments
     * cannot be cancelled.
     */
    // eslint-disable-next-line no-unused-vars
    const canCancel =
        canPay;


    return (

        <div className="card shadow-sm h-100">

            <div className="card-body">


                <div
                    className="
                        d-flex
                        justify-content-between
                        align-items-start
                        mb-3
                    "
                >

                    <div>

                        <h5 className="mb-1">
                            {
                                appointment
                                    .doctorName
                            }
                        </h5>

                        <p className="text-primary mb-0">
                            {
                                appointment
                                    .specialization
                            }
                        </p>

                    </div>


                    <span
                        className={
                            `badge ${
                                getAppointmentBadge(
                                    appointment
                                        .appointmentStatus
                                )
                            }`
                        }
                    >

                        {
                            appointment
                                .appointmentStatus
                        }

                    </span>

                </div>


                <hr />


                <p className="mb-2">

                    <strong>
                        Appointment ID:
                    </strong>{" "}

                    {appointment.id}

                </p>


                <p className="mb-2">

                    <strong>
                        Date:
                    </strong>{" "}

                    {
                        appointment
                            .appointmentDate
                    }

                </p>


                <p className="mb-2">

                    <strong>
                        Time:
                    </strong>{" "}

                    {
                        formatTimeSlot(
                            appointment
                                .timeSlot
                        )
                    }

                </p>


                <p className="mb-2">

                    <strong>
                        Consultation Fee:
                    </strong>{" "}

                    ₹{
                        appointment
                            .consultationFee
                    }

                </p>


                <p className="mb-4">

                    <strong>
                        Payment:
                    </strong>{" "}

                    <span
                        className={
                            `badge ${
                                getPaymentBadge(
                                    appointment
                                        .paymentStatus
                                )
                            }`
                        }
                    >

                        {
                            appointment
                                .paymentStatus
                        }

                    </span>

                </p>


                {/* PAYMENT + CANCEL */}

                {
                    canPay
                    && (

                        <div className="d-grid gap-2">

                            <PaymentButton
                                appointment={
                                    appointment
                                }
                                onPaymentSuccess={
                                    onPaymentSuccess
                                }
                            />


                            <button
                                type="button"
                                className="btn btn-outline-danger"
                                onClick={
                                    () =>
                                        onCancel(
                                            appointment.id
                                        )
                                }
                                disabled={
                                    cancellingId ===
                                    appointment.id
                                }
                            >

                                {
                                    cancellingId ===
                                    appointment.id

                                        ? "Cancelling..."

                                        : "Cancel Appointment"
                                }

                            </button>

                        </div>

                    )
                }


                {/* CONFIRMED */}

                {
                    appointment
                        .appointmentStatus ===
                        "CONFIRMED"

                    &&

                    appointment
                        .paymentStatus ===
                        "PAID"

                    && (

                        <div className="alert alert-success mb-0">

                            Payment completed.
                            Appointment confirmed.

                        </div>

                    )
                }


                {/* VIEW PRESCRIPTION */}

                {
                    appointment
                        .appointmentStatus ===
                        "COMPLETED"
                    && (

                        <button
                            type="button"
                            className="btn btn-outline-primary w-100"
                            onClick={
                                () =>
                                    onViewPrescription(
                                        appointment.id
                                    )
                            }
                        >
                            View Prescription
                        </button>

                    )
                }


                {/* CANCELLED */}

                {
                    appointment
                        .appointmentStatus ===
                        "CANCELLED"
                    && (

                        <div
                            className="
                                alert
                                alert-secondary
                                mb-0
                            "
                        >

                            This appointment
                            has been cancelled.

                        </div>

                    )
                }

            </div>

        </div>
    );
}


export default AppointmentCard;