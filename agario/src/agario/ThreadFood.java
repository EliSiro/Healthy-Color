/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agario;

import java.awt.geom.Ellipse2D;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ricca
 */
public class ThreadFood extends Thread {
    private Foods food;
    
    ThreadFood(Foods food) {
        this.food = food;
    }
    
    public void run() {
        // ottenute le posizioni in cui manca cibo, ripristina quella posizione con un cibo piazzato in maniera random
        Random rn=new Random();
        int pos=-1;
        Ellipse2D.Double cibo=null;
        //int cont = 0;
        while(true) {
            pos=food.getFirstNoFoodPos();
            if(pos != -1) {
                cibo = new Ellipse2D.Double(rn.nextInt(4000), rn.nextInt(3000), 9.3, 9.3); // cibo random
                //cibo = new Ellipse2D.Double(food.getX(), food.getY(), 9.3, 9.3); // cibo dove era prima
                food.setFoodElement(pos, cibo);
                //System.out.println("Ripristinato"+cont);
                //cont++;
            }
            
            try {
                sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadFood.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
