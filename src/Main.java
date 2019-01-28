import javax.swing.*;
import java.awt.*;

/**
 * Main
 * Entry Point
 * Frame wordt gemaakt, MainPanel als contentpane,
 * Frame is fullscreen
 */
public class Main {

    public static void main(String[] args) {
        // grootte van het beeldscherm
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // het frame
        JFrame frame = new JFrame();
        frame.setSize(screenSize.width, screenSize.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setResizable(false);

        // main panel
        MainPanel panel = new MainPanel();
        panel.init();

        // set main panel & visible
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
