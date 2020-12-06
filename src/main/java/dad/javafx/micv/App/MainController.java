package dad.javafx.micv.App;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.google.gson.JsonSyntaxException;

import dad.javafx.micv.model.CV;
import dad.javafx.micv.controller.ConocimientosController;
import dad.javafx.micv.controller.ContactoController;
import dad.javafx.micv.controller.ExperienciaController;
import dad.javafx.micv.controller.FormacionController;
import dad.javafx.micv.controller.PersonalController;
import dad.javafx.micv.utils.JSONUtils;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {
	
	// controllers
	
	private PersonalController personalController = new PersonalController();
	private ContactoController contactoController= new ContactoController();
	private FormacionController formacionController= new FormacionController();
	private ExperienciaController experienciaController= new ExperienciaController();
	private ConocimientosController conocimientosController= new ConocimientosController();
	
	// model
	
	private ObjectProperty<CV> cv = new SimpleObjectProperty<>();
	
	// view
	
    @FXML
    private BorderPane view;

    @FXML
    private Tab personalTab;

    @FXML
    private Tab contactoTab;

    @FXML
    private Tab formacionTab;

    @FXML
    private Tab experienciaTab;

    @FXML
    private Tab conocimientosTab;

	public MainController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		personalTab.setContent(personalController.getView());
		contactoTab.setContent(contactoController.getView());
		formacionTab.setContent(formacionController.getView());
		experienciaTab.setContent(experienciaController.getView());
		conocimientosTab.setContent(conocimientosController.getView());
		
		cv.addListener((o, ov, nv) -> onCVChanged(o, ov, nv));
		
		cv.set(new CV());
	}
	
	private void onCVChanged(ObservableValue<? extends CV> o, CV ov, CV nv) {

		if (ov != null) {
			personalController.personalProperty().unbind();
	
		}
		
		if (nv != null) {
			personalController.personalProperty().bind(nv.personalProperty());
			// TODO bindear el resto de controladores
		}
		
	}

	public BorderPane getView() {
		return view;
	}

    @FXML
    void onAbrirAction(ActionEvent event) {

    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Abrir un currículum");
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Currículum (*.cv)", "*.cv"));
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Todos los archivos (*.*)", "*.*"));
    	File cvFile = fileChooser.showOpenDialog(App.getPrimaryStage());
    	if (cvFile != null) {
    		
    		try {
    			cv.set(JSONUtils.fromJson(cvFile, CV.class));
    			App.info("Se ha abierto el fichero " + cvFile.getName() + " correctamente.", "Pues eso...");
			} catch (JsonSyntaxException|IOException e) {
				App.error("Ha ocurrido un error al abrir " + cvFile, e.getMessage());
			}
    		
    	}
    	
    }

    @FXML
    void onAcercaDeAction(ActionEvent event) {

    }

    @FXML
    void onCerrarAction(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Salir");
    	alert.setHeaderText("Va a salir de la aplicación");
    	alert.setContentText("¿Estás seguro que quieres cerrar?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
        	Platform.exit();
        	
    	}
    

    }

    @FXML
    void onGuardarAction(ActionEvent event) {

    }

    @FXML
    void onGuardarComoAction(ActionEvent event) {

	    	FileChooser fileChooser = new FileChooser();
	    	fileChooser.setTitle("Guardar un currículum");
	    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Currículum (*.cv)", "*.cv"));
	    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Todos los archivos (*.*)", "*.*"));
	    	File cvFile = fileChooser.showSaveDialog(App.getPrimaryStage());
	    	if (cvFile != null) {

	    		try {
	    			JSONUtils.toJson(cvFile, cv.get());
				} catch (JsonSyntaxException|IOException e) {
					App.error("Ha ocurrido un error al guardar " + cvFile, e.getMessage());
				}
	    		
	    	}
    	
    }

    @FXML
    void onNuevoAction(ActionEvent event) {
    	System.out.println("Nuevo");
    	cv.set(new CV());
    }
    
}