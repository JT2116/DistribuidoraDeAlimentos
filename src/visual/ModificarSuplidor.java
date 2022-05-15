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
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class ModificarSuplidor extends JFrame {

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
	public ModificarSuplidor(int idS,Connection con) {
		setTitle("Modificar datos del suplidor");
		setBounds(100, 100, 369, 439);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setResizable(false);
		
		ImageIcon imagenIcon=new ImageIcon("imagenes"+File.separator+"repartidor.png");
		setIconImage(imagenIcon.getImage());
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		buttonPane.setBounds(10, 358, 336, 35);
		contentPane.add(buttonPane);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton okButton = new JButton("Modificar");
		okButton.setBackground(Color.WHITE);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtTelefono.getText().equalsIgnoreCase("")  && !txtEmail.getText().equalsIgnoreCase("") && !txtNombreRepresentante.getText().equalsIgnoreCase("") && !txtNombreSuplidor.getText().equalsIgnoreCase("") && !txtApellidoRepresentante.getText().equalsIgnoreCase("") ){
					String nombresS = txtNombreSuplidor.getText();			
					String nombresR = txtNombreRepresentante.getText();
					String apellidosR = txtApellidoRepresentante.getText();
					String telefonoS = txtTelefono.getText();
					String emailS = txtEmail.getText();
					
					actualizarSuplidor(idS,nombresS,nombresR, apellidosR,emailS, telefonoS,con);
					listSuplidor2.tableSuplidor.setRowCount(0);
					
					listSuplidor2.mostrar(con);
					dispose();
				
					
				}else {
					JOptionPane.showMessageDialog(null, "Revise los datos", "Validación", JOptionPane.WARNING_MESSAGE);
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
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registrar informacion del suplidor", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
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
		//txtID.setText(String.valueOf(getIdSuplidor(con)));
		
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
		
		buscarSuplidor(idS,con);
	}
	
	
	public void buscarSuplidor(int IDSuplidor,Connection con) {
		PreparedStatement st3 = null;
		ResultSet datos3 = null;
		
		try {
			st3 = con.prepareStatement("select ID_Suplidor,Nombre_Compania,Nombre_Representante,Apellido_Representante,Email,Telefono from Suplidor where ID_Suplidor = ?");
			st3.setInt(1,IDSuplidor);
			
			datos3 = st3.executeQuery();
			
			while (datos3.next()) {
				
				txtID.setText(datos3.getString("ID_Suplidor"));
				txtNombreSuplidor.setText(datos3.getString("Nombre_Compania"));
				txtNombreRepresentante.setText(datos3.getString("Nombre_Representante"));
				txtApellidoRepresentante.setText(datos3.getString("Apellido_Representante"));
				txtEmail.setText(datos3.getString("Email"));
				txtTelefono.setText(datos3.getString("Telefono"));
				
			}
			
			
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
	}
	
	private void actualizarSuplidor(int ID,String nombresSuplidor,String nombreRepresentate, String apellidoRepresentate,String email, String telefono,Connection con) {
		
		String insertTableSQLMarcaModelo = "update Suplidor set Nombre_Compania = ?,Nombre_Representante = ?,Apellido_Representante = ?,Email = ?,Telefono = ? where ID_Suplidor = ?";
		
		try {
			PreparedStatement preparedStmtV = con.prepareStatement(insertTableSQLMarcaModelo);
			
			preparedStmtV.setString(1,nombresSuplidor);
			preparedStmtV.setString(2,nombreRepresentate);
			preparedStmtV.setString(3,apellidoRepresentate);
			preparedStmtV.setString(4,email);
			preparedStmtV.setString(5,telefono);
				
			preparedStmtV.setInt(6,ID);
			preparedStmtV.execute();
			
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
		
	}
	
	
	
	
	
	private void clean() {
		txtNombreSuplidor.setText("");
		txtNombreRepresentante.setText("");
		txtApellidoRepresentante.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");;	
	}
}
