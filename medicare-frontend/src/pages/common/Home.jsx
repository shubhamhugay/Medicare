import {
    Link
} from "react-router";

import {
    useAuth
} from "../../context/AuthContext";


function Home() {

    const {
        user,
        isAuthenticated
    } = useAuth();


    const getDashboardPath = () => {

        if (
            user?.role === "PATIENT"
        ) {

            return "/patient/dashboard";
        }


        if (
            user?.role === "DOCTOR"
        ) {

            return "/doctor/dashboard";
        }


        return "/";
    };


    return (

        <div className="container py-5">


            <div
                className="
                    row
                    justify-content-center
                    text-center
                    py-5
                "
            >

                <div className="col-lg-8">


                    <h1
                        className="
                            display-5
                            fw-bold
                            text-primary
                            mb-3
                        "
                    >
                        Medicare Healthcare
                    </h1>


                    <p
                        className="
                            lead
                            text-secondary
                            mb-4
                        "
                    >
                        Find doctors,
                        manage appointments
                        and access your
                        prescriptions from
                        one place.
                    </p>


                    {
                        isAuthenticated

                            ? (

                                <>

                                    <p className="mb-3">

                                        Welcome back,{" "}

                                        <strong>
                                            {user?.name}
                                        </strong>

                                    </p>


                                    <Link
                                        to={
                                            getDashboardPath()
                                        }
                                        className="
                                            btn
                                            btn-primary
                                            btn-lg
                                        "
                                    >
                                        Go to Dashboard
                                    </Link>

                                </>

                            )

                            : (

                                <div
                                    className="
                                        d-flex
                                        justify-content-center
                                        gap-3
                                    "
                                >

                                    <Link
                                        to="/login"
                                        className="
                                            btn
                                            btn-primary
                                            btn-lg
                                        "
                                    >
                                        Login
                                    </Link>


                                    <Link
                                        to="/register"
                                        className="
                                            btn
                                            btn-outline-primary
                                            btn-lg
                                        "
                                    >
                                        Register
                                    </Link>

                                </div>

                            )
                    }


                </div>

            </div>

        </div>
    );
}


export default Home;