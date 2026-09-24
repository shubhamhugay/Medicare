import {
    createBrowserRouter
} from "react-router";

import Home from "../pages/common/Home";

import Login from "../pages/auth/Login";

import Register from "../pages/auth/Register";

import PatientDashboard from "../pages/patient/PatientDashboard";

import DoctorDashboard from "../pages/doctor/DoctorDashboard";

import Unauthorized from "../pages/common/Unauthorized";

import NotFound from "../pages/common/NotFound";

import ProtectedRoute from "./ProtectedRoute";


const router =
    createBrowserRouter([

        {
            path: "/",

            element: <Home />
        },


        {
            path: "/login",

            element: <Login />
        },


        {
            path: "/register",

            element: <Register />
        },


        {
            path:
                "/patient/dashboard",

            element: (

                <ProtectedRoute
                    allowedRoles={[
                        "PATIENT"
                    ]}
                >

                    <PatientDashboard />

                </ProtectedRoute>
            )
        },


        {
            path:
                "/doctor/dashboard",

            element: (

                <ProtectedRoute
                    allowedRoles={[
                        "DOCTOR"
                    ]}
                >

                    <DoctorDashboard />

                </ProtectedRoute>
            )
        },


        {
            path: "/unauthorized",

            element:
                <Unauthorized />
        },


        {
            path: "*",

            element:
                <NotFound />
        }

    ]);


export default router;