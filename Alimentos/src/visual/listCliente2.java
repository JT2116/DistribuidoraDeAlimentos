package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class listCliente2 extends JFrame {
	//private DefaultTableModel tableCliente;
	public static DefaultTableModel tableCliente;
	private JPanel contentPane;
	//private JTable table;
	public static JTable table;
	private static String selectIDcliente;
	private JTextField txtBuscarC;
	
	private JButton btnModificarC;
	private JButton btnPagarDeuda;
	private JButton btnAcredito;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					listCliente frame = new listCliente();
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
	public listCliente2(Connection con) {
		
		setBounds(100, 100, 987, 605);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Lista de clientes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
				selectIDcliente = (String) table.getValueAt(index, 0);
				
				btnModificarC.setEnabled(true);
				btnPagarDeuda.setEnabled(true);
				btnAcredito.setEnabled(true);
							
			}
		});
		scrollPane.setColumnHeaderView(table);
		
		tableCliente = new DefaultTableModel();
		mostrar(con);
		
		scrollPane.setViewportView(table);
		
		txtBuscarC = new JTextField();
		txtBuscarC.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String cadena = (txtBuscarC.getText());
				
				txtBuscarC.setText(cadena);
				
				repaint();
				
				filtrarCliente();
				
			}
		});
		txtBuscarC.setColumns(10);
		txtBuscarC.setBounds(10, 74, 183, 20);
		panel.add(txtBuscarC);
		
		JLabel lblBuscarCliente = new JLabel("Buscar Cliente:");
		lblBuscarCliente.setBounds(10, 49, 162, 14);
		panel.add(lblBuscarCliente);
		
		btnModificarC = new JButton("Modificar datos");
		btnModificarC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				int idC = Integer.valueOf(selectIDcliente);
				
				if(index != -1) {
					
					String[] options = {"Si", "No"};
					int seleccion = JOptionPane.showOptionDialog(null, "¿Seguro que desea modificar los datos de este cliente?", "Modificacion producto", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

					if(seleccion == 0) {
						
						//System.out.println(idC);							
						
						ModificarCliente mCliente = new ModificarCliente(idC,con);
						mCliente.setVisible(true);					
						
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Selecione un cliente", "Validación", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnModificarC.setEnabled(false);
		btnModificarC.setBounds(203, 73, 162, 23);
		panel.add(btnModificarC);
		
		btnPagarDeuda = new JButton("Pagar deuda");
		btnPagarDeuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = table.getSelectedRow();
				int idC = Integer.valueOf(selectIDcliente);
				String respuesta = JOptionPane.showInputDialog("¿Cuanto desea abonar a la deuda?");
				
				if(index != -1) {
					if(!(respuesta == null) &&  Float.parseFloat(respuesta)<= Float.parseFloat((String) table.getValueAt(index, 5))) {
						
						float pagoDeuda = Float.parseFloat(respuesta);
						
						float Deuda = Float.parseFloat((String) table.getValueAt(index, 5));
						
						//System.out.println("owo");
						pagarDeuda(idC,pagoDeuda,Deuda, con);
						
						tableCliente.setRowCount(0);
						mostrar(con);
					}
														
				}else {
					JOptionPane.showMessageDialog(null, "Selecione un cliente", "Validación", JOptionPane.WARNING_MESSAGE);
				}
				
				
				
 
				
			}
		});
		btnPagarDeuda.setEnabled(false);
		btnPagarDeuda.setBounds(375, 73, 162, 23);
		panel.add(btnPagarDeuda);
		
		btnAcredito = new JButton("Aumentar credicto");
		btnAcredito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				int idC = Integer.valueOf(selectIDcliente);
				String respuesta = JOptionPane.showInputDialog("¿Cuanto desea abonar al credito?");
				
				if(index != -1) {
					if(!(respuesta == null) && Float.parseFloat(respuesta) > 0) {
						
						
						float creditoActual = Float.parseFloat((String) table.getValueAt(index, 4));
						
						float aumentoCredito = Float.parseFloat(respuesta);
						
						aumentarCredito(idC,creditoActual,aumentoCredito, con);
						tableCliente.setRowCount(0);
						mostrar(con);
					}															
					
				}else {
					JOptionPane.showMessageDialog(null, "Selecione un cliente", "Validación", JOptionPane.WARNING_MESSAGE);
				}
				

				
			}
		});
		btnAcredito.setEnabled(false);
		btnAcredito.setBounds(547, 73, 166, 23);
		panel.add(btnAcredito);				
		
		TableRowSorter sorter = new TableRowSorter<>(table.getModel());
		
		table.setRowSorter(sorter);
		
		
		
	}
	
	
	public static void mostrar(Connection con){
		//Principal auxp = new Principal();
		//Connection cn = auxp.getConnection();
		tableCliente.setColumnIdentifiers(new Object[] {"ID","Nombre","Email","Telefono","Credito","Deuda"});
		Statement st;
		ResultSet datos = null;
		try {
			st = con.createStatement();
			datos = st.executeQuery("select ID_Cliente,Nombres,Apellidos,Email,Telefono,Credito,Deuda from Cliente where Enable = 1");
			while(datos.next()) {
				tableCliente.addRow(new Object[]{datos.getString("ID_Cliente"),datos.getString("Nombres")+" "+datos.getString("Apellidos"),datos.getString("Email"),datos.getString("Telefono"),datos.getString("Credito"),datos.getString("Deuda")});
			}
			table.setModel(tableCliente);
		}catch(Exception e) {
			System.out.println(e.toString());
		}
	}
	
	private void aumentarCredito(int idC,float creditoActual,float aumentoCredito, Connection con) {
		String insertTableSQLMarcaModelo = "update Cliente set Credito = ? where ID_Cliente = ?";
		
		float resulCredito = creditoActual + aumentoCredito;
		
		try {
			PreparedStatement preparedStmtV = con.prepareStatement(insertTableSQLMarcaModelo);
			
			preparedStmtV.setFloat(1,resulCredito);
			preparedStmtV.setInt(2,idC);
			preparedStmtV.execute();
									
			JOptionPane.showMessageDialog(null, "Aumento Exitoso", "Información", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	private void pagarDeuda(int idC,float pagoDeuda,float Deuda, Connection con) {
		
		String insertTableSQLMarcaModelo = "update Cliente set Deuda = ? where ID_Cliente = ?";
		//PreparedStatement st = null;
		//ResultSet datos = null;
		
		float resulDeuda = Deuda - pagoDeuda;
		
		try {
			PreparedStatement preparedStmtV = con.prepareStatement(insertTableSQLMarcaModelo);

			
			preparedStmtV.setFloat(1,resulDeuda);
			preparedStmtV.setInt(2,idC);
			preparedStmtV.execute();
			
			JOptionPane.showMessageDialog(null, "Pago Exitoso", "Información", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			 System.out.println(e.getMessage());
		}
		
		
	}
			
	private void filtrarCliente() {
		String filtro = txtBuscarC.getText();
		
		TableRowSorter trsfiltro = new TableRowSorter(table.getModel());
		
		table.setRowSorter(trsfiltro);
		
		trsfiltro.setRowFilter(RowFilter.regexFilter(txtBuscarC.getText(), 1));
				
	}
}
