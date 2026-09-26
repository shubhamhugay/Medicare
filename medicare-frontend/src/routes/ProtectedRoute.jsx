import {
    Navigate
} from "react-router";

import {
    useAuth
} from "../context/AuthContext";


function ProtectedRoute({
    children,
    allowedRoles = []
}) {

    const {
        user,
        isAuthenticated
    } = useAuth();


    // User is not logged in

    if (!isAuthenticated) {

        return (
            <Navigate
                to="/login"
                replace
            />
        );
    }


    // User is logged in,
    // but doesn't have required role

    if (
        allowedRoles.length > 0
        &&
        !allowedRoles.includes(
            user?.role
        )
    ) {

        return (
            <Navigate
                to="/unauthorized"
                replace
            />
        );
    }


    return children;
}


export default ProtectedRoute;