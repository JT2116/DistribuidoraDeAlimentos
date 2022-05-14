package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class listProductoS extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JButton btnEliminarP;
	private JButton btnAumentoExistencia;
	private JTable table;
	private DefaultTableModel tableProductos;
	TableRowSorter<DefaultTableModel> tr;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					listProductoS frame = new listProductoS();
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
	public listProductoS(int idS, Connection con) {
		
		setBounds(100, 100, 834, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "Lista de productos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 11, 797, 555);
		contentPane.add(panel_1);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBounds(10, 83, 777, 461);
		panel_1.add(panel_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 777, 461);
		panel_1_1.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnEliminarP.setEnabled(true);
				btnAumentoExistencia.setEnabled(true);
			}
		});
		scrollPane.setColumnHeaderView(table);
		
		JLabel lblElegirTipoDe = new JLabel("Buscar productos:");
		lblElegirTipoDe.setBounds(10, 33, 162, 14);
		panel_1.add(lblElegirTipoDe);
		
		txtBuscar = new JTextField();
		txtBuscar.setColumns(10);
		txtBuscar.setBounds(10, 52, 183, 20);
		panel_1.add(txtBuscar);
		
		btnEliminarP = new JButton("Eliminar producto");
		btnEliminarP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = table.getSelectedRow();
				
				if(index != -1) {
					
					int idP =  (int) table.getValueAt(index, 0);
					String[] options = {"Si", "No"};
					int seleccion = JOptionPane.showOptionDialog(null, "¿Seguro que desea Eliminar este producto?", "Modificacion producto", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

					if(seleccion == 0) {
						eliminarProducto(Integer.valueOf(idP),con);
						tableProductos.setRowCount(0);
						mostrar(idS,con);
					}
				
				}else {
					JOptionPane.showMessageDialog(null, "Selecione una producto", "Validación", JOptionPane.WARNING_MESSAGE);
				}	
				
			}
		});
		btnEliminarP.setEnabled(false);
		btnEliminarP.setBounds(203, 51, 162, 23);
		panel_1.add(btnEliminarP);
		
		btnAumentoExistencia = new JButton("Aumento existencia");
		btnAumentoExistencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = table.getSelectedRow();
				
				if(index != -1) {
					
					int idP =  (int) table.getValueAt(index, 0);
					//System.out.println(table.getValueAt(index, 0));
					
					int extActual = (int) table.getValueAt(index, 2);
				
					String respuesta = JOptionPane.showInputDialog("¿Cuanto desea aumentar?");
					
					if(respuesta != null) {						
						
						//aumentarExistenciaP(Integer.valueOf(idP),Integer.valueOf(respuesta),Integer.valueOf(extActual),con);
						aumentarExistenciaP(idP,Integer.valueOf(respuesta),extActual,con);
						tableProductos.setRowCount(0);
						mostrar(idS,con);
						
					}
									
				}else {
					JOptionPane.showMessageDialog(null, "Selecione una producto", "Validación", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		btnAumentoExistencia.setEnabled(false);
		btnAumentoExistencia.setBounds(375, 51, 162, 23);
		panel_1.add(btnAumentoExistencia);
		
		tableProductos= new DefaultTableModel() {
			
			Class[] types = { Integer.class, String.class,Integer.class ,Float.class, Float.class, String.class,String.class };
			boolean[] canEdit = new boolean [] {
			    false, false,false,false, false, false,false
			};
			
			@Override
			public Class getColumnClass(int columnIndex) {
			        return this.types[columnIndex];			        
			}       

			     // This override is just for avoid editing the content of my JTable. 
			@Override
			public boolean isCellEditable(int row, int column) {
			        return false;        
			}
			
		};
		scrollPane.setColumnHeaderView(table);
		mostrar(idS,con);
		scrollPane.setViewportView(table);
		
		
	}
	
	
	public void mostrar(int idS,Connection con){
		tableProductos.setColumnIdentifiers(new Object[] {"ID","Producto","Existencia","Precio de compra","Precio de Venta","Tipo","Descripcion"});		
		PreparedStatement st = null;
		ResultSet datos = null;

		try {
			
			st = con.prepareStatement("select ID_Producto,Nombre,Cantidad,PrecioCompra,PrecioVenta,Tipo,Descripcion from Producto join Suplidor on Producto.Suplidor = Suplidor.ID_Suplidor where Producto.Suplidor = ? and Producto.Enable = 1");
			
			st.setInt(1,idS);
			
			datos = st.executeQuery();
			
			while(datos.next()) {
				tableProductos.addRow(new Object[]{datos.getInt("ID_Producto"),datos.getString("Nombre"),datos.getInt("Cantidad"),datos.getFloat("PrecioCompra"),datos.getFloat("PrecioVenta"),datos.getString("Tipo"),datos.getString("Descripcion")});				
			}
			table.setModel(tableProductos);
		}catch(Exception e) {
			System.out.println(e.toString());
		}
												
		tr = new TableRowSorter<>(tableProductos);
		
		
		table.setRowSorter(tr);
						
		
	}
	
	
	private void aumentarExistenciaP(int idP,int cantidadAumentar,int cantidadActual, Connection con) {
		String insertTableSQLMarcaModelo = "update Producto set Cantidad = ? where ID_Producto = ?";
		
		try {
			PreparedStatement preparedStmtV = con.prepareStatement(insertTableSQLMarcaModelo);
			
			preparedStmtV.setInt(1,cantidadActual+cantidadAumentar);
			preparedStmtV.setInt(2,idP);
			preparedStmtV.execute();
			
			
		} catch (Exception e) {
		}
	}
	
	
	private void eliminarProducto(int idP, Connection con) {
		String insertTableSQLMarcaModelo = "update Producto set Enable = 0 where ID_Producto = ?";
		
		try {
			PreparedStatement preparedStmtV = con.prepareStatement(insertTableSQLMarcaModelo);
			preparedStmtV.setInt(1,idP);			
			preparedStmtV.execute();
			
		} catch (Exception e) {
		}
	}
	
	
	
	
	
}
