package LR;

import java.util.LinkedList;
import java.util.Stack;

public class sintaxAnalyze {

    private int m;
    private int n;
    private Object tabla[][];
    private String[] columnNames;
    private final LinkedList<LinkedList<Nodo>> rules;

    public sintaxAnalyze(Object[][] tabla, int m, int n, String[] columnNames, LinkedList<LinkedList<Nodo>> rules) {
        this.m = m;
        this.n = n;
        this.tabla = tabla;
        this.columnNames = columnNames;
        this.rules = rules;
    }

    public boolean Analyze(LinkedList<Token> tokens) {
        boolean band = false;
        Token finCadena = new Token();
        tokens.removeLast();
        finCadena.setLexema("$");
        finCadena.setTok(119);
        tokens.addLast(finCadena);
        Stack<Object> pila = new Stack<>();
        Object z = tabla[0][0]; //primer id del estado de la tabla va dentro de la pila        
        pila.push(z);
        Object symbol = Tokens.IDTokens.get(tokens.getFirst().getTok());
        String action = this.getAction(pila.lastElement(), symbol);
        while (!"error".equals(action) && !pila.empty()) {
            System.out.println("Tamño de la pila: " + pila.size());
            printStack(tokens);
            if (action.equals("r0")) {
                band = true;
                break;
            }
            switch (action.charAt(0)) {
                case 'd'://Desplazamieno
                    System.out.println("Desplazamiento: " + action);
                    Object edo = action.substring(1);
                    pila.push(Tokens.IDTokens.get(tokens.removeFirst().getTok())); //elemtento de la cadena o lexema
                    pila.push(edo);//numero de estado
                    symbol = Tokens.IDTokens.get(tokens.getFirst().getTok());
                    action = this.getAction(pila.lastElement(), symbol);
                    break;
                case 'r'://Reducción
                    System.out.println("Reduccion: " + action);
                    int nr = Integer.parseInt(action.substring(1));
                    int m = (rules.get(nr).size() - 1) * 2;
                    //Quitamos m*2 elementos de la pila
                    for (int i = 0; i < m; i++)
                        pila.pop();
                    Nodo aux = rules.get(nr).getFirst();
                    edo = this.getAction(pila.lastElement(), aux.getSimbolo());
                    pila.push(aux);
                    pila.push(edo);
                    symbol = Tokens.IDTokens.get(tokens.getFirst().getTok());
                    action = this.getAction(pila.lastElement(), symbol);
            }
        }
        return band;
    }


    /**
     * Me retrona el índice de un estado a buscar en la tabla LR0
     *
     * @param edo
     * @return
     */
    private int getIndexInTable(Object edo) {
        for (int i = 0; i < m; i++)
            if (tabla[i][0].toString().equals(edo.toString()))
                return i;
        return -1;
    }

    /**
     * Me retrona el índice de un símbolo de la gramatica a buscar en la tabla LR0
     *
     * @param symb
     * @return
     */
    private int getIndexSymb(Object symb) {
        for (int j = 1; j < n; j++)
            if (columnNames[j].equals(symb))
                return j;
        return -1;
    }

    /**
     * Me retorna un Object que dirá si será una reduccion o un desplazamiento
     *
     * @param edo
     * @param symb
     * @return
     */
    private String getAction(Object edo, Object symb) {
        //buscamos el id del estado en la columna de edos de la tabla LR0        
        String msg = "error";
        int x = this.getIndexInTable(edo);
        int y = this.getIndexSymb(symb);
        if (tabla[x][y] != null)
            msg = this.tabla[x][y].toString();
        return msg;
    }

    public void printStack(LinkedList<Token> p) {
        p.forEach(e -> {
            System.out.print(e.getLexema());
        });
        System.out.println();
    }

}
