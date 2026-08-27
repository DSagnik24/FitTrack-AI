import { Navigate, Route, Routes } from 'react-router-dom'
import { useFitness } from './context/FitnessContext.jsx'
import DashboardLayout from './components/layout/DashboardLayout.jsx'
import Dashboard from './pages/Dashboard.jsx'
import Workouts from './pages/Workouts.jsx'
import ActiveWorkout from './pages/ActiveWorkout.jsx'
import Nutrition from './pages/Nutrition.jsx'
import Progress from './pages/Progress.jsx'
import Goals from './pages/Goals.jsx'
import AICoach from './pages/AICoach.jsx'
import Profile from './pages/Profile.jsx'
import Settings from './pages/Settings.jsx'
import Onboarding from './pages/Onboarding.jsx'

function HomeRedirect() {
  const { user } = useFitness()
  return <Navigate to={user ? '/dashboard' : '/onboarding'} replace />
}

export default function App() {
  return (
    <Routes>
      <Route element={<DashboardLayout />}>
        <Route path="dashboard" element={<Dashboard />} />
        <Route path="workouts" element={<Workouts />} />
        <Route path="workouts/active" element={<ActiveWorkout />} />
        <Route path="nutrition" element={<Nutrition />} />
        <Route path="progress" element={<Progress />} />
        <Route path="goals" element={<Goals />} />
        <Route path="ai-coach" element={<AICoach />} />
        <Route path="profile" element={<Profile />} />
        <Route path="settings" element={<Settings />} />
      </Route>
      <Route path="onboarding" element={<Onboarding />} />
      <Route index element={<HomeRedirect />} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  )
}
