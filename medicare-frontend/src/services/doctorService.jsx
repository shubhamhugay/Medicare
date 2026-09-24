import api, {
    getAuthConfig
} from "../api/api";


const getDoctors = async ({
    specialization = "",
    maxFee = "",
    name = "",
    page = 0,
    size = 6,
    sortBy = "consultationFee",
    direction = "asc"
}) => {

    const params = {
        page,
        size,
        sortBy,
        direction
    };


    if (specialization) {

        params.specialization =
            specialization;
    }


    if (
        maxFee !== ""
        &&
        Number(maxFee) > 0
    ) {

        params.maxFee =
            maxFee;
    }


    if (name.trim()) {

        params.name =
            name.trim();
    }


    const response =
        await api.get(
            "/doctors",
            {
                ...getAuthConfig(),
                params
            }
        );


    return response.data;
};


const getDoctorById =
    async (doctorId) => {

        const response =
            await api.get(
                `/doctors/${doctorId}`,
                getAuthConfig()
            );

        return response.data;
    };


const doctorService = {
    getDoctors,
    getDoctorById
};


export default doctorService;