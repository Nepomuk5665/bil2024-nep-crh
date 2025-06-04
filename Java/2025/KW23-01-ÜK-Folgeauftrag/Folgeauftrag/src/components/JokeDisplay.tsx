import React from 'react';
import type { ChuckJoke } from '../types/joke';

interface JokeDisplayProps {
  joke: ChuckJoke | null;
  onAddToFavorites: (joke: ChuckJoke) => void;
  isFavorite: boolean;
}

export const JokeDisplay: React.FC<JokeDisplayProps> = ({ joke, onAddToFavorites, isFavorite }) => {
  if (!joke) {
    return (
      <div className="joke-display empty">
        <p>Click the button to get a Chuck Norris joke!</p>
      </div>
    );
  }

  return (
    <div className="joke-display">
      <p className="joke-text">{joke.value}</p>
      {joke.categories.length > 0 && (
        <div className="categories">
          {joke.categories.map((category) => (
            <span key={category} className="category-badge">
              {category}
            </span>
          ))}
        </div>
      )}
      <button
        className="favorite-button"
        onClick={() => onAddToFavorites(joke)}
        disabled={isFavorite}
      >
        {isFavorite ? '⭐ Favorited' : '☆ Add to Favorites'}
      </button>
    </div>
  );
};