import {
    createContext,
    useContext,
    useState
} from "react";


const AuthContext =
    createContext(null);


function getStoredUser() {

    const storedUser =
        localStorage.getItem("user");

    if (!storedUser) {
        return null;
    }

    try {

        return JSON.parse(storedUser);

    } catch {

        localStorage.removeItem("user");

        return null;
    }
}


export function AuthProvider({ children }) {

    const [token, setToken] =
        useState(() =>
            localStorage.getItem("token")
        );

    const [user, setUser] =
        useState(getStoredUser);


    const login = (authResponse) => {

        const loggedInUser = {

            userId:
                authResponse.userId,

            name:
                authResponse.name,

            email:
                authResponse.email,

            role:
                authResponse.role
        };


        localStorage.setItem(
            "token",
            authResponse.token
        );

        localStorage.setItem(
            "user",
            JSON.stringify(
                loggedInUser
            )
        );


        setToken(
            authResponse.token
        );

        setUser(
            loggedInUser
        );
    };


    const logout = () => {

        localStorage.removeItem(
            "token"
        );

        localStorage.removeItem(
            "user"
        );

        setToken(null);
        setUser(null);
    };


    const isAuthenticated =
        Boolean(token && user);


    return (

        <AuthContext.Provider
            value={{
                user,
                token,
                isAuthenticated,
                login,
                logout
            }}
        >

            {children}

        </AuthContext.Provider>
    );
}


export function useAuth() {

    return useContext(
        AuthContext
    );
}