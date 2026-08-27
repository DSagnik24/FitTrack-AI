import { initialNutrition } from '../data/mockNutrition.js'
import { strengthProgress, weeklyVolume, weightProgress } from '../data/mockProgress.js'
import { workoutPlans } from '../data/mockWorkouts.js'

export const getDashboardData = async () => ({
  workouts: workoutPlans,
  nutrition: initialNutrition,
  progress: { weightProgress, strengthProgress, weeklyVolume },
})

export const getWorkouts = async () => workoutPlans

export const createWorkout = async (workout) => workout

export const getNutrition = async () => initialNutrition

export const sendCoachMessage = async (message) => ({
  message,
  response:
    'Based on your last 30 days, your workout consistency is strong and your protein intake is close to target. Add around 20g of protein daily and keep the current program for two more weeks before changing volume.',
})
