package pe.edu.upc.sicar.bean;

public class Persona {

	private int idpersona;
	private String nombre;
	private int tiempo;
	public static final String PERSONA_ID = "idpersona";
	public static final String PERSONA_DI = "nombre";
	public static final String PERSONA_EX = "tiempo";

	public int getIdpersona() {
		return idpersona;
	}

	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

}
