package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import dad.javafx.micv.model.Titulo;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
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
	   	private ObjectProperty<Titulo> tituloObj= new SimpleObjectProperty<>();
	
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
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Borrar formación");
		alert.setHeaderText("Va a borrar una formación");
		alert.setContentText("¿Está seguro de que quiere hacerlo?");

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK){
			Titulo borraTitulo= tituloObj.get();
			titulos.get().remove(borraTitulo);
		} 
		
		
	
	}

	private void onAddFAction() {
		
		Dialog<Titulo> dialog= new Dialog<>();
		dialog.setTitle("Nuevo título");
		
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
		TextField organizadorText = new TextField();
		DatePicker desdeDate = new DatePicker();
		DatePicker hastaDate = new DatePicker();
		
		Node addButtonN = dialog.getDialogPane().lookupButton(addButton);
		addButtonN.setDisable(true);
		
		addButtonN.disableProperty().bind(
				denominacionText.textProperty().isEmpty().or(
				organizadorText.textProperty().isEmpty()).or(
				desdeDate.valueProperty().isNull()).or(
				hastaDate.valueProperty().isNull()));
		
		Label denominacionLabel= new Label("Denominación");
		Label organizadorLabel= new Label("Organizador");
		Label desdeLabel= new Label("Desde");
		Label hastaLabel= new Label("Hasta");
		
		
		
		root.add(denominacionLabel, 0, 0);
		root.add(denominacionText, 1, 0);
		root.add(organizadorLabel, 0, 1);
		root.add(organizadorText, 1, 1);
		root.add(desdeLabel, 0, 2);
		root.add(desdeDate, 1, 2);
		root.add(hastaLabel, 0, 3);
		root.add(hastaDate, 1, 3);
		
		GridPane.setColumnSpan(denominacionText, 2);
		GridPane.setColumnSpan(organizadorText, 2);
		
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
				Titulo addTitulo = new Titulo();
				addTitulo.setDenominacion(denominacionText.getText());
				addTitulo.setOrganizador(organizadorText.getText());
				addTitulo.setDesde(desdeDate.getValue());
				addTitulo.setHasta(hastaDate.getValue());
				return addTitulo;
			}
			return null;
		});
		
		
	Optional<Titulo> result = dialog.showAndWait();
		
		if (result.isPresent())
			titulos.get().add(result.get());
		
	}
		
		
	
	
	

	private void onTituloChanged(ObservableValue<? extends ObservableList<Titulo>> o, ObservableList<Titulo> ov,
			ObservableList<Titulo> nv) {
		if(ov!=null) {
			formacionTable.itemsProperty().unbindBidirectional(titulos);
			tituloObj.unbind();
		}
		
		if(nv!=null) {
			
		formacionTable.itemsProperty().bindBidirectional(titulos);
		tituloObj.bind(formacionTable.getSelectionModel().selectedItemProperty());	
			
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
