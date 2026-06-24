import axios from "axios";

const apiBaseUrl = import.meta.env.VITE_API_BASE_URL;

if (!apiBaseUrl) {
    throw new Error("VITE_API_BASE_URL is not configured");
}

const axiosInstance = axios.create({
    baseURL: apiBaseUrl.replace(/\/$/, ""),
    headers: {
        "Content-Type": "application/json",
    },
});

export default axiosInstance;
