package com.kpocha.fvstore.core.service;

import com.kpocha.fvstore.core.model.Producto;
import java.util.List;
import java.util.ArrayList;

// Placeholder class
public class ProductoService {

    // Simulación de un repositorio o DAO en memoria
    private final List<Producto> productos = new ArrayList<>();
    private Long nextId = 1L;

    public Producto crearProducto(Producto producto) {
        if (producto.getId() == null) {
            producto.setId(nextId++);
        }
        productos.add(producto);
        return producto;
    }

    public Producto obtenerProductoPorId(Long id) {
        for (Producto p : productos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public List<Producto> obtenerTodosLosProductos() {
        return new ArrayList<>(productos);
    }

    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        Producto existente = obtenerProductoPorId(id);
        if (existente != null) {
            existente.setNombre(productoActualizado.getNombre());
            existente.setDescripcion(productoActualizado.getDescripcion());
            existente.setPrecio(productoActualizado.getPrecio());
            return existente;
        }
        return null; // O lanzar excepción
    }

    public boolean eliminarProducto(Long id) {
        Producto productoAEliminar = obtenerProductoPorId(id);
        if (productoAEliminar != null) {
            return productos.remove(productoAEliminar);
        }
        return false;
    }
}
