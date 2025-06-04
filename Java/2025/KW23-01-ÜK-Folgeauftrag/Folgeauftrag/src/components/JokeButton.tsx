import React from 'react';

interface JokeButtonProps {
  onClick: () => void;
  loading: boolean;
}

export const JokeButton: React.FC<JokeButtonProps> = ({ onClick, loading }) => {
  return (
    <button
      className="joke-button"
      onClick={onClick}
      disabled={loading}
    >
      {loading ? 'Loading...' : 'Get New Chuck Joke!'}
    </button>
  );
};