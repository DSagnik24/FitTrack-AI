import { useState } from 'react'
import { Navigate, Outlet } from 'react-router-dom'
import ToastStack from '../common/ToastStack.jsx'
import Navbar from './Navbar.jsx'
import Sidebar from './Sidebar.jsx'
import { useFitness } from '../../context/FitnessContext.jsx'

export default function DashboardLayout() {
  const { user } = useFitness()
  const [collapsed, setCollapsed] = useState(false)
  const [mobileOpen, setMobileOpen] = useState(false)

  if (!user) return <Navigate to="/onboarding" replace />
  return (
    <div className="min-h-screen bg-slate-50 text-slate-950 dark:bg-neutral-950 dark:text-neutral-50">
      {mobileOpen && <button className="fixed inset-0 z-30 bg-slate-950/40 lg:hidden" aria-label="Close navigation" onClick={() => setMobileOpen(false)} />}
      <div className="flex min-h-screen">
        <Sidebar collapsed={collapsed} setCollapsed={setCollapsed} mobileOpen={mobileOpen} setMobileOpen={setMobileOpen} />
        <div className="min-w-0 flex-1">
          <Navbar setMobileOpen={setMobileOpen} />
          <main className="mx-auto w-full max-w-7xl px-4 py-6 md:px-6">
            <Outlet />
          </main>
        </div>
      </div>
      <ToastStack />
    </div>
  )
}
