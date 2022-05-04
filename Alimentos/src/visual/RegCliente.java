package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import logica.Almacen;
import logica.Cliente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class RegCliente extends JFrame {

	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtCredito;
	private JTextField txtNombres;
	private JTextField txtApellidos;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	
	
	
	

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegCliente frame = new RegCliente();
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
	public RegCliente(Almacen al,Connection con) {
		
		setBounds(100, 100, 388, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Informacion del cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 352, 279);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("ID:");
		lblNewLabel_1.setBounds(10, 24, 24, 14);
		panel.add(lblNewLabel_1);
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(44, 21, 86, 20);
		panel.add(txtID);
		txtID.setText(String.valueOf(getIdCliente(con)));
		
		JLabel lblNewLabel_3_1 = new JLabel("Credito:");
		lblNewLabel_3_1.setBounds(140, 24, 57, 14);
		panel.add(lblNewLabel_3_1);
		
		txtCredito = new JTextField();
		txtCredito.setColumns(10);
		txtCredito.setBounds(192, 21, 86, 20);
		panel.add(txtCredito);
		
		txtNombres = new JTextField();
		txtNombres.setColumns(10);
		txtNombres.setBounds(20, 74, 242, 20);
		panel.add(txtNombres);
		
		JLabel lblNewLabel_3 = new JLabel("Nombres:");
		lblNewLabel_3.setBounds(10, 49, 113, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_5 = new JLabel("Apellidos:");
		lblNewLabel_5.setBounds(10, 105, 113, 14);
		panel.add(lblNewLabel_5);
		
		txtApellidos = new JTextField();
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(20, 130, 242, 20);
		panel.add(txtApellidos);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(20, 186, 145, 20);
		panel.add(txtTelefono);
		
		JLabel lblNewLabel_7 = new JLabel("Telefono (Formato 8091127788):");
		lblNewLabel_7.setBounds(10, 161, 201, 14);
		panel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_7_1 = new JLabel("Correo electronico:");
		lblNewLabel_7_1.setBounds(10, 217, 96, 14);
		panel.add(lblNewLabel_7_1);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(20, 242, 310, 20);
		panel.add(txtEmail);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 297, 372, 38);
		contentPane.add(buttonPane);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton okButton = new JButton("Registrar");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtTelefono.getText().equalsIgnoreCase("")  && !txtEmail.getText().equalsIgnoreCase("") && !txtNombres.getText().equalsIgnoreCase("") && !txtApellidos.getText().equalsIgnoreCase("") && !txtCredito.getText().equalsIgnoreCase("")) {
					String nombres = txtNombres.getText();
					String apellidos = txtApellidos.getText();
					String telefono = txtTelefono.getText();
					String email = txtEmail.getText();
					float credito = Float.parseFloat(txtCredito.getText());
					
					insertarBDC(getIdCliente(con),nombres,apellidos,email,telefono,credito,con);
					
					txtID.setText(String.valueOf(getIdCliente(con)));
					
					int idCliente = getIdCliente(con) - 1; 
					
					buscarCliente(idCliente, con);
					//buscarCliente(1, con);
					
					dispose();
					
					/*Cliente cl = new Cliente(getIdCliente(con),nombres,apellidos,email,telefono,credito,0);
					
					al.insertarCliente(cl);*/
					
					
					clean();
					
				}else {
					JOptionPane.showMessageDialog(null, "Revise los datos", "Validación", JOptionPane.WARNING_MESSAGE);
				}
				
				
				
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}
	
	public void insertarBDC(int ID,String nombres, String apellidos,String email, String telefono,float credito,Connection con) {
		String insertTableSQLMarcaModelo = "insert into Cliente values (?,?,?,?,?,?,?)";
		PreparedStatement st3 = null;
		ResultSet datos3 = null;
		String nC = null;
		String aC = null;
		
				
		String id = null;
		int marcar = 0;
		try {
			PreparedStatement preparedStmtV = con.prepareStatement(insertTableSQLMarcaModelo);
			st3 = con.prepareStatement("select distinct * from Cliente where Nombres = ? and Apellidos = ?");
			
			st3.setString(1,nombres);
			st3.setString(2,apellidos);
			
				
			datos3 = st3.executeQuery();
			
			while(datos3.next()) {
				
				nC = datos3.getString("Nombres");
				aC = datos3.getString("Apellidos");
			
				
								
				if(aC.equals(apellidos) && nC.equals(nombres)) {
					marcar = 1;
					//id = datos3.getString("ID_Suplidor");
					JOptionPane.showMessageDialog(null, "Este cliente ya esta registrado", "Validación", JOptionPane.WARNING_MESSAGE);
					clean();
				}else {
					marcar = 2;
				}
			}
			
			if(marcar == 0 || marcar == 2) {
				preparedStmtV.setInt(1,ID);
				preparedStmtV.setString(2,nombres);
				preparedStmtV.setString(3,apellidos);
				preparedStmtV.setString(4,email);
				preparedStmtV.setString(5,telefono);	
				preparedStmtV.setFloat(6,credito);
				preparedStmtV.setFloat(7,0);
				preparedStmtV.execute();
				JOptionPane.showMessageDialog(null, "Registro Exitoso", "Información", JOptionPane.INFORMATION_MESSAGE);
			}
			

			
		}catch (SQLException e) {

            System.out.println(e.getMessage());

        }
		
	}
		
	
	private int getIdCliente(Connection con) {
		
		int id = 0;
		Statement st2;
		ResultSet datos2 = null;
		
		try {
			st2 = con.createStatement();
			datos2 = st2.executeQuery("select count(ID_Cliente) as Cantidad from Cliente");
			
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
	
	
	private void clean() {
		txtCredito.setText("");
		txtNombres.setText("");
		txtApellidos.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");;	
	}
	

	
	
}
