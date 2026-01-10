import axios from "axios";

const API_URL = "http://localhost:8080/api/pawns";

export const createPawn = async (pawnData) => {
  try {
    const response = await axios.post(
      API_URL,
      pawnData,
      {
        headers: {
          "Content-Type": "application/json",
        },
        withCredentials: true,
      }
    );
    return response;
  } catch (error) {
    console.error("Error creating pawn:", error);
  }
};

export const getAllPawns = async (filters = {}) => {
  const response = await axios.get(API_URL, {
    params: {
      name: filters.name,
      level: filters.level,
      platform: filters.platform,
      gender: filters.gender,
      page: filters.page || 0,
      size: filters.size || 20,
    },
    withCredentials: false,
  });
  return response.data;
};

export const getPawnById = async (pawnId) => {
  const response = await axios.get(`${API_URL}/by-pawn-id/${pawnId}`, {
    withCredentials: false,
  });
  return response.data;
};
