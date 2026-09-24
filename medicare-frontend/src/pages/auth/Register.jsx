import {
    useState
} from "react";

import {
    Link,
    useNavigate
} from "react-router";

import authService from "../../services/authService";


function Register() {

    const navigate =
        useNavigate();


    const [formData, setFormData] =
        useState({

            name: "",
            email: "",
            phone: "",
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

                await authService.register(
                    formData
                );

                navigate(
                    "/login",
                    {
                        state: {
                            message:
                                "Registration successful. Please login."
                        }
                    }
                );

            } catch (error) {

                setError(
                    error.response
                        ?.data
                        ?.message
                    ||
                    "Registration failed"
                );

            } finally {

                setLoading(false);
            }
        };


    return (

        <div className="container py-5">

            <div className="row justify-content-center">

                <div className="col-md-7 col-lg-6">

                    <div className="card shadow-sm">

                        <div className="card-body p-4">

                            <h2 className="text-center mb-4">
                                Create Account
                            </h2>


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
                                        Name
                                    </label>

                                    <input
                                        type="text"
                                        name="name"
                                        className="form-control"
                                        value={formData.name}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>


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
                                        Phone Number
                                    </label>

                                    <input
                                        type="tel"
                                        name="phone"
                                        className="form-control"
                                        value={formData.phone}
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
                                            ? "Creating Account..."
                                            : "Register"
                                    }

                                </button>

                            </form>


                            <p className="text-center mt-4 mb-0">

                                Already have an account?{" "}

                                <Link to="/login">
                                    Login
                                </Link>

                            </p>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}


export default Register;