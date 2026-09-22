import { Link } from "react-router";

function Unauthorized() {

    return (
        <div className="container py-5 text-center">

            <h1 className="display-5">
                403
            </h1>

            <h3>
                Access Denied
            </h3>

            <p className="text-secondary">
                You do not have permission
                to access this page.
            </p>

            <Link
                to="/"
                className="btn btn-primary"
            >
                Go Home
            </Link>

        </div>
    );
}

export default Unauthorized;