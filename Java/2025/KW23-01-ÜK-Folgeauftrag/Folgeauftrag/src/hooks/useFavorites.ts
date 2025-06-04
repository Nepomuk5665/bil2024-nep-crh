import { useState, useEffect } from 'react';
import type { ChuckJoke, FavoriteJoke } from '../types/joke';

const FAVORITES_KEY = 'chuck-norris-favorites';
const MAX_FAVORITES = 5;

export const useFavorites = () => {
  const [favorites, setFavorites] = useState<FavoriteJoke[]>([]);

  useEffect(() => {
    const storedFavorites = localStorage.getItem(FAVORITES_KEY);
    if (storedFavorites) {
      try {
        const parsed = JSON.parse(storedFavorites);
        const favoritesWithDates = parsed.map((fav: any) => ({
          ...fav,
          addedAt: new Date(fav.addedAt)
        }));
        setFavorites(favoritesWithDates);
      } catch (error) {
        console.error('Error parsing favorites:', error);
      }
    }
  }, []);

  useEffect(() => {
    localStorage.setItem(FAVORITES_KEY, JSON.stringify(favorites));
  }, [favorites]);

  const addFavorite = (joke: ChuckJoke) => {
    if (favorites.some(fav => fav.id === joke.id)) {
      return;
    }

    const newFavorite: FavoriteJoke = {
      id: joke.id,
      value: joke.value,
      addedAt: new Date()
    };

    setFavorites(prevFavorites => {
      const updatedFavorites = [newFavorite, ...prevFavorites].slice(0, MAX_FAVORITES);
      return updatedFavorites;
    });
  };

  const removeFavorite = (id: string) => {
    setFavorites(prevFavorites => prevFavorites.filter(fav => fav.id !== id));
  };

  const isFavorite = (id: string): boolean => {
    return favorites.some(fav => fav.id === id);
  };

  return {
    favorites,
    addFavorite,
    removeFavorite,
    isFavorite
  };
};