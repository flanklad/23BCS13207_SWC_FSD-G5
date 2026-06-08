import { useState, useEffect, useCallback } from 'react'
import Header from './components/Header.jsx'
import ProductGrid from './components/ProductGrid.jsx'
import SkeletonGrid from './components/SkeletonGrid.jsx'
import ErrorState from './components/ErrorState.jsx'
import './App.css'

export default function App() {
  const [products, setProducts] = useState([])
  const [status, setStatus] = useState('idle') // idle | loading | success | error
  const [error, setError] = useState(null)
  const [query, setQuery] = useState('')
  const [category, setCategory] = useState('All')

  const fetchProducts = useCallback(async () => {
    setStatus('loading')
    setError(null)
    try {
      // Simulated 800ms network delay so the loading state is visible
      await new Promise(r => setTimeout(r, 800))
      const res = await fetch('/api/products')
      if (!res.ok) throw new Error(`Server responded with ${res.status}`)
      const data = await res.json()
      setProducts(data)
      setStatus('success')
    } catch (err) {
      setError(err.message)
      setStatus('error')
    }
  }, [])

  useEffect(() => {
    fetchProducts()
  }, [fetchProducts])

  const categories = ['All', ...new Set(products.map(p => p.category))]

  const visible = products.filter(p => {
    const matchesQuery = p.title.toLowerCase().includes(query.toLowerCase()) ||
                         p.category.toLowerCase().includes(query.toLowerCase())
    const matchesCategory = category === 'All' || p.category === category
    return matchesQuery && matchesCategory
  })

  return (
    <div className="app">
      <Header
        query={query}
        onQuery={setQuery}
        resultCount={status === 'success' ? visible.length : null}
      />

      <main className="main">
        {status === 'success' && (
          <div className="toolbar">
            <div className="categories">
              {categories.map(c => (
                <button
                  key={c}
                  className={`cat-btn ${category === c ? 'active' : ''}`}
                  onClick={() => setCategory(c)}
                >
                  {c}
                </button>
              ))}
            </div>
            <p className="result-count">
              {visible.length} result{visible.length !== 1 ? 's' : ''}
            </p>
          </div>
        )}

        {status === 'loading' && <SkeletonGrid />}
        {status === 'error'   && <ErrorState message={error} onRetry={fetchProducts} />}
        {status === 'success' && visible.length === 0 && (
          <div className="empty">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
              <circle cx="11" cy="11" r="8"/><path d="m21 21-4.35-4.35"/>
            </svg>
            <p>No products match <strong>"{query}"</strong></p>
            <button className="btn-link" onClick={() => { setQuery(''); setCategory('All') }}>
              Clear filters
            </button>
          </div>
        )}
        {status === 'success' && visible.length > 0 && (
          <ProductGrid products={visible} />
        )}
      </main>
    </div>
  )
}
