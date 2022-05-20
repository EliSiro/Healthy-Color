package agario;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.Random;

public class Players implements Serializable {

    private int counterHalve = 0;
    private Ellipse2D.Double Player;
    private Ellipse2D.Double SonPlayer1;
    private Ellipse2D.Double SonPlayer2;
    private Ellipse2D.Double SonPlayer3;
    private Ellipse2D.Double SonPlayer4;
    private Ellipse2D.Double SonPlayer5;
    private Ellipse2D.Double SonPlayer6;
    private Color playerColor;
    public boolean isplayer = true;
    private double velocity = 10; //200005
    Random random;

    Players() {
        random = new Random();
        Player = new Ellipse2D.Double(random.nextInt(500) + 3000, random.nextInt(500) + 2000, 80, 80);
        playerColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public void drawPlayerG2(Graphics2D g2) {
        g2.setColor(playerColor);
        g2.fill(Player);
    }

    public void drawPlayerG3(Graphics2D g3) {
        g3.setColor(playerColor);
        g3.fill(SonPlayer1);

    }

    public void drawPlayerG4(Graphics2D g4) {
        g4.setColor(playerColor);
        g4.fill(SonPlayer2);
    }

    public void drawPlayerG5(Graphics2D g5) {
        g5.setColor(playerColor);
        g5.fill(SonPlayer3);
    }

    public void drawPlayerG6(Graphics2D g6) {
        g6.setColor(playerColor);
        g6.fill(SonPlayer4);
    }

    public void increaseSize() {
        Player.width += 1;
        Player.height += 1;
        velocity -= 0.03;
    }

    public void decreaseSize() {
        Player.width -= 2;
        Player.height -= 2;
        velocity += 0.05;
    }
    public void decreaseSizew(){
        Player.width -= 30;
        Player.height -= 30;
        velocity += 0.5;
    }

    public int countHalve() {
        counterHalve++;
        return counterHalve;
    }

    public void halveSizeFather() {

        //Padre
        Player.width = Player.width / 2;
        Player.height = Player.height / 2;
        velocity = velocity * 2;
    }

    /*public void halveSizeSon()
        {
            if(counterHalve == 0)
            {
            SonPlayer1.width = Player.width;
            SonPlayer1.height = Player.height;
            }
            
            if(counterHalve == 1)
            {
            SonPlayer2.width = Player.width;
            SonPlayer2.height = Player.height;
            }
            
            if(counterHalve == 2)
            {
            SonPlayer3.width = Player.width;
            SonPlayer3.height = Player.height;
            }
            
            if(counterHalve == 3)
            {
            SonPlayer4.width = Player.width;
            SonPlayer4.height = Player.height;
            }
            
            if(counterHalve == 4)
            {
            SonPlayer5.width = Player.width;
            SonPlayer5.height = Player.height;
            }
            
            if(counterHalve == 5)
            {
            SonPlayer6.width = Player.width;
            SonPlayer6.height = Player.height;
            }
            
        }
     */
    public void moveRight() {
        Player.x += 1;
    }

    public Ellipse2D.Double getPlayer() {
        return Player;
    }

    public void setX(double n) {
        Player.x = n;
    }

    public void setY(double n) {
        Player.y = n;
    }

    public double getX() {
        return Player.x;
    }

    public double getY() {
        return Player.y;
    }

    public void setPlayer(Ellipse2D.Double player) {
        Player = player;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getSize() {
        return Player.width;
    }
}
