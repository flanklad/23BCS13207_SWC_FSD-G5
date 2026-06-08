import './ErrorState.css'

export default function ErrorState({ message, onRetry }) {
  return (
    <div className="error-wrap" role="alert">
      <div className="error-icon">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
          <circle cx="12" cy="12" r="10"/>
          <line x1="12" y1="8" x2="12" y2="12"/>
          <circle cx="12" cy="16" r="0.5" fill="currentColor"/>
        </svg>
      </div>
      <h2 className="error-title">Failed to load products</h2>
      <p className="error-msg">{message || 'An unexpected error occurred.'}</p>
      <p className="error-hint">
        Make sure the Spring Boot backend is running on <code>localhost:8080</code>.
      </p>
      <button className="retry-btn" onClick={onRetry}>
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
          <path d="M1 4v6h6"/><path d="M23 20v-6h-6"/>
          <path d="M20.49 9A9 9 0 0 0 5.64 5.64L1 10m22 4-4.64 4.36A9 9 0 0 1 3.51 15"/>
        </svg>
        Try again
      </button>
    </div>
  )
}
