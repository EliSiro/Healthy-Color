/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agario;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Math;

/**
 *
 * @author ricca
 */
public class ThreadBot extends Thread {

    private double size;
    private Players p;
    private DisplayGame c;
    int xx = 0;
    int yy = 0;
    int id = 0;
    int N = 6;
    int pos[] = new int[2];
    int dd = 0;

    ThreadBot(Players p1, int id, DisplayGame c) {
        //size=25;
        p = p1;
        this.id = id;
        this.c = c;
    }

    public void run() {
        int posizionenemx = 0;
        int posizionenemy = 0;
        while (true) {
            try {
                Thread.sleep(1000);
                xx = (int) (Math.random() * (20 - (-20) + 1) - 20);
                yy = (int) (Math.random() * (20 - (-20) + 1) - 20);
                p.setX(p.getX() - xx);
                p.setY(p.getY() - yy);

            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadBot.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int i = 2; i <= N; i++) {
                if (id != i) {
                    dd = (int) p.getSize() * 3;
                    pos = c.getplayer(i);
                    if (Math.sqrt((pos[1] - (int) p.getX()) * (pos[1] - (int) p.getX()) + (pos[2] - (int) p.getY()) * (pos[2] - (int) p.getY())) < dd) {
                        if (c.getsize(i) > p.getSize()) {
                            posizionenemx = pos[1] - (int) p.getX();
                            posizionenemy = pos[1] - (int) p.getX();
                            if (posizionenemx < 0) {
                                p.setX(p.getX() - 10);
                                p.setY(p.getY() - 10);
                            }
                        } else {
                            posizionenemx = pos[1] - (int) p.getX();
                            posizionenemy = pos[1] - (int) p.getX();
                            if (posizionenemx < 0) {
                                p.setX(p.getX() + 10);
                                p.setY(p.getY() + 10);
                            }
                        }
                    }
                }
            }
        }
    }

    /* Random rn=new Random();//estraggo un numero random(0=+ , 1=-)
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
        }*/
    public double getSize() {
        return size;
    }

}
