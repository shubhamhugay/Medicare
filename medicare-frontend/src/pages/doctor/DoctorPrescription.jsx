import {
    useEffect,
    useState
} from "react";

import {
    Link,
    useParams
} from "react-router";

import prescriptionService from "../../services/prescriptionService";


const emptyMedicine = {

    medicineName: "",
    dosage: "",
    frequency: "",
    duration: ""
};


function DoctorPrescription() {

    const {
        appointmentId
    } = useParams();


    const [diagnosis, setDiagnosis] =
        useState("");


    const [doctorNotes, setDoctorNotes] =
        useState("");


    const [medicines, setMedicines] =
        useState([
            { ...emptyMedicine }
        ]);


    const [
        existingPrescription,
        setExistingPrescription
    ] = useState(null);


    const [checking, setChecking] =
        useState(true);


    const [saving, setSaving] =
        useState(false);


    const [error, setError] =
        useState("");


    const [success, setSuccess] =
        useState("");
 const checkExistingPrescription =
        async () => {

            try {

                setChecking(true);
                setError("");


                const prescription =
                    await prescriptionService
                        .getPrescriptionByAppointment(
                            appointmentId
                        );


                setExistingPrescription(
                    prescription
                );

            } catch (error) {

                /*
                 * 404 simply means the doctor
                 * has not created one yet.
                 */
                if (
                    error.response
                        ?.status === 404
                ) {

                    setExistingPrescription(
                        null
                    );

                    return;
                }


                setError(
                    error.response
                        ?.data
                        ?.message
                    ||
                    "Unable to check prescription"
                );

            } finally {

                setChecking(false);
            }
        };

    useEffect(() => {

        // eslint-disable-next-line react-hooks/set-state-in-effect
        checkExistingPrescription();

    }, [appointmentId]);


   


    const handleMedicineChange =
        (
            index,
            event
        ) => {

            const {
                name,
                value
            } = event.target;


            setMedicines(
                previousMedicines => {

                    const updatedMedicines =
                        [...previousMedicines];


                    updatedMedicines[index] = {

                        ...updatedMedicines[index],

                        [name]: value
                    };


                    return updatedMedicines;
                }
            );
        };


    const addMedicine = () => {

        setMedicines(
            previousMedicines => [

                ...previousMedicines,

                { ...emptyMedicine }
            ]
        );
    };


    const removeMedicine =
        (index) => {

            if (
                medicines.length === 1
            ) {

                return;
            }


            setMedicines(
                previousMedicines =>
                    previousMedicines.filter(
                        (
                            medicine,
                            medicineIndex
                        ) =>
                            medicineIndex !==
                            index
                    )
            );
        };


    const handleSubmit =
        async (event) => {

            event.preventDefault();

            setError("");
            setSuccess("");


            const prescriptionData = {

                appointmentId:
                    Number(
                        appointmentId
                    ),

                diagnosis:
                    diagnosis.trim(),

                doctorNotes:
                    doctorNotes.trim(),

                medicines
            };


            try {

                setSaving(true);


                const response =
                    await prescriptionService
                        .createPrescription(
                            prescriptionData
                        );


                setExistingPrescription(
                    response
                );


                setSuccess(
                    "Prescription saved successfully."
                );

            } catch (error) {

                setError(
                    error.response
                        ?.data
                        ?.message
                    ||
                    "Unable to save prescription"
                );

            } finally {

                setSaving(false);
            }
        };


    if (checking) {

        return (

            <div className="container py-5 text-center">

                <div
                    className="spinner-border text-primary"
                >
                </div>

                <p className="text-secondary mt-3">
                    Loading prescription...
                </p>

            </div>
        );
    }


    return (

        <div className="container py-5">

            <div className="row justify-content-center">

                <div className="col-lg-9">


                    <div className="mb-4">

                        <h2>
                            Prescription
                        </h2>

                        <p className="text-secondary">

                            Appointment ID:{" "}

                            <strong>
                                {appointmentId}
                            </strong>

                        </p>

                    </div>


                    {success && (

                        <div className="alert alert-success">
                            {success}
                        </div>

                    )}


                    {error && (

                        <div className="alert alert-danger">
                            {error}
                        </div>

                    )}


                    {
                        existingPrescription

                            ? (

                                <ExistingPrescription
                                    prescription={
                                        existingPrescription
                                    }
                                />

                            )

                            : (

                                <form
                                    onSubmit={
                                        handleSubmit
                                    }
                                >

                                    <div className="card shadow-sm mb-4">

                                        <div className="card-body p-4">

                                            <h5 className="mb-3">
                                                Diagnosis
                                            </h5>

                                            <textarea
                                                className="form-control"
                                                rows="4"
                                                value={
                                                    diagnosis
                                                }
                                                onChange={
                                                    (event) =>
                                                        setDiagnosis(
                                                            event.target.value
                                                        )
                                                }
                                                required
                                            >
                                            </textarea>

                                        </div>

                                    </div>


                                    <div className="card shadow-sm mb-4">

                                        <div className="card-body p-4">

                                            <div
                                                className="
                                                    d-flex
                                                    justify-content-between
                                                    align-items-center
                                                    mb-4
                                                "
                                            >

                                                <h5 className="mb-0">
                                                    Medicines
                                                </h5>


                                                <button
                                                    type="button"
                                                    className="btn btn-outline-primary btn-sm"
                                                    onClick={
                                                        addMedicine
                                                    }
                                                >
                                                    + Add Medicine
                                                </button>

                                            </div>


                                            {
                                                medicines.map(
                                                    (
                                                        medicine,
                                                        index
                                                    ) => (

                                                        <div
                                                            key={
                                                                index
                                                            }
                                                            className="border rounded p-3 mb-3"
                                                        >

                                                            <div
                                                                className="
                                                                    d-flex
                                                                    justify-content-between
                                                                    mb-3
                                                                "
                                                            >

                                                                <strong>
                                                                    Medicine {
                                                                        index + 1
                                                                    }
                                                                </strong>


                                                                {
                                                                    medicines.length > 1
                                                                    && (

                                                                        <button
                                                                            type="button"
                                                                            className="btn btn-outline-danger btn-sm"
                                                                            onClick={
                                                                                () =>
                                                                                    removeMedicine(
                                                                                        index
                                                                                    )
                                                                            }
                                                                        >
                                                                            Remove
                                                                        </button>

                                                                    )
                                                                }

                                                            </div>


                                                            <div className="row g-3">


                                                                <div className="col-md-6">

                                                                    <label className="form-label">
                                                                        Medicine Name
                                                                    </label>

                                                                    <input
                                                                        type="text"
                                                                        name="medicineName"
                                                                        className="form-control"
                                                                        value={
                                                                            medicine.medicineName
                                                                        }
                                                                        onChange={
                                                                            (event) =>
                                                                                handleMedicineChange(
                                                                                    index,
                                                                                    event
                                                                                )
                                                                        }
                                                                        required
                                                                    />

                                                                </div>


                                                                <div className="col-md-6">

                                                                    <label className="form-label">
                                                                        Dosage
                                                                    </label>

                                                                    <input
                                                                        type="text"
                                                                        name="dosage"
                                                                        className="form-control"
                                                                        placeholder="Example: 500 mg"
                                                                        value={
                                                                            medicine.dosage
                                                                        }
                                                                        onChange={
                                                                            (event) =>
                                                                                handleMedicineChange(
                                                                                    index,
                                                                                    event
                                                                                )
                                                                        }
                                                                        required
                                                                    />

                                                                </div>


                                                                <div className="col-md-6">

                                                                    <label className="form-label">
                                                                        Frequency
                                                                    </label>

                                                                    <input
                                                                        type="text"
                                                                        name="frequency"
                                                                        className="form-control"
                                                                        placeholder="Example: Twice daily"
                                                                        value={
                                                                            medicine.frequency
                                                                        }
                                                                        onChange={
                                                                            (event) =>
                                                                                handleMedicineChange(
                                                                                    index,
                                                                                    event
                                                                                )
                                                                        }
                                                                        required
                                                                    />

                                                                </div>


                                                                <div className="col-md-6">

                                                                    <label className="form-label">
                                                                        Duration
                                                                    </label>

                                                                    <input
                                                                        type="text"
                                                                        name="duration"
                                                                        className="form-control"
                                                                        placeholder="Example: 5 days"
                                                                        value={
                                                                            medicine.duration
                                                                        }
                                                                        onChange={
                                                                            (event) =>
                                                                                handleMedicineChange(
                                                                                    index,
                                                                                    event
                                                                                )
                                                                        }
                                                                        required
                                                                    />

                                                                </div>

                                                            </div>

                                                        </div>

                                                    )
                                                )
                                            }

                                        </div>

                                    </div>


                                    <div className="card shadow-sm mb-4">

                                        <div className="card-body p-4">

                                            <h5 className="mb-3">
                                                Doctor Notes
                                            </h5>

                                            <textarea
                                                className="form-control"
                                                rows="4"
                                                value={
                                                    doctorNotes
                                                }
                                                onChange={
                                                    (event) =>
                                                        setDoctorNotes(
                                                            event.target.value
                                                        )
                                                }
                                                placeholder="Additional advice..."
                                            >
                                            </textarea>

                                        </div>

                                    </div>


                                    <div className="d-flex gap-2">

                                        <Link
                                            to="/doctor/schedule"
                                            className="btn btn-outline-secondary"
                                        >
                                            Back
                                        </Link>


                                        <button
                                            type="submit"
                                            className="btn btn-primary flex-grow-1"
                                            disabled={
                                                saving
                                            }
                                        >

                                            {
                                                saving
                                                    ? "Saving Prescription..."
                                                    : "Save Prescription"
                                            }

                                        </button>

                                    </div>

                                </form>

                            )
                    }

                </div>

            </div>

        </div>
    );
}


