package Clases.AnalizadorSintactico;

/* IMPORTAMOS LOS TOKEN PREVIAMENTE REALIZADOS */
import Clases.Tokens;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

/* ANÁLISIS DE ARITMÉTICA BÁSICA PARA UNA CADENA DADA */

public class Calculadora {

    /* PARA MANEJAR REFERENCIA EN JAVA */
    private class Numero {

        private double value;

        public Numero() {
            this.value = Double.parseDouble("0");
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
    private Vector<Pair<String, Integer>> V;
    private HashSet<String> C;
    private boolean R;

    public void AnalizarSintacticamente(Vector<Pair<String, Integer>> V) {
        this.V = V;
        C = new HashSet<>();
        Stack<Integer> PilaAux = new Stack<>();
        this.Pila = new Stack<>();
        R = false;

        for (Pair<String, Integer> P : V) {
            System.out.println(P.getValue());
            PilaAux.push(P.getValue());
        }
        PilaAux.push(0);

        /* INVERTIMOS LA PILA PARA TRABAJAR CON COMODIDAD */
        for (int i = PilaAux.size() - 1; i > -1; i--) {
            this.Pila.push(PilaAux.get(i));
        }

        //COMIENZA A EVALUAR
        System.out.print("G->");
        if (G(N)) {
            R = true;
            System.out.println("CADENA SINTÁCTICAMENTE CORRECTA");
            System.out.println("EL RESULTADO DE LA OPERACION ES: " + N.getValue());
        } else System.err.println("CADENA SINTÁCTICAMENTE INCORRECTO");

    }

    public boolean getR(){
        return R;
    }

    public double getResultado(){
        return N.getValue();
    }

     //INICIAMOS CON EL ANÁLISIS SINTÁCTICO
    public boolean G(Numero v) {
        if (E(v)) {
            /* PEDIMOS UN TOKEN A LA PILA QUE OBTUVO EL ANALIZADOR LEXICO */
            int TokenPedido = Pila.pop();
            if (TokenPedido == Tokens.ERROR) return false;

            if (TokenPedido == Tokens.FIN) {
                return true;
            }
        }
        return false;
    }

    public boolean E(Numero v) {
        if (T(v)) {
            if (Ep(v)) {
                return true;
            }
        }
        return false;
    }

    public boolean Ep(Numero v) {
        Numero v1 = new Numero();

        /* PEDIMOS UN TOKEN A LA PILA QUE OBTUVO EL ANALIZADOR LEXICO */
        int TokenPedido = Pila.pop();
        if (TokenPedido == Tokens.ERROR) return false;


        if (TokenPedido == Tokens.CMAS || TokenPedido == Tokens.CMENOS) {
            if (T(v1)) {
                if (TokenPedido == Tokens.CMAS)
                    v.setValue(v.getValue() + v1.getValue());
                else {
                    v.setValue(v.getValue() - v1.getValue());
                }
                if (Ep(v)) {
                    return true;
                }
    		}
            System.out.print(false);
            return false;
    	}

        /* REGRESAMOS EL TOKEN A LA PILA */
        Pila.push(TokenPedido);
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
        if (TokenPedido == Tokens.ERROR) return false;


        if (TokenPedido == Tokens.CPROD || TokenPedido == Tokens.CDIV) {
            if (P(v1)) {
                if (TokenPedido == Tokens.CPROD) {
                    v.setValue(v.getValue() * v1.getValue());

                } else {
                    v.setValue(v.getValue() / v1.getValue());
                }

                return true;

            }
            return false;
        }


        /* REGRESAMOS EL TOKEN A LA PILA */
        Pila.push(TokenPedido);

        return true;
    }

    public boolean P(Numero v) {
        if (F(v)) {
            if (Pp(v)) {
                return true;
            }
        }
        return false;
    }

    public boolean Pp(Numero v) {

        Numero v1 = new Numero();

        /* PEDIMOS UN TOKEN A LA PILA QUE OBTUVO EL ANALIZADOR LEXICO */
        int TokenPedido = Pila.pop();
        if (TokenPedido == Tokens.ERROR) return false;

        if (TokenPedido == Tokens.CPOT) {
            if (F(v)) {
                if (Pp(v1)) {
                    v.setValue(Math.pow(v.getValue(), v1.getValue()));
                    return true;
                }
            }
            return false;
        }

        /* REGRESAMOS EL TOKEN A LA PILA */
        Pila.push(TokenPedido);

        return true;
    }


    public boolean F(Numero v) {

        /* PEDIMOS UN TOKEN A LA PILA QUE OBTUVO EL ANALIZADOR LEXICO */
        int TokenPedido = Pila.pop();

        if (TokenPedido == Tokens.ERROR) return false;

        if (TokenPedido == Tokens.CNUM) {

            boolean creado = false;

            /*OBTENEMOS EL NUMERO EN NUESTRO VECTOR DE LEXEMA Y TOKENS*/
            for (Pair<String, Integer> P : V) {
                if (P.getValue() == Tokens.CNUM && !C.contains(P.getKey())) {
                    C.add(P.getKey());
                    v.setValue(Double.parseDouble(P.getKey()));
                    creado = true;
                    break;
                }
            }

            if (!creado) {
                for (Pair<String, Integer> P : V) {
                    //CORROBORAR REPETIDOS 5+5
                    if (P.getValue() == Tokens.CNUM) {
                        C.add(P.getKey());
                        v.setValue(Double.parseDouble(P.getKey()));
                        break;
                    }
                }
            }

            return true;
        }

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