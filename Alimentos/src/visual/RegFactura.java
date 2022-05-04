package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import logica.Almacen;
import logica.Producto;
import logica.Suplidor;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RegFactura extends JFrame {

	private JPanel contentPane;
	public static JTextField txtNombreC;
	public static JTextField txtEmailC;
	public static JTextField txtTelefonoC;
	public static JTextField txtDeudaC;
	public static JTextField txtCreditoC;
	public static JTextField txtTotal;
	private JButton btnVender;
	private JButton btnDevolver;
	
	public static int IDclientes;

	private int ID = 0; 
	private String suplidor = null; 
	private String nombreP = null; 
	private int cantidad = 0;
	private float precioC = 0; 
	private float precioV = 0;
	private String tipo = null;
	private String descripcion = null;
	private float totalC = 0;
	public static int idCliente;
	
	
	private JTable tableAntes;
	private JTable tableVentas;
	private DefaultTableModel tableProductosAntes;
	private DefaultTableModel tableProductosVentas;
	private JTextField txtBuscar;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegFactura frame = new RegFactura();
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
	public RegFactura(Almacen al,Connection con) {
		
		al.getProductos().clear();
		llenarAlmacen(al,con);
		
		setBounds(100, 100, 994, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelCliente = new JPanel();
		panelCliente.setLayout(null);
		panelCliente.setBorder(new TitledBorder(null, "Buscar cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCliente.setBounds(10, 11, 958, 114);
		contentPane.add(panelCliente);
		
		JLabel lblNombre_1 = new JLabel("Nombre:");
		lblNombre_1.setBounds(10, 49, 56, 14);
		panelCliente.add(lblNombre_1);
		
		txtNombreC = new JTextField();
		txtNombreC.setEditable(false);
		txtNombreC.setColumns(10);
		txtNombreC.setBounds(10, 74, 234, 20);
		panelCliente.add(txtNombreC);
		
		JLabel lblDireccion = new JLabel("Correo electronico:");
		lblDireccion.setBounds(254, 49, 186, 14);
		panelCliente.add(lblDireccion);
		
		txtEmailC = new JTextField();
		txtEmailC.setEditable(false);
		txtEmailC.setColumns(10);
		txtEmailC.setBounds(254, 74, 275, 20);
		panelCliente.add(txtEmailC);
		
		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(539, 52, 73, 14);
		panelCliente.add(lblTelefono);
		
		txtTelefonoC = new JTextField();
		txtTelefonoC.setEditable(false);
		txtTelefonoC.setColumns(10);
		txtTelefonoC.setBounds(539, 74, 126, 20);
		panelCliente.add(txtTelefonoC);
		
		JLabel lblCredicto = new JLabel("Cr\u00E9dicto:");
		lblCredicto.setBounds(675, 52, 56, 14);
		panelCliente.add(lblCredicto);
		
		txtDeudaC = new JTextField();
		txtDeudaC.setText("0");
		txtDeudaC.setEditable(false);
		txtDeudaC.setColumns(10);
		txtDeudaC.setBounds(811, 74, 124, 20);
		panelCliente.add(txtDeudaC);
		
		JLabel lblDeuda = new JLabel("Deuda:");
		lblDeuda.setBounds(811, 52, 46, 14);
		panelCliente.add(lblDeuda);
		
		txtCreditoC = new JTextField();
		txtCreditoC.setText("0");
		txtCreditoC.setEditable(false);
		txtCreditoC.setColumns(10);
		txtCreditoC.setBounds(675, 74, 126, 20);
		panelCliente.add(txtCreditoC);
		
		JButton btnBuscar = new JButton((Icon) null);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listCliente listC = new listCliente(con);
				listC.setVisible(true);
			}			
		});
		btnBuscar.setText("Buscar cliente");
		btnBuscar.setBounds(10, 20, 126, 23);
		panelCliente.add(btnBuscar);
		
		JButton btnPagarDeuda = new JButton((Icon) null);
		btnPagarDeuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idCliente != 0) {
					//System.out.println("owo");					
					int idC = idCliente;
					String respuesta = JOptionPane.showInputDialog("¿Cuanto desea abonar a la deuda?");
					if(!(respuesta == null) &&  Float.parseFloat(respuesta)<=Float.parseFloat(txtDeudaC.getText())) {
						
						float pagoDeuda = Float.parseFloat(respuesta);
						
						float Deuda = Float.parseFloat(txtDeudaC.getText());
						
						//System.out.println("owo");
						pagarDeuda(idC,Float.parseFloat(respuesta),Float.parseFloat(txtDeudaC.getText()), con);
						
						txtDeudaC.setText(String.valueOf(Deuda - pagoDeuda));
					} 
					
				}else {
					JOptionPane.showMessageDialog(null, "Selecione un cliente", "Validación", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		btnPagarDeuda.setText("Pagar deuda");
		btnPagarDeuda.setBounds(811, 20, 137, 23);
		panelCliente.add(btnPagarDeuda);
		
		JButton btnNewButton = new JButton("Crear cliente");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegCliente rgC = new RegCliente(al,con);
				rgC.setVisible(true);
			}
		});
		btnNewButton.setBounds(148, 20, 124, 23);
		panelCliente.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Aumentar credicto");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idCliente != 0) {
					//System.out.println("owo");
					int idC = idCliente;
					String respuesta = JOptionPane.showInputDialog("¿Cuanto desea abonar al credito?");
					
					if(!(respuesta == null)) {
												
						//float creditoActual = Float.parseFloat(respuesta);
						
						float creditoActual = Float.parseFloat(txtCreditoC.getText());
						
						float aumentoCredito = Float.parseFloat(respuesta);
						
						aumentarCredito(idC,creditoActual,aumentoCredito, con);
						
						txtCreditoC.setText(String.valueOf(creditoActual + aumentoCredito));
					}
					
					
					
				}else {
					JOptionPane.showMessageDialog(null, "Selecione un cliente", "Validación", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNewButton_1.setBounds(635, 20, 166, 23);
		panelCliente.add(btnNewButton_1);
		
		JPanel panelFactura = new JPanel();
		panelFactura.setLayout(null);
		panelFactura.setBorder(new TitledBorder(null, "Factura", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelFactura.setBounds(10, 136, 958, 358);
		contentPane.add(panelFactura);
		
		btnVender = new JButton((Icon) null);
		btnVender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DecimalFormat dv = new DecimalFormat("#.00");
				
				int marca = 0;				
				int index = tableAntes.getSelectedRow();
				int tablaVentaCount = tableVentas.getRowCount();
				int filaCV = 0;

				if(index != -1) {
					int idP= (int) tableAntes.getValueAt(index, 0);					
					Producto proV = al.buscarProducto(idP);
					
					for (int i = 0; i < tablaVentaCount; i++) {
						if( (int) tableVentas.getValueAt(i, 0) == proV.getID()) {

							filaCV = i;
							marca = 1;
						}
					}
										
					if(proV.getCantidad()>=1) {
																		
						proV.setCantidad(proV.getCantidad()-1);
						
						totalC += proV.getPrecioVenta();
						
						if(marca !=1 ) {
							CargarProductosVentas(proV);							
						}else {

							tableVentas.setValueAt((int) tableVentas.getValueAt(filaCV, 3)+1, filaCV, 3);
							tableVentas.setValueAt( (float) tableVentas.getValueAt(filaCV, 4) + proV.getPrecioVenta(),filaCV,4); 
						}
						
						
						txtTotal.setText(dv.format(totalC));																																			
						tableProductosAntes.setRowCount(0);
						ListaDeProductosAntes(al);						
					}else if(proV.getCantidad() == 0){
						JOptionPane.showMessageDialog(null, "Este producto esta agotado", "Validación", JOptionPane.WARNING_MESSAGE);
					}
											
					
				}else {
					JOptionPane.showMessageDialog(null, "Selecione un producto", "Validación", JOptionPane.WARNING_MESSAGE);
				}
				//System.out.println(proV);
								
			}
		});
		btnVender.setText("----->");
		btnVender.setEnabled(false);
		btnVender.setBounds(436, 154, 89, 23);
		panelFactura.add(btnVender);
		
		btnDevolver = new JButton((Icon) null);
		btnDevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DecimalFormat dv = new DecimalFormat("#.00");
				
				int marca = 0;				
				int index = tableVentas.getSelectedRow();
				//int tablaVentaCount = tableVentas.getRowCount();
				//int filaCV = 0;
				
				if(index != -1) {
					
					int idP= (int) tableVentas.getValueAt(index, 0);					
					Producto proV = al.buscarProducto(idP);
					
					if((int) tableVentas.getValueAt(index, 3)>=2) {
						proV.setCantidad(proV.getCantidad()+1);						
						totalC -= proV.getPrecioVenta();
						
						//System.out.println("owo");
						tableVentas.setValueAt((int) tableVentas.getValueAt(index, 3)-1, index, 3);
						tableVentas.setValueAt( (float) tableVentas.getValueAt(index, 4) - proV.getPrecioVenta(),index,4); 
					}else {
						proV.setCantidad(proV.getCantidad()+1);						
						totalC -= proV.getPrecioVenta();
						
						//System.out.println("owo");
						tableVentas.setValueAt((int) tableVentas.getValueAt(index, 3)-1, index, 3);
						tableVentas.setValueAt( (float) tableVentas.getValueAt(index, 4) - proV.getPrecioVenta(),index,4);																		
						tableProductosVentas.removeRow(index);
					}
					
					
					
					txtTotal.setText(dv.format(totalC));																																			
					tableProductosAntes.setRowCount(0);
					ListaDeProductosAntes(al);
					
				}else {
					JOptionPane.showMessageDialog(null, "Selecione un producto", "Validación", JOptionPane.WARNING_MESSAGE);
				}
				
				
			}
		});
		btnDevolver.setText("<-----");
		btnDevolver.setEnabled(false);
		btnDevolver.setBounds(436, 187, 89, 23);
		panelFactura.add(btnDevolver);
		
		JLabel lblTontal = new JLabel("Balance total:");
		lblTontal.setForeground(Color.RED);
		lblTontal.setBounds(738, 333, 89, 14);
		panelFactura.add(lblTontal);
		
		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);
		txtTotal.setBounds(837, 330, 80, 20);
		panelFactura.add(txtTotal);
		
		JLabel lblElegirTipoDe = new JLabel("Buscar productos:");
		lblElegirTipoDe.setBounds(20, 25, 162, 14);
		panelFactura.add(lblElegirTipoDe);
		
		JLabel label = new JLabel("$");
		label.setBounds(927, 333, 21, 14);
		panelFactura.add(label);
		
		JPanel listAnte = new JPanel();
		listAnte.setLayout(null);
		listAnte.setBounds(10, 50, 416, 272);
		panelFactura.add(listAnte);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 416, 272);
		listAnte.add(scrollPane);
		
		tableAntes = new JTable();
		tableAntes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnVender.setEnabled(true);
			}
		});
		tableProductosAntes= new DefaultTableModel();
		scrollPane.setColumnHeaderView(tableAntes);
		
		ListaDeProductosAntes(al);

		scrollPane.setViewportView(tableAntes);
		
		
		JPanel listVentas = new JPanel();
		listVentas.setLayout(null);
		listVentas.setBounds(535, 50, 413, 272);
		panelFactura.add(listVentas);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 413, 272);
		listVentas.add(scrollPane_1);
		
		tableVentas = new JTable();
		tableVentas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnDevolver.setEnabled(true);
			}
		});
		tableProductosVentas= new DefaultTableModel();
		scrollPane_1.setColumnHeaderView(tableVentas);
		
		scrollPane_1.setViewportView(tableVentas);
		
		txtBuscar = new JTextField();
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				String cadena = (txtBuscar.getText());
				
				txtBuscar.setText(cadena);
				
				repaint();
				filtrarProductos();
				
			}
		});
		txtBuscar.setBounds(141, 22, 183, 20);
		panelFactura.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(null);
		buttonPane.setBounds(10, 505, 958, 33);
		contentPane.add(buttonPane);
		
		JButton btnRealizarComp = new JButton("Completar la compra");
		btnRealizarComp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int idC = idCliente;
				float totalCompra = totalC;
				Calendar cal = Calendar.getInstance();
				java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
				
				float credito = Float.parseFloat(txtCreditoC.getText());
				
				float deuda = Float.parseFloat(txtDeudaC.getText());
				
				
				if (idC != 0 && tableVentas.getRowCount() != 0) {
					
					String[] options = {"Crédito", "Contado"};
					int seleccion = JOptionPane.showOptionDialog(null, "¿Que tipo de compra desea hacer?", "Tipo de compra", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
								
					if(seleccion==1) {
						//System.out.println("ewe");						
						
						int idF = getIdFactura(con);
						
						crearFactura(idF,idC,timestamp,"Contado",con);
						//System.out.println(idF);
						for (int i = 0; i < tableVentas.getRowCount(); i++) {
														
							detallesFactura(getIdDetallesC(con),idF,(int) tableVentas.getValueAt(i, 0), (float) tableVentas.getValueAt(i, 4),(int) tableVentas.getValueAt(i, 3), con); 
							//int idD = getIdDetallesC(con);
							//System.out.println(idD);		
							actualizarProducto((int) tableVentas.getValueAt(i, 0), (int) tableVentas.getValueAt(i, 3), con);
						}
						
						
						tableProductosVentas.setRowCount(0);
						
						clean();
						
						totalC = 0;
					}
					
					if(credito>totalCompra && credito>deuda && seleccion == 0) {
						System.out.println("owo");
						
						int idF = getIdFactura(con);
						
						crearFactura(idF,idC,timestamp,"Crédito",con);
						
						for (int i = 0; i < tableVentas.getRowCount(); i++) {
							
							detallesFactura(getIdDetallesC(con),idF,(int) tableVentas.getValueAt(i, 0), (float) tableVentas.getValueAt(i, 4),(int) tableVentas.getValueAt(i, 3), con); 
							//int idD = getIdDetallesC(con);
							//System.out.println(idD);		
							actualizarProducto((int) tableVentas.getValueAt(i, 0), (int) tableVentas.getValueAt(i, 3), con);
						}
						
						float creditoActual = Float.parseFloat(txtCreditoC.getText());
						
						float deudaActual = Float.parseFloat(txtDeudaC.getText());
						
						atualizarDeudaCredito(idC,creditoActual, deudaActual, totalCompra, con);
						tableProductosVentas.setRowCount(0);
						
						clean();
						
						totalC = 0;
					}else if(credito<totalCompra) {
						JOptionPane.showMessageDialog(null, "No tiene suficiente credito", "Validación", JOptionPane.WARNING_MESSAGE);
					}
					
					
					
				}else {
					JOptionPane.showMessageDialog(null, "Selecione un cliente y/o un producto", "Validación", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		btnRealizarComp.setActionCommand("OK");
		btnRealizarComp.setBounds(684, 5, 190, 23);
		buttonPane.add(btnRealizarComp);
		
		JButton cancelButton = new JButton("Salir");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		cancelButton.setBounds(884, 5, 64, 23);
		buttonPane.add(cancelButton);
	}
	
	private void llenarAlmacen(Almacen al,Connection con) {
		
		Statement st;
		ResultSet datos = null;
		
		try {
			st = con.createStatement();
			datos = st.executeQuery("select ID_Producto,Nombre,Nombre_Compania,Cantidad,PrecioCompra,PrecioVenta,Tipo,Descripcion from Producto join Suplidor on Producto.Suplidor = Suplidor.ID_Suplidor");
			
			while(datos.next()) {
				
				/*int ID = datos.getInt("ID_Producto"); 
				String suplidor = datos.getString("Nombre_Compania"); 
				String nombreP = datos.getString("Nombre"); 
				int cantidad = datos.getInt("Cantidad");
				float precioC = datos.getFloat("PrecioCompra"); 
				float precioV = datos.getFloat("PrecioVenta");
				String tipo = datos.getString("Tipo");
				String descripcion = datos.getString("Descripcion");*/
				
				ID = datos.getInt("ID_Producto"); 
				suplidor = datos.getString("Nombre_Compania"); 
				nombreP = datos.getString("Nombre"); 
				cantidad = datos.getInt("Cantidad");
				precioC = datos.getFloat("PrecioCompra"); 
				precioV = datos.getFloat("PrecioVenta");
				tipo = datos.getString("Tipo");
				descripcion = datos.getString("Descripcion");
				
				Producto pro = new Producto(ID,suplidor, nombreP, cantidad,precioC, precioV,tipo,descripcion);
				
				al.insertarProducto(pro);
												
			}
			
		}catch(Exception e) {
			System.out.println(e.toString());
		}
		
		
	}
	
	
	private void ListaDeProductosAntes(Almacen al){

		tableProductosAntes.setColumnIdentifiers(new Object[] {"ID","Producto","Suplidor","Cantidad","Precio"});
		
		for(int i=0; i<al.getProductos().size();i++){
			tableProductosAntes.addRow(new Object[]{al.getProductos().get(i).getID(),al.getProductos().get(i).getNombre(),al.getProductos().get(i).getSuplidor(),al.getProductos().get(i).getCantidad(),al.getProductos().get(i).getPrecioVenta()});
		}
		
		tableAntes.setModel(tableProductosAntes);
		//tableProductosAntes.fireTableDataChanged();
	}
	
		
	private void CargarProductosVentas(Producto producto) {
				
		tableProductosVentas.setColumnIdentifiers(new Object[] {"ID","Producto","Suplidor","Cantidad","Precio"});
				
		tableProductosVentas.addRow(new Object[]{producto.getID(),producto.getNombre(),producto.getSuplidor(),1,producto.getPrecioVenta()});
		
		tableVentas.setModel(tableProductosVentas);
		
	}
	
	
	private void filtrarProductos() {
		String filtro = txtBuscar.getText();
		
		TableRowSorter trsfiltro = new TableRowSorter(tableAntes.getModel());
		
		tableAntes.setRowSorter(trsfiltro);
		
		trsfiltro.setRowFilter(RowFilter.regexFilter(txtBuscar.getText(), 1));
				
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
	
	private void atualizarDeudaCredito(int idC,float creditoActual, float deudaActual, float totalDeCompra, Connection con) {
		String insertTableSQLMarcaModelo = "update Cliente set Credito = ?, Deuda = ? where ID_Cliente = ?";
		
		float resulCredito = creditoActual - totalDeCompra;
		
		float resulDeuda = deudaActual + totalDeCompra;
		
		try {
			PreparedStatement preparedStmtV = con.prepareStatement(insertTableSQLMarcaModelo);
			
			preparedStmtV.setFloat(1,resulCredito);
			preparedStmtV.setFloat(2,resulDeuda);
			preparedStmtV.setInt(3,idC);
			
			preparedStmtV.execute();
									
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
	

	
	private void crearFactura(int idF, int idC, java.sql.Timestamp timestamp, String tipoPago,Connection con) {
		String insertTableSQLMarcaModelo = "insert into Factura values (?,?,?,?)";
		//PreparedStatement st = null;
		//ResultSet datos = null;
		
		try {
			
			PreparedStatement preparedStmtV = con.prepareStatement(insertTableSQLMarcaModelo);
			
			
			preparedStmtV.setInt(1,idF);
			preparedStmtV.setInt(2,idC);
			preparedStmtV.setTimestamp(3,timestamp);
			preparedStmtV.setString(4,tipoPago);
			
			
			preparedStmtV.execute();
			
			
			JOptionPane.showMessageDialog(null, "Compra realizada", "Información", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
		
		
	}
		
	private int getIdFactura(Connection con) {
				
		int id = 0;
		Statement st2;
		ResultSet datos2 = null;
		
		try {
			st2 = con.createStatement();
			datos2 = st2.executeQuery("select count(ID_Factura) as Cantidad from Factura");
			
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
	
	
	private void detallesFactura(int idD, int idF, int idP, float precioC, int cantidadP, Connection con) {
		String insertTableSQLMarcaModelo = "insert into DetallesCompra values (?,?,?,?,?)";
		
		try {
			PreparedStatement preparedStmtV = con.prepareStatement(insertTableSQLMarcaModelo);
			
			preparedStmtV.setInt(1,idD);
			preparedStmtV.setInt(2,idF);
			preparedStmtV.setInt(3,idP);						
			preparedStmtV.setFloat(4,precioC);			
			preparedStmtV.setInt(5,cantidadP);
						
			preparedStmtV.execute();
															
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	private int getIdDetallesC(Connection con) {
		
		int id = 0;
		Statement st2;
		ResultSet datos2 = null;
		
		try {
			st2 = con.createStatement();
			datos2 = st2.executeQuery("select count(ID_Detalle) as Cantidad from DetallesCompra");
			
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
	
	private void actualizarProducto(int idP, int cantidadCompra,Connection con) {
		String insertTableSQLMarcaModelo = "update Producto set Cantidad = ? where ID_Producto = ?";
		
		PreparedStatement st = null;
		ResultSet datos = null;
		
		int cantidadActual = 0; 
		
		try {
			PreparedStatement preparedStmtV = con.prepareStatement(insertTableSQLMarcaModelo);
			
			st = con.prepareStatement("select distinct Cantidad from Producto where ID_Producto = ?");
		
			st.setInt(1,idP);
			
			datos = st.executeQuery();
			
			while(datos.next()) {
				
				cantidadActual = datos.getInt("Cantidad");
				
			}
			
			preparedStmtV.setInt(1,cantidadActual - cantidadCompra);			
			preparedStmtV.setInt(2,idP);
			
			preparedStmtV.execute();
									
		} catch (Exception e) {
			System.out.println(e.toString());
		}
						
	}
	

	
	
	private void clean() {
		txtNombreC.setText("");
		txtTelefonoC.setText("");
		txtEmailC.setText("");
		txtCreditoC.setText("");
		txtDeudaC.setText("");
		txtTotal.setText("");
		
	}
}
