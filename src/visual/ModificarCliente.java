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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class ModificarCliente extends JFrame {

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
	public ModificarCliente(int idC,Connection con) {
		setTitle("Modificar datos del cliente");
		setBounds(100, 100, 385, 380);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setResizable(false);

		ImageIcon imagenIcon=new ImageIcon("imagenes"+File.separator+"consumidor.png");
		
		setIconImage(imagenIcon.getImage());
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Informacion del cliente", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
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
		//txtID.setText(String.valueOf(getIdCliente(con)));
		
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
		lblNewLabel_7_1.setBounds(10, 217, 201, 14);
		panel.add(lblNewLabel_7_1);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(20, 242, 310, 20);
		panel.add(txtEmail);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		buttonPane.setBounds(10, 297, 352, 38);
		contentPane.add(buttonPane);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton okButton = new JButton("Modificar");
		okButton.setBackground(Color.WHITE);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtTelefono.getText().equalsIgnoreCase("")  && !txtEmail.getText().equalsIgnoreCase("") && !txtNombres.getText().equalsIgnoreCase("") && !txtApellidos.getText().equalsIgnoreCase("") && !txtCredito.getText().equalsIgnoreCase("")) {
					
					String nombres = txtNombres.getText();
					String apellidos = txtApellidos.getText();
					String telefono = txtTelefono.getText();
					String email = txtEmail.getText();
					float credito = Float.parseFloat(txtCredito.getText());
					
					//System.out.println(credito);
					
					actualizarCliente(idC,nombres,apellidos,email,telefono,credito,con);
					listCliente2.tableCliente.setRowCount(0);
					listCliente2.mostrar(con);
					dispose();
										
					//clean();
					
				}else {
					JOptionPane.showMessageDialog(null, "Revise los datos", "Validaci?n", JOptionPane.WARNING_MESSAGE);
				}
								
				
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBackground(Color.WHITE);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		buscarCliente(idC,con);
	}
		
	public void buscarCliente(int IDCliente,Connection con) {
		
		PreparedStatement st3 = null;
		ResultSet datos3 = null;
		try {
			
			st3 = con.prepareStatement("select ID_Cliente,Nombres,Apellidos,Email,Telefono,Credito,Deuda from Cliente where ID_Cliente = ?");
			st3.setInt(1,IDCliente);
			
			datos3 = st3.executeQuery();
			
			while(datos3.next()) {
				//System.out.println(datos3.getString("ID"));
				txtID.setText(datos3.getString("ID_Cliente"));
				txtNombres.setText(datos3.getString("Nombres"));
				txtApellidos.setText(datos3.getString("Apellidos"));
				txtTelefono.setText(datos3.getString("Telefono"));
				txtEmail.setText(datos3.getString("Email"));
				txtCredito.setText(String.valueOf(datos3.getFloat("Credito")));
																
			}
		} catch (Exception e) {
			 System.out.println(e.getMessage());
		}	
	}
	
	private void actualizarCliente(int ID,String nombres, String apellidos,String email, String telefono,float credito,Connection con) {
		
		String insertTableSQLMarcaModelo = "update Cliente set Nombres = ?,Apellidos = ?,Email = ?,Telefono = ?,Credito = ? where ID_Cliente = ?";
		
		//System.out.println("owo");
		
		try {
			PreparedStatement preparedStmtV = con.prepareStatement(insertTableSQLMarcaModelo);
			
			preparedStmtV.setString(1,nombres);
			preparedStmtV.setString(2,apellidos);
			preparedStmtV.setString(3,email);
			preparedStmtV.setString(4,telefono);
			preparedStmtV.setFloat(5,credito);			
			preparedStmtV.setInt(6,ID);
			preparedStmtV.execute();
			
			
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
