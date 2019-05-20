package LR;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class FirFoll {

    private final HashMap<String, LinkedList<LinkedList<String>>> grammar = new HashMap<>();
    private final HashMap<String, HashSet<String>> mamoryFolow = new HashMap<>();
    private final HashMap<String, HashSet<String>> mamoryFirst = new HashMap<>();
    private final HashSet<String> vt = new HashSet<>();
    private final String axioma, axioma2;


    public FirFoll(Grammar G) {
        this.vt.addAll(G.getVT());
        G.getRulesMap().getRulesMap().entrySet().forEach(regla -> {
            String ladoIzq = regla.getKey();
            LinkedList<LinkedList<String>> LadosDer = new LinkedList<>();
            regla.getValue().forEach(ladosDer -> {
                LinkedList<String> ladoDer = new LinkedList<>();
                ladosDer.forEach(nodo -> {
                    ladoDer.addLast(nodo.getSimbolo());
                });
                LadosDer.addLast(ladoDer);
            });
            this.grammar.put(ladoIzq, LadosDer);
        });
        axioma = G.getAxioma();
        axioma2 = "┐";
    }

    private LinkedList<String> copyRule(LinkedList<String> ladoDer) {
        LinkedList<String> newRule = new LinkedList<>();
        for (int i = 1; i < ladoDer.size(); i++)
            newRule.addLast(ladoDer.get(i));
        return newRule;
    }

    public HashSet<String> First(LinkedList<String> ladoDer) {
        HashSet<String> R = new HashSet<>();
        LinkedList<String> ladoDerCopy = this.copyRule(ladoDer);
        while (!ladoDerCopy.isEmpty()) {
            String symbol = ladoDerCopy.removeFirst();
            if (vt.contains(symbol) || symbol.equals("Ɛ") || symbol.equals("$"))
                R.add(symbol);
            else {
                System.out.println(symbol);
                this.grammar.get(symbol).forEach(producciones -> {
                    if (!symbol.equals(producciones.get(1))) //Evitamos posible recursión
                        R.addAll(First(producciones));
                });
            }
            //Si hay epsilon y los simbolos aun no se acabn se quita epsilon de la produccion                                 
            if ((R.contains("Ɛ") && ladoDerCopy.isEmpty()) || !(R.contains("Ɛ")))
                return R;
            else
                R.remove("Ɛ");
        }
        return R;
    }


    private LinkedList<LinkedList<String>> findRules(String symbol) {
        LinkedList<LinkedList<String>> R = new LinkedList<>();
        this.grammar.values().forEach(LL -> {
            LL.forEach(l -> {
                String temp = l.removeFirst();
                if (l.contains(symbol))
                    R.addLast(l);
                l.addFirst(temp);
            });
        });
        return R;
    }

    private boolean isThereAnyNext(LinkedList<String> r) {
        return r.get(1).equals(r.getLast());
    }


    private LinkedList<String> getNextSymb(LinkedList<String> rule, String s) {
        int index = 0;
        LinkedList<String> R = new LinkedList<>();
        R.addLast("");
        for (int i = 1; i < rule.size(); i++) {
            if (s.equals(rule.get(i))) {
                index = i;
                break;
            }
        }

        for (int i = index + 1; i < rule.size(); i++)
            R.addLast(rule.get(i));

        return R;
    }

    public HashSet<String> Follow(String symbol) {
        HashSet<String> R = new HashSet<>();
        if (!this.mamoryFolow.containsKey(symbol)) {
            //si symbol corresonpende al axioma de la gramática
            if (symbol.equals(axioma) || symbol.equals(axioma2))
                R.add("$");
            //consegumos todas las reglas donde aparece "symbol" en la gramática del lado derecho
            LinkedList<LinkedList<String>> toAnalyze = findRules(symbol);
            //Para cada una de las reglas que se encontró del lado derecho 
            toAnalyze.forEach(rule -> {
                //Existe una regla que no sea A -> aB donde B = symbol
                if (!rule.getLast().equals(symbol)) {
                    LinkedList<String> toFirstAnalyze = this.getNextSymb(rule, symbol);
                    R.addAll(First(toFirstAnalyze));
                    if (R.contains("Ɛ")) {
                        R.remove("Ɛ");
                        String ladoIzq = rule.getFirst();
                        R.addAll(Follow(ladoIzq));
                        this.mamoryFolow.put(symbol, R);
                    }
                } else {
                    String ladoIzq = rule.getFirst();
                    if (!ladoIzq.equals(symbol)) {
                        R.addAll(Follow(ladoIzq));
                        this.mamoryFolow.put(symbol, R);
                    }
                }
            });
        } else
            R.addAll(this.mamoryFolow.get(symbol));
        return R;
    }
}
