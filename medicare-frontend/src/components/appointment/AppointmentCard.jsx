function AppointmentCard({
    appointment,
    onCancel,
    cancellingId,
    onViewPrescription
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


    // -------------------------------------------------
    // PAYMENT STATUS BADGE
    // -------------------------------------------------

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


    // -------------------------------------------------
    // CAN PATIENT CANCEL?
    // -------------------------------------------------

    const canCancel =

        appointment.appointmentStatus ===
            "PENDING"

        ||

        appointment.appointmentStatus ===
            "CONFIRMED";


    return (

        <div className="card shadow-sm h-100">

            <div className="card-body">


                {/* DOCTOR + STATUS */}

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


                {/* APPOINTMENT ID */}

                <p className="mb-2">

                    <strong>
                        Appointment ID:
                    </strong>{" "}

                    {
                        appointment.id
                    }

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


                {/* CANCEL BUTTON */}

                {
                    canCancel
                    && (

                        <button
                            type="button"
                            className="btn btn-outline-danger w-100"
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


                {/* CANCELLED MESSAGE */}

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