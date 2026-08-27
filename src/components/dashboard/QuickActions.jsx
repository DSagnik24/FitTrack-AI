import { Bot, Plus, Target } from 'lucide-react'
import { Link } from 'react-router-dom'

const actions = [
  { to: '/nutrition', label: 'Log food', icon: Plus },
  { to: '/goals', label: 'Update goals', icon: Target },
  { to: '/ai-coach', label: 'Ask coach', icon: Bot },
]

export default function QuickActions() {
  return (
    <div className="grid gap-3 sm:grid-cols-3">
      {actions.map(({ to, label, icon: Icon }) => (
        <Link key={to} to={to} className="panel flex items-center gap-3 p-4 text-sm font-semibold hover:-translate-y-0.5 hover:border-mint">
          <span className="rounded-lg bg-mint/10 p-2 text-mint"><Icon size={18} /></span>
          {label}
        </Link>
      ))}
    </div>
  )
}
