/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agario;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ricca
 */
public class ThreadClassifica extends Thread {

    private Players p;
    private ThreadBot[] vBot;
    private double[] sizes;
    private DisplayGame dg;

    public ThreadClassifica(Players p, ThreadBot[] vBot, DisplayGame dg) {
        this.p = p;
        this.vBot = vBot;
        sizes = new double[vBot.length + 1];
        this.dg = dg;
    }

    public void run() {
        int i;
        double temp;
        while (true) {
            synchronized (sizes) {
                for (i = 0; i < sizes.length - 1; i++) {
                    sizes[i] = vBot[i].getSize();
                }
                sizes[i] = p.getSize();

                for (int j = 0; j < sizes.length - 1; j++) {
                    for (int k = j + 1; k < sizes.length; k++) {
                        if (sizes[j] < sizes[k]) { // vogliamo la classifica con il primo in posizione 0
                            temp = sizes[j];
                            sizes[j] = sizes[k];
                            sizes[k] = temp;
                        }
                    }
                }
            }

            System.out.println("CLASSIFICA");
            for (int j = 0; j < sizes.length; j++) {
                System.out.println(sizes[j]);
            }
            System.out.println("");
            System.out.println("");

            dg.Repaint();
            try {
                sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadClassifica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public double[] getClassifica() {
        synchronized (sizes) {
            return sizes;
        }
    }

}
