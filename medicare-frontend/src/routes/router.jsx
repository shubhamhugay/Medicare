import {
    createBrowserRouter
} from "react-router";

import MainLayout from "../components/layout/Layout";

import Home from "../pages/common/Home";

import Login from "../pages/auth/Login";

import Register from "../pages/auth/Register";

import PatientDashboard from "../pages/patient/PatientDashboard";

import MyProfile from "../pages/patient/MyProfile";

import DoctorDashboard from "../pages/doctor/DoctorDashboard";

import Unauthorized from "../pages/common/Unauthorized";

import NotFound from "../pages/common/NotFound";

import Doctors from "../components/doctor/Doctors";
import BookAppointment from "../pages/patient/BookAppointment";
import ProtectedRoute from "./ProtectedRoute";


const router =
    createBrowserRouter([

        {
            path: "/",

            element:
                <MainLayout />,

            children: [

                {
                    index: true,

                    element:
                        <Home />
                },


                {
                    path: "login",

                    element:
                        <Login />
                },


                {
                    path: "register",

                    element:
                        <Register />
                },


                {
                    path:
                        "patient/dashboard",

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
                        "patient/doctors",

                    element: (

                        <ProtectedRoute
                            allowedRoles={[
                                "PATIENT"
                            ]}
                        >

                            <Doctors />

                        </ProtectedRoute>
                    )
                }, {
                    path:
                        "patient/doctors/:doctorId/book",

                    element: (

                        <ProtectedRoute
                            allowedRoles={[
                                "PATIENT"
                            ]}
                        >

                            <BookAppointment />

                        </ProtectedRoute>
                    )
                },
                {
                    path:
                        "patient/profile",

                    element: (

                        <ProtectedRoute
                            allowedRoles={[
                                "PATIENT"
                            ]}
                        >

                            <MyProfile />

                        </ProtectedRoute>
                    )
                },


                {
                    path:
                        "doctor/dashboard",

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
                    path:
                        "unauthorized",

                    element:
                        <Unauthorized />
                },


                {
                    path: "*",

                    element:
                        <NotFound />
                }

            ]
        }

    ]);


export default router;