import {
    Link
} from "react-router";


function DoctorCard({
    doctor
}) {

    return (

        <div className="card h-100 shadow-sm">

            {
                doctor.photoUrl
                    ? (

                        <img
                            src={doctor.photoUrl}
                            className="card-img-top"
                            alt={doctor.name}
                            style={{
                                height: "220px",
                                objectFit: "cover"
                            }}
                        />

                    )
                    : (

                        <div
                            className="
                                bg-light
                                d-flex
                                align-items-center
                                justify-content-center
                                text-secondary
                            "
                            style={{
                                height: "220px"
                            }}
                        >
                            No Photo
                        </div>

                    )
            }


            <div className="card-body d-flex flex-column">

                <h5 className="card-title">
                    {doctor.name}
                </h5>


                <p className="text-primary fw-semibold mb-2">

                    {doctor.specialization}

                </p>


                <p className="card-text mb-2">

                    <strong>
                        Experience:
                    </strong>{" "}

                    {doctor.experienceYears} years

                </p>


                <p className="card-text mb-4">

                    <strong>
                        Consultation Fee:
                    </strong>{" "}

                    ₹{doctor.consultationFee}

                </p>


                <Link
                    to={
                        `/patient/doctors/${doctor.id}/book`
                    }
                    className="btn btn-primary w-100 mt-auto"
                >
                    Book Appointment
                </Link>

            </div>

        </div>
    );
}


export default DoctorCard;