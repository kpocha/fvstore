package com.kpocha.fvstore.core.service;

import com.kpocha.fvstore.core.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

// Placeholder test class
public class ProductoServiceTest {

    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        productoService = new ProductoService();
        // Datos de prueba iniciales
        productoService.crearProducto(new Producto(null, "Laptop", "Laptop de alto rendimiento", 1200.00));
        productoService.crearProducto(new Producto(null, "Mouse", "Mouse óptico inalámbrico", 25.00));
    }

    @Test
    void testCrearProducto() {
        Producto nuevoProducto = new Producto(null, "Teclado", "Teclado mecánico RGB", 75.00);
        Producto productoCreado = productoService.crearProducto(nuevoProducto);

        assertNotNull(productoCreado);
        assertNotNull(productoCreado.getId());
        assertEquals("Teclado", productoCreado.getNombre());
        assertEquals(3, productoService.obtenerTodosLosProductos().size()); // 2 iniciales + 1 nuevo
    }

    @Test
    void testObtenerProductoPorId() {
        Producto producto = productoService.obtenerProductoPorId(1L); // Asumiendo que el primer producto tiene ID 1
        assertNotNull(producto);
        assertEquals("Laptop", producto.getNombre());

        Producto noExistente = productoService.obtenerProductoPorId(99L);
        assertNull(noExistente);
    }

    @Test
    void testObtenerTodosLosProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        assertNotNull(productos);
        assertEquals(2, productos.size());
    }

    @Test
    void testActualizarProducto() {
        Producto datosActualizados = new Producto(null, "Laptop Gamer", "Laptop potente para juegos", 1500.00);
        Producto productoActualizado = productoService.actualizarProducto(1L, datosActualizados); // ID 1 es Laptop

        assertNotNull(productoActualizado);
        assertEquals("Laptop Gamer", productoActualizado.getNombre());
        assertEquals(1500.00, productoActualizado.getPrecio());

        Producto noExistente = productoService.actualizarProducto(99L, datosActualizados);
        assertNull(noExistente);
    }

    @Test
    void testEliminarProducto() {
        boolean eliminado = productoService.eliminarProducto(2L); // ID 2 es Mouse
        assertTrue(eliminado);
        assertEquals(1, productoService.obtenerTodosLosProductos().size());
        assertNull(productoService.obtenerProductoPorId(2L));

        boolean noEliminado = productoService.eliminarProducto(99L);
        assertFalse(noEliminado);
    }
}
