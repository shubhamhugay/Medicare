import {
    useEffect,
    useState
} from "react";

import appointmentService from "../../services/appointmentService";

import prescriptionService from "../../services/prescriptionService";

import AppointmentCard from "../../components/appointment/AppointmentCard";

import PrescriptionModal from "../../components/prescription/PrescriptionModal";


function MyAppointments() {

    const [
        appointments,
        setAppointments
    ] = useState([]);


    const [
        loading,
        setLoading
    ] = useState(true);


    const [
        error,
        setError
    ] = useState("");


    const [
        cancellingId,
        setCancellingId
    ] = useState(null);


    // Prescription modal state

    const [
        showPrescription,
        setShowPrescription
    ] = useState(false);


    const [
        prescription,
        setPrescription
    ] = useState(null);


    const [
        prescriptionLoading,
        setPrescriptionLoading
    ] = useState(false);


    const [
        prescriptionError,
        setPrescriptionError
    ] = useState("");


    // -------------------------------------------------
    // LOAD PATIENT APPOINTMENTS
    // -------------------------------------------------

    useEffect(() => {

        const loadAppointments =
            async () => {

                try {

                    setLoading(true);
                    setError("");


                    const data =
                        await appointmentService
                            .getMyAppointments();


                    setAppointments(
                        data
                    );

                } catch (error) {

                    setError(
                        error.response
                            ?.data
                            ?.message
                        ||
                        "Unable to load appointments"
                    );

                } finally {

                    setLoading(false);
                }
            };


        loadAppointments();

    }, []);


    // -------------------------------------------------
    // CANCEL APPOINTMENT
    // -------------------------------------------------

    const handleCancel =
        async (appointmentId) => {

            const confirmed =
                window.confirm(
                    "Are you sure you want to cancel this appointment?"
                );


            if (!confirmed) {
                return;
            }


            try {

                setCancellingId(
                    appointmentId
                );

                setError("");


                const updatedAppointment =
                    await appointmentService
                        .cancelAppointment(
                            appointmentId
                        );


                setAppointments(
                    previousAppointments =>
                        previousAppointments.map(
                            appointment => {

                                if (
                                    appointment.id ===
                                    appointmentId
                                ) {

                                    return updatedAppointment;
                                }

                                return appointment;
                            }
                        )
                );

            } catch (error) {

                setError(
                    error.response
                        ?.data
                        ?.message
                    ||
                    "Unable to cancel appointment"
                );

            } finally {

                setCancellingId(
                    null
                );
            }
        };


    // -------------------------------------------------
    // VIEW PRESCRIPTION
    // -------------------------------------------------

    const handleViewPrescription =
        async (appointmentId) => {

            setShowPrescription(
                true
            );

            setPrescription(
                null
            );

            setPrescriptionError(
                ""
            );

            setPrescriptionLoading(
                true
            );


            try {

                const data =
                    await prescriptionService
                        .getPrescriptionByAppointment(
                            appointmentId
                        );


                setPrescription(
                    data
                );

            } catch (error) {

                if (
                    error.response
                        ?.status === 404
                ) {

                    setPrescriptionError(
                        "Prescription is not available yet."
                    );

                } else {

                    setPrescriptionError(
                        error.response
                            ?.data
                            ?.message
                        ||
                        "Unable to load prescription"
                    );
                }

            } finally {

                setPrescriptionLoading(
                    false
                );
            }
        };


    // -------------------------------------------------
    // CLOSE PRESCRIPTION MODAL
    // -------------------------------------------------

    const closePrescription =
        () => {

            setShowPrescription(
                false
            );

            setPrescription(
                null
            );

            setPrescriptionError(
                ""
            );

            setPrescriptionLoading(
                false
            );
        };


    // -------------------------------------------------
    // LOADING
    // -------------------------------------------------

    if (loading) {

        return (

            <div className="container py-5 text-center">

                <div
                    className="
                        spinner-border
                        text-primary
                    "
                >
                </div>

                <p className="text-secondary mt-3">
                    Loading appointments...
                </p>

            </div>
        );
    }


    // -------------------------------------------------
    // PAGE
    // -------------------------------------------------

    return (

        <div className="container py-5">


            {/* PAGE HEADER */}

            <div className="mb-4">

                <h2>
                    My Appointments
                </h2>

                <p className="text-secondary">
                    View and manage your
                    doctor appointments.
                </p>

            </div>


            {/* ERROR */}

            {error && (

                <div className="alert alert-danger">

                    {error}

                </div>

            )}


            {/* NO APPOINTMENTS */}

            {
                appointments.length === 0

                    ? (

                        <div className="alert alert-info">

                            You do not have any
                            appointments yet.

                        </div>

                    )

                    : (

                        // APPOINTMENT CARDS

                        <div className="row g-4">

                            {
                                appointments.map(
                                    appointment => (

                                        <div
                                            className="
                                                col-md-6
                                                col-lg-4
                                            "
                                            key={
                                                appointment.id
                                            }
                                        >

                                            <AppointmentCard

                                                appointment={
                                                    appointment
                                                }

                                                onCancel={
                                                    handleCancel
                                                }

                                                cancellingId={
                                                    cancellingId
                                                }

                                                onViewPrescription={
                                                    handleViewPrescription
                                                }

                                            />

                                        </div>

                                    )
                                )
                            }

                        </div>

                    )
            }


            {/* PRESCRIPTION MODAL */}

            <PrescriptionModal

                show={
                    showPrescription
                }

                prescription={
                    prescription
                }

                loading={
                    prescriptionLoading
                }

                error={
                    prescriptionError
                }

                onClose={
                    closePrescription
                }

            />

        </div>
    );
}


export default MyAppointments;