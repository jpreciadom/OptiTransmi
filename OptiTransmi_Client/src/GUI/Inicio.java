/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author ADMIN
 */
public class Inicio extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("MenuInicio.fxml"));
        Scene scene= new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("file: images/OptiTransmi_logo.png "));//colocar icono, pendiente
        primaryStage.setTitle("OptiTransmi");
        primaryStage.show();
    }
    
     public static void main(String[] args) {
        launch(args);
    }
}
