package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import dad.javafx.micv.model.*;
import dad.javafx.micv.model.Conocimiento.Nivel;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class ConocimientosController implements Initializable{
	
	//view
	    @FXML
	    private BorderPane view;

	    @FXML
	    private TableView<Conocimiento> conocimientosTable;

	    @FXML
	    private TableColumn<Conocimiento, String> denominacionColumn;

	    @FXML
	    private TableColumn<Conocimiento, Nivel> nivelColumn;

	    @FXML
	    private Button addConButton;

	    @FXML
	    private Button addIdButton;

	    @FXML
	    private Button removeConButton;
	
	//model
	    
		private ListProperty<Conocimiento> conocimiento = new SimpleListProperty<>(FXCollections.observableArrayList());
		private ObjectProperty<Conocimiento> conocimientoObj = new SimpleObjectProperty<>();
	
	public ConocimientosController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConocimientosView.fxml"));
		loader.setController(this);
		loader.load();
		
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		denominacionColumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
		nivelColumn.setCellValueFactory(v -> v.getValue().nivelProperty());

		denominacionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nivelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Nivel.values()));

		this.conocimiento.addListener((o, ov, nv) -> onConocimientoChanged(o, ov, nv));
		
		addConButton.setOnAction(e->OnAddConAction());
		addIdButton.setOnAction(e->onAddIdAction());
		removeConButton.setOnAction(e->onRemoveConAction());
		
	}
	
private void onRemoveConAction() {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Borrar conocimiento");
	alert.setHeaderText("Va a borrar un conocimiento ");
	alert.setContentText("¿Está seguro de que quiere hacerlo?");

	Optional<ButtonType> result = alert.showAndWait();
	
	if (result.get() == ButtonType.OK){
		Conocimiento borraConocimiento= conocimientoObj.get();
		conocimiento.get().remove(borraConocimiento);
	} 
	}


private void onAddIdAction() {
		
	 Dialog <Idioma> dialog= new Dialog<>();
	 dialog.setTitle("Nuevo idioma: ");
	 Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
	stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
	ButtonType addButton = new ButtonType("Crear", ButtonData.OK_DONE);
	dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
	
	
	GridPane root = new GridPane();
	root.setHgap(5);
	root.setVgap(10);
	root.setPadding(new Insets(20, 10, 10, 10));

	TextField denominacionText = new TextField();
	ComboBox<Nivel> nivelBox = new ComboBox<>();
	Button removeComboButton = new Button("X");

	nivelBox.getItems().addAll(Nivel.values());

	Node addButonN = dialog.getDialogPane().lookupButton(addButton);
	addButonN.setDisable(true);

	addButonN.disableProperty()
			.bind(denominacionText.textProperty().isEmpty().or(nivelBox.valueProperty().isNull()));

	removeComboButton.setOnAction(e -> {
		nivelBox.setValue(null);
	});
	
	Label denominacionLabel= new Label("Denominación");
	Label nivelLabel= new Label("Nivel");
	
	root.add(denominacionLabel, 0, 0);
	root.add(denominacionText, 1, 0);
	root.add(nivelLabel, 0, 1);
	root.add(nivelBox, 1, 1);
	root.add(removeComboButton, 2, 1);

	GridPane.setColumnSpan(denominacionText, 2);

	ColumnConstraints[] cols = { new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints() };

	cols[0].setHalignment(HPos.RIGHT);
	cols[2].setHgrow(Priority.ALWAYS);
	cols[2].setFillWidth(true);
	cols[2].setHalignment(HPos.LEFT);

	root.getColumnConstraints().setAll(cols);

	dialog.getDialogPane().setContent(root);

	Platform.runLater(() -> denominacionText.requestFocus());
	

	dialog.setResultConverter(dialogButton -> {
		if (dialogButton == addButton) {
			Idioma addIdioma = new Idioma();
			addIdioma.setDenominacion(denominacionText.getText());
			addIdioma.setNivel(nivelBox.getValue());
			return addIdioma;
		}
		return null;
	});
	
	Optional<Idioma> result = dialog.showAndWait();

	if (result.isPresent())
		getConocimiento().add(result.get());
	
	
	}


private void OnAddConAction() {
	 Dialog <Conocimiento> dialog= new Dialog<>();
	 dialog.setTitle("Nuevo conocimiento: ");
	 Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
	stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
	ButtonType addButton = new ButtonType("Crear", ButtonData.OK_DONE);
	dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
	
	
	GridPane root = new GridPane();
	root.setHgap(5);
	root.setVgap(10);
	root.setPadding(new Insets(20, 10, 10, 10));

	TextField denominacionText = new TextField();
	ComboBox<Nivel> nivelBox = new ComboBox<>();
	Button removeComboButton = new Button("X");

	nivelBox.getItems().addAll(Nivel.values());

	Node addButonN = dialog.getDialogPane().lookupButton(addButton);
	addButonN.setDisable(true);

	addButonN.disableProperty()
			.bind(denominacionText.textProperty().isEmpty().or(nivelBox.valueProperty().isNull()));

	removeComboButton.setOnAction(e -> {
		nivelBox.setValue(null);
	});
	
	Label denominacionLabel= new Label("Denominación");
	Label nivelLabel= new Label("Nivel");
	
	root.add(denominacionLabel, 0, 0);
	root.add(denominacionText, 1, 0);
	root.add(nivelLabel, 0, 1);
	root.add(nivelBox, 1, 1);
	root.add(removeComboButton, 2, 1);

	GridPane.setColumnSpan(denominacionText, 2);

	ColumnConstraints[] cols = { new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints() };

	cols[0].setHalignment(HPos.RIGHT);
	cols[2].setHgrow(Priority.ALWAYS);
	cols[2].setFillWidth(true);
	cols[2].setHalignment(HPos.LEFT);

	root.getColumnConstraints().setAll(cols);

	dialog.getDialogPane().setContent(root);

	Platform.runLater(() -> denominacionText.requestFocus());
	

	dialog.setResultConverter(dialogButton -> {
		if (dialogButton == addButton) {
			Conocimiento addConocimiento = new Conocimiento();
			addConocimiento.setDenominacion(denominacionText.getText());
			addConocimiento.setNivel(nivelBox.getValue());
			return addConocimiento;
		}
		return null;
	});
	
	Optional<Conocimiento> result = dialog.showAndWait();

	if (result.isPresent())
		getConocimiento().add(result.get());
	
	}


private void onConocimientoChanged(ObservableValue<? extends ObservableList<Conocimiento>> o,
			ObservableList<Conocimiento> ov, ObservableList<Conocimiento> nv) {
	
	if(ov!=null) {
		conocimientosTable.itemsProperty().unbind();
		conocimientoObj.unbind();
	}
	if(nv!=null) {
		conocimientosTable.itemsProperty().bind(conocimiento);
		conocimientoObj.bind(conocimientosTable.getSelectionModel().selectedItemProperty());
		
	}
	
	}


public BorderPane getView() {
	return view;
}


public final ListProperty<Conocimiento> conocimientoProperty() {
	return this.conocimiento;
}



public final ObservableList<Conocimiento> getConocimiento() {
	return this.conocimientoProperty().get();
}



public final void setConocimiento(final ObservableList<Conocimiento> conocimiento) {
	this.conocimientoProperty().set(conocimiento);
}
	

}
