import { useState } from 'react'
import './ProductCard.css'

function Stars({ rating }) {
  return (
    <span className="stars" aria-label={`Rating: ${rating} out of 5`}>
      {[1, 2, 3, 4, 5].map(n => (
        <svg key={n} viewBox="0 0 20 20"
          className={n <= Math.round(rating) ? 'star filled' : 'star'}>
          <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
        </svg>
      ))}
      <span className="rating-num">{rating.toFixed(1)}</span>
    </span>
  )
}

export default function ProductCard({ product }) {
  const [imgError, setImgError] = useState(false)
  const inStock = product.stock > 0
  const lowStock = product.stock > 0 && product.stock <= 30

  return (
    <article className="card">
      <div className="card-img-wrap">
        {imgError ? (
          <div className="img-fallback">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
              <rect x="3" y="3" width="18" height="18" rx="2"/>
              <circle cx="8.5" cy="8.5" r="1.5"/>
              <path d="m21 15-5-5L5 21"/>
            </svg>
          </div>
        ) : (
          <img
            src={product.thumbnail}
            alt={product.title}
            className="card-img"
            loading="lazy"
            onError={() => setImgError(true)}
          />
        )}
        <span className="category-badge">{product.category}</span>
      </div>

      <div className="card-body">
        <h3 className="card-title">{product.title}</h3>
        <p className="card-desc">{product.description}</p>

        <Stars rating={product.rating} />

        <div className="card-footer">
          <span className="price">${product.price.toFixed(2)}</span>
          <span className={`stock-label ${!inStock ? 'out' : lowStock ? 'low' : ''}`}>
            {!inStock ? 'Out of stock' : lowStock ? `Only ${product.stock} left` : 'In stock'}
          </span>
        </div>

        <button className="add-btn" disabled={!inStock}>
          {inStock ? 'Add to Cart' : 'Unavailable'}
        </button>
      </div>
    </article>
  )
}
