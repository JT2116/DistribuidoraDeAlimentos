package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class listSuplidor extends JFrame {
	private DefaultTableModel tableSuplidor;
	private JPanel contentPane;
	private JTable table;

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
	public listSuplidor(Connection con) {
		
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
		scrollPane.setColumnHeaderView(table);
		
		tableSuplidor= new DefaultTableModel();
		mostrar(con);
		scrollPane.setViewportView(table);
		
	}
	
	
	public void mostrar(Connection con){
		//Principal auxp = new Principal();
		//Connection cn = auxp.getConnection();
		tableSuplidor.setColumnIdentifiers(new Object[] {"ID","Nombre Compania","Nombre Representante","Apellido Representante","Email","Telefono"});
		Statement st;
		ResultSet datos = null;
		try {
			st = con.createStatement();
			datos = st.executeQuery("select ID_Suplidor,Nombre_Compania,Nombre_Representante,Apellido_Representante,Email,Telefono from Suplidor");
			while(datos.next()) {
				tableSuplidor.addRow(new Object[]{datos.getString("ID_Suplidor"),datos.getString("Nombre_Compania"),datos.getString("Nombre_Representante"),datos.getString("Apellido_Representante"),datos.getString("Email"),datos.getString("Telefono")});
			}
			table.setModel(tableSuplidor);
		}catch(Exception e) {
			System.out.println(e.toString());
		}
	}
	
	
	
	
	
	
	
}
