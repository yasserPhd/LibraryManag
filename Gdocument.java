

import java.awt.BorderLayout;
import java.awt.Choice;
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
public class Gdocument {
	private JLabel label0,label1,label2,label3,label4,label5,label6;
	private static JTextField textfield1,textfield2,textfield3,textfield4,textfield5,textfield6;
	private JButton button1,button2,button3;
	private static Choice action;
	private static String requet="";
	private static Connection con;
	private static Statement st;
	private static Frame gf;
	private JPanel panel1,panel2,panel3,panel4,panel5;
	
	public static void clear(){
		
		textfield1.setText("");
		textfield2.setText("");
		textfield3.setText("");
		textfield4.setText("");
		textfield5.setText("");
		textfield6.setText("");
		
	}
	public static void ajouter(){
		if (textfield1.getText().equals("")||textfield2.getText().equals("")||textfield3.getText().equals("")||textfield4.getText().equals("")||textfield5.getText().equals("")||textfield6.getText().equals("")) 
		{
			Listedoc.message( "Entrez les information du document svp "," Gestion des documents");
		}
		else {	
			try {	
				String requete = "INSERT INTO doc VALUES ("+textfield1.getText()+",'"+textfield2.getText()+"','"+textfield3.getText()+"','"+textfield4.getText()+"','"+textfield5.getText()+"',"+textfield6.getText()+")";
					Statement stmt = con.createStatement();
					int nbe = stmt.executeUpdate(requete);
					Listedoc.message("Le document a été "+action.getSelectedItem()," Gestion des documents ");
					stmt.close();
				}catch (SQLException e) {
					Listedoc.message( "Il ya un probléme dans la base dedonnée","Gestion d'utilisateurs");
				 }
				
			}
	}
//	*******************************************************************************************	

	public static void supprimer(){
		if (textfield1.getText().equals("")) 
		{
			Listedoc.message( "Entrez le numéro du document svp "," Gestion des documents");
		}
		else {	
			try {	
				String requete = "DELETE FROM doc WHERE num ="+textfield1.getText();
				Statement stmt = con.createStatement();
					int nbe = stmt.executeUpdate(requete);
					Listedoc.message("Le document a été "+action.getSelectedItem()," Gestion des documents ");
					stmt.close();
				}catch (SQLException e) {
					Listedoc.message( "Il ya un probléme dans la base dedonnée"," Gestion des documents");
				 }
				
			}
	}
//*******************************************************************************************	

  public static void  modifier(){
	 if (textfield1.getText().equals("")) 
		{
				Listedoc.message( "Entrez le numéro et le mot de passe svp ","Gestion d'utilisateurs");
		}
	 else {
		ResultSet résultats = null;
		if(requet.equals("")){
		  requet="SELECT * FROM doc WHERE num = "+textfield1.getText();
		  try {
			Statement stmt = con.createStatement();
			résultats = stmt.executeQuery(requet);
			boolean encore = résultats.next();
			  while (encore) {
			  	textfield2.setText(résultats.getString(2));
			  	textfield3.setText(résultats.getString(3));
			  	textfield4.setText(résultats.getString(4));
			  	textfield5.setText(résultats.getString(5));
			  	textfield6.setText(résultats.getString(6));
			    encore = résultats.next();
			  }
			  résultats.close();
		   } catch (SQLException e) {
			   Listedoc.message("Il y a un probléme dans la base de donnée","Recherche");
		   }
		}else{
		   try {	
		   	requet="UPDATE doc SET titre = '"+textfield2.getText()+"',auteur='"+textfield3.getText()+"',categorie='"+textfield4.getText()+"',type='"+textfield5.getText()+"',ncop="+textfield6.getText()+" WHERE num="+textfield1.getText();
			Statement stmt = con.createStatement();
			int nbe= stmt.executeUpdate(requet);
			requet="";
			clear();
			Listedoc.message("Le compte a été "+action.getSelectedItem()," Gestion d'utilisateur ");
			stmt.close();
		}catch (SQLException e) {
			Listedoc.message( "Il ya un probléme dans la base dedonnée","Gestion d'utilisateurs");
			e.printStackTrace();
		 }
		}
		
	 }	
  }
	
	
//*******************************************************************************************	
	public Gdocument(){
		
		gf=new Frame(" Gestion des document ");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (InstantiationException e) {e.printStackTrace();} 
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}
		
		gf.addWindowListener (new WindowAdapter( ) {
			public void windowClosing(WindowEvent e)
			{ 
			  gf.dispose();
			}
		});

