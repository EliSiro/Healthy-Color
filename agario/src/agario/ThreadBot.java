/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agario;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ricca
 */
public class ThreadBot extends Thread{
    private double size;
    
    ThreadBot(){
        size=25;
    }
    
    public void run(){
        Random rn=new Random();//estraggo un numero random(0=+ , 1=-)
        int random;
        while(true){
            random=rn.nextInt(2);//0 incluso, 2 escluso
            if(random==0){
                size++;
            }
            else{
                size--;
            }
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadBot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public double getSize() {
        return size;
    }
    
}
