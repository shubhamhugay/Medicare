import { Link } from "react-router";

function Home() {

    return (
        <div className="container py-5">

            <div className="text-center py-5">

                <h1 className="display-5 fw-bold">
                    Medicare Healthcare
                </h1>

                <p className="lead text-secondary mt-3">
                    Book doctor appointments and manage
                    your healthcare easily.
                </p>

                <div className="d-flex justify-content-center gap-3 mt-4">

                    <Link
                        to="/login"
                        className="btn btn-primary"
                    >
                        Login
                    </Link>

                    <Link
                        to="/register"
                        className="btn btn-outline-primary"
                    >
                        Register
                    </Link>

                </div>

            </div>

        </div>
    );
}

export default Home;