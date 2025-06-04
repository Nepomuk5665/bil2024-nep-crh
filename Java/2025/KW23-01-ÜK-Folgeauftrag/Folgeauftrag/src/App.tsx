import { useState, useEffect } from 'react';
import './App.css';
import type { ChuckJoke } from './types/joke';
import { getRandomJoke, getJokeByCategory } from './services/api';
import { useFavorites } from './hooks/useFavorites';
import { JokeDisplay } from './components/JokeDisplay';
import { CategorySelector } from './components/CategorySelector';
import { JokeButton } from './components/JokeButton';
import { FavoritesList } from './components/FavoritesList';

function App() {
  const [currentJoke, setCurrentJoke] = useState<ChuckJoke | null>(null);
  const [selectedCategory, setSelectedCategory] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [jokeCount, setJokeCount] = useState<number>(0);
  
  const { favorites, addFavorite, removeFavorite, isFavorite } = useFavorites();

  const fetchNewJoke = async () => {
    setLoading(true);
    setError(null);
    
    try {
      const joke = selectedCategory 
        ? await getJokeByCategory(selectedCategory)
        : await getRandomJoke();
      
      setCurrentJoke(joke);
      setJokeCount(prevCount => prevCount + 1);
    } catch (err) {
      setError('Failed to fetch joke. Please try again.');
      console.error('Error fetching joke:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleCategoryChange = (category: string) => {
    setSelectedCategory(category);
  };

  const handleAddToFavorites = (joke: ChuckJoke) => {
    addFavorite(joke);
  };

  useEffect(() => {
    fetchNewJoke();
  }, []);

  return (
    <div className="app">
      <header className="app-header">
        <h1>Chuck Norris Jokes</h1>
        <p className="joke-count">Total jokes viewed: {jokeCount}</p>
      </header>

      <main className="app-main">
        <CategorySelector 
          onCategoryChange={handleCategoryChange}
          selectedCategory={selectedCategory}
        />

        {error && <div className="error-message">{error}</div>}

        <JokeDisplay 
          joke={currentJoke}
          onAddToFavorites={handleAddToFavorites}
          isFavorite={currentJoke ? isFavorite(currentJoke.id) : false}
        />

        <JokeButton 
          onClick={fetchNewJoke}
          loading={loading}
        />

        <FavoritesList 
          favorites={favorites}
          onRemoveFavorite={removeFavorite}
        />
      </main>
    </div>
  );
}

export default App;
