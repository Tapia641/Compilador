package Clases.AnalizadorSintactico;

import Clases.AnalizadorLexico;
/* IMPORTAMOS LOS TOKEN PREVIAMENTE REALIZADOS */
import Clases.Tokens;
import jdk.nashorn.internal.parser.Token;

import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

/* ANÁLISIS DE ARITMÉTICA BÁSICA PARA UNA CADENA DADA */

public class Calculadora {

    /* PARA MANEJAR REFERENCIA EN JAVA */
    private class Numero {

        private double value;

        public Numero() {
            this.value = Double.parseDouble(0 + "");
        }

        private void setValue(double f) {
            this.value = f;
        }

        private double getValue() {
            return this.value;
        }
    }

    /* DATOS CON LOS QUE VAMOS A TRABAJAR */
    private Numero N = new Numero();
    private Stack<Integer> Pila;

    public void AnalizarSintacticamente(Stack<Integer> Pila) {

        this.Pila = new Stack<>();

        /* INVERTIMOS LA PILA PARA TRABAJAR CON COMODIDAD */
        for (int i = Pila.size() - 1; i > -1; i--) {
            this.Pila.push(Pila.get(i));
        }

        System.out.println("Elementos de la pila: " + this.Pila.size());
        for (int i : Pila) System.out.println(i);

        //COMIENZA A EVALUAR
        boolean correcta;
        System.out.print("G->");
        if (G(N)) {
            System.out.println("CADENA SINTÁCTICAMENTE CORRECTA");
            //Evaluar
        } else System.err.println("CADENA SINTÁCTICAMENTE INCORRECTO");

    }

     //INICIAMOS CON EL ANÁLISIS SINTÁCTICO

    public boolean G(Numero v) {
        System.out.print("E->");
        if (E(v)) {
            /* PEDIMOS UN TOKEN A LA PILA QUE OBTUVO EL ANALIZADOR LEXICO */
            int TokenPedido = Pila.pop();
            if (TokenPedido == Tokens.ERROR) return false;

            System.out.print(TokenPedido);
            if (TokenPedido == Tokens.FIN) {
                System.out.print("true");
                return true;
            }
        }
        System.out.print("false");
        return false;
    }

    public boolean E(Numero v) {
        System.out.print("T");
        if (T(v)) {
            System.out.print("Ep");
            if (Ep(v)) {
                System.out.print("true");
                return true;
            }
        }
        System.out.print("false");
        return false;
    }

    public boolean Ep(Numero v) {
        Numero v1 = new Numero();

        /* PEDIMOS UN TOKEN A LA PILA QUE OBTUVO EL ANALIZADOR LEXICO */
        int TokenPedido = Pila.pop();
        System.out.print(TokenPedido);
        if (TokenPedido == Tokens.ERROR) return false;


        if (TokenPedido == Tokens.CMAS || TokenPedido == Tokens.CMENOS) {
            System.out.print("T");
            if (T(v1)) {
                //Resolver si es correcto
                if (TokenPedido == Tokens.CMAS)
                    v.setValue(v.getValue() + v1.getValue());
                else {
                    v.setValue(v.getValue() - v1.getValue());
                }
                System.out.print("Ep");
                if (Ep(v)) {
                    System.out.print("true");
                    return true;
                }
    		}
            System.out.print(false);
            return false;
    	}

        //if (P(v)) return true;

        /* REGRESAMOS EL TOKEN A LA PILA */
        Pila.push(TokenPedido);
        System.out.print("true");
    	return true;
    }

    public boolean T(Numero v) {
        if (P(v)) {
            if (Tp(v)) {
                return true;
            }
        }
        return false;
    }

    public boolean Tp(Numero v) {
        Numero v1 = new Numero();

        /* PEDIMOS UN TOKEN A LA PILA QUE OBTUVO EL ANALIZADOR LEXICO */
        int TokenPedido = Pila.pop();
        System.out.print(TokenPedido);
        if (TokenPedido == Tokens.ERROR) return false;


        if (TokenPedido == Tokens.CPROD || TokenPedido == Tokens.CDIV) {
            System.out.print("P");
            if (P(v)) {
                if (TokenPedido == Tokens.CPROD)
                    v.setValue(v.getValue() * v1.getValue());
                else
                    v.setValue(v.getValue() / v1.getValue());

                return true;

            }
            System.out.print("false");
            return false;
        }


        /* REGRESAMOS EL TOKEN A LA PILA */
        System.out.print("Regresa a la cola: " + TokenPedido);
        Pila.push(TokenPedido);

        return true;
    }

