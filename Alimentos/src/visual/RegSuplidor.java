package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Almacen;
import logica.Suplidor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
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
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class RegSuplidor extends JFrame {

	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtNombreSuplidor;
	private JTextField txtNombreRepresentante;
	private JTextField txtApellidoRepresentante;
	private JTextField txtEmail;
	private JTextField txtTelefono;
	
	
	private int IDsuplidor;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegSuplidor frame = new RegSuplidor();
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
	public RegSuplidor(Almacen al,Connection con) {
		setBounds(100, 100, 369, 424);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 350, 353, 35);
		contentPane.add(buttonPane);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton okButton = new JButton("Registrar");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtTelefono.getText().equalsIgnoreCase("")  && !txtEmail.getText().equalsIgnoreCase("") && !txtNombreRepresentante.getText().equalsIgnoreCase("") && !txtNombreSuplidor.getText().equalsIgnoreCase("") && !txtApellidoRepresentante.getText().equalsIgnoreCase("") ){
					String nombresS = txtNombreSuplidor.getText();			
					String nombresR = txtNombreRepresentante.getText();
					String apellidosR = txtApellidoRepresentante.getText();
					String telefonoS = txtTelefono.getText();
					String emailS = txtEmail.getText();
					
					
					insertarBDS(getIdSuplidor(con),nombresS,nombresR,apellidosR,emailS, telefonoS,con);
					
					txtID.setText(String.valueOf(getIdSuplidor(con)));
					
					/*Suplidor sup = new Suplidor(getIdSuplidor(con),nombresS,nombresR,apellidosR,telefonoS,emailS);
					
					al.insertarSuplidor(sup);*/
					
					//JOptionPane.showMessageDialog(null, "Registro Exitoso", "Información", JOptionPane.INFORMATION_MESSAGE);
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
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registrar informacion del suplidor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 336, 336);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("ID:");
		lblNewLabel_1.setBounds(10, 28, 24, 14);
		panel.add(lblNewLabel_1);
		
		txtID = new JTextField();
		txtID.setBounds(44, 25, 86, 20);
		panel.add(txtID);
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setText(String.valueOf(getIdSuplidor(con)));
		
		JLabel lblNewLabel_3 = new JLabel("Nombres del suplidor:");
		lblNewLabel_3.setBounds(10, 53, 168, 14);
		panel.add(lblNewLabel_3);
		
		txtNombreSuplidor = new JTextField();
		txtNombreSuplidor.setBounds(20, 78, 242, 20);
		panel.add(txtNombreSuplidor);
		txtNombreSuplidor.setColumns(10);
		
		JLabel lblNewLabel_3_1 = new JLabel("Nombres del representante:");
		lblNewLabel_3_1.setBounds(10, 109, 188, 14);
		panel.add(lblNewLabel_3_1);
		
		txtNombreRepresentante = new JTextField();
		txtNombreRepresentante.setBounds(20, 134, 242, 20);
		panel.add(txtNombreRepresentante);
		txtNombreRepresentante.setColumns(10);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Apellido del representante:");
		lblNewLabel_3_1_1.setBounds(10, 165, 188, 14);
		panel.add(lblNewLabel_3_1_1);
		
		txtApellidoRepresentante = new JTextField();
		txtApellidoRepresentante.setBounds(20, 190, 242, 20);
		panel.add(txtApellidoRepresentante);
		txtApellidoRepresentante.setColumns(10);
		
		JLabel lblNewLabel_3_1_1_1 = new JLabel("Correo electronico:");
		lblNewLabel_3_1_1_1.setBounds(10, 221, 188, 14);
		panel.add(lblNewLabel_3_1_1_1);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(20, 246, 310, 20);
		panel.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Telefono (Formato 8091127788):");
		lblNewLabel_7.setBounds(10, 277, 188, 14);
		panel.add(lblNewLabel_7);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(20, 302, 145, 20);
		panel.add(txtTelefono);
		txtTelefono.setColumns(10);
	}
	
	
	public void insertarBDS(int ID,String nombresSuplidor,String nombreRepresentate, String apellidoRepresentate,String email, String telefono,Connection con) {
		String insertTableSQLMarcaModelo = "insert into Suplidor values (?,?,?,?,?,?)";
		PreparedStatement st3 = null;
		ResultSet datos3 = null;
		String nS = null;
		String em = null;
		String tel = null;
				
		String id = null;
		int marcar = 0;
		try {
			PreparedStatement preparedStmtV = con.prepareStatement(insertTableSQLMarcaModelo);
			st3 = con.prepareStatement("select distinct * from Suplidor where Nombre_Compania = ? and Email = ? and Telefono = ?");
			
			st3.setString(1,nombresSuplidor);
			st3.setString(2,email);
			st3.setString(3,telefono);
				
			datos3 = st3.executeQuery();
			
			while(datos3.next()) {
				
				nS = datos3.getString("Nombre_Compania");
				em = datos3.getString("Email");
				tel = datos3.getString("Telefono");
				
								
				if(em.equals(email) && tel.equals(telefono) && nS.equals(nombresSuplidor)) {
					marcar = 1;
					//id = datos3.getString("ID_Suplidor");
					JOptionPane.showMessageDialog(null, "Este suplidor ya existe", "Validación", JOptionPane.WARNING_MESSAGE);
					clean();
				}else {
					marcar = 2;
				}
			}
			
			if(marcar == 0 || marcar == 2) {
				preparedStmtV.setInt(1,ID);
				preparedStmtV.setString(2,nombresSuplidor);
				preparedStmtV.setString(3,nombreRepresentate);
				preparedStmtV.setString(4,apellidoRepresentate);
				preparedStmtV.setString(5,email);
				preparedStmtV.setString(6,telefono);		
				preparedStmtV.execute();
				JOptionPane.showMessageDialog(null, "Registro Exitoso", "Información", JOptionPane.INFORMATION_MESSAGE);
			}
			

			
		}catch (SQLException e) {

            System.out.println(e.getMessage());

        }
		
	}
	
	private int getIdSuplidor(Connection con) {
		
		int id = 0;
		Statement st2;
		ResultSet datos2 = null;
		
		try {
			st2 = con.createStatement();
			datos2 = st2.executeQuery("select count(ID_Suplidor) as Cantidad from Suplidor");
			
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
	
	
	
	private void clean() {
		txtNombreSuplidor.setText("");
		txtNombreRepresentante.setText("");
		txtApellidoRepresentante.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");;	
	}
}
