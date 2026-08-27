import { CheckCircle2, Info } from 'lucide-react'
import { useFitness } from '../../context/FitnessContext.jsx'

export default function ToastStack() {
  const { toasts } = useFitness()

  return (
    <div className="fixed bottom-4 right-4 z-50 space-y-2">
      {toasts.map((toast) => (
        <div key={toast.id} className="flex min-w-64 items-center gap-3 rounded-lg border border-slate-200 bg-white p-3 shadow-soft dark:border-neutral-800 dark:bg-neutral-900">
          {toast.type === 'success' ? <CheckCircle2 className="text-mint" size={18} /> : <Info className="text-amber" size={18} />}
          <span className="text-sm font-medium">{toast.message}</span>
        </div>
      ))}
    </div>
  )
}
