package LR;

import java.util.*;

public class RulesMap {
    private final HashMap<String, LinkedList<LinkedList<Nodo>>> mapa;
    private final HashSet<Nodo> tnSimbolsSet = new HashSet<>();

    public RulesMap() {
        mapa = new LinkedHashMap<>();
    }

    public RulesMap(RulesMap r) {
        mapa = r.getRulesMap();
    }

    public void setRule(Nodo n, LinkedList<LinkedList<Nodo>> L) {
        mapa.put(n.getSimbolo(), L);
    }

    public HashMap<String, LinkedList<LinkedList<Nodo>>> getRulesMap() {
        return mapa;
    }


    public LinkedList<LinkedList<Nodo>> getDerivatedRules(Nodo n) {

        return mapa.get(n.getSimbolo());

    }

    public LinkedList<LinkedList<Nodo>> getDerivatedRules(String n) {

        return mapa.get(n);

    }


    public void UnionMap(RulesMap m) {
        mapa.putAll(m.getRulesMap());
    }

    public HashSet<Nodo> getTnSimbolsSet() {
        return tnSimbolsSet;
    }

    public void setNodeToSet(Nodo n) {
        this.tnSimbolsSet.add(n);
    }

    public void printRulesMap() {
        Iterator it = mapa.keySet().iterator();
        System.out.println("---------------Mapa de reglas---------------------");
        while (it.hasNext()) {
            String ladoIzquierdo = (String) it.next();
            System.out.println("\nDerivaciones del lado izquierdo " + ladoIzquierdo + " :\n");
            LinkedList<LinkedList<Nodo>> LadoDerecho = this.mapa.get(ladoIzquierdo);
            LadoDerecho.forEach(d -> {
                System.out.println();
                Nodo f = d.removeFirst();
                System.out.println(f.getSimbolo() + " --> ");
                d.forEach(n -> {
                    System.out.println("    " + n.getSimbolo() + " " + n.getIsTerminal());
                });
                d.addFirst(f);
                System.out.println();
            });

        }
    }

}
