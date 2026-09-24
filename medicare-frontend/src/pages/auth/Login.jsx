import {
    useState
} from "react";

import {
    Link,
    useLocation,
    useNavigate
} from "react-router";

import authService from "../../services/authService";

import {
    useAuth
} from "../../context/AuthContext";


function Login() {

    const navigate =
        useNavigate();

    const location =
        useLocation();

    const {
        login
    } = useAuth();


    const [formData, setFormData] =
        useState({

            email: "",
            password: ""
        });


    const [error, setError] =
        useState("");

    const [loading, setLoading] =
        useState(false);


    const handleChange = (event) => {

        const {
            name,
            value
        } = event.target;

        setFormData(previous => ({
            ...previous,
            [name]: value
        }));
    };


    const handleSubmit =
        async (event) => {

            event.preventDefault();

            setError("");
            setLoading(true);

            try {

                const response =
                    await authService.login(
                        formData
                    );

                login(response);


                if (
                    response.role ===
                    "PATIENT"
                ) {

                    navigate(
                        "/patient/dashboard"
                    );

                } else if (
                    response.role ===
                    "DOCTOR"
                ) {

                    navigate(
                        "/doctor/dashboard"
                    );

                } else {

                    navigate(
                        "/unauthorized"
                    );
                }

            } catch (error) {

                setError(
                    error.response
                        ?.data
                        ?.message
                    ||
                    "Invalid email or password"
                );

            } finally {

                setLoading(false);
            }
        };


    return (

        <div className="container py-5">

            <div className="row justify-content-center">

                <div className="col-md-6 col-lg-5">

                    <div className="card shadow-sm">

                        <div className="card-body p-4">

                            <h2 className="text-center mb-4">
                                Login
                            </h2>


                            {
                                location.state
                                    ?.message
                                && (

                                <div
                                    className="alert alert-success"
                                >
                                    {
                                        location
                                            .state
                                            .message
                                    }
                                </div>

                            )}


                            {error && (

                                <div
                                    className="alert alert-danger"
                                >
                                    {error}
                                </div>

                            )}


                            <form
                                onSubmit={handleSubmit}
                            >

                                <div className="mb-3">

                                    <label
                                        className="form-label"
                                    >
                                        Email
                                    </label>

                                    <input
                                        type="email"
                                        name="email"
                                        className="form-control"
                                        value={formData.email}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>


                                <div className="mb-3">

                                    <label
                                        className="form-label"
                                    >
                                        Password
                                    </label>

                                    <input
                                        type="password"
                                        name="password"
                                        className="form-control"
                                        value={formData.password}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>


                                <button
                                    type="submit"
                                    className="btn btn-primary w-100"
                                    disabled={loading}
                                >

                                    {
                                        loading
                                            ? "Logging in..."
                                            : "Login"
                                    }

                                </button>

                            </form>


                            <p className="text-center mt-4 mb-0">

                                Don't have an account?{" "}

                                <Link to="/register">
                                    Register
                                </Link>

                            </p>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}


export default Login;