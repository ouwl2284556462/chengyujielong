import javax.swing.SwingUtilities;

import ui.EntryFrame;

/**
 * 程序入口
 */
public class Launcher {
	public static void main(String[] args){
		//创建入口窗口
		SwingUtilities.invokeLater(()->new EntryFrame());
	}
}
