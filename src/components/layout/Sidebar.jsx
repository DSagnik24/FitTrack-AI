import { Bot, Dumbbell, Flag, LayoutDashboard, LogOut, Menu, Settings, Target, TrendingUp, User, Utensils } from 'lucide-react'
import { NavLink } from 'react-router-dom'
import Button from '../common/Button.jsx'

const links = [
  { to: '/', label: 'Dashboard', icon: LayoutDashboard },
  { to: '/workouts', label: 'Workouts', icon: Dumbbell },
  { to: '/nutrition', label: 'Nutrition', icon: Utensils },
  { to: '/progress', label: 'Progress', icon: TrendingUp },
  { to: '/goals', label: 'Goals', icon: Target },
  { to: '/ai-coach', label: 'AI Coach', icon: Bot },
  { to: '/profile', label: 'Profile', icon: User },
]

export default function Sidebar({ collapsed, setCollapsed, mobileOpen, setMobileOpen }) {
  const base = 'fixed inset-y-0 left-0 z-40 flex flex-col border-r border-slate-200 bg-white transition-all duration-300 dark:border-neutral-800 dark:bg-neutral-950 lg:sticky'
  const mobile = mobileOpen ? 'translate-x-0' : '-translate-x-full lg:translate-x-0'

  return (
    <aside className={`${base} ${mobile} ${collapsed ? 'lg:w-20' : 'lg:w-72'} w-72`}>
      <div className="flex h-16 items-center gap-3 px-4">
        <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-lg bg-ink text-white dark:bg-mint">
          <Flag size={20} />
        </div>
        {!collapsed && <div><p className="font-bold leading-tight">FitTrack AI</p><p className="text-xs text-slate-500">Train smarter</p></div>}
        <Button className="ml-auto hidden lg:inline-flex" variant="ghost" size="icon" onClick={() => setCollapsed(!collapsed)} aria-label="Collapse sidebar">
          <Menu size={18} />
        </Button>
      </div>
      <nav className="flex-1 space-y-1 px-3 py-4">
        {links.map(({ to, label, icon: Icon }) => (
          <NavLink
            key={to}
            to={to}
            end={to === '/'}
            onClick={() => setMobileOpen(false)}
            className={({ isActive }) =>
              `flex h-11 items-center gap-3 rounded-md px-3 text-sm font-medium transition ${isActive ? 'bg-mint/10 text-mint' : 'text-slate-600 hover:bg-slate-100 dark:text-neutral-300 dark:hover:bg-neutral-900'}`
            }
            title={collapsed ? label : undefined}
          >
            <Icon size={19} />
            {!collapsed && <span>{label}</span>}
          </NavLink>
        ))}
      </nav>
      <div className="space-y-1 border-t border-slate-200 p-3 dark:border-neutral-800">
        <NavLink to="/settings" onClick={() => setMobileOpen(false)} className={({ isActive }) => `flex h-11 items-center gap-3 rounded-md px-3 text-sm font-medium transition ${isActive ? 'bg-mint/10 text-mint' : 'text-slate-600 hover:bg-slate-100 dark:text-neutral-300 dark:hover:bg-neutral-900'}`}>
          <Settings size={19} />
          {!collapsed && <span>Settings</span>}
        </NavLink>
        <button className="flex h-11 w-full items-center gap-3 rounded-md px-3 text-sm font-medium text-slate-600 transition hover:bg-slate-100 dark:text-neutral-300 dark:hover:bg-neutral-900">
          <LogOut size={19} />
          {!collapsed && <span>Logout</span>}
        </button>
      </div>
    </aside>
  )
}
