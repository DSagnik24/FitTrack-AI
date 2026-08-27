import { Bot, TrendingDown } from 'lucide-react'
import { Area, AreaChart, CartesianGrid, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts'
import DailyProgress from '../components/dashboard/DailyProgress.jsx'
import QuickActions from '../components/dashboard/QuickActions.jsx'
import TodayWorkout from '../components/dashboard/TodayWorkout.jsx'
import { useFitness } from '../context/FitnessContext.jsx'

export default function Dashboard() {
  const { user, progress } = useFitness()
  const date = new Intl.DateTimeFormat('en-US', { weekday: 'long', month: 'long', day: 'numeric' }).format(new Date())

  return (
    <div className="space-y-6">
      <section className="flex flex-wrap items-end justify-between gap-4">
        <div>
          <p className="text-sm font-medium text-slate-500 dark:text-neutral-400">{date}</p>
          <h2 className="mt-2 text-3xl font-bold tracking-normal md:text-4xl">Good morning, {user.name}</h2>
          <p className="mt-2 text-slate-600 dark:text-neutral-300">Let's see how you're doing today.</p>
        </div>
        <QuickActions />
      </section>
      <DailyProgress />
      <div className="grid gap-6 xl:grid-cols-[1.05fr_0.95fr]">
        <TodayWorkout />
        <section className="panel p-5">
          <h3 className="text-lg font-semibold">Weekly Progress</h3>
          <p className="muted mt-1">Training volume is trending up this month.</p>
          <div className="mt-4 h-72">
            <ResponsiveContainer width="100%" height="100%">
              <AreaChart data={progress.weeklyVolume}>
                <defs>
                  <linearGradient id="volume" x1="0" x2="0" y1="0" y2="1">
                    <stop offset="0%" stopColor="#14b8a6" stopOpacity={0.35} />
                    <stop offset="100%" stopColor="#14b8a6" stopOpacity={0.02} />
                  </linearGradient>
                </defs>
                <CartesianGrid strokeDasharray="3 3" stroke="#e2e8f0" />
                <XAxis dataKey="week" />
                <YAxis />
                <Tooltip />
                <Area type="monotone" dataKey="volume" stroke="#14b8a6" fill="url(#volume)" strokeWidth={3} />
              </AreaChart>
            </ResponsiveContainer>
          </div>
        </section>
      </div>
      <div className="grid gap-6 lg:grid-cols-2">
        <section className="panel p-5">
          <div className="flex items-center justify-between">
            <div>
              <h3 className="text-lg font-semibold">Weight Progress</h3>
              <p className="muted mt-1">82 kg to 77.8 kg</p>
            </div>
            <TrendingDown className="text-mint" />
          </div>
          <div className="mt-4 h-64">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart data={progress.weightProgress}>
                <CartesianGrid strokeDasharray="3 3" stroke="#e2e8f0" />
                <XAxis dataKey="month" />
                <YAxis domain={['dataMin - 1', 'dataMax + 1']} />
                <Tooltip />
                <Line dataKey="weight" stroke="#111827" strokeWidth={3} dot={{ r: 4 }} />
              </LineChart>
            </ResponsiveContainer>
          </div>
        </section>
        <section className="panel p-5">
          <div className="flex items-center gap-3">
            <div className="rounded-lg bg-mint/10 p-2 text-mint"><Bot size={22} /></div>
            <div>
              <h3 className="text-lg font-semibold">AI Insight</h3>
              <p className="muted mt-1">Generated from recent trends</p>
            </div>
          </div>
          <p className="mt-5 text-lg leading-8 text-slate-700 dark:text-neutral-200">You're making steady progress. Water intake is close to target, strength is climbing, and your next easy win is adding 20g protein on training days.</p>
        </section>
      </div>
    </div>
  )
}
