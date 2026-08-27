import { Check } from 'lucide-react'
import Button from '../common/Button.jsx'

export default function SetRow({ set, index, onChange, onRemove, canRemove }) {
  return (
    <div className={`grid grid-cols-[40px_1fr_1fr_44px_36px] items-center gap-2 rounded-md p-2 transition ${set.completed ? 'bg-mint/10' : 'bg-slate-50 dark:bg-neutral-950'}`}>
      <span className="text-sm font-semibold">{index + 1}</span>
      <input className="field h-9" aria-label={`Weight for set ${index + 1}`} type="number" value={set.weight} onChange={(event) => onChange({ ...set, weight: event.target.value })} />
      <input className="field h-9" aria-label={`Reps for set ${index + 1}`} type="number" value={set.reps} onChange={(event) => onChange({ ...set, reps: event.target.value })} />
      <button aria-label={`Complete set ${index + 1}`} className={`flex h-9 w-9 items-center justify-center rounded-md border transition ${set.completed ? 'border-mint bg-mint text-white' : 'border-slate-200 bg-white dark:border-neutral-700 dark:bg-neutral-900'}`} onClick={() => onChange({ ...set, completed: !set.completed })}>
        {set.completed && <Check size={16} />}
      </button>
      <Button aria-label="Remove set" variant="ghost" size="icon" className="h-9 w-9" disabled={!canRemove} onClick={onRemove}>x</Button>
    </div>
  )
}
