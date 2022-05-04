package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logica.Almacen;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class RegProducto extends JFrame {

	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtNombreP;
	private JTextField txtNombreS;
	private JTextField txtPrecioC;
	private JTextField txtPrecioV;
	private JTextPane txtDescripcion;
	private JSpinner spiExistencia;
	private JTable table;
	
	private DefaultTableModel tableProductos;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegProducto frame = new RegProducto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public RegProducto(Almacen al,Connection con) {
		
		setBounds(100, 100, 1132, 616);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Informacion del producto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 289, 555);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("ID:");
		lblNewLabel_1.setBounds(10, 26, 24, 14);
		panel.add(lblNewLabel_1);
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(44, 23, 86, 20);
		panel.add(txtID);
		txtID.setText(String.valueOf(getIdProducto(con)));
		
		
		JLabel lblNewLabel_3 = new JLabel("Nombres del Producto:");
		lblNewLabel_3.setBounds(10, 51, 168, 14);
		panel.add(lblNewLabel_3);
		
		txtNombreP = new JTextField();
		txtNombreP.setColumns(10);
		txtNombreP.setBounds(20, 79, 242, 20);
		panel.add(txtNombreP);
		
		txtNombreS = new JTextField();
		txtNombreS.setColumns(10);
		txtNombreS.setBounds(20, 135, 242, 20);
		panel.add(txtNombreS);
		
		JLabel lblNewLabel_3_1 = new JLabel("Nombres del suplidor:");
		lblNewLabel_3_1.setBounds(10, 110, 168, 14);
		panel.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Existencia:");
		lblNewLabel_3_1_1.setBounds(10, 166, 168, 14);
		panel.add(lblNewLabel_3_1_1);
		
		spiExistencia = new JSpinner();
		spiExistencia.setBounds(20, 189, 73, 20);
		panel.add(spiExistencia);
		
		JLabel lblNewLabel_3_1_1_1 = new JLabel("Precio Compra:");
		lblNewLabel_3_1_1_1.setBounds(10, 220, 168, 14);
		panel.add(lblNewLabel_3_1_1_1);
		
		txtPrecioC = new JTextField();
		txtPrecioC.setColumns(10);
		txtPrecioC.setBounds(20, 245, 86, 20);
		panel.add(txtPrecioC);
		
		txtPrecioV = new JTextField();
		txtPrecioV.setColumns(10);
		txtPrecioV.setBounds(137, 245, 86, 20);
		panel.add(txtPrecioV);
		
		JLabel lblNewLabel_3_1_1_1_1 = new JLabel("Precio de Venta:");
		lblNewLabel_3_1_1_1_1.setBounds(127, 220, 135, 14);
		panel.add(lblNewLabel_3_1_1_1_1);
		
		JLabel lblNewLabel_3_1_1_1_2 = new JLabel("Tipo de producto:");
		lblNewLabel_3_1_1_1_2.setBounds(10, 276, 168, 14);
		panel.add(lblNewLabel_3_1_1_1_2);
		
		JComboBox BoxTipos = new JComboBox();
		BoxTipos.setModel(new DefaultComboBoxModel(new String[] {"Tipo", "Grano ", "Carne ", "Vegentales ", "Especias y hierbas ", "Refigerios", "Otros"}));
		BoxTipos.setBounds(20, 301, 158, 22);
		panel.add(BoxTipos);
		
		JLabel lblNewLabel_3_1_1_1_2_1 = new JLabel("Descripci\u00F3n :");
		lblNewLabel_3_1_1_1_2_1.setBounds(10, 334, 168, 14);
		panel.add(lblNewLabel_3_1_1_1_2_1);
		
		txtDescripcion = new JTextPane();
		txtDescripcion.setBounds(10, 359, 252, 150);
		panel.add(txtDescripcion);
		
		JButton btnCancalar = new JButton("Cancelar");
		btnCancalar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancalar.setBounds(167, 520, 95, 23);
		panel.add(btnCancalar);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtNombreS.getText().equalsIgnoreCase("") && !txtNombreP.getText().equalsIgnoreCase("") && BoxTipos.getSelectedItem().toString() != "Tipos" && Integer.parseInt(spiExistencia.getValue().toString()) != 0 && !txtPrecioC.getText().equalsIgnoreCase("") && !txtPrecioV.getText().equalsIgnoreCase("") && !txtDescripcion.getText().equalsIgnoreCase("")) {
					
					String nombreS = txtNombreS.getText();
					String nombreP = txtNombreP.getText();
					int Cantidad = Integer.parseInt(spiExistencia.getValue().toString());
					float PrecioC = Float.parseFloat(txtPrecioC.getText());
					float PrecioV = Float.parseFloat(txtPrecioV.getText());
					String tipo = BoxTipos.getSelectedItem().toString();
					String Descripcion = txtDescripcion.getText();
					
					
					
					int idS =buscarSuplidor(nombreS,con);
					
					if(idS != 0 && BoxTipos.getSelectedIndex() != 0 ) {
						insertarBDP(getIdProducto(con),idS , nombreP, Cantidad, PrecioC, PrecioV, tipo, Descripcion,con);
						clean();
						tableProductos.setRowCount(0);
						mostrar(con);
						txtID.setText(String.valueOf(getIdProducto(con)));
					}else {
						JOptionPane.showMessageDialog(null, "Revisar los datos", "Validación", JOptionPane.WARNING_MESSAGE);
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Revise los datos", "Validación", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		btnRegistrar.setBounds(61, 520, 96, 23);
		panel.add(btnRegistrar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Lista de productos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(309, 11, 797, 555);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBounds(10, 116, 777, 428);
		panel_1.add(panel_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 777, 428);
		panel_1_1.add(scrollPane);
		
		table = new JTable();
		tableProductos= new DefaultTableModel();
		scrollPane.setColumnHeaderView(table);
		mostrar(con);
		scrollPane.setViewportView(table);
	}
	
	public void mostrar(Connection con){
		//Principal auxp = new Principal();
		//Connection cn = auxp.getConnection();
		tableProductos.setColumnIdentifiers(new Object[] {"ID","Producto","Suplidor","Existencia","Precio de compra","Precio de Venta","Tipo","Descripcion"});
		Statement st;
		ResultSet datos = null;
		try {
			st = con.createStatement();
			datos = st.executeQuery("select ID_Producto,Nombre,Nombre_Compania,Cantidad,PrecioCompra,PrecioVenta,Tipo,Descripcion from Producto join Suplidor on Producto.Suplidor = Suplidor.ID_Suplidor");
			while(datos.next()) {
				tableProductos.addRow(new Object[]{datos.getString("ID_Producto"),datos.getString("Nombre"),datos.getString("Nombre_Compania"),datos.getInt("Cantidad"),datos.getFloat("PrecioCompra"),datos.getFloat("PrecioVenta"),datos.getString("Tipo"),datos.getString("Descripcion")});
			}
			table.setModel(tableProductos);
		}catch(Exception e) {
			System.out.println(e.toString());
		}
	}
	
	
	
	public void insertarBDP(int ID, int suplidor, String nombreP, int cantidad, float precioC, float precioV, String tipo, String descripcion,Connection con) {
		String insertTableSQLMarcaModelo = "insert into Producto values (?,?,?,?,?,?,?,?)";
		PreparedStatement st3 = null;
		ResultSet datos3 = null;
		
		int idS = 0;
		String nP = null;
		String tP = null;
				
		int id = 0;
		int marcar = 0;
		
		try {
			PreparedStatement preparedStmtV = con.prepareStatement(insertTableSQLMarcaModelo);
			st3 = con.prepareStatement("select distinct * from Producto where Suplidor = ? and Nombre = ? and Tipo = ?");
			
			st3.setInt(1,suplidor);
			st3.setString(2,nombreP);
			st3.setString(3,tipo);
				
			datos3 = st3.executeQuery();
			
			while(datos3.next()) {
				
				idS = datos3.getInt("Suplidor");
				nP = datos3.getString("Nombre");
				tP = datos3.getString("Tipo");
				
								
				if(idS == suplidor && nP.equals(nombreP) && tP.equals(tipo)) {
					marcar = 1;
					//id = datos3.getString("ID_Suplidor");
					JOptionPane.showMessageDialog(null, "Este producto ya existe", "Validación", JOptionPane.WARNING_MESSAGE);
					clean();
				}else {
					marcar = 2;
				}
			}
			
			if(marcar == 0 || marcar == 2) {
				preparedStmtV.setInt(1,ID);
				preparedStmtV.setInt(2,suplidor);
				preparedStmtV.setString(3,nombreP);
				preparedStmtV.setInt(4,cantidad);
				preparedStmtV.setFloat(5,precioC);
				preparedStmtV.setFloat(6,precioV);
				preparedStmtV.setString(7,tipo);
				preparedStmtV.setString(8,descripcion);
				preparedStmtV.execute();
				JOptionPane.showMessageDialog(null, "Registro Exitoso", "Información", JOptionPane.INFORMATION_MESSAGE);
			}
			

			
		}catch (SQLException e) {

            System.out.println(e.getMessage());

        }
		
	}
	
	
	private int getIdProducto(Connection con) {
		
		int id = 0;
		Statement st2;
		ResultSet datos2 = null;
		
		try {
			st2 = con.createStatement();
			datos2 = st2.executeQuery("select count(ID_Producto) as Cantidad from Producto");
			
			while(datos2.next()) {
				if(datos2.getInt("Cantidad") == 0) {
					
					id = 1;
					
				}else{
					id = datos2.getInt("Cantidad") + 1;
				}
		
			}
			
			//System.out.print(id);
		}catch(Exception e) {
			System.out.println(e.toString());
		}
		return id;	
	}
	
	public int buscarSuplidor(String nombreS,Connection con) {
		int id = 0;
		int idS = 0;
		PreparedStatement st3 = null;
		ResultSet datos3 = null;
		try {
			st3 = con.prepareStatement("select ID_Suplidor from Suplidor where Nombre_Compania = ?");
			st3.setString(1,nombreS);
			
			datos3 = st3.executeQuery();
			
			while(datos3.next()) {
				//System.out.println(datos3.getString("ID"));
				id = datos3.getInt("ID_Suplidor"); 
				idS = id;
				
			}
		} catch (Exception e) {
			 System.out.println(e.getMessage());
		}
			
		return idS;
	}
	
	
	private void clean() {
		txtNombreS.setText("");
		txtNombreP.setText("");
		txtPrecioC.setText("");
		txtPrecioV.setText("");
		txtDescripcion.setText("");
		spiExistencia.setValue(Integer.parseInt("0"));
		
	}
	
	
	
	
	
}
