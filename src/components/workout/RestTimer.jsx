import { Pause, Play, RotateCcw, SkipForward } from 'lucide-react'
import { useEffect, useState } from 'react'
import Button from '../common/Button.jsx'

export default function RestTimer() {
  const [seconds, setSeconds] = useState(90)
  const [running, setRunning] = useState(true)

  useEffect(() => {
    if (!running || seconds <= 0) return undefined
    const timer = window.setInterval(() => setSeconds((value) => Math.max(value - 1, 0)), 1000)
    return () => window.clearInterval(timer)
  }, [running, seconds])

  const minutes = String(Math.floor(seconds / 60)).padStart(2, '0')
  const restSeconds = String(seconds % 60).padStart(2, '0')

  return (
    <aside className="sticky top-20 rounded-lg border border-slate-200 bg-white p-4 shadow-soft dark:border-neutral-800 dark:bg-neutral-900">
      <p className="text-xs font-bold uppercase tracking-wider text-mint">Rest Timer</p>
      <p className="mt-2 text-4xl font-semibold tabular-nums">{minutes}:{restSeconds}</p>
      <div className="mt-4 flex flex-wrap gap-2">
        <Button size="sm" variant="secondary" onClick={() => setRunning(!running)}>{running ? <Pause size={15} /> : <Play size={15} />}{running ? 'Pause' : 'Start'}</Button>
        <Button size="sm" variant="secondary" onClick={() => setSeconds(seconds + 30)}><RotateCcw size={15} />+30 sec</Button>
        <Button size="sm" variant="ghost" onClick={() => setSeconds(0)}><SkipForward size={15} />Skip</Button>
      </div>
    </aside>
  )
}
