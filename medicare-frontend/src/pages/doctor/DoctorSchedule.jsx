import {
    useEffect,
    useState
} from "react";

import appointmentService from "../../services/appointmentService";

import DoctorAppointmentCard from "../../components/appointment/DoctorAppointmentCard";


function DoctorSchedule() {

    const [
        appointments,
        setAppointments
    ] = useState([]);


    const [loading, setLoading] =
        useState(true);


    const [error, setError] =
        useState("");


    const [
        completingId,
        setCompletingId
    ] = useState(null);


    useEffect(() => {

        loadAppointments();

    }, []);


    const loadAppointments =
        async () => {

            try {

                setLoading(true);
                setError("");


                const data =
                    await appointmentService
                        .getDoctorAppointments();


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


    const handleComplete =
        async (appointmentId) => {

            const confirmed =
                window.confirm(
                    "Mark this appointment as completed?"
                );


            if (!confirmed) {
                return;
            }


            try {

                setCompletingId(
                    appointmentId
                );

                setError("");


                const updatedAppointment =
                    await appointmentService
                        .completeAppointment(
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
                    "Unable to complete appointment"
                );

            } finally {

                setCompletingId(null);
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
                    Loading schedule...
                </p>

            </div>
        );
    }


    return (

        <div className="container py-5">


            <div className="mb-4">

                <h2>
                    My Schedule
                </h2>

                <p className="text-secondary">
                    View and manage your
                    assigned appointments.
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
                            appointments assigned.

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

                                            <DoctorAppointmentCard

                                                appointment={
                                                    appointment
                                                }

                                                onComplete={
                                                    handleComplete
                                                }

                                                completingId={
                                                    completingId
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


export default DoctorSchedule;