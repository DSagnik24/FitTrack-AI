export const workoutPlans = [
  {
    id: 'push-day',
    name: 'Push Day',
    description: 'Chest, shoulders, and triceps with progressive overload.',
    exercises: [
      { id: 'bench-press', name: 'Bench Press', sets: 3, targetReps: '8-10', rest: 90, previous: [{ weight: 65, reps: 8 }, { weight: 65, reps: 8 }, { weight: 60, reps: 10 }] },
      { id: 'incline-db-press', name: 'Incline Dumbbell Press', sets: 4, targetReps: '10-12', rest: 75, previous: [{ weight: 24, reps: 10 }, { weight: 24, reps: 10 }, { weight: 22, reps: 12 }] },
      { id: 'shoulder-press', name: 'Shoulder Press', sets: 4, targetReps: '8-10', rest: 90, previous: [{ weight: 40, reps: 8 }, { weight: 37.5, reps: 9 }] },
      { id: 'lateral-raises', name: 'Lateral Raises', sets: 4, targetReps: '12-15', rest: 45, previous: [{ weight: 10, reps: 15 }, { weight: 10, reps: 14 }] },
      { id: 'tricep-pushdowns', name: 'Tricep Pushdowns', sets: 3, targetReps: '12-15', rest: 60, previous: [{ weight: 30, reps: 12 }, { weight: 30, reps: 12 }] },
    ],
  },
  {
    id: 'pull-day',
    name: 'Pull Day',
    description: 'Back, biceps, and rear delts.',
    exercises: [
      { id: 'deadlift', name: 'Deadlift', sets: 3, targetReps: '5', rest: 150, previous: [{ weight: 120, reps: 5 }] },
      { id: 'lat-pulldown', name: 'Lat Pulldown', sets: 4, targetReps: '10-12', rest: 75, previous: [{ weight: 60, reps: 10 }] },
      { id: 'barbell-row', name: 'Barbell Row', sets: 4, targetReps: '8-10', rest: 90, previous: [{ weight: 70, reps: 8 }] },
      { id: 'face-pulls', name: 'Face Pulls', sets: 3, targetReps: '15', rest: 45, previous: [{ weight: 25, reps: 15 }] },
      { id: 'db-curls', name: 'Dumbbell Curls', sets: 3, targetReps: '10-12', rest: 60, previous: [{ weight: 14, reps: 10 }] },
      { id: 'hammer-curls', name: 'Hammer Curls', sets: 3, targetReps: '12', rest: 60, previous: [{ weight: 12, reps: 12 }] },
    ],
  },
  {
    id: 'leg-day',
    name: 'Leg Day',
    description: 'Lower body strength and hypertrophy.',
    exercises: [
      { id: 'squat', name: 'Back Squat', sets: 4, targetReps: '6-8', rest: 120, previous: [{ weight: 95, reps: 6 }] },
      { id: 'leg-press', name: 'Leg Press', sets: 4, targetReps: '10-12', rest: 90, previous: [{ weight: 180, reps: 10 }] },
      { id: 'rdl', name: 'Romanian Deadlift', sets: 3, targetReps: '8-10', rest: 90, previous: [{ weight: 80, reps: 8 }] },
      { id: 'lunges', name: 'Walking Lunges', sets: 3, targetReps: '12 each', rest: 75, previous: [{ weight: 20, reps: 12 }] },
      { id: 'leg-curl', name: 'Leg Curl', sets: 3, targetReps: '12-15', rest: 60, previous: [{ weight: 45, reps: 12 }] },
      { id: 'calf-raise', name: 'Calf Raises', sets: 3, targetReps: '15-20', rest: 45, previous: [{ weight: 70, reps: 16 }] },
      { id: 'plank', name: 'Plank', sets: 2, targetReps: '60 sec', rest: 45, previous: [{ weight: 0, reps: 60 }] },
    ],
  },
]
