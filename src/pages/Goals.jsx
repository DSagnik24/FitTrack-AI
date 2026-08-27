import { Save } from 'lucide-react'
import { useState } from 'react'
import Button from '../components/common/Button.jsx'
import ProgressBar from '../components/common/ProgressBar.jsx'
import { useFitness } from '../context/FitnessContext.jsx'

export default function Goals() {
  const { goals, setGoals, user, notify } = useFitness()
  const [draft, setDraft] = useState(goals)

  const fields = [
    ['targetWeight', 'Target Weight', 'kg', user.currentWeight],
    ['targetCalories', 'Target Calories', 'kcal', 1850],
    ['proteinGoal', 'Protein Goal', 'g', 110],
    ['weeklyWorkoutGoal', 'Weekly Workout Goal', 'workouts', 4],
    ['dailyWaterGoal', 'Daily Water Goal', 'L', 2.2],
    ['dailyStepGoal', 'Daily Step Goal', 'steps', 6850],
  ]

  const save = (event) => {
    event.preventDefault()
    setGoals(draft)
    notify('Goals updated')
  }

  return (
    <form className="space-y-6" onSubmit={save}>
      <section className="panel p-6">
        <p className="text-xs font-bold uppercase tracking-wider text-mint">Primary Goal</p>
        <h2 className="mt-2 text-3xl font-bold">{user.primaryGoal}</h2>
      </section>
      <div className="grid gap-5 md:grid-cols-2 xl:grid-cols-3">
        {fields.map(([key, label, unit, current]) => (
          <section key={key} className="panel p-5">
            <label className="text-sm font-semibold" htmlFor={key}>{label}</label>
            <div className="mt-3 flex items-center gap-2">
              <input id={key} className="field" type="number" value={draft[key]} onChange={(event) => setDraft({ ...draft, [key]: Number(event.target.value) })} />
              <span className="w-20 text-sm text-slate-500">{unit}</span>
            </div>
            <ProgressBar value={current} max={draft[key]} className="mt-4" />
            <p className="muted mt-2">{current.toLocaleString()} of {Number(draft[key]).toLocaleString()} {unit}</p>
          </section>
        ))}
      </div>
      <Button type="submit"><Save size={16} />Save Goals</Button>
    </form>
  )
}
