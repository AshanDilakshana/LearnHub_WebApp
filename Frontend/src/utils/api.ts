import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api'
});


export const createLearningPlan = (plan: any) => api.post('/learning-plans', plan);
export const getLearningPlans = () => api.get('/learning-plans');
export const updateLearningPlan = (id: string, plan: any) => api.put(`/learning-plans/${id}`, plan);
export const deleteLearningPlan = (id: string) => api.delete(`/learning-plans/${id}`);