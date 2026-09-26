import {
    Link,
    NavLink,
    useNavigate
} from "react-router";

import {
    useAuth
} from "../../context/AuthContext";


function Navbar() {

    const navigate =
        useNavigate();


    const {
        user,
        isAuthenticated,
        logout
    } = useAuth();


    const handleLogout = () => {

        logout();

        navigate(
            "/login"
        );
    };


    const getNavClass =
        ({ isActive }) => {

            return isActive
                ? "nav-link active fw-semibold"
                : "nav-link";
        };


    return (

        <nav
            className="
                navbar
                navbar-expand-lg
                bg-white
                border-bottom
                shadow-sm
            "
        >

            <div className="container">


                {/* BRAND */}

                <Link
                    className="
                        navbar-brand
                        fw-bold
                        text-primary
                    "
                    to="/"
                >
                    Medicare
                </Link>


                {/* MOBILE BUTTON */}

                <button
                    className="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#mainNavbar"
                >

                    <span
                        className="navbar-toggler-icon"
                    >
                    </span>

                </button>


                <div
                    className="
                        collapse
                        navbar-collapse
                    "
                    id="mainNavbar"
                >

                    <ul
                        className="
                            navbar-nav
                            ms-auto
                            align-items-lg-center
                        "
                    >


                        {/* -------------------------------- */}
                        {/* LOGGED OUT */}
                        {/* -------------------------------- */}

                        {
                            !isAuthenticated
                            && (

                                <>

                                    <li className="nav-item">

                                        <NavLink
                                            className={
                                                getNavClass
                                            }
                                            to="/login"
                                        >
                                            Login
                                        </NavLink>

                                    </li>


                                    <li className="nav-item">

                                        <Link
                                            className="
                                                btn
                                                btn-primary
                                                ms-lg-2
                                            "
                                            to="/register"
                                        >
                                            Register
                                        </Link>

                                    </li>

                                </>

                            )
                        }


                        {/* -------------------------------- */}
                        {/* PATIENT */}
                        {/* -------------------------------- */}

                        {
                            isAuthenticated
                            &&
                            user?.role ===
                                "PATIENT"
                            && (

                                <>

                                    <li className="nav-item">

                                        <NavLink
                                            className={
                                                getNavClass
                                            }
                                            to="/patient/dashboard"
                                        >
                                            Dashboard
                                        </NavLink>

                                    </li>


                                    <li className="nav-item">

                                        <NavLink
                                            className={
                                                getNavClass
                                            }
                                            to="/patient/doctors"
                                        >
                                            Doctors
                                        </NavLink>

                                    </li>


                                    <li className="nav-item">

                                        <NavLink
                                            className={
                                                getNavClass
                                            }
                                            to="/patient/appointments"
                                        >
                                            My Appointments
                                        </NavLink>

                                    </li>


                                    <li className="nav-item">

                                        <NavLink
                                            className={
                                                getNavClass
                                            }
                                            to="/patient/profile"
                                        >
                                            My Profile
                                        </NavLink>

                                    </li>

                                </>

                            )
                        }


                        {/* -------------------------------- */}
                        {/* DOCTOR */}
                        {/* -------------------------------- */}

                        {
                            isAuthenticated
                            &&
                            user?.role ===
                                "DOCTOR"
                            && (

                                <>

                                    <li className="nav-item">

                                        <NavLink
                                            className={
                                                getNavClass
                                            }
                                            to="/doctor/dashboard"
                                        >
                                            Dashboard
                                        </NavLink>

                                    </li>


                                    <li className="nav-item">

                                        <NavLink
                                            className={
                                                getNavClass
                                            }
                                            to="/doctor/schedule"
                                        >
                                            My Schedule
                                        </NavLink>

                                    </li>


                                    <li className="nav-item">

                                        <NavLink
                                            className={
                                                getNavClass
                                            }
                                            to="/doctor/profile"
                                        >
                                            My Profile
                                        </NavLink>

                                    </li>

                                </>

                            )
                        }


                        {/* -------------------------------- */}
                        {/* LOGOUT */}
                        {/* -------------------------------- */}

                        {
                            isAuthenticated
                            && (

                                <li className="nav-item ms-lg-3">

                                    <button
                                        type="button"
                                        className="
                                            btn
                                            btn-outline-danger
                                        "
                                        onClick={
                                            handleLogout
                                        }
                                    >
                                        Logout
                                    </button>

                                </li>

                            )
                        }


                    </ul>

                </div>

            </div>

        </nav>
    );
}


export default Navbar;