import api, {
    getAuthConfig
} from "../api/api";


const createPrescription =
    async (prescriptionData) => {

        const response =
            await api.post(
                "/prescriptions",
                prescriptionData,
                getAuthConfig()
            );

        return response.data;
    };


const getPrescriptionByAppointment =
    async (appointmentId) => {

        const response =
            await api.get(
                `/prescriptions/appointment/${appointmentId}`,
                getAuthConfig()
            );

        return response.data;
    };


const prescriptionService = {

    createPrescription,
    getPrescriptionByAppointment
};


export default prescriptionService;