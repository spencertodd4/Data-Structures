/*
HashAssign2.java
Suraj Rampure
2015-04-13

This program takes in locations and their corresponding "emotions"
and provides a graphic representation of the emotions within a 10px radius
of the user's mouse on a map.
*/

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

/*
Frame class
Creates a DisplayPanel and displays it,
and calls actionPerformed to refresh it
*/
public class HashAssign2 extends JFrame implements ActionListener {

    javax.swing.Timer myTimer;
    DisplayPanel panel;

    public HashAssign2 () {
        super("Creeper");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new DisplayPanel();
        add(panel);

        myTimer = new javax.swing.Timer(10, this);
        myTimer.start();

        setResizable(false);
        setVisible(true);

    }

    public void actionPerformed (ActionEvent evt) {
        panel.repaint();
    }

    // New instance of the class
    public static void main (String [] args) {
        new HashAssign2();
    }

}

/*
Panel class
Consists of the logic of the application.
This loads all of the entries from the data file,
creates a hash table of all entries, and uses a defined hash method
to check the user's mouse location's hash code against the entries
to find matches.
*/
class DisplayPanel extends JPanel implements MouseListener, MouseMotionListener {

    private int mx, my;
    private boolean isClicked = false;
    private Image map;

    private HashTable <Entry> entries = new HashTable <Entry> ();

    public DisplayPanel() {

        entries = loadEntries();
        map = new ImageIcon("windsor.png").getImage();

        addMouseListener(this);
        addMouseMotionListener(this);

        setVisible(true);

    }

    // MouseListener and MouseMotionListener implementation
    // The first four methods have no use in this class
    public void mouseClicked(MouseEvent evt) {}
    public void mouseEntered(MouseEvent evt) {}
    public void mouseExited(MouseEvent evt) {}
    public void mouseMoved(MouseEvent evt) {}

    public void mousePressed(MouseEvent evt) {
        isClicked = true;
        mx = evt.getX();
        my = evt.getY();
    }

    public void mouseReleased(MouseEvent evt) {
        isClicked = false;
    }

    public void mouseDragged(MouseEvent evt) {
        mx = evt.getX();
        my = evt.getY();
    }

    // loadEntries – reads from creeper.txt
    // and creates Entry objects from the file
    public HashTable <Entry> loadEntries() {
        Scanner inFile = null;
        File f = new File("creeper.txt");
        HashTable <Entry> entries = new HashTable <Entry> ();

        try {
            inFile = new Scanner(f);

            while (inFile.hasNext()) {
                // inFileanner gets integer tokens
                // Entries are in the form x, y, lh, hs and eb, one per line
                Entry data = new Entry(inFile.nextInt(), inFile.nextInt(), inFile.nextInt(), inFile.nextInt(), inFile.nextInt());
                entries.add(data);
            }
        }

        catch (FileNotFoundException ex) {
            System.out.println(ex);
            return null;
        }

        return entries;
    }

    // hashAsEntry - hashes two integers the same way the Entry class does
    // Used to hash the user's mouse position to check it against the entries
    public int hashAsEntry(int x, int y) {
        return (Integer.toString(x) + Integer.toString(y)).hashCode();
    }

    // toColor
    // ls, hs and eb values come from -100 to 100
    // This converts them to 0 to 255
    public int toColor(int val) {
        return (val + 100) * 255/200;
    }

    // paintComponent – handles all drawing
    public void paintComponent (Graphics g) {
        g.drawImage(map, 0, 0, this);
        int rad = 64;

        // Checks for all points in a 10 pixel radius around the mouse
        // to uncover entries
        if (isClicked) {
            for (int x = mx - rad; x < mx + rad; x ++) {
                for (int y = my - rad; y < my + rad; y ++) {
                    if (x > 0 && x < getWidth() && y > 0 && y < getHeight()) {
                        // Makes sure the points are within a circle, as the current traversal method checks points around a square
                        if (Math.hypot(mx - x, my - y) <= rad) {

                            int hashval = hashAsEntry(x, y);
                            // Since there may be multiple entries at the same position,
                            // We need to get the average emotion at the point –
                            // This is done by getting a list of the entries
                            // with the same hashcode as the position and averaging their
                            // converted r,g,b values
                            LinkedList <Entry> list = entries.getList(hashval);

                            int red = 0, green = 0, blue = 0;

                            // The values required must be an average of all
                            if (list != null) {
                                for (Entry e: list) {
                                    if (e.getX() == x && e.getY() == y) {
                                        red += toColor(e.getLH());
                                        green += toColor(e.getHS());
                                        blue += toColor(e.getEB());
                                    }
                                }

                                Color c = new Color(red/list.size(), green/list.size(), blue/list.size());
                                g.setColor(c);
                                g.drawLine(x, y, x, y);
                            }
                        }
                    }
                }
            }

            // Draws a red circle around the mouse
            g.setColor(Color.RED);
            g.drawOval(mx-rad, my-rad, 2*rad, 2*rad);
        }
    }
}

/*
Entry class
Used to store the data of each entry (position and three emotions)
and hash the location values of each entry
*/
class Entry {

    private int x, y;
    private int lh, hs, eb;

    public Entry (int x, int y, int lh, int hs, int eb) {
        this.x = x;
        this.y = y;
        this.lh = lh;
        this.hs = hs;
        this.eb = eb;
    }

    // Getter methods
    public int getX() {return x;}
    public int getY() {return y;}
    public int getLH() {return lh;}
    public int getHS() {return hs;}
    public int getEB() {return eb;}

    // Overrides the hashCode() method
    // Hashes by converting the points into a string and taking that hash code
    // Emotions aren't considered because user mouse position to which entries
    // are compared to do not have emotion
    @Override
    public int hashCode() {
        return (Integer.toString(x) + Integer.toString(y)).hashCode();
    }
}