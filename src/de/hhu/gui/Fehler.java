package de.hhu.gui;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * @author Viet Dung Tran
 *
 * created on 07.06.2011
 */

public final class Fehler {
	private static  JFrame frame = new JFrame();
	
	/**
	 * Fehler handeln
	 * @param string Name der Fehle
	 */
	public static  void undefinedString(String string){
		JOptionPane.showMessageDialog( frame ,string,
				"Fehler!", JOptionPane.ERROR_MESSAGE);
	}
	
	
}
