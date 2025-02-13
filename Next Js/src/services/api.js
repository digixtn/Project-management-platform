// services/api.js
import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api/v1/', // Set your base URL here
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor to add the Authorization header
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const apiService = async (endpoint, data, method = 'POST') => {
  try {
    const response = await apiClient({
      method,
      url: endpoint,
      data,
    });
    return response.data;
  } catch (error) {
    // Handle errors globally
    console.error('API request failed:', error);
    throw error;
  }
};