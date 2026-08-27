import { Plus } from 'lucide-react'
import Button from '../common/Button.jsx'

export default function MealCard({ meal, onAdd }) {
  const calories = meal.foods.reduce((sum, food) => sum + Number(food.calories || 0), 0)

  return (
    <section className="panel p-5">
      <div className="flex items-start justify-between gap-4">
        <div>
          <h3 className="text-lg font-semibold">{meal.name}</h3>
          <p className="muted mt-1">{calories} kcal</p>
        </div>
        <Button size="sm" variant="secondary" onClick={() => onAdd(meal.id)}><Plus size={15} />Add Food</Button>
      </div>
      <div className="mt-4 space-y-2">
        {meal.foods.map((food) => (
          <div key={food.id} className="flex items-center justify-between rounded-md bg-slate-50 px-3 py-2 dark:bg-neutral-950">
            <span className="text-sm font-medium">{food.name}</span>
            <span className="text-sm text-slate-500 dark:text-neutral-400">{food.calories} kcal</span>
          </div>
        ))}
      </div>
    </section>
  )
}
