import api, {
    getAuthConfig
} from "../api/api";


const createOrder =
    async (appointmentId) => {

        const response =
            await api.post(
                `/payments/${appointmentId}/order`,
                {},
                getAuthConfig()
            );

        return response.data;
    };


const verifyPayment =
    async (
        appointmentId,
        paymentData
    ) => {

        const response =
            await api.post(
                `/payments/${appointmentId}/verify`,
                paymentData,
                getAuthConfig()
            );

        return response.data;
    };


const paymentService = {

    createOrder,
    verifyPayment
};


export default paymentService;