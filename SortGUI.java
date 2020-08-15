/*******************************************************************************
  *                                                                        *
  *                                                                        *
  * Programmer : Bodipudi Pragna                                           *
  *                                                                        *
  *                                                                        *
  * Purpose : This program creates frame, panels and displays the GUI      *
  *  to the user                                                           *
  *                                                                        *
  ******************************************************************************/

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class SortGUI extends JFrame {
 /**
  * 
  */
 private static final long serialVersionUID = 1L;

 public static void main(String[] args) {
   SortGUI mainFrame = new SortGUI();              // Makes new GUI
  mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       // Setting a default close.
  mainFrame.setResizable(false);          // Not to make resizable
  mainFrame.setVisible(true);             // Makes GUI visible.
 }

 public SortGUI() {

  super("Sorting Animation");                    // Title of the panel.
  //setSize(700, 450); // Setting size of the panel.
  setSize(700, 450);
  setLayout(new FlowLayout());

  SortPanel leftPanel = new SortPanel();         // Instantiation of panel.
  SortPanel rightPanel = new SortPanel();        // Instantiation of panel.

  /* Adding frames */
  
  add(leftPanel);
  add(rightPanel);

  pack();

 }
}