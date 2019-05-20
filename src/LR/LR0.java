package LR;

import java.util.*;

public class LR0 {
    private final LinkedList<LinkedList<Nodo>> NumeratedRules;
    private final AFD automata = new AFD();
    private final RulesMap increasedGrammar;
    private final Grammar grammar;
    private final String axioma;
    private Object LR0Table[][];
    private String[] Symbolcols;
    private int rows, cols;
    private LinkedList<HashSet<LinkedList<Nodo>>> SnResultSet = null;

    public LR0(Grammar G) {
        this.grammar = G;
        this.axioma = G.getAxioma();
        this.increasedGrammar = LRTools.makeIncreasedGrammar(G);
        this.NumeratedRules = LRTools.NumberRules(increasedGrammar);
    }

    public void printIncreasedGramar() {
        System.out.println("\n---------------GRAMÁTICA AUMENTADA---------------------\n");
        this.increasedGrammar.printRulesMap();
    }

    public HashSet<LinkedList<Nodo>> itemLock(LinkedList<Nodo> it) {
        HashSet<LinkedList<Nodo>> R = new HashSet<>();
        Stack<LinkedList<Nodo>> pila = new Stack<>();
        LinkedList<Nodo> item;
        pila.push(it);
        while (!pila.empty()) {
            item = pila.pop();
            if (!R.contains(item)) {
                R.add(item);
                if (!LRTools.point.equals(item.getLast())) {//si el punto no está al final
                    Nodo nap = LRTools.getNap(item);
                    if (!nap.getIsTerminal() && !nap.equals(item.getFirst()) && !nap.getSimbolo().equals("Ɛ")) {
                        LinkedList<LinkedList<Nodo>> ladosDer = this.increasedGrammar.getDerivatedRules(nap);
                        ladosDer.forEach(ladoDer -> {
                            pila.push(LRTools.makeItem(ladoDer));
                        });
                    }
                }
            }
        }
        return R;
    }

    private HashSet<LinkedList<Nodo>> itemSetLock(HashSet<LinkedList<Nodo>> items) {
        HashSet<LinkedList<Nodo>> R = new HashSet<>();
        items.forEach(it -> {
            R.addAll(this.itemLock(it));
        });
        return R;
    }

    private HashSet<LinkedList<Nodo>> goTo(HashSet<LinkedList<Nodo>> item, String simbolo) {
        HashSet<LinkedList<Nodo>> R = new HashSet<>();
        R.addAll(this.itemSetLock(LRTools.movePoint(item, simbolo)));
        return R;
    }

    public void displacements() {
        HashMap<HashSet<LinkedList<Nodo>>, Estado> SnMap = new LinkedHashMap<>();
        Nodo firstRule = LRTools.getIncreaseRule(increasedGrammar);
        LinkedList<HashSet<LinkedList<Nodo>>> Sn = new LinkedList<>(); //Lista enlazada para conjunto de analisis
        LinkedList<LinkedList<Nodo>> ladosDer = increasedGrammar.getDerivatedRules(firstRule);///Regla de la gramatica aumentada
        HashSet<LinkedList<Nodo>> s0 = itemLock(LRTools.makeItem(ladosDer.getFirst()));
        Sn.addLast(s0); //agregamos a Sn s0 como conjunto no marcado que representa el estdo inicial
        SnMap.put(s0, automata.getEstadoInicial());//Colocamos en el mapa el primer estado asociado al conjunto S0
        while (!Sn.isEmpty()) {
            HashSet<LinkedList<Nodo>> Si = Sn.removeFirst(); //Sacamos un conjunto si de Sn para analizar
            HashSet<Nodo> symbols = LRTools.findSimbols(Si); //Símbolos con los que se ejecutara el metodo GoTo
            symbols.forEach(s -> {
                HashSet<LinkedList<Nodo>> Ai = goTo(Si, s.getSimbolo());
                if (!Ai.isEmpty()) {
                    if (!SnMap.containsKey(Ai)) {
                        Sn.addLast(Ai);
                        Estado e = new Estado();
                       /*En la siguiente linea conseguimos el estado asociado al conjunto que se está 
                         analizando "Si" y procedemos a colocar trancisiones con el símbolo s 
                         al estado creado "e"
                       */
                        SnMap.get(Si).SetTransicion(new Transicion(s.getSimbolo(), e));
                        automata.addConjuntoEstados(e);
                        SnMap.put(Ai, e);
                    } else {
                       /*
                        De estar Ai en en SnMap recuperamos su estado y colocamos una transición
                        del estado asociado al conjunto de análisis a ese estado con el símbolo s                       
                        */
                        SnMap.get(Si).SetTransicion(new Transicion(s.getSimbolo(), SnMap.get(Ai)));
                    }
                }
            });
        }
        automata.addAlfabeto(grammar.getVN());
        automata.addAlfabeto(grammar.getVT());
        SnResultSet = this.makeSnResultSet(SnMap.keySet()); //Conjunto Sn con todos los Si totales generados
    }

    private LinkedList<HashSet<LinkedList<Nodo>>> makeSnResultSet(Set<HashSet<LinkedList<Nodo>>> SnResultSet) {
        LinkedList<HashSet<LinkedList<Nodo>>> R = new LinkedList();
        SnResultSet.forEach(s -> {
            R.add(s);
        });
        return R;
    }

    public LinkedList<HashSet<LinkedList<Nodo>>> getSnResultSet() {
        return SnResultSet;
    }

    public void printTransTable() {
        automata.printAFD();//Imprime en consola el AFD resultante
        //automata.print_AFD("",true);//Muestra el frame con la tabla de tranisiones del AFD resultante
    }

    public void AnalyzeString(LinkedList<Token> tk) {
        sintaxAnalyze w = new sintaxAnalyze(LR0Table, rows, cols, Symbolcols, NumeratedRules);
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


    public void buildTableLR0() {
        displacements();
        LR0Table t = new LR0Table(this);
        t.pack();
        t.setVisible(true);
        t.setTitle("Tabla LR0");
        this.LR0Table = t.getTabla();
        this.Symbolcols = t.getColumnNames();
        this.cols = t.getN();
        this.rows = t.getM();
    }
}

