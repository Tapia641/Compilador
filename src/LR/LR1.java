package LR;

import javafx.util.Pair;

import java.util.*;

public class LR1 {
    private final FirFoll operation;
    private final RulesMap IncreasedGrammar;
    private final AFD automata = new AFD();
    private final Grammar grammar;
    private Object LR1Table[][];
    private String[] Symbolcols;
    private int rows, cols;
    private final LinkedList<LinkedList<Nodo>> NumeratedRules;
    private LinkedList<HashSet<Pair<LinkedList<Nodo>, HashSet<String>>>> SnResultSet = null;

    public LR1(Grammar G) {
        this.IncreasedGrammar = LRTools.makeIncreasedGrammar(G);
        this.operation = new FirFoll(G);
        this.grammar = G;
        this.NumeratedRules = LRTools.NumberRules(IncreasedGrammar);

    }

    public HashSet<String> getPreAnalySet(Pair<LinkedList<Nodo>, HashSet<String>> it) {
        HashSet<String> R = new HashSet<>();
        LinkedList<Nodo> ncp = LRTools.moveIt(LRTools.copy(it.getKey()));
        //si al mover el punto queda al final
        String e = (ncp.getLast().equals(LRTools.point)) ? "Ɛ" : LRTools.getNap(ncp).getSimbolo();
        it.getValue().forEach(sym -> {
            LinkedList<String> sigma = new LinkedList<>();
            sigma.add("");
            sigma.add(e);
            sigma.add(sym);
            R.addAll(this.operation.First(sigma));
        });
        return R;
    }


    public void printItem(Pair<LinkedList<Nodo>, HashSet<String>> it) {
        it.getKey().forEach(w -> {
            System.out.print(w.getSimbolo());
        });
        System.out.println(" -- { " + it.getValue() + " }");
        System.out.println();
    }

    private HashSet<Pair<LinkedList<Nodo>, HashSet<String>>> itemSetLock(HashSet<Pair> items) {
        HashSet<Pair<LinkedList<Nodo>, HashSet<String>>> R = new HashSet<>();
        items.forEach(it -> {
            R.addAll(itemLock(it));
        });
        return R;
    }


    private HashSet<Pair<LinkedList<Nodo>, HashSet<String>>> goTo(HashSet<Pair<LinkedList<Nodo>, HashSet<String>>> items, String s) {
        return itemSetLock(LRTools.movePointLR1(items, s));
    }

    private HashSet<Pair<LinkedList<Nodo>, HashSet<String>>> itemLock(Pair<LinkedList<Nodo>, HashSet<String>> item) {
        HashSet<Pair<LinkedList<Nodo>, HashSet<String>>> R = new HashSet<>();
        Stack<Pair<LinkedList<Nodo>, HashSet<String>>> pila = new Stack<>();
        pila.push(item);
        while (!pila.empty()) {
            Pair it = pila.pop(); //Item para analizar           
            R.add(it);
            HashSet<String> preSet = new HashSet();
            LinkedList<Nodo> ln = (LinkedList<Nodo>) it.getKey();
            if (!LRTools.point.equals(ln.getLast())) {//si el punto no está al final
                Nodo nap = LRTools.getNap(ln);
                if (!nap.getIsTerminal() && !nap.getSimbolo().equals(ln.getFirst().getSimbolo()) && !nap.getSimbolo().equals("Ɛ")) {
                    preSet.addAll(getPreAnalySet(it)); //Consegumos el set de preanálisis para sus producciones                                        
                    IncreasedGrammar.getDerivatedRules(nap).forEach(ladoDer -> {
                        LinkedList<Nodo> li = LRTools.makeItem(ladoDer);
                        if (LRTools.getNap(li).getSimbolo().equals(nap.getSimbolo()))
                            preSet.addAll(getPreAnalySet(new Pair(li, preSet)));
                        pila.push(new Pair(li, preSet));
                    });
                }
            }
        }
        return R;
    }

