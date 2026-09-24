import api from "../api/api";

const register = async (registerData) => {

    const response = await api.post(
        "/auth/register",
        registerData
    );

    return response.data;
};


const login = async (loginData) => {

    const response = await api.post(
        "/auth/login",
        loginData
    );

    return response.data;
};


const authService = {
    register,
    login
};

export default authService;