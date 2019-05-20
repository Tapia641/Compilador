package LR;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class LR0Table extends JFrame {
    private final ArrayList<String> cols = new ArrayList<>();
    private final AFD automata;
    private int m;
    private int n;
    private Object tabla[][];
    private String[] columnNames;
    private final FirFoll operation;
    private final LinkedList<HashSet<LinkedList<Nodo>>> sn;
    private final HashSet<String> vt;
    private final HashSet<String> vn;
    private final LinkedList<LinkedList<Nodo>> rules;

    public LR0Table(LR0 F) {
        this.automata = F.getAutomata();
        this.sn = F.getSnResultSet();
        this.operation = new FirFoll(F.getGrammar());
        this.vt = F.getVT();
        this.vn = F.getVN();
        this.rules = F.getNumeratedRules();
        createTable();
    }


    private void createTable() {
        cols.add("Id estado");//Nombre de la primara columna
        vt.forEach(s -> {
            cols.add(s);
        }); //se llena la cabecera de la tabla con los simbolos termnales
        cols.add("$"); //Se agrega el símbolo de pesos despues de los terminales
        vn.forEach(s -> {
            cols.add(s);
        });//se llena la cabecera de la tabla con los simbolos no termnales
        m = automata.getConjuntoEstados().size(); //cantidad de filas
        n = automata.getAlfabeto().size() + 2; //cantidad de columnas
        tabla = new Object[m][n];
        //Nombre de las colmunas en un arreglo de String
        columnNames = cols.toArray(new String[cols.size()]);
        //Construimos la tabla LR0 con desplazamientos y reducciones
        for (int i = 0; i < m; i++) {
            int k = i;
            Estado e = automata.getConjuntoEstados().get(i);
            tabla[i][0] = e.getIdEdo();
            //Desplazamientos
            e.getTransitionTo().forEach(t -> {
                String displacement = "d";
                for (int j = 0; j < n - 1; j++) {
                    if (t.getSimbolo().equals(columnNames[j + 1])) {
                        if (vt.contains(t.getSimbolo())) {
                            displacement += t.getEdo_destino().getIdEdo();
                            tabla[k][j + 1] = displacement;
                        } else
                            tabla[k][j + 1] = t.getEdo_destino().getIdEdo();
                    }
                }
            });
            //Reducciones            
            HashSet<LinkedList<Nodo>> endPoints = LRTools.findEndPoints(sn.get(i));
            if (!endPoints.isEmpty()) {
                endPoints.forEach(item -> {
                    int numrule = LRTools.IndexOfRule(item, rules);
                    String reduction = "r" + numrule;
                    HashSet<String> symb = operation.Follow(item.getFirst().getSimbolo());
                    symb.forEach(s -> {
                        for (int j = 0; j < n - 1; j++) {
                            if (s.equals(columnNames[j + 1]))
                                tabla[k][j + 1] = reduction;
                        }
                    });
                });
            }
        }

        DefaultTableModel dtm = new DefaultTableModel(tabla, columnNames);
        final JTable table = new JTable(dtm);
        table.setPreferredScrollableViewportSize(new Dimension(450, 200));
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public Object[][] getTabla() {
        return this.tabla;
    }

}
