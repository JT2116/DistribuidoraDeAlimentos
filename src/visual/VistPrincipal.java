package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Almacen;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistPrincipal extends JFrame {

	private JPanel contentPane;
	private Connection con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Almacen al = new Almacen();
					VistPrincipal frame = new VistPrincipal(al);
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
	public VistPrincipal(Almacen al) {
		Conexion();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1116, 588);
		//ImageIcon imagen1=new ImageIcon("imagenes"+File.separator+"queso.png");
		
		ImageIcon imagen1=new ImageIcon("imagenes"+File.separator+"tienda.png");
		
		setIconImage(imagen1.getImage());
		contentPane = new JPanel();
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

		
		
		JLabel lblNewLabel = new JLabel(imagen1);
		lblNewLabel.setBounds(568, 58, 522, 482);
		contentPane.add(lblNewLabel);
		
		
		ImageIcon imagenFactura=new ImageIcon("imagenes"+File.separator+"caja-registradora.png");
		JButton btnCFactura = new JButton(imagenFactura);
		btnCFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegFactura rgF = new RegFactura(al,getConnection());
				rgF.setVisible(true);
			}
		});
		btnCFactura.setBackground(Color.WHITE);
		btnCFactura.setForeground(Color.BLACK);
		btnCFactura.setBounds(10, 11, 514, 96);
		btnCFactura.setText("Crear Factura");
		contentPane.add(btnCFactura);
		
		
		ImageIcon imagenListFactura=new ImageIcon("imagenes"+File.separator+"factura.png");
		JButton btnListaDeFacturas = new JButton(imagenListFactura);
		btnListaDeFacturas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listFactura listF = new listFactura(con);
				listF.setVisible(true);				
			}
		});
		btnListaDeFacturas.setForeground(Color.BLACK);
		btnListaDeFacturas.setBackground(Color.WHITE);
		btnListaDeFacturas.setBounds(10, 123, 514, 96);
		btnListaDeFacturas.setText("Lista de Facturas"); 
		contentPane.add(btnListaDeFacturas);
		
		
		ImageIcon imagenProducto=new ImageIcon("imagenes"+File.separator+"inventario.png");
		JButton btnProductos = new JButton(imagenProducto);
		btnProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegProducto rgP = new RegProducto(al,getConnection()); 
				rgP.setVisible(true);
			}
		});
		btnProductos.setForeground(Color.BLACK);
		btnProductos.setBackground(Color.WHITE);
		btnProductos.setBounds(10, 230, 514, 96);
		btnProductos.setText("Productos");
		contentPane.add(btnProductos);
		
		
		ImageIcon imagenSuplidor=new ImageIcon("imagenes"+File.separator+"repartidor.png");
		JButton btnRegistrarSuplidor = new JButton(imagenSuplidor);
		btnRegistrarSuplidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listSuplidor2 listS = new listSuplidor2(getConnection());
				listS.setVisible(true);				
			}
		});
		btnRegistrarSuplidor.setForeground(Color.BLACK);
		btnRegistrarSuplidor.setBackground(Color.WHITE);
		btnRegistrarSuplidor.setBounds(10, 337, 514, 96);
		btnRegistrarSuplidor.setText("Suplidores");
		contentPane.add(btnRegistrarSuplidor);
		
		
		ImageIcon imagenCliente=new ImageIcon("imagenes"+File.separator+"consumidor.png");
		JButton btnListaDeClientes = new JButton(imagenCliente);
		btnListaDeClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listCliente2 listC = new listCliente2(getConnection());
				listC.setVisible(true);
			}
		});
		btnListaDeClientes.setForeground(Color.BLACK);
		btnListaDeClientes.setBackground(Color.WHITE);
		btnListaDeClientes.setBounds(10, 444, 514, 96);
		btnListaDeClientes.setText("Lista de Clientes");
		contentPane.add(btnListaDeClientes);
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
