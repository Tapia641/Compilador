package LR;

import java.util.LinkedList;

public class Lexico {
    //private final HashMap <String,Object> mapa = new LinkedHashMap<>();
    private final LinkedList<Token> tokens = new LinkedList<>();
    private final String[] cadena;
    private String lexema = "";
    public String[] columns;
    public Object[][] M;

    public Lexico(String cadena, AFD D) {
        this.M = D.getMatriz();
        this.columns = D.getColumnNames();
        this.cadena = cadena.split("");
    }

    public void print_Tokens() {
        System.out.println("------------ANÁLISIS LÉXICO-----------");
        this.tokens.forEach(t -> {
            System.out.println("LEXEMA :" + t.getLexema() + " TOKEN : " + t.getTok());
        });
    }

    public LinkedList<Token> getTokens() {
        return this.tokens;
    }


    public void Analiza() {
        Object prevEdoAcept = "SIN TRANSCICION PREVIEMNETE";
        Object actualTok = "";
        int s = 0, f = 0;
        M = new Object[f][s];
        //Object e = new Object();
        Object e;
        boolean band = true;
        for (int car = 0; car < cadena.length; car++) {
            System.err.println("Holaaaaaaaaaaaaaaa");
            for (int i = 1; i < columns.length - 1; i++) {
                System.err.println("Holaaaaaaaaaaaaaaa2222");

                if (columns[i].equals(cadena[car]) && M[f][i] != null) {
                    s = i;
                    band = true;
                    break;
                } else {
                    band = false; //Indica que no hay transcicion
                }
            }
            if (band) {//Si hubo una trancision a un eatdo con el simbolo analizado
                e = M[f][s]; //Estado al que pasmos con el simbolo analizado
                lexema += cadena[car];
                for (int j = 0; j < M.length; j++) {
                    if (M[j][0].equals(e)) {
                        f = j;
                        break;
                    }
                }
                if (!M[f][columns.length - 1].equals(-1))
                    prevEdoAcept = M[f][columns.length - 1]; //guarda el último token
                actualTok = M[f][columns.length - 1];

            } else { //si no hubo transicion a un estado con el simbolo
                Token t = new Token();
                if (prevEdoAcept.equals("SIN TRANSCICION PREVIEMNETE")) {
                    lexema += cadena[car];
                    t.setLexema(lexema);
                    t.setTok(-1);
                    this.tokens.addLast(t);
                } else {
                    t.setLexema(lexema);
                    t.setTok(Integer.parseInt(prevEdoAcept + ""));
                    this.tokens.addLast(t);
                    f = s = 0;
                    prevEdoAcept = "SIN TRANSCICION PREVIEMNETE";
                    car--;
                }
                lexema = "";
            }
        }

        //Evaluamos la última secuancia de carácteres
        Token t1 = new Token();
        if (!prevEdoAcept.equals("SIN TRANSCICION PREVIEMNETE")) {
            t1.setLexema(lexema);
            t1.setTok(Integer.parseInt(prevEdoAcept + ""));
            this.tokens.addLast(t1);
        }
        //Agregamos un token con valor de token cero indicando el final de cadena
        Token t2 = new Token();
        t2.setLexema("FIN");
        t2.setTok(0);
        this.tokens.addLast(t2);
    }


}
