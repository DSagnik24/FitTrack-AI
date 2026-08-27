import { useEffect, useState } from 'react'
import Button from '../common/Button.jsx'
import Modal from '../common/Modal.jsx'

const initialForm = {
  name: '',
  calories: '',
  protein: '',
  carbs: '',
  fat: '',
  mealId: 'breakfast',
}
const foodSuggestions = ['Dal', 'Rice', 'Roti', 'Paneer']

export default function AddFoodModal({ open, onClose, meals, selectedMeal, onSubmit }) {
  const [form, setForm] = useState({ ...initialForm, mealId: selectedMeal || 'breakfast' })

  useEffect(() => {
    if (open) setForm((current) => ({ ...current, mealId: selectedMeal || 'breakfast' }))
  }, [open, selectedMeal])

  const update = (field, value) => setForm((current) => ({ ...current, [field]: value }))
  const submit = (event) => {
    event.preventDefault()
    onSubmit(form.mealId, {
      name: form.name,
      calories: Number(form.calories),
      protein: Number(form.protein),
      carbs: Number(form.carbs),
      fat: Number(form.fat),
    })
    setForm({ ...initialForm, mealId: selectedMeal || 'breakfast' })
    onClose()
  }

  return (
    <Modal title="Add Food" open={open} onClose={onClose}>
      <form className="space-y-4" onSubmit={submit}>
        <div>
          <label className="text-sm font-medium" htmlFor="food-name">Food Name</label>
          <input id="food-name" required list="food-suggestions" className="field mt-1" value={form.name} onChange={(event) => update('name', event.target.value)} />
          <datalist id="food-suggestions">{foodSuggestions.map((food) => <option key={food} value={food} />)}</datalist>
        </div>
        <div className="grid gap-4 sm:grid-cols-4">
          {['calories', 'protein', 'carbs', 'fat'].map((field) => (
            <div key={field}>
              <label className="text-sm font-medium capitalize" htmlFor={field}>{field}</label>
              <input id={field} required min="0" type="number" className="field mt-1" value={form[field]} onChange={(event) => update(field, event.target.value)} />
            </div>
          ))}
        </div>
        <div>
          <label className="text-sm font-medium" htmlFor="meal-type">Meal Type</label>
          <select id="meal-type" className="field mt-1" value={form.mealId} onChange={(event) => update('mealId', event.target.value)}>
            {meals.map((meal) => <option key={meal.id} value={meal.id}>{meal.name}</option>)}
          </select>
        </div>
        <div className="flex justify-end gap-2">
          <Button type="button" variant="secondary" onClick={onClose}>Cancel</Button>
          <Button type="submit">Add Food</Button>
        </div>
      </form>
    </Modal>
  )
}
