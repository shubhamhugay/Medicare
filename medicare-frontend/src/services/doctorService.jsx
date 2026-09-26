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


const createMyProfile =
    async (profileData) => {

        const response =
            await api.post(
                "/doctors/profile",
                profileData,
                getAuthConfig()
            );

        return response.data;
    };


const getMyProfile =
    async () => {

        const response =
            await api.get(
                "/doctors/profile/me",
                getAuthConfig()
            );

        return response.data;
    };


const updateMyProfile =
    async (profileData) => {

        const response =
            await api.put(
                "/doctors/profile/me",
                profileData,
                getAuthConfig()
            );

        return response.data;
    };


const deleteMyProfile =
    async () => {

        await api.delete(
            "/doctors/profile/me",
            getAuthConfig()
        );
    };


const doctorService = {

    getDoctors,
    getDoctorById,

    createMyProfile,
    getMyProfile,
    updateMyProfile,
    deleteMyProfile
};


export default doctorService;