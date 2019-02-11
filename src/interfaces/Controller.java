package interfaces;

import classes.AFN;

import java.awt.event.MouseEvent;

public class Controller {

    public void onCrearAFNButtonClicked(MouseEvent event) {
        AFN f = new AFN();
        f.crearBasico('A');
        System.out.println(f.imprimeAFN());
    }

}
