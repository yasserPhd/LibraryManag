

/*
 * Created on 17 avr. 2007
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * Created on 17 avr. 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


public class Recherche {
	private JLabel label1,label2,label3,label4,label5;
	private JTextField textfield1,textfield2,textfield3,textfield4,textfield5;
	private JButton button1,button2;
	private Frame rf;
	private JPanel panel1,panel2,panel3,panel4;
	private Connection con;	
	private ResultSet rs;
//***************************************************************************	
	public void chercher(){
		
		ResultSet résultats = null;
		String requet="SELECT * FROM doc WHERE ";
		boolean vid=false;
		if (textfield1.getText().equals("")&& textfield2.getText().equals("")&& textfield3.getText().equals("")&& textfield4.getText().equals("")&& textfield5.getText().equals("")) 
		{
			Listedoc.message( "Entrez le critaire de recherche svp  ","Recherche");
		}
		else {	
		vid=textfield1.getText().equals("");
		if(vid == false){
			requet=requet+"num = "+textfield1.getText();
		}else{
			vid=textfield2.getText().equals("");
			if(vid == false){
		    requet=requet+" titre ='"+textfield2.getText()+"'AND ";
			}
			vid=textfield3.getText().equals("");
			if(vid == false){
			requet=requet+"auteur ='"+textfield3.getText()+"' AND ";
			}
			vid=textfield4.getText().equals("");
			if(vid ==false){
				requet=requet+"categorie ='"+textfield4.getText()+"' AND ";
			}
			vid=textfield5.getText().equals("");
			if(vid ==false){
					requet=requet+"type ='"+textfield5.getText()+"' AND ";
			}
			requet=requet+"ncop >= 0";
		}
		try {
			Statement stmt = con.createStatement();
			résultats = stmt.executeQuery(requet);
			boolean encore = résultats.next();
			if(encore==false){
				Listedoc.message("Aucune résultat pour cette recherche..","Recherche");
			}else{
			 Listedoc.effacer();
			 while (encore) {
			 	int nc=résultats.getInt(6);
			 	String dis="Disponible   ("+nc+")";
				if(nc==0){
					dis="Indisponible";
				}
			    Listedoc.setdoc(résultats.getString(1),résultats.getString(2),résultats.getString(3),résultats.getString(4),résultats.getString(5),dis);
			    encore = résultats.next();
			 }
			 résultats.close();
			 rf.dispose();
			}
		} catch (SQLException e) {
			Listedoc.message("Il y a un probléme dans la base de donnée","Recherche");
		}
            
		}	 
	}
//********************************************************************************************
	public Recherche(){
		rf=new Frame(" Chercher un document ");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (InstantiationException e) {e.printStackTrace();} 
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}
		
		rf.addWindowListener (new WindowAdapter( ) {
			public void windowClosing(WindowEvent e)
			{ 
			  rf.dispose();
			}
		});

		rf.setAlwaysOnTop(true);
		rf.setLayout(new BorderLayout());
		rf.setResizable(false);
		rf.setLocation(330,230);
		
		label1 = new JLabel("N°du document :");
		label2 = new JLabel("Titre :        ");
		label3 = new JLabel("Auteur :       ");
		label4 = new JLabel("Catégorie :    ");
		label5 = new JLabel("Type  :        ");
								
		textfield1 = new JTextField(30);
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
				chercher();
				
			}
		});
		
		button2 = new JButton("Annuler");
		button2.setPreferredSize(new java.awt.Dimension(80,25));
		button2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				rf.dispose();
			}
		});

		panel1 = new javax.swing.JPanel(new FlowLayout(5,15,11));
		panel1.setPreferredSize(new java.awt.Dimension(100,130));
		panel1.add(label1);
		panel1.add(label2);
		panel1.add(label3);
		panel1.add(label4);
		panel1.add(label5);
		
		panel2 = new javax.swing.JPanel(new FlowLayout(5,5,5));
		panel2.setPreferredSize(new java.awt.Dimension(280,140));
		panel2.add(textfield1);
		panel2.add(textfield2);
		panel2.add(textfield3);
		panel2.add(textfield4);
		panel2.add(textfield5);
				
		panel3 = new JPanel(new FlowLayout());
		panel3.setPreferredSize(new java.awt.Dimension(360,45));
		panel3.add(button1);
		panel3.add(button2);
		
		panel4 = new JPanel();
		panel4.setPreferredSize(new java.awt.Dimension(360,15));
		
		rf.add(panel4,BorderLayout.NORTH);
		rf.add(panel1,BorderLayout.WEST);
		rf.add(panel2,BorderLayout.EAST);
		rf.add(panel3,BorderLayout.SOUTH);
		rf.pack();
		rf.setVisible(true);
		
	}
}
