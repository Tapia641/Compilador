import classes.AFN;

public class Main {

    public static void main(String[] args) {
        System.out.println("Compilando...");

        AFN f1, f2, f3;
        f1 = new AFN();
        f2 = new AFN();
        f3 = new AFN();

        f1.crearBasico('A');
        f2.crearBasico('B');
        f3.crearBasico('C');

        //f1.Unir(f2);
        f1.imprimeAFN();
        f2.imprimeAFN();
        f3.imprimeAFN();

    }

}
