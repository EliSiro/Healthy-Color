package agario;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Menu implements MouseListener { 
    private Rectangle playButton = new Rectangle(DisplayGame.WIDTH / 2 - 50, DisplayGame.HEIGHT / 2, 100, 50);
    private Rectangle quitButton = new Rectangle(DisplayGame.WIDTH / 2 - 50, DisplayGame.HEIGHT / 2 + 100, 100, 50);
    private boolean enabled = true;
    private DisplayGame displayGame;
    private Point pointPlayer1;
    public String[] args;
    private Frame f;
    private BufferedImage logo;

    public Menu(DisplayGame displayGame, Frame f) {
        this.displayGame = displayGame;
        this.f = f;
    }

    public void setArgs(String[] A) {
        args = A;
    }

    public void render(Graphics2D g2) {
        Font font = new Font("calibri", Font.BOLD, 50);
        g2.setFont(font);
        logo = ImageIO.read(new File("C:\\Users\\Tommaso\\Desktop\\Principale\\Scuola_Superiori\\Tommy\\ANNO_2022\\Progetto_Tecnologie_Agar.Io_20.05.2022\\Healthy-Color\\agario\\src\\agario\\images\\Healthy_Color_Logo.png"));
        g2.fillOval(f.getWidth() / 2 - 73, f.getHeight() / 2 - 250, 150, 150);
        
        g2.setColor(Color.ORANGE);
        g2.drawString("Good Game", f.getWidth() / 2 - 140, f.getHeight() / 2 - 50);
        
        g2.setColor(Color.BLACK);
        playButton = new Rectangle(f.getWidth() / 2 - 50, f.getHeight() / 2, 100, 50);
        quitButton = new Rectangle(f.getWidth() / 2 - 50, f.getHeight() / 2 + 100, 100, 50);
        g2.drawString("Play", playButton.x, playButton.y + 40);         
        g2.drawString("Quit", quitButton.x, quitButton.y + 40);
    }

    public void setPoint(Point pointPlayer1) {
        this.pointPlayer1 = pointPlayer1;
    }

    public void setDisplayGame(DisplayGame dg) {
        displayGame = dg;
    }

    public void player1Won(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        Font font = new Font("calibri", Font.BOLD, 50);
        g2.setFont(font);
        g2.drawString("YOU WON", pointPlayer1.x - 100, pointPlayer1.y);
    }

    public void player2Won(Graphics2D g2) {
        g2.setColor(Color.RED);
        Font font = new Font("calibri", Font.BOLD, 50);
        g2.setFont(font);
        g2.drawString("YOU LOST", pointPlayer1.x - 100, pointPlayer1.y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        if (enabled) {
            int mx = e.getX();
            int my = e.getY();
            int mDonnax = f.getWidth() / 2 - 50;
            int mDonnay = f.getHeight() / 2;
            if (mx >= mDonnax && mx <= mDonnax + 100) {
                if (my >= mDonnay && my <= mDonnay + 50) {
                    DisplayGame.state = DisplayGame.STATE.GAME;
                    enabled = false;
                }
            }
            if (mx >= 370 && mx <= 470) {
                if (my >= 440 && my < 490) {
                    System.exit(1);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

}
