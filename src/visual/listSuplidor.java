package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class listSuplidor extends JFrame {
	private DefaultTableModel tableSuplidor;
	private JPanel contentPane;
	private JTable table;
	private JTextField txtBuscarS;

	public static String selectSuplidor;
	TableRowSorter<DefaultTableModel> tr;
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
		setTitle("Lista de suplidores");
		setBounds(100, 100, 987, 605);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setResizable(false);
		
		ImageIcon imagenIcon=new ImageIcon("imagenes"+File.separator+"repartidor.png");
		setIconImage(imagenIcon.getImage());
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Lista de suplidores", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
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
				selectSuplidor = (String) table.getValueAt(index, 1);
								
				RegProducto.txtNombreS.setText(selectSuplidor);
				
				dispose();				
			}
		});
		scrollPane.setColumnHeaderView(table);
		
		tableSuplidor= new DefaultTableModel();
		mostrar(con);
		scrollPane.setViewportView(table);
		
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
	
	
	public void mostrar(Connection con){
		//Principal auxp = new Principal();
		//Connection cn = auxp.getConnection();
		tableSuplidor.setColumnIdentifiers(new Object[] {"ID","Nombre Compania","Nombre Representante","Apellido Representante","Email","Telefono"});
		Statement st;
		ResultSet datos = null;
		try {
			st = con.createStatement();
			datos = st.executeQuery("select ID_Suplidor,Nombre_Compania,Nombre_Representante,Apellido_Representante,Email,Telefono from Suplidor where Enable = 1");
			while(datos.next()) {
				tableSuplidor.addRow(new Object[]{datos.getString("ID_Suplidor"),datos.getString("Nombre_Compania"),datos.getString("Nombre_Representante"),datos.getString("Apellido_Representante"),datos.getString("Email"),datos.getString("Telefono")});
			}
			table.setModel(tableSuplidor);
		}catch(Exception e) {
			System.out.println(e.toString());
		}
		
		tr = new TableRowSorter<>(tableSuplidor);
				
		table.setRowSorter(tr);		
		
	}
	
	
	
	private void filtrarSuplidor() {
		String filtro = txtBuscarS.getText();
		
		TableRowSorter trsfiltro = new TableRowSorter(table.getModel());
		
		table.setRowSorter(trsfiltro);
		
		trsfiltro.setRowFilter(RowFilter.regexFilter(txtBuscarS.getText(), 1));
				
	}
	
	
	
}
