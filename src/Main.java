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

        f1.Unir(f2);
        ImprimeAFN(f1);
        ImprimeAFN(f2);
        ImprimeAFN(f3);

    }

    public static void ImprimeAFN(AFN f) {
        System.out.println("Estados: " + f.EstadosAFN);
        System.out.println("Estado inicial: " + f.EstadoAFNInicial);
        System.out.println("Estados de aceptacion" + f.EstadosAceptacion);
        System.out.println("Alfabeto: " + f.Alfabeto);
        System.out.println("Epsilon: " + f.getEpsilon() + "\n");

    }
}
