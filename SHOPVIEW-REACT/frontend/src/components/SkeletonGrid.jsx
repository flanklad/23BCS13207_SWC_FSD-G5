import './SkeletonGrid.css'

function SkeletonCard() {
  return (
    <div className="sk-card" aria-hidden="true">
      <div className="sk-img shimmer" />
      <div className="sk-body">
        <div className="sk-badge shimmer" />
        <div className="sk-line shimmer" style={{ width: '90%' }} />
        <div className="sk-line shimmer" style={{ width: '70%' }} />
        <div className="sk-line shimmer" style={{ width: '55%', height: '12px' }} />
        <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: '8px' }}>
          <div className="sk-line shimmer" style={{ width: '30%', height: '22px' }} />
          <div className="sk-line shimmer" style={{ width: '25%', height: '14px' }} />
        </div>
        <div className="sk-btn shimmer" />
      </div>
    </div>
  )
}

export default function SkeletonGrid() {
  return (
    <div role="status" aria-label="Loading products…">
      <p className="sk-status">Loading products…</p>
      <ul className="sk-grid" aria-hidden="true">
        {Array.from({ length: 8 }).map((_, i) => (
          <li key={i}><SkeletonCard /></li>
        ))}
      </ul>
    </div>
  )
}
