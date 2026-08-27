import { createContext, useContext, useMemo, useState } from 'react'
import { readStorage, writeStorage } from '../services/storageService.js'

const FitnessContext = createContext(null)

export function FitnessProvider({ children }) {
  const [user, setUserState] = useState(() => readStorage('user', null))
  const [goals, setGoalsState] = useState(() => readStorage('goals', null))
  const [workouts, setWorkoutsState] = useState(() => readStorage('workouts', []))
  const [workoutHistory, setWorkoutHistory] = useState(() => readStorage('workout-history', []))
  const [nutritionEntries, setNutritionEntries] = useState(() => readStorage('nutrition', []))
  const [waterEntries, setWaterEntries] = useState(() => readStorage('water', []))
  const [sleepEntries, setSleepEntries] = useState(() => readStorage('sleep', []))
  const [stepEntries, setStepEntries] = useState(() => readStorage('steps', []))
  const [measurements, setMeasurements] = useState(() => readStorage('measurements', []))
  const [toasts, setToasts] = useState([])

  const notify = (message, type = 'success') => {
    const id = crypto.randomUUID()
    setToasts((items) => [...items, { id, message, type }])
    window.setTimeout(() => setToasts((items) => items.filter((item) => item.id !== id)), 3000)
  }

  const setUser = (next) => {
    setUserState(next)
    writeStorage('user', next)
  }

  const setGoals = (next) => {
    setGoalsState(next)
    writeStorage('goals', next)
  }

  const setWorkouts = (next) => {
    setWorkoutsState(next)
    writeStorage('workouts', next)
  }

  const addWater = (liters) => {
    const next = [...waterEntries, { id: crypto.randomUUID(), amount: Number(liters), date: new Date().toISOString() }]
    setWaterEntries(next)
    writeStorage('water', next)
    notify(`Added ${Math.round(liters * 1000)} ml water`)
  }

  const addFood = (mealId, food) => {
    const next = [...nutritionEntries, { ...food, foodName: food.foodName || food.name, mealType: mealId, id: crypto.randomUUID(), date: food.date || new Date().toISOString() }]
    setNutritionEntries(next)
    writeStorage('nutrition', next)
    notify(`${food.foodName || food.name} added to ${mealId}`)
  }

  const addWorkoutPlan = (plan) => {
    setWorkouts([...workouts, { ...plan, id: crypto.randomUUID() }])
    notify('Workout plan created')
  }

  const deleteWorkoutPlan = (planId) => {
    setWorkouts(workouts.filter((plan) => plan.id !== planId))
    notify('Workout plan deleted')
  }

  const today = new Date().toISOString().slice(0, 10)
  const nutritionTotals = useMemo(() => nutritionEntries.filter((entry) => entry.date?.slice(0, 10) === today).reduce(
      (totals, food) => ({
        calories: totals.calories + Number(food.calories || 0),
        protein: totals.protein + Number(food.protein || 0),
        carbs: totals.carbs + Number(food.carbohydrates || food.carbs || 0),
        fat: totals.fat + Number(food.fat || 0),
      }),
      { calories: 0, protein: 0, carbs: 0, fat: 0 },
    ), [nutritionEntries, today])

  const nutrition = useMemo(() => ({
    meals: ['breakfast', 'lunch', 'dinner', 'snacks'].map((id) => ({ id, name: id[0].toUpperCase() + id.slice(1), foods: nutritionEntries.filter((entry) => entry.mealType === id && entry.date?.slice(0, 10) === today) })),
    macros: { carbs: { goal: null }, fat: { goal: null } },
  }), [nutritionEntries, today])
  const water = waterEntries.filter((entry) => entry.date?.slice(0, 10) === today).reduce((sum, entry) => sum + Number(entry.amount || 0), 0)
  const latestMeasurement = [...measurements].reverse().find((entry) => entry.weight !== null && entry.weight !== '')
  const weeklyVolume = workoutHistory.filter((entry) => new Date(entry.endTime || entry.startTime) >= new Date(Date.now() - 7 * 86400000)).map((entry) => ({ week: new Date(entry.endTime || entry.startTime).toLocaleDateString(), volume: entry.totalVolume || 0 }))
  const weightProgress = measurements.filter((entry) => entry.weight !== null && entry.weight !== '').map((entry) => ({ month: entry.date, weight: Number(entry.weight) }))

  const value = {
    user,
    setUser,
    goals,
    setGoals,
    nutrition,
    nutritionTotals,
    water,
    addWater,
    workouts,
    addWorkoutPlan,
    deleteWorkoutPlan,
    progress: { weightProgress, strengthProgress: [], weeklyVolume },
    workoutHistory,
    nutritionEntries,
    waterEntries,
    sleepEntries,
    stepEntries,
    measurements,
    currentWeight: latestMeasurement?.weight ?? null,
    addMeasurement: (entry) => { const next = [...measurements, { ...entry, id: crypto.randomUUID() }]; setMeasurements(next); writeStorage('measurements', next) },
    addSleep: (entry) => { const next = [...sleepEntries, { ...entry, id: crypto.randomUUID() }]; setSleepEntries(next); writeStorage('sleep', next) },
    addSteps: (entry) => { const next = [...stepEntries, { ...entry, id: crypto.randomUUID() }]; setStepEntries(next); writeStorage('steps', next) },
    completeWorkout: (entry) => { const next = [...workoutHistory, { ...entry, id: crypto.randomUUID() }]; setWorkoutHistory(next); writeStorage('workout-history', next) },
    toasts,
    notify,
  }

  return <FitnessContext.Provider value={value}>{children}</FitnessContext.Provider>
}

export const useFitness = () => useContext(FitnessContext)
