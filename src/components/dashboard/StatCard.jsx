import ProgressBar from '../common/ProgressBar.jsx'

export default function StatCard({ icon: Icon, label, value, detail, progress, color = 'bg-mint', children }) {
  return (
    <section className="panel p-4 hover:-translate-y-0.5">
      <div className="flex items-start justify-between gap-3">
        <div>
          <p className="text-sm font-medium text-slate-500 dark:text-neutral-400">{label}</p>
          <p className="mt-2 text-2xl font-semibold tracking-normal">{value}</p>
        </div>
        {Icon && <div className="rounded-lg bg-slate-100 p-2 text-slate-700 dark:bg-neutral-800 dark:text-neutral-200"><Icon size={20} /></div>}
      </div>
      {typeof progress === 'number' && <ProgressBar value={progress} className="mt-4" color={color} />}
      {detail && <p className="mt-3 text-sm text-slate-500 dark:text-neutral-400">{detail}</p>}
      {children}
    </section>
  )
}
