package LR;

import java.util.HashMap;

public class Tokens {
    /*---TOKENS PARA EXPRESIONES ARIMTMÉTICAS----*/
    public static final int FIN = 0;
    public static final int MAS = 10;
    public static final int MENOS = 20;
    public static final int PROD = 30;
    public static final int DIV = 40;
    public static final int PAR_I = 50;
    public static final int PAR_D = 60;
    public static final int NUM = 70;
    public static final int INT = 80;
    public static final int FLOAT = 90;
    public static final int EDEN = 100;
    public static final int SPACE = 110;
    public static final int POT = 120;
    /*----TOKEN SPARA EXPRESIONES REGULARES---*/
    public static final int OR = 111;
    public static final int CONCAT = 112;
    public static final int LOCK_POS = 113;
    public static final int STAR_LOCK = 114;
    public static final int INTERROGATIVE = 115;
    public static final int SIMBOLO = 116;
    /*---TOKENS PARA GRAMTICA DE GRAMATICA DE GRAMATICAS----*/
    public static final int PUNTO_COMA = 117;
    public static final int FLECHA = 118;
    public static final int TERMIN_CADENA = 119;
    /*---IDENTIFIACADORES A SU NUM DE TOKEN ASOCIADO-----------*/
    public static final HashMap<Integer, String> IDTokens = new HashMap() {{
        put(70, "n");
        put(50, "(");
        put(10, "+");
        put(60, ")");
        put(20, "-");
        put(30, "*");
        put(40, "/");
        put(117, ";");
        put(116, "(");
        put(118, "->");
        put(119, "$");
        put(120, "^");
    }};


}