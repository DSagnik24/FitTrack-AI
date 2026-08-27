import { Edit2, Eye, Play, Trash2 } from 'lucide-react'
import { useNavigate } from 'react-router-dom'
import Button from '../common/Button.jsx'

export default function WorkoutCard({ plan, onDelete }) {
  const navigate = useNavigate()
  const totalSets = plan.exercises.reduce((sum, item) => sum + Number(item.sets || 0), 0)

  return (
    <article className="panel p-5">
      <div className="flex items-start justify-between gap-3">
        <div>
          <h3 className="text-lg font-semibold">{plan.name}</h3>
          <p className="muted mt-1">{plan.description}</p>
        </div>
        <span className="rounded-md bg-mint/10 px-2.5 py-1 text-xs font-semibold text-mint">{plan.exercises.length} Exercises</span>
      </div>
      <p className="mt-4 text-sm font-medium">{totalSets} Sets</p>
      <div className="mt-5 grid grid-cols-2 gap-2">
        <Button variant="outline" size="sm"><Eye size={15} />View</Button>
        <Button variant="outline" size="sm"><Edit2 size={15} />Edit</Button>
        <Button size="sm" onClick={() => navigate('/workouts/active')}><Play size={15} />Start</Button>
        <Button variant="danger" size="sm" onClick={() => onDelete(plan.id)}><Trash2 size={15} />Delete</Button>
      </div>
    </article>
  )
}
