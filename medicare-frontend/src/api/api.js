import axios from "axios";

const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    headers: {
        "Content-Type": "application/json"
    }
});


// Used for APIs that require JWT
export const getAuthConfig = () => {

    const token =
        localStorage.getItem("token");

    if (!token) {
        return {};
    }

    return {
        headers: {
            Authorization: `Bearer ${token}`
        }
    };
};


export default api;