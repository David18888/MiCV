package dad.javafx.micv.model;

import java.util.ArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Conocimiento {
	
private StringProperty denominacion= new SimpleStringProperty();
private ObjectProperty<Nivel> nivel= new SimpleObjectProperty<Nivel>();

public enum Nivel{
	BASICO,
	MEDIO,
	AVANZADO
}

public Conocimiento() {}

public Conocimiento(String denominacion, Nivel nivel) {
	this.denominacion.set(denominacion);
	this.nivel.set(nivel);
}


public final StringProperty denominacionProperty() {
	return this.denominacion;
}


public final String getDenominacion() {
	return this.denominacionProperty().get();
}


public final void setDenominacion(final String denominacion) {
	this.denominacionProperty().set(denominacion);
}


public final ObjectProperty<Nivel> nivelProperty() {
	return this.nivel;
}





public final Nivel getNivel() {
	return this.nivelProperty().get();
}



public final void setNivel(final Nivel nivel) {
	this.nivelProperty().set(nivel);
}

public static ObservableList<Nivel> getNiveles() {
	
	ObservableList<Nivel> niveles = FXCollections.observableArrayList(new ArrayList<>());
	niveles.addAll(Nivel.BASICO, Nivel.MEDIO, Nivel.AVANZADO);
	
	return niveles;
}

}