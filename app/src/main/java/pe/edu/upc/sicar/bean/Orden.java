package pe.edu.upc.sicar.bean;

public class Orden {

	private int idorden;
	private int idpersona;
	private String numero;
	private String fecha;
	public static final String ORDEN_ID = "idorden";
	public static final String ORDEN_DI = "numero";

	public int getIdorden() {
		return idorden;
	}

	public void setIdorden(int idorden) {
		this.idorden = idorden;
	}

	public int getIdpersona() {
		return idpersona;
	}

	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
