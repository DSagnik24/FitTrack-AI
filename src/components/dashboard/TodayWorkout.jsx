import { Clock, Dumbbell, ListChecks, Play } from 'lucide-react'
import { useNavigate } from 'react-router-dom'
import { useFitness } from '../../context/FitnessContext.jsx'
import Button from '../common/Button.jsx'

export default function TodayWorkout() {
  const navigate = useNavigate()
  const { workouts } = useFitness()
  const workout = workouts[0]
  if (!workout) {
    return (
      <section className="panel p-5">
        <p className="text-xs font-bold uppercase tracking-wider text-mint">Today's Workout</p>
        <h2 className="mt-2 text-2xl font-semibold">No workout plans yet</h2>
        <p className="muted mt-2">Create your first workout plan to start training.</p>
        <Button className="mt-5" onClick={() => navigate('/workouts')}><Dumbbell size={16} />Create Workout</Button>
      </section>
    )
  }
  const totalSets = workout.exercises.reduce((sum, exercise) => sum + Number(exercise.sets || exercise.targetSets || 0), 0)

  return (
    <section className="panel p-5">
      <div className="flex flex-wrap items-start justify-between gap-4">
        <div>
          <p className="text-xs font-bold uppercase tracking-wider text-mint">Today's Workout</p>
          <h2 className="mt-2 text-2xl font-semibold">{workout.name}</h2>
          <p className="muted mt-1">{workout.description}</p>
        </div>
        <Button onClick={() => navigate('/workouts/active')}><Play size={16} />Start Workout</Button>
      </div>
      <div className="mt-5 grid gap-3 sm:grid-cols-3">
        <div className="rounded-lg bg-slate-50 p-3 dark:bg-neutral-950"><Dumbbell size={18} /><p className="mt-2 font-semibold">{workout.exercises.length} Exercises</p></div>
        <div className="rounded-lg bg-slate-50 p-3 dark:bg-neutral-950"><ListChecks size={18} /><p className="mt-2 font-semibold">{totalSets} Total Sets</p></div>
        <div className="rounded-lg bg-slate-50 p-3 dark:bg-neutral-950"><Clock size={18} /><p className="mt-2 font-semibold">65 minutes</p></div>
      </div>
      <ol className="mt-5 grid gap-2 sm:grid-cols-2">
        {workout.exercises.map((exercise, index) => (
          <li key={exercise.id} className="flex items-center gap-3 rounded-md border border-slate-200 p-3 text-sm dark:border-neutral-800">
            <span className="flex h-6 w-6 items-center justify-center rounded bg-slate-100 text-xs font-bold dark:bg-neutral-800">{index + 1}</span>
            {exercise.name}
          </li>
        ))}
      </ol>
    </section>
  )
}
