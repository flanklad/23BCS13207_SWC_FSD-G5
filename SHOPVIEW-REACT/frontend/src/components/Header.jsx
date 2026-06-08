import './Header.css'

export default function Header({ query, onQuery, resultCount }) {
  return (
    <header className="header">
      <div className="header-inner">
        <div className="logo">
          <svg viewBox="0 0 24 24" fill="currentColor">
            <path d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
          </svg>
          <span>ShopView</span>
        </div>

        <div className="search-wrap">
          <svg className="search-icon" viewBox="0 0 24 24" fill="none"
            stroke="currentColor" strokeWidth="2">
            <circle cx="11" cy="11" r="8" /><path d="m21 21-4.35-4.35" />
          </svg>
          <input
            type="text"
            className="search-input"
            placeholder="Search products, categories…"
            value={query}
            onChange={e => onQuery(e.target.value)}
            aria-label="Search products"
          />
          {query && (
            <button className="clear-btn" onClick={() => onQuery('')} aria-label="Clear search">
              ✕
            </button>
          )}
        </div>

        <div className="header-meta">
          {resultCount !== null && (
            <span className="badge">{resultCount} items</span>
          )}
        </div>
      </div>
    </header>
  )
}
