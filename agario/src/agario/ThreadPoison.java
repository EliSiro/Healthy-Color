/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agario;

import java.awt.geom.Ellipse2D;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ricca
 */
public class ThreadPoison extends Thread {
    private Poisons poison;
    
    ThreadPoison(Poisons poison) {
        this.poison = poison;
    }
    
    public void run() {
        // ottenute le posizioni in cui manca cibo, ripristina quella posizione con un cibo piazzato in maniera random
        Random rn=new Random();
        int pos=-1;
        Ellipse2D.Double ps=null;
        //int cont = 0;
        while(true) {
            pos=poison.getFirstNoPoisonPos();
            if(pos != -1) {
                ps = new Ellipse2D.Double(rn.nextInt(4000), rn.nextInt(3000), 40, 40); // veleno random
                //ps = new Ellipse2D.Double(poison.getX(), poison.getY(), 40, 40); // veleno dove era prima
                poison.setPoisonElement(pos, ps);
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
