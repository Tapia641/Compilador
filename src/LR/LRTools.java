
package LR;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.LinkedList;

public class LRTools {

    public static Nodo point = new Nodo(".", false);
    private final RulesMap mapa = new RulesMap();

    public static RulesMap makeIncreasedGrammar(Grammar G) {
        RulesMap rm = new RulesMap();
        Nodo n = new Nodo("┐", false); //Lado izquierdo
        LinkedList<LinkedList<Nodo>> l1 = new LinkedList<>(); //Lista de Lista de nodos (lado derecho)
        LinkedList<Nodo> l2 = new LinkedList<>();
        l2.add(new Nodo("┐", false));//lado izq E'
        l2.add(new Nodo(G.getAxioma(), false));//Nodo de un lado der E
        l1.add(l2);
        rm.setRule(n, l1);
        //Una vez creda la regla auemntada se une con las reglas de la gramatica original
        rm.UnionMap(G.getRulesMap());
        return rm;
    }

    public static LinkedList<Nodo> makeItem(LinkedList<Nodo> ladoDer) {
        LinkedList<Nodo> item = new LinkedList<>();
        item.addLast(ladoDer.getFirst());
        item.addLast(point);
        for (int i = 1; i < ladoDer.size(); i++)
            item.addLast(ladoDer.get(i));
        return item;
    }

    public static Nodo getNap(LinkedList<Nodo> f) {
        int index = f.indexOf(point); //índice del punto en el item
        return f.get(index + 1);//Símbolo después del punto
    }

    public static LinkedList<Nodo> moveIt(LinkedList<Nodo> item) {
        LinkedList<Nodo> R = item;
        int indexPoint = R.indexOf(point); //Índice donde se encuntra el punto en a regla de lado derecho                             
        Nodo punto = R.remove(indexPoint); //Removemos punto                    
        R.add(indexPoint + 1, punto); //Agregamos el punto depues de n
        return R;
    }

    public static LinkedList<Nodo> copy(LinkedList<Nodo> lista) {
        LinkedList<Nodo> it = new LinkedList<>();
        for (int i = 0; i < lista.size(); i++)
            it.addLast(lista.get(i));
        return it;
    }

    public static HashSet<LinkedList<Nodo>> movePoint(HashSet<LinkedList<Nodo>> item, String simbolo) {
        HashSet<LinkedList<Nodo>> R = new HashSet<>();
        item.forEach(i -> {
            if (!point.equals(i.getLast())) {//siempre que el punto no esté al final
                Nodo sap = LRTools.getNap(i);
                if (sap.getSimbolo().equals(simbolo)) {
                    LinkedList<Nodo> it = LRTools.copy(i);
                    R.add(LRTools.moveIt(it));
                }
            }
        });
        return R;
    }

    public static HashSet<Pair> movePointLR1(HashSet<Pair<LinkedList<Nodo>, HashSet<String>>> items, String s) {
        HashSet<Pair> R = new HashSet<>();
        items.forEach(it -> {
            if (!point.equals(it.getKey().getLast())) {//siempre que el punto no esté al final
                Nodo nap = LRTools.getNap(it.getKey());
                if (nap.getSimbolo().equals(s)) {
                    LinkedList<Nodo> newit = LRTools.copy(it.getKey());
                    R.add(new Pair(LRTools.moveIt(newit), it.getValue()));
                }
            }
        });
        return R;
    }


    public static LinkedList<LinkedList<Nodo>> NumberRules(RulesMap rm) {
        LinkedList<LinkedList<Nodo>> R = new LinkedList<>();
        rm.getRulesMap().entrySet().forEach(r -> {
            r.getValue().forEach(ladoDerecho -> {
                R.addLast(ladoDerecho);
            });
        });
        return R;
    }

    public static HashSet<Nodo> findSimbols(HashSet<LinkedList<Nodo>> items) {
        HashSet<Nodo> R = new HashSet<>();
        items.forEach(r -> {
            if (!r.getLast().equals(point)) {
                int indexPoint = r.indexOf(point); //Índice donde se encuntra el punto en a regla de lado derecho                             
                Nodo temp = r.get(indexPoint + 1);
                R.add(temp); //Agregamos el símbolo que hay después del punto
            }
        });
        return R;
    }

    public static HashSet<String> findSimbolsLR1(HashSet<Pair<LinkedList<Nodo>, HashSet<String>>> items) {
        HashSet<String> R = new HashSet<>();
        items.forEach(r -> {
            Pair<LinkedList<Nodo>, HashSet<String>> it = r;
            if (!it.getKey().getLast().equals(point)) {
                int indexPoint = it.getKey().indexOf(point); //Índice donde se encuntra el punto en a regla de lado derecho                             
                Nodo temp = it.getKey().get(indexPoint + 1);
                R.add(temp.getSimbolo()); //Agregamos el símbolo que hay después del punto
            }
        });
        return R;
    }

    public static Nodo getIncreaseRule(RulesMap rm) {
        return rm.getRulesMap().entrySet().iterator().next().getValue().getFirst().getFirst();
    }


    public static HashSet<LinkedList<Nodo>> findEndPoints(HashSet<LinkedList<Nodo>> Sn) {
        HashSet<LinkedList<Nodo>> R = new HashSet<>();
        Sn.forEach(item -> {
            if (point.equals(item.getLast()))
                R.add(item);
        });
        return R;
    }

    public static HashSet<Pair<LinkedList<Nodo>, HashSet<String>>> findEndPointsLR1(HashSet<Pair<LinkedList<Nodo>, HashSet<String>>> Sn) {
        HashSet<Pair<LinkedList<Nodo>, HashSet<String>>> R = new HashSet<>();
        Sn.forEach(item -> {
            if (point.equals(item.getKey().getLast()))
                R.add(item);
        });
        return R;
    }

    public static int IndexOfRule(LinkedList<Nodo> item, LinkedList<LinkedList<Nodo>> rulesList) {
        Nodo p = item.removeLast(); //Quitamos temporalmente el punto del final
        int i = rulesList.indexOf(item);
        item.addLast(p);
        return i;
    }

}
