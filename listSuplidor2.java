package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class listSuplidor2 extends JFrame {
	//private DefaultTableModel tableSuplidor;
	
	public static DefaultTableModel tableSuplidor;
	private JPanel contentPane;
	//private JTable table;
	public static JTable table;
	private JTextField txtBuscarS;
	private JButton btnModificarS;
	private JButton btnProductoSuplidor;
	
	public static int selectSuplidor;
	private JButton btnEliminarS;
	static TableRowSorter<DefaultTableModel> tr;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					listSuplidor frame = new listSuplidor();
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
	public listSuplidor2(Connection con) {
		
		setBounds(100, 100, 987, 605);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Lista de suplidores", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 951, 544);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(10, 105, 931, 428);
		panel.add(panel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 931, 428);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				selectSuplidor = (int) table.getValueAt(index, 0);
				
				btnModificarS.setEnabled(true);
				btnEliminarS.setEnabled(true);
				btnProductoSuplidor.setEnabled(true);
			
			}
		});
	
		
		txtBuscarS = new JTextField();
		txtBuscarS.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String cadena = (txtBuscarS.getText());
				
				txtBuscarS.setText(cadena);
				
				repaint();
				
				filtrarSuplidor();
				
			}
		});
		txtBuscarS.setColumns(10);
		txtBuscarS.setBounds(10, 74, 183, 20);
		panel.add(txtBuscarS);
		
		JLabel lblBuscarSuplidor = new JLabel("Buscar suplidor:");
		lblBuscarSuplidor.setBounds(10, 49, 162, 14);
		panel.add(lblBuscarSuplidor);
		
		btnModificarS = new JButton("Modificar datos");
		btnModificarS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				int index = table.getSelectedRow();
				int idS = selectSuplidor;
				
				if(index != -1) {
					String[] options = {"Si", "No"};
					int seleccion = JOptionPane.showOptionDialog(null, "¿Seguro que desea modificar los datos de este suplidor?", "Modificacion producto", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

					if (seleccion == 0) {
						ModificarSuplidor mSuplidor = new ModificarSuplidor(idS,con);
						mSuplidor.setVisible(true);
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Selecione un suplidor", "Validación", JOptionPane.WARNING_MESSAGE);
				}
				
				
			}
		});
		btnModificarS.setEnabled(false);
		btnModificarS.setBounds(203, 73, 162, 23);
		panel.add(btnModificarS);
		
		
		btnEliminarS = new JButton("Eliminar suplidor");
		btnEliminarS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = table.getSelectedRow();
				int idS = selectSuplidor;
				
				if(index != -1) {
					String[] options = {"Si", "No"};
					int seleccion = JOptionPane.showOptionDialog(null, "¿Seguro que desea eliminar los datos de este suplidor?", "Modificacion producto", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

					if (seleccion == 0) {
						eliminarSuplidor(idS, con);
						tableSuplidor.setRowCount(0);
						mostrar(con);
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Selecione un suplidor", "Validación", JOptionPane.WARNING_MESSAGE);
				}
				
				
			}
		});
		btnEliminarS.setEnabled(false);
		btnEliminarS.setBounds(375, 73, 162, 23);
		panel.add(btnEliminarS);
		
		btnProductoSuplidor = new JButton("Productos del suplidor");
		btnProductoSuplidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = table.getSelectedRow();
				int idS = selectSuplidor;
				if(index != -1) {
					
					listProductoS listPS = new listProductoS(idS,con);
					listPS.setVisible(true);
					
					
				}
				
				
			}
		});
		btnProductoSuplidor.setEnabled(false);
		btnProductoSuplidor.setBounds(547, 73, 175, 23);
		panel.add(btnProductoSuplidor);
		
		tableSuplidor= new DefaultTableModel() {
			
			Class[] types = { Integer.class, String.class, String.class,String.class,String.class, String.class};
			boolean[] canEdit = new boolean [] {
			    false, false, false,false,false, false
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
				
		mostrar(con);
		scrollPane.setViewportView(table);
		
		
	}
	
	
	public static void mostrar(Connection con){
		//Principal auxp = new Principal();
		//Connection cn = auxp.getConnection();
		tableSuplidor.setColumnIdentifiers(new Object[] {"ID","Nombre Compania","Nombre Representante","Apellido Representante","Email","Telefono"});
		Statement st;
		ResultSet datos = null;
		try {
			st = con.createStatement();
			datos = st.executeQuery("select ID_Suplidor,Nombre_Compania,Nombre_Representante,Apellido_Representante,Email,Telefono from Suplidor where Enable = 1");
			while(datos.next()) {
				tableSuplidor.addRow(new Object[]{datos.getInt("ID_Suplidor"),datos.getString("Nombre_Compania"),datos.getString("Nombre_Representante"),datos.getString("Apellido_Representante"),datos.getString("Email"),datos.getString("Telefono")});
			}
			table.setModel(tableSuplidor);
		}catch(Exception e) {
			System.out.println(e.toString());
		}
		
		tr = new TableRowSorter<>(tableSuplidor);
		
		
		table.setRowSorter(tr);
		
		
	}
	
	private void eliminarSuplidor(int idS, Connection con) {
		String insertTableSQLMarcaModelo = "update Suplidor set Enable = 0 where ID_Suplidor = ?";
		
		try {
			PreparedStatement preparedStmtV = con.prepareStatement(insertTableSQLMarcaModelo);
			preparedStmtV.setInt(1,idS);	
			preparedStmtV.execute();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	
	
			
	private void filtrarSuplidor() {
		String filtro = txtBuscarS.getText();
		
		TableRowSorter trsfiltro = new TableRowSorter(table.getModel());
		
		table.setRowSorter(trsfiltro);
		
		trsfiltro.setRowFilter(RowFilter.regexFilter(txtBuscarS.getText(), 1));
				
	}
}
