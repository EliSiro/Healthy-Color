/**
 * @author  Zonca Tommaso
 * @author Pirovano Yuri
 * @author Lettiero Riccardo
 * @version 0.1
 * @file ComandiAggiuntivi.java
 *
 * @brief File per la gestione i comandi aggiuntivi (W end SpaceBar)
 *
 */



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agario;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;

/**
 * @class ComandiAggiuntivi.java
 *
 * @brief Costruttore Default con implementazione a KeyListener (Ascolto di input)
 *
 */
public class ComandiAggiuntivi implements KeyListener {

    DisplayGame g;
    Players p;
    Poisons poison;
    Foods f;
    public ComandiAggiuntivi(DisplayGame g, Players p, Poisons poison, Foods f) {
        this.g = g;
        this.p = p;
        this.poison = poison;
        this.f=f;
    }

     /**
     @brief KeyPressed, Ascolto di input (W and SpaceBar)
     * 
     * @param KeyEvent e
     */
    
    @Override
    public void keyPressed(KeyEvent e) { // spazio per dividersi e w per dare una parte della tua massa
        /*if (e.getKeyCode() == KeyEvent.VK_SPACE) //se premo spazio
        {
            p.halveSizeFather();
            p.countHalve();
                                
        }*/
        if (e.getKeyCode() == KeyEvent.VK_W)// se premo w
        {
            if(p.getSize()-30>10){
            p.decreaseSizew();
            f.w((int)p.getX(), (int)p.getY());
            }
        }
    }
    
    /**
     @brief keyTyped
     * 
     * @param KeyEvent e
     */

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    /**
     @brief keyReleased
     * 
     * @param KeyEvent e
     */

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
