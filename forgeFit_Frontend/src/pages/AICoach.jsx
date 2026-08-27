import { Bot, Send } from 'lucide-react'
import { useState } from 'react'
import Button from '../components/common/Button.jsx'

const prompts = [
  "Why isn't my weight decreasing?",
  'What workout should I do today?',
  'How is my progress?',
  'Am I eating enough protein?',
  'Should I increase my weights?',
]

const mockResponse = (message) => {
  if (message.toLowerCase().includes('protein')) {
    return 'Based on your recent meals, you are averaging close to 120g protein per day. Your target is 150g, so add one high-protein snack or increase paneer, eggs, or chicken portions at lunch.'
  }
  if (message.toLowerCase().includes('weight')) {
    return 'Your AI Coach needs more data to provide personalized insights. Try logging workouts, measurements, nutrition, sleep, and daily activity.'
  }
  if (message.toLowerCase().includes('weights')) {
    return 'Increase load when you can complete every target set with clean reps and still have 1-2 reps in reserve. For bench press, a 2.5kg jump is a sensible next step.'
  }
  return 'Based on your last 30 days: workout consistency is excellent, average protein intake is improving, and training volume is trending up. Keep Push Day today and prioritize sleep tonight.'
}

export default function AICoach() {
  const [messages, setMessages] = useState([
    { id: 'welcome', role: 'ai', text: 'I can review your workouts, nutrition, recovery, and goals using your FitTrack AI data.', time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) },
  ])
  const [input, setInput] = useState('')
  const [typing, setTyping] = useState(false)

  const send = (text = input) => {
    if (!text.trim()) return
    const time = new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    setMessages((items) => [...items, { id: crypto.randomUUID(), role: 'user', text, time }])
    setInput('')
    setTyping(true)
    window.setTimeout(() => {
      setMessages((items) => [...items, { id: crypto.randomUUID(), role: 'ai', text: mockResponse(text), time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) }])
      setTyping(false)
    }, 700)
  }

  return (
    <div className="grid min-h-[calc(100vh-8rem)] gap-6 lg:grid-cols-[320px_1fr]">
      <aside className="panel p-5">
        <div className="flex items-center gap-3">
          <div className="rounded-lg bg-mint/10 p-2 text-mint"><Bot size={22} /></div>
          <div>
            <h2 className="text-lg font-semibold">FitTrack AI Coach</h2>
            <p className="muted">Your personal fitness assistant</p>
          </div>
        </div>
        <div className="mt-6 space-y-2">
          {prompts.map((prompt) => (
            <button key={prompt} className="w-full rounded-md border border-slate-200 p-3 text-left text-sm transition hover:border-mint hover:text-mint dark:border-neutral-800" onClick={() => send(prompt)}>
              {prompt}
            </button>
          ))}
        </div>
      </aside>
      <section className="panel flex min-h-[640px] flex-col overflow-hidden">
        <div className="flex-1 space-y-4 overflow-y-auto p-5">
          {messages.map((message) => (
            <div key={message.id} className={`flex ${message.role === 'user' ? 'justify-end' : 'justify-start'}`}>
              <div className={`max-w-[82%] rounded-lg p-3 ${message.role === 'user' ? 'bg-mint text-white' : 'bg-slate-100 dark:bg-neutral-800'}`}>
                <p className="text-sm leading-6">{message.text}</p>
                <p className={`mt-2 text-xs ${message.role === 'user' ? 'text-white/75' : 'text-slate-500 dark:text-neutral-400'}`}>{message.time}</p>
              </div>
            </div>
          ))}
          {typing && <div className="inline-flex rounded-lg bg-slate-100 px-4 py-3 text-sm dark:bg-neutral-800">Typing...</div>}
        </div>
        <form className="flex gap-2 border-t border-slate-200 p-4 dark:border-neutral-800" onSubmit={(event) => { event.preventDefault(); send() }}>
          <input className="field" placeholder="Ask about training, nutrition, or recovery" value={input} onChange={(event) => setInput(event.target.value)} />
          <Button type="submit" size="icon" aria-label="Send message"><Send size={18} /></Button>
        </form>
      </section>
    </div>
  )
}
