package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Almacen;
import logica.Producto;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {
	
	private Connection con;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Almacen al = new Almacen();
					ArrayList<Producto> productosV = new ArrayList<Producto>();
					Principal frame = new Principal(al);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal(Almacen al) {
		Conexion();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 733, 517);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 717, 22);
		contentPane.add(menuBar);
		
		JMenu facturaMenu = new JMenu("Facturas");
		menuBar.add(facturaMenu);
		
		JMenuItem crearFactura = new JMenuItem("Crear facturas");
		crearFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				RegFactura rgF = new RegFactura(al,getConnection());
				rgF.setVisible(true);
			}
		});
		facturaMenu.add(crearFactura);
		
		JMenuItem listaFactura = new JMenuItem("Lista de facturas");
		facturaMenu.add(listaFactura);
		
		JMenu productoMenu_1 = new JMenu("Productos");
		menuBar.add(productoMenu_1);
		
		JMenuItem regisProducto = new JMenuItem("Registrar producto");
		regisProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegProducto rgP = new RegProducto(al,getConnection()); 
				rgP.setVisible(true);
			}
		});
		productoMenu_1.add(regisProducto);
		
		JMenu suplidorMenu_2 = new JMenu("Suplidores");
		menuBar.add(suplidorMenu_2);
		
		JMenuItem regisSuplidor = new JMenuItem("Registrar suplidor");
		regisSuplidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegSuplidor rgS = new RegSuplidor(al,getConnection());
				rgS.setVisible(true);
			}
		});
		suplidorMenu_2.add(regisSuplidor);
		
		JMenuItem listaSuplidor = new JMenuItem("Lista de Suplidores");
		listaSuplidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listSuplidor listS = new listSuplidor(getConnection());
				listS.setVisible(true);
			}
		});
		suplidorMenu_2.add(listaSuplidor);
		
		JMenu clienteMenu_3 = new JMenu("Clientes");
		menuBar.add(clienteMenu_3);
		
		JMenuItem listaCliente = new JMenuItem("Lista de clientes");
		clienteMenu_3.add(listaCliente);
	}
	
	
	public void Conexion() {
				
		String url = "jdbc:mariadb://localhost:3306/DAlimentos";
		//String url = "jdbc:mysql//localhost:3306/DAlimentos";
		String user = "root";
		String pass = "1011";
		
		try {
			con = DriverManager.getConnection(url, user, pass);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public Connection getConnection(){
		return con;
	}
	
	
	
	
	
	
}
