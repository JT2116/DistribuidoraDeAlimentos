package logica;

public class Producto {
	private int ID;
	//private Suplidor suplidor;
	private String suplidor;
	private String nombre;
	private int cantidad;
	private float precioCompra;
	private float precioVenta;
	private String tipo;
	private String descripcion;
	
	public Producto(int iD, String suplidor, String nombre, int cantidad, float precioCompra, float precioVenta,
			String tipo, String descripcion) {
		super();
		ID = iD;
		this.suplidor = suplidor;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.tipo = tipo;
		this.descripcion = descripcion;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getSuplidor() {
		return suplidor;
	}

	public void setSuplidor(String suplidor) {
		this.suplidor = suplidor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(float precioCompra) {
		this.precioCompra = precioCompra;
	}

	public float getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(float precioVenta) {
		this.precioVenta = precioVenta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
