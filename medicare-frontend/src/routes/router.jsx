import {
    createBrowserRouter
} from "react-router";

import MainLayout from "../components/layout/MainLayout";

import ProtectedRoute from "./ProtectedRoute";


// COMMON PAGES

import Home from "../pages/common/Home";

import Unauthorized from "../pages/common/Unauthorized";

import NotFound from "../pages/common/NotFound";


// AUTH PAGES

import Login from "../pages/auth/Login";

import Register from "../pages/auth/Register";


// PATIENT PAGES

import PatientDashboard from "../pages/patient/PatientDashboard";

import Doctors from "../pages/patient/Doctors";

import BookAppointment from "../pages/patient/BookAppointment";

import MyAppointments from "../pages/patient/MyAppointments";

import MyProfile from "../pages/patient/MyProfile";


// DOCTOR PAGES

import DoctorDashboard from "../pages/doctor/DoctorDashboard";

import DoctorSchedule from "../pages/doctor/DoctorSchedule";

import DoctorProfile from "../pages/doctor/DoctorProfile";

import DoctorPrescription from "../pages/doctor/DoctorPrescription";


const router =
    createBrowserRouter([

        {
            path: "/",

            element:
                <MainLayout />,

            children: [


                // -----------------------------------
                // PUBLIC ROUTES
                // -----------------------------------

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


                // -----------------------------------
                // PATIENT ROUTES
                // -----------------------------------

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
                },


                {
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
                        "patient/appointments",

                    element: (

                        <ProtectedRoute
                            allowedRoles={[
                                "PATIENT"
                            ]}
                        >

                            <MyAppointments />

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


                // -----------------------------------
                // DOCTOR ROUTES
                // -----------------------------------

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
                        "doctor/schedule",

                    element: (

                        <ProtectedRoute
                            allowedRoles={[
                                "DOCTOR"
                            ]}
                        >

                            <DoctorSchedule />

                        </ProtectedRoute>
                    )
                },


                {
                    path:
                        "doctor/profile",

                    element: (

                        <ProtectedRoute
                            allowedRoles={[
                                "DOCTOR"
                            ]}
                        >

                            <DoctorProfile />

                        </ProtectedRoute>
                    )
                },


                {
                    path:
                        "doctor/appointments/:appointmentId/prescription",

                    element: (

                        <ProtectedRoute
                            allowedRoles={[
                                "DOCTOR"
                            ]}
                        >

                            <DoctorPrescription />

                        </ProtectedRoute>
                    )
                },


                // -----------------------------------
                // COMMON ROUTES
                // -----------------------------------

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