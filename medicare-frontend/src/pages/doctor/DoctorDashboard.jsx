import { Link } from "react-router";

import {
    useAuth
} from "../../context/AuthContext";


function DoctorDashboard() {

    const {
        user
    } = useAuth();


    return (

        <div className="container py-5">

            <div className="mb-5">

                <h2>
                    Welcome, Dr. {user?.name}
                </h2>

                <p className="text-secondary">
                    Manage appointments and
                    patient consultations.
                </p>

            </div>


            <div className="row g-4">


                {/* My Schedule */}

                <div className="col-md-6 col-lg-4">

                    <div className="card h-100 shadow-sm">

                        <div className="card-body">

                            <h5>
                                My Schedule
                            </h5>

                            <p className="text-secondary">

                                View your assigned patient
                                appointments.

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


                {/* Prescriptions */}

                <div className="col-md-6 col-lg-4">

                    <div className="card h-100 shadow-sm">

                        <div className="card-body">

                            <h5>
                                Prescriptions
                            </h5>

                            <p className="text-secondary">

                                Add prescriptions after
                                completing consultations.

                            </p>

                            <button
                                className="btn btn-primary"
                                disabled
                            >
                                Coming Soon
                            </button>

                        </div>

                    </div>

                </div>


                {/* My Profile */}

                <div className="col-md-6 col-lg-4">

                    <div className="card h-100 shadow-sm">

                        <div className="card-body">

                            <h5>
                                My Profile
                            </h5>

                            <p className="text-secondary">

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
