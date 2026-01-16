import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

/**
 * Clase principal de la aplicación EcoTrack
 * Sistema Inteligente de Gestión de Residuos Urbanos
 *
 * Proyecto de Estructuras de Datos - II PAO 2025
 */
public class EcoTrackApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Intentar cargar desde resources primero
            URL fxmlLocation = getClass().getResource("/EcoTrackView.fxml");
            
            // Si no está en resources, buscar en el mismo paquete
            if (fxmlLocation == null) {
                fxmlLocation = getClass().getResource("EcoTrackView.fxml");
            }
            
            // Verificar que se encontró el archivo
            if (fxmlLocation == null) {
                System.err.println("ERROR: No se encontró EcoTrackView.fxml");
                System.err.println("Verifica que el archivo esté en:");
                System.err.println("  - src/main/resources/EcoTrackView.fxml  O");
                System.err.println("  - src/main/java/EcoTrackView.fxml");
                return;
            }
            
            System.out.println("Cargando FXML desde: " + fxmlLocation);
            
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            
            // Configurar la escena
            Scene scene = new Scene(root);
            
            // Configurar el stage
            primaryStage.setTitle("EcoTrack - Sistema de Gestión de Residuos Urbanos");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
            
            System.out.println("✓ EcoTrack cargado exitosamente");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cargar la interfaz: " + e.getMessage());
        }
    }
    
    @Override
    public void stop() {
        System.out.println("Cerrando EcoTrack...");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}