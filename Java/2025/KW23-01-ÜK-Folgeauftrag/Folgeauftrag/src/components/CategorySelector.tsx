import React, { useEffect, useState } from 'react';
import { getCategories } from '../services/api';

interface CategorySelectorProps {
  onCategoryChange: (category: string) => void;
  selectedCategory: string;
}

export const CategorySelector: React.FC<CategorySelectorProps> = ({ 
  onCategoryChange, 
  selectedCategory 
}) => {
  const [categories, setCategories] = useState<string[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchCategories = async () => {
      setLoading(true);
      setError(null);
      try {
        const fetchedCategories = await getCategories();
        setCategories(fetchedCategories);
      } catch (err) {
        setError('Failed to load categories');
        console.error('Error loading categories:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchCategories();
  }, []);

  const handleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    onCategoryChange(event.target.value);
  };

  if (loading) return <div>Loading categories...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="category-selector">
      <label htmlFor="category-select">Select Category:</label>
      <select
        id="category-select"
        value={selectedCategory}
        onChange={handleChange}
        className="category-select"
      >
        <option value="">All Categories</option>
        {categories.map((category) => (
          <option key={category} value={category}>
            {category.charAt(0).toUpperCase() + category.slice(1)}
          </option>
        ))}
      </select>
    </div>
  );
};