import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth/';

export const login = async (email, password) => {
    const response = await axios.post(`${API_URL}authenticate`, { email, password }, { withCredentials: true });
    return response.data;
}

export const register = async(email, username, password) => {
    const response = await axios.post(`${API_URL}register`, { email, username, password }, {withCredentials: true});
    return response.data;
}

export const getCurrentUser = async() => {
    const response = await axios.get(`http://localhost:8080/api/users/me`, {withCredentials: true});
    return response.data;
}

export const logout = async () => {
    await axios.post(`${API_URL}logout`, {}, { withCredentials: true });
}

export const forgotPassword = async (email) => {
    const response = await axios.post(`${API_URL}forgot`, { email }, { withCredentials: true });
    return response.data;
}

export const resetPassword = async (token, newPassword) => {
    const response = await axios.post(`${API_URL}reset`, { token, newPassword }, { withCredentials: true });
    return response.data;
}