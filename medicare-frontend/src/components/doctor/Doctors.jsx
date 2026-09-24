import {
    useEffect,
    useState
} from "react";

import doctorService from "../../services/doctorService";

import DoctorCard from "../../components/doctor/DoctorCard";

import DoctorFilter from "../../components/doctor/DoctorFilter";


const MAX_FEE =
    "5000";


function Doctors() {

    const [doctors, setDoctors] =
        useState([]);


    const [specialization, setSpecialization] =
        useState("");


    const [maxFee, setMaxFee] =
        useState(MAX_FEE);


    const [name, setName] =
        useState("");


    const [sortBy, setSortBy] =
        useState(
            "consultationFee"
        );


    const [direction, setDirection] =
        useState("asc");


    const [page, setPage] =
        useState(0);


    const [totalPages, setTotalPages] =
        useState(0);


    const [totalElements, setTotalElements] =
        useState(0);


    const [loading, setLoading] =
        useState(false);


    const [error, setError] =
        useState("");


    /*
     * Reset pagination whenever a
     * filter or sorting option changes.
     */
    useEffect(() => {

        // eslint-disable-next-line react-hooks/set-state-in-effect
        setPage(0);

    }, [
        specialization,
        maxFee,
        name,
        sortBy,
        direction
    ]);


    /*
     * Load doctors.
     *
     * Small timeout gives us a simple
     * live-search debounce for the
     * doctor name input.
     */

    let loadDoctors =
        async () => {

            try {

                setLoading(true);
                setError("");


                const data =
                    await doctorService
                        .getDoctors({

                            specialization,

                            maxFee:
                                maxFee === MAX_FEE
                                    ? ""
                                    : maxFee,

                            name,

                            page,

                            size: 6,

                            sortBy,

                            direction
                        });


                setDoctors(
                    data.doctors
                );


                setTotalPages(
                    data.totalPages
                );


                setTotalElements(
                    data.totalElements
                );

            } catch (error) {

                setError(
                    error.response
                        ?.data
                        ?.message
                    ||
                    "Unable to load doctors"
                );

            } finally {

                setLoading(false);
            }
        };
    useEffect(() => {

        const timer =
            setTimeout(() => {

                loadDoctors();

            }, 400);


        return () => {

            clearTimeout(timer);
        };

    }, [
        specialization,
        maxFee,
        name,
        sortBy,
        direction,
        page
    ]);


    


    const resetFilters = () => {

        setSpecialization("");

        setMaxFee(
            MAX_FEE
        );

        setName("");

        setSortBy(
            "consultationFee"
        );

        setDirection(
            "asc"
        );

        setPage(0);
    };


    const previousPage = () => {

        if (page > 0) {

            setPage(
                page - 1
            );
        }
    };


    const nextPage = () => {

        if (
            page <
            totalPages - 1
        ) {

            setPage(
                page + 1
            );
        }
    };


    return (

        <div className="container py-5">


            {/* PAGE HEADING */}

            <div className="mb-4">

                <h2>
                    Find a Doctor
                </h2>

                <p className="text-secondary">
                    Search and filter doctors
                    based on your healthcare needs.
                </p>

            </div>


            {/* FILTERS */}

            <DoctorFilter
                specialization={
                    specialization
                }
                setSpecialization={
                    setSpecialization
                }

                maxFee={
                    maxFee
                }
                setMaxFee={
                    setMaxFee
                }

                name={
                    name
                }
                setName={
                    setName
                }

                sortBy={
                    sortBy
                }
                setSortBy={
                    setSortBy
                }

                direction={
                    direction
                }
                setDirection={
                    setDirection
                }

                resetFilters={
                    resetFilters
                }
            />


            {/* RESULT COUNT */}

            {!loading && !error && (

                <p className="text-secondary">

                    {
                        totalElements
                    } doctor(s) found

                </p>

            )}


            {/* ERROR */}

            {error && (

                <div className="alert alert-danger">

                    {error}

                </div>

            )}


            {/* LOADING */}

            {loading && (

                <div className="text-center py-5">

                    <div
                        className="
                            spinner-border
                            text-primary
                        "
                    >
                    </div>

                    <p className="text-secondary mt-3">
                        Loading doctors...
                    </p>

                </div>

            )}


            {/* NO RESULT */}

            {
                !loading
                &&
                !error
                &&
                doctors.length === 0
                && (

                    <div className="alert alert-info">

                        No doctors found
                        matching your filters.

                    </div>

                )
            }


            {/* DOCTOR CARDS */}

            {
                !loading
                &&
                !error
                &&
                doctors.length > 0
                && (

                    <>

                        <div className="row g-4">

                            {
                                doctors.map(
                                    (doctor) => (

                                        <div
                                            className="
                                                col-md-6
                                                col-lg-4
                                            "
                                            key={doctor.id}
                                        >

                                            <DoctorCard
                                                doctor={
                                                    doctor
                                                }
                                            />

                                        </div>

                                    )
                                )
                            }

                        </div>


                        {/* PAGINATION */}

                        <div
                            className="
                                d-flex
                                justify-content-center
                                align-items-center
                                gap-3
                                mt-5
                            "
                        >

                            <button
                                className="btn btn-outline-primary"
                                onClick={previousPage}
                                disabled={
                                    page === 0
                                }
                            >
                                Previous
                            </button>


                            <span>

                                Page{" "}

                                <strong>
                                    {
                                        page + 1
                                    }
                                </strong>

                                {" "}of{" "}

                                <strong>
                                    {
                                        totalPages
                                    }
                                </strong>

                            </span>


                            <button
                                className="btn btn-outline-primary"
                                onClick={nextPage}
                                disabled={
                                    page
                                    >=
                                    totalPages - 1
                                }
                            >
                                Next
                            </button>

                        </div>

                    </>

                )
            }

        </div>
    );
}


export default Doctors;