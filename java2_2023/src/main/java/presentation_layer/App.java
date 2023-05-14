package presentation_layer;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {
	public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("domain_layer");
	
	static Logger log = LogManager.getLogger(App.class);	
	
    @FXML
    private AnchorPane login;
    
    public static void main(String[] args) {
    	log.log(Level.INFO, "launch");
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) { 
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/LoginScreen.fxml"));
        try {
            login = loader.load();
        } catch (IOException e) {
            log.fatal("FXML failure", e);
        }
        
        Scene loginScreen = new Scene(login);  
        primaryStage.setScene(loginScreen);
        primaryStage.show();
    }
    
    @SuppressWarnings("unused")
    public void exitProgram(WindowEvent evt) {
        SceneController.stopThread();
        System.exit(0);  
    }
    
}
