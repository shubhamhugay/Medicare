function DoctorFilter({
    specialization,
    setSpecialization,
    maxFee,
    setMaxFee,
    name,
    setName,
    sortBy,
    setSortBy,
    direction,
    setDirection,
    resetFilters
}) {

    return (

        <div className="card shadow-sm mb-4">

            <div className="card-body">

                <div className="row g-3">


                    {/* DOCTOR NAME */}

                    <div className="col-md-6 col-lg-4">

                        <label className="form-label">
                            Doctor Name
                        </label>

                        <input
                            type="text"
                            className="form-control"
                            placeholder="Search doctor..."
                            value={name}
                            onChange={
                                (event) =>
                                    setName(
                                        event.target.value
                                    )
                            }
                        />

                    </div>


                    {/* SPECIALIZATION */}

                    <div className="col-md-6 col-lg-4">

                        <label className="form-label">
                            Specialization
                        </label>

                        <select
                            className="form-select"
                            value={specialization}
                            onChange={
                                (event) =>
                                    setSpecialization(
                                        event.target.value
                                    )
                            }
                        >

                            <option value="">
                                All Specializations
                            </option>

                            <option value="Orthopedic">
                                Orthopedic
                            </option>

                            <option value="Dentist">
                                Dentist
                            </option>

                            <option value="General Physician">
                                General Physician
                            </option>

                            <option value="Cardiologist">
                                Cardiologist
                            </option>

                        </select>

                    </div>


                    {/* MAX FEE */}

                    <div className="col-md-6 col-lg-4">

                        <label className="form-label">

                            Maximum Fee:{" "}

                            {
                                maxFee === "5000"
                                    ? "Any"
                                    : maxFee
                            }

                        </label>

                        <input
                            type="range"
                            className="form-range"
                            min="500"
                            max="5000"
                            step="100"
                            value={maxFee}
                            onChange={
                                (event) =>
                                    setMaxFee(
                                        event.target.value
                                    )
                            }
                        />

                        <div className="d-flex justify-content-between">

                            <small className="text-secondary">
                                500
                            </small>

                            <small className="text-secondary">
                                5000+
                            </small>

                        </div>

                    </div>


                    {/* SORT */}

                    <div className="col-md-6 col-lg-4">

                        <label className="form-label">
                            Sort By
                        </label>

                        <select
                            className="form-select"
                            value={sortBy}
                            onChange={
                                (event) =>
                                    setSortBy(
                                        event.target.value
                                    )
                            }
                        >

                            <option value="consultationFee">
                                Consultation Fee
                            </option>

                            <option value="name">
                                Doctor Name
                            </option>

                            <option value="experienceYears">
                                Experience
                            </option>

                            <option value="specialization">
                                Specialization
                            </option>

                        </select>

                    </div>


                    {/* DIRECTION */}

                    <div className="col-md-6 col-lg-4">

                        <label className="form-label">
                            Direction
                        </label>

                        <select
                            className="form-select"
                            value={direction}
                            onChange={
                                (event) =>
                                    setDirection(
                                        event.target.value
                                    )
                            }
                        >

                            <option value="asc">
                                Ascending
                            </option>

                            <option value="desc">
                                Descending
                            </option>

                        </select>

                    </div>


                    {/* RESET */}

                    <div
                        className="
                            col-md-6
                            col-lg-4
                            d-flex
                            align-items-end
                        "
                    >

                        <button
                            type="button"
                            className="btn btn-outline-secondary w-100"
                            onClick={resetFilters}
                        >
                            Reset Filters
                        </button>

                    </div>

                </div>

            </div>

        </div>
    );
}


export default DoctorFilter;