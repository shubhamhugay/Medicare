import {
    Link
} from "react-router";


function DoctorAppointmentCard({
    appointment,
    onComplete,
    completingId
}) {


    // -------------------------------------------------
    // FORMAT TIME SLOT
    // -------------------------------------------------

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


    // -------------------------------------------------
    // APPOINTMENT STATUS BADGE
    // -------------------------------------------------

    const getStatusBadge =
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


    // -------------------------------------------------
    // CAN DOCTOR COMPLETE?
    // -------------------------------------------------

    const canComplete =

        appointment.appointmentStatus ===
            "CONFIRMED"

        &&

        appointment.paymentStatus ===
            "PAID";


    // -------------------------------------------------
    // WAITING FOR PAYMENT?
    // -------------------------------------------------

    const waitingForPayment =

        appointment.appointmentStatus ===
            "PENDING"

        &&

        appointment.paymentStatus ===
            "UNPAID";


    return (

        <div className="card shadow-sm h-100">

            <div className="card-body">


                {/* PATIENT + STATUS */}

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
                                appointment.patientName
                            }

                        </h5>


                        <small className="text-secondary">

                            Patient

                        </small>

                    </div>


                    <span
                        className={
                            `badge ${
                                getStatusBadge(
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


                {/* APPOINTMENT ID */}

                <p className="mb-2">

                    <strong>
                        Appointment ID:
                    </strong>{" "}

                    {appointment.id}

                </p>


                {/* DATE */}

                <p className="mb-2">

                    <strong>
                        Date:
                    </strong>{" "}

                    {
                        appointment
                            .appointmentDate
                    }

                </p>


                {/* TIME */}

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


                {/* CONSULTATION FEE */}

                <p className="mb-2">

                    <strong>
                        Consultation Fee:
                    </strong>{" "}

                    ₹{
                        appointment
                            .consultationFee
                    }

                </p>


                {/* PAYMENT STATUS */}

                <p className="mb-4">

                    <strong>
                        Payment:
                    </strong>{" "}

                    {
                        appointment
                            .paymentStatus
                    }

                </p>


                {/* WAITING FOR PATIENT PAYMENT */}

                {
                    waitingForPayment
                    && (

                        <div
                            className="
                                alert
                                alert-warning
                                mb-0
                            "
                        >

                            Waiting for patient payment.

                        </div>

                    )
                }


                {/* MARK COMPLETED */}

                {
                    canComplete
                    && (

                        <button
                            type="button"
                            className="btn btn-success w-100"
                            onClick={
                                () =>
                                    onComplete(
                                        appointment.id
                                    )
                            }
                            disabled={
                                completingId ===
                                appointment.id
                            }
                        >

                            {
                                completingId ===
                                    appointment.id

                                    ? "Completing..."

                                    : "Mark Completed"
                            }

                        </button>

                    )
                }


                {/* PRESCRIPTION */}

                {
                    appointment
                        .appointmentStatus ===
                        "COMPLETED"
                    && (

                        <Link
                            to={
                                `/doctor/appointments/${appointment.id}/prescription`
                            }
                            className="btn btn-primary w-100"
                        >
                            Add Prescription
                        </Link>

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

                            Patient cancelled
                            this appointment.

                        </div>

                    )
                }


            </div>

        </div>
    );
}


export default DoctorAppointmentCard;