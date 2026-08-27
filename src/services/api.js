export const getDashboardData = async () => ({ workouts: [], nutrition: [], progress: {} })

export const getWorkouts = async () => []

export const createWorkout = async (workout) => workout

export const getNutrition = async () => []

export const sendCoachMessage = async (message) => ({
  message,
  response: 'Log workouts, nutrition, sleep, and measurements so personalized coaching can be connected to your future API.',
})
