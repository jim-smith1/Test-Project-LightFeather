import axios from "axios";

const BASE_URL = "http://localhost:8080/api";

export const fetchSupervisors = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/supervisors`);
    return response.data;
  } catch (error) {
    console.error("Error fetching supervisors:", error);
    throw error;
  }
};

export const submitFormData = async (formData) => {
  try {
    const response = await axios.post(`${BASE_URL}/submit`, formData);
    return response.data;
  } catch (error) {
    console.error("Error submitting form:", error);
    throw error;
  }
};
