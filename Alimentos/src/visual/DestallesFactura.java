package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import visual.listFactura.BillPrintable;

import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.print.Printable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class DestallesFactura extends JFrame {

	private JPanel contentPane;
	private JTextArea txtFactura;
	private int idFactura;
	private ArrayList<String> productoP = new ArrayList<String>();
	
	
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DestallesFactura frame = new DestallesFactura();
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
	public DestallesFactura(int idF,Connection con) {
		idFactura = idF;
		setBounds(100, 100, 434, 557);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Detalles de la factura", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 398, 447);
		contentPane.add(panel);
		panel.setLayout(null);
				
		txtFactura = new JTextArea();
		txtFactura.setEditable(false);
		txtFactura.setBounds(10, 22, 378, 414);
		panel.add(txtFactura); 

		
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(10, 469, 398, 38);
		contentPane.add(buttonPane);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		//System.out.println(idF);
		
		formatoFactura(idF,con);
	}
		
	private void formatoFactura(int idF, Connection con) {
		
		PreparedStatement st = null;
		ResultSet datos = null;
		
		String formato = null;
		
		String producto = null;
		
		float precioVenta = 0;
		
		float precioComp = 0;
		
		float precioTotal = 0;
		
		int cantidadComp = 0;
		
		String fechaCreacion = null;
				
		formato = "\t   Factura #"+ idF + "\n--------------------------------------------------------\n";
		txtFactura.append(formato);
		
		try {
			//st = con.prepareStatement("select Nombre,PrecioVenta,CantidadComprado,Precio from DetallesCompra join Producto on DetallesCompra.Producto = Producto.ID_Producto where Factura = ?");
			st = con.prepareStatement(" select Nombre,PrecioVenta,CantidadComprado,Precio,Fecha_Creacion from DetallesCompra join Producto on DetallesCompra.Producto = Producto.ID_Producto join Factura on DetallesCompra.Factura = Factura.ID_Factura where Factura = ?");
			st.setInt(1,idF);						
			
			datos = st.executeQuery();
			
			
			
			while(datos.next()) {

				producto = datos.getString("Nombre");
				precioVenta = datos.getFloat("PrecioVenta");
				cantidadComp = datos.getInt("CantidadComprado");
				precioComp = datos.getFloat("Precio");
				precioTotal += datos.getInt("Precio");
				
				fechaCreacion = datos.getString("Fecha_Creacion");
				
				txtFactura.append(producto+"\n");
				txtFactura.append(precioVenta+" x "+cantidadComp+"\t= "+precioComp+"\n");
								
			}
			//System.out.println(producto);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		txtFactura.append("\n--------------------------------------------------------\n");
		
		txtFactura.append("Total\t   "+precioTotal);		
		txtFactura.append("\n--------------------------------------------------------\n");
		txtFactura.append("Fecha\t   "+fechaCreacion);
	}
	
	
	
	
}
