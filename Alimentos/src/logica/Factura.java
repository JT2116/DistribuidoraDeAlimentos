package logica;

import java.sql.Date;
import java.util.ArrayList;

public class Factura {
	
	private int ID;
	private Cliente cliente;
	private Date fecha_Creacion;
	private String tipoPago;
	private ArrayList<Producto> productos;
	private float precioTotal;
	private int cantidadComprada;
	
	public Factura(int iD, Cliente cliente, Date fecha_Creacion, String tipoPago, ArrayList<Producto> productos,
			float precioTotal, int cantidadComprada) {
		super();
		this.ID = iD;
		this.cliente = cliente;
		this.fecha_Creacion = fecha_Creacion;
		this.tipoPago = tipoPago;
		this.productos = productos;
		this.precioTotal = precioTotal;
		this.cantidadComprada = cantidadComprada;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getFecha_Creacion() {
		return fecha_Creacion;
	}

	public void setFecha_Creacion(Date fecha_Creacion) {
		this.fecha_Creacion = fecha_Creacion;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	public float getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(float precioTotal) {
		this.precioTotal = precioTotal;
	}

	public int getCantidadComprada() {
		return cantidadComprada;
	}

	public void setCantidadComprada(int cantidadComprada) {
		this.cantidadComprada = cantidadComprada;
	}
	
	
	
	

}
