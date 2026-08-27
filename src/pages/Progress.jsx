import { Award } from 'lucide-react'
import { useState } from 'react'
import { Bar, BarChart, CartesianGrid, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts'
import ProgressBar from '../components/common/ProgressBar.jsx'
import { useFitness } from '../context/FitnessContext.jsx'
import { personalRecords } from '../data/mockProgress.js'

export default function Progress() {
  const { progress } = useFitness()
  const exercises = Object.keys(progress.strengthProgress)
  const [exercise, setExercise] = useState(exercises[0])

  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-2xl font-bold">Progress Analytics</h2>
        <p className="muted mt-1">Weight, strength, consistency, and personal records.</p>
      </div>
      <div className="grid gap-6 lg:grid-cols-2">
        <section className="panel p-5">
          <h3 className="text-lg font-semibold">Weight Progress</h3>
          <p className="muted mt-1">82 kg to 77.8 kg over six months.</p>
          <div className="mt-4 h-72"><ResponsiveContainer><LineChart data={progress.weightProgress}><CartesianGrid strokeDasharray="3 3" /><XAxis dataKey="month" /><YAxis domain={['dataMin - 1', 'dataMax + 1']} /><Tooltip /><Line dataKey="weight" stroke="#14b8a6" strokeWidth={3} /></LineChart></ResponsiveContainer></div>
        </section>
        <section className="panel p-5">
          <div className="flex flex-wrap items-center justify-between gap-3">
            <h3 className="text-lg font-semibold">Strength Progress</h3>
            <select className="field w-auto" value={exercise} onChange={(event) => setExercise(event.target.value)}>{exercises.map((name) => <option key={name}>{name}</option>)}</select>
          </div>
          <div className="mt-4 h-72"><ResponsiveContainer><LineChart data={progress.strengthProgress[exercise]}><CartesianGrid strokeDasharray="3 3" /><XAxis dataKey="month" /><YAxis /><Tooltip /><Line dataKey="weight" stroke="#111827" strokeWidth={3} /></LineChart></ResponsiveContainer></div>
        </section>
        <section className="panel p-5">
          <h3 className="text-lg font-semibold">Weekly Training Volume</h3>
          <div className="mt-4 h-72"><ResponsiveContainer><BarChart data={progress.weeklyVolume}><CartesianGrid strokeDasharray="3 3" /><XAxis dataKey="week" /><YAxis /><Tooltip /><Bar dataKey="volume" fill="#14b8a6" radius={[6, 6, 0, 0]} /></BarChart></ResponsiveContainer></div>
        </section>
        <section className="panel p-5">
          <h3 className="text-lg font-semibold">Workout Consistency</h3>
          <p className="mt-3 text-3xl font-bold">4 / 5</p>
          <p className="muted mt-1">Workouts completed this week</p>
          <ProgressBar value={80} className="mt-5" />
          <p className="mt-3 text-sm font-semibold">80%</p>
        </section>
      </div>
      <section>
        <h3 className="text-lg font-semibold">Personal Records</h3>
        <div className="mt-4 grid gap-4 sm:grid-cols-2 xl:grid-cols-4">
          {personalRecords.map((record) => (
            <div key={record.lift} className="panel p-5">
              <Award className="text-amber" />
              <p className="mt-3 font-semibold">{record.lift}</p>
              <p className="mt-2 text-3xl font-bold">{record.value}</p>
              <p className="muted mt-1">{record.label}</p>
            </div>
          ))}
        </div>
      </section>
    </div>
  )
}
