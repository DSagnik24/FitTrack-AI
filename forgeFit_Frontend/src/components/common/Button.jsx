export default function Button({ children, variant = 'primary', size = 'md', className = '', ...props }) {
  const variants = {
    primary: 'bg-mint text-white hover:bg-teal-600 focus:ring-mint/30',
    secondary: 'bg-slate-100 text-slate-900 hover:bg-slate-200 dark:bg-neutral-800 dark:text-neutral-100 dark:hover:bg-neutral-700',
    ghost: 'bg-transparent text-slate-600 hover:bg-slate-100 dark:text-neutral-300 dark:hover:bg-neutral-800',
    danger: 'bg-coral text-white hover:bg-red-600 focus:ring-red-300',
    outline: 'border border-slate-200 bg-white text-slate-700 hover:border-mint hover:text-mint dark:border-neutral-700 dark:bg-neutral-900 dark:text-neutral-200',
  }
  const sizes = {
    sm: 'h-8 px-3 text-xs',
    md: 'h-10 px-4 text-sm',
    lg: 'h-11 px-5 text-sm',
    icon: 'h-10 w-10 p-0',
  }

  return (
    <button
      type={props.type || 'button'}
      className={`inline-flex items-center justify-center gap-2 rounded-md font-medium transition focus:outline-none focus:ring-2 disabled:cursor-not-allowed disabled:opacity-50 ${variants[variant]} ${sizes[size]} ${className}`}
      {...props}
    >
      {children}
    </button>
  )
}
