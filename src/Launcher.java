import javax.swing.SwingUtilities;

import ui.EntryFrame;

/**
 * �������
 */
public class Launcher {
	public static void main(String[] args){
		//������ڴ���
		SwingUtilities.invokeLater(()->new EntryFrame());
	}
}
