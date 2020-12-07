package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import dad.javafx.micv.model.Experiencia;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

public class ExperienciaController implements Initializable{
	
	

//view
    @FXML
    private BorderPane view;

    @FXML
    private TableView<Experiencia> experienciaTable;

    @FXML
    private TableColumn<Experiencia, LocalDate> desdeColumn;

    @FXML
    private TableColumn<Experiencia, LocalDate> hastaColumn;

    @FXML
    private TableColumn<Experiencia, String> denominacionColumn;

    @FXML
    private TableColumn<Experiencia, String> empleadorColumn;

    @FXML
    private Button addExpButton;

    @FXML
    private Button removeExpButton;

	
    //model
    private ListProperty<Experiencia>experiencia= new SimpleListProperty<>(FXCollections.observableArrayList());
    private ObjectProperty<Experiencia>experienciaObj=new SimpleObjectProperty<>();
    
    
	public ExperienciaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienciaView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		desdeColumn.setCellValueFactory(v -> v.getValue().desdeProperty());
		hastaColumn.setCellValueFactory(v -> v.getValue().hastaProperty());
		denominacionColumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
		empleadorColumn.setCellValueFactory(v -> v.getValue().empleadorProperty());
		
		desdeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		hastaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		denominacionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		empleadorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		addExpButton.setOnAction(e->onAddExpAction());
		removeExpButton.setOnAction(e->onRemoveExpAction());
		
		experiencia.addListener((o,ov,nv)-> onExperienciaChanged(o,ov,nv));
	}
	
private void onRemoveExpAction() {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Borrar experiencia");
	alert.setHeaderText("Va a borrar una experiencia");
	alert.setContentText("¿Está seguro de que quiere hacerlo?");

	Optional<ButtonType> result = alert.showAndWait();
	
	if (result.get() == ButtonType.OK){
		Experiencia borraExp= experienciaObj.get();
		getExperiencia().remove(borraExp);
	} 
	}


private void onAddExpAction() {

	Dialog<Experiencia> dialog= new Dialog<>();
	dialog.setTitle("Nueva Experiencia");
	
	Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
	stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
	stage.setMinWidth(550);
	stage.setMinHeight(200);
	
	ButtonType addButton = new ButtonType("Crear", ButtonData.OK_DONE);
	dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
	
	GridPane root = new GridPane();
	root.setHgap(10);
	root.setVgap(10);
	root.setPadding(new Insets(20, 10, 10, 10));
	
	TextField denominacionText = new TextField();
	TextField empleadorText = new TextField();
	DatePicker desdeDate = new DatePicker();
	DatePicker hastaDate = new DatePicker();
	
	Node addButtonN = dialog.getDialogPane().lookupButton(addButton);
	addButtonN.setDisable(true);
	
	addButtonN.disableProperty().bind(
			denominacionText.textProperty().isEmpty().or(
			empleadorText.textProperty().isEmpty()).or(
			desdeDate.valueProperty().isNull()).or(
			hastaDate.valueProperty().isNull()));
	
	Label denominacionLabel= new Label("Denominación");
	Label empleadorLabel= new Label("Empleador");
	Label desdeLabel= new Label("Desde");
	Label hastaLabel= new Label("Hasta");
	
	
	
	root.add(denominacionLabel, 0, 0);
	root.add(denominacionText, 1, 0);
	root.add(empleadorLabel, 0, 1);
	root.add(empleadorText, 1, 1);
	root.add(desdeLabel, 0, 2);
	root.add(desdeDate, 1, 2);
	root.add(hastaLabel, 0, 3);
	root.add(hastaDate, 1, 3);
	
	GridPane.setColumnSpan(denominacionText, 2);
	GridPane.setColumnSpan(empleadorText, 2);
	
	ColumnConstraints[] cols = {
			new ColumnConstraints(),
			new ColumnConstraints(),
			new ColumnConstraints()
	};
	
	cols[0].setHalignment(HPos.RIGHT);
	cols[1].setHgrow(Priority.ALWAYS);
	cols[1].setFillWidth(true);
	
	root.getColumnConstraints().setAll(cols);
	
	dialog.getDialogPane().setContent(root);
	
	Platform.runLater(() -> denominacionText.requestFocus());
	
	
	dialog.setResultConverter(dialogButton -> {
		if (dialogButton == addButton) {
			Experiencia addExp = new Experiencia();
			addExp.setDenominacion(denominacionText.getText());
			addExp.setEmpleador(empleadorText.getText());
			addExp.setDesde(desdeDate.getValue());
			addExp.setHasta(hastaDate.getValue());
			return addExp;
		}
		return null;
	});
	
	
Optional<Experiencia> result = dialog.showAndWait();
	
	if (result.isPresent())
		getExperiencia().add(result.get());
	
}
	
	
	


private void onExperienciaChanged(ObservableValue<? extends ObservableList<Experiencia>> o,
			ObservableList<Experiencia> ov, ObservableList<Experiencia> nv) {
		
		if(ov!=null) {
			
			experienciaTable.itemsProperty().unbind();
			experienciaObj.unbind();
		}
		
		if(nv!=null) {
			experienciaTable.itemsProperty().bind(experiencia);		
			experienciaObj.bind(experienciaTable.getSelectionModel().selectedItemProperty());
		}

}


public BorderPane getView() {
	return view;
}


public final ListProperty<Experiencia> experienciaProperty() {
	return this.experiencia;
}



public final ObservableList<Experiencia> getExperiencia() {
	return this.experienciaProperty().get();
}



public final void setExperiencia(final ObservableList<Experiencia> experiencia) {
	this.experienciaProperty().set(experiencia);
}
	

}
