import { Plus } from 'lucide-react'
import { useState } from 'react'
import AddFoodModal from '../components/nutrition/AddFoodModal.jsx'
import MacroCard from '../components/nutrition/MacroCard.jsx'
import MealCard from '../components/nutrition/MealCard.jsx'
import Button from '../components/common/Button.jsx'
import { useFitness } from '../context/FitnessContext.jsx'

export default function Nutrition() {
  const { nutrition, nutritionTotals, goals, addFood } = useFitness()
  const [modalOpen, setModalOpen] = useState(false)
  const [selectedMeal, setSelectedMeal] = useState('breakfast')
  const caloriesPercent = goals?.targetCalories ? Math.min(100, (nutritionTotals.calories / goals.targetCalories) * 100) : 0

  const openAddFood = (mealId = 'breakfast') => {
    setSelectedMeal(mealId)
    setModalOpen(true)
  }

  return (
    <div className="space-y-6">
      <div className="flex flex-wrap items-center justify-between gap-3">
        <div>
          <h2 className="text-2xl font-bold">Nutrition Tracking</h2>
          <p className="muted mt-1">Daily calories, macros, and meals.</p>
        </div>
        <Button onClick={() => openAddFood()}><Plus size={16} />Add Meal</Button>
      </div>
      <div className="grid gap-6 lg:grid-cols-[340px_1fr]">
        <section className="panel flex flex-col items-center justify-center p-6 text-center">
          <div className="relative grid h-52 w-52 place-items-center rounded-full" style={{ background: `conic-gradient(#14b8a6 ${caloriesPercent}%, #e2e8f0 0)` }}>
            <div className="grid h-40 w-40 place-items-center rounded-full bg-white dark:bg-neutral-900">
              <div>
                <p className="text-4xl font-bold">{nutritionTotals.calories.toLocaleString()}</p>
                <p className="muted">{goals?.targetCalories ? `of ${goals.targetCalories} kcal` : 'Set a calorie goal'}</p>
              </div>
            </div>
          </div>
          <p className="mt-4 text-sm font-semibold">{Math.round(caloriesPercent)}% of daily target</p>
        </section>
        <section className="panel p-5">
          <h3 className="text-lg font-semibold">Macronutrients</h3>
          <div className="mt-4 grid gap-3">
            <MacroCard label="Protein" current={nutritionTotals.protein} goal={goals?.proteinGoal} color="bg-amber" />
            <MacroCard label="Carbohydrates" current={nutritionTotals.carbs} goal={null} color="bg-mint" />
            <MacroCard label="Fats" current={nutritionTotals.fat} goal={null} color="bg-coral" />
          </div>
        </section>
      </div>
      <div className="grid gap-5 md:grid-cols-2">
        {nutrition.meals.map((meal) => <MealCard key={meal.id} meal={meal} onAdd={openAddFood} />)}
      </div>
      <AddFoodModal open={modalOpen} selectedMeal={selectedMeal} meals={nutrition.meals} onClose={() => setModalOpen(false)} onSubmit={addFood} />
    </div>
  )
}
