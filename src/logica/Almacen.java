package logica;

import java.util.ArrayList;

public class Almacen {
	private ArrayList<Factura> facturas;
	private ArrayList<Cliente> clientes;
	private ArrayList<Producto> productos;
	private ArrayList<Suplidor> suplidores;
	
	public Almacen() {
		super();
		this.facturas = new ArrayList<Factura>();
		this.clientes = new ArrayList<Cliente>();
		this.productos = new ArrayList<Producto>();
		this.suplidores = new ArrayList<Suplidor>();
	}

	public ArrayList<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(ArrayList<Factura> facturas) {
		this.facturas = facturas;
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	public ArrayList<Suplidor> getSuplidores() {
		return suplidores;
	}

	public void setSuplidores(ArrayList<Suplidor> suplidores) {
		this.suplidores = suplidores;
	}
	
	
	public void insertarFactura(Factura factura) {
		facturas.add(factura);
	}
	
	public void insertarCliente(Cliente cliente) {
		clientes.add(cliente);
	}
	
	public void insertarProducto(Producto producto) {
		productos.add(producto);
	}
	
	public void insertarSuplidor(Suplidor suplidor) {
		suplidores.add(suplidor);
	}
	
	public Producto buscarProducto(int id) {
		Producto aux = null;
		
		for(Producto pro : productos) {
			if(pro.getID() == id) {
				aux = pro;
			}
		}
		
		return aux;
	}
	
	

}