    public boolean P(Numero v) {
        System.out.print("F");
        if (F(v)) {
            System.out.print("Pp");
            if (Pp(v)) {
                System.out.print("true");
                return true;
            }
        }
        System.out.print("false");
        return false;
    }

    public boolean Pp(Numero v) {

        Numero v1 = new Numero();

        /* PEDIMOS UN TOKEN A LA PILA QUE OBTUVO EL ANALIZADOR LEXICO */
        int TokenPedido = Pila.pop();
        System.out.print(TokenPedido);
        if (TokenPedido == Tokens.ERROR) return false;

        if (TokenPedido == Tokens.CPOT) {
            System.out.print("F");
            if (F(v)) {
                System.out.print("Pp");
                if (Pp(v)) {
                    System.out.print("true");
                    return true;
                }
            }
            System.out.print("false");
            return false;
        }
        /* REGRESAMOS EL TOKEN A LA PILA */
        Pila.push(TokenPedido);

        System.out.print("true");
        return true;
    }


    public boolean F(Numero v) {
        Numero v1 = new Numero();

        /* PEDIMOS UN TOKEN A LA PILA QUE OBTUVO EL ANALIZADOR LEXICO */
        int TokenPedido = Pila.pop();
        System.out.print("Estamos en F con " + TokenPedido);
        if (TokenPedido == Tokens.ERROR) return false;

        if (TokenPedido == Tokens.CNUM)
            return true;

        if (TokenPedido == Tokens.CPAR_I) {
            if (E(v)) {
                TokenPedido = Pila.pop();
                if (TokenPedido == Tokens.CPAR_D) {
                    return true;
                }
            }
            return false;
        }

        if (TokenPedido == Tokens.CSIN) {
            TokenPedido = Pila.pop();
            if (TokenPedido == Tokens.CPAR_I) {
                if (E(v)) {
                    TokenPedido = Pila.pop();
                    if (TokenPedido == Tokens.CPAR_D) {
                        v.setValue(Math.sin(v.getValue()));
                        return true;
                    }
                }
            }
            return false;
        }

        if (TokenPedido == Tokens.CCOS) {
            TokenPedido = Pila.pop();
            if (TokenPedido == Tokens.CPAR_I) {
                if (E(v)) {
                    TokenPedido = Pila.pop();
                    if (TokenPedido == Tokens.CPAR_D) {
                        v.setValue(Math.cos(v.getValue()));
                        return true;
                    }
                }
            }
            return false;
        }


        if (TokenPedido == Tokens.CTAN) {
            TokenPedido = Pila.pop();
            if (TokenPedido == Tokens.CPAR_I) {
                if (E(v)) {
                    TokenPedido = Pila.pop();
                    if (TokenPedido == Tokens.CPAR_D) {
                        v.setValue(Math.tan(v.getValue()));
                        return true;
                    }
                }
            }
            return false;
        }


        if (TokenPedido == Tokens.CEXP) {
            TokenPedido = Pila.pop();
            if (TokenPedido == Tokens.CPAR_I) {
                if (E(v)) {
                    TokenPedido = Pila.pop();
                    if (TokenPedido == Tokens.CPAR_D) {
                        v.setValue(Math.exp(v.getValue()));
                        return true;
                    }
                }
            }
            return false;
        }

        if (TokenPedido == Tokens.CEXP) {
            TokenPedido = Pila.pop();
            if (TokenPedido == Tokens.CPAR_I) {
                if (E(v)) {
                    TokenPedido = Pila.pop();
                    if (TokenPedido == Tokens.CPAR_D) {
                        v.setValue(Math.exp(v.getValue()));
                        return true;
                    }
                }
            }
            return false;
        }

        if (TokenPedido == Tokens.CLOG) {
            TokenPedido = Pila.pop();
            if (TokenPedido == Tokens.CPAR_I) {
                if (E(v)) {
                    TokenPedido = Pila.pop();
                    if (TokenPedido == Tokens.CPAR_D) {
                        v.setValue(Math.log(v.getValue()));
                        return true;
                    }
                }
            }
            return false;
        }

        if (TokenPedido == Tokens.CLN) {
            TokenPedido = Pila.pop();
            if (TokenPedido == Tokens.CPAR_I) {
                if (E(v)) {
                    TokenPedido = Pila.pop();
                    if (TokenPedido == Tokens.CPAR_D) {
                        v.setValue(Math.log1p(v.getValue()));
                        return true;
                    }
                }
            }
            return false;
        }
        /* REGRESAMOS EL TOKEN A LA PILA */
        Pila.push(TokenPedido);

        return false;

    }

}