package LR;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;


public class Grammar {
    private HashSet<String> VT;
    private HashSet<String> VN;
    private Lexico lexic;
    private SintaxGramaticExpresion sintax;
    private String grammarRules;
    private RulesMap G;

    public Grammar(HashSet<String> vt, HashSet<String> vn, String g) {
        this.VN = vn;
        this.VT = vt;
        this.grammarRules = g;
        this.G = new RulesMap();
    }

    public Grammar(RulesMap rm) {
        this.G = rm;
    }

    public void LexicAnalize(AFD afd) {
        lexic = new Lexico(this.grammarRules, afd);
        lexic.Analiza();
        lexic.print_Tokens();
    }

    public void SintaxAnalize() {
        sintax = new SintaxGramaticExpresion(lexic.getTokens(), this.VT);
        sintax.SintaxAnalize();
        sintax.Evaluate();
        this.G = sintax.getRm();
    }

    public HashSet<String> getVT() {
        return VT;
    }

    public HashSet<String> getVN() {
        return VN;
    }

    public String getGrammarRules() {
        return grammarRules;
    }

    public RulesMap getRulesMap() {
        return sintax.getRm();
    }


    public String getAxioma() {
        String axioma = "";
        Iterator it = G.getRulesMap().keySet().iterator();
        while (it.hasNext()) {
            String ladoIzquierdo = (String) it.next();
            LinkedList<LinkedList<Nodo>> LadosDerechos = this.G.getRulesMap().get(ladoIzquierdo);
            axioma = LadosDerechos.getFirst().getFirst().getSimbolo();
            break;
        }
        return axioma;
    }

    public void unionGram(Grammar g) {
        G.UnionMap(g.getRulesMap());
    }

    public SintaxGramaticExpresion getSintax() {
        return sintax;
    }

}
