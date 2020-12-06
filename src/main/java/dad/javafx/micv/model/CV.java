package dad.javafx.micv.model;



import java.util.ArrayList;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class CV {

	private ObjectProperty<Personal> personal = new SimpleObjectProperty<Personal>(new Personal());
	private ObjectProperty <Contacto> contacto= new SimpleObjectProperty<Contacto>(new Contacto());
	private ListProperty <Titulo> formacion= new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
	private ListProperty<Experiencia>experiencia= new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
	private ListProperty<Conocimiento>habilidades= new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
	
	
	public CV(){}
	
	
	public CV(Personal personal,Contacto contacto, ArrayList<Titulo> titulos, ArrayList<Experiencia>experiencia,ArrayList<Conocimiento> habilidades ) {
		
		this.personal.set(personal);
		this.contacto.set(contacto);
		this.formacion.addAll(titulos);
		this.experiencia.addAll(experiencia);
		this.habilidades.addAll(habilidades);
		
		
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


	public ObjectProperty<Contacto> getContacto() {
		return contacto;
	}


	public void setContacto(ObjectProperty<Contacto> contacto) {
		this.contacto = contacto;
	}


	public ListProperty<Titulo> getFormacion() {
		return formacion;
	}


	public void setFormacion(ListProperty<Titulo> formacion) {
		this.formacion = formacion;
	}


	public ListProperty<Experiencia> getExperiencia() {
		return experiencia;
	}


	public void setExperiencia(ListProperty<Experiencia> experiencia) {
		this.experiencia = experiencia;
	}


	public ListProperty<Conocimiento> getHabilidades() {
		return habilidades;
	}


	public void setHabilidades(ListProperty<Conocimiento> habilidades) {
		this.habilidades = habilidades;
	}


	public void setPersonal(ObjectProperty<Personal> personal) {
		this.personal = personal;
	}

	
	
	
}
