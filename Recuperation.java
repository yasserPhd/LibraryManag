

/*
 * Created on 16 avr. 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Recuperation {
	private JLabel lab1;
	static JTextField tf1;
	private JButton b1,b2;
	private static Frame rf;
	private static Connection con;
	private JPanel panel1,panel2,panel3;
	
//*******************************************************
	public static void vospret(){
		
		if (tf1.getText().equals("")) 
		{
			Listedoc.message( "Entrez le numéro de preneur  ","Gestion des prets");
		}
		else {	
			
			ResultSet résultats = null;
			String requet="SELECT * FROM pret,doc WHERE pret.num=doc.num AND pret.numE ="+tf1.getText();
			try {
				Statement stmt = con.createStatement();
				résultats = stmt.executeQuery(requet);
				boolean encore = résultats.next();
				if(encore){
					rf.dispose();
					Listepret liste=new Listepret(" Liste des prets");
					Listepret.effacer();
				    while (encore) {
					    Listepret.setdoc(résultats.getInt(2)+"",résultats.getString(3)+"  "+résultats.getString(4),résultats.getString(1),résultats.getString(8) ,résultats.getString(9),DateFormat.getDateInstance().format(résultats.getDate(5)),""+DateFormat.getDateInstance().format(résultats.getDate(6)));
					    encore = résultats.next();
				    }
				}else{
					 Listedoc.message("Vous n'avez aucun pret ","Récupération");
					 rf.dispose();
				}
				résultats.close();
			} catch (SQLException sql) {
				 Listedoc.message("Il y a un probléme dans la base de donnée","Récupération");
			}
	     }
	}
	public Recuperation(){
		 rf= new Frame ("Récupération ");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (InstantiationException e) {e.printStackTrace();} 
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}
		
		rf.setLayout(new BorderLayout());
		rf.setVisible(true);
		rf.setAlwaysOnTop(true);
		rf.setResizable(false);
		rf.setLocation(380,300);
		rf.addWindowListener (new WindowAdapter( ) {
			public void windowClosing(WindowEvent e)
			{ 
			 rf.dispose();
			}
		});
		
		lab1 = new JLabel(" Numéro de preneur :      ");
		
		tf1=new JTextField(15);	
		tf1.addKeyListener(new KeyListener () {
			public void keyTyped(KeyEvent arg0) {
				int k =arg0.getKeyChar();
				if((k>47 && k<58)||k==8 ){}else{
					arg0.setKeyChar((char)KeyEvent.VK_CLEAR);
				}
			}			 
			public void keyPressed(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {}
		});
		
		b1=new JButton("Annuler");
		b1.setPreferredSize(new java.awt.Dimension(80,24));
		b1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				rf.dispose();
			}
		});
		
		b2=new JButton("OK");
		b2.setPreferredSize(new java.awt.Dimension(80,24));
		b2.addActionListener(new java.awt.event.ActionListener() {
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
				vospret();
				tf1.setText("");
			}
		});
		
		panel1=new JPanel(new FlowLayout(10,10,11));
		panel1.setPreferredSize(new java.awt.Dimension(290,50));
		panel1.add(lab1);
		panel1.add(tf1);
		
		
		
		
		panel2=new JPanel(new FlowLayout());
		panel2.setPreferredSize(new java.awt.Dimension(290,40));
		panel2.add(b2);
		panel2.add(b1);
		
		panel3 = new JPanel();
		panel3.setPreferredSize(new java.awt.Dimension(290,10));
		
		rf.add(panel1,BorderLayout.CENTER);
		rf.add(panel2,BorderLayout.SOUTH);
		rf.add(panel3,BorderLayout.NORTH);
		
		rf.pack();
		
	}
}
