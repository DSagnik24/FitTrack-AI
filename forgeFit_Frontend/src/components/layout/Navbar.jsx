import { Bell, Menu, Search } from 'lucide-react'
import { useLocation } from 'react-router-dom'
import { useFitness } from '../../context/FitnessContext.jsx'
import Button from '../common/Button.jsx'

const titles = {
  '/': 'Dashboard',
  '/workouts': 'Workouts',
  '/workouts/active': 'Active Workout',
  '/nutrition': 'Nutrition',
  '/progress': 'Progress',
  '/goals': 'Goals',
  '/ai-coach': 'AI Coach',
  '/profile': 'Profile',
  '/settings': 'Settings',
}

export default function Navbar({ setMobileOpen }) {
  const { pathname } = useLocation()
  const { user } = useFitness()
  const date = new Intl.DateTimeFormat('en-US', { weekday: 'long', month: 'long', day: 'numeric' }).format(new Date())

  return (
    <header className="sticky top-0 z-30 flex min-h-16 items-center justify-between border-b border-slate-200 bg-white/85 px-4 backdrop-blur dark:border-neutral-800 dark:bg-neutral-950/85 md:px-6">
      <div className="flex items-center gap-3">
        <Button className="lg:hidden" variant="ghost" size="icon" onClick={() => setMobileOpen(true)} aria-label="Open navigation">
          <Menu size={20} />
        </Button>
        <div>
          <h1 className="text-lg font-semibold">{titles[pathname] || 'FitTrack AI'}</h1>
          <p className="hidden text-xs text-slate-500 dark:text-neutral-400 sm:block">Good morning, {user.name} · {date}</p>
        </div>
      </div>
      <div className="flex items-center gap-2">
        <Button variant="ghost" size="icon" aria-label="Search"><Search size={18} /></Button>
        <Button variant="ghost" size="icon" aria-label="Notifications"><Bell size={18} /></Button>
        <div className="ml-1 flex items-center gap-3">
          <img className="h-9 w-9 rounded-full border border-slate-200 bg-slate-100 dark:border-neutral-700" src={user.avatar} alt={user.name} />
          <span className="hidden text-sm font-medium md:inline">{user.name}</span>
        </div>
      </div>
    </header>
  )
}
