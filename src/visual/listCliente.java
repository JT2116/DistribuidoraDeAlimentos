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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class listCliente extends JFrame {
	private DefaultTableModel tableCliente;
	private JPanel contentPane;
	private JTable table;
	
	public static int selectIDcliente;
	private JTextField txtBuscarC;
	
	TableRowSorter<DefaultTableModel> tr;
	
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
	public listCliente(Connection con) {
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
				
				buscarCliente(Integer.valueOf(selectIDcliente), con);
								
				dispose();
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
	
	
	public void mostrar(Connection con){
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
	
	
	public void buscarCliente(int IDCliente,Connection con) {
		RegFactura.IDclientes = IDCliente;
		PreparedStatement st3 = null;
		ResultSet datos3 = null;
		try {
			
			st3 = con.prepareStatement("select ID_Cliente,Nombres,Apellidos,Email,Telefono,Credito,Deuda from Cliente where ID_Cliente = ?");
			st3.setInt(1,IDCliente);
			
			datos3 = st3.executeQuery();
			
			while(datos3.next()) {
				//System.out.println(datos3.getString("ID"));
				RegFactura.idCliente = datos3.getInt("ID_Cliente");
				RegFactura.txtNombreC.setText(datos3.getString("Nombres")+" "+datos3.getString("Apellidos"));
				RegFactura.txtTelefonoC.setText(datos3.getString("Telefono"));
				RegFactura.txtEmailC.setText(datos3.getString("Email"));
				RegFactura.txtCreditoC.setText(String.valueOf( datos3.getFloat("Credito")));
				RegFactura.txtDeudaC.setText(String.valueOf( datos3.getFloat("Deuda")));
				//repaint();								
			}
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
