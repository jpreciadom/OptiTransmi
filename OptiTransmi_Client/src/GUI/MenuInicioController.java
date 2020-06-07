/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class MenuInicioController implements Initializable {

    @FXML
    private JFXButton inicioSesion;

    @FXML
    private JFXButton salir;

    @FXML
    private JFXButton registro;

    
    public void passInicio(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("InicioSesion.fxml"));
        Parent root= loader.load();
        InicioSesionController controller= loader.getController();

        Scene scene= new Scene(root);
        Stage stage= new Stage();

        stage.setScene(scene);
        stage.show();


        Stage myStage= (Stage)this.inicioSesion.getScene().getWindow();
        myStage.close();
    }

    public void passRegistro(MouseEvent event) throws IOException {
        FXMLLoader loader2= new FXMLLoader(getClass().getResource("Registro.fxml"));
        Parent root2= loader2.load();
        RegistroController controller= loader2.getController();

        Scene scene= new Scene(root2);
        Stage stage= new Stage();

        stage.setScene(scene);
        stage.show();


        Stage myStage= (Stage)this.registro.getScene().getWindow();
        myStage.close();
    }

    public void salirApp(MouseEvent event) {
        Stage myStage= (Stage)this.salir.getScene().getWindow();
        myStage.close();
    }


    
    /**
     * Initializes the controller class.
     */
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }



}
