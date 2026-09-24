import api, {
    getAuthConfig
} from "../api/api";


const getCurrentUser = async () => {

    const response = await api.get(
        "/users/me",
        getAuthConfig()
    );

    return response.data;
};


const userService = {
    getCurrentUser
};


export default userService;