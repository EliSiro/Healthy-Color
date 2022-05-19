package agario;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;

public class main {

    public static boolean gamemode;

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("agar.io");
        JScrollPane pane = new JScrollPane();
        JViewport vport = new JViewport();
        DisplayGame panel = new DisplayGame(frame);
        Poisons poi = new Poisons(5);
        
        panel.menu.setArgs(args);
        vport.add(panel);
        frame.setVisible(true);
        pane.setViewport(vport);
        vport.add(panel);
        frame.add(pane);
        frame.setSize(DisplayGame.WIDTH, DisplayGame.HEIGHT);
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                DisplayGame.WIDTH = frame.getWidth();
                DisplayGame.HEIGHT = frame.getHeight();
            }
        });
        panel.setvPort(vport);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
