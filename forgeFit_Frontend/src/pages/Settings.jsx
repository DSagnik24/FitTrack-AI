import { Moon, Monitor, Sun } from 'lucide-react'
import { useTheme } from '../context/ThemeContext.jsx'

const options = [
  { value: 'light', label: 'Light mode', icon: Sun },
  { value: 'dark', label: 'Dark mode', icon: Moon },
  { value: 'system', label: 'System preference', icon: Monitor },
]

export default function Settings() {
  const { theme, setTheme } = useTheme()

  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-2xl font-bold">Settings</h2>
        <p className="muted mt-1">Manage appearance and app preferences.</p>
      </div>
      <section className="panel p-6">
        <h3 className="text-lg font-semibold">Appearance</h3>
        <div className="mt-5 grid gap-3 sm:grid-cols-3">
          {options.map(({ value, label, icon: Icon }) => (
            <button key={value} className={`flex items-center gap-3 rounded-lg border p-4 text-left transition ${theme === value ? 'border-mint bg-mint/10 text-mint' : 'border-slate-200 hover:border-mint dark:border-neutral-800'}`} onClick={() => setTheme(value)}>
              <Icon size={20} />
              <span className="font-medium">{label}</span>
            </button>
          ))}
        </div>
      </section>
      <section className="panel p-6">
        <h3 className="text-lg font-semibold">Data</h3>
        <p className="muted mt-2">Fitness interactions are stored locally in this browser until a Spring Boot API is connected.</p>
      </section>
    </div>
  )
}
