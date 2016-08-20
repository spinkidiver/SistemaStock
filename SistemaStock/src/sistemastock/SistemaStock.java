package sistemastock;

import sistemastock.controller.RootLayoutController;
//import sistemastock.controller.ProductoViewController;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 *
 * @author Tke Kaiser
 */
public class SistemaStock extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    @Override
    public void start(Stage stage) {
         this.primaryStage = stage;
          //  this.primaryStage.setResizable(false);
            this.primaryStage.setTitle("Agenda");
           
             initRootLayout();
            // mostrarLogin();
           showProductoView();
    }
    
    
    //Es necesario cuando se carga un fxml en el init
    @Override
    public void stop(){
       System.exit(0);

    }

   
    public static void main(String[] args) {
        launch(args);
    }

    private void initRootLayout() {
       try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SistemaStock.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
           
            //Dar acceso al controlador de agendaapp
            RootLayoutController controller = loader.getController();
            controller.setSistemaStock(this);
            
            primaryStage.centerOnScreen();
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showProductoView() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SistemaStock.class.getResource("view/ProductoView.fxml"));
            AnchorPane productoView = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(productoView);
            
             // Give the controller access to the main app.
            //ProductoViewController contr = loader.getController();
            //contr.setSistemaApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
      public Stage getPrimaryStage() {
        return primaryStage;
    
        
            
        }
       
      
  
    
}
