package dad.javafx.micv.model;

import java.util.ArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Telefono {
	
private StringProperty numero= new SimpleStringProperty();
private ObjectProperty<TipoTelefono> tipo= new SimpleObjectProperty<TipoTelefono>();


public enum TipoTelefono{
	DOMICILIO, MOVIL;
	}



public final StringProperty numeroProperty() {
	return this.numero;
}


public final String getNumero() {
	return this.numeroProperty().get();
}


public final void setNumero(final String numero) {
	this.numeroProperty().set(numero);
}
	

public static ObservableList<TipoTelefono> getTiposTelefono() {
	
	ObservableList<TipoTelefono> tiposTelefono = FXCollections.observableArrayList(new ArrayList<>());
	tiposTelefono.add(TipoTelefono.DOMICILIO);
	tiposTelefono.add(TipoTelefono.MOVIL);
	
	return tiposTelefono;
}

public final ObjectProperty<TipoTelefono> tipoProperty() {
	return this.tipo;
}


public final TipoTelefono getTipo() {
	return this.tipoProperty().get();
}


public final void setTipo(final TipoTelefono tipo) {
	this.tipoProperty().set(tipo);
}



}
