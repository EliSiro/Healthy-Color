package agario;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Foods implements Serializable {
	
        // creare metodo ad hoc per ripristinare un cibo che è stato mangiato
        private Ellipse2D.Double foods[];
	private Color foodColors[];
        private ArrayList<Integer> noFoodPos;
	
	Foods(int numoffoods){
		foods = new Ellipse2D.Double[numoffoods];
		foodColors = new Color[numoffoods];
                noFoodPos = new ArrayList<>();
		callOnce();
	}
	public void callOnce(){
		randomFoodColorInitializer();
		initializeFoods();
	}
	public void randomFoodColorInitializer(){
		Random a=new Random();
		for (int i = 0; i < foodColors.length; i++) {
			foodColors[i]=new Color(a.nextInt(255),a.nextInt(255),a.nextInt(255));
		}

	}
	public void drawFood(Graphics2D g2){

		for (int i = 0; i < foods.length; i++) {
			if(foods[i]!=null){
			g2.setColor(foodColors[i]);
			g2.fill(foods[i]);
			}
		}
	}
	public void initializeFoods(){
		Random a=new Random();
		for (int i = 0; i < foods.length; i++) {
			foods[i]=new Ellipse2D.Double(a.nextInt(4000), a.nextInt(3000), 9.3, 9.3);
			}
	}
	public Ellipse2D.Double[] getFoods() {
		return foods;
	}
	public void setFoods(Ellipse2D.Double[] foods) {
		this.foods = foods;
	}
	public Color[] getFoodColors() {
		return foodColors;
	}
	public void setFoodColors(Color[] foodColors) {
		this.foodColors = foodColors;
	}

        // metodo per aggiungere/settare a null un cibo
        public synchronized void setFoodElement(int pos, Ellipse2D.Double cibo) {
            foods[pos] = cibo;
            
        }
        
        // metodo per inserire una nuova posizione (nella lista) in cui manca cibo
        public synchronized void addNoFoodPos(int pos) {
            noFoodPos.add(pos);
        }
        
        // metodo che ottiene il primo elemento della lista no cibo (la posizione del vettore food in cui manca cibo)
        public synchronized int getFirstNoFoodPos() {
            if (!noFoodPos.isEmpty())
                return noFoodPos.remove(0);
            else
                return -1;
        }
        
}
