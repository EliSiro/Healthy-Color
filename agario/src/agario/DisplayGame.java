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

public class DisplayGame extends JPanel implements ActionListener{
        // servono un array/list di giocatori controllati dalla CPU (= ogni gioatore deve essere un thread)
	private Rectangle outerArea;
        private Rectangle Bordo;
	public static int WIDTH=1920;
	public static int HEIGHT=1080;
        private int currentWidth;
        private int currentHeitht;
	private int numoffoods=1000;
	public Players player1;
	private JViewport vPort;
	private Players player2;
        private Players player3;
        private Players player4;
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
        
        Players p;
        
        private ThreadBot[] vBot;
        private ThreadClassifica tClassifica;
        
	public static enum STATE{
		MENU,
		GAME,
		LOSE,
		WIN
	};
	public static STATE state=STATE.MENU;

	public DisplayGame(Frame f) {
		Timer timer=new Timer(1,this);
                time=System.nanoTime();
                this.f = f;
		menu= new Menu(this, f);
		addMouseListener(menu);
		setFocusable(true);
		requestFocusInWindow();
		player1 = new Players();
		player2 = new Players();   
                player3 = new Players(); 
                player4 = new Players(); 
                player2.isplayer = false;
                player3.isplayer = false;
                player4.isplayer = false;
                comandi = new ComandiAggiuntivi(this, player1, poison);
		poison = new Poisons(numoffoods/10);
		food= new Foods(numoffoods);
                tf = new ThreadFood(food);
                tp = new ThreadPoison(poison);
                tf.start();
                tp.start();
                

		Dimension newSize = new Dimension(7000,7000);
		outerArea= new Rectangle(0, 0, 7000, 7000);
                Bordo= new Rectangle(1000,1000,5040,5040);
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
            
            vBot = new ThreadBot[10];
            for(int i=0;i<10;i++){
                //vBot[i]=new ThreadBot();
                //vBot[i].start();
            }
                
            tClassifica = new ThreadClassifica(player1, vBot, this); // questo va modificato sulla base di come gestite i giocatori
            tClassifica.start();
	}	
	public void setvPort(JViewport vPort) {
		this.vPort = vPort;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
                //G2
		Graphics2D g2=(Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);               
                g2.drawImage(background, 1000, 1000, 5040, 5040, this);
                
                //G3
                Graphics2D g3=(Graphics2D)g;
		g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);               
                g3.drawImage(background, 1000, 1000, 5040, 5040, this);
                
