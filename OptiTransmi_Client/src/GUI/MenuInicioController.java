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
import optitransmi_client.MainModel;

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
    private JFXButton inicioSesion; //boton de inicio de sesion menu de inicio

    @FXML
    private JFXTextField mail; //campo correo ventana inicio de sesion

    @FXML
    private JFXButton salir;//boton salir menu de inicio

    @FXML
    private JFXButton backFromEstacionToMenuPrincipalButton;//boton regresar de buscarEstacionWindow

    @FXML
    private JFXButton backFromRutaToMenuPrincipalButton;//boton regresar de buscarRutaWindow

    @FXML
    private JFXTextField password;//contraseña en menu de inicio de sesion

    @FXML
    private JFXButton crearSolicitudButton;//boton de crearSolicitud en crearSolicitudWindow

    @FXML
    private JFXButton passToPlanearRutaWindowButton;

    @FXML
    private JFXButton passToBuscarRutaWindowButton;

    @FXML
    private JFXTextField nombreRuta;//nombre de la ruta en buscarRutaWindow

    @FXML
    private JFXTextArea resultadosPlanearRuta;//text area para colocar resultados de planear ruta

    @FXML
    private AnchorPane menuPrincipal;//menu principal

    @FXML
    private JFXTextField nombreRegistro;//campo nombre en el menu de registro

    @FXML
    private AnchorPane solicitarArticuladoWindow;//ventana de solicitar articulado

    @FXML
    private AnchorPane buscarEstacionWindow;//ventana buscar estacion

    @FXML
    private JFXTextField rutaDestino;//ruta de destino en planearRuta

    @FXML
    private JFXButton buscarEstacionButton;//boton buscar estacion en buscarEstacionWindow

    @FXML
    private JFXButton passToSolicitarArticuladoWindowButton;

    @FXML
    private JFXButton atrasRegistroButton;

    @FXML
    private Label labelNombre;//nombre de la persona que inicio sesion para colocar en el menu principal

    @FXML
    private JFXTextField contrasennaRegistro;//campo contraseña en el registro

    @FXML
    private JFXTextField nombreEstacionSolicitud;//nombre de la estacion en planear solicitud

    @FXML
    private AnchorPane registroWindow;//ventana registro

    @FXML
    private AnchorPane planearRutaWindow;//ventana planear ruta

    @FXML
    private JFXButton atrasButton;

    @FXML
    private AnchorPane verMapaWindow;//ventana ver mapa

    @FXML
    private JFXTextArea ResultadosBuscarEstacion;//text area para mostrar reultados de buscar estacion

    @FXML
    private JFXTextArea respuestaSolicitud;//text area para mostrar procesamiento de solicitud

    @FXML
    private JFXButton planearRutaButton;//boton de planear ruta en planearRutaWindow

    @FXML
    private JFXTextArea resultadosBuscarRuta;//text area para mostrar resultados de buscar ruta

    @FXML
    private AnchorPane panel;

    @FXML
    private JFXTextField nombreEstacion;//nombre de la estacion en buscarEstacionWindow

    @FXML
    private JFXButton buscarRutaButton;//boton buscar ruta

    @FXML
    private JFXTextField emailRegistro;//correo en ventana de registro

    @FXML
    private JFXButton btnRegistro;//boton de registrarse

    @FXML
    private JFXButton cerrarSesion;

    @FXML
    private AnchorPane buscarRutaWindow;//ventana buscar ruta

    @FXML
    private AnchorPane inicioWindow;//ventana menuInicio

    @FXML
    private JFXTextField rutaInicio;//ruta de inicio en planear ruta

    @FXML
    private JFXButton backFromPlanearToMenuPrincipalButton;

    @FXML
    private RadioButton recordarUsuarioButton;//radioButton de recordar usuario

    @FXML
    private JFXButton passToBuscarEstacionWindowButton;

    @FXML
    private JFXButton passToVerMapaWindowButton;

    @FXML
    private JFXButton backFromVerToMenuPrincipalButton;

    @FXML
    private JFXButton registro;//boton de registro en menu de inicio

    @FXML
    private JFXTextField nombreRutaSolicitud;//nombre de ruta e solicitarArticulado

    @FXML
    private JFXButton iniciarSesionButton;//boton de iniciar sesion en inicioSesionWindow

    @FXML
    private JFXButton backFromSolicitudToMenuPrincipalButton;

    @FXML
    private Label labelCorreo;//correo de la persona que ingreso para mostrarlo en menuPrincipal

    @FXML
    private AnchorPane inicioSesionWindow;//ventana inicio de sesion

    @FXML
    private RadioButton inicioAdmin;//radioButton para iniciar como administrador

    public void passInicio(MouseEvent event) throws IOException {
        inicioWindow.setVisible(false);
        inicioSesionWindow.setVisible(true);
        beginSession(null);
    }

    public void passRegistro(MouseEvent event) throws IOException {
        inicioWindow.setVisible(false);
        registroWindow.setVisible(true);
    }

    public void salirApp(MouseEvent event) {//salir de la aplicacion
        Stage myStage= (Stage)this.panel.getScene().getWindow();
        myStage.close();
    }

    public void beginSession(javafx.scene.input.MouseEvent mouseEvent) {//iniciar sesion
        String mail = null, password = null;
        boolean rememberUSer = false;
        
        mail = loginModel.readSaveUser();
        if(mail != null){
            String []s = mail.split(" ");
            mail = s[0];
            password = s[1];
        } else {
        //En otro caso
            mail = this.mail.getText();
            this.mail.clear();
            password = this.password.getText();
            this.password.clear();
            rememberUSer = recordarUsuarioButton.isSelected();
        }

        if(mail.length() > 0){
            Answer login = loginModel.singIn(mail, password, rememberUSer);
            if(login != null && login.getAnswer()){
                loginModel.EndModel();
                inicioSesionWindow.setVisible(false);
                menuPrincipal.setVisible(true);
            } else {
                if(login == null){
                    System.out.println("Tiempo de espera excedido");
                } else {
                    System.out.println("Datos de ingreso errados");
                }
            }
        }
    }

    public void rememberUser(javafx.scene.input.MouseEvent mouseEvent) {//recordar usuario

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

        Answer login = loginModel.singUp(mail, password, name, false);
        if(login != null){
            if(login.getAnswer()){
                loginModel.EndModel();
                registroWindow.setVisible(false);
                menuPrincipal.setVisible(true);
            } else {
                System.out.println(login.getMessage());
            }
        } else {
            System.out.println("Timeout");
        }
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

    public void passToVerMapaWindow(MouseEvent mouseEvent) {//pasa a verMapaWindow
        buscarEstacionWindow.setVisible(false);
        verMapaWindow.setVisible(true);
    }

    public void backFromVerToMenuPrincipal(MouseEvent mouseEvent) {//regresa a buscarEstacionWindow
        verMapaWindow.setVisible(false);
        buscarEstacionWindow.setVisible(true);
    }

    /**
     * Initializes the controller class.
     */
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginModel = new LoginModel();
        //loginModel.start();
    }
    
    private LoginModel loginModel;
    private MainModel mainModel;
}
