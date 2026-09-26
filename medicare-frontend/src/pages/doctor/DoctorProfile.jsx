import {
    useEffect,
    useState
} from "react";

import doctorService from "../../services/doctorService";


const emptyProfile = {

    specialization: "",
    experienceYears: "",
    consultationFee: "",
    photoUrl: ""
};


function DoctorProfile() {

    const [
        formData,
        setFormData
    ] = useState(
        emptyProfile
    );


    const [
        profileExists,
        setProfileExists
    ] = useState(false);


    const [loading, setLoading] =
        useState(true);


    const [saving, setSaving] =
        useState(false);


    const [deleting, setDeleting] =
        useState(false);


    const [error, setError] =
        useState("");


    const [success, setSuccess] =
        useState("");

           const loadProfile =
        async () => {

            try {

                setLoading(true);
                setError("");


                const profile =
                    await doctorService
                        .getMyProfile();


                setProfileExists(true);


                setFormData({

                    specialization:
                        profile.specialization
                        || "",

                    experienceYears:
                        profile.experienceYears
                        ?? "",

                    consultationFee:
                        profile.consultationFee
                        ?? "",

                    photoUrl:
                        profile.photoUrl
                        || ""
                });

            } catch (error) {

                /*
                 * 404 means this doctor has
                 * not created a profile yet.
                 *
                 * This is not treated as a
                 * frontend error.
                 */
                if (
                    error.response
                        ?.status === 404
                ) {

                    setProfileExists(false);

                    setFormData(
                        emptyProfile
                    );

                    return;
                }


                setError(
                    error.response
                        ?.data
                        ?.message
                    ||
                    "Unable to load doctor profile"
                );

            } finally {

                setLoading(false);
            }
        };

    useEffect(() => {

        // eslint-disable-next-line react-hooks/set-state-in-effect
        loadProfile();

    }, []);


 


    const handleChange =
        (event) => {

            const {
                name,
                value
            } = event.target;


            setFormData(
                previous => ({

                    ...previous,

                    [name]: value
                })
            );
        };


    const handleSubmit =
        async (event) => {

            event.preventDefault();

            setError("");
            setSuccess("");


            const profileData = {

                specialization:
                    formData
                        .specialization
                        .trim(),

                experienceYears:
                    Number(
                        formData
                            .experienceYears
                    ),

                consultationFee:
                    Number(
                        formData
                            .consultationFee
                    ),

                photoUrl:
                    formData
                        .photoUrl
                        .trim()
            };


            try {

                setSaving(true);


                let response;


                if (profileExists) {

                    response =
                        await doctorService
                            .updateMyProfile(
                                profileData
                            );

                    setSuccess(
                        "Doctor profile updated successfully."
                    );

                } else {

                    response =
                        await doctorService
                            .createMyProfile(
                                profileData
                            );

                    setProfileExists(
                        true
                    );

                    setSuccess(
                        "Doctor profile created successfully."
                    );
                }


                setFormData({

                    specialization:
                        response.specialization
                        || "",

                    experienceYears:
                        response.experienceYears
                        ?? "",

                    consultationFee:
                        response.consultationFee
                        ?? "",

                    photoUrl:
                        response.photoUrl
                        || ""
                });

            } catch (error) {

                setError(
                    error.response
                        ?.data
                        ?.message
                    ||
                    "Unable to save doctor profile"
                );

            } finally {

                setSaving(false);
            }
        };


    const handleDelete =
        async () => {

            const confirmed =
                window.confirm(
                    "Are you sure you want to delete your doctor profile?"
                );


            if (!confirmed) {
                return;
            }


            try {

                setDeleting(true);
                setError("");
                setSuccess("");


                await doctorService
                    .deleteMyProfile();


                setProfileExists(false);

                setFormData(
                    emptyProfile
                );


                setSuccess(
                    "Doctor profile deleted successfully."
                );

            } catch (error) {

                setError(
                    error.response
                        ?.data
                        ?.message
                    ||
                    "Unable to delete doctor profile"
                );

            } finally {

                setDeleting(false);
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
                    Loading doctor profile...
                </p>

            </div>
        );
    }


    return (

        <div className="container py-5">

            <div className="row justify-content-center">

                <div className="col-lg-8">


                    {/* HEADING */}

                    <div className="mb-4">

                        <h2>
                            {
                                profileExists
                                    ? "My Doctor Profile"
                                    : "Create Doctor Profile"
                            }
                        </h2>

                        <p className="text-secondary">

                            Manage the information
                            patients see when
                            searching for doctors.

                        </p>

                    </div>


                    {/* SUCCESS */}

                    {success && (

                        <div
                            className="alert alert-success"
                        >
                            {success}
                        </div>

                    )}


                    {/* ERROR */}

                    {error && (

                        <div
                            className="alert alert-danger"
                        >
                            {error}
                        </div>

                    )}


                    {/* PROFILE FORM */}

                    <div className="card shadow-sm">

                        <div className="card-body p-4">


                            {/* PHOTO PREVIEW */}

                            {
                                formData.photoUrl
                                && (

                                    <div className="text-center mb-4">

                                        <img
                                            src={
                                                formData.photoUrl
                                            }
                                            alt="Doctor"
                                            className="rounded"
                                            style={{
                                                width: "140px",
                                                height: "140px",
                                                objectFit: "cover"
                                            }}
                                        />

                                    </div>

                                )
                            }


                            <form
                                onSubmit={
                                    handleSubmit
                                }
                            >


                                {/* SPECIALIZATION */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        Specialization
                                    </label>


                                    <select
                                        name="specialization"
                                        className="form-select"
                                        value={
                                            formData.specialization
                                        }
                                        onChange={
                                            handleChange
                                        }
                                        required
                                    >

                                        <option value="">
                                            Select specialization
                                        </option>

                                        <option value="Orthopedic">
                                            Orthopedic
                                        </option>

                                        <option value="Dentist">
                                            Dentist
                                        </option>

                                        <option value="General Physician">
                                            General Physician
                                        </option>

                                        <option value="Cardiologist">
                                            Cardiologist
                                        </option>

                                    </select>

                                </div>


                                {/* EXPERIENCE */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        Experience Years
                                    </label>

                                    <input
                                        type="number"
                                        name="experienceYears"
                                        className="form-control"
                                        min="0"
                                        value={
                                            formData.experienceYears
                                        }
                                        onChange={
                                            handleChange
                                        }
                                        required
                                    />

                                </div>


                                {/* CONSULTATION FEE */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        Consultation Fee
                                    </label>


                                    <div className="input-group">

                                        <span className="input-group-text">
                                            ₹
                                        </span>

                                        <input
                                            type="number"
                                            name="consultationFee"
                                            className="form-control"
                                            min="1"
                                            step="0.01"
                                            value={
                                                formData.consultationFee
                                            }
                                            onChange={
                                                handleChange
                                            }
                                            required
                                        />

                                    </div>

                                </div>


                                {/* PHOTO URL */}

                                <div className="mb-4">

                                    <label className="form-label">
                                        Photo URL
                                    </label>

                                    <input
                                        type="url"
                                        name="photoUrl"
                                        className="form-control"
                                        placeholder="https://example.com/doctor.jpg"
                                        value={
                                            formData.photoUrl
                                        }
                                        onChange={
                                            handleChange
                                        }
                                    />

                                </div>


                                <button
                                    type="submit"
                                    className="btn btn-primary w-100"
                                    disabled={
                                        saving
                                    }
                                >

                                    {
                                        saving

                                            ? "Saving..."

                                            : profileExists

                                                ? "Update Profile"

                                                : "Create Profile"
                                    }

                                </button>


                                {
                                    profileExists
                                    && (

                                        <button
                                            type="button"
                                            className="btn btn-outline-danger w-100 mt-3"
                                            onClick={
                                                handleDelete
                                            }
                                            disabled={
                                                deleting
                                            }
                                        >

                                            {
                                                deleting
                                                    ? "Deleting..."
                                                    : "Delete Profile"
                                            }

                                        </button>

                                    )
                                }

                            </form>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}


export default DoctorProfile;