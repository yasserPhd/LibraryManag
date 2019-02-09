

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
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

public class Listedoc extends javax.swing.JPanel {
	private static JTable Table;
	private JMenuBar bar;
	private JMenu mnfile;
	private JScrollPane ScrollPane;
	private JPanel Panel1,Panel2,Panel3;
	private static JPanel mpanel1,mpanel2,mpanel3,mpanel0;
	private static JLabel lab1;
	private static JButton Button1,Button2,Button3,Button4,Button5,Button6,Button7,b1,b2;
	static int rowCnt = 0;
	private static int selectedRow;
	private Connection con;	
	private ResultSet rs;

//**************************************************************************************
public static void message(String message,String message1){
	final Frame fm =new Frame(message1);
	try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException e) {e.printStackTrace();}
	catch (InstantiationException e) {e.printStackTrace();} 
	catch (IllegalAccessException e) {e.printStackTrace();} 
	catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}
	
	fm.setLayout(new BorderLayout());
	fm.setVisible(true);
	fm.setAlwaysOnTop(true);
	fm.setResizable(false);
	fm.setLocation(395,320);
	fm.addWindowListener (new WindowAdapter( ) {
		public void windowClosing(WindowEvent e)
		{ 
			fm.dispose();
		}
	});
	
	b1=new JButton("OK");
	b1.setPreferredSize(new java.awt.Dimension(80,24));
	b1.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			fm.dispose();
		}
	});
	
	lab1 = new JLabel(message);
		
	mpanel0=new JPanel(new FlowLayout());
	mpanel0.setPreferredSize(new java.awt.Dimension(280,40));
	mpanel0.add(b1);
	
	mpanel1=new JPanel(new FlowLayout());
	mpanel1.setPreferredSize(new java.awt.Dimension(280,30));
	mpanel1.add(lab1);
	
	fm.add(mpanel1,BorderLayout.NORTH);
	fm.add(mpanel0,BorderLayout.SOUTH);
	fm.pack();
	
}
//**************************************************************************************
public void afficherliste(){
	ResultSet résultats = null;
	String requet="SELECT * FROM doc";
	try {
		Statement stmt = con.createStatement();
		résultats = stmt.executeQuery(requet);
		boolean encore = résultats.next();
		effacer();
		while (encore) {
			int nc=résultats.getInt(6);
			String dis="Disponible   ("+nc+")";
			if(nc==0){
				dis="Indisponible";
			}
		   Listedoc.setdoc(résultats.getString(1),résultats.getString(2),résultats.getString(3),résultats.getString(4),résultats.getString(5), dis);
		   encore = résultats.next();
		}
		résultats.close();
	}catch (SQLException e1) {
		      Listedoc.message("Il y a un probléme dans la base de donnée","Recherche");
	}
        
}
//**************************************************************************************
public Listedoc() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (InstantiationException e) {e.printStackTrace();} 
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}
      //*****************************************************************************		
		final Frame Frame = new Frame(" Gestion du bibliothéque");
		Table = new javax.swing.JTable(new AbstractTable());
		getSelectedRow();
    //******************************Table Column size*******************************
		javax.swing.table.TableColumn column = null;
		for(int i = 0; i < 4; i++) {
			column = Table.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(1);
			} else if(i == 1) {
				column.setPreferredWidth(200);
			} else if(i == 2) {
				column.setPreferredWidth(150);
			} else if(i==3){
				column.setPreferredWidth(80);
			}else {
				column.setPreferredWidth(30);
			}
		}
		ScrollPane = new JScrollPane(Table);
		Panel1 = new JPanel(new BorderLayout());
		Panel1.add(ScrollPane,BorderLayout.CENTER);
   //********************************************************************************	
		MenuBar mb = new MenuBar();
		Frame.setMenuBar(mb);
		
		Menu fichier = new Menu("Fichier");
		MenuItem rech=new MenuItem(" Recherche ");
		rech.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Recherche r = new Recherche();
			}
		});
		MenuItem listdoc=new MenuItem(" Liste des documents ");
		listdoc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
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
				effacer();
				afficherliste();
				}
		});
		MenuItem Quitter=new MenuItem(" Quitter ");
		Quitter.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Frame.dispose();
			}
		});
		fichier.add(rech);
		fichier.add(listdoc);
		fichier.add(Quitter);
		mb.add(fichier);
  //*************************************************************************************		
		Menu gest = new Menu("Gestion");
		MenuItem util=new MenuItem(" D'utilisateurs ");
		util.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Mdpasse md= new Mdpasse("admin");
			}
		});
		MenuItem doc=new MenuItem(" Des documents ");
		doc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Gdocument gd=new Gdocument();
			}
		});
		MenuItem mpo=new MenuItem(" Mot de passe oublier ");
		mpo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Utilisateur.mpoblier();
			}
		});
		gest.add(util);
		gest.add(doc);
		gest.add(mpo);
		mb.add(gest);
	//****************************************************************************************	
		Menu pret = new Menu("Gestion des prets");
		MenuItem pre = new MenuItem(" Prendre ");
		pre.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(Table.getValueAt(getSelectedRow(), 5).equals("Indisponible")){
					message("Vous ne pouvez pas prendre ce document ","Gestion des pret");
				}else{
				 Pret p=new Pret(Table.getValueAt(getSelectedRow(), 0),Table.getValueAt(getSelectedRow(), 1),Table.getValueAt(getSelectedRow(), 2));
				}
			}
		});
		MenuItem recu=new MenuItem(" Récupérer ");
		recu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Recuperation rec =new Recuperation();
			}
		});
		MenuItem listpre = new MenuItem(" Liste des prets ");
		listpre.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Pret.listepret("general");
			}
		});
		MenuItem lpretar = new MenuItem(" Liste des retard ");
		lpretar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Pret.listepret("retard");
			}
		});
		pret.add(pre);
		pret.add(recu);
		pret.add(listpre);
		pret.add(lpretar);
		mb.add(pret);
		
		Menu ap = new Menu("? ");
		MenuItem app=new MenuItem(" A propo de biblio ");
		app.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Listedoc.message("Copie right LMD_GL Juin 2007", "A propo");
			}
		});
		ap.add(app);
		mb.add(ap);
    //******************************************************************************************		
		Button1 = new JButton();
		Button1.setIcon(new ImageIcon ("images/pret.png"));
		Button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Table.getValueAt(getSelectedRow(), 5).equals("Indisponible")){
					message("Vous ne pouvez pas prendre ce document ","Gestion des pret");
				}else{
				Pret p=new Pret(Table.getValueAt(getSelectedRow(), 0),Table.getValueAt(getSelectedRow(), 1),Table.getValueAt(getSelectedRow(), 2));
				}
			}
		});
		
		Button2 = new JButton();
		Button2.setIcon(new ImageIcon ("images/Exit.gif"));
		Button2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				System.exit(0);
				Frame.dispose();
			}
		});
   //******************************************************************************************		
		Button3 = new JButton();		
		Button3.setIcon(new ImageIcon ("images/Recherche.png"));
		Button3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Recherche r = new Recherche();
			}
		});
   //******************************************************************************************		
		Button4 = new JButton();
		Button4.setIcon(new ImageIcon ("images/Recuperation.png"));
		Button4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Recuperation rec =new Recuperation();
			}
		});
  //******************************************************************************************		
		Button5 = new JButton();
		Button5.setIcon(new ImageIcon ("images/Mes_Documents.png"));
		Button5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
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
				effacer();
				afficherliste();
			}
		});
  //******************************************************************************************		
		Button6 = new JButton();
		Button6.setIcon(new ImageIcon ("images/Gestion_doc.gif"));
		Button6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Gdocument gd=new Gdocument();
			}
		});
  //******************************************************************************************		
		Button7 = new JButton();
		Button7.setIcon(new ImageIcon ("images/Utilisateur.gif"));
		Button7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Mdpasse md= new Mdpasse("admin");
			}
		});
  //******************************************************************************************		
		Panel2 = new JPanel(new FlowLayout());
		Panel2.add(Button1);
		Panel2.add(Button2);
  //******************************************************************
		Panel3 = new JPanel(new FlowLayout());
		Panel3.setPreferredSize(new java.awt.Dimension(750,70));
		Panel3.add(Button3);
		Panel3.add(Button4);
		Panel3.add(Button5);
		Panel3.add(Button6);
    	Panel3.add(Button7);
  //********************************************************************		
		Panel1.add(Panel2, java.awt.BorderLayout.SOUTH);
		Panel1.add(Panel3, java.awt.BorderLayout.NORTH);
		Panel1.setPreferredSize(new java.awt.Dimension(750,500));
  //********************************************************************	
		Frame.addWindowListener (new WindowAdapter( ) {
			public void windowClosing(WindowEvent e)
			{ 
				System.exit(0);
			    Frame.dispose();
			}
		});
		Frame.setResizable(false);
		Frame.setLocation(130,100);
		Frame.add(Panel1);
		Frame.pack();
		Frame.setVisible(true);
		
}
//*************************************************************************************************************
	public static void setdoc(String n,String titre,String autheur,String categorie,String type,String etat) {
		Table.setValueAt(n, rowCnt, 0);
		Table.setValueAt(titre, rowCnt, 1);
		Table.setValueAt(autheur, rowCnt, 2);
		Table.setValueAt(categorie, rowCnt, 3);
		Table.setValueAt(type, rowCnt,4);
		Table.setValueAt(etat, rowCnt,5);
		rowCnt++;
	}
