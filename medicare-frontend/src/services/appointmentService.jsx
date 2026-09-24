import api, {
    getAuthConfig
} from "../api/api";


const createAppointment =
    async (appointmentData) => {

        const response =
            await api.post(
                "/appointments",
                appointmentData,
                getAuthConfig()
            );

        return response.data;
    };


const getMyAppointments =
    async () => {

        const response =
            await api.get(
                "/appointments/my",
                getAuthConfig()
            );

        return response.data;
    };


const cancelAppointment =
    async (appointmentId) => {

        const response =
            await api.patch(
                `/appointments/${appointmentId}/cancel`,
                {},
                getAuthConfig()
            );

        return response.data;
    };


const appointmentService = {

    createAppointment,
    getMyAppointments,
    cancelAppointment
};


export default appointmentService;