import ProgressBar from '../common/ProgressBar.jsx'

export default function MacroCard({ label, current, goal, color }) {
  return (
    <div className="rounded-lg border border-slate-200 p-4 dark:border-neutral-800">
      <div className="flex items-center justify-between">
        <p className="font-medium">{label}</p>
        <p className="text-sm font-semibold">{current} / {goal}g</p>
      </div>
      <ProgressBar value={current} max={goal} color={color} className="mt-3" />
    </div>
  )
}
