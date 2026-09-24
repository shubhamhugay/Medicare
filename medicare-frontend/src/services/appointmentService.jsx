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


const appointmentService = {
    createAppointment
};


export default appointmentService;