package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import java.io.File;
import java.awt.Color;
import javax.swing.border.EtchedBorder;



public class listFactura extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableFactura;
	private JButton btnDetallesFactura;	
	private JButton btnImprimir;
		
	
	private int idFactura;
			
	private ArrayList<String> producto = new ArrayList<String>();		
	private ArrayList<Float> precioVenta = new ArrayList<Float>();		
	private ArrayList<Float> precioComp = new ArrayList<Float>();		
	private float precioTotal;		
	private ArrayList<Integer> cantidadComp = new ArrayList<Integer>();
	//private java.sql.Timestamp fechaCreacion;
	private String fechaCreacion;
	TableRowSorter<DefaultTableModel> tr;
	private JButton btnPago;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					listFactura frame = new listFactura();
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
	public listFactura(Connection con) {
		setTitle("Lista de facturas");
		setBounds(100, 100, 1019, 609);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ImageIcon imagenIcon=new ImageIcon("imagenes"+File.separator+"factura.png");
		setIconImage(imagenIcon.getImage());
		setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Facturas", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel.setBounds(10, 11, 983, 548);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(10, 109, 963, 428);
		panel.add(panel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 963, 428);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				btnDetallesFactura.setEnabled(true);
				btnImprimir.setEnabled(true);
				btnPago.setEnabled(true);
			}
		});

		
		
		btnDetallesFactura = new JButton("Detalles de la factura");
		btnDetallesFactura.setBackground(Color.WHITE);
		btnDetallesFactura.setEnabled(false);
		btnDetallesFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = table.getSelectedRow();
				
				if(index != -1) {
					
					int idF= (int) table.getValueAt(index, 0);
					//System.out.println(table.getValueAt(index, 0));
					
					DestallesFactura dF = new DestallesFactura(idF,con);				
					dF.setVisible(true);
					
				}else {
					JOptionPane.showMessageDialog(null, "Selecione una factura", "Validaci?n", JOptionPane.WARNING_MESSAGE);
				}
				
				
			}
		});
		btnDetallesFactura.setBounds(10, 75, 172, 23);
		panel.add(btnDetallesFactura);
		
		btnImprimir = new JButton("Imprimir factura");
		btnImprimir.setBackground(Color.WHITE);
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				
				if(index != -1) {
					
					producto.clear();		
					precioVenta.clear();		
					precioComp.clear();		
					precioTotal = 0;		
					cantidadComp.clear();
					
					int idF= (int) table.getValueAt(index, 0);
					//System.out.println(table.getValueAt(index, 0));
					ImprimirFactura(idF,con);
					
					
				}else {
					JOptionPane.showMessageDialog(null, "Selecione una factura", "Validaci?n", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		btnImprimir.setEnabled(false);
		btnImprimir.setBounds(192, 75, 172, 23);
		panel.add(btnImprimir);
		
		btnPago = new JButton("Cambiar tipo de pago");
		btnPago.setBackground(Color.WHITE);
		btnPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = table.getSelectedRow();
				
				if(index != -1) {
					
					String[] options = {"Si", "No"};
					int seleccion = JOptionPane.showOptionDialog(null, "?Seguro que desea modificar el tipod de pago de la factura?", "Modificacion producto", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

					if(seleccion == 0) {
						int idF = (int) table.getValueAt(index, 0);
						
						String tipoP = (String) table.getValueAt(index, 1);
						
						modificarFactura(idF,tipoP,con);			
						tableFactura.setRowCount(0);
						mostrar(con);
					}
					
					
					
					
					
				}else {
					JOptionPane.showMessageDialog(null, "Selecione una factura", "Validaci?n", JOptionPane.WARNING_MESSAGE);
				}
				
				
				
			}
		});
		btnPago.setEnabled(false);
		btnPago.setBounds(374, 75, 179, 23);
		panel.add(btnPago);
		
		tableFactura= new DefaultTableModel() {
			
			Class[] types = { Integer.class, String.class, Calendar.class,String.class,String.class, String.class};
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
		tableFactura.setColumnIdentifiers(new Object[] {"ID","Tipo de Pago","Fecha de creacion","Nombre del Cliente","Email","Telefono"});
		Statement st;
		ResultSet datos = null;
		try {
			st = con.createStatement();
			datos = st.executeQuery("select ID_Factura,TipoDePago,Fecha_Creacion,Nombres,Apellidos,Email,Telefono from Factura join cliente on Factura.Cliente = Cliente.ID_Cliente order by Fecha_Creacion");
			
			while(datos.next()) {				
				tableFactura.addRow(new Object[]{datos.getInt("ID_Factura"),datos.getString("TipoDePago"),datos.getDate("Fecha_Creacion"),datos.getString("Nombres")+" "+datos.getString("Apellidos"),datos.getString("Email"),datos.getString("Telefono")});
			}
			table.setModel(tableFactura);
		}catch(Exception e) {
			System.out.println(e.toString());		
		}
		
		tr = new TableRowSorter<>(tableFactura);
				
		table.setRowSorter(tr);
		
		
	}
	
	private void ImprimirFactura(int idF, Connection con) {
		idFactura = idF;
		PreparedStatement st = null;
		ResultSet datos = null;
		
		try {
			//st = con.prepareStatement("select Nombre,PrecioVenta,CantidadComprado,Precio from DetallesCompra join Producto on DetallesCompra.Producto = Producto.ID_Producto where Factura = ?");
			st = con.prepareStatement("select Nombre,PrecioVenta,CantidadComprado,Precio,Fecha_Creacion from DetallesCompra join Producto on DetallesCompra.Producto = Producto.ID_Producto join Factura on DetallesCompra.Factura = Factura.ID_Factura where Factura = ?");
			st.setInt(1,idF);
			
			datos = st.executeQuery();
			
			while(datos.next()) {
				
				producto.add(datos.getString("Nombre"));
				precioVenta.add(datos.getFloat("PrecioVenta"));
				precioComp.add(datos.getFloat("Precio"));
				cantidadComp.add(datos.getInt("CantidadComprado"));				
				
				precioTotal += datos.getFloat("Precio");
				
				fechaCreacion = datos.getString("Fecha_Creacion");
				
			}						
		} catch (Exception e) {
			System.out.println(e.toString());
		}

        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new BillPrintable(),getPageFormat(pj));
        //pj.setPrintable(new BillPrintable());
       
        
        if (pj.printDialog()) {
            try {
                pj.print();
            } catch (PrinterException ex) {
            	System.out.println(ex.toString());
            }
        }else{
        	JOptionPane.showMessageDialog(this, "La impresion se cancelo");
        }
		
	}
	
	private void modificarFactura(int idF,String tipoP,Connection con) {
		String insertTableSQLMarcaModelo1 = "update Factura set TipoDePago = ? where ID_Factura = ?";
		String insertTableSQLMarcaModelo2 = "update Cliente set Credito = ?,Deuda = ? where ID_Cliente = ?";
		
		PreparedStatement st1 = null;
		ResultSet datos1 = null;
		
		PreparedStatement st2 = null;
		ResultSet datos2 = null;
		
				
		int idC = 0;
		
		float deudaAct = 0;
		float creditoAct = 0;
		
		try {
			st1 = con.prepareStatement("select Nombre,PrecioVenta,CantidadComprado,Precio,Fecha_Creacion,Cliente from DetallesCompra join Producto on DetallesCompra.Producto = Producto.ID_Producto join Factura on DetallesCompra.Factura = Factura.ID_Factura where Factura = ?");
			st1.setInt(1,idF);
			
			datos1 = st1.executeQuery();
			
			while(datos1.next()) {
				precioTotal += datos1.getFloat("Precio");
				idC = datos1.getInt("Cliente");				
			}
			
			st2 = con.prepareStatement("select * from cliente where ID_Cliente = ?");
			st2.setInt(1,idC);
			
			datos2 = st2.executeQuery();
			
			while(datos2.next()) {
				deudaAct = datos2.getFloat("Deuda");
				creditoAct = datos2.getFloat("Credito");
				
			}
			
			PreparedStatement preparedStmtV1 = con.prepareStatement(insertTableSQLMarcaModelo1);
			PreparedStatement preparedStmtV2 = con.prepareStatement(insertTableSQLMarcaModelo2);
			
			if(tipoP.equals("Contado")) {
				String credito = "Cr?dito";
				
				preparedStmtV1.setString(1,credito);
				preparedStmtV1.setInt(2,idF);				
				preparedStmtV1.execute();
				
				preparedStmtV2.setFloat(1, creditoAct-precioTotal);
				preparedStmtV2.setFloat(2, deudaAct+precioTotal);
				preparedStmtV2.setInt(3,idC);
				preparedStmtV2.execute();
				
			}else if (tipoP.equals("Cr?dito")) {
				String contado = "Contado";
						
				preparedStmtV1.setString(1,contado);
				preparedStmtV1.setInt(2,idF);				
				preparedStmtV1.execute();
				
				preparedStmtV2.setFloat(1, creditoAct+precioTotal);
				preparedStmtV2.setFloat(2, deudaAct-precioTotal);
				preparedStmtV2.setInt(3,idC);
				preparedStmtV2.execute();
				
			}
					
				
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
				
	}
	
	
	
	
    public PageFormat getPageFormat(PrinterJob pj) {
    
        //Double bHeight=0.0;
    	
    	PageFormat pf = pj.defaultPage();
	    Paper paper = pf.getPaper();    
	
	    double bodyHeight = Double.valueOf(producto.size());
	    double headerHeight = 5.0;                  
	    double footerHeight = 5.0;        
	    double width = cm_to_pp(8); 
	    double height = cm_to_pp(headerHeight+bodyHeight+footerHeight); 
	    paper.setSize(width, height);
	    paper.setImageableArea(0,10,width,height - cm_to_pp(1));  
	            
	    pf.setOrientation(PageFormat.PORTRAIT);  
	    pf.setPaper(paper);    
	
	    return pf;
    }
    
    
    protected static double cm_to_pp(double cm) {            
	        return toPPI(cm * 0.393600787);            
    }
 
    protected static double toPPI(double inch) {            
	        return inch * 72d;            
    }
	
		
    
    public class BillPrintable implements Printable {
    	public int print(Graphics graphics, PageFormat pageFormat,int pageIndex) throws PrinterException {
    		
    		
    		int result = NO_SUCH_PAGE;
    		if (pageIndex == 0) {
    			Graphics2D g2d = (Graphics2D) graphics;                    
    			double width = pageFormat.getImageableWidth();                               
    			g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 
    			//  FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
    			try{
    				int y=20;
		            int yShift = 10;
		            int headerRectHeight=15;
		            
		            g2d.setFont(new Font("Monospaced",Font.PLAIN,9));		        
		            g2d.drawString("-------------------------------------",12,y);y+=yShift;
		            g2d.drawString("              Factura#"+idFactura+"              ",12,y);y+=yShift;

		            g2d.drawString("-------------------------------------",12,y);y+=headerRectHeight;
	
		            g2d.drawString(" Productos                 Valor   ",10,y);y+=yShift;
		            g2d.drawString("-------------------------------------",10,y);y+=headerRectHeight;
		           
		            for (int i = 0; i < producto.size(); i++) {
		            	g2d.drawString(" "+producto.get(i)+"                            ",10,y);y+=yShift;
		            	g2d.drawString("      "+cantidadComp.get(i)+" * "+precioVenta.get(i),10,y); g2d.drawString(precioComp.get(i)+"",160,y);y+=yShift;
						
					}
		            		            		          
		            g2d.drawString("-------------------------------------",10,y);y+=yShift;
		            g2d.drawString(" Total:                     "+precioTotal+"   ",10,y);y+=yShift;		            
		            g2d.drawString("-------------------------------------",10,y);y+=yShift;
		            g2d.drawString(" Fecha:       "+fechaCreacion+"   ",10,y);y+=yShift;		            		  
		            g2d.drawString("*************************************",10,y);y+=yShift;
		            g2d.drawString("       Gracias por su compra            ",10,y);y+=yShift;
		            g2d.drawString("*************************************",10,y);y+=yShift;

		            
    			}
    			catch(Exception e){
    				e.printStackTrace();	
    			}
    			result = PAGE_EXISTS;
    			}    
		          return result;    
		      }
    	}
    
    
    
	
	
}