    public void displacements() {
        HashMap<HashSet<Pair<LinkedList<Nodo>, HashSet<String>>>, Estado> SnMap = new LinkedHashMap<>();
        Nodo firstRule = LRTools.getIncreaseRule(IncreasedGrammar);
        LinkedList<HashSet<Pair<LinkedList<Nodo>, HashSet<String>>>> Sn = new LinkedList<>(); //Lista enlazada para conjunto de analisis
        LinkedList<LinkedList<Nodo>> ladosDer = IncreasedGrammar.getDerivatedRules(firstRule);///Regla de la gramatica aumentada
        HashSet<String> pre = new HashSet();
        pre.add("$");
        HashSet<Pair<LinkedList<Nodo>, HashSet<String>>> s0 = itemLock(new Pair(LRTools.makeItem(ladosDer.getFirst()), pre));
        Sn.addLast(s0); //agregamos a Sn s0 como conjunto no marcado que representa el estdo inicial
        SnMap.put(s0, automata.getEstadoInicial());//Colocamos en el mapa el primer estado asociado al conjunto S0
        //D.addSnList(s0);
        while (!Sn.isEmpty()) {
            HashSet<Pair<LinkedList<Nodo>, HashSet<String>>> Si = Sn.removeFirst(); //Sacamos un conjunto si de Sn para analizar
            Si.forEach(w -> {
                this.printItem(w);
            });
            HashSet<String> symbols = LRTools.findSimbolsLR1(Si); //Símbolos con los que se ejecutara el metodo GoTo
            symbols.forEach(sym -> {
                HashSet<Pair<LinkedList<Nodo>, HashSet<String>>> Ai = goTo(Si, sym);
                if (!Ai.isEmpty()) {
                    if (!SnMap.containsKey(Ai)) {
                        Sn.addLast(Ai);
                        Estado e = new Estado();
                       /*En la siguiente linea conseguimos el estado asociado al conjunto que se está 
                         analizando "Si" y procedemos a colocar trancisiones con el símbolo s 
                         al estado creado "e"
                       */
                        SnMap.get(Si).SetTransicion(new Transicion(sym, e));
                        automata.addConjuntoEstados(e);
                        //D.addSnList(Ai);
                        SnMap.put(Ai, e);
                    } else {
                       /*
                        De estar Ai en en SnMap recuperamos su estado y colocamos una transición
                        del estado asociado al conjunto de análisis a ese estado con el símbolo s                       
                        */
                        SnMap.get(Si).SetTransicion(new Transicion(sym, SnMap.get(Ai)));
                    }
                }
            });
        }
        automata.addAlfabeto(grammar.getVN());
        automata.addAlfabeto(grammar.getVT());
        SnResultSet = makeSnResultSet(SnMap.keySet()); //Conjunto resultante de todos slo Si generados
    }

    public void printTransTable() {
        automata.printAFD();//Imprime en consola el AFD resultante
        //automata.print_AFD("",true);//Muestra el frame con la tabla de tranisiones del AFD resultante
    }

    private LinkedList<HashSet<Pair<LinkedList<Nodo>, HashSet<String>>>> makeSnResultSet(Set<HashSet<Pair<LinkedList<Nodo>, HashSet<String>>>> SnResultSet) {
        LinkedList<HashSet<Pair<LinkedList<Nodo>, HashSet<String>>>> R = new LinkedList();
        SnResultSet.forEach(s -> {
            R.add(s);
        });
        return R;
    }

    public LinkedList<HashSet<Pair<LinkedList<Nodo>, HashSet<String>>>> getSnResultSet() {
        return SnResultSet;
    }

    public LinkedList<LinkedList<Nodo>> getNumeratedRules() {
        return NumeratedRules;
    }

    public AFD getAutomata() {
        return automata;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public HashSet<String> getVT() {
        return grammar.getVT();
    }

    public HashSet<String> getVN() {
        return grammar.getVN();
    }

    public void AnalyzeString(LinkedList<Token> tk) {
        sintaxAnalyze w = new sintaxAnalyze(LR1Table, rows, cols, Symbolcols, NumeratedRules);
        if (w.Analyze(tk)) {
            System.out.println("\n--------------------------------\n");
            System.out.println("!CADENA SINTACTICAMENTE CORRECTA!");
            System.out.println("\n--------------------------------\n");
        } else {
            System.out.println("\n--------------------------------\n");
            System.out.println("!CADENA SINTACTICAMENTE INCORRECTA!");
            System.out.println("\n--------------------------------\n");
        }
    }

    public void buildTableLR1() {
        displacements();
        LR1Table t = new LR1Table(this);
        t.pack();
        t.setVisible(true);
        t.setTitle("Tabla LR1");
        this.LR1Table = t.getTabla();
        this.Symbolcols = t.getColumnNames();
        this.cols = t.getN();
        this.rows = t.getM();
    }

}

    
 