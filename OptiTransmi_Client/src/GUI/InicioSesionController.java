/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class InicioSesionController implements Initializable {
    @FXML
    private JFXTextField password;

    @FXML
    private JFXButton iniciarSesionButton;

    @FXML
    private JFXTextField correo;

    @FXML
    private RadioButton recordarUsuarioButton;

    @FXML
    private JFXButton atrasButton;

    public void beginSession(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void rememberUser(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void back(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("MenuInicio.fxml"));
        Parent root= loader.load();
        MenuInicioController controller= loader.getController();

        Scene scene= new Scene(root);
        Stage stage= new Stage();

        stage.setScene(scene);
        stage.show();


        Stage myStage= (Stage)this.atrasButton.getScene().getWindow();
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
