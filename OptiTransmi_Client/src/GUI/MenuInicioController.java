/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Administrator.*;
import Information.*;
import Login.SingInAnswer;
import Request.*;

import UserDataConfig.*;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import optitransmi_client.Model;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.io.IOException;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class MenuInicioController implements Initializable {



    @FXML private AnchorPane panel;//Ventana base

    //Ventana de menu de inicio
    @FXML public AnchorPane inicioWindow;//ventana menuInicio
    @FXML public JFXButton inicioSesion; //boton de inicio de sesion menu de inicio
    @FXML public JFXButton registro;//boton de registro en menu de inicio
    @FXML private JFXButton salir;//boton salir menu de inicio

    //Ventana inicio de sesion
    @FXML private AnchorPane inicioSesionWindow;//ventana inicio de sesion
    @FXML private JFXTextField mail; //campo correo ventana inicio de sesion
    @FXML private JFXPasswordField password;//contraseña en menu de inicio de sesion
    @FXML private RadioButton recordarUsuarioButton;//radioButton de recordar usuario
    @FXML private RadioButton inicioAdmin;//radioButton para iniciar como administrador
    @FXML private JFXButton iniciarSesionButton;//boton de iniciar sesion en inicioSesionWindow
    @FXML private JFXButton atrasButton;
    @FXML public Label datosIncorrectos;


    //Ventana de registro
    @FXML private AnchorPane registroWindow;//ventana registro
    @FXML private JFXTextField nombreRegistro;//campo nombre en el menu de registro
    @FXML private JFXTextField emailRegistro;//correo en ventana de registro
    @FXML private JFXTextField contrasennaRegistro;//campo contraseña en el registro
    @FXML private JFXButton btnRegistro;//boton de registrarse
    @FXML private JFXButton atrasRegistroButton;


    //Ventana menu principal
    @FXML public AnchorPane menuPrincipal;//menu principal
    @FXML private Label labelNombre;//nombre de la persona que inicio sesion para colocar en el menu principal
    @FXML private Label labelCorreo;//correo de la persona que ingreso para mostrarlo en menuPrincipal
    @FXML private JFXButton passToBuscarEstacionWindowButton;
    @FXML private JFXButton passToBuscarRutaWindowButton;
    @FXML public JFXButton passToPlanearRutaWindowButton;
    @FXML public JFXButton passToSolicitarArticuladoWindowButton;
    @FXML private JFXButton passToModificarInfoWindowButton;
    @FXML private JFXButton cerrarSesion;
    @FXML private JFXButton botonRecargas;


    //Ventana buscar estacion
    @FXML public AnchorPane buscarEstacionWindow;//ventana buscar estacion
    @FXML private JFXTextField nombreEstacion;//nombre de la estacion en buscarEstacionWindow
    @FXML private JFXButton buscarEstacionButton;//boton buscar estacion en buscarEstacionWindow
    @FXML public JFXTextArea ResultadosBuscarEstacion;//text area para mostrar reultados de buscar estacion
    @FXML private JFXButton passToVerMapaWindowButton;
    @FXML private JFXButton backFromEstacionToMenuPrincipalButton;//boton regresar de buscarEstacionWindow


    //Ventana ver mapa de estaciones
    @FXML private AnchorPane verMapaWindow;//ventana ver mapa
    @FXML private JFXButton backFromVerToMenuPrincipalButton;


    //Ventana buscar ruta
    @FXML public AnchorPane buscarRutaWindow;//ventana buscar ruta
    @FXML private JFXTextField nombreRuta;//nombre de la ruta en buscarRutaWindow
    @FXML private JFXButton buscarRutaButton;//boton buscar ruta
    @FXML private JFXButton backFromRutaToMenuPrincipalButton;//boton regresar de buscarRutaWindow
    @FXML public JFXTreeTableView<Ruta> resultadosBuscarRuta;
    //@FXML private JFXTextArea resultadosBuscarRuta;//text area para mostrar resultados de buscar ruta



    //Ventana de planear ruta
    @FXML public AnchorPane planearRutaWindow;//ventana planear ruta
    @FXML public ComboBox<String> estacionInicio;//ruta de inicio en planear ruta
    @FXML public ComboBox<String> estacionDestino;//ruta de destino en planearRuta
    @FXML private JFXButton planearRutaButton;//boton de planear ruta en planearRutaWindow
    @FXML private JFXButton backFromPlanearToMenuPrincipalButton;
    @FXML private Label estacionIncicioRes;
    @FXML private Label rutaAleatoria;
    @FXML private Label estacionDestinoRes;
    @FXML private ImageView imagenRuta;


    //Ventana de solicitar articulado
    @FXML private AnchorPane solicitarArticuladoWindow;//ventana de solicitar articulado
    @FXML public ComboBox<String> nombreEstacionSolicitud;//nombre de la estacion en planear solicitud
    @FXML public ComboBox<String> nombreRutaSolicitud;//nombre de ruta e solicitarArticulado
    @FXML private JFXButton crearSolicitudButton;//boton de crearSolicitud en crearSolicitudWindow
    @FXML private JFXTextArea respuestaSolicitud;//text area para mostrar procesamiento de solicitud
    @FXML private JFXButton backFromSolicitudToMenuPrincipalButton;


    //Ventana de estaciones
    @FXML public AnchorPane noticiasWindow;
    @FXML private ScrollPane noticiasPanel;//Panel donde se van a mostrar las noticias
    @FXML private JFXButton backToMenuPrincipalFromNoticiasButton;


    //Ventana de modificar informacion
    @FXML private AnchorPane configurarInfoWindow;
    @FXML private JFXTextField nombreUsuarioConfig;
    @FXML private JFXTextField correoUsuarioConfig;
    @FXML private JFXTextField contrasenaResultadoConfig;
    @FXML private JFXButton guardarCambiosInfoButton;
    @FXML private JFXButton backToMenuPrincipalFromModificarButton;




    //Ventana menu principal de administrador
    @FXML private JFXButton passToAjustarEstacionesWindowButton;
    @FXML private JFXButton passToAjustarRutasWindowButton;
    @FXML private JFXButton passToEstadisticasWindowButton;
    @FXML private AnchorPane menuPrincipalAdmin;
    @FXML private Label labelCorreoAdmin;
    @FXML private JFXButton backToInicioSesionFromMenuAdminButton;
    @FXML private Label labelNombreAdmin;


    //Ventana de ajustes de estaciones
    @FXML private AnchorPane ajustesEstacionesWindow;
    @FXML private JFXButton passToModificarEstacionWindowButton;
    @FXML private JFXButton passToAgregarEstacionWindowButton;
    @FXML private JFXButton backToMenAdminFromEstacionesWindowButton;
    @FXML private JFXButton passToEliminarEstacionWindowButton;


    //Ventana de ajustes de rutas
    @FXML private AnchorPane ajustesRutaWindow;
    @FXML private JFXButton passToModifcarWindowButton;
    @FXML private JFXButton passToAgregarRutaWindowButton;
    @FXML private JFXButton passToEliminarRutaWindowButton;
    @FXML private JFXButton backToMenAdminFromAjustesRutaButton;



    //Ventana Agregar Nueva estacion
    @FXML private AnchorPane agregarEstaicionWindow;
    @FXML private JFXTextField nombreNuevaEstacion;
    @FXML private JFXTextField direccionNuevaEstacion;
    @FXML private JFXTextField zonaNuevaEstacion;
    @FXML private JFXTextField numVagonesNuevaEstacion;
    @FXML private JFXButton backToMenAdminFromNuevaEstacionButton;



    //Ventana de modificar estacion
    @FXML public AnchorPane modificarEstacionWindow;
    @FXML private JFXTextField nombreEstacionBusquedaModificacion;
    @FXML public JFXTextField nombreEstacionAModificar;
    @FXML public JFXTextField direccionEstacionABuscar;
    @FXML public JFXTextField zonaEstacionABuscar;
    @FXML public JFXTextField numVagonesEstacionABuscar;
    @FXML private JFXButton guardarCambiosEstacionButton;
    @FXML private JFXButton backToMenAdminFromModifcarEstacionButton;


    //Ventana de eliminar estacion
    @FXML private AnchorPane EliminarEstacionWindow;
    @FXML private JFXTextField nombreEstacionAEliminar;//resultado de la busqueda
    @FXML private JFXButton eliminarEstacionButton;
    @FXML private JFXButton backToMenAdminFromEliminarEstacionButton;


    //Ventana de agregar ruta
    @FXML private AnchorPane agregarRutaWindow;
    @FXML private JFXTextField nombreRutaAAgregar;
    @FXML private JFXTextField diaRutaAAgregar;
    @FXML private JFXTextField horaInicioRutaAAgregar;
    @FXML private JFXTextField horaFinRutaAAgregar;
    @FXML private JFXButton backToAjustesRutasFromAgregarRutaButton;
    @FXML private JFXButton agregarRutaButton;



    //Ventana de modificar ruta
    @FXML public AnchorPane modificarRutaWindow;
    @FXML public JFXTextField codigoRutaABuscar;
    @FXML public JFXTextField diaRutaABuscar;
    @FXML private JFXButton buscarRutaAModificarButton;
    @FXML public JFXTextField horaInicioRutaBuscada;
    @FXML public JFXTextField horaFinRutaBuscada;
    @FXML private JFXButton guardarCambiosRutaButton;
    @FXML private JFXButton backToAjustesRutaFromModificarRutaButton;



    //Ventana de eliminar ruta
    @FXML public AnchorPane eliminarRutaWindow;
    @FXML public JFXTextField codigoRutaAEliminar;
    @FXML public JFXTextField diaRutaAEliminar;
    @FXML private JFXButton buscarRutaAEliminarButton;
    @FXML public Label horaInicioRutaAEliminar;
    @FXML public Label horaFinRutaAEliminar;
    @FXML private JFXButton eliminarRutaButton;
    @FXML private JFXButton backToAjustesRutasFromEliminarRutaButton;


    //Ventana de agregar noticia
    @FXML private AnchorPane agregarNoticiaWindow;
    @FXML private JFXButton passToAgregarNoticiaWindowButton;
    @FXML private JFXTextField tituloNuevaNoticia;
    @FXML private JFXTextArea contenidoNuevaNoticia;
    @FXML private JFXButton agregarNuevaNoticiaButton;
    @FXML private JFXButton backToMenAdminFromAgregarNoticiaButton;

    //Ventab¿na de estadisticas
    @FXML public AnchorPane estadisticasWindow;
    @FXML public BarChart<?, ?> estadisticasRutas;
    @FXML public CategoryAxis xRutas;
    @FXML public NumberAxis yRutas;
    @FXML private JFXButton backFromEstadisticas;

    //Area de noticias
    @FXML public JFXTextArea areaNoticias;

    public boolean ingresoPorAdmin=false;


    public void passPuntosRecarga(MouseEvent event) throws IOException {
        Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe /K start https://www.tullaveplus.gov.co/web/guest/puntos-de-recarga");
    }

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
        model.EndModel();
        myStage.close();
    }

    public void beginSession(javafx.scene.input.MouseEvent mouseEvent) {//iniciar sesion
        String mail = null, password = null;
        boolean rememberUSer = false;
        
        mail = model.readSaveUser();
        if(mail != null){
            String []s = mail.split(" ");
            mail = s[0];
            password = s[1];
        } else {
            mail = this.mail.getText();
            this.mail.clear();
            password = this.password.getText();
            this.password.clear();
            rememberUSer = recordarUsuarioButton.isSelected();
        }

        if(mail.length() > 0){
            SingInAnswer login = model.singIn(mail, password, rememberUSer);
            if(login != null && login.getAnswer()){
                if(login.getUserType()==1){
                    model.setLogged(true);
                    inicioSesionWindow.setVisible(false);
                    menuPrincipal.setVisible(true);
                    labelNombre.setText(login.getMessage());
                    labelCorreo.setText(mail);
                    if(datosIncorrectos.isVisible() == true){
                        datosIncorrectos.setVisible(false);
                    }
                }else if(login.getUserType()==0){
                    model.setAdmin(true);
                    inicioSesionWindow.setVisible(false);
                    menuPrincipalAdmin.setVisible(true);
                    labelNombreAdmin.setText(login.getUserName());
                    labelCorreoAdmin.setText(mail);
                    if(datosIncorrectos.isVisible() == true){
                        datosIncorrectos.setVisible(false);
                    }
                }
            } else {
                datosIncorrectos.setVisible(true);
                if(login == null){
                    datosIncorrectos.setText("Tiempo de espera excedido");
                } else {
                    datosIncorrectos.setVisible(true);
                }
            }
        }

    }

    public void rememberUser(javafx.scene.input.MouseEvent mouseEvent) {//recordar usuario

    }

    public void back(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        inicioSesionWindow.setVisible(false);
        inicioWindow.setVisible(true);
        datosIncorrectos.setVisible(false);
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
        if(login != null){
            if(login.getAnswer()){
                model.setLogged(true);
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
    public void passToAjustarEstacionesWindow(MouseEvent mouseEvent) {
        menuPrincipalAdmin.setVisible(false);
        ajustesEstacionesWindow.setVisible(true);
    }

    public void passToAjustarRutasWindow(MouseEvent mouseEvent) {
        menuPrincipalAdmin.setVisible(false);
        ajustesRutaWindow.setVisible(true);
    }

    public void backToInicioSesionFromMenuAdmin(MouseEvent mouseEvent) {
        menuPrincipalAdmin.setVisible(false);
        inicioSesionWindow.setVisible(true);
    }

    public void buscarRuta(MouseEvent mouseEvent) {//Metodo del boton buscarRutabutton, busca las rutas
        String ruta = nombreRuta.getText();
        nombreRuta.clear();
        RutaListRequest rlr = new RutaListRequest(model.getCurrentIdRequest(), ruta,null);
        model.createRequest(rlr);

        resultadosBuscarRuta.setDisable(false);
    }                                              //y las muestra en textArea resultadosBuscarRuta

    public void passToAgregarEstacionWindow(MouseEvent mouseEvent) {
        ajustesEstacionesWindow.setVisible(false);
        agregarEstaicionWindow.setVisible(true);
    }

    public void passToModificarEstacionWindow(MouseEvent mouseEvent) {
        ajustesEstacionesWindow.setVisible(false);
        modificarEstacionWindow.setVisible(true);
    }

    public void backToMenAdminFromEstacionesWindow(MouseEvent mouseEvent) {
        ajustesEstacionesWindow.setVisible(false);
        menuPrincipalAdmin.setVisible(true);
    }

    public void passToEliminarEstacionWindow(MouseEvent mouseEvent) {
        ajustesEstacionesWindow.setVisible(false);
        EliminarEstacionWindow.setVisible(true);
    }

    public void passToModifcarWindow(MouseEvent mouseEvent) {
        ajustesRutaWindow.setVisible(false);
        modificarRutaWindow.setVisible(true);
    }

    public void passToEliminarRutaWindow(MouseEvent mouseEvent) {
        ajustesRutaWindow.setVisible(false);
        eliminarRutaWindow.setVisible(true);
    }

    public void backToMenAdminFromAjustesRuta(MouseEvent mouseEvent) {
        ajustesRutaWindow.setVisible(false);
        menuPrincipalAdmin.setVisible(true);


    }

    public void passToAgregarRutaWindow(MouseEvent mouseEvent) {
        ajustesRutaWindow.setVisible(false);
        agregarRutaWindow.setVisible(true);
    }

    public void agregarNuevaEstacion(MouseEvent mouseEvent) throws AWTException {

        String nombreEstacion = nombreNuevaEstacion.getText();
        String direccion = direccionNuevaEstacion.getText();
        String zona = zonaNuevaEstacion.getText();
        int vagones = Integer.parseInt(numVagonesNuevaEstacion.getText());

        AddEstacion ae = new AddEstacion(model.getCurrentIdRequest(), nombreEstacion,direccion,zona,vagones);
        model.createRequest(ae);

        SystemTray tray= SystemTray.getSystemTray();
        Image image= Toolkit.getDefaultToolkit().createImage("src/GUI/images/OptiTransmi_logo.PNG");
        TrayIcon trayIcon= new TrayIcon(image, "OptiTransmi");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Notificacion OptiTransmi");
        tray.add(trayIcon);

        trayIcon.displayMessage("Nueva estacion agregada","Notificacion OptiTransmi", TrayIcon.MessageType.INFO);
    }

    public void backToMenAdminFromNuevaEstacionButton(MouseEvent mouseEvent) {
        agregarEstaicionWindow.setVisible(false);
        menuPrincipalAdmin.setVisible(true);
    }

    public void guardarCambiosEstacion(MouseEvent mouseEvent) {//Guarda los cambios de la estacion a modificar
        String estacion = nombreEstacionAModificar.getText();
        String direccion = direccionEstacionABuscar.getText();
        String zona = zonaEstacionABuscar.getText();
        int vagones = Integer.parseInt(numVagonesEstacionABuscar.getText());
        ModificarEstacion me = new ModificarEstacion(model.getCurrentIdRequest(),estacion,direccion,zona,vagones);
        model.createRequest(me);

        nombreEstacionBusquedaModificacion.clear();
        nombreEstacionAModificar.clear();
        zonaEstacionABuscar.clear();
        numVagonesEstacionABuscar.clear();
    }

    public void backToMenAdminFromModifcarEstacion(MouseEvent mouseEvent) {
        modificarEstacionWindow.setVisible(false);
        menuPrincipalAdmin.setVisible(true);
    }

    public void buscarEstacionModificacion(MouseEvent mouseEvent) {//Busca la estacion a modifcar
        String estacion = nombreEstacionBusquedaModificacion.getText();
        StationListRequest slr = new StationListRequest(model.getCurrentIdRequest(),estacion);
        model.createRequest(slr);
    }

    public void busquedaEstacionAEliminar(MouseEvent mouseEvent) {//Busca la estacion a eliminar
    }

    public void eliminarEstacion(MouseEvent mouseEvent) throws AWTException {//elimina la estacion
        SystemTray tray= SystemTray.getSystemTray();
        Image image= Toolkit.getDefaultToolkit().createImage("src/GUI/images/OptiTransmi_logo.PNG");
        TrayIcon trayIcon= new TrayIcon(image, "OptiTransmi");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Notificacion OptiTransmi");
        tray.add(trayIcon);

        trayIcon.displayMessage("Estacion eliminada","Notificacion OptiTransmi", TrayIcon.MessageType.INFO);
        String estacion = nombreEstacionAEliminar.getText();
        EliminarEstacion ee = new EliminarEstacion(model.getCurrentIdRequest(),estacion);
        model.createRequest(ee);
        nombreEstacionAEliminar.clear();
    }

    public void backToMenAdminFromEliminarEstacion(MouseEvent mouseEvent) {
        EliminarEstacionWindow.setVisible(false);
        menuPrincipalAdmin.setVisible(true);
    }

    public void agregarRuta(MouseEvent mouseEvent) throws AWTException {//Agregar la ruta en la venta agregarRutaWindow

        String codigo = nombreRutaAAgregar.getText();
        String dia = diaRutaAAgregar.getText();
        String inicio = horaInicioRutaAAgregar.getText();
        String fin = horaFinRutaAAgregar.getText();

        AddRuta ar = new AddRuta(model.getCurrentIdRequest(),codigo, dia, inicio, fin);
        model.createRequest(ar);

        SystemTray tray= SystemTray.getSystemTray();
        Image image= Toolkit.getDefaultToolkit().createImage("src/GUI/images/OptiTransmi_logo.PNG");
        TrayIcon trayIcon= new TrayIcon(image, "OptiTransmi");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Notificacion OptiTransmi");
        tray.add(trayIcon);

        trayIcon.displayMessage("Nueva ruta agregada","Notificacion OptiTransmi", TrayIcon.MessageType.INFO);
    }

    public void backToAjustesRutasFromAgregarRuta(MouseEvent mouseEvent) {
        agregarRutaWindow.setVisible(false);
        ajustesRutaWindow.setVisible(true);
    }

    public void buscarRutaAModificar(MouseEvent mouseEvent) {//Busca la ruta a la cual se le va a modificar la informacion
        String codigo_ruta= codigoRutaABuscar.getText();
        String dia=diaRutaABuscar.getText();
        RutaListRequest request= new RutaListRequest(model.getCurrentIdRequest(), codigo_ruta, dia);
        model.createRequest(request);
    }

    public void guardarCambiosRuta(MouseEvent mouseEvent) {//guarda los cambios de la ruta ajustada
        String codigo_ruta= codigoRutaABuscar.getText();
        String dia=diaRutaABuscar.getText();
        String inicio= horaInicioRutaBuscada.getText();
        String fin= horaFinRutaBuscada.getText();
        ModificarRuta mdr= new ModificarRuta(model.getCurrentIdRequest(),codigo_ruta,dia,inicio,fin);
        model.createRequest(mdr);
    }

    public void backToAjustesRutaFromModificarRuta(MouseEvent mouseEvent) {
        modificarRutaWindow.setVisible(false);
        ajustesRutaWindow.setVisible(true);

    }

    public void buscarRutaAEliminar(MouseEvent mouseEvent) {//Busca la ruta para eliminar la ruta, en eliminarRutaWindow
    }

    public void eliminarRuta(MouseEvent mouseEvent) throws AWTException {//elimina la ruta en eliminarRutaWindow
        SystemTray tray= SystemTray.getSystemTray();
        Image image= Toolkit.getDefaultToolkit().createImage("src/GUI/images/OptiTransmi_logo.PNG");
        TrayIcon trayIcon= new TrayIcon(image, "OptiTransmi");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Notificacion OptiTransmi");
        tray.add(trayIcon);

        trayIcon.displayMessage("Ruta eliminada","Notificacion OptiTransmi", TrayIcon.MessageType.INFO);
        String codigo_ruta= codigoRutaAEliminar.getText();
        String dia=diaRutaAEliminar.getText();
        EliminarRuta elr=  new EliminarRuta(model.getCurrentIdRequest(), codigo_ruta, dia);
        model.createRequest(elr);
    }

    public void backToAjustesRutasFromEliminarRuta(MouseEvent mouseEvent) {
        eliminarRutaWindow.setVisible(false);
        ajustesRutaWindow.setVisible(true);
    }

    public void backToMenuPrincipalFromNoticias(MouseEvent mouseEvent) {
        if(ingresoPorAdmin){
            menuPrincipalAdmin.setVisible(true);
            noticiasWindow.setVisible(false);
        }else{
            noticiasWindow.setVisible(false);
            menuPrincipal.setVisible(true);
        }
        ingresoPorAdmin=false;

        areaNoticias.clear();
        
    }

    public void passToNoticiasWindow(MouseEvent mouseEvent) {
        menuPrincipal.setVisible(false);
        noticiasWindow.setVisible(true);
        while(!model.news.isEmpty()){
            News news = model.news.removeFirst();
            areaNoticias.appendText(news.getTitle() + "\n");
            areaNoticias.appendText(news.getContent() + "\n");
            areaNoticias.appendText("______________________________________\n");
        }
    }

    public void passToAgregarNoticiaWindow(MouseEvent mouseEvent) {
        menuPrincipalAdmin.setVisible(false);
        agregarNoticiaWindow.setVisible(true);

    }

    public void backToMenAdminFromAgregarNoticia(MouseEvent mouseEvent) {
        agregarNoticiaWindow.setVisible(false);
        menuPrincipalAdmin.setVisible(true);
    }

    public void agregarNuevaNoticia(MouseEvent mouseEvent) throws AWTException {//Evento para agregar nueva noticia
        model.createRequest(new News(tituloNuevaNoticia.getText(), contenidoNuevaNoticia.getText(), model.getCurrentIdRequest()));
        tituloNuevaNoticia.clear();
        contenidoNuevaNoticia.clear();
        
        SystemTray tray= SystemTray.getSystemTray();
        Image image= Toolkit.getDefaultToolkit().createImage("src/GUI/images/OptiTransmi_logo.PNG");
        TrayIcon trayIcon= new TrayIcon(image, "OptiTransmi");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Notificacion OptiTransmi");
        tray.add(trayIcon);

        trayIcon.displayMessage("Nueva noticia agregada","Notificacion OptiTransmi", TrayIcon.MessageType.INFO);
    }

    public void passToModificarInfoWindow(MouseEvent mouseEvent) {
        menuPrincipal.setVisible(false);
        configurarInfoWindow.setVisible(true);
    }

    public void guardarCambiosInfo(MouseEvent mouseEvent) throws AWTException {//guarda loscambios de la informacion del usuario
        String nombreUsuario = nombreUsuarioConfig.getText();
        String correo = correoUsuarioConfig.getText();
        String contrasenna = contrasenaResultadoConfig.getText();
        ChangeUserInfo cui = new ChangeUserInfo(model.getCurrentIdRequest(), nombreUsuario, correo, contrasenna);
        model.createRequest(cui);

        nombreUsuarioConfig.clear();
        correoUsuarioConfig.clear();
        contrasenaResultadoConfig.clear();

        SystemTray tray= SystemTray.getSystemTray();
        Image image= Toolkit.getDefaultToolkit().createImage("src/GUI/images/OptiTransmi_logo.PNG");
        TrayIcon trayIcon= new TrayIcon(image, "OptiTransmi");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Notificacion OptiTransmi");
        tray.add(trayIcon);

        trayIcon.displayMessage("Informacion cambiada","Notificacion OptiTransmi", TrayIcon.MessageType.INFO);
    }

    public void backToMenuPrincipalFromModificar(MouseEvent mouseEvent) {
        configurarInfoWindow.setVisible(false);
        menuPrincipal.setVisible(true);
    }

    public void ayuda(MouseEvent mouseEvent) throws IOException {
        File documento= new File("OptiTransmi_Client/src/GUI/files/ManualdeusoOptiTransmi.pdf");
        Desktop.getDesktop().open(documento);
    }

    public void passToEstadisticasWindow(MouseEvent mouseEvent) {
        Estadisticas es = new Estadisticas(model.getCurrentIdRequest());
        model.createRequest(es);

        menuPrincipalAdmin.setVisible(false);
        estadisticasWindow.setVisible(true);
    }

    public void backFromEstadisticasButton(MouseEvent mouseEvent){
        estadisticasRutas.getData().clear();
        estadisticasWindow.setVisible(false);
        menuPrincipalAdmin.setVisible(true);
    }

    public void passToNoticiasWindow2(MouseEvent mouseEvent) {
        menuPrincipalAdmin.setVisible(false);
        noticiasWindow.setVisible(true);
        ingresoPorAdmin=true;
    }


    class Ruta extends RecursiveTreeObject<Ruta>{
        StringProperty CodigoRuta;
        StringProperty Dia;
        StringProperty Inicio;
        StringProperty Fin;

        public Ruta(String CodigoRuta, String Dia, String Inicio, String Fin){
            this.CodigoRuta = new SimpleStringProperty(CodigoRuta);
            this.Dia = new SimpleStringProperty(Dia);
            this.Inicio = new SimpleStringProperty(Inicio);
            this.Fin = new SimpleStringProperty(Fin);
        }
    }

        public void CrearListaRutas (ArrayList < String > valores) {

            JFXTreeTableColumn<Ruta, String> codRu = new JFXTreeTableColumn<>("Codigo ruta");
            codRu.setPrefWidth(150);
            codRu.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ruta, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ruta, String> param) {
                    return param.getValue().getValue().CodigoRuta;
                }
            });

            JFXTreeTableColumn<Ruta, String> diaRu = new JFXTreeTableColumn<>("Dia");
            diaRu.setPrefWidth(150);
            diaRu.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ruta, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ruta, String> param) {
                    return param.getValue().getValue().Dia;
                }
            });

            JFXTreeTableColumn<Ruta, String> inicioRu = new JFXTreeTableColumn<>("Hora de inicio");
            inicioRu.setPrefWidth(150);
            inicioRu.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ruta, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ruta, String> param) {
                    return param.getValue().getValue().Inicio;
                }
            });

            JFXTreeTableColumn<Ruta, String> finRu = new JFXTreeTableColumn<>("Hora de Fin");
            finRu.setPrefWidth(150);
            finRu.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ruta, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ruta, String> param) {
                    return param.getValue().getValue().Fin;
                }
            });

            ObservableList<Ruta> rutas = FXCollections.observableArrayList();

            //System.out.println("prueba aqui"+valores.get(0));
            for (int i = 0; i < valores.size(); i = i + 4) {
                rutas.add(new Ruta(valores.get(i), valores.get(i + 1), valores.get(i + 2), valores.get(i + 3)));
            }

            Platform.runLater(() -> {
                final TreeItem<Ruta> root = new RecursiveTreeItem<Ruta>(rutas, RecursiveTreeObject::getChildren);
                resultadosBuscarRuta.getColumns().setAll(codRu, diaRu, inicioRu, finRu);
                resultadosBuscarRuta.setRoot(root);
                resultadosBuscarRuta.setShowRoot(false);
            });
        }


    public void backFromRutaToMenuPrincipal(MouseEvent mouseEvent) {
        //BuscarRuta.setText("");
        buscarRutaWindow.setVisible(false);
        menuPrincipal.setVisible(true);
        resultadosBuscarRuta.setDisable(true);
        resultadosBuscarRuta.setRoot(null);
    }


    public void passToSolicitarArticuladoWindow(MouseEvent mouseEvent) {//Metodo de solicitarArticuladoButton, pasa a solicitarArticuladoWindow
        solicitarArticuladoWindow.setVisible(true);
        menuPrincipal.setVisible(false);

        new AutoCompleteComboBoxListener<>(nombreEstacionSolicitud);
        new AutoCompleteComboBoxListener<>(nombreRutaSolicitud);
    }

    public void backToBeginSession(MouseEvent mouseEvent) {
        menuPrincipal.setVisible(false);
        inicioSesionWindow.setVisible(true);
        model.setLogged(false);
    }

    public void buscarEstacion(MouseEvent mouseEvent) {//Evento a boton BuscarEstacionButton, busca estaciones
        String estacion = nombreEstacion.getText();
        nombreEstacion.clear();
        
        StationListRequest slr = new StationListRequest(model.getCurrentIdRequest(), estacion);
        model.createRequest(slr);
    }

    public void backFromEstacionToMenuPrincipal(MouseEvent mouseEvent) {//volver de buscarEstacionWindow a menuPrincipal
        buscarEstacionWindow.setVisible(false);
        menuPrincipal.setVisible(true);
        ResultadosBuscarEstacion.setText("");
    }

    public void passToPlanearRutaWindow(MouseEvent mouseEvent) {//Metodo de planearRutaButton, pasa a planearRutaWindow
        menuPrincipal.setVisible(false);
        planearRutaWindow.setVisible(true);

        new AutoCompleteComboBoxListener<>(estacionInicio);
        new AutoCompleteComboBoxListener<>(estacionDestino);
    }

    public void planearRuta(MouseEvent mouseEvent) { //Metodo del boton planearRutaButton, muestra resultados de mejores rutas
        estacionIncicioRes.setVisible(true);
        estacionDestinoRes.setVisible(true);
        estacionIncicioRes.setText(estacionIncicioRes.getText()+" "+estacionInicio.getValue());
        estacionDestinoRes.setText(estacionDestinoRes.getText()+" "+estacionDestino.getValue());
        Random r = new Random();
        int ruta = r.nextInt(217)+1;
        rutaAleatoria.setText(model.rutas.get(ruta));
    }


    public void backFromPlanearToMenuPrincipal(MouseEvent mouseEvent) {
        estacionIncicioRes.setText("Sale de la estacion");
        estacionDestinoRes.setText("Hasta la estación");
        rutaAleatoria.setText("");
        estacionIncicioRes.setVisible(false);
        estacionDestinoRes.setVisible(false);
        planearRutaWindow.setVisible(false);
        menuPrincipal.setVisible(true);
    }


    public void crearSolicitud(MouseEvent mouseEvent) {
        model.AddInToWriteQueue(new RequestRoute(nombreRutaSolicitud.getValue(), nombreEstacionSolicitud.getValue(), model.getCurrentIdRequest()));
        nombreRutaSolicitud.getEditor().clear();
        nombreEstacionSolicitud.getEditor().clear();
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

    private Model model;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new Model(this);
        model.start();
    }

}
