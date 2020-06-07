package GUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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

public class RegistroController implements Initializable {
    @FXML
    private JFXTextField contrasenna;

    @FXML
    private JFXTextField nombre;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXButton btnRegistro;

    @FXML
    private JFXButton atrasRegistroButton;

    /**
     * Initializes the controller class.
     */
    public void registrarUsuario(MouseEvent mouseEvent) {
    }

    public void regresar(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("MenuInicio.fxml"));
        Parent root= loader.load();
        MenuInicioController controller= loader.getController();

        Scene scene= new Scene(root);
        Stage stage= new Stage();

        stage.setScene(scene);
        stage.show();


        Stage myStage= (Stage)this.atrasRegistroButton.getScene().getWindow();
        myStage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }



}
