export const initialNutrition = {
  calorieGoal: 2300,
  macros: {
    protein: { current: 110, goal: 150 },
    carbs: { current: 220, goal: 280 },
    fat: { current: 60, goal: 75 },
  },
  meals: [
    {
      id: 'breakfast',
      name: 'Breakfast',
      foods: [
        { id: 'eggs', name: 'Eggs', calories: 160, protein: 13, carbs: 1, fat: 11 },
        { id: 'oats', name: 'Oats', calories: 220, protein: 8, carbs: 38, fat: 4 },
        { id: 'banana', name: 'Banana', calories: 140, protein: 1, carbs: 35, fat: 0 },
      ],
    },
    {
      id: 'lunch',
      name: 'Lunch',
      foods: [
        { id: 'rice', name: 'Rice', calories: 260, protein: 5, carbs: 56, fat: 1 },
        { id: 'chicken-curry', name: 'Chicken Curry', calories: 330, protein: 35, carbs: 8, fat: 18 },
        { id: 'dal', name: 'Dal', calories: 130, protein: 8, carbs: 20, fat: 2 },
      ],
    },
    {
      id: 'dinner',
      name: 'Dinner',
      foods: [
        { id: 'roti', name: 'Roti', calories: 220, protein: 7, carbs: 44, fat: 2 },
        { id: 'paneer', name: 'Paneer', calories: 300, protein: 19, carbs: 6, fat: 22 },
      ],
    },
    {
      id: 'snacks',
      name: 'Snacks',
      foods: [
        { id: 'poha', name: 'Poha', calories: 90, protein: 3, carbs: 12, fat: 3 },
      ],
    },
  ],
}

export const foodSuggestions = ['Rice', 'Roti', 'Dal', 'Chicken Curry', 'Paneer', 'Eggs', 'Dosa', 'Poha', 'Biryani']
