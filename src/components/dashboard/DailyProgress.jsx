import { Droplets, Flame, Footprints, Moon, Utensils } from 'lucide-react'
import { useFitness } from '../../context/FitnessContext.jsx'
import Button from '../common/Button.jsx'
import StatCard from './StatCard.jsx'

export default function DailyProgress() {
  const { goals, nutritionTotals, water, addWater, stepEntries, sleepEntries } = useFitness()
  const calories = nutritionTotals.calories
  const protein = nutritionTotals.protein
  const today = new Date().toISOString().slice(0, 10)
  const steps = stepEntries.filter((entry) => entry.date?.slice(0, 10) === today).reduce((sum, entry) => sum + Number(entry.steps || 0), 0)
  const sleep = sleepEntries.find((entry) => entry.date?.slice(0, 10) === today)

  return (
    <div className="grid gap-4 sm:grid-cols-2 xl:grid-cols-5">
      <StatCard icon={Flame} label="Calories" value={`${calories.toLocaleString()} / ${goals.targetCalories || '-'} kcal`} progress={goals.targetCalories ? (calories / goals.targetCalories) * 100 : 0} color="bg-coral" />
      <StatCard icon={Utensils} label="Protein" value={`${protein}g / ${goals.proteinGoal || '-'}g`} progress={goals.proteinGoal ? (protein / goals.proteinGoal) * 100 : 0} color="bg-amber" />
      <StatCard icon={Droplets} label="Water" value={`${water}L / ${goals.dailyWaterGoal}L`} progress={(water / goals.dailyWaterGoal) * 100} color="bg-sky-500">
        <div className="mt-4 flex flex-wrap gap-2">
          <Button size="sm" variant="secondary" onClick={() => addWater(0.25)}>+250 ml</Button>
          <Button size="sm" variant="secondary" onClick={() => addWater(0.5)}>+500 ml</Button>
        </div>
      </StatCard>
      <StatCard icon={Moon} label="Sleep" value={sleep?.duration ? `${sleep.duration}h` : 'No entry'} progress={0} color="bg-indigo-500" />
      <StatCard icon={Footprints} label="Steps" value={`${steps.toLocaleString()} / ${(goals.dailyStepGoal || 0).toLocaleString()}`} progress={goals.dailyStepGoal ? (steps / goals.dailyStepGoal) * 100 : 0} color="bg-emerald-500" />
    </div>
  )
}
