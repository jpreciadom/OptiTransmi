/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane inicioWindow;

    @FXML
    private JFXButton inicioSesion;

    @FXML
    private JFXButton salir;

    @FXML
    private AnchorPane registroWindow;

    @FXML
    private RadioButton recordarUsuarioButton;

    @FXML
    private JFXTextField contrasenna;

    @FXML
    private JFXButton atrasButton;

    @FXML
    private JFXTextField nombre;

    @FXML
    private JFXButton registro;

    @FXML
    private JFXTextField password;

    @FXML
    private JFXButton iniciarSesionButton;

    @FXML
    private AnchorPane inicioSesionWindow;

    @FXML
    private JFXTextField correo;

    @FXML
    private RadioButton inicioAdmin;

    @FXML
    private AnchorPane panel;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXButton btnRegistro;

    @FXML
    private JFXButton atrasRegistroButton;


    public void passInicio(MouseEvent event) throws IOException {
        inicioWindow.setVisible(false);
        inicioSesionWindow.setVisible(true);

    }

    public void passRegistro(MouseEvent event) throws IOException {
        inicioWindow.setVisible(false);
        registroWindow.setVisible(true);
    }

    public void salirApp(MouseEvent event) {
        Stage myStage= (Stage)this.panel.getScene().getWindow();
        myStage.close();
    }


    public void beginSession(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void rememberUser(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void back(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        inicioSesionWindow.setVisible(false);
        inicioWindow.setVisible(true);


    }


    public void registrarUsuario(MouseEvent mouseEvent) {
    }

    public void regresar(MouseEvent mouseEvent) throws IOException {
        registroWindow.setVisible(false);
        inicioWindow.setVisible(true);
    }
    /**
     * Initializes the controller class.
     */
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }



}
