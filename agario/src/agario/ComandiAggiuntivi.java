/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agario;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Sucone
 */
public class ComandiAggiuntivi implements KeyListener {

    DisplayGame g;
    Players p;
    Poisons poison;

    public ComandiAggiuntivi(DisplayGame g, Players p, Poisons poison) {
        this.g = g;
        this.p = p;
        this.poison = poison;
    }

    @Override
    public void keyPressed(KeyEvent e) { // spazio per dividersi e w per dare una parte della tua massa
        System.out.println(e);
        /*if (e.getKeyCode() == KeyEvent.VK_SPACE) //se premo spazio
        {
            p.halveSizeFather();
            p.countHalve();
            p.halveSizeSon();
                                
        }
        if (e.getKeyCode() == KeyEvent.VK_W)// se premo w
        {
        }*/
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
