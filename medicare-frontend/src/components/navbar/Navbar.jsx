import {
    Link,
    useNavigate
} from "react-router";

import {
    useAuth
} from "../../context/AuthContext";


function Navbar() {

    const navigate = useNavigate();

    const {
        user,
        isAuthenticated,
        logout
    } = useAuth();


    const handleLogout = () => {

        logout();

        navigate("/login");
    };


    return (

        <nav className="navbar navbar-expand-lg bg-white border-bottom">

            <div className="container">

                <Link
                    className="navbar-brand fw-bold text-primary"
                    to="/"
                >
                    Medicare
                </Link>


                <button
                    className="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#mainNavbar"
                >
                    <span className="navbar-toggler-icon"></span>
                </button>


                <div
                    className="collapse navbar-collapse"
                    id="mainNavbar"
                >

                    <ul className="navbar-nav ms-auto align-items-lg-center">


                        {!isAuthenticated && (

                            <>
                                <li className="nav-item">

                                    <Link
                                        className="nav-link"
                                        to="/login"
                                    >
                                        Login
                                    </Link>

                                </li>


                                <li className="nav-item">

                                    <Link
                                        className="btn btn-primary ms-lg-2"
                                        to="/register"
                                    >
                                        Register
                                    </Link>

                                </li>
                            </>

                        )}


                        {
                            isAuthenticated
                            &&
                            user?.role === "PATIENT"
                            && (

                                <>
                                    <li className="nav-item">

                                        <Link
                                            className="nav-link"
                                            to="/patient/dashboard"
                                        >
                                            Dashboard
                                        </Link>

                                    </li>


                                    {/* Doctors link added here */}

                                    <li className="nav-item">

                                        <Link
                                            className="nav-link"
                                            to="/patient/doctors"
                                        >
                                            Doctors
                                        </Link>

                                    </li>

                                    <li className="nav-item">

                                        <Link
                                            className="nav-link"
                                            to="/patient/appointments"
                                        >
                                            My Appointments
                                        </Link>

                                    </li>
                                    <li className="nav-item">

                                        <Link
                                            className="nav-link"
                                            to="/patient/profile"
                                        >
                                            My Profile
                                        </Link>

                                    </li>
                                </>

                            )
                        }


                        {
                            isAuthenticated
                            &&
                            user?.role === "DOCTOR"
                            && (

                                <li className="nav-item">

                                    <Link
                                        className="nav-link"
                                        to="/doctor/dashboard"
                                    >
                                        Dashboard
                                    </Link>

                                </li>

                            )
                        }


                        {isAuthenticated && (

                            <li className="nav-item ms-lg-3">

                                <button
                                    className="btn btn-outline-danger"
                                    onClick={handleLogout}
                                >
                                    Logout
                                </button>

                            </li>

                        )}


                    </ul>

                </div>

            </div>

        </nav>

    );
}


export default Navbar;