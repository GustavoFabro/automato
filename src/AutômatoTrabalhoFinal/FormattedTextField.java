package AutômatoTrabalhoFinal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

/**
 *
 * @author gustavo
 */

public class FormattedTextField extends JTextField {
    //modo E - Somente letras maiusculas
    //modo A - Números e letras minusculas
    
    public FormattedTextField(String modo) {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch(modo){
                    case "E": if (("" + e.getKeyChar()).matches("[0-9]")){
                                e.consume(); 
                              }else
                                e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
                    break;
                    case "A": e.setKeyChar(Character.toLowerCase(e.getKeyChar()));
                    break;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    
   
}
