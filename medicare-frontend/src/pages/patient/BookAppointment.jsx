import {
    useEffect,
    useState
} from "react";

import {
    Link,
    useParams
} from "react-router";

import doctorService from "../../services/doctorService";

import appointmentService from "../../services/appointmentService";


function BookAppointment() {

    const {
        doctorId
    } = useParams();


    const [doctor, setDoctor] =
        useState(null);


    const [appointmentDate, setAppointmentDate] =
        useState("");


    const [timeSlot, setTimeSlot] =
        useState("");


    const [loadingDoctor, setLoadingDoctor] =
        useState(true);


    const [booking, setBooking] =
        useState(false);


    const [error, setError] =
        useState("");


    const [success, setSuccess] =
        useState(null);
const loadDoctor =
        async () => {

            try {

                setLoadingDoctor(true);
                setError("");


                const data =
                    await doctorService
                        .getDoctorById(
                            doctorId
                        );

                setDoctor(data);

            } catch (error) {

                setError(
                    error.response
                        ?.data
                        ?.message
                    ||
                    "Unable to load doctor"
                );

            } finally {

                setLoadingDoctor(false);
            }
        };

    useEffect(() => {

        // eslint-disable-next-line react-hooks/set-state-in-effect
        loadDoctor();

    }, [doctorId]);


    


    const getToday = () => {

        const today =
            new Date();

        const year =
            today.getFullYear();

        const month =
            String(
                today.getMonth() + 1
            ).padStart(2, "0");

        const day =
            String(
                today.getDate()
            ).padStart(2, "0");


        return `${year}-${month}-${day}`;
    };


    const handleSubmit =
        async (event) => {

            event.preventDefault();

            setError("");
            setSuccess(null);


            if (!appointmentDate) {

                setError(
                    "Please select an appointment date"
                );

                return;
            }


            if (!timeSlot) {

                setError(
                    "Please select a time slot"
                );

                return;
            }


            try {

                setBooking(true);


                const appointmentData = {

                    doctorId:
                        Number(doctorId),

                    appointmentDate,

                    timeSlot
                };


                const response =
                    await appointmentService
                        .createAppointment(
                            appointmentData
                        );


                setSuccess(response);


                setAppointmentDate("");
                setTimeSlot("");

            } catch (error) {

                setError(
                    error.response
                        ?.data
                        ?.message
                    ||
                    "Unable to book appointment"
                );

            } finally {

                setBooking(false);
            }
        };


    if (loadingDoctor) {

        return (

            <div className="container py-5 text-center">

                <div
                    className="spinner-border text-primary"
                >
                </div>

                <p className="text-secondary mt-3">
                    Loading doctor...
                </p>

            </div>
        );
    }


    if (!doctor) {

        return (

            <div className="container py-5">

                <div className="alert alert-danger">

                    Doctor information
                    could not be loaded.

                </div>

                <Link
                    to="/patient/doctors"
                    className="btn btn-primary"
                >
                    Back to Doctors
                </Link>

            </div>
        );
    }


    return (

        <div className="container py-5">

            <div className="row justify-content-center">

                <div className="col-lg-8">


                    {/* PAGE HEADER */}

                    <div className="mb-4">

                        <h2>
                            Book Appointment
                        </h2>

                        <p className="text-secondary">
                            Select your preferred
                            appointment date and time.
                        </p>

                    </div>


                    {/* DOCTOR INFORMATION */}

                    <div className="card shadow-sm mb-4">

                        <div className="card-body">

                            <div className="row align-items-center">

                                {
                                    doctor.photoUrl
                                    && (

                                        <div className="col-md-3 mb-3 mb-md-0">

                                            <img
                                                src={
                                                    doctor.photoUrl
                                                }
                                                alt={
                                                    doctor.name
                                                }
                                                className="img-fluid rounded"
                                            />

                                        </div>

                                    )
                                }


                                <div
                                    className={
                                        doctor.photoUrl
                                            ? "col-md-9"
                                            : "col-12"
                                    }
                                >

                                    <h4 className="mb-1">
                                        {doctor.name}
                                    </h4>

                                    <p className="text-primary fw-semibold mb-2">
                                        {
                                            doctor.specialization
                                        }
                                    </p>


                                    <p className="mb-1">

                                        <strong>
                                            Experience:
                                        </strong>{" "}

                                        {
                                            doctor.experienceYears
                                        } years

                                    </p>


                                    <p className="mb-0">

                                        <strong>
                                            Consultation Fee:
                                        </strong>{" "}

                                        ₹{
                                            doctor.consultationFee
                                        }

                                    </p>

                                </div>

                            </div>

                        </div>

                    </div>


                    {/* ERROR */}

                    {error && (

                        <div className="alert alert-danger">

                            {error}

                        </div>

                    )}


                    {/* SUCCESS */}

                    {success && (

                        <div className="alert alert-success">

                            <h5>
                                Appointment Booked
                            </h5>

                            <p className="mb-1">

                                Appointment ID:{" "}

                                <strong>
                                    {success.id}
                                </strong>

                            </p>


                            <p className="mb-1">

                                Doctor:{" "}

                                <strong>
                                    {success.doctorName}
                                </strong>

                            </p>


                            <p className="mb-1">

                                Date:{" "}

                                <strong>
                                    {
                                        success.appointmentDate
                                    }
                                </strong>

                            </p>


                            <p className="mb-1">

                                Slot:{" "}

                                <strong>
                                    {success.timeSlot}
                                </strong>

                            </p>


                            <p className="mb-1">

                                Status:{" "}

                                <strong>
                                    {
                                        success.appointmentStatus
                                    }
                                </strong>

                            </p>


                            <p className="mb-0">

                                Payment:{" "}

                                <strong>
                                    {
                                        success.paymentStatus
                                    }
                                </strong>

                            </p>

                        </div>

                    )}


                    {/* BOOKING FORM */}

                    <div className="card shadow-sm">

                        <div className="card-body p-4">

                            <form
                                onSubmit={
                                    handleSubmit
                                }
                            >


                                {/* DATE */}

                                <div className="mb-4">

                                    <label className="form-label">

                                        Appointment Date

                                    </label>

                                    <input
                                        type="date"
                                        className="form-control"
                                        min={
                                            getToday()
                                        }
                                        value={
                                            appointmentDate
                                        }
                                        onChange={
                                            (event) =>
                                                setAppointmentDate(
                                                    event.target.value
                                                )
                                        }
                                        required
                                    />

                                </div>


                                {/* TIME SLOT */}

                                <div className="mb-4">

                                    <label className="form-label">

                                        Select Time Slot

                                    </label>


                                    <div className="row g-3">


                                        {/* 10 AM */}

                                        <div className="col-md-4">

                                            <input
                                                type="radio"
                                                className="btn-check"
                                                name="timeSlot"
                                                id="slotMorning"
                                                value="MORNING_10_AM"
                                                checked={
                                                    timeSlot ===
                                                    "MORNING_10_AM"
                                                }
                                                onChange={
                                                    (event) =>
                                                        setTimeSlot(
                                                            event.target.value
                                                        )
                                                }
                                            />

                                            <label
                                                className="btn btn-outline-primary w-100"
                                                htmlFor="slotMorning"
                                            >
                                                10:00 AM
                                            </label>

                                        </div>


                                        {/* 2 PM */}

                                        <div className="col-md-4">

                                            <input
                                                type="radio"
                                                className="btn-check"
                                                name="timeSlot"
                                                id="slotAfternoon"
                                                value="AFTERNOON_2_PM"
                                                checked={
                                                    timeSlot ===
                                                    "AFTERNOON_2_PM"
                                                }
                                                onChange={
                                                    (event) =>
                                                        setTimeSlot(
                                                            event.target.value
                                                        )
                                                }
                                            />

                                            <label
                                                className="btn btn-outline-primary w-100"
                                                htmlFor="slotAfternoon"
                                            >
                                                2:00 PM
                                            </label>

                                        </div>


                                        {/* 5 PM */}

                                        <div className="col-md-4">

                                            <input
                                                type="radio"
                                                className="btn-check"
                                                name="timeSlot"
                                                id="slotEvening"
                                                value="EVENING_5_PM"
                                                checked={
                                                    timeSlot ===
                                                    "EVENING_5_PM"
                                                }
                                                onChange={
                                                    (event) =>
                                                        setTimeSlot(
                                                            event.target.value
                                                        )
                                                }
                                            />

                                            <label
                                                className="btn btn-outline-primary w-100"
                                                htmlFor="slotEvening"
                                            >
                                                5:00 PM
                                            </label>

                                        </div>

                                    </div>

                                </div>


                                {/* FEE */}

                                <div className="alert alert-light border">

                                    Consultation Fee:{" "}

                                    <strong>
                                        ₹{
                                            doctor.consultationFee
                                        }
                                    </strong>

                                </div>


                                <div className="d-flex gap-2">

                                    <Link
                                        to="/patient/doctors"
                                        className="btn btn-outline-secondary"
                                    >
                                        Back
                                    </Link>


                                    <button
                                        type="submit"
                                        className="btn btn-primary flex-grow-1"
                                        disabled={
                                            booking
                                        }
                                    >

                                        {
                                            booking
                                                ? "Booking..."
                                                : "Book Appointment"
                                        }

                                    </button>

                                </div>

                            </form>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}


export default BookAppointment;