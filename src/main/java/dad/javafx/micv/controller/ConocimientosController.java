package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import dad.javafx.micv.model.*;
import dad.javafx.micv.model.Conocimiento.Nivel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;

public class ConocimientosController implements Initializable{
	
	//view
	    @FXML
	    private BorderPane view;

	    @FXML
	    private TableView<?> conocimientosTable;

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
		
	}


private void onAddIdAction() {
		
	}


private void OnAddConAction() {
		
	}


private void onConocimientoChanged(ObservableValue<? extends ObservableList<Conocimiento>> o,
			ObservableList<Conocimiento> ov, ObservableList<Conocimiento> nv) {
		
	
	//BINDEAR Y DESBINDEAR
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
