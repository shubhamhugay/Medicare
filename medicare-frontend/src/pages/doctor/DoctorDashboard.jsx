import {
    Link
} from "react-router";

import {
    useAuth
} from "../../context/AuthContext";


function DoctorDashboard() {

    const {
        user
    } = useAuth();


    return (

        <div className="container py-5">


            {/* WELCOME SECTION */}

            <div className="mb-5">

                <h2>
                    Welcome, Dr. {user?.name}
                </h2>

                <p className="text-secondary">
                    Manage appointments,
                    prescriptions and your
                    doctor profile.
                </p>

            </div>


            <div className="row g-4">


                {/* MY SCHEDULE */}

                <div className="col-md-6 col-lg-4">

                    <div className="card h-100 shadow-sm">

                        <div className="card-body">

                            <h5 className="card-title">
                                My Schedule
                            </h5>

                            <p className="card-text text-secondary">

                                View your assigned patient
                                appointments and manage
                                consultations.

                            </p>


                            <Link
                                to="/doctor/schedule"
                                className="btn btn-primary"
                            >
                                View Schedule
                            </Link>

                        </div>

                    </div>

                </div>


                {/* PRESCRIPTIONS */}

                <div className="col-md-6 col-lg-4">

                    <div className="card h-100 shadow-sm">

                        <div className="card-body">

                            <h5 className="card-title">
                                Prescriptions
                            </h5>

                            <p className="card-text text-secondary">

                                Add or view prescriptions
                                for completed patient
                                appointments.

                            </p>


                            <Link
                                to="/doctor/schedule"
                                className="btn btn-outline-primary"
                            >
                                Manage Prescriptions
                            </Link>

                        </div>

                    </div>

                </div>


                {/* MY PROFILE */}

                <div className="col-md-6 col-lg-4">

                    <div className="card h-100 shadow-sm">

                        <div className="card-body">

                            <h5 className="card-title">
                                My Profile
                            </h5>

                            <p className="card-text text-secondary">

                                Manage specialization,
                                experience, consultation
                                fee and profile photo.

                            </p>


                            <Link
                                to="/doctor/profile"
                                className="btn btn-outline-primary"
                            >
                                Manage Profile
                            </Link>

                        </div>

                    </div>

                </div>


            </div>

        </div>
    );
}


export default DoctorDashboard;