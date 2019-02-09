

/*
 * Created on 17 avr. 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.awt.BorderLayout;
import java.awt.Choice;
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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author info
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Utilisateur {
	
	private static JLabel label1,label2,label3,label4;
	private static JTextField textfield1,textfield2,textfield3;
	private static JButton button1,button2;
	private static Frame uf,mof;
	private static Choice action;
	private static Connection con;
	private static Statement st;
	static String nu="",mp="";
	private static JPanel panel1,panel2,panel3,panel4;
	
//	***********************************************************************	
	
	public static void libererchamp(){
		textfield1.setText("");
		textfield2.setText("");
	}

//********************************************************************************	
	public static void ajouter(){
		if (textfield1.getText().equals("")||textfield2.getText().equals("")||textfield3.getText().equals("")) 
		{
			Listedoc.message( "Entrez le nom et le mot de passe svp ","Gestion d'utilisateurs");
		}
		else {	
			if (textfield2.getText().equals(textfield3.getText())){
				try {	
					String requete = "INSERT INTO util VALUES ('"+textfield1.getText()+"','"+textfield2.getText()+"')";
					Statement stmt = con.createStatement();
					int nbe = stmt.executeUpdate(requete);
					uf.dispose();
					Listedoc.message("Le compte a été "+action.getSelectedItem()," Gestion d'utilisateur ");
					stmt.close();
				}catch (SQLException e) {
					Listedoc.message( "Il ya un probléme dans la base dedonnée..","Gestion d'utilisateurs");
				 }
				
			}else {
				Listedoc.message( " Mot de passe invalide.. ","Gestion d'utilisateurs");
			}
		}

	}
	
//********************************************************************************	
	public static void supprimer(){
		if (textfield1.getText().equals("")||textfield2.getText().equals("")||textfield3.getText().equals("")) 
		{
			Listedoc.message( "Entrez le nom et le mot de passe svp ","Gestion d'utilisateurs");
		}
		else {	
			if (textfield2.getText().equals(textfield3.getText())){
				try {	
					String requete = "DELETE FROM util WHERE nomU='"+textfield1.getText()+"'AND mpass='"+textfield2.getText()+"'";
					Statement stmt = con.createStatement();
					int nbe= stmt.executeUpdate(requete);
					uf.dispose();
					Listedoc.message("Le compte a été "+action.getSelectedItem()," Gestion d'utilisateur ");
					stmt.close();
				}catch (SQLException e) {
					Listedoc.message( "Il ya un probléme dans la base dedonnée","Gestion d'utilisateurs");
				 }
				
			}else {
				Listedoc.message( " Mot de passe invalide  ","Gestion d'utilisateurs");
			}
		}

	}
	
//********************************************************************************
	public static void modifier(){
	  if (textfield1.getText().equals("")||textfield2.getText().equals("")||textfield3.getText().equals("")) 
		{
			Listedoc.message( "Entrez le nom et le mot de passe svp ","Gestion d'utilisateurs");
		}
	  else {	
			if (textfield2.getText().equals(textfield3.getText())){
				if(nu.equals("")&&mp.equals("")){
					nu=textfield1.getText();
					mp=textfield2.getText();
					Listedoc.message( " Remplir les chompes svp ","Gestion d'utilisateurs");
				}else{
					try {	
						String requete = "UPDATE util SET nomU ='"+textfield1.getText()+"',mpass ='"+textfield2.getText()+"'WHERE nomU='"+nu+"'AND mpass='"+mp+"'";
						Statement stmt = con.createStatement();
						int nbe= stmt.executeUpdate(requete);
						uf.dispose();
						Listedoc.message("Le compte a été "+action.getSelectedItem()," Gestion d'utilisateur ");
						stmt.close();
					}catch (SQLException e) {
						Listedoc.message( "Il ya un probléme dans la base dedonnée","Gestion d'utilisateurs");
					 }
				}
				
			}else {
				Listedoc.message( " Mot de passe invalide  ","Gestion d'utilisateurs");
			}
		}
	}
	
//********************************************************************************
	public static void mpoblier(){
		mof=new Frame(" Gestion d'utilisateur ");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (InstantiationException e) {e.printStackTrace();} 
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}
		
		mof.addWindowListener (new WindowAdapter( ) {
			public void windowClosing(WindowEvent e)
			{ 
			  mof.dispose();
			}
		});

		mof.setAlwaysOnTop(true);
		mof.setLayout(new BorderLayout());
		mof.setResizable(false);
		mof.setLocation(330,230);
//********************************************************************************	
		
		label1 = new JLabel(" Question :       ");
		label2 = new JLabel(" Répence : ");
		label3 = new JLabel("Entrer votre question et sa répence :");
		
		
//********************************************************************************	
								
		textfield1 = new JTextField(25);
		textfield2 = new JTextField(25);
		
//********************************************************************************	
	 	button1 = new JButton("OK");
		button1.setPreferredSize(new java.awt.Dimension(80,25));
		button1.addActionListener(new java.awt.event.ActionListener() {
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
            //*****************************************************************************************
				
				if (textfield1.getText().equals ("") || textfield2.equals("") ) {
					Listedoc.message( "Remplire les chomps svp "," Administrateur");
				}
				else {
					try {	
						String requet = "SELECT * FROM admin WHERE quest = '" + textfield1.getText() + "'AND rep='"+textfield2.getText()+"'";
						Statement st = con.createStatement ();		
						ResultSet rs = st.executeQuery (requet);
						rs.next();
						String quest =rs.getString ("quest");
						String rep =rs.getString ("rep");
						if(textfield1.getText().equals(quest)||textfield2.getText().equals(rep)){
							label1.setText("Utilisateur:");
							label2.setText("Mpasse:");
							textfield1.setText(rs.getString ("nomU"));
							textfield2.setText(rs.getString ("mpass"));
						}else{
							Listedoc.message ( "Question ou répence incorrect "," Administrateur");
							libererchamp();
						}
						rs.close();
					}catch (SQLException sqlex) {
						Listedoc.message ( "Question ou répence incorrect"," Administrateur");
						libererchamp();
					}
				}
				
				
			}
		});
		
		button2 = new JButton("  Annuler ");
		button2.setPreferredSize(new java.awt.Dimension(80,25));
		button2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mof.dispose();
			}
		});
//********************************************************************************	

		panel1 = new javax.swing.JPanel(new FlowLayout(5,15,10));
		panel1.setPreferredSize(new java.awt.Dimension(90,40));
		panel1.add(label1);
		panel1.add(label2);
		
		
//********************************************************************************	
		
		panel2 = new javax.swing.JPanel(new FlowLayout(5,5,5));
		panel2.setPreferredSize(new java.awt.Dimension(240,70));
		panel2.add(textfield1);
		panel2.add(textfield2);
		
		
//********************************************************************************	
				
		panel3 = new JPanel(new FlowLayout());
		panel3.setPreferredSize(new java.awt.Dimension(200,40));
		panel3.add(button1);
		panel3.add(button2);
		
//********************************************************************************	
	
		panel4 = new JPanel();
		panel4.setPreferredSize(new java.awt.Dimension(200,30));
		panel4.add(label3);
		
//********************************************************************************	
		
		mof.add(panel4,BorderLayout.NORTH);
		mof.add(panel1,BorderLayout.WEST);
		mof.add(panel2,BorderLayout.EAST);
		mof.add(panel3,BorderLayout.SOUTH);
		mof.pack();
		mof.setVisible(true);
	}
//********************************************************************************	
	public Utilisateur(){
		uf=new Frame(" Gestion d'utilisateur ");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (InstantiationException e) {e.printStackTrace();} 
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}
		
		uf.addWindowListener (new WindowAdapter( ) {
			public void windowClosing(WindowEvent e)
			{ 
			  uf.dispose();
			}
		});

		uf.setAlwaysOnTop(true);
		uf.setLayout(new BorderLayout());
		uf.setResizable(false);
		uf.setLocation(330,230);
		
//********************************************************************************	
		
		label1 = new JLabel("       Choisissez une Action :                                             ");
		label2 = new JLabel("Nom d'utilisateur :    ");
		label3 = new JLabel("Mot de passe :         ");
		label4 = new JLabel("Confirmé Mpasse :");
		
//********************************************************************************	
								
		textfield1= new JTextField(30);
		textfield2 = new JTextField(30);
		textfield3 = new JTextField(30); 
//********************************************************************************	
	 
		action=new Choice();
		action.addItem("Créer");
		action.add("Supprimer");
		action.add("Modifier");
		
//********************************************************************************	
			
		button1 = new JButton("OK");
		button1.setPreferredSize(new java.awt.Dimension(80,25));
		button1.addActionListener(new java.awt.event.ActionListener() {
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
            //*****************************************************************************************				
				if (action.getSelectedItem().equals("Créer")){
					ajouter();
				}else if(action.getSelectedItem().equals("Supprimer")){
					supprimer();
				}else{
					modifier();
				}
			}
		});
		
		button2 = new JButton("  Annuler ");
		button2.setPreferredSize(new java.awt.Dimension(80,25));
		button2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				uf.dispose();
			}
		});
//********************************************************************************	

		panel1 = new javax.swing.JPanel(new FlowLayout(5,15,10));
		panel1.setPreferredSize(new java.awt.Dimension(130,90));
		panel1.add(label2);
		panel1.add(label3);
		panel1.add(label4);
		
//********************************************************************************	
		
		panel2 = new javax.swing.JPanel(new FlowLayout(5,5,5));
		panel2.setPreferredSize(new java.awt.Dimension(280,100));
		panel2.add(textfield1);
		panel2.add(textfield2);
		panel2.add(textfield3);
		
//********************************************************************************	
				
		panel3 = new JPanel(new FlowLayout());
		panel3.setPreferredSize(new java.awt.Dimension(360,45));
		panel3.add(button1);
		panel3.add(button2);
		
//********************************************************************************	
	
		panel4 = new JPanel();
		panel4.setPreferredSize(new java.awt.Dimension(360,40));
		panel4.add(label1);
		panel4.add(action);
		
//********************************************************************************	
		
		uf.add(panel4,BorderLayout.NORTH);
		uf.add(panel1,BorderLayout.WEST);
		uf.add(panel2,BorderLayout.EAST);
		uf.add(panel3,BorderLayout.SOUTH);
		uf.pack();
		uf.setVisible(true);
		
	   }					
}	


