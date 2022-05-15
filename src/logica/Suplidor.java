package logica;

public class Suplidor {
	private int ID;
	private String nombreComapnia;
	private String nombreRepresentante;
	private String apellidoRepresentante;
	private String email;
	private String telefono;
	
	public Suplidor(int iD, String nombreComapnia, String nombreRepresentante, String apellidoRepresentante,
			String email, String telefono) {
		super();
		this.ID = iD;
		this.nombreComapnia = nombreComapnia;
		this.nombreRepresentante = nombreRepresentante;
		this.apellidoRepresentante = apellidoRepresentante;
		this.email = email;
		this.telefono = telefono;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public String getNombreComapnia() {
		return nombreComapnia;
	}
	
	public void setNombreComapnia(String nombreComapnia) {
		this.nombreComapnia = nombreComapnia;
	}
	
	public String getNombreRepresentante() {
		return nombreRepresentante;
	}
	
	public void setNombreRepresentante(String nombreRepresentante) {
		this.nombreRepresentante = nombreRepresentante;
	}
	
	public String getApellidoRepresentante() {
		return apellidoRepresentante;
	}
	
	public void setApellidoRepresentante(String apellidoRepresentante) {
		this.apellidoRepresentante = apellidoRepresentante;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	

}
