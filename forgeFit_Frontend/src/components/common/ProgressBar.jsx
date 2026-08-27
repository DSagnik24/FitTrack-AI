export default function ProgressBar({ value, max = 100, color = 'bg-mint', className = '' }) {
  const percent = Math.max(0, Math.min(100, (Number(value) / Number(max || 1)) * 100))

  return (
    <div className={`h-2.5 w-full overflow-hidden rounded-full bg-slate-100 dark:bg-neutral-800 ${className}`}>
      <div className={`h-full rounded-full ${color} transition-all duration-500`} style={{ width: `${percent}%` }} />
    </div>
  )
}
