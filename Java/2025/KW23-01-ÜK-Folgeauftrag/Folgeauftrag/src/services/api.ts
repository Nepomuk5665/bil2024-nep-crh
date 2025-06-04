import axios from 'axios';
import type { ChuckJoke } from '../types/joke';

const BASE_URL = 'https://api.chucknorris.io/jokes';

export const getRandomJoke = async (): Promise<ChuckJoke> => {
  try {
    const response = await axios.get<ChuckJoke>(`${BASE_URL}/random`);
    return response.data;
  } catch (error) {
    console.error('Error fetching random joke:', error);
    throw error;
  }
};

export const getJokeByCategory = async (category: string): Promise<ChuckJoke> => {
  try {
    const response = await axios.get<ChuckJoke>(`${BASE_URL}/random`, {
      params: { category }
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching joke by category:', error);
    throw error;
  }
};

export const getCategories = async (): Promise<string[]> => {
  try {
    const response = await axios.get<string[]>(`${BASE_URL}/categories`);
    return response.data;
  } catch (error) {
    console.error('Error fetching categories:', error);
    throw error;
  }
};