package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import dad.javafx.micv.App.App;
import dad.javafx.micv.model.Contacto;
import dad.javafx.micv.model.Email;
import dad.javafx.micv.model.Telefono;
import dad.javafx.micv.model.Telefono.TipoTelefono;
import dad.javafx.micv.model.Web;

public class ContactoController implements Initializable {
	
//model

	private ObjectProperty<Contacto> contacto= new SimpleObjectProperty<>();
	private ObjectProperty<Telefono> telefono= new SimpleObjectProperty<>();
	private ObjectProperty<Email> email= new SimpleObjectProperty<>();
	private ObjectProperty <Web> url= new SimpleObjectProperty<>();
	

//view
	 @FXML
	    private SplitPane view;

	    @FXML
	    private TableView<Telefono> telefonosTable;

	    @FXML
	    private TableColumn<Telefono,String> numeroColumn;

	    @FXML
	    private TableColumn<Telefono, TipoTelefono> tipoColumn;

	    @FXML
	    private Button addTlfButton;

	    @FXML
	    private Button removeTlfButton;

	    @FXML
	    private TableView<Email> emailTable;

	    @FXML
	    private TableColumn<Email, String> emailColumn;

	    @FXML
	    private Button addEmailButton;

	    @FXML
	    private Button removeEmailButton;

	    @FXML
	    private TableView<Web> urlTable;

	    @FXML
	    private TableColumn<Web, String> URLColumn;

	    @FXML
	    private Button addWebButton;

	    @FXML
	    private Button removeWebButton;

	
    

	
	
	
	
	
		public ContactoController() throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContactoView.fxml"));
			loader.setController(this);
			loader.load();
			
		}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
      
		numeroColumn.setCellValueFactory(v -> v.getValue().numeroProperty());
		tipoColumn.setCellValueFactory(v -> v.getValue().tipoProperty());
		
		numeroColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		tipoColumn.setCellFactory(ComboBoxTableCell.forTableColumn(TipoTelefono.values()));
		
	
		emailColumn.setCellValueFactory(v -> v.getValue().direccionProperty());
		emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		

		URLColumn.setCellValueFactory(v -> v.getValue().urlProperty());
		URLColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		
		this.contacto.addListener((o,ov,nv)->onContactoChanged(o,ov,nv));
		
		/*addTlfButton.setOnAction(e->onAddtlfAction());
		addEmailButton.setOnAction(e->onAddEmailAction());
		addWebButton.setOnAction(e->onAddWebAction());
		
		
		removeTlfButton.setOnAction(e->onRemovetlfAction());
		removeEmailButton.setOnAction(e->onRemoveEmailAction());
		removeWebButton.setOnAction(e->onRemoveWebAction());
		*/
	}
	
	
	
	
	
	@FXML
	private void  onRemoveTlfAction() {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Borrar teléfono");
		alert.setHeaderText("Va a borrar un teléfono");
		alert.setContentText("¿Está seguro de que quiere hacerlo?");

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK){
			Telefono borraTelefono= telefono.get();
			contacto.get().getTelefonos().remove(borraTelefono);
		} 
		
		
		
		
	}


@FXML
	private void onRemoveEmailAction() {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Borrar email");
		alert.setHeaderText("Va a borrar un email");
		alert.setContentText("¿Está seguro de que quiere hacerlo?");

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK){
			Email borraEmail= email.get();
			contacto.get().getEmails().remove(borraEmail);
		} 
		
		
	}


@FXML
	private void onRemoveWebAction() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Borrar web");
		alert.setHeaderText("Va a borrar una dirección web ");
		alert.setContentText("¿Está seguro de que quiere hacerlo?");

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK){
			Web borraWeb= url.get();
			contacto.get().getWebs().remove(borraWeb);
		} 
	}


