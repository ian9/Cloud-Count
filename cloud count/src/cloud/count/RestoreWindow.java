/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * From stackoverflow:http://stackoverflow.com/questions/7777640/what-is-the-best-practice-for-setting-jframe-locations-in-java/7778332#7778332
 */
package cloud.count;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Properties;
import java.io.*;

class RestoreWindow {

    /** This will end up in the current directory
    A more sensible location is a sub-directory of user.home.
    (left as an exercise for the reader) */
    public static final String fileName = "options.prop";

    /** Store location & size of UI */
    public static void storeOptions(JFileChooser f) throws Exception {
        File file = new File(fileName);
        Properties p = new Properties();
        // restore the frame from 'full screen' first!
        //f.setExtendedState(Frame.NORMAL);
        Rectangle r = f.getBounds();
        int x = (int)r.getX();
        int y = (int)r.getY();
        int w = (int)r.getWidth();
        int h = (int)r.getHeight();

        p.setProperty("x", "" + x);
        p.setProperty("y", "" + y);
        p.setProperty("w", "" + w);
        p.setProperty("h", "" + h);
        p.setProperty("Current Directory", f.getCurrentDirectory().getAbsolutePath());

        BufferedWriter br = new BufferedWriter(new FileWriter(file));
        p.store(br, "Properties of the user frame");
    }

    /** Restore location & size of UI */
    public static void restoreOptions(JFileChooser f) throws IOException {
        File file = new File(fileName);
        Properties p = new Properties();
        BufferedReader br = new BufferedReader(new FileReader(file));
        p.load(br);

        int x = Integer.parseInt(p.getProperty("x"));
        int y = Integer.parseInt(p.getProperty("y"));
        int w = Integer.parseInt(p.getProperty("w"));
        int h = Integer.parseInt(p.getProperty("h"));
        String path = p.getProperty("Current Directory");

        Rectangle r = new Rectangle(x,y,w,h);

        f.setBounds(r);
        f.setCurrentDirectory(new File(path));
    }

//    public static void main(String[] args) {
//        final JFrame f = new JFrame("Good Location & Size");
//        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        f.addWindowListener( new WindowAdapter() {
//            public void windowClosing(WindowEvent we) {
//                try {
//                    storeOptions(f);
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//                System.exit(0);
//            }
//        });
//        JTextArea ta = new JTextArea(20,50);
//        f.add(ta);
//        f.pack();
//
//        File optionsFile = new File(fileName);
//        if (optionsFile.exists()) {
//            try {
//                restoreOptions(f);
//            } catch(IOException ioe) {
//                ioe.printStackTrace();
//            }
//        } else {
//            f.setLocationByPlatform(true);
//        }
//        f.setVisible(true);
//    }
}
