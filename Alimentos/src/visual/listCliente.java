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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class listCliente extends JFrame {
	private DefaultTableModel tableCliente;
	private JPanel contentPane;
	private JTable table;
	
	public static String selectIDcliente;

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
				
				buscarCliente(Integer.valueOf(selectIDcliente), con);
				
				
				dispose();
			}
		});
		scrollPane.setColumnHeaderView(table);
		
		tableCliente = new DefaultTableModel();
		mostrar(con);
		
		scrollPane.setViewportView(table);
		
		
	}
	
	
	public void mostrar(Connection con){
		//Principal auxp = new Principal();
		//Connection cn = auxp.getConnection();
		tableCliente.setColumnIdentifiers(new Object[] {"ID","Nombres","Apellidos","Email","Telefono","Credito","Deuda"});
		Statement st;
		ResultSet datos = null;
		try {
			st = con.createStatement();
			datos = st.executeQuery("select ID_Cliente,Nombres,Apellidos,Email,Telefono,Credito,Deuda from Cliente");
			while(datos.next()) {
				tableCliente.addRow(new Object[]{datos.getString("ID_Cliente"),datos.getString("Nombres"),datos.getString("Apellidos"),datos.getString("Email"),datos.getString("Telefono"),datos.getString("Credito"),datos.getString("Deuda")});
			}
			table.setModel(tableCliente);
		}catch(Exception e) {
			System.out.println(e.toString());
		}
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
	
	
	
	
	
}
