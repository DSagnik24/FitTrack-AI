const prefix = 'fittrack-'

export const readStorage = (key, fallback) => {
  try {
    const value = localStorage.getItem(`${prefix}${key}`)
    return value ? JSON.parse(value) : fallback
  } catch {
    return fallback
  }
}

export const writeStorage = (key, value) => {
  localStorage.setItem(`${prefix}${key}`, JSON.stringify(value))
}