                //G4
                Graphics2D g4=(Graphics2D)g;
		g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);               
                g4.drawImage(background, 1000, 1000, 5040, 5040, this);
                
                //G5
                Graphics2D g5=(Graphics2D)g;
		g5.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);               
                g5.drawImage(background, 1000, 1000, 5040, 5040, this);
                
                //G6
                Graphics2D g6=(Graphics2D)g;
		g6.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);               
                g6.drawImage(background, 1000, 1000, 5040, 5040, this);
                
                
		//setBackground(Color.GRAY);
		if(state==STATE.MENU){
			menu.render(g2);
		}
		else if(state==STATE.GAME){
			poison.drawPoisons(g2);
			food.drawFood(g2);
			player1.drawPlayerG2(g2);
                        
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
                        
			pointPlayer1= new Point((int)(player1.getX()-player1.getPlayer().width-100),(int)(player1.getY()-player1.getPlayer().height-100));
			menu.setPoint(pointPlayer1);
			didBallIntersect();
			printInfoBall(g2);
                        printClassifica(g2);
			whoWon();
                        g2.draw(Bordo);
			g2.draw(outerArea);
			g2.dispose();
		}
		else if(state==STATE.WIN){
			menu.player1Won(g2);
		}
		else if(state==STATE.LOSE){
			menu.player2Won(g2);
		}
	}
	public void whoWon(){
		if(player1.getPlayer().height>player2.getPlayer().height&&player1.getPlayer().getBounds().intersects(player2.getPlayer().getBounds())){
			state=STATE.WIN;
		}
		else if(player1.getPlayer().height<player2.getPlayer().height&&player1.getPlayer().getBounds().intersects(player2.getPlayer().getBounds())){
			state=STATE.LOSE;
		}
	}
	public void didBallIntersect(){
		for (int i = 0; i < food.getFoods().length; i++) {
			if(food.getFoods()[i]!=null && player1.getPlayer().getBounds().intersects(food.getFoods()[i].getBounds())){
				//food.getFoods()[i] = null;
                                food.setFoodElement(i, null);
                                food.addNoFoodPos(i);
				player1.increaseSize();
			}
		}
		for (int i = 0; i < food.getFoods().length; i++) {
			if(food.getFoods()[i]!=null && player2.getPlayer().getBounds().intersects(food.getFoods()[i].getBounds())){
				food.getFoods()[i] = null;
				player2.increaseSize();
			}
		}
		for (int i = 0; i < poison.getPoisons().length; i++) {
			if(poison.getPoisons()[i]!=null && player1.getPlayer().getBounds().intersects(poison.getPoisons()[i].getBounds())){
				//poison.getPoisons()[i]=null;
				poison.setPoisonElement(i, null);
                                poison.addNoPoisonPos(i);
                                player1.decreaseSize();
			}
		}
		for (int i = 0; i < poison.getPoisons().length; i++) {
			if(poison.getPoisons()[i]!=null && player2.getPlayer().getBounds().intersects(poison.getPoisons()[i].getBounds())){
				poison.getPoisons()[i]=null;
				player2.decreaseSize();
			}
		}	
	}
	public void printInfoBall(Graphics2D g2){
		g2.setColor(Color.ORANGE);
		double a=TimeUnit.SECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
		Font font= new Font("arial",Font.BOLD,15);
		g2.setFont(font);
		g2.drawString("SPEED: "+new DecimalFormat("##.##").format(player1.getVelocity()),(int)(player1.getX()-940), (int)(player1.getY()-500)); // 950 520
		g2.drawString("RADIUS OF BALL: "+Math.floor(player1.getPlayer().height),(int)(player1.getX()-940), (int)(player1.getY()-480)); // 950 500
		g2.drawString("TIME: "+a, (int)(player1.getX()-940),  (int)(player1.getY()-460)); // 950 480
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(state==STATE.GAME){
			Point mousePosition=getMousePosition();
			if(mousePosition==null)return;
			double dx = mousePosition.x - player1.getPlayer().x - player1.getPlayer().width/2;
			double dy = mousePosition.y - player1.getPlayer().y - player1.getPlayer().height/2;
			if(dx*dx+dy*dy >12){
				double angle=Math.atan2(dy, dx);
				if(mousePosition.getX()<player1.getPlayer().getBounds().getMinX()||mousePosition.getX()>player1.getPlayer().getBounds().getMaxX()||mousePosition.getY()<
                                        player1.getPlayer().getBounds().getMinY()||mousePosition.getY()>player1.getPlayer().getBounds().getMaxY()){
					player1.getPlayer().x+=(int)(player1.getVelocity()*Math.cos(angle));
                                        player1.getPlayer().y+=(int)(player1.getVelocity()*Math.sin(angle));
                                        if(player1.getX()>=6000)
                                        {
                                            player1.getPlayer().x = 5998;
                                        }
                                        if(player1.getX()<=1000)
                                        {
                                            player1.getPlayer().x = 1002;
                                        }
                                        if(player1.getY()>=6000)
                                        {
                                            player1.getPlayer().y = 5998;
                                        }
                                        if(player1.getY()<=1000)
                                        {
                                            player1.getPlayer().y = 1002;
                                        }
                                        Point view = new Point((int)player1.getPlayer().getX()+(int)player1.getPlayer().height/2-WIDTH/2,(int)player1.getPlayer().getY()+(int)player1.getPlayer().height/2-HEIGHT/2);
                                      // Point view = new Point((int)player1.getPlayer().x-WIDTH/2,(int)player1.getPlayer().y-HEIGHT/2);
                                        
                                        if(player1.getX()>=6000 || player1.getY()>=6000 || player1.getX()<=1000 || player1.getY()<=1000)
                                        {
                                            
                                        }
                                        else
                                        {
                                            vPort.setViewPosition(view);
                                        }
				}
			}		
			repaint();
		}
	}
	public Players getPlayer1() {
		return player1;
	}
	public void setPlayer1(Players player1) {
		this.player1 = player1;
	}
	public Players getPlayer2() {
		return player2;
	}
	public void setPlayer2(Players player2) {
		this.player2 = player2;
	}
	public Foods getFood() {
		return food;
	}
	public void setFood(Foods food) {
		this.food = food;
	}
	public Poisons getPoison() {
		return poison;
	}
	public void setPoison(Poisons poison) {
		this.poison = poison;
	}
        
        public void Repaint() {
            repaint();
        }
        
	public void printClassifica(Graphics2D g2){
                double[] classifica = tClassifica.getClassifica();
		g2.setColor(Color.ORANGE);
		Font font= new Font("arial",Font.BOLD,15);
		g2.setFont(font);
                g2.drawString("CLASSIFICA: ",(int)(player1.getX()+740), (int)(player1.getY()-500));
                for(int i=0; i < classifica.length; i++) {
                    g2.drawString(new DecimalFormat("##.##").format(classifica[i]),(int)(player1.getX()+740), (int)(player1.getY()-470+i*30));
                }
	}
        
}