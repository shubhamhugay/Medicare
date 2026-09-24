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


                <div className="col-md-6">

                    <div className="card h-100 shadow-sm">

                        <div className="card-body">

                            <h5>
                                My Schedule
                            </h5>

                            <p className="text-secondary">
                                View your assigned patient
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


                <div className="col-md-6">

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


            </div>

        </div>
    );
}


export default DoctorDashboard;