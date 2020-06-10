/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Information.Answer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import optitransmi_client.LoginModel;

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
    private JFXTextField contrasennaRegistro;

    @FXML
    private JFXTextField nombreEstacionSolicitud;

    @FXML
    private JFXTextField mail;

    @FXML
    private JFXButton salir;

    @FXML
    private AnchorPane registroWindow;

    @FXML
    private AnchorPane planearRutaWindow;

    @FXML
    private JFXButton atrasButton;

    @FXML
    private JFXButton backFromEstacionToMenuPrincipalButton;

    @FXML
    private JFXTextArea ResultadosBuscarEstacion;

    @FXML
    private JFXButton backFromRutaToMenuPrincipalButton;

    @FXML
    private JFXTextField password;

    @FXML
    private JFXTextArea respuestaSolicitud;

    @FXML
    private JFXButton planearRutaButton;

    @FXML
    private JFXButton crearSolicitudButton;

    @FXML
    private JFXTextArea resultadosBuscarRuta;

    @FXML
    private AnchorPane panel;

    @FXML
    private JFXTextField nombreEstacion;

    @FXML
    private JFXButton buscarRutaButton;

    @FXML
    private JFXTextField emailRegistro;

    @FXML
    private JFXButton btnRegistro;

    @FXML
    private JFXButton cerrarSesion;

    @FXML
    private AnchorPane buscarRutaWindow;

    @FXML
    private AnchorPane inicioWindow;

    @FXML
    private JFXButton passToPlanearRutaWindowButton;

    @FXML
    private JFXButton passToBuscarRutaWindowButton;

    @FXML
    private JFXTextField nombreRuta;

    @FXML
    private JFXTextField rutaInicio;

    @FXML
    private JFXButton backFromPlanearToMenuPrincipalButton;

    @FXML
    private RadioButton recordarUsuarioButton;

    @FXML
    private JFXTextArea resultadosPlanearRuta;

    @FXML
    private JFXButton passToBuscarEstacionWindowButton;

    @FXML
    private AnchorPane menuPrincipal;

    @FXML
    private JFXTextField nombreRegistro;

    @FXML
    private AnchorPane solicitarArticuladoWindow;

    @FXML
    private AnchorPane buscarEstacionWindow;

    @FXML
    private JFXButton registro;

    @FXML
    private JFXTextField rutaDestino;

    @FXML
    private JFXTextField nombreRutaSolicitud;

    @FXML
    private JFXButton iniciarSesionButton;

    @FXML
    private JFXButton backFromSolicitudToMenuPrincipalButton;

    @FXML
    private Label labelCorreo;

    @FXML
    private AnchorPane inicioSesionWindow;

    @FXML
    private RadioButton inicioAdmin;

    @FXML
    private JFXButton buscarEstacionButton;

    @FXML
    private JFXButton passToSolicitarArticuladoWindowButton;

    @FXML
    private JFXButton atrasRegistroButton;

    @FXML
    private Label labelNombre;

    private LoginModel model;

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
        String mail = null, password = null;
        boolean rememberUSer = false;

        //Mirar si el archivo contiene algo
        //Si tiene algo usar esa inforamción

        //En otro caso
            mail = this.mail.getText();
            this.mail.clear();
            password = this.password.getText();
            this.password.clear();
            rememberUSer = recordarUsuarioButton.isSelected();

        Answer login = model.singIn(mail, password, rememberUSer);
        if(login != null && login.getAnswer()){
            System.out.println("Inicio de sesion exitoso!");
            //Cambiamos a la siguiente ventana
            /*inicioSesionWindow.setVisible(false);
            menuPrincipal.setVisible(true);*/
        } else {
            if(login == null){
                System.out.println("Tiempo de espera excedido");
            } else {
                System.out.println("Datos de ingreso errados");
            }
        }

    }

    public void rememberUser(javafx.scene.input.MouseEvent mouseEvent) {

    }

    public void back(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        inicioSesionWindow.setVisible(false);
        inicioWindow.setVisible(true);
    }


    public void registrarUsuario(MouseEvent mouseEvent) {
        String name = null, password = null, mail = null;
        name = nombreRegistro.getText().trim();
        password = contrasennaRegistro.getText().trim();
        mail = emailRegistro.getText().trim();

        nombreRegistro.clear();
        contrasennaRegistro.clear();
        emailRegistro.clear();

        Answer login = model.singUp(mail, password, name, false);
        System.out.println(login);
    }

    public void regresar(MouseEvent mouseEvent) throws IOException {
        registroWindow.setVisible(false);
        inicioWindow.setVisible(true);
    }

    public void passToBuscarEstacionWindow(MouseEvent mouseEvent) {//Metodode boton buscarEstacionButton
        menuPrincipal.setVisible(false);
        buscarEstacionWindow.setVisible(true);
    }

    public void passToBuscarRutaWindow(MouseEvent mouseEvent) { //Metodo de boton buscarRutaButton
        menuPrincipal.setVisible(false);
        buscarRutaWindow.setVisible(true);

    }


    public void passToPlanearRutaWindow(MouseEvent mouseEvent) {//Metodo de planearRutaButton, pasa a planearRutaWindow
        menuPrincipal.setVisible(false);
        planearRutaWindow.setVisible(true);
    }

    public void passToSolicitarArticuladoWindow(MouseEvent mouseEvent) {//Metodo de solicitarArticuladoButton, pasa a solicitarArticuladoWindow
        solicitarArticuladoWindow.setVisible(true);
        menuPrincipal.setVisible(false);
    }


    public void backToBeginSession(MouseEvent mouseEvent) {
        menuPrincipal.setVisible(false);
        inicioSesionWindow.setVisible(true);
    }

    public void buscarEstacion(MouseEvent mouseEvent) {//Evento a boton BuscarEstacionButton, busca estaciones
    }                                                  //y muestra en textArea resultadosBuscarEstacion


    public void backFromEstacionToMenuPrincipal(MouseEvent mouseEvent) {//volver de buscarEstacionWindow a menuPrincipal
        buscarEstacionWindow.setVisible(false);
        menuPrincipal.setVisible(true);
        ResultadosBuscarEstacion.setText("");
    }

    public void buscarRuta(MouseEvent mouseEvent) {//Metodo del boton buscarRutabutton, busca las rutas
    }                                              //y las muestra en textArea resultadosBuscarRuta

    public void backFromRutaToMenuPrincipal(MouseEvent mouseEvent) {
        resultadosBuscarRuta.setText("");
        buscarRutaWindow.setVisible(false);
        menuPrincipal.setVisible(true);
    }


    public void planearRuta(MouseEvent mouseEvent) { //Metodo del boton planearRutaButton, muestra resultados de mejores rutas
    }                                                // en el textArea resultadosPlanearRuta


    public void backFromPlanearToMenuPrincipal(MouseEvent mouseEvent) {
        planearRutaWindow.setVisible(false);
        menuPrincipal.setVisible(true);
        resultadosPlanearRuta.setText("");
    }


    public void crearSolicitud(MouseEvent mouseEvent) {
    }

    public void backFromSolicitudToMenuPrincipal(MouseEvent mouseEvent) {//pasar de CrearSolicitudWindow a menuPrincipal
        solicitarArticuladoWindow.setVisible(false);
        respuestaSolicitud.setText("");
        menuPrincipal.setVisible(true);
    }

    /**
     * Initializes the controller class.
     */
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new LoginModel();
        model.start();
    }


}
