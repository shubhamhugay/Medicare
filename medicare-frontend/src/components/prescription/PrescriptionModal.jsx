function PrescriptionModal({
    show,
    prescription,
    loading,
    error,
    onClose
}) {

    if (!show) {
        return null;
    }


    return (

        <>

            <div
                className="modal fade show d-block"
                tabIndex="-1"
                role="dialog"
            >

                <div
                    className="
                        modal-dialog
                        modal-lg
                        modal-dialog-centered
                        modal-dialog-scrollable
                    "
                >

                    <div className="modal-content">


                        <div className="modal-header">

                            <h5 className="modal-title">
                                Prescription
                            </h5>

                            <button
                                type="button"
                                className="btn-close"
                                onClick={
                                    onClose
                                }
                            >
                            </button>

                        </div>


                        <div className="modal-body">


                            {
                                loading
                                && (

                                    <div className="text-center py-5">

                                        <div
                                            className="
                                                spinner-border
                                                text-primary
                                            "
                                        >
                                        </div>

                                        <p className="text-secondary mt-3">
                                            Loading prescription...
                                        </p>

                                    </div>

                                )
                            }


                            {
                                !loading
                                &&
                                error
                                && (

                                    <div className="alert alert-danger">

                                        {error}

                                    </div>

                                )
                            }


                            {
                                !loading
                                &&
                                !error
                                &&
                                prescription
                                && (

                                    <>

                                        <div className="mb-4">

                                            <small className="text-secondary">
                                                Doctor
                                            </small>

                                            <h5>
                                                {
                                                    prescription
                                                        .doctorName
                                                }
                                            </h5>

                                        </div>


                                        <div className="mb-4">

                                            <small className="text-secondary">
                                                Diagnosis
                                            </small>

                                            <p className="mb-0">
                                                {
                                                    prescription
                                                        .diagnosis
                                                }
                                            </p>

                                        </div>


                                        <h6>
                                            Medicines
                                        </h6>


                                        <div className="table-responsive">

                                            <table className="table table-bordered">

                                                <thead>

                                                    <tr>

                                                        <th>
                                                            Medicine
                                                        </th>

                                                        <th>
                                                            Dosage
                                                        </th>

                                                        <th>
                                                            Frequency
                                                        </th>

                                                        <th>
                                                            Duration
                                                        </th>

                                                    </tr>

                                                </thead>


                                                <tbody>

                                                    {
                                                        prescription
                                                            .medicines
                                                            .map(
                                                                (
                                                                    medicine,
                                                                    index
                                                                ) => (

                                                                    <tr
                                                                        key={
                                                                            index
                                                                        }
                                                                    >

                                                                        <td>
                                                                            {
                                                                                medicine
                                                                                    .medicineName
                                                                            }
                                                                        </td>

                                                                        <td>
                                                                            {
                                                                                medicine
                                                                                    .dosage
                                                                            }
                                                                        </td>

                                                                        <td>
                                                                            {
                                                                                medicine
                                                                                    .frequency
                                                                            }
                                                                        </td>

                                                                        <td>
                                                                            {
                                                                                medicine
                                                                                    .duration
                                                                            }
                                                                        </td>

                                                                    </tr>

                                                                )
                                                            )
                                                    }

                                                </tbody>

                                            </table>

                                        </div>


                                        {
                                            prescription
                                                .doctorNotes
                                            && (

                                                <div className="mt-4">

                                                    <small className="text-secondary">
                                                        Doctor Notes
                                                    </small>

                                                    <p>
                                                        {
                                                            prescription
                                                                .doctorNotes
                                                        }
                                                    </p>

                                                </div>

                                            )
                                        }

                                    </>

                                )
                            }

                        </div>


                        <div className="modal-footer">

                            <button
                                type="button"
                                className="btn btn-secondary"
                                onClick={
                                    onClose
                                }
                            >
                                Close
                            </button>

                        </div>

                    </div>

                </div>

            </div>


            <div
                className="modal-backdrop fade show"
            >
            </div>

        </>
    );
}


export default PrescriptionModal;