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
import java.io.File;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class listCliente2 extends JFrame {
	//private DefaultTableModel tableCliente;
	public static DefaultTableModel tableCliente;
	private JPanel contentPane;
	//private JTable table;
	public static JTable table;
	private static int selectIDcliente;
	private JTextField txtBuscarC;
	
	private JButton btnModificarC;
	private JButton btnPagarDeuda;
	private JButton btnAcredito;
	private JButton btnFacturasC;
	static TableRowSorter<DefaultTableModel> tr;
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
		setTitle("Lista de clientes");
		setBounds(100, 100, 987, 605);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setResizable(false);

		ImageIcon imagenIcon=new ImageIcon("imagenes"+File.separator+"consumidor.png");
		
		setIconImage(imagenIcon.getImage());
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Lista de clientes", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
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
				selectIDcliente = (int) table.getValueAt(index, 0);
				
				btnModificarC.setEnabled(true);
				btnPagarDeuda.setEnabled(true);
				btnAcredito.setEnabled(true);
				btnFacturasC.setEnabled(true);
			}
		});

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
		btnModificarC.setBackground(Color.WHITE);
		btnModificarC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				int idC = selectIDcliente;
				
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
		btnPagarDeuda.setBackground(Color.WHITE);
		btnPagarDeuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = table.getSelectedRow();
				int idC = selectIDcliente;
				String respuesta = JOptionPane.showInputDialog("¿Cuanto desea abonar a la deuda?");
				

				
				//float Deuda = Float.parseFloat((String) table.getValueAt(index, 5));
				
				
				if(index != -1) {
					float pagoDeuda = Float.parseFloat(respuesta);
					float Deuda = (float) table.getValueAt(index, 5);
					
					if(!(respuesta == null) &&  pagoDeuda <= Deuda) {
						
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
		btnAcredito.setBackground(Color.WHITE);
		btnAcredito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				int idC = selectIDcliente;
				String respuesta = JOptionPane.showInputDialog("¿Cuanto desea abonar al credito?");
				
				
				float aumentoCredito = Float.parseFloat(respuesta);
				
				if(index != -1) {
					if(!(respuesta == null) &&  aumentoCredito > 0) {
						

						float creditoActual = (float) table.getValueAt(index, 4);
						//float creditoActual = Float.parseFloat((String) table.getValueAt(index, 4));
						
						//float aumentoCredito = Float.parseFloat(respuesta);
						
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

		
		btnFacturasC = new JButton("Facturas del Cliente");
		btnFacturasC.setBackground(Color.WHITE);
		btnFacturasC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				int idC = selectIDcliente;
				
				if(index != -1) {
					listFactura2 listF2 = new listFactura2(idC,con);
					listF2.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "Selecione un cliente", "Validación", JOptionPane.WARNING_MESSAGE);
				}

				
			}
		});
		btnFacturasC.setEnabled(false);
		btnFacturasC.setBounds(723, 73, 162, 23);
		panel.add(btnFacturasC);
		
		tableCliente = new DefaultTableModel() {
			
			Class[] types = { Integer.class, String.class, String.class,String.class,Float.class, Float.class};
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
		tableCliente.setColumnIdentifiers(new Object[] {"ID","Nombre","Email","Telefono","Credito","Deuda"});
		Statement st;
		ResultSet datos = null;
		try {
			st = con.createStatement();
			datos = st.executeQuery("select ID_Cliente,Nombres,Apellidos,Email,Telefono,Credito,Deuda from Cliente where Enable = 1");
			while(datos.next()) {
				tableCliente.addRow(new Object[]{datos.getInt("ID_Cliente"),datos.getString("Nombres")+" "+datos.getString("Apellidos"),datos.getString("Email"),datos.getString("Telefono"),datos.getFloat("Credito"),datos.getFloat("Deuda")});
			}
			table.setModel(tableCliente);
		}catch(Exception e) {
			System.out.println(e.toString());
		}
		
		tr = new TableRowSorter<>(tableCliente);
		
		
		table.setRowSorter(tr);
						
		
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