//********************************************************************
	public static void supdoc() {
		for(int i = getSelectedRow(); i < rowCnt; i++) {
			Table.setValueAt(Table.getValueAt(i + 1, 0), i, 0);
			Table.setValueAt(Table.getValueAt(i + 1, 1), i, 1);
			Table.setValueAt(Table.getValueAt(i + 1, 2), i, 2);
			Table.setValueAt(Table.getValueAt(i + 1, 3), i, 3);
			Table.setValueAt(Table.getValueAt(i + 1, 4), i, 4);
			Table.setValueAt(Table.getValueAt(i + 1, 5), i, 5);
		}
		rowCnt--;
	}
//**********************************************************************		
	public static void getdoc() {
		Table.getValueAt(getSelectedRow(), 0);
		Table.getValueAt(getSelectedRow(), 1);
		Table.getValueAt(getSelectedRow(), 2);
		Table.getValueAt(getSelectedRow(), 3);
		Table.getValueAt(getSelectedRow(), 4);
	}
//************************************************************************
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
//**************************************************************************************
	public static void effacer(){
		
		if(rowCnt!=0){
			for(int i=rowCnt;i>0;i--){
				supdoc();
			}
		}
	}
//*************************************************************************
	class AbstractTable extends AbstractTableModel {
		private String[] columnNames = { " N°", "Titre", "Autheur", "Catégorie","Type","Etat"};
		private Object[][] data = new Object[100][6];

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
//	***********************************************************************		

	public static void main(String args[]) {
		
		Listedoc liste=new Listedoc();
		
		
	}
}