@FXML
	private void onAddWebAction() {
		TextInputDialog dialog = new TextInputDialog();
		
		dialog.setTitle("Nueva web");
		dialog.setHeaderText("Crear una nueva dirección web.");
		dialog.setContentText("URL:");
		dialog.initOwner(App.getPrimaryStage());
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		
		dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
				dialog.getEditor().textProperty().isEmpty()
		);
		
		dialog.getEditor().setText("http://");
		
		Platform.runLater(() -> dialog.getEditor().requestFocus());
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			Web web = new Web(result.get());
			contacto.get().websProperty().add(web);
		}
		
		
	}
	@FXML
	private void onAddEmailAction() {
	TextInputDialog dialog= new TextInputDialog();
	dialog.setTitle("Nuevo e-mail");
	dialog.setHeaderText("Crear una nueva dirección de correo");
	dialog.setContentText("E-mail");
	dialog.initOwner(App.getPrimaryStage());
	
	Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
	stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
	
	dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
			dialog.getEditor().textProperty().isEmpty()
	);
	
	Optional<String> result = dialog.showAndWait();
	if (result.isPresent()) {
		Email email = new Email(result.get());
		contacto.get().emailsProperty().add(email);
	}
	
	}

@FXML
	private void onAddtlfAction() {
Dialog<Pair<String, TipoTelefono>> dialog = new Dialog<>();
		
		dialog.setTitle("Nuevo teléfono");
		dialog.setContentText("Introduzca el nuevo número de teléfono.");
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		
		ButtonType addButton = new ButtonType("Añadir", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
		
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(20, 150, 10, 10));
		
		Label numeroLabel= new Label("Número: ");
		Label tipoLabel= new Label("Tipo: ");
		
		TextField numeroText = new TextField();
		numeroText.setPromptText("Número de teléfono");
		
		ComboBox<TipoTelefono> tipoTelefonoBox = new ComboBox<>();
		tipoTelefonoBox.getItems().addAll(TipoTelefono.values());
		tipoTelefonoBox.setPromptText("Seleccione un tipo");
		
		Node addButtonN = dialog.getDialogPane().lookupButton(addButton);
		addButtonN.setDisable(true);
		
		addButtonN.disableProperty().bind(
				numeroText.textProperty().isEmpty().or(
				tipoTelefonoBox.valueProperty().isNull()));
		
		root.add(numeroLabel, 0, 0);
		root.add(numeroText, 1, 0);
		root.add(tipoLabel, 0, 1);
		root.add(tipoTelefonoBox, 1, 1);
		
		dialog.getDialogPane().setContent(root);
		
		Platform.runLater(() -> numeroText.requestFocus());
		
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == addButton) {
				return new Pair<>(numeroText.getText(), tipoTelefonoBox.getSelectionModel().getSelectedItem());
			}
			return null;
		});
		
		Optional<Pair<String, TipoTelefono>> result = dialog.showAndWait();
		
		if (result.isPresent()) {
			Telefono addTelefono= new Telefono();
			addTelefono.setNumero(result.get().getKey());
			addTelefono.setTipo(result.get().getValue());
			contacto.get().getTelefonos().add(addTelefono);
		}
		
		
		
	}


	
	
	
	

	private void onContactoChanged(ObservableValue<? extends Contacto> o, Contacto ov, Contacto nv) {
		if(ov!=null) {
			
			telefonosTable.itemsProperty().unbind();
			emailTable.itemsProperty().unbind();
			urlTable.itemsProperty().unbind();
			removeTlfButton.disableProperty().unbind();
			removeEmailButton.disableProperty().unbind();
			removeWebButton.disableProperty().unbind();
			
			
			telefono.unbind();
			email.unbind();
			url.unbind();
			
			
			
		}
		
		
		if (nv != null) {
			telefonosTable.itemsProperty().bind(nv.telefonosProperty());
			emailTable.itemsProperty().bind(nv.emailsProperty());
			urlTable.itemsProperty().bind(nv.websProperty());
			removeTlfButton.disableProperty().bind(Bindings.isEmpty(telefonosTable.getItems()));
			removeEmailButton.disableProperty().bind(Bindings.isEmpty(emailTable.getItems()));
			removeWebButton.disableProperty().bind(Bindings.isEmpty(urlTable.getItems()));
			
			telefono.bind(telefonosTable.getSelectionModel().selectedItemProperty());
			email.bind(emailTable.getSelectionModel().selectedItemProperty());
			url.bind(urlTable.getSelectionModel().selectedItemProperty());
		
		}
	}



	public SplitPane getView() {
		return view;
	}



	public final ObjectProperty<Contacto> contactoProperty() {
		return this.contacto;
	}
	



	public final Contacto getContacto() {
		return this.contactoProperty().get();
	}
	



	public final void setContacto(final Contacto contacto) {
		this.contactoProperty().set(contacto);
	}
	



	

}
