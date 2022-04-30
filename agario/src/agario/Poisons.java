package agario;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.Random;
import javax.swing.JViewport;

public class Poisons implements Serializable {
	private Ellipse2D.Double poisons[];
	private Color color = Color.GREEN;
	
	Poisons(int numofpoisons){
		poisons= new Ellipse2D.Double[numofpoisons];
		initializePoisons();
	}

	public void drawPoisons(Graphics2D g2){
		for (int i = 0; i < poisons.length; i++) {
			if(poisons[i]!=null){
			g2.setColor(color);
			g2.fill(poisons[i]);
			}
		}
	}
	public void initializePoisons(){
		Random a=new Random();

		for (int i = 0; i < poisons.length; i++) {
			poisons[i]=new Ellipse2D.Double(a.nextInt(4000), a.nextInt(3000),40, 40);
			}
	}

	public Ellipse2D.Double[] getPoisons() {
		return poisons;
	}

	public void setPoisons(Ellipse2D.Double[] poisons) {
		this.poisons = poisons;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

    void drawPoisons(JViewport vport) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
