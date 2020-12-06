package dad.javafx.micv.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.model.Nacionalidad;
import dad.javafx.micv.model.Personal;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PersonalController implements Initializable {

	// model

	private ObjectProperty<Personal> personal = new SimpleObjectProperty<>();
	
	private ListProperty<String> paisesList = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
	private ArrayList<String> nacionalidadesList = new ArrayList<String>();
	// view

	   @FXML
	    private GridPane view;

	    @FXML
	    private TextField identificacionText;

	    @FXML
	    private TextField nombreText;

	    @FXML
	    private TextField apellidosText;

	    @FXML
	    private DatePicker fechaPicker;

	    @FXML
	    private TextArea direccionArea;

	    @FXML
	    private TextField codPostalText;

	    @FXML
	    private TextField localidadText;

	    @FXML
	    private ComboBox<String> paisCombo;

	    @FXML
	    private ListView<Nacionalidad> nacionalidadView;
	    
	    @FXML
	    private Button addNacButton;

	    @FXML
	    private Button removeNacButton;
	

	public PersonalController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonalView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		paisCombo.itemsProperty().bind(paisesList);
		cargarPaises();
		cargarNacionalidades();
		
		this.personal.addListener((o, ov, nv) -> onPersonalChanged(o, ov, nv));
		
		addNacButton.setOnAction(e-> onAddNacionalidadAction());
		removeNacButton.setOnAction(e->onRemoveNacionalidadAction());
		
		
		
		
	}

	private void onRemoveNacionalidadAction() {
		Nacionalidad nacBorrada = nacionalidadView.getSelectionModel().getSelectedItem();
		if( nacBorrada != null ) {
			getPersonal().getNacionalidades().remove(nacBorrada);
		}
	}

	private void onAddNacionalidadAction() {
		ChoiceDialog <String> add= new ChoiceDialog<>();
		add.setTitle("Nueva nacionalidad");
		add.setHeaderText("Añadir Nacionalidad");
		add.setContentText("Seleccione una nacionalidad");
		add.getItems().addAll(nacionalidadesList);
		add.setSelectedItem(nacionalidadesList.get(57));
		
		
		Optional <String> respuesta= add.showAndWait();
		if(respuesta.isPresent() && !respuesta.isEmpty()) {
			getPersonal().nacionalidadesProperty().add(new Nacionalidad(respuesta.get()));
		}
		
		
		
		
		
	}

	private void cargarNacionalidades() {
		try {
			
			String nacionalidades="src/main/resources/csv/nacionalidades.csv";	
			FileInputStream fis= new FileInputStream(nacionalidades);
			InputStreamReader isr= new InputStreamReader(fis,"UTF-8");
			BufferedReader br= new BufferedReader(isr);
			
			String line="";
			while((line=br.readLine())!=null) {
				nacionalidadesList.add(line);
				
			}
			fis.close();
			isr.close();
			br.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			
		
	}

	private void cargarPaises() {
		try {
		
		String paises="src/main/resources/csv/paises.csv";	
		FileInputStream fis= new FileInputStream(paises);
		InputStreamReader isr= new InputStreamReader(fis,"UTF-8");
		BufferedReader br= new BufferedReader(isr);
		
		String line="";
		while((line=br.readLine())!=null) {
			paisesList.add(line);
			
		}
		fis.close();
		isr.close();
		br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

	private void onPersonalChanged(ObservableValue<? extends Personal> o, Personal ov, Personal nv) {
		
		if (ov != null) {
			identificacionText.textProperty().unbindBidirectional(ov.identificacionProperty());
			nombreText.textProperty().unbindBidirectional(ov.nombreProperty());
			apellidosText.textProperty().unbindBidirectional(ov.apellidosProperty());
			fechaPicker.valueProperty().unbindBidirectional(ov.fechaNacimientoProperty());
			direccionArea.textProperty().unbindBidirectional(ov.direccionProperty());
			codPostalText.textProperty().unbindBidirectional(ov.codigoPostalProperty());
			localidadText.textProperty().unbindBidirectional(ov.localidadProperty());
			paisCombo.valueProperty().unbindBidirectional(ov.paisProperty());
			nacionalidadView.itemsProperty().unbindBidirectional(ov.nacionalidadesProperty());
		
		}

		if (nv != null) {
			identificacionText.textProperty().bindBidirectional(nv.identificacionProperty());
			nombreText.textProperty().bindBidirectional(nv.nombreProperty());
			apellidosText.textProperty().bindBidirectional(nv.apellidosProperty());
			fechaPicker.valueProperty().bindBidirectional(nv.fechaNacimientoProperty());
			direccionArea.textProperty().bindBidirectional(nv.direccionProperty());
			codPostalText.textProperty().bindBidirectional(nv.codigoPostalProperty());
			localidadText.textProperty().bindBidirectional(nv.localidadProperty());
			paisCombo.valueProperty().bindBidirectional(nv.paisProperty());
			nacionalidadView.itemsProperty().bindBidirectional(nv.nacionalidadesProperty());
		}
		
	}
	
	
	
	public GridPane getView() {
		return view;
	}

	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}

	public final Personal getPersonal() {
		return this.personalProperty().get();
	}

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
	}

}