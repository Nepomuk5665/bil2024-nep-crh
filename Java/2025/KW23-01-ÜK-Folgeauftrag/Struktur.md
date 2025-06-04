# Chuck Norris Jokes App - Projektstruktur

## Ordnerstruktur

```
my-chuck-app/
├── src/
│   ├── components/
│   │   ├── JokeDisplay.tsx        # Zeigt den Witz an
│   │   ├── CategorySelector.tsx   # Dropdown für Kategorien
│   │   ├── FavoritesList.tsx      # Liste der Favoriten
│   │   └── JokeButton.tsx         # "Neuer Witz" Button
│   ├── types/
│   │   └── joke.ts                # TypeScript Interfaces
│   ├── services/
│   │   └── api.ts                 # API Calls zu Chuck Norris API
│   ├── hooks/
│   │   └── useFavorites.ts        # Custom Hook für Favoriten
│   ├── App.tsx                    # Main Component
│   ├── App.css                    # Styling
│   └── main.tsx                   # Entry Point
├── package.json
└── README.md
```

