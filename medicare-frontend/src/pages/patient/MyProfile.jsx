import {
    useEffect,
    useState
} from "react";

import userService from "../../services/userService";


function MyProfile() {

    const [user, setUser] =
        useState(null);

    const [loading, setLoading] =
        useState(true);

    const [error, setError] =
        useState("");


    useEffect(() => {
 const loadProfile = async () => {

        try {

            setLoading(true);
            setError("");

            const data =
                await userService
                    .getCurrentUser();

            setUser(data);

        } catch (error) {

            setError(
                error.response
                    ?.data
                    ?.message
                ||
                "Unable to load profile"
            );

        } finally {

            setLoading(false);
        }
    };
        loadProfile();

    }, []);


   


    if (loading) {

        return (

            <div className="container py-5 text-center">

                <div
                    className="spinner-border text-primary"
                >
                </div>

                <p className="mt-3 text-secondary">
                    Loading profile...
                </p>

            </div>
        );
    }


    if (error) {

        return (

            <div className="container py-5">

                <div className="alert alert-danger">
                    {error}
                </div>

            </div>
        );
    }


    return (

        <div className="container py-5">

            <div className="row justify-content-center">

                <div className="col-md-8 col-lg-6">

                    <div className="card shadow-sm">

                        <div className="card-body p-4">

                            <h3 className="mb-4">
                                My Profile
                            </h3>


                            <div className="mb-3">

                                <small className="text-secondary">
                                    Name
                                </small>

                                <p className="fw-semibold mb-0">
                                    {user?.name}
                                </p>

                            </div>


                            <hr />


                            <div className="mb-3">

                                <small className="text-secondary">
                                    Email
                                </small>

                                <p className="fw-semibold mb-0">
                                    {user?.email}
                                </p>

                            </div>


                            <hr />


                            <div className="mb-3">

                                <small className="text-secondary">
                                    Phone
                                </small>

                                <p className="fw-semibold mb-0">
                                    {user?.phone}
                                </p>

                            </div>


                            <hr />


                            <div>

                                <small className="text-secondary">
                                    Account Type
                                </small>

                                <p className="mb-0">

                                    <span className="badge text-bg-primary">

                                        {user?.role}

                                    </span>

                                </p>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}


export default MyProfile;