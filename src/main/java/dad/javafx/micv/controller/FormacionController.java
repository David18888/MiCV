package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dad.javafx.micv.model.Titulo;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.LocalDateStringConverter;

public class FormacionController implements Initializable  {

	//view
	@FXML
    private BorderPane view;

    @FXML
    private TableView<Titulo> formacionTable;

    @FXML
    private TableColumn<Titulo, LocalDate> desdeColumn;

    @FXML
    private TableColumn<Titulo,LocalDate> hastaColumn;

    @FXML
    private TableColumn<Titulo,String> denominacionColumn;

    @FXML
    private TableColumn<Titulo, String> organizadorColumn;

    @FXML
    private Button addFButton;

    @FXML
    private Button removeFButton;
	
	
	//model
	   	private ListProperty<Titulo> titulos = new SimpleListProperty<>(FXCollections.observableArrayList());    
	   	private ObjectProperty<Titulo> titulo= new SimpleObjectProperty<>();
	
	public FormacionController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		desdeColumn.setCellValueFactory(v-> v.getValue().desdeProperty());
		hastaColumn.setCellValueFactory(v-> v.getValue().hastaProperty());
		denominacionColumn.setCellValueFactory(v-> v.getValue().denominacionProperty());
		organizadorColumn.setCellValueFactory(v-> v.getValue().organizadorProperty());
		
		desdeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		hastaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		denominacionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		organizadorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		addFButton.setOnAction(e-> onAddFAction());
		removeFButton.setOnAction(e->onRemoveFAction());
		
		
		titulos.addListener((o,ov,nv)-> onTituloChanged(o,ov,nv));
	}

	private void onRemoveFAction() {
	
	}

	private void onAddFAction() {
		
	
	}

	private void onTituloChanged(ObservableValue<? extends ObservableList<Titulo>> o, ObservableList<Titulo> ov,
			ObservableList<Titulo> nv) {
		if(ov!=null) {
			formacionTable.setItems(null);
			titulo.unbind();
			removeFButton.disableProperty().unbind();
		}
		
		if(nv!=null) {
			formacionTable.setItems(nv);
			titulo.bind(formacionTable.getSelectionModel().selectedItemProperty());
			removeFButton.disableProperty().bind(Bindings.isEmpty(formacionTable.getItems()));
		}
		
	}

	public BorderPane getView() {
		return view;
	}

	public final ListProperty<Titulo> titulosProperty() {
		return this.titulos;
	}
	

	public final ObservableList<Titulo> getTitulos() {
		return this.titulosProperty().get();
	}
	

	public final void setTitulos(final ObservableList<Titulo> titulos) {
		this.titulosProperty().set(titulos);
	}
	

}
