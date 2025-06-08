import axios from 'axios';

// La URL base se manejará con el proxy en desarrollo (definido en package.json -> "proxy": "http://localhost:8080")
// En producción, esto podría ser una URL absoluta o configurada a través de variables de entorno.
const API_URL = '/api/v1/productos'; // Ruta relativa gracias al proxy

const getAllProductos = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error fetching productos:', error.response ? error.response.data : error.message);
    // Lanzar el error para que el componente que llama pueda manejarlo (e.g., mostrar un mensaje al usuario)
    throw error;
  }
};

const getProductoById = async (id) => {
  if (!id) {
    throw new Error("El ID del producto es requerido.");
  }
  try {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching producto con id ${id}:`, error.response ? error.response.data : error.message);
    throw error;
  }
};

const createProducto = async (productoData) => {
  if (!productoData) {
    throw new Error("Los datos del producto son requeridos.");
  }
  try {
    const response = await axios.post(API_URL, productoData);
    return response.data;
  } catch (error) {
    console.error('Error creating producto:', error.response ? error.response.data : error.message);
    throw error;
  }
};

// Aquí podrías añadir más funciones como:
// const updateProducto = async (id, productoData) => { ... };
// const deleteProducto = async (id) => { ... };

const productoService = {
    getAllProductos,
    getProductoById,
    createProducto
    // updateProducto,
    // deleteProducto
};

export default productoService;
