import { useState } from 'react'
import { Navigate, useNavigate } from 'react-router-dom'
import Button from '../components/common/Button.jsx'
import { useFitness } from '../context/FitnessContext.jsx'

const initialForm = { name: '', age: '', height: '', currentWeight: '', fitnessLevel: 'Beginner', primaryGoal: 'General Fitness', targetWeight: '', dailyWaterGoal: '', dailyStepGoal: '', targetCalories: '', proteinGoal: '', weeklyWorkoutGoal: '' }

export default function Onboarding() {
  const { user, setUser, setGoals } = useFitness()
  const navigate = useNavigate()
  const [form, setForm] = useState(initialForm)
  if (user) return <Navigate to="/" replace />
  const update = (key, value) => setForm((current) => ({ ...current, [key]: value }))
  const submit = (event) => {
    event.preventDefault()
    const { targetWeight, dailyWaterGoal, dailyStepGoal, targetCalories, proteinGoal, weeklyWorkoutGoal, ...profile } = form
    setUser({ ...profile, age: Number(form.age), currentWeight: Number(form.currentWeight) })
    setGoals({ targetWeight: Number(targetWeight), dailyWaterGoal: Number(dailyWaterGoal), dailyStepGoal: Number(dailyStepGoal), targetCalories: Number(targetCalories), proteinGoal: Number(proteinGoal), weeklyWorkoutGoal: Number(weeklyWorkoutGoal) })
    navigate('/')
  }
  return <main className="min-h-screen bg-slate-50 px-4 py-10 text-slate-950 dark:bg-neutral-950 dark:text-neutral-50"><form onSubmit={submit} className="panel mx-auto max-w-3xl space-y-6 p-6"><div><p className="text-sm font-medium text-mint">ForgeFit AI</p><h1 className="mt-2 text-3xl font-bold">Set up your fitness profile</h1><p className="muted mt-2">Your dashboard starts empty and grows from what you log.</p></div><div className="grid gap-4 sm:grid-cols-2">{[['name','Name','text'],['age','Age (years)','number'],['height','Height (cm)','number'],['currentWeight','Current Weight (kg)','number'],['targetWeight','Target Weight (kg)','number'],['dailyWaterGoal','Daily Water Goal (L)','number'],['dailyStepGoal','Daily Step Goal (steps)','number'],['targetCalories','Daily Calorie Goal (kcal)','number'],['proteinGoal','Daily Protein Goal (g)','number'],['weeklyWorkoutGoal','Weekly Workout Goal (workouts)','number']].map(([key, label, type]) => <label key={key} className="space-y-1 text-sm font-medium">{label}<input className="field" required type={type} min={type === 'number' ? '0' : undefined} step={['height', 'currentWeight', 'targetWeight', 'dailyWaterGoal'].includes(key) ? '0.1' : '1'} value={form[key]} onChange={(event) => update(key, event.target.value)} /></label>)}<label className="space-y-1 text-sm font-medium">Fitness Level<select className="field" value={form.fitnessLevel} onChange={(event) => update('fitnessLevel', event.target.value)}>{['Beginner','Intermediate','Advanced'].map((item) => <option key={item}>{item}</option>)}</select></label><label className="space-y-1 text-sm font-medium">Primary Goal<select className="field" value={form.primaryGoal} onChange={(event) => update('primaryGoal', event.target.value)}>{['Lose Weight','Build Muscle','Maintain Weight','Improve Strength','General Fitness'].map((item) => <option key={item}>{item}</option>)}</select></label></div><Button type="submit">Continue to dashboard</Button></form></main>
}