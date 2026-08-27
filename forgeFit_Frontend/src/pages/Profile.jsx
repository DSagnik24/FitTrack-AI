import { Save } from 'lucide-react'
import { useState } from 'react'
import Button from '../components/common/Button.jsx'
import { useFitness } from '../context/FitnessContext.jsx'

export default function Profile() {
  const { user, setUser, notify } = useFitness()
  const [draft, setDraft] = useState(user)

  const fields = [
    ['name', 'Name'],
    ['age', 'Age'],
    ['height', 'Height'],
    ['currentWeight', 'Current Weight'],
    ['fitnessLevel', 'Fitness Level'],
    ['primaryGoal', 'Primary Goal'],
  ]

  const submit = (event) => {
    event.preventDefault()
    setUser(draft)
    notify('Profile updated')
  }

  return (
    <div className="grid gap-6 lg:grid-cols-[320px_1fr]">
      <aside className="panel p-6 text-center">
        <img className="mx-auto h-28 w-28 rounded-full border border-slate-200 bg-slate-100 dark:border-neutral-700" src={user.avatar} alt={user.name} />
        <h2 className="mt-4 text-2xl font-bold">{user.name}</h2>
        <p className="muted mt-1">{user.fitnessLevel} · {user.primaryGoal}</p>
        <div className="mt-6 grid grid-cols-2 gap-3 text-left">
          <div className="rounded-lg bg-slate-50 p-3 dark:bg-neutral-950"><p className="muted">Age</p><strong>{user.age}</strong></div>
          <div className="rounded-lg bg-slate-50 p-3 dark:bg-neutral-950"><p className="muted">Height</p><strong>{user.height}</strong></div>
          <div className="rounded-lg bg-slate-50 p-3 dark:bg-neutral-950"><p className="muted">Weight</p><strong>{user.currentWeight} kg</strong></div>
          <div className="rounded-lg bg-slate-50 p-3 dark:bg-neutral-950"><p className="muted">Level</p><strong>{user.fitnessLevel}</strong></div>
        </div>
      </aside>
      <form className="panel p-6" onSubmit={submit}>
        <h3 className="text-lg font-semibold">Edit Profile</h3>
        <div className="mt-5 grid gap-4 md:grid-cols-2">
          {fields.map(([key, label]) => (
            <label key={key} className="text-sm font-medium">
              {label}
              <input className="field mt-1" value={draft[key]} onChange={(event) => setDraft({ ...draft, [key]: key === 'age' || key === 'currentWeight' ? Number(event.target.value) : event.target.value })} />
            </label>
          ))}
        </div>
        <Button className="mt-5" type="submit"><Save size={16} />Save Profile</Button>
      </form>
    </div>
  )
}
