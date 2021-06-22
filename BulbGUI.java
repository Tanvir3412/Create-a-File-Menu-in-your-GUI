mport java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

public class BulbGUI extends JFrame {

      // defining all required components

      // declaring one ArrayList of Bulbs
      private ArrayList<Bulb> unSortedBulbs;
      
  

      // text areas to display unsorted and sorted texts

      JTextArea unsortedText, sortedText;

      // new variables for menu components

      JMenuItem openFile, quit;

      /**

      * method to load dates from an input file, and fill the two lists, and then

      * update the text areas
     * @param filename
      */

      public void loadFromFile(String filename) {

            // initializing lists
            unSortedBulbs=new ArrayList<>();

            try {

                  // using scanner to read file line by line

                  Scanner scanner = new Scanner(new File(filename));

                  while (scanner.hasNext()) {

                       // using StringTokenizer to split line by comma

                       StringTokenizer line = new StringTokenizer(scanner.nextLine(),

                                   ",");
                       //Declare variablee for holding data
                             String manufacturer="";
                             String partNumber="";
                             int wattage=0;
                             int lumens=0;

                       while (line.hasMoreTokens()) {

                             // getting a token
                             
                             manufacturer=line.nextToken();
                             partNumber=line.nextToken();
                             wattage=Integer.parseInt(line.nextToken());
                             lumens=Integer.parseInt(line.nextToken());

                             try {
                                   if(wattage<5){
                                       throw new IllegalBulbException(manufacturer+" is Invalid Bulb");
                                   }
                                   
                                   Bulb b = new Bulb(manufacturer,partNumber,wattage,lumens);
                                   unSortedBulbs.add(b);
                                   
                             } catch (IllegalBulbException e) {
                                   // date creation failed, invalid date, printing to
                                   // console
                                   System.out.println(e.getMessage());
                             }

                       }

                  }

            } catch (FileNotFoundException e) {
                  // file not found
                  System.out.println("Input data file not found!");
            }

            // appending all dates to the both text fields
            String output="";
            
            for(Bulb b:unSortedBulbs){
                output += b.toString();
            }
            unsortedText.setText(output);

      }

      public BulbGUI() {
            /**
            * using a GridLayout with 1 row and 1 column to display elements
            */
            setLayout(new GridLayout(1, 1, 20, 20));

            // initializing ui components
            unsortedText = new JTextArea();

            // using a bigger font size
            unsortedText.setFont(new Font("SansSerif", Font.PLAIN, 12));
            add(unsortedText);

            /**
            * setting up menu bar
            */
            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            openFile = new JMenuItem("Open");
            quit = new JMenuItem("Quit");
            fileMenu.add(openFile);
            fileMenu.add(quit);
            menuBar.add(fileMenu);
            setJMenuBar(menuBar);

            /**
            * Creating a FileMenuHandler, setting this object as the event handler
            * for opening file option and quit option
            */
            FileMenuHandler handler = new FileMenuHandler();
            openFile.addActionListener(handler);
            quit.addActionListener(handler);
            setSize(700, 700);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setTitle("BULB GUI");
            setVisible(true);
      }

      /**
      * inner class representing action listener for file operations in menu bar
      */
      class FileMenuHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                  // finding source of action
                  if (e.getSource().equals(openFile)) {
                       // creating and displaying a file opening dialog
                       JFileChooser fileChooser = new JFileChooser(
                                   System.getProperty("user.dir"));

                       int choice = fileChooser.showOpenDialog(BulbGUI.this);

                       // checking if a file has been chosen correctly
                       if (choice == JFileChooser.APPROVE_OPTION) {

                             // getting selected file
                             File f = fileChooser.getSelectedFile();

                             // loading data from file
                             loadFromFile(f.getAbsolutePath());
                       }
                  } else if (e.getSource().equals(quit)) {
                       System.exit(0);
                  }
            }
      }

} //end of BulbGUI.java