/*
 * Application and MainFrame
 * 
 * Andrew Ettles
 *
 */




import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class KlondikeApp {
    private static final int JFRAME_AREA_WIDTH = A1Constants.JFRAME_AREA_WIDTH;
    private static final int JFRAME_AREA_HEIGHT = A1Constants.JFRAME_AREA_HEIGHT;;
    
    public static void main(String[] args) {
        

        JFrame gui = new KFrame("Four Kings", 300, 300, JFRAME_AREA_WIDTH, JFRAME_AREA_HEIGHT);
    }
}

class KFrame extends JFrame {
    public KFrame(String title, int x, int y, int width, int height) {
        // Set the title, top left location, and close operation for the frame
        setTitle(title);
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create an instance of the JPanel class, and set this to define the
        // content of the window
        final KPanel frameContent = new KPanel();
        Container visibleArea = getContentPane();
        visibleArea.add(frameContent);
        
        // Set the size of the content pane of the window, resize and validate the
        // window to suit, obtain keyboard focus, and then make the window visible
        frameContent.setPreferredSize(new Dimension(width + 10, height + 10));
        pack();
        frameContent.requestFocusInWindow();
        setVisible(true);
        
        
        
      //-------------------------------------------------------------------------------------------------
    	//initialize MainFrame menu items
    		
    		JMenuBar menuBar = new JMenuBar();
    		setJMenuBar(menuBar);
    		
    		JMenu mnFile = new JMenu("File");
    		menuBar.add(mnFile);
    		
    		JMenuItem mntmNewGame = new JMenuItem("new game");
    		mntmNewGame.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				
    				
    				
    				frameContent.reset();
    				repaint();
    			
    			}
    		});
    		mnFile.add(mntmNewGame);
    		
    		JMenuItem mntmStats = new JMenuItem("stats");
    		mntmStats.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				
    				
    				
    			}
    		});
    		mnFile.add(mntmStats);
    		
    		JMenuItem mntmOptions = new JMenuItem("options");
    		mntmOptions.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				
    				
    				
    			}
    		});
    		mnFile.add(mntmOptions);
    		
    		JMenuItem mntmExit = new JMenuItem("exit");
    		mntmExit.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				System.exit(0);
    				
    			}
    		});
    		mnFile.add(mntmExit);
    		
    		JMenu mnHelp = new JMenu("Help");
    		mnHelp.setBackground(Color.PINK);
    		menuBar.add(mnHelp);
    		
    		JMenuItem mntmHelp = new JMenuItem("help");
    		mntmHelp.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				
    				}
    		});
    		mnHelp.add(mntmHelp);
    		
    		
    		repaint();
    		
    }
}
