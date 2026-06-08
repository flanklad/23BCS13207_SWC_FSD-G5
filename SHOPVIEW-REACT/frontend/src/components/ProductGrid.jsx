import ProductCard from './ProductCard.jsx'
import './ProductGrid.css'

export default function ProductGrid({ products }) {
  return (
    <ul className="product-grid" role="list">
      {products.map(p => (
        <li key={p.id}>
          <ProductCard product={p} />
        </li>
      ))}
    </ul>
  )
}
