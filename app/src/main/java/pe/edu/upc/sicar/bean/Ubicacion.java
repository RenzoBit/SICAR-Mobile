package pe.edu.upc.sicar.bean;

public class Ubicacion {

	private int idubicacion;
	private int idorden;
	private String latitud;
	private String longitud;
	private String hora;
	private String direccion;
	public static final String UBICACION_ID = "idubicacion";
	public static final String UBICACION_DI = "hora";

	public int getIdubicacion() {
		return idubicacion;
	}

	public void setIdubicacion(int idubicacion) {
		this.idubicacion = idubicacion;
	}

	public int getIdorden() {
		return idorden;
	}

	public void setIdorden(int idorden) {
		this.idorden = idorden;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

}
