import { CheckCircle2 } from 'lucide-react'
import { useEffect, useMemo, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import Button from '../components/common/Button.jsx'
import Modal from '../components/common/Modal.jsx'
import ExerciseCard from '../components/workout/ExerciseCard.jsx'
import RestTimer from '../components/workout/RestTimer.jsx'
import { useFitness } from '../context/FitnessContext.jsx'

export default function ActiveWorkout() {
  const { workouts } = useFitness()
  const navigate = useNavigate()
  const workout = workouts[0]
  const [elapsed, setElapsed] = useState(0)
  const [complete, setComplete] = useState(false)
  const totalSets = useMemo(() => workout.exercises.reduce((sum, exercise) => sum + exercise.sets, 0), [workout])

  useEffect(() => {
    const timer = window.setInterval(() => setElapsed((value) => value + 1), 1000)
    return () => window.clearInterval(timer)
  }, [])

  const elapsedText = `${String(Math.floor(elapsed / 60)).padStart(2, '0')}:${String(elapsed % 60).padStart(2, '0')}`

  return (
    <div className="space-y-6">
      <div className="flex flex-wrap items-center justify-between gap-3">
        <div>
          <h2 className="text-3xl font-bold">{workout.name}</h2>
          <p className="muted mt-1">Elapsed Time: {elapsedText}</p>
        </div>
        <Button onClick={() => setComplete(true)}><CheckCircle2 size={16} />Finish Workout</Button>
      </div>
      <div className="grid gap-6 xl:grid-cols-[1fr_300px]">
        <div className="space-y-5">{workout.exercises.map((exercise) => <ExerciseCard key={exercise.id} exercise={exercise} />)}</div>
        <RestTimer />
      </div>
      <section className="panel p-5">
        <h3 className="text-lg font-semibold">Workout Summary</h3>
        <div className="mt-4 grid gap-3 sm:grid-cols-4">
          <p className="rounded-lg bg-slate-50 p-3 dark:bg-neutral-950">Duration<br /><strong>1h 08m</strong></p>
          <p className="rounded-lg bg-slate-50 p-3 dark:bg-neutral-950">Exercises<br /><strong>{workout.exercises.length}</strong></p>
          <p className="rounded-lg bg-slate-50 p-3 dark:bg-neutral-950">Sets<br /><strong>{totalSets}</strong></p>
          <p className="rounded-lg bg-slate-50 p-3 dark:bg-neutral-950">Volume<br /><strong>12,450 kg</strong></p>
        </div>
      </section>
      <Modal title="Workout Complete!" open={complete} onClose={() => setComplete(false)}>
        <div className="space-y-5 text-center">
          <CheckCircle2 className="mx-auto text-mint" size={52} />
          <div>
            <h3 className="text-2xl font-bold">Great job!</h3>
            <p className="muted mt-1">Total Volume</p>
            <p className="mt-2 text-3xl font-semibold">12,450 kg</p>
          </div>
          <div className="rounded-lg bg-amber/10 p-4 text-amber">Bench Press +5kg personal record</div>
          <Button onClick={() => navigate('/')}>Back to Dashboard</Button>
        </div>
      </Modal>
    </div>
  )
}