function ExistingPrescription({
    prescription
}) {

    return (

        <div className="card shadow-sm">

            <div className="card-body p-4">

                <div className="alert alert-info">

                    A prescription already exists
                    for this appointment.

                </div>


                <h5>
                    Patient
                </h5>

                <p>
                    {prescription.patientName}
                </p>


                <h5>
                    Diagnosis
                </h5>

                <p>
                    {prescription.diagnosis}
                </p>


                <h5 className="mt-4">
                    Medicines
                </h5>


                <div className="table-responsive">

                    <table className="table table-bordered">

                        <thead>

                            <tr>

                                <th>
                                    Medicine
                                </th>

                                <th>
                                    Dosage
                                </th>

                                <th>
                                    Frequency
                                </th>

                                <th>
                                    Duration
                                </th>

                            </tr>

                        </thead>


                        <tbody>

                            {
                                prescription
                                    .medicines
                                    .map(
                                        (
                                            medicine,
                                            index
                                        ) => (

                                            <tr key={index}>

                                                <td>
                                                    {
                                                        medicine
                                                            .medicineName
                                                    }
                                                </td>

                                                <td>
                                                    {
                                                        medicine
                                                            .dosage
                                                    }
                                                </td>

                                                <td>
                                                    {
                                                        medicine
                                                            .frequency
                                                    }
                                                </td>

                                                <td>
                                                    {
                                                        medicine
                                                            .duration
                                                    }
                                                </td>

                                            </tr>

                                        )
                                    )
                            }

                        </tbody>

                    </table>

                </div>


                {
                    prescription.doctorNotes
                    && (

                        <>
                            <h5 className="mt-4">
                                Doctor Notes
                            </h5>

                            <p>
                                {
                                    prescription
                                        .doctorNotes
                                }
                            </p>
                        </>

                    )
                }


                <Link
                    to="/doctor/schedule"
                    className="btn btn-outline-primary mt-3"
                >
                    Back to Schedule
                </Link>

            </div>

        </div>
    );
}


export default DoctorPrescription;