import axios from 'axios';

const API_URL = 'http://localhost:8080/upload';

export const uploadImage = async (imageFile) => {
    const response = await axios.post(`${API_URL}`, imageFile, {
        headers: {
            "Content-Type": "multipart/form-data"
        },
  withCredentials: true
    });
    return response.data;
}