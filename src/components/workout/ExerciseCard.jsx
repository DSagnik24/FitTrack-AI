import { CheckCircle2, Plus } from 'lucide-react'
import { useMemo, useState } from 'react'
import Button from '../common/Button.jsx'
import SetRow from './SetRow.jsx'

const makeSet = (index) => ({ id: crypto.randomUUID(), weight: index === 0 ? 65 : '', reps: '', completed: false })

export default function ExerciseCard({ exercise }) {
  const [sets, setSets] = useState(() => Array.from({ length: exercise.sets }, (_, index) => makeSet(index)))
  const complete = sets.every((set) => set.completed)
  const volume = useMemo(() => sets.reduce((sum, set) => sum + Number(set.weight || 0) * Number(set.reps || 0), 0), [sets])

  const updateSet = (id, next) => setSets(sets.map((set) => (set.id === id ? next : set)))
  const removeSet = (id) => setSets(sets.filter((set) => set.id !== id))

  return (
    <article className="panel p-5">
      <div className="flex flex-wrap items-start justify-between gap-4">
        <div>
          <h3 className="text-lg font-semibold">{exercise.name}</h3>
          <p className="muted mt-1">Target {exercise.sets} sets · {exercise.targetReps} reps · {exercise.rest}s rest</p>
        </div>
        {complete && <span className="inline-flex items-center gap-1 rounded-md bg-mint/10 px-2.5 py-1 text-xs font-semibold text-mint"><CheckCircle2 size={14} />Complete</span>}
      </div>
      <div className="mt-4 rounded-lg bg-slate-50 p-3 dark:bg-neutral-950">
        <p className="text-xs font-bold uppercase text-slate-500">Previous</p>
        <div className="mt-2 flex flex-wrap gap-2">
          {exercise.previous.map((set, index) => <span key={index} className="rounded bg-white px-2 py-1 text-sm dark:bg-neutral-900">{set.weight}kg x {set.reps}</span>)}
        </div>
      </div>
      <div className="mt-4 space-y-2">
        <div className="grid grid-cols-[40px_1fr_1fr_44px_36px] gap-2 px-2 text-xs font-bold uppercase text-slate-500">
          <span>Set</span><span>Weight</span><span>Reps</span><span>Done</span><span />
        </div>
        {sets.map((set, index) => (
          <SetRow key={set.id} set={set} index={index} canRemove={sets.length > 1} onChange={(next) => updateSet(set.id, next)} onRemove={() => removeSet(set.id)} />
        ))}
      </div>
      <div className="mt-4 flex flex-wrap items-center justify-between gap-3">
        <Button variant="secondary" size="sm" onClick={() => setSets([...sets, makeSet(sets.length)])}><Plus size={15} />Add Set</Button>
        <p className="text-sm font-semibold">Volume: {volume.toLocaleString()} kg</p>
      </div>
    </article>
  )
}
