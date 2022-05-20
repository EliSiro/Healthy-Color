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
    int pos[] = new int[3];
    int dd = 0;

    ThreadBot(Players p1, int id, DisplayGame c) {
        //size=25;
        p = p1;
        this.id = id;
        this.c = c;
    }

    public void run() { // non legge gli sleep non capisco il perche
        int posizionenemx = 0;
        int posizionenemy = 0;
        while (true) {
            for (int i = 2; i <= N; i++) {
                if (id != i) {
                    dd = (int) p.getSize() * 3;
                    pos = c.getplayer(i);
                    if (Math.sqrt((pos[1] - (int) p.getX()) * (pos[1] - (int) p.getX()) + (pos[2] - (int) p.getY()) * (pos[2] - (int) p.getY())) < dd) {
                        if (c.getsize(i) > p.getSize()) {
                            try {
                                sleep(10000);
                                posizionenemx = pos[1] - (int) p.getX();
                                posizionenemy = pos[1] - (int) p.getX();
                                if (posizionenemx < 0) {
                                    p.setX(p.getX() - 10);
                                    p.setY(p.getY() - 10);
                                } else {
                                    p.setX(p.getX() + 10);
                                    p.setY(p.getY() + 10);
                                }
                            } catch (InterruptedException ex) {
                                Logger.getLogger(ThreadBot.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } else {
                            try {
                                sleep(10000);
                                posizionenemx = pos[1] - (int) p.getX();
                                posizionenemy = pos[1] - (int) p.getX();
                                if (posizionenemx < 0) {
                                    p.setX(p.getX() + 10);
                                    p.setY(p.getY() + 10);
                                } else {
                                    p.setX(p.getX() - 10);
                                    p.setY(p.getY() - 10);
                                }
                            } catch (InterruptedException ex) {
                                Logger.getLogger(ThreadBot.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    } else {
                        try {
                            sleep(10000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ThreadBot.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        xx = (int) (Math.random() * (10 - (-10) + 1) - 10);
                        yy = (int) (Math.random() * (10 - (-10) + 1) - 10);
                        p.setX(p.getX() - xx);
                        p.setY(p.getY() - yy);
                    }
                }
                if (p.getX() >= 6000) {
                    p.getPlayer().x = 5998;
                }
                if (p.getX() <= 1000) {
                    p.getPlayer().x = 1002;
                }
                if (p.getY() >= 6000) {
                    p.getPlayer().y = 5998;
                }
                if (p.getY() <= 1000) {
                    p.getPlayer().y = 1002;
                }
                size = p.getSize();
            }

        }
    }

    public double getSize() {
        return size;
    }

}
