package com.kpocha.fvstore.desktop.app;

// Importaciones para el core (si se usan directamente)
// import com.kpocha.fvstore.core.model.Producto;
// import com.kpocha.fvstore.core.service.ProductoService;

// Importaciones para Swing
import javax.swing.*;
import java.awt.*;

// Importaciones para JavaFX (descomentar si se usa JavaFX)
/*
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;
*/

// Para JavaFX, la clase debería extender Application: public class MainApp extends Application
public class MainApp {

    // Ejemplo de cómo se podría usar la lógica del core directamente.
    // Esto es solo ilustrativo; en una aplicación real, la interacción con
    // el core o la API sería más estructurada, posiblemente a través de una clase de servicio.
    /*
    private void usarCoreLogicDirectamente() {
        System.out.println("Intentando usar lógica del core directamente...");
        // Nota: ProductoService probablemente necesitaría ser un bean o tener una forma de ser instanciado/inyectado.
        // Para este ejemplo básico, asumimos que se puede instanciar directamente si no tiene dependencias complejas.
        ProductoService productoService = new ProductoService();

        // Crear un nuevo producto
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre("Producto Desktop");
        nuevoProducto.setDescripcion("Creado desde la aplicación de escritorio");
        nuevoProducto.setPrecio(29.99);
        // productoService.crearProducto(nuevoProducto); // Asumiendo que crearProducto existe y maneja el ID

        System.out.println("Productos existentes (desde el core):");
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos para mostrar.");
        } else {
            for (Producto p : productos) {
                System.out.println("- " + p.getNombre() + ": " + p.getDescripcion() + " (" + p.getPrecio() + ")");
            }
        }
    }
    */

    // Método para crear y mostrar la GUI de Swing
    private static void createAndShowGUISwing() {
        JFrame frame = new JFrame("FVStore Desktop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 400)); // Tamaño un poco más grande

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Etiqueta de bienvenida en la parte superior (North)
        JLabel welcomeLabel = new JLabel("Bienvenido a FVStore Desktop (Swing)", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Área de texto para mostrar información (Center)
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setText("Aquí se mostraría la información de productos o logs.\n");
        // Simular carga de datos o conexión a servicio
        // infoArea.append("Conectando a ProductoService...\n");
        // try {
        //     ProductoService productoService = new ProductoService(); // Instanciación directa (simplificado)
        //     List<Producto> productos = productoService.obtenerTodosLosProductos();
        //     if (productos.isEmpty()) {
        //         infoArea.append("No hay productos en el core.\n");
        //     } else {
        //         infoArea.append("Productos cargados desde el core:\n");
        //         productos.forEach(p -> infoArea.append(String.format("- %s: %.2f\n", p.getNombre(), p.getPrecio())));
        //     }
        // } catch (Exception e) {
        //     infoArea.append("Error al cargar productos: " + e.getMessage() + "\n");
        // }


        JScrollPane scrollPane = new JScrollPane(infoArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Botón de acción en la parte inferior (South)
        JButton actionButton = new JButton("Cargar Productos (Simulado)");
        actionButton.addActionListener(e -> {
            // Aquí iría la lógica para cargar productos, ya sea del core o de una API
            infoArea.append("Botón 'Cargar Productos' presionado. (Simulación)\n");
            // new MainApp().usarCoreLogicDirectamente(); // Ejemplo si se quiere llamar a la lógica del core
        });
        mainPanel.add(actionButton, BorderLayout.SOUTH);


        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar en pantalla
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Iniciar la GUI de Swing en el Event Dispatch Thread
        SwingUtilities.invokeLater(MainApp::createAndShowGUISwing);

        // Para iniciar JavaFX, se llamaría a launch(args);
        // launch(args);

        System.out.println("FVStore Desktop Application iniciada. GUI de Swing debería estar visible.");

        // Ejemplo de lógica que se podría ejecutar (separado de la UI)
        // MainApp app = new MainApp();
        // app.usarCoreLogicDirectamente(); // Llamada de ejemplo
    }

    // --- Sección para JavaFX (descomentar y completar si se usa JavaFX) ---
    /*
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Opción 1: Cargar desde FXML
        // URL fxmlUrl = getClass().getResource("/fxml/MainView.fxml");
        // if (fxmlUrl == null) {
        //     System.err.println("Error: No se pudo encontrar el archivo FXML MainView.fxml");
        //     // Considerar mostrar un diálogo de error al usuario
        //     javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        //     alert.setTitle("Error de Carga");
        //     alert.setHeaderText("No se puede cargar la interfaz de usuario");
        //     alert.setContentText("El archivo FXML '/fxml/MainView.fxml' no fue encontrado en los recursos.");
        //     alert.showAndWait();
        //     return;
        // }
        // Parent root = FXMLLoader.load(fxmlUrl);
        // primaryStage.setTitle("FVStore Desktop (JavaFX)");
        // primaryStage.setScene(new Scene(root, 800, 600));

        // Opción 2: Crear la UI programáticamente
        javafx.scene.control.Label fxLabel = new javafx.scene.control.Label("Hola desde FVStore Desktop (JavaFX)!");
        javafx.scene.layout.StackPane root = new javafx.scene.layout.StackPane(fxLabel);
        primaryStage.setTitle("FVStore Desktop (JavaFX Programático)");
        primaryStage.setScene(new Scene(root, 400, 300));

        primaryStage.show();
        System.out.println("Interfaz JavaFX iniciada.");
    }
    */
}
