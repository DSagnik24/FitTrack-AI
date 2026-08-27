import { X } from 'lucide-react'
import Button from './Button.jsx'

export default function Modal({ title, open, onClose, children, footer }) {
  if (!open) return null

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-950/50 p-4 backdrop-blur-sm">
      <div className="panel max-h-[90vh] w-full max-w-2xl overflow-hidden">
        <div className="flex items-center justify-between border-b border-slate-200 p-5 dark:border-neutral-800">
          <h2 className="text-lg font-semibold">{title}</h2>
          <Button aria-label="Close modal" variant="ghost" size="icon" onClick={onClose}>
            <X size={18} />
          </Button>
        </div>
        <div className="max-h-[65vh] overflow-y-auto p-5">{children}</div>
        {footer ? <div className="border-t border-slate-200 p-5 dark:border-neutral-800">{footer}</div> : null}
      </div>
    </div>
  )
}
