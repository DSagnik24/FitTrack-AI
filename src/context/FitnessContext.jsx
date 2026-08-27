import { createContext, useContext, useMemo, useState } from 'react'
import { initialNutrition } from '../data/mockNutrition.js'
import { strengthProgress, weeklyVolume, weightProgress } from '../data/mockProgress.js'
import { workoutPlans } from '../data/mockWorkouts.js'

const FitnessContext = createContext(null)

const defaultUser = {
  name: 'Sagnik',
  age: 24,
  height: '178 cm',
  currentWeight: 77.8,
  fitnessLevel: 'Intermediate',
  primaryGoal: 'Build Muscle',
  avatar: 'https://api.dicebear.com/9.x/initials/svg?seed=Sagnik',
}

const defaultGoals = {
  targetWeight: 76,
  targetCalories: 2300,
  proteinGoal: 150,
  weeklyWorkoutGoal: 5,
  dailyWaterGoal: 3,
  dailyStepGoal: 10000,
}

const readStorage = (key, fallback) => {
  try {
    const value = localStorage.getItem(key)
    return value ? JSON.parse(value) : fallback
  } catch {
    return fallback
  }
}

const writeStorage = (key, value) => {
  localStorage.setItem(key, JSON.stringify(value))
}

export function FitnessProvider({ children }) {
  const [user, setUserState] = useState(() => readStorage('fittrack-user', defaultUser))
  const [goals, setGoalsState] = useState(() => readStorage('fittrack-goals', defaultGoals))
  const [nutrition, setNutritionState] = useState(() => readStorage('fittrack-nutrition', initialNutrition))
  const [water, setWaterState] = useState(() => readStorage('fittrack-water', 2.2))
  const [workouts, setWorkoutsState] = useState(() => readStorage('fittrack-workouts', workoutPlans))
  const [toasts, setToasts] = useState([])

  const notify = (message, type = 'success') => {
    const id = crypto.randomUUID()
    setToasts((items) => [...items, { id, message, type }])
    window.setTimeout(() => setToasts((items) => items.filter((item) => item.id !== id)), 3000)
  }

  const setUser = (next) => {
    setUserState(next)
    writeStorage('fittrack-user', next)
  }

  const setGoals = (next) => {
    setGoalsState(next)
    writeStorage('fittrack-goals', next)
  }

  const setNutrition = (next) => {
    setNutritionState(next)
    writeStorage('fittrack-nutrition', next)
  }

  const setWorkouts = (next) => {
    setWorkoutsState(next)
    writeStorage('fittrack-workouts', next)
  }

  const addWater = (liters) => {
    setWaterState((current) => {
      const next = Math.min(Number((current + liters).toFixed(2)), goals.dailyWaterGoal)
      writeStorage('fittrack-water', next)
      return next
    })
    notify(`Added ${Math.round(liters * 1000)} ml water`)
  }

  const addFood = (mealId, food) => {
    const nextNutrition = {
      ...nutrition,
      meals: nutrition.meals.map((meal) =>
        meal.id === mealId ? { ...meal, foods: [...meal.foods, { ...food, id: crypto.randomUUID() }] } : meal,
      ),
    }
    setNutrition(nextNutrition)
    notify(`${food.name} added to ${mealId}`)
  }

  const addWorkoutPlan = (plan) => {
    setWorkouts([...workouts, { ...plan, id: crypto.randomUUID() }])
    notify('Workout plan created')
  }

  const deleteWorkoutPlan = (planId) => {
    setWorkouts(workouts.filter((plan) => plan.id !== planId))
    notify('Workout plan deleted')
  }

  const nutritionTotals = useMemo(() => {
    const foods = nutrition.meals.flatMap((meal) => meal.foods)
    return foods.reduce(
      (totals, food) => ({
        calories: totals.calories + Number(food.calories || 0),
        protein: totals.protein + Number(food.protein || 0),
        carbs: totals.carbs + Number(food.carbs || 0),
        fat: totals.fat + Number(food.fat || 0),
      }),
      { calories: 0, protein: 0, carbs: 0, fat: 0 },
    )
  }, [nutrition])

  const value = {
    user,
    setUser,
    goals,
    setGoals,
    nutrition,
    setNutrition,
    nutritionTotals,
    water,
    addWater,
    workouts,
    addWorkoutPlan,
    deleteWorkoutPlan,
    progress: { weightProgress, strengthProgress, weeklyVolume },
    toasts,
    notify,
  }

  return <FitnessContext.Provider value={value}>{children}</FitnessContext.Provider>
}

export const useFitness = () => useContext(FitnessContext)
