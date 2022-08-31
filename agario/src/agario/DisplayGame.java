/**
 * @author  Zonca Tommaso
 * @author Pirovano Yuri
 * @author Lettiero Riccardo
 * @version 0.1
 * @file DisplayGame.java
 *
 * @brief File per la visualizzazione a video
 *
 */


package agario;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * @class DisplayGame.java
 *
 * @brief Costruttore Default con estenzione JPanel e implementazione a ActionListener
 *
 */

public class DisplayGame extends JPanel implements ActionListener {
    // servono un array/list di giocatori controllati dalla CPU (= ogni gioatore deve essere un thread) 

    private Rectangle outerArea;
    private Rectangle Bordo;
    public static int WIDTH = 1920;
    public static int HEIGHT = 1080;
    private int currentWidth;
    private int currentHeitht;
    private int numoffoods = 1000;
    public Players player1; 
    private JViewport vPort;
    private Players player2;
    private Players player3;
    private Players player4;
    private Players player5;
    private Players player6;
    private ComandiAggiuntivi comandi;
    private Foods food;
    private long time;
    private Poisons poison;
    public Menu menu;
    private Point pointPlayer1;
    private JTextField connect;
    public String[] args;
    private ThreadFood tf;
    private ThreadPoison tp;
    private Frame f;
    private BufferedImage background;
    private client client;
    Players p;

    private ThreadBot[] vBot;
    private ThreadClassifica tClassifica;

    /**
     @brief Stato Programma
     */
    
    public static enum STATE {
        MENU,
        GAME,
        LOSE,
        WIN
    };
    public static STATE state = STATE.MENU;

/**
 * @class DisplayGame.java
 *
 * @brief Costruttore Default
 * 
 * @param Frame F
 */
    
