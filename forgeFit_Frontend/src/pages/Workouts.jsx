import { Plus, Trash2 } from 'lucide-react'
import { useState } from 'react'
import Button from '../components/common/Button.jsx'
import EmptyState from '../components/common/EmptyState.jsx'
import Modal from '../components/common/Modal.jsx'
import WorkoutCard from '../components/workout/WorkoutCard.jsx'
import { useFitness } from '../context/FitnessContext.jsx'

export default function Workouts() {
  const { workouts, addWorkoutPlan, deleteWorkoutPlan } = useFitness()
  const [open, setOpen] = useState(false)
  const [form, setForm] = useState({ name: '', description: '', exercises: [{ name: '', sets: 3, targetReps: '8-10', rest: 90 }] })

  const updateExercise = (index, field, value) => {
    const exercises = form.exercises.map((exercise, itemIndex) => (itemIndex === index ? { ...exercise, [field]: value } : exercise))
    setForm({ ...form, exercises })
  }

  const submit = (event) => {
    event.preventDefault()
    addWorkoutPlan({
      name: form.name,
      description: form.description,
      exercises: form.exercises.map((exercise) => ({ ...exercise, id: crypto.randomUUID(), previous: [] })),
    })
    setForm({ name: '', description: '', exercises: [{ name: '', sets: 3, targetReps: '8-10', rest: 90 }] })
    setOpen(false)
  }

  return (
    <div className="space-y-6">
      <div className="flex flex-wrap items-center justify-between gap-3">
        <div>
          <h2 className="text-2xl font-bold">My Workout Plans</h2>
          <p className="muted mt-1">Create, review, and start your training blocks.</p>
        </div>
        <Button onClick={() => setOpen(true)}><Plus size={16} />Create Workout Plan</Button>
      </div>
      {workouts.length ? <div className="grid gap-5 md:grid-cols-2 xl:grid-cols-3">{workouts.map((plan) => <WorkoutCard key={plan.id} plan={plan} onDelete={deleteWorkoutPlan} />)}</div> : <EmptyState title="No workout plans" description="Create a plan to start tracking sets and volume." />}
      <Modal title="Create Workout Plan" open={open} onClose={() => setOpen(false)}>
        <form className="space-y-4" onSubmit={submit}>
          <input className="field" required placeholder="Workout Name" value={form.name} onChange={(event) => setForm({ ...form, name: event.target.value })} />
          <textarea className="field min-h-24" placeholder="Description" value={form.description} onChange={(event) => setForm({ ...form, description: event.target.value })} />
          <div className="space-y-3">
            {form.exercises.map((exercise, index) => (
              <div key={index} className="rounded-lg border border-slate-200 p-3 dark:border-neutral-800">
                <div className="grid gap-3 sm:grid-cols-[1.4fr_0.7fr_0.9fr_0.8fr_40px]">
                  <input className="field" required placeholder="Exercise" value={exercise.name} onChange={(event) => updateExercise(index, 'name', event.target.value)} />
                  <input className="field" type="number" min="1" value={exercise.sets} onChange={(event) => updateExercise(index, 'sets', Number(event.target.value))} />
                  <input className="field" value={exercise.targetReps} onChange={(event) => updateExercise(index, 'targetReps', event.target.value)} />
                  <input className="field" type="number" min="15" value={exercise.rest} onChange={(event) => updateExercise(index, 'rest', Number(event.target.value))} />
                  <Button type="button" variant="ghost" size="icon" disabled={form.exercises.length === 1} onClick={() => setForm({ ...form, exercises: form.exercises.filter((_, itemIndex) => itemIndex !== index) })}><Trash2 size={16} /></Button>
                </div>
              </div>
            ))}
          </div>
          <div className="flex flex-wrap justify-between gap-2">
            <Button type="button" variant="secondary" onClick={() => setForm({ ...form, exercises: [...form.exercises, { name: '', sets: 3, targetReps: '8-10', rest: 90 }] })}><Plus size={16} />Add Exercise</Button>
            <Button type="submit">Save Plan</Button>
          </div>
        </form>
      </Modal>
    </div>
  )
}