		gf.setAlwaysOnTop(true);
		gf.setLayout(new BorderLayout());
		gf.setResizable(false);
		gf.setLocation(330,230);
		
		action=new Choice();
		action.addItem("Ajouter");
		action.add("Supprimer");
		action.add("Modifier");
		
		
		label0 = new JLabel("       Choisissez une Action :                                           ");
		label1 = new JLabel("N°du document :");
		label2 = new JLabel("Titre :        ");
		label3 = new JLabel("Auteur :       ");
		label4 = new JLabel("Catégorie :    ");
		label5 = new JLabel("Type  :        ");
		label6 = new JLabel("Nembre de copie :        ");
		
								
		textfield1= new JTextField(30);
        textfield1.addKeyListener(new KeyListener () {
			public void keyTyped(KeyEvent arg0) {
				int k =arg0.getKeyChar();
				if((k>47 && k<58)||k==8 ){}else{
					arg0.setKeyChar((char)KeyEvent.VK_CLEAR);
				}
			}			 
			public void keyPressed(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {}
		});
		textfield2 = new JTextField(30);
		textfield3 = new JTextField(30);
		textfield3.addKeyListener(new KeyListener () {
			public void keyTyped(KeyEvent arg0) {
				int k =arg0.getKeyChar();
				if((k>='A' && k<='Z')||(k>='a' && k<='z')||k==8 ){
					
				}else{
					arg0.setKeyChar((char)KeyEvent.VK_CLEAR);
				}
				
			}			 
			public void keyPressed(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {}
		});
		textfield4 = new JTextField(30);
		textfield4.addKeyListener(new KeyListener () {
			public void keyTyped(KeyEvent arg0) {
				int k =arg0.getKeyChar();
				if((k>='A' && k<='Z')||(k>='a' && k<='z')||k==8 ){
					
				}else{
					arg0.setKeyChar((char)KeyEvent.VK_CLEAR);
				}
				
			}			 
			public void keyPressed(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {}
		});
		textfield5 = new JTextField(30);
		textfield6 = new JTextField(30);
        textfield6.addKeyListener(new KeyListener () {
			public void keyTyped(KeyEvent arg0) {
				int k =arg0.getKeyChar();
				if((k>47 && k<58)||k==8 ){}else{
					arg0.setKeyChar((char)KeyEvent.VK_CLEAR);
				}
			}			 
			public void keyPressed(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {}
		});
				
		button1 = new JButton("Terminer");
		button1.setPreferredSize(new java.awt.Dimension(80,25));
		button1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				gf.dispose();
			}
		});
		
		button2 = new JButton("  Annuler ");
		button2.setPreferredSize(new java.awt.Dimension(80,25));
		button2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				gf.dispose();
			}
		});
		
		button3=new JButton("OK");
		button3.setPreferredSize(new java.awt.Dimension(80,25));
		button3.addActionListener(new java.awt.event.ActionListener() {
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
				if (action.getSelectedItem().equals("Ajouter")){
		 			ajouter();
		 			clear();
				}else if(action.getSelectedItem().equals("Supprimer")){
					supprimer();
					clear();
				}else{
					modifier();
					
				}
			}
		});

		panel1 = new javax.swing.JPanel(new FlowLayout(5,15,11));
		panel1.setPreferredSize(new java.awt.Dimension(125,130));
		panel1.add(label1);
		panel1.add(label2);
		panel1.add(label3);
		panel1.add(label4);
		panel1.add(label5);
		panel1.add(label6);
		
		panel2 = new javax.swing.JPanel(new FlowLayout(5,5,5));
		panel2.setPreferredSize(new java.awt.Dimension(280,165));
		panel2.add(textfield1);
		panel2.add(textfield2);
		panel2.add(textfield3);
		panel2.add(textfield4);
		panel2.add(textfield5);
		panel2.add(textfield6);
				
		panel3 = new JPanel(new FlowLayout());
		panel3.setPreferredSize(new java.awt.Dimension(360,45));
		panel3.add(button1);
		panel3.add(button3);
		panel3.add(button2);
		
		panel4 = new JPanel();
		panel4.setPreferredSize(new java.awt.Dimension(360,40));
		panel4.add(label0);
		panel4.add(action);
		
		gf.add(panel4,BorderLayout.NORTH);
		gf.add(panel1,BorderLayout.WEST);
		gf.add(panel2,BorderLayout.EAST);
		gf.add(panel3,BorderLayout.SOUTH);
		gf.pack();
		gf.setVisible(true);
		
	}

}
