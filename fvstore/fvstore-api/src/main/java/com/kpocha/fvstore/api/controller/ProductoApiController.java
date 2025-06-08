package com.kpocha.fvstore.api.controller;

import com.kpocha.fvstore.core.model.Producto;
import com.kpocha.fvstore.core.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// import java.util.Optional; // No longer needed due to service method change

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "API para la gestión de productos")
public class ProductoApiController {

    private final ProductoService productoService;

    @Autowired
    public ProductoApiController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos")
    public List<Producto> getAllProductos() {
        return productoService.obtenerTodosLosProductos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por su ID")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.obtenerProductoPorId(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto")
    public Producto createProducto(@RequestBody Producto producto) {
        return productoService.crearProducto(producto);
    }
}
