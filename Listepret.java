/*
 * Created on 23 avr. 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */



/**
 * @author info
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 * @author Noel Jarencio
 *
 * Description: Address book program.
 */

public class Listepret extends javax.swing.JPanel {
	private static JTable Table;
	private JScrollPane ScrollPane;
	private JPanel Panel1,Panel2,Panel3;
	private static JLabel label0;
	private static JButton Button1,Button2;
	private Choice action;
	private static int rowCnt = 0;
	private static int selectedRow;
	private static Connection con;



public Listepret(String titre) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (InstantiationException e) {e.printStackTrace();} 
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}
//************************************************************************************		
		final Frame Frame = new Frame(titre);
		Frame.setAlwaysOnTop(true);
		Table = new javax.swing.JTable(new AbstractTable());
		getSelectedRow();
//******************************Table Column size*********************************************************
		javax.swing.table.TableColumn column = null;
		for(int i = 0; i < 6; i++) {
			column = Table.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(20);
			} else if(i == 1) {
				column.setPreferredWidth(120);
			} else if(i == 2) {
				column.setPreferredWidth(25);
			} else if(i==3){
				column.setPreferredWidth(120);
			}else if(i==4) {
				column.setPreferredWidth(120);
			}else if(i==5) {
				column.setPreferredWidth(70);
			}else if(i==6) {
				column.setPreferredWidth(40);
			}	
		}
		ScrollPane = new JScrollPane(Table);
		Panel1 = new JPanel(new BorderLayout());
		Panel1.add(ScrollPane,BorderLayout.CENTER);
//******************************************************************************************		
		Button1 = new JButton();
		Button1.setIcon(new ImageIcon ("images/supprimer.PNG"));
		Button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
					String loc = "jdbc:odbc:bibliotheque";
					con = DriverManager.getConnection (loc,"Administrateur","1987");
				}
				catch (ClassNotFoundException cnf)  {
					Listedoc.message ("Driver non chargé...","Base de donnée");
				}
				catch (SQLException sqlex) {
					Listedoc.message ( "Incapable de connecter à la base de données...","Base de donnée");
		 		}
				if(action.getSelectedItem().equals("Supprimer")){
					supprimer();
					Listedoc.message("Le document a été récupéré . "," Gestion des prets ");
				}else{
					if(rowCnt!=0){
					  for(int i=rowCnt;i>0;i--){
						supprimer();
						}
					 Listedoc.message("Le documents ont étés récupérés . "," Gestion des prets ");
					  
					}
				}
			}
		});
		
		Button2 = new JButton();
		Button2.setIcon(new ImageIcon ("images/Exit.gif"));
		Button2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Frame.dispose();
			}
		});
		
		label0 = new JLabel("                                                                           " +
				"                                                                                       ");
		
		action=new Choice();
		action.add("Supprimer");
		action.add("Supprimer tout");
//******************************************************************************************		
		Panel2 = new JPanel(new FlowLayout());
		Panel2.add(Button1);
		Panel2.add(Button2);
//******************************************************************
		
		Panel3 = new JPanel(new FlowLayout());
		Panel3.setPreferredSize(new java.awt.Dimension(750,35));
		Panel3.add(label0);
		Panel3.add(action);
		
//******************************************************************************************		
		Panel1.add(Panel2, java.awt.BorderLayout.SOUTH);
		Panel1.add(Panel3, java.awt.BorderLayout.NORTH);
		Panel1.setPreferredSize(new java.awt.Dimension(750,520));
//******************************************************************************************		
		Frame.addWindowListener (new WindowAdapter( ) {
			public void windowClosing(WindowEvent e)
			{ 
				Frame.dispose();
			}
		});
		Frame.setResizable(false);
		Frame.setLocation(130,100);
		Frame.add(Panel1);
		Frame.pack();
		Frame.setVisible(true);
	}
//******************************************************************************

  public void supprimer(){
	  try {	
		String requete = "DELETE FROM pret WHERE num ="+Table.getValueAt(getSelectedRow(), 2)+" AND numE="+Table.getValueAt(getSelectedRow(), 0);
		Statement stmt = con.createStatement();
		int nbe= stmt.executeUpdate(requete);
		stmt.executeUpdate("UPDATE doc SET ncop= ncop+1  WHERE num = "+Table.getValueAt(getSelectedRow(), 2));
		stmt.close();
	  }catch (SQLException er) {
		er.printStackTrace();
		Listedoc.message( "Il ya un probléme dans la base de donnée...","Gestion des prets");
	   }
    supdoc();	
}
//******************************************************************************

  public static void effacer(){
	
	  if(rowCnt!=0){
		for(int i=rowCnt;i>0;i--){
			supdoc();
	  }
 	}
 }
//******************************************************************************
	public static void setdoc(String n,String nom,String nl,String titre,String auteur,String dd,String df) {
		Table.setValueAt(n, rowCnt, 0);
		Table.setValueAt(nom, rowCnt, 1);
		Table.setValueAt(nl, rowCnt, 2);
		Table.setValueAt(titre, rowCnt, 3);
		Table.setValueAt(auteur, rowCnt,4);
		Table.setValueAt(dd, rowCnt,5);
		Table.setValueAt(df, rowCnt,6);
		rowCnt++;
	}

//******************************************************************************
	public static void supdoc() {
		for(int i = getSelectedRow(); i < rowCnt; i++) {
			Table.setValueAt(Table.getValueAt(i + 1, 0), i, 0);
			Table.setValueAt(Table.getValueAt(i + 1, 1), i, 1);
			Table.setValueAt(Table.getValueAt(i + 1, 2), i, 2);
			Table.setValueAt(Table.getValueAt(i + 1, 3), i, 3);
			Table.setValueAt(Table.getValueAt(i + 1, 4), i, 4);
			Table.setValueAt(Table.getValueAt(i + 1, 5), i, 5);
			Table.setValueAt(Table.getValueAt(i + 1, 6), i, 6);
		}
		rowCnt--;
	}
//*****************************************************************************************		
	public static void getdoc() {
		Table.getValueAt(getSelectedRow(), 0);
		Table.getValueAt(getSelectedRow(), 1);
		Table.getValueAt(getSelectedRow(), 2);
		Table.getValueAt(getSelectedRow(), 3);
		Table.getValueAt(getSelectedRow(), 4);
		Table.getValueAt(getSelectedRow(), 5);
		Table.getValueAt(getSelectedRow(), 6);
	}
//******************************************************************************
	public static int getSelectedRow() {
		Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel rowSel = Table.getSelectionModel();
		rowSel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) return;
				ListSelectionModel sel = (ListSelectionModel)e.getSource();
				if (!sel.isSelectionEmpty()) {
					selectedRow = sel.getMinSelectionIndex();
				}
			}
		});
		return selectedRow;
	}
//********************************************************************************************
	class AbstractTable extends AbstractTableModel {
		private String[] columnNames = { " N°", "Nom d'étudiant", "Doc N°", "Titre","Auteur","Pret le :","Rendre le"};
		private Object[][] data = new Object[100][7];

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}
//******************************************************************************************		
	
}
