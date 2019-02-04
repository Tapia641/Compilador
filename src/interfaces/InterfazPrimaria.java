package interfaces;

import classes.AFN;

import javax.swing.*;
import java.awt.event.*;

public class InterfazPrimaria extends JDialog {
    private JPanel contentPane;
    private JTextArea Salida;
    private JComboBox comboBox1;
    private JButton crearAutomataButton;

    public InterfazPrimaria() {
        setContentPane(contentPane);
        setModal(true);
    }

    public void Operaciones() {
        /*
        AFN f1, f2;
        f1 = new AFN();
        f2 = new AFN();

        f1.crearBasico('A');
        f2.crearBasico('B');

        System.out.println("Imprimiendo...");
        Salida.setText(f1.imprimeAFN());
        Salida.setText(f2.imprimeAFN());
        //f1.imprimeAFN();
        //f2.imprimeAFN();
        Salida.setText("Uniendo f1 con f2...");

        f1.Unir(f2);
        //f1.imprimeAFN();

        Salida.setText(f1.imprimeAFN());
        */
    }


}
