import classes.AFN;

public class Main {

    public static void main(String[] args) {
        System.out.println("Compilando...");

        AFN f1, f2, f3;
        f1 = new AFN();
        f2 = new AFN();
        f3 = new AFN();

        System.out.println("Creando autómatas básicos...");
        f1.crearBasico('A');
        f2.crearBasico('B');

        System.out.println("Imprimiendo...");
        f1.imprimeAFN();
        f2.imprimeAFN();

        System.out.println("Uniendo f1 con f2...");
        f1.Unir(f2);
        f1.imprimeAFN();

        f2.crearBasico('C');
        f2.imprimeAFN();

    }

}
