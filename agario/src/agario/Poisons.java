package agario;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JViewport;

public class Poisons implements Serializable {
	private Ellipse2D.Double poisons[];
	private Color color = Color.GREEN;
        private ArrayList<Integer> noPoisonsPos;
	
	Poisons(int numofpoisons){
		poisons= new Ellipse2D.Double[numofpoisons];
                noPoisonsPos = new ArrayList<>();
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

    
    // metodo per aggiungere/settare a null un cibo
        public synchronized void setPoisonElement(int pos, Ellipse2D.Double veleno) {
            poisons[pos] = veleno;
            
        }
        
        // metodo per inserire una nuova posizione (nella lista) in cui manca cibo
        public synchronized void addNoPoisonPos(int pos) {
            noPoisonsPos.add(pos);
        }
        
        // metodo che ottiene il primo elemento della lista no cibo (la posizione del vettore food in cui manca cibo)
        public synchronized int getFirstNoPoisonPos() {
            if (!noPoisonsPos.isEmpty())
                return noPoisonsPos.remove(0);
            else
                return -1;
        }
        
}
