package LR;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class SintaxGramaticExpresion {

    private LinkedList<Token> tokens = new LinkedList<>();
    private LinkedList<LinkedList<Nodo>> LLn = new LinkedList<>();
    private HashMap<String, LinkedList<LinkedList<Nodo>>> mapa = new LinkedHashMap<>();
    private RulesMap rm = new RulesMap();
    private HashSet<String> vt = new HashSet<>();
    private boolean iscorrect = true; //Me indicará si la cadena analizada es correcta sintacticamente

    public SintaxGramaticExpresion(LinkedList<Token> t, HashSet<String> vt) {
        this.tokens = t;
        this.vt = vt;
    }

    private boolean G(RulesMap rm) {
        if (ListaReglas(rm))
            return true;
        return false;
    }

    private boolean ListaReglas(RulesMap rm) {
        if (Regla(rm)) {
            Token t = tokens.removeFirst();
            if (t.getTok() == Tokens.PUNTO_COMA) {
                if (ListaReglasP(rm))
                    return true;
            }
        }
        return false;
    }

    private boolean ListaReglasP(RulesMap rm) {
        LinkedList<Token> memory = new LinkedList<>();
        memory.addAll(this.tokens);
        Token t = null;
        if (Regla(rm)) {
            t = tokens.removeFirst();
            if (t.getTok() == Tokens.PUNTO_COMA) {
                if (ListaReglasP(rm))
                    return true;
            }
            return false;
        }
        this.tokens = memory;
        return true;
    }

    private boolean Regla(RulesMap rm) {
        Nodo n = new Nodo();
        LinkedList<LinkedList<Nodo>> L = new LinkedList<>();
        if (LadoIzquierdo(n)) {
            Token t = tokens.removeFirst();
            if (Tokens.FLECHA == t.getTok()) {
                if (ListaLadosDer(L, n)) {
                    rm.setRule(n, L);
                    return true;
                }
            }
        }
        return false;
    }


    private boolean LadoIzquierdo(Nodo n) {
        Token t = tokens.removeFirst();
        if (t.getTok() == Tokens.SIMBOLO) {
            n.setSimbolo(t.getLexema());
            n.setIsTerminal(false);
            return true;
        }
        return false;
    }

    private boolean ListaLadosDer(LinkedList<LinkedList<Nodo>> L, Nodo ni) {
        if (LadoDer(L, ni))
            if (ListaLadosDerP(L, ni))
                return true;
        return false;
    }

    private boolean ListaLadosDerP(LinkedList<LinkedList<Nodo>> L, Nodo ni) {
        Token t = tokens.removeFirst();
        if (t.getTok() == Tokens.OR) {
            if (LadoDer(L, ni)) {
                if (ListaLadosDerP(L, ni))
                    return true;
            }
            return false;
        }
        tokens.addFirst(t);
        return true;
    }

    private boolean LadoDer(LinkedList<LinkedList<Nodo>> L, Nodo ni) {
        Nodo n = new Nodo();
        LinkedList<Nodo> ln = new LinkedList<>();
        Token t = tokens.removeFirst();
        if (t.getTok() == Tokens.SIMBOLO) {
            n.setSimbolo(t.getLexema());
            if (this.vt.contains(t.getLexema()))
                n.setIsTerminal(true);
            else
                n.setIsTerminal(false);
            ln.add(ni);
            ln.add(n);
            if (LadoDerP(ln)) {
                L.add(ln);
                return true;
            }

        }
        return false;
    }

    private boolean LadoDerP(LinkedList<Nodo> ln) {
        Token t = tokens.removeFirst();
        Nodo n = new Nodo();
        if (t.getTok() == Tokens.SIMBOLO) {
            n.setSimbolo(t.getLexema());
            if (this.vt.contains(t.getLexema()))
                n.setIsTerminal(true);
            else
                n.setIsTerminal(false);
            ln.add(n);
            if (LadoDerP(ln))
                return true;
            return false;
        }
        tokens.addFirst(t);
        return true;
    }

    private boolean Analiza() {
        if (G(this.rm)) {
            Token t = tokens.removeFirst();
            if (t.getTok() == Tokens.FIN)
                return true;
        }
        return false;
    }

    public boolean SintaxAnalize() {
        if (Analiza())
            System.out.println("CADENA SINTÁCTICAMENTE CORRECTA");
        else {
            System.out.println("CADENA SINTÁCTICAMENTE INCORRECTA");
            this.iscorrect = false;
        }
        return this.iscorrect;

    }


    public void Evaluate() {
        if (this.iscorrect)
            this.rm.printRulesMap();
        else
            System.out.println("¡LA EVALUACIÓN NO ES POSIBLE!");
    }

    public RulesMap getRm() {
        return rm;
    }


}
