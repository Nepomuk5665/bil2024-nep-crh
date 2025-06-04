import React, { useState } from 'react';
import type { FavoriteJoke } from '../types/joke';

interface FavoritesListProps {
  favorites: FavoriteJoke[];
  onRemoveFavorite: (id: string) => void;
}

export const FavoritesList: React.FC<FavoritesListProps> = ({ favorites, onRemoveFavorite }) => {
  const [isCollapsed, setIsCollapsed] = useState(false);

  const toggleCollapse = () => {
    setIsCollapsed(!isCollapsed);
  };

  return (
    <div className="favorites-list">
      <div className="favorites-header">
        <h3>Favorite Jokes ({favorites.length}/5)</h3>
        <button 
          className="collapse-button" 
          onClick={toggleCollapse}
          aria-label={isCollapsed ? 'Expand favorites' : 'Collapse favorites'}
        >
          {isCollapsed ? '▼' : '▲'}
        </button>
      </div>
      
      {!isCollapsed && (
        <div className="favorites-content">
          {favorites.length === 0 ? (
            <p className="no-favorites">No favorite jokes yet!</p>
          ) : (
            <ul className="favorites-items">
              {favorites.map((favorite) => (
                <li key={favorite.id} className="favorite-item">
                  <p className="favorite-text">{favorite.value}</p>
                  <button
                    className="remove-button"
                    onClick={() => onRemoveFavorite(favorite.id)}
                    aria-label="Remove from favorites"
                  >
                    ✕
                  </button>
                </li>
              ))}
            </ul>
          )}
        </div>
      )}
    </div>
  );
};