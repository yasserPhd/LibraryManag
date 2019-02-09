

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * Created on 18 avr. 2007
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
public class Mdpasse extends Frame{
	private JLabel lab1,lab2;
	static JTextField textfield1;
	static JPasswordField textfield2;
	private JButton b1,b2,b3;
	private JPanel panel1,panel2,panel3,panel0;
	private static Connection con;		
	protected static String util,pass;
	private static boolean verify = false;
//***********************************************************************	
	public static void libererchamp(){
		textfield1.setText("");
		textfield2.setText("");
	}
//***********************************************************************	
	public static boolean confirmer(String table){
		
		String mpasse = new String (textfield2.getPassword());
		if (textfield1.getText().equals ("") || mpasse.equals("") ) {
			Listedoc.message( "Nom ou mot de passe incorrect","Authentification");
		}
		else {
			verify = false;		
 			try {	
					String requet = "SELECT * FROM "+table+" WHERE nomU = '" + textfield1.getText() + "'AND mpass='"+mpasse+"'";
					Statement st = con.createStatement ();		
					ResultSet rs = st.executeQuery (requet);		
				    rs.next();					
					util= rs.getString ("nomU");		
					pass = rs.getString ("mpass");	
					rs.close();
					if (textfield1.getText().equals (util) && mpasse.equals (pass)) {
					verify = true;
					}else {
					verify = false;
					Listedoc.message ( "Nom ou mot de passe incorrect ","Authentification");
					libererchamp();
				}
			}
			catch (SQLException sqlex) {
				if (verify == false) {
					Listedoc.message ( "Nom ou mot de passe incorrect","Authentification");
					libererchamp();
				}
			}
		}

		return verify;
	}
	
	public Mdpasse(final String table1){
		
		super("Authentification");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (InstantiationException e) {e.printStackTrace();} 
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}
		
		setLayout(new BorderLayout());
		setVisible(true);
		setAlwaysOnTop(true);
		setResizable(false);
		setLocation(380,300);
		addWindowListener (new WindowAdapter( ) {
			public void windowClosing(WindowEvent e)
			{ 
			  dispose();
			}
		});
					
		b1=new JButton("OK");
		b1.setPreferredSize(new java.awt.Dimension(80,24));
		b1.addActionListener(new java.awt.event.ActionListener() {
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
				
				confirmer(table1);
				if(verify==true){
					if(table1.equals("util")){
				      Listedoc liste=new Listedoc();
				      verify=false;
				      dispose();
					}else{
					  Utilisateur ut=new Utilisateur();
					  verify=false;
					  dispose();
					}
				}
			}
		});
		
		b2=new JButton("Annuler");
		b2.setPreferredSize(new java.awt.Dimension(80,24));
		b2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dispose();
			}
		});

		lab1 = new JLabel(" Utilisateur  :");
		lab2 = new JLabel(" Mot de passe  :      ");
		
		textfield1=new JTextField();
		textfield1.setPreferredSize(new java.awt.Dimension(170,22));
		textfield2=new JPasswordField();
		textfield2.setPreferredSize(new java.awt.Dimension(170,22));	
		
		panel0=new JPanel(new FlowLayout(10,10,11));
		panel0.setPreferredSize(new java.awt.Dimension(120,70));
		panel0.add(lab1);
		panel0.add(lab2);
		
		panel1=new JPanel(new FlowLayout());
		panel1.setPreferredSize(new java.awt.Dimension(185,70));
		panel1.add(textfield1);
		panel1.add(textfield2);
		
		panel2=new JPanel(new FlowLayout());
		panel2.setPreferredSize(new java.awt.Dimension(270,40));
		panel2.add(b1);
		panel2.add(b2);
		
		panel3 = new JPanel();
		panel3.setPreferredSize(new java.awt.Dimension(270,10));
				
		add(panel3,BorderLayout.NORTH);
		add(panel1,BorderLayout.EAST);
		add(panel0,BorderLayout.WEST);
		add(panel2,BorderLayout.SOUTH);
		pack();
		
		
	}

	public static void main(String[] args) {
		Mdpasse md= new Mdpasse("util");
	}
}
