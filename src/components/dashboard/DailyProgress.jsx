import { Droplets, Flame, Footprints, Moon, Utensils } from 'lucide-react'
import { useFitness } from '../../context/FitnessContext.jsx'
import Button from '../common/Button.jsx'
import StatCard from './StatCard.jsx'

export default function DailyProgress() {
  const { goals, nutritionTotals, water, addWater } = useFitness()
  const calories = nutritionTotals.calories
  const protein = nutritionTotals.protein
  const steps = 6850

  return (
    <div className="grid gap-4 sm:grid-cols-2 xl:grid-cols-5">
      <StatCard icon={Flame} label="Calories" value={`${calories.toLocaleString()} / ${goals.targetCalories} kcal`} progress={(calories / goals.targetCalories) * 100} detail={`${Math.round((calories / goals.targetCalories) * 100)}% completed`} color="bg-coral" />
      <StatCard icon={Utensils} label="Protein" value={`${protein}g / ${goals.proteinGoal}g`} progress={(protein / goals.proteinGoal) * 100} detail={`${Math.max(goals.proteinGoal - protein, 0)}g remaining`} color="bg-amber" />
      <StatCard icon={Droplets} label="Water" value={`${water}L / ${goals.dailyWaterGoal}L`} progress={(water / goals.dailyWaterGoal) * 100} color="bg-sky-500">
        <div className="mt-4 flex flex-wrap gap-2">
          <Button size="sm" variant="secondary" onClick={() => addWater(0.25)}>+250 ml</Button>
          <Button size="sm" variant="secondary" onClick={() => addWater(0.5)}>+500 ml</Button>
        </div>
      </StatCard>
      <StatCard icon={Moon} label="Sleep" value="7h 15m" progress={86} detail="Good sleep quality" color="bg-indigo-500" />
      <StatCard icon={Footprints} label="Steps" value={`${steps.toLocaleString()} / ${goals.dailyStepGoal.toLocaleString()}`} progress={(steps / goals.dailyStepGoal) * 100} detail={`${Math.round((steps / goals.dailyStepGoal) * 100)}% completed`} color="bg-emerald-500" />
    </div>
  )
}
