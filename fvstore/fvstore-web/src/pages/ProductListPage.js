import React, { useState, useEffect } from 'react';
import productoService from '../services/productoService';
import ProductCard from '../components/Producto/ProductCard';
// import './ProductListPage.css'; // Opcional: si necesitas estilos específicos

function ProductListPage() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true);
        const data = await productoService.getAllProductos();
        setProducts(data);
        setError(null);
      } catch (err) {
        setError(err.message || 'Error al cargar productos');
        // Considerar si el backend devuelve un array vacío en 404 o error específico
        // y si eso debe ser tratado como un error o simplemente "no hay productos".
        // Por ahora, si hay error, se muestra mensaje de error y no se muestran productos.
        setProducts([]);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  if (loading) return <p>Cargando productos...</p>;
  // Mostrar el error de forma más prominente si existe
  if (error) return <p style={{ color: 'red' }}>Error: {error}</p>;

  return (
    <div>
      <h2>Lista de Productos</h2>
      {products.length === 0 ? (
        <p>No hay productos disponibles en este momento.</p>
      ) : (
        <div className="product-list"> {/* Usando la clase de App.css */}
          {products.map(product => (
            <ProductCard key={product.id} product={product} />
          ))}
        </div>
      )}
    </div>
  );
}

export default ProductListPage;
