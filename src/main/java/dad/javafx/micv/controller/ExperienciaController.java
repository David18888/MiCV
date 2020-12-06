package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dad.javafx.micv.model.Experiencia;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.LocalDateStringConverter;

public class ExperienciaController implements Initializable{
	
	

//view
    @FXML
    private BorderPane view;

    @FXML
    private TableView<?> experienciaTable;

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
		//QUITAR ELEMENTO
	}


private void onAddExpAction() {
		
	}


private void onExperienciaChanged(ObservableValue<? extends ObservableList<Experiencia>> o,
			ObservableList<Experiencia> ov, ObservableList<Experiencia> nv) {
		//DESBINDEAR Y BINDEAR
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
