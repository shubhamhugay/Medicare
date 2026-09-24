import {
    useEffect,
    useState
} from "react";

import appointmentService from "../../services/appointmentService";

import AppointmentCard from "../../components/appointment/AppointmentCard";


function MyAppointments() {

    const [
        appointments,
        setAppointments
    ] = useState([]);


    const [loading, setLoading] =
        useState(true);


    const [error, setError] =
        useState("");


    const [
        cancellingId,
        setCancellingId
    ] = useState(null);
    const loadAppointments =
        async () => {

            try {

                setLoading(true);
                setError("");


                const data =
                    await appointmentService
                        .getMyAppointments();


                setAppointments(data);

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

    useEffect(() => {

        // eslint-disable-next-line react-hooks/set-state-in-effect
        loadAppointments();

    }, []);





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

                setCancellingId(null);
            }
        };


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


    return (

        <div className="container py-5">


            <div className="mb-4">

                <h2>
                    My Appointments
                </h2>

                <p className="text-secondary">
                    View and manage your
                    doctor appointments.
                </p>

            </div>


            {error && (

                <div className="alert alert-danger">

                    {error}

                </div>

            )}


            {
                appointments.length === 0

                    ? (

                        <div className="alert alert-info">

                            You do not have any
                            appointments yet.

                        </div>

                    )

                    : (

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
                                            />

                                        </div>

                                    )
                                )
                            }

                        </div>

                    )
            }

        </div>
    );
}


export default MyAppointments;