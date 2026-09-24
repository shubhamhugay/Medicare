import {
    Link
} from "react-router";

import {
    useAuth
} from "../../context/AuthContext";


function PatientDashboard() {

    const {
        user
    } = useAuth();


    return (

        <div className="container py-5">

            <div className="mb-5">

                <h2>
                    Welcome, {user?.name}
                </h2>

                <p className="text-secondary">
                    Manage your healthcare
                    from your dashboard.
                </p>

            </div>


            <div className="row g-4">


                {/* FIND DOCTOR */}

                <div className="col-md-6 col-lg-3">

                    <div className="card h-100 shadow-sm">

                        <div className="card-body">

                            <h5 className="card-title">
                                Find Doctor
                            </h5>

                            <p className="card-text text-secondary">
                                Search doctors by specialization,
                                name and consultation fee.
                            </p>

                            <button
                                className="btn btn-primary"
                                disabled
                            >
                                Coming Next
                            </button>

                        </div>

                    </div>

                </div>


                {/* APPOINTMENTS */}

                <div className="col-md-6 col-lg-3">

                    <div className="card h-100 shadow-sm">

                        <div className="card-body">

                            <h5 className="card-title">
                                My Appointments
                            </h5>

                            <p className="card-text text-secondary">
                                View upcoming and completed
                                appointments.
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


                {/* PRESCRIPTIONS */}

                <div className="col-md-6 col-lg-3">

                    <div className="card h-100 shadow-sm">

                        <div className="card-body">

                            <h5 className="card-title">
                                Prescriptions
                            </h5>

                            <p className="card-text text-secondary">
                                View prescriptions from
                                completed appointments.
                            </p>

                            <Link
    to="/patient/doctors"
    className="btn btn-primary"
>
    Find Doctor
</Link>

                        </div>

                    </div>

                </div>


                {/* PROFILE */}

                <div className="col-md-6 col-lg-3">

                    <div className="card h-100 shadow-sm">

                        <div className="card-body">

                            <h5 className="card-title">
                                My Profile
                            </h5>

                            <p className="card-text text-secondary">
                                View your Medicare
                                account information.
                            </p>

                            <Link
                                to="/patient/profile"
                                className="btn btn-outline-primary"
                            >
                                View Profile
                            </Link>

                        </div>

                    </div>

                </div>


            </div>

        </div>
    );
}


export default PatientDashboard;