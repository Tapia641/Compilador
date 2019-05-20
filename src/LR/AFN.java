package LR;


import java.util.*;

public class AFN {
    private static int idAFNsig = 0;
    private int idAFN;
    private Estado estadoInicial;
    private HashSet<Estado> estadosAceptacion;
    private HashSet<String> alfabeto;
    private HashSet<Estado> conjuntoEstados;
    private final String epsilon = "EPSILON";
    private static AFN especial = new AFN();
    private int Token;

    public AFN() {
        idAFN = idAFNsig;
        estadoInicial = new Estado();
        estadosAceptacion = new HashSet<>();
        alfabeto = new HashSet<>();
        conjuntoEstados = new HashSet<>();
        idAFNsig++;
    }

    public int getIdAFN() {
        return idAFN;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public HashSet<Estado> getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public HashSet<String> getAlfabeto() {
        return alfabeto;
    }

    public HashSet<Estado> getConjuntoEstados() {
        return conjuntoEstados;
    }

    /**
     * createAFN
     *
     * @param s Sibolo con el qeu se creará el AFN básico, puede ser un rango
     * @return retorn un AFN básico de Thomson
     */
    public AFN createAFN(String s) {
        Estado e1 = new Estado();
        e1.setEdoAceptacion(true);
        if (s.equals("0_TO_9")) {//Perimte transisciones a otro estado con los números del 0 al 9
            for (int i = 0; i < 10; i++) {
                alfabeto.add(i + "");
                estadoInicial.SetTransicion(new Transicion(i + "", e1));
            }

        } else if (s.equals("__SIMBOLO__")) { //Permite trasnsicones a un estado con los simbolos de la tabla ascci
            for (char i = ' '; i <= '}'; i++) {
                alfabeto.add(i + "");
                estadoInicial.SetTransicion(new Transicion(i + "", e1));
            }

        } else {
            alfabeto.add(s);
            estadoInicial.SetTransicion(new Transicion(s, e1));
        }
        estadosAceptacion.add(e1);
        conjuntoEstados.add(e1);
        conjuntoEstados.add(estadoInicial);
        return this;
    }


    /**
     * Lock
     *
     * @param e
     * @return conjunto de estados a lo que sellega desde e con epsilon
     */
    public HashSet<Estado> lock(Estado e) {
        HashSet<Estado> C = new HashSet<>();
        Estado e_temp;
        C.clear();
        Stack<Estado> pila = new Stack<>();
        pila.push(e);
        while (!pila.isEmpty()) {
            e_temp = pila.pop();
            if (!C.contains(e_temp)) {
                C.add(e_temp);
                e_temp.getTransitionTo().forEach(t -> {
                    if (t.getSimbolo().equals(epsilon))
                        pila.push(t.getEdo_destino());
                });
            }
        }
        return C;
    }

    /**
     * epsilonLock
     *
     * @param estados
     * @return conjunto de estados a lo que se llega desde e con epsilon usanndo el algoritmo Lock
     */
    public HashSet<Estado> epsilonLock(HashSet<Estado> estados) {
        HashSet<Estado> R = new HashSet<>();
        R.clear();
        estados.forEach(e -> {
            R.addAll(lock(e));
        });
        return R;
    }

    /**
     * moveFrom Operation
     *
     * @param e Estado al que se transita
     * @param s Síblo con la que hay una transición
     * @return conjunto de estados a lo que se llega desde el estado e
     */
    public HashSet<Estado> moveFrom(Estado e, String s) {
        HashSet<Estado> R = new HashSet<>();
        e.getTransitionTo().forEach(t -> {
            if (t.getSimbolo().equals(s))
                R.add(t.getEdo_destino());
        });
        return R;
    }

    /**
     * @param E conjunto de estados
     * @param s simbolo
     * @return conjunto de estados a lo que se llega con s
     */
    public HashSet<Estado> move(HashSet<Estado> E, String s) {
        HashSet<Estado> R = new HashSet<>();
        R.clear();
        E.forEach(e -> {
            R.addAll(moveFrom(e, s));
        });
        return R;
    }

    /***
     *
     * @param e Estado
     * @param s Simbolo
     * @return Conjunto de estado a los que se llegan con el simbolo s aplicando la erradura eplison al estado e
     */
    public HashSet<Estado> goTo(Estado e, String s) {
        return epsilonLock(moveFrom(e, s));
    }

    public HashSet<Estado> Go_to(HashSet<Estado> E, String s) {
        HashSet<Estado> R = new HashSet<>();
        E.forEach(e -> {
            R.addAll(goTo(e, s));
        });
        return R;
    }

    /***
     *
     * @param f2 Automata con el que se unira this
     * @return Retorna el AFN de unir this con con el automata f2
     */
    public AFN union(AFN f2) {
        Estado e1 = new Estado();
        Estado e2 = new Estado();
        e2.setEdoAceptacion(true);
        e1.SetTransicion(new Transicion(epsilon, this.estadoInicial));
        e1.SetTransicion(new Transicion(epsilon, f2.estadoInicial));

        //Quitamos los estados de aceptacion de los dos automatas
        f2.estadosAceptacion.forEach(e -> {
            e.SetTransicion(new Transicion(epsilon, e2));
            e.setEdoAceptacion(false);

        });

        this.estadosAceptacion.forEach(e -> {
            e.SetTransicion(new Transicion(epsilon, e2));
            e.setEdoAceptacion(false);
        });

        //Borramos estados de aceptacion
        this.estadosAceptacion.clear();

        this.estadoInicial = e1;
        this.estadosAceptacion.add(e2);

        //Unimos los alfabetos de this y f2
        (this.alfabeto).addAll(f2.alfabeto);

        //Agreamos la conjutno de estados de this los estados e1 y e2 nuevos
        this.conjuntoEstados.add(e1);
        this.conjuntoEstados.add(e2);

        //Agregamos los estdos de f2 con los de this, su conjunto seran el afn resultante
        this.conjuntoEstados.addAll(f2.conjuntoEstados);

        //eliminamos el automata f2   
        f2 = null;
        return this;
    }

    /***
     *
     * @param f2 auomata AFN
     * @return Retorna AFN de concatenar this con f2
     */
    public AFN concatenacion(AFN f2) {

        for (Estado e : this.estadosAceptacion) {
            e.setEdoAceptacion(false);
            f2.estadoInicial.getTransitionTo().forEach(t -> {
                e.SetTransicion(t);
            });
        }
        this.estadosAceptacion.clear();
        //Unimos alfabetos de this y f2
        this.alfabeto.addAll(f2.alfabeto);

        //Unimos los estados de aceptacion de el automata this y el automata f2
        this.estadosAceptacion.addAll(f2.estadosAceptacion);

        //Agregamos el estado de aceptacion de f2 a this
        f2.conjuntoEstados.remove(f2.estadoInicial);

        this.conjuntoEstados.addAll(f2.conjuntoEstados);
        return this;
    }

    /***
     *
     * @return Concatenacion de la cerradura estrella fromada con this
     */
    public AFN starLock() {
        Estado e1 = new Estado();
        Estado e2 = new Estado();
        e1.SetTransicion(new Transicion(epsilon, this.estadoInicial));
        for (Estado e : this.estadosAceptacion) {
            e.SetTransicion(new Transicion(epsilon, e2));
            e.SetTransicion(new Transicion(epsilon, this.estadoInicial));
            e.setEdoAceptacion(false);
        }
        e1.SetTransicion(new Transicion(epsilon, e2));
        e2.setEdoAceptacion(true);
        this.estadosAceptacion.clear();
        this.estadosAceptacion.add(e2);
        this.estadoInicial = e1;
        this.conjuntoEstados.add(e1);
        this.conjuntoEstados.add(e2);

        return this;
    }

    public AFN positiveLock() {
        Estado e1 = new Estado();
        Estado e2 = new Estado();
        e2.setEdoAceptacion(true);

        e1.SetTransicion(new Transicion(epsilon, this.estadoInicial));
        this.estadosAceptacion.forEach(e -> {
            e.SetTransicion(new Transicion(epsilon, e2));
            e.SetTransicion(new Transicion(epsilon, this.estadoInicial));
            e.setEdoAceptacion(false);
        });

        this.estadosAceptacion.clear();
        this.estadosAceptacion.add(e2);
        this.conjuntoEstados.add(e1);
        this.conjuntoEstados.add(e2);
        this.estadoInicial = e1;
        return this;
    }

    public AFN lock_interrogante() {
        Estado e1 = new Estado();
        Estado e2 = new Estado();

        e1.SetTransicion(new Transicion(epsilon, this.estadoInicial));
        e2.setEdoAceptacion(true);
        this.estadosAceptacion.forEach(e -> {
            e.SetTransicion(new Transicion(epsilon, e2));
            e.setEdoAceptacion(false);
        });

        this.estadosAceptacion.clear();
        this.estadosAceptacion.add(e2);
        this.estadoInicial = e1;
        this.estadoInicial.SetTransicion(new Transicion(epsilon, e2));

        //Agreamos al conjutno de estados los estados e1 y e2 nuevos
        this.conjuntoEstados.add(e1);
        this.conjuntoEstados.add(e2);
        return this;
    }

    public void printAFN() {
        this.conjuntoEstados.forEach((e) -> {
            e.getTransitionTo().forEach(t -> {
                System.out.println("Estado  :" + e.getIdEdo() + " transita al estado " + t.getEdo_destino().getIdEdo() + " con el simbolo " + t.getSimbolo());
            });
        });
    }

    public AFD convertToAFD() {
        AFD D = new AFD();
        LinkedList<HashSet<Estado>> Sn = new LinkedList<>();
        HashMap<HashSet<Estado>, Estado> mapa = new LinkedHashMap<>();
        Sn.addLast(this.lock(this.estadoInicial));
        mapa.put(Sn.getFirst(), D.getEstadoInicial());
        while (!Sn.isEmpty()) {
            HashSet<Estado> Si = Sn.removeFirst();
            this.alfabeto.forEach(simbolo -> {
                HashSet<Estado> Ai = this.Go_to(Si, simbolo);
                if (!Ai.isEmpty()) {
                    if (!mapa.containsKey(Ai)) {
                        Sn.addLast(Ai);
                        Estado e = new Estado();
                        Ai.forEach(edo -> {
                            if (this.estadosAceptacion.contains(edo)) {
                                e.setEdoAceptacion(true);
                                e.setToken(edo.getToken());
                                D.addEstadosAceptacion(e);
                            }
                        });
                        mapa.get(Si).SetTransicion(new Transicion(simbolo, e));
                        D.addConjuntoEstados(e);
                        mapa.put(Ai, e);
                    } else
                        mapa.get(Si).SetTransicion(new Transicion(simbolo, mapa.get(Ai)));
                }
            });
        }
        D.addAlfabeto(this.alfabeto);
        return D;
    }

    public static AFN makeAFNEspecial(AFN A) {
        especial.estadoInicial.SetTransicion(new Transicion("EPSILON", A.estadoInicial));
        especial.conjuntoEstados.add(especial.estadoInicial);
        especial.conjuntoEstados.addAll(A.conjuntoEstados);
        especial.estadosAceptacion.addAll(A.estadosAceptacion);
        especial.alfabeto.addAll(A.getAlfabeto());
        return especial;
    }

    public AFN makeAFNSpecial(ArrayList<AFN> A) {
        AFN S = new AFN();
        A.forEach(afn -> {
            S.estadoInicial.SetTransicion(new Transicion("EPSILON", afn.estadoInicial));
            S.conjuntoEstados.add(especial.estadoInicial);
            S.conjuntoEstados.addAll(afn.conjuntoEstados);
            S.estadosAceptacion.addAll(afn.estadosAceptacion);
            S.alfabeto.addAll(afn.getAlfabeto());
        });
        return S;
    }

    public AFN asignaToken(int t) {
        this.estadosAceptacion.forEach(e -> {
            e.setToken(t);
        });
        return this;
    }


}
