import { createBrowserRouter } from "react-router";

import Login from "../pages/auth/Login";
import Register from "../pages/auth/Register";
import Home from "../pages/common/Home";

import DoctorDashboard from "../pages/doctor/DoctorDashboard";
import PatientDashboard from "../pages/patient/PatientDashboard";

import NotFound from "../pages/common/NotFound";
import Unauthorized from "../pages/common/Unauthorized";

const router = createBrowserRouter([

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
        path: "/patient/dashboard",
        element: <PatientDashboard />
    },

    {
        path: "/doctor/dashboard",
        element: <DoctorDashboard />
    },

    {
        path: "/unauthorized",
        element: <Unauthorized />
    },

    {
        path: "*",
        element: <NotFound />
    }

]);

export default router;  