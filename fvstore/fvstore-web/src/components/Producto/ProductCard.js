import React from 'react';
// import './ProductCard.css'; // Opcional: si necesitas estilos específicos para la tarjeta

const ProductCard = ({ product }) => {
  if (!product) {
    return null; // O un placeholder si un producto es inválido pero se intenta renderizar
  }

  return (
    <div className="product-card" style={{
        border: '1px solid #ddd',
        margin: '10px', // Espacio alrededor de la tarjeta
        padding: '15px',
        borderRadius: '8px',
        width: '250px', // Ancho fijo para cada tarjeta
        boxShadow: '0 2px 4px rgba(0,0,0,0.1)' // Sombra sutil
    }}>
      <h4>{product.nombre || "Nombre no disponible"}</h4>
      <p>{product.descripcion || "Descripción no disponible."}</p>
      <p><strong>Precio:</strong> ${product.precio !== undefined && product.precio !== null ? product.precio.toFixed(2) : "N/A"}</p>
      {/* Podrías añadir más detalles o botones aquí, como "Añadir al carrito" */}
    </div>
  );
};

export default ProductCard;
