import { Inbox } from 'lucide-react'

export default function EmptyState({ title = 'Nothing here yet', description = 'Create an item to get started.' }) {
  return (
    <div className="flex flex-col items-center justify-center rounded-lg border border-dashed border-slate-300 p-8 text-center dark:border-neutral-700">
      <Inbox className="mb-3 text-slate-400" size={32} />
      <h3 className="font-semibold">{title}</h3>
      <p className="muted mt-1">{description}</p>
    </div>
  )
}
