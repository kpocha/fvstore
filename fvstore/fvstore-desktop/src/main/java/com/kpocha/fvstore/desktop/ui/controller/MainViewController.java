package com.kpocha.fvstore.desktop.ui.controller;

import com.kpocha.fvstore.core.model.Producto;
import com.kpocha.fvstore.core.service.ProductoService; // Para uso directo del core

// Importaciones JavaFX
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
// import javafx.event.ActionEvent; // No es necesario si el método de acción no lo usa explícitamente

import java.util.List;

// Asumiendo que esta aplicación podría tener su propio servicio para llamar a la API
// import com.kpocha.fvstore.desktop.service.ApiClientService;

public class MainViewController {

    @FXML
    private Label statusLabel;

    @FXML
    private Button loadProductsButton;

    @FXML
    private ListView<String> productListView; // Mostrará nombres de productos por simplicidad

    @FXML
    private TextArea logTextArea;

    // Instancia de ProductoService (para demo, en una app real podría ser inyectado o gestionado de otra forma)
    private ProductoService productoService;

    // Para conectar a la API (descomentar si se usa)
    // private ApiClientService apiClientService;

    private final ObservableList<String> productNames = FXCollections.observableArrayList();

    public MainViewController() {
        // Inicializar servicios aquí o en initialize()
        // Esto es una instanciación directa, podría ser más complejo en una app real
        this.productoService = new ProductoService();
        // this.apiClientService = new ApiClientService(); // Si se usa un servicio de cliente API
    }

    @FXML
    public void initialize() {
        productListView.setItems(productNames);
        logTextArea.appendText("Controlador de vista inicializado.\n");
        statusLabel.setText("Listo. Presione el botón para cargar productos.");

        // Cargar algunos productos de ejemplo del core al iniciar (opcional)
        // loadProductsFromCore();
    }

    @FXML
    private void handleLoadProducts() {
        logTextArea.appendText("Intentando cargar productos...\n");
        statusLabel.setText("Cargando productos...");
        productNames.clear(); // Limpiar lista antes de cargar

        // Opción 1: Cargar desde el módulo Core directamente
        loadProductsFromCore();

        // Opción 2: Cargar desde la API (requeriría ApiClientService y su implementación)
        // loadProductsFromApi();
    }

    private void loadProductsFromCore() {
        try {
            // Simulación de carga de productos desde el core
            // Añadir un producto de ejemplo si la lista está vacía para demostración
            if (productoService.obtenerTodosLosProductos().isEmpty()) {
                 productoService.crearProducto(new Producto(null, "Producto Core FX 1", "Demo", 10.99));
                 productoService.crearProducto(new Producto(null, "Producto Core FX 2", "Demo", 12.99));
            }

            List<Producto> productos = productoService.obtenerTodosLosProductos();
            if (productos.isEmpty()) {
                logTextArea.appendText("No se encontraron productos en el core.\n");
                statusLabel.setText("No hay productos.");
            } else {
                productos.forEach(p -> productNames.add(p.getNombre() + " (Precio: " + p.getPrecio() + ")"));
                logTextArea.appendText(productos.size() + " productos cargados desde el core.\n");
                statusLabel.setText("Productos cargados desde el core.");
            }
        } catch (Exception e) {
            logTextArea.appendText("Error al cargar productos desde el core: " + e.getMessage() + "\n");
            e.printStackTrace(); // Imprimir stack trace para depuración
            statusLabel.setText("Error al cargar productos.");
        }
    }

    /*
    private void loadProductsFromApi() {
        // Esto es un placeholder para la lógica de llamar a la API
        // Debería ser asíncrono para no bloquear la UI
        statusLabel.setText("Cargando desde API...");
        logTextArea.appendText("Iniciando carga desde API...\n");

        // Ejemplo (requiere ApiClientService y que devuelva List<Producto> o similar):
        // apiClientService.fetchProductos(
        //     (productos) -> { // Callback de éxito
        //         javafx.application.Platform.runLater(() -> {
        //             if (productos.isEmpty()) {
        //                 logTextArea.appendText("No se encontraron productos en la API.\n");
        //                 statusLabel.setText("No hay productos (API).");
        //             } else {
        //                 productos.forEach(p -> productNames.add(p.getNombre() + " (API)"));
        //                 logTextArea.appendText(productos.size() + " productos cargados desde la API.\n");
        //                 statusLabel.setText("Productos cargados (API).");
        //             }
        //         });
        //     },
        //     (exception) -> { // Callback de error
        //         javafx.application.Platform.runLater(() -> {
        //             logTextArea.appendText("Error al cargar productos desde API: " + exception.getMessage() + "\n");
        //             exception.printStackTrace();
        //             statusLabel.setText("Error al cargar desde API.");
        //         });
        //     }
        // );
        logTextArea.appendText("Llamada a API (simulada) completada.\n"); // Quitar si es asíncrono real
        statusLabel.setText("Carga API (simulada) finalizada."); // Quitar si es asíncrono real
    }
    */
}
