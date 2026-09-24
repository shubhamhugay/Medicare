import {
    useNavigate
} from "react-router";

import {
    useAuth
} from "../../context/AuthContext";


function PatientDashboard() {

    const navigate =
        useNavigate();

    const {
        user,
        logout
    } = useAuth();


    const handleLogout = () => {

        logout();

        navigate(
            "/login"
        );
    };


    return (

        <div className="container py-5">

            <div
                className="
                    d-flex
                    justify-content-between
                    align-items-center
                    mb-4
                "
            >

                <div>

                    <h2>
                        Patient Dashboard
                    </h2>

                    <p className="text-secondary mb-0">
                        Welcome, {user?.name}
                    </p>

                </div>


                <button
                    className="btn btn-outline-danger"
                    onClick={handleLogout}
                >
                    Logout
                </button>

            </div>


            <div className="alert alert-info">

                Patient healthcare modules
                will be added next.

            </div>

        </div>
    );
}


export default PatientDashboard;