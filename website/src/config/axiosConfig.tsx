import axios from 'axios';

// Create an instance of Axios with base configuration
export const axiosConfig = axios.create({
  baseURL: process.env.VITE_BACKEND_URL ?? 'http://localhost:8888',
  timeout: 1000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add a request interceptor to set the Authorization header dynamically
axiosConfig.interceptors.request.use(
  (config) => {
    // Retrieve the token from the cookie
    const token = localStorage.getItem('token');
    if (token) {
      // Set the Authorization header with the token
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
