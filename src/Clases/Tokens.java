package Clases;


public class Tokens {

    /* TOKENS PARA ARITMÉTICA BÁSICA */
    public static int MAS = 10;
    public static int MENOS = 20;
    public static int PROD = 30;
    public static int DIV = 40;
    public static int PAR_I = 50;
    public static int PAR_D = 60;
    public static int NUM = 70;
    public static int FIN = 0;
    public static int ERROR = -1;
    //FIN TOKENS

    /* TOKEN PARA CALCULADORA */
    public static int CMAS = 10;
    public static int CMENOS = 20;
    public static int CPROD = 30;
    public static int CDIV = 40;
    public static int CPOT = 50;
    public static int CPAR_I = 60;
    public static int CPAR_D = 70;
    public static int CSIN = 80;
    public static int CCOS = 90;
    public static int CTAN = 100;
    public static int CEXP = 110;
    public static int CLOG = 120;
    public static int CLN = 130;
    public static int CNUM = 140;
    //FIN TOKENS

    /* TOKENS PARA EXPRESIONES REGULARES*/
    public static int ER_CERRADURA_POSTIVIA = 200;
    public static int ER_CERRADURA_ESTRELLA = 210;
    public static int ER_THOMPSON = 220;
    public static int ER_OR = 230;
    public static int ER_SIMBOLO = 250;
    public static int ER_PAR_I = 260;
    public static int ER_PAR_D = 270;
    public static int ER_CONCA = 280;
    //FIN TOKENS

    /* TOKENS  DE GRAMATICA DE GRAMATICAS */
    public static int GG_OR = 300;
    public static int GG_PUNTO_COMA = 310;
    public static int GG_FLECHA = 320;
    public static int GG_SIMBOLO = 330;

}
