import axios from 'axios';

export const axiosConfig = axios.create({
  baseURL: 'http://localhost:8888',
  timeout: 1000,
  headers: {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${document.cookie.split('=')[1]}`,
  },
});
