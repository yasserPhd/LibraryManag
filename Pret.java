




/*
 * Created on 14 avr. 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
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
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Pret {
	
	private JLabel label1,label2,label3,label4,label5,label6,label7,label8;
	private static JTextField textfield1,textfield2,textfield3,textfield4,textfield5,textfield6,textfield7;
	private JButton button1,button2;
	private static Choice action;
	private static Frame pf;
	private static Connection con;
	private static long Etud=1382400000,EF=1814400000;
	private JPanel panel1,panel2,panel3,panel4,panel5,panel6;
	
//************************************************************************************************************************
	public static void listepret(String pret){
		Listepret liste=new Listepret(" Liste des prets");
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
		ResultSet résultats = null;
		String requet="SELECT * FROM pret,doc WHERE pret.num=doc.num";
		try {
			Statement stmt = con.createStatement();
			résultats = stmt.executeQuery(requet);
			boolean encore = résultats.next();
			Listepret.effacer();
			if(pret.equals("general")){
			  while (encore) {
				Listepret.setdoc(résultats.getInt(2)+"",résultats.getString(3)+"  "+résultats.getString(4),résultats.getString(1),résultats.getString(8) ,résultats.getString(9), DateFormat.getDateInstance().format(résultats.getDate(5)),DateFormat.getDateInstance().format(résultats.getDate(6)));
			    encore = résultats.next();
			  }
			}else{
				 while (encore) {
				   Date datef=résultats.getDate(6); 
				   Date dd =new Date(System.currentTimeMillis());
				   if(dd.after(datef)){
				  	Listepret.setdoc(résultats.getInt(2)+"",résultats.getString(3)+"  "+résultats.getString(4),résultats.getString(1),résultats.getString(8) ,résultats.getString(9),DateFormat.getDateInstance().format(résultats.getDate(5)),""+DateFormat.getDateInstance().format(datef));
				   }
				   encore = résultats.next();
				 }
			}
			résultats.close();
		}catch (SQLException sql) {
			 Listedoc.message("Il y a un probléme dans la base de donnée","Liste des prets");
		}
            
	}
//************************************************************************************************************************	
	
public static boolean verifier(int n){
		int np=0;
		if (textfield1.getText().equals("")){
			
		}else{
			try {	
				String requet = "SELECT * FROM pret WHERE numE =" + textfield1.getText();
				Statement st = con.createStatement ();		
				ResultSet rs = st.executeQuery (requet);
				boolean ver=rs.next();
				while(ver){
					np++;
					ver=rs.next();
				}
				st.close(); 					
			}
			catch (SQLException sqlex) {
		          Listedoc.message ( "Il y a un probléme ","Pret");
			}
		}
		if(np>=n){
			return false;
		}else{
			return true;
		}
	  
	}
//************************************************************************************************************************	
	public static void prendre(){
		Date df = null;
		if(action.getSelectedItem().equals("Etudiant")){
		  df =new Date(System.currentTimeMillis()+Etud);
		}else if(action.getSelectedItem().equals("Enseignant")||action.getSelectedItem().equals("Fin cycle")){
		   df =new Date(System.currentTimeMillis()+EF);
		}
		if(verifier(3)==false){
			Listedoc.message( "Vous ne pouvez pas prendre plus que 3 livres  ","Gestion des prets");
		}else{
		   if (textfield1.getText().equals("")||textfield2.getText().equals("")||
				textfield3.getText().equals("")||textfield4.getText().equals("")) 
		   {
			Listedoc.message( "Vous devez remplir toutes les informations  ","Gestion des prets");
		   }
		   else {	
			    try {	
					String requete = "INSERT INTO pret VALUES ("+textfield5.getText()+","
					                                            +textfield1.getText()+",'"
																+textfield2.getText()+"','"
																+textfield3.getText()+"','"
																+textfield4.getText()+"','"
																+DateFormat.getDateInstance().format(df)+"')";
					Statement stmt = con.createStatement();
					int nbe = stmt.executeUpdate(requete);
					nbe =stmt.executeUpdate("UPDATE doc SET ncop= ncop-1  WHERE num = "+textfield5.getText());
					pf.dispose();
					Listedoc.message("Le pret a été effectuer"," Gestion des pret ");
					Listedoc.supdoc();
					stmt.close();
				 }catch (SQLException e) {
					e.printStackTrace();
					Listedoc.message( "Il ya un probléme dans la base de donnée..","Gestion d'utilisateurs");
				 }
				
			
				
			}
		}
	}
//************************************************************************************
    public Pret(Object num,Object titre,Object auteur){
		pf=new Frame("Pret d'un document");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (InstantiationException e) {e.printStackTrace();} 
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}
		
		pf.addWindowListener (new WindowAdapter( ) {
			public void windowClosing(WindowEvent e)
			{ 
			  pf.dispose();
			}
		});
		pf.setAlwaysOnTop(true);
		pf.setLayout(new BorderLayout());
		pf.setResizable(false);
		pf.setLocation(330,230);

		label1 = new JLabel("Numéro : ");
		label2 = new JLabel("Nom : ");
		label3 = new JLabel("Prénom : ");
		label4 = new JLabel("Pret le : ");
		label5 = new JLabel("N°Document : ");
		label6 = new JLabel("Titre :      ");
		label7 = new JLabel("Auteur :      ");
		label8 = new JLabel("Selectionnez le type de pret :                        ");
						
		textfield1= new JTextField(30);
		textfield1.addKeyListener(new KeyListener () {
			public void keyTyped(KeyEvent arg0) {
				int k =arg0.getKeyChar();
				if((k>47 && k<58)||k==8 ){}
				else{
				  arg0.setKeyChar((char)KeyEvent.VK_CLEAR);
				}
			}			 
			public void keyPressed(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {}
		});
		
		textfield2 = new JTextField(30);
		textfield2.addKeyListener(new KeyListener () {
			public void keyTyped(KeyEvent arg0) {
				int k =arg0.getKeyChar();
				if((k>='A' && k<='Z')||(k>='a' && k<='z')||k==8 ){}				
				else{
					arg0.setKeyChar((char)KeyEvent.VK_CLEAR);
				}
			}			 
			public void keyPressed(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {}
		});
		
		textfield3 = new JTextField(30);
		textfield3.addKeyListener(new KeyListener () {
			public void keyTyped(KeyEvent arg0) {
				int k =arg0.getKeyChar();
				if((k>='A' && k<='Z')||(k>='a' && k<='z')||k==8 ){}
				else{
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
				if((k>47 && k<58)||k=='/'||k==8 ){}
				else{
					arg0.setKeyChar((char)KeyEvent.VK_CLEAR);
				}
			}			 
			public void keyPressed(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {}
		});
		Date dd =new Date(System.currentTimeMillis());
		textfield4.setText(DateFormat.getDateInstance().format(dd));
		
		textfield5 = new JTextField(30);
		textfield5.setText((String) num);
		textfield5.setEditable(false);
		
		textfield6 = new JTextField(30);
		textfield6.setText((String) titre);
		textfield6.setEditable(false);
		
		textfield7 = new JTextField(30);
		textfield7.setText((String) auteur);
		textfield7.setEditable(false);
		
		button1 = new JButton("Valider");
		button1.setPreferredSize(new java.awt.Dimension(80,24));
		button1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
					String loc = "jdbc:odbc:bibliotheque";
					con = DriverManager.getConnection (loc,"Administrateur","1987");
				}
				catch (ClassNotFoundException cnf)  {
					Listedoc.message ("Driver non chargé...","Base de donn?e");
				}
				catch (SQLException sqlex) {
					Listedoc.message ( "Incapable de connecter la base de données...","Base de donn?e");
		 		}
				prendre();
			}
		});
		
		button2 = new JButton("Annuler");
		button2.setPreferredSize(new java.awt.Dimension(80,24));
		button2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				pf.dispose();
			}
		});

		action=new Choice();
		action.addItem("Etudiant");
		action.add("Fin cycle");
		action.add("Enseignant");
		
		panel1 = new JPanel(new FlowLayout(5,15,11));
		panel1.setPreferredSize(new java.awt.Dimension(90,190));
		panel1.add(label1);
		panel1.add(label2);
		panel1.add(label3);
		panel1.add(label4);
		panel1.add(label5);
		panel1.add(label6);
		panel1.add(label7);
		

		panel2 = new javax.swing.JPanel(new FlowLayout(5,5,5));
		panel2.setPreferredSize(new java.awt.Dimension(280,200));
		panel2.add(textfield1);
		panel2.add(textfield2);
		panel2.add(textfield3);
		panel2.add(textfield4);
		panel2.add(textfield5);
		panel2.add(textfield6);
		panel2.add(textfield7);
		
		
		panel3 = new JPanel(new FlowLayout());
		panel3.setPreferredSize(new java.awt.Dimension(360,35));
		panel3.add(button1);
		panel3.add(button2);
		
		panel4 = new JPanel();
		panel4.setPreferredSize(new java.awt.Dimension(360,35));
		panel4.add(label8);
		panel4.add(action);
		
		
		pf.add(panel4,BorderLayout.NORTH);
		pf.add(panel1,BorderLayout.WEST);
		pf.add(panel2,BorderLayout.EAST);
		pf.add(panel3,BorderLayout.SOUTH);
		pf.pack();
		pf.setVisible(true);
		
	}
}

