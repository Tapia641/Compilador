from sintactico.LR.lalr import LALR
from sintactico.LR.lr_cero import LR_CERO
from sintactico.LR.lr_uno import LR_UNO
import sys
#archivo = input('Introduce el nombre del archivo con la gramatica plis: ')
#tipo = input("""Tipos de tabla
    #1) LR(0)
    #2) LR(1)
    #3) LALR
#Selecciona alguna: """)
#archivo = "gramatica.txt"
#tipo = 1

def iniciar(archivo, tipo):
    if int(tipo) == 1:
        analizador = LR_CERO(archivo)
        analizador.obtener_conjuntos()
    elif int(tipo) == 2:
        analizador = LR_UNO(archivo)
        analizador.obtener_conjuntos()
    else:
        analizador = LALR(archivo)
        analizador.obtener_conjuntos()
        analizador.unir_conjuntos()
    analizador.construir_tabla()

def function():
    if len(sys.argv) >= 2:
        #print "La cadena introducida tiene",len(sys.argv[1]),"caracteres
        #print("1: ", sys.argv[0])
        #print("2: ", sys.argv[1])
        #print("3: ", sys.argv[2])
        iniciar(sys.argv[1], sys.argv[2])
    else:
        print ("Faltan parametros... ")


if __name__ == '__main__':
    function()
