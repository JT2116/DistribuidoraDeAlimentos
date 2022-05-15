package logica;

public class Cliente {
	private int ID;
	private String nombres;
	private String apellidos;
	private String email;
	private String telefono;
	private float credito;
	private float deuda;
	
	
	public Cliente(int iD, String nombres, String apellidos, String email, String telefono, float credito,
			float deuda) {
		super();
		ID = iD;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.email = email;
		this.telefono = telefono;
		this.credito = credito;
		this.deuda = deuda;
	}


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public String getNombres() {
		return nombres;
	}


	public void setNombres(String nombres) {
		this.nombres = nombres;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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


	public float getCredito() {
		return credito;
	}


	public void setCredito(float credito) {
		this.credito = credito;
	}


	public float getDeuda() {
		return deuda;
	}


	public void setDeuda(float deuda) {
		this.deuda = deuda;
	}
	
	
	
	
}