    public DisplayGame(Frame f) {
        Timer timer = new Timer(1, this);
        time = System.nanoTime();
        this.f = f;
        menu = new Menu(this, f);
        addMouseListener(menu);
        setFocusable(true);
        requestFocusInWindow();
        client = new client("101.58.50.82", 12345);
        player1 = new Players();
        player2 = new Players();
        player3 = new Players();
        player4 = new Players();
        player5 = new Players();
        player6 = new Players();
        comandi = new ComandiAggiuntivi(this, player1, poison, food);
        f.addKeyListener(comandi);
        poison = new Poisons(numoffoods / 10);
        food = new Foods(numoffoods);
        tf = new ThreadFood(food);
        tp = new ThreadPoison(poison);
        tf.start();
        tp.start();

        Dimension newSize = new Dimension(7000, 7000);
        outerArea = new Rectangle(0, 0, 7000, 7000);
        Bordo = new Rectangle(1000, 1000, 5040, 5040);
        setPreferredSize(newSize);
        currentWidth = WIDTH;
        currentHeitht = HEIGHT;
        timer.start();
        background = null;
        try {
            background = ImageIO.read(new File(".\\src\\agario\\images\\sfondo_ok.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(DisplayGame.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*for (int i = 0; i < 10; i++) {
            //vBot[i]=new ThreadBot();
            //vBot[i].start();
        }*/

        tClassifica = new ThreadClassifica(player1, vBot, this); // questo va modificato sulla base di come gestite i giocatori
        tClassifica.start();
    }
    
    /**
     @brief Setta vPort
     * 
     * @param vPort
     */

    public void setvPort(JViewport vPort) {
        this.vPort = vPort;
    }
    
    /**
     @brief Disegna i componenti grafici
     * 
     * @param paintComponent
     * @param Graphics2D
     * @param setRenderingHint
     * @param drawImage
     */
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //G2
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(background, 1000, 1000, 5040, 5040, this);

        //setBackground(Color.GRAY);
        if (state == STATE.MENU) {
            menu.render(g2);
        } else if (state == STATE.GAME) {
            poison.drawPoisons(g2);
            food.drawFood(g2);
            player1.drawPlayerG2(g2);
            player2.drawPlayerG2(g2);
            player3.drawPlayerG2(g2);
            player4.drawPlayerG2(g2);
            player5.drawPlayerG2(g2);
            player6.drawPlayerG2(g2);
            
            if(player1.getSize()<=0){
            state = STATE.LOSE;
            }
            /* if(p.countHalve() == 1)
                        {
                            SonPlayer1.drawPlayer(g2);
                        }
                        
                        if(p.countHalve() == 2)
                        {
                            SonPlayer2.drawPlayer(g2);
                        }
                        
                        if(p.countHalve() == 3)
                        {
                            SonPlayer3.drawPlayer(g2);
                        }
                        
                        if(p.countHalve() == 4)
                        {
                            SonPlayer4.drawPlayer(g2);
                        }
                        
                        if(p.countHalve() == 5)
                        {
                            SonPlayer5.drawPlayer(g2);
                        }
                        
                        if(p.countHalve() == 6)
                        {
                            SonPlayer6.drawPlayer(g2);
                        }*/
            pointPlayer1 = new Point((int) (player1.getX() - player1.getPlayer().width - 100), (int) (player1.getY() - player1.getPlayer().height - 100));
            menu.setPoint(pointPlayer1);
//            increaseSize();
            didBallIntersect();
            printInfoBall(g2);
            printClassifica(g2);
            whoWon();
            g2.draw(Bordo);
            g2.draw(outerArea);
            g2.dispose();
        } else if (state == STATE.WIN) {
            menu.player1Won(g2);
        } else if (state == STATE.LOSE) {
            menu.player2Won(g2);
        }
    }
    
    /**
     @brief Vincita
     * 
     * @param player1
     * @param player2
     * @param player3
     * @param player4
     * @param player5
     * @param player6
     * @param height
     * @param state
     */

    public void whoWon() {
//        if(player2.getPlayer().height==0 && player3.getPlayer().height==0 && player4.getPlayer().height==0 && player5.getPlayer().height==0){
//            state = STATE.WIN;
//        }
//        else if(player1.getPlayer().height==0){
//            state = STATE.LOSE;
//        }
        if (player1.getPlayer().height > player2.getPlayer().height && player1.getPlayer().getBounds().intersects(player2.getPlayer().getBounds())) {
            state = STATE.WIN;
        } else if (player1.getPlayer().height < player2.getPlayer().height && player1.getPlayer().getBounds().intersects(player2.getPlayer().getBounds())) {
            state = STATE.LOSE;
        } else if (player1.getPlayer().height < player3.getPlayer().height && player1.getPlayer().getBounds().intersects(player3.getPlayer().getBounds())) {
            state = STATE.LOSE;
        } else if (player1.getPlayer().height < player4.getPlayer().height && player1.getPlayer().getBounds().intersects(player4.getPlayer().getBounds())) {
            state = STATE.LOSE;
        } else if (player1.getPlayer().height < player5.getPlayer().height && player1.getPlayer().getBounds().intersects(player5.getPlayer().getBounds())) {
            state = STATE.LOSE;
        } else if (player1.getPlayer().height < player6.getPlayer().height && player1.getPlayer().getBounds().intersects(player6.getPlayer().getBounds())) {
            state = STATE.LOSE;
        }
    }
    
    /**
     @brief Quando si mangia una Sfera
     * 
     * @param food
     * @param length
     * @param poison
     * @param player1
     * @param player2
     * @param player3
     * @param player4
     * @param player5
     * @param player6
     */

//    public void increaseSize(){
//        if(player1.getSize()>player2.getSize() && player1.getPlayer().getBounds().intersects(player2.getPlayer().getBounds() ) ){
//            player1.setSize(player1.getSize()+player2.getSize());
//            player1.setVelocity(player1.getVelocity()-0.03);
//            player2.setSize(0);
//        }
//        else if(player1.getSize()>player3.getSize() && player1.getPlayer().getBounds().intersects(player3.getPlayer().getBounds() ) ){
//            player1.setSize(player1.getSize()+player3.getSize());
//            player1.setVelocity(player1.getVelocity()-0.03);
//            player3.setSize(0);
//        }
//        else if(player1.getSize()>player4.getSize() && player1.getPlayer().getBounds().intersects(player4.getPlayer().getBounds() ) ){
//            player1.setSize(player1.getSize()+player4.getSize());
//            player1.setVelocity(player1.getVelocity()-0.03);
//            player4.setSize(0);
//        }
//        else if(player1.getSize()>player5.getSize() && player1.getPlayer().getBounds().intersects(player5.getPlayer().getBounds() ) ){
//            player1.setSize(player1.getSize()+player5.getSize());
//            player1.setVelocity(player1.getVelocity()-0.03);
//            player5.setSize(0);
//        }
//    }
    
    public void didBallIntersect() {
        for (int i = 0; i < food.getFoods().length; i++) {
            if (food.getFoods()[i] != null && player1.getPlayer().getBounds().intersects(food.getFoods()[i].getBounds())) {
                //food.getFoods()[i] = null;
                food.setFoodElement(i, null);
                food.addNoFoodPos(i);
                player1.increaseSize();
            }
        }
        for (int i = 0; i < food.getFoods().length; i++) {
            if (food.getFoods()[i] != null && player2.getPlayer().getBounds().intersects(food.getFoods()[i].getBounds())) {
                food.setFoodElement(i, null);
                food.addNoFoodPos(i);
                player2.increaseSize();
            }
        }
        for (int i = 0; i < poison.getPoisons().length; i++) {
            if (poison.getPoisons()[i] != null && player1.getPlayer().getBounds().intersects(poison.getPoisons()[i].getBounds())) {
                //poison.getPoisons()[i]=null;
                poison.setPoisonElement(i, null);
                poison.addNoPoisonPos(i);
                player1.decreaseSize();
            }
        }
        for (int i = 0; i < poison.getPoisons().length; i++) {
            if (poison.getPoisons()[i] != null && player2.getPlayer().getBounds().intersects(poison.getPoisons()[i].getBounds())) {
                poison.getPoisons()[i] = null;
                player2.decreaseSize();
            }
        }
        for (int i = 0; i < food.getFoods().length; i++) {
            if (food.getFoods()[i] != null && player3.getPlayer().getBounds().intersects(food.getFoods()[i].getBounds())) {
                //food.getFoods()[i] = null;
                food.setFoodElement(i, null);
                food.addNoFoodPos(i);
                player3.increaseSize();
            }
        }
        for (int i = 0; i < food.getFoods().length; i++) {
            if (food.getFoods()[i] != null && player4.getPlayer().getBounds().intersects(food.getFoods()[i].getBounds())) {
                food.setFoodElement(i, null);
                food.addNoFoodPos(i);
                player4.increaseSize();
            }
        }
        for (int i = 0; i < poison.getPoisons().length; i++) {
            if (poison.getPoisons()[i] != null && player3.getPlayer().getBounds().intersects(poison.getPoisons()[i].getBounds())) {
                //poison.getPoisons()[i]=null;
                poison.setPoisonElement(i, null);
                poison.addNoPoisonPos(i);
                player3.decreaseSize();
            }
        }
        for (int i = 0; i < poison.getPoisons().length; i++) {
            if (poison.getPoisons()[i] != null && player4.getPlayer().getBounds().intersects(poison.getPoisons()[i].getBounds())) {
                poison.getPoisons()[i] = null;
                player4.decreaseSize();
            }
        }
        for (int i = 0; i < food.getFoods().length; i++) {
            if (food.getFoods()[i] != null && player5.getPlayer().getBounds().intersects(food.getFoods()[i].getBounds())) {
                food.setFoodElement(i, null);
                food.addNoFoodPos(i);
                player5.increaseSize();
            }
        }
        for (int i = 0; i < poison.getPoisons().length; i++) {
            if (poison.getPoisons()[i] != null && player5.getPlayer().getBounds().intersects(poison.getPoisons()[i].getBounds())) {
                //poison.getPoisons()[i]=null;
                poison.setPoisonElement(i, null);
                poison.addNoPoisonPos(i);
                player5.decreaseSize();
            }
        }
        for (int i = 0; i < food.getFoods().length; i++) {
            if (food.getFoods()[i] != null && player6.getPlayer().getBounds().intersects(food.getFoods()[i].getBounds())) {
                food.setFoodElement(i, null);
                food.addNoFoodPos(i);
                player6.increaseSize();
            }
        }
        for (int i = 0; i < poison.getPoisons().length; i++) {
            if (poison.getPoisons()[i] != null && player6.getPlayer().getBounds().intersects(poison.getPoisons()[i].getBounds())) {
                //poison.getPoisons()[i]=null;
                poison.setPoisonElement(i, null);
                poison.addNoPoisonPos(i);
                player6.decreaseSize();
            }
        }
    }
    
    /**
     @brief Comunica a video le informazioni della Sfera Avatar
     * 
     * @param a
     * @param font
     * @param height
     * @param player1
     */

    public void printInfoBall(Graphics2D g2) {
        g2.setColor(Color.ORANGE);
        double a = TimeUnit.SECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
        Font font = new Font("arial", Font.BOLD, 15);
        g2.setFont(font);
        /*
        g2.drawString("SPEED: " + new DecimalFormat("##.##").format(player1.getVelocity()), (int) (player1.getX() - 940 + (int) player1.getPlayer().height / 2), (int) (player1.getY() - 500 + (int) player1.getPlayer().height / 2)); // 950 520
        g2.drawString("RADIUS OF BALL: " + Math.floor(player1.getPlayer().height), (int) (player1.getX() - 940 + (int) player1.getPlayer().height / 2), (int) (player1.getY() - 480 + (int) player1.getPlayer().height / 2)); // 950 500
        g2.drawString("TIME: " + a, (int) (player1.getX() - 940 + (int) player1.getPlayer().height / 2), (int) (player1.getY() - 460 + (int) player1.getPlayer().height / 2)); // 950 480
        */
        g2.drawString("SPEED: " + new DecimalFormat("##.##").format(player1.getVelocity()), (int) (player1.getX() - 600 + (int) player1.getPlayer().height / 2), (int) (player1.getY() - 350 + (int) player1.getPlayer().height / 2)); // 950 520
        g2.drawString("RADIUS OF BALL: " + Math.floor(player1.getPlayer().height), (int) (player1.getX() - 600 + (int) player1.getPlayer().height / 2), (int) (player1.getY() - 330 + (int) player1.getPlayer().height / 2)); // 950 500
        g2.drawString("TIME: " + a, (int) (player1.getX() - 600 + (int) player1.getPlayer().height / 2), (int) (player1.getY() - 310 + (int) player1.getPlayer().height / 2)); // 950 480
        
    }
    
    /**
     @brief Utilizzo Mouse per il movimento
     * 
     * @param state
     * @param mousePosition
     * @param x
     * @param y
     * @param width
     * @param height
     * @param angle
     * @param player1
     * @param vPort
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (state == STATE.GAME) {
            Point mousePosition = getMousePosition();
            if (mousePosition == null) {
                return;
            }
            double dx = mousePosition.x - player1.getPlayer().x - player1.getPlayer().width / 2;
            double dy = mousePosition.y - player1.getPlayer().y - player1.getPlayer().height / 2;
            if (dx * dx + dy * dy > 12) {
                double angle = Math.atan2(dy, dx);
                if (mousePosition.getX() < player1.getPlayer().getBounds().getMinX() || mousePosition.getX() > player1.getPlayer().getBounds().getMaxX() || mousePosition.getY()
                        < player1.getPlayer().getBounds().getMinY() || mousePosition.getY() > player1.getPlayer().getBounds().getMaxY()) {
                    player1.getPlayer().x += (int) (player1.getVelocity() * Math.cos(angle));
                    player1.getPlayer().y += (int) (player1.getVelocity() * Math.sin(angle));
                    if (player1.getX() >= 6000) {
                        player1.getPlayer().x = 5998;
                    }
                    if (player1.getX() <= 1000) {
                        player1.getPlayer().x = 1002;
                    }
                    if (player1.getY() >= 6000) {
                        player1.getPlayer().y = 5998;
                    }
                    if (player1.getY() <= 1000) {
                        player1.getPlayer().y = 1002;
                    }
                    Point view = new Point((int) player1.getPlayer().getX() + (int) player1.getPlayer().height / 2 - WIDTH / 2, (int) player1.getPlayer().getY() + (int) player1.getPlayer().height / 2 - HEIGHT / 2);
                    // Point view = new Point((int)player1.getPlayer().x-WIDTH/2,(int)player1.getPlayer().y-HEIGHT/2);

                    if (player1.getX() >= 6000 || player1.getY() >= 6000 || player1.getX() <= 1000 || player1.getY() <= 1000) {

                    } else {
                        vPort.setViewPosition(view);
                    }
                }
            }
            
            String data = player1.getX() + ";" + player1.getY() + ";" +player1.getSize();
            String c = client.trasmeti(data);
            String[] c1 = c.split(";");
            double posx1 = Double.parseDouble(c1[0]);
            double posy1 = Double.parseDouble(c1[1]);
            double size1 = Double.parseDouble(c1[2]);
            
             player2.setX(posx1);
             player2.setX(posy1);
             player2.setSize(size1);
            
            double posx2 =Double.parseDouble(c1[3]);
            double posy2 = Double.parseDouble(c1[4]);
            double size2 = Double.parseDouble(c1[5]);
            
            player3.setX(posx2);
             player3.setX(posy2);
             player3.setSize(size2);
            
           double posx3 = Double.parseDouble(c1[6]);
            double posy3 = Double.parseDouble(c1[7]);
            double size3 = Double.parseDouble(c1[8]);
            
            player4.setX(posx3);
             player4.setX(posy3);
             player4.setSize(size3);
            
            double posx4 = Double.parseDouble(c1[9]);
            double posy4 = Double.parseDouble(c1[10]);
           double size4 = Double.parseDouble(c1[11]);
            
            player5.setX(posx4);
             player5.setX(posy4);
             player5.setSize(size4);
            
            double posx5 = Double.parseDouble(c1[12]);
            double posy5 = Double.parseDouble(c1[13]);
            double size5= Double.parseDouble(c1[14]);
            
            player6.setX(posx5);
             player6.setX(posy5);
             player6.setSize(size5);
             repaint();
        }
    }
    
    /**
     @brief Getter Player1
     * 
     * @param Player1
     */

    public Players getPlayer1() {
        return player1;
    }
    
    /**
     @brief Setter Player1
     * 
     * @param Player1
     */

    public void setPlayer1(Players player1) {
        this.player1 = player1;
    }
    
    /**
     @brief Getter Player2
     * 
     * @param Player2
     */

    public Players getPlayer2() {
        return player2;
    }
    
    /**
     @brief Setter Player2
     * 
     * @param Player2
     */

    public void setPlayer2(Players player2) {
        this.player2 = player2;
    }
    
    /**
     @brief Getter Food
     * 
     * @param food
     */

    public Foods getFood() {
        return food;
    }
    
    /**
     @brief Setter Food
     * 
     * @param food
     */

    public void setFood(Foods food) {
        this.food = food;
    }
    
    /**
     @brief Getter Poison
     * 
     * @param poison
     */

    public Poisons getPoison() {
        return poison;
    }

    /**
     @brief Setter Poison
     * 
     * @param poison
     */
    
    public void setPoison(Poisons poison) {
        this.poison = poison;
    }
    
    /**
     @brief Ridisegna
     * 
     */

    public void Repaint() {
        repaint();
    }
    
    /**
     @brief Getter Players
     * 
     * @param s
     * @param player2
     * @param player3
     * @param player4
     * @param player5
     * @param player6
     * @param aa
     */

    public int[] getplayer(int s) {
        int[] aa = new int[3];
        if (s == 2) {
            aa[1] = (int) player2.getX();
            aa[2] = (int) player2.getY();
        } else if (s == 3) {
            aa[1] = (int) player3.getX();
            aa[2] = (int) player3.getY();

        } else if (s == 4) {
            aa[1] = (int) player4.getX();
            aa[2] = (int) player4.getY();

        } else if (s == 5) {
            aa[1] = (int) player5.getX();
            aa[2] = (int) player5.getY();

        } else if (s == 6) {
            aa[1] = (int) player6.getX();
            aa[2] = (int) player6.getY();
        }
        return aa;
    }
    
    /**
     @brief Getter Size
     * 
     * @param s
     * @param player2
     * @param player3
     * @param player4
     * @param player5
     * @param player6
     * @param aa
     */

    public int getsize(int s) {
        int aa = 0;
        if (s == 2) {
            aa = (int) player2.getSize();
        } else if (s == 3) {
            aa = (int) player3.getSize();

        } else if (s == 4) {
            aa = (int) player4.getSize();

        } else if (s == 5) {
            aa = (int) player5.getSize();

        } else if (s == 6) {
            aa = (int) player6.getSize();
        }
        return aa;
    }

    /**
     @brief Disegna a video la Classifica
     * 
     * @param classifica
     * @param id
     * @param Color
     * @param font
     * @param length
     * @param height
     * @param player1
     */
    
    public void printClassifica(Graphics2D g2) {
        double[] classifica = tClassifica.getClassifica();
        int[] id = tClassifica.getIdentifiers();
        g2.setColor(Color.ORANGE);
        Font font = new Font("arial", Font.BOLD, 15);
        g2.setFont(font);
        /*
        g2.drawString("CLASSIFICA: ", (int) (player1.getX() + 740 + player1.getPlayer().height / 2), (int) (player1.getY() - 490 + player1.getPlayer().height / 2));
        for (int i = 0; i < classifica.length; i++) {
            g2.drawString("Player " + id[i] + " = " + classifica[i], (int) (player1.getX() + 740 + player1.getPlayer().height / 2), (int) (player1.getY() - 470 + i * 30 + player1.getPlayer().height / 2));
        }
        */
        g2.drawString("CLASSIFICA: ", (int) (player1.getX() + 500 + player1.getPlayer().height / 2), (int) (player1.getY() - 350 + player1.getPlayer().height / 2));
        for (int i = 0; i < classifica.length; i++) {
            g2.drawString("Player " + id[i] + " = " + classifica[i], (int) (player1.getX() + 500 + player1.getPlayer().height / 2), (int) (player1.getY() - 320 + i * 30 + player1.getPlayer().height / 2));
        }
    }

}
