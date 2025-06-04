export interface ChuckJoke {
  categories: string[];
  created_at: string;
  icon_url: string;
  id: string;
  updated_at: string;
  url: string;
  value: string;
}

export interface FavoriteJoke {
  id: string;
  value: string;
  addedAt: Date;
}