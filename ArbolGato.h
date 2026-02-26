/*
 * ArbolGato.h
 * TAD Árbol de Juego para el Gato (Tic-Tac-Toe)
 * 
 * Este TAD implementa un árbol n-ario para representar todos los posibles
 * estados del juego del Gato, permitiendo evaluar movimientos mediante
 * el algoritmo Minimax con profundidad configurable.
 */

#ifndef ARBOL_GATO_H
#define ARBOL_GATO_H

#define TAMANO_TABLERO 3
#define MAX_HIJOS 9
#define JUGADOR_X 'X'
#define JUGADOR_O 'O'
#define CELDA_VACIA ' '

/*
 * Estructura: NodoArbol
 * Representa un nodo en el árbol de juego.
 * Cada nodo contiene un estado del tablero y sus posibles movimientos (hijos).
 */
typedef struct NodoArbol {
    char tablero[TAMANO_TABLERO][TAMANO_TABLERO];  // Estado del tablero
    int evaluacion;                                  // Valor de evaluación del nodo
    struct NodoArbol **hijos;                        // Arreglo dinámico de punteros a hijos
    int numHijos;                                    // Cantidad de hijos
    int nivel;                                       // Nivel en el árbol (profundidad desde raíz)
    char jugadorTurno;                               // Jugador que mueve en este estado
    int fila;                                        // Fila del movimiento que llevó a este estado
    int columna;                                     // Columna del movimiento
} NodoArbol;

/*
 * Estructura: ArbolGato
 * TAD principal que representa el árbol completo del juego.
 */
typedef struct {
    NodoArbol *raiz;         // Raíz del árbol (estado actual)
    int profundidadMaxima;   // Profundidad máxima de análisis
    char jugadorMax;         // Jugador que maximiza (computadora)
    char jugadorMin;         // Jugador que minimiza (humano)
} ArbolGato;

/* ============================================================================
 * OPERACIONES DEL TAD ArbolGato
 * ============================================================================ */

/*
 * ArbolGato_crear
 * Crea un nuevo árbol de juego con el tablero inicial dado.
 * 
 * Parámetros:
 *   - tableroInicial: Estado inicial del tablero 3x3
 *   - profundidad: Profundidad máxima de análisis (número de movimientos futuros)
 *   - jugadorMax: Carácter del jugador que maximiza ('X' u 'O')
 *   - jugadorMin: Carácter del jugador que minimiza ('X' u 'O')
 * 
 * Retorna: Puntero al ArbolGato creado, o NULL si hay error
 */
ArbolGato* ArbolGato_crear(char tableroInicial[TAMANO_TABLERO][TAMANO_TABLERO], 
                           int profundidad, 
                           char jugadorMax, 
                           char jugadorMin);

/*
 * ArbolGato_construir
 * Construye el árbol de juego completo hasta la profundidad especificada.
 * Genera todos los posibles estados del juego.
 * 
 * Parámetros:
 *   - arbol: Puntero al árbol de juego
 */
void ArbolGato_construir(ArbolGato *arbol);

/*
 * ArbolGato_evaluar
 * Evalúa el árbol aplicando el algoritmo Minimax.
 * Propaga los valores desde las hojas hacia la raíz.
 * 
 * Parámetros:
 *   - arbol: Puntero al árbol de juego
 */
void ArbolGato_evaluar(ArbolGato *arbol);

/*
 * ArbolGato_mejorMovimiento
 * Encuentra el mejor movimiento para el jugador que maximiza.
 * 
 * Parámetros:
 *   - arbol: Puntero al árbol de juego
 *   - fila: Puntero donde se guardará la fila del mejor movimiento
 *   - columna: Puntero donde se guardará la columna del mejor movimiento
 * 
 * Retorna: 1 si encontró movimiento, 0 si no hay movimientos disponibles
 */
int ArbolGato_mejorMovimiento(ArbolGato *arbol, int *fila, int *columna);

/*
 * ArbolGato_movimientoAleatorio
 * Selecciona un movimiento completamente aleatorio.
 * Útil para nivel "Muy Fácil".
 * 
 * Parámetros:
 *   - arbol: Puntero al árbol de juego
 *   - fila: Puntero donde se guardará la fila del movimiento
 *   - columna: Puntero donde se guardará la columna del movimiento
 * 
 * Retorna: 1 si encontró movimiento, 0 si no hay movimientos disponibles
 */
int ArbolGato_movimientoAleatorio(ArbolGato *arbol, int *fila, int *columna);

/*
 * ArbolGato_movimientoConError
 * Selecciona el mejor movimiento pero con probabilidad de cometer errores.
 * Útil para niveles intermedios.
 * 
 * Parámetros:
 *   - arbol: Puntero al árbol de juego
 *   - fila: Puntero donde se guardará la fila del movimiento
 *   - columna: Puntero donde se guardará la columna del movimiento
 *   - probabilidadError: Probabilidad de error (0-100)
 * 
 * Retorna: 1 si encontró movimiento, 0 si no hay movimientos disponibles
 */
int ArbolGato_movimientoConError(ArbolGato *arbol, int *fila, int *columna, int probabilidadError);

/*
 * ArbolGato_destruir
 * Libera toda la memoria del árbol.
 * 
 * Parámetros:
 *   - arbol: Puntero al árbol a destruir
 */
void ArbolGato_destruir(ArbolGato *arbol);

/*
 * ArbolGato_imprimirArbol
 * Imprime la estructura del árbol con sus evaluaciones (para debug).
 * 
 * Parámetros:
 *   - arbol: Puntero al árbol de juego
 */
void ArbolGato_imprimirArbol(ArbolGato *arbol);

/* ============================================================================
 * FUNCIONES AUXILIARES DE TABLERO
 * ============================================================================ */

/*
 * Tablero_copiar
 * Copia un tablero a otro.
 */
void Tablero_copiar(char destino[TAMANO_TABLERO][TAMANO_TABLERO], 
                    char origen[TAMANO_TABLERO][TAMANO_TABLERO]);

/*
 * Tablero_imprimir
 * Imprime el tablero en formato visual.
 */
void Tablero_imprimir(char tablero[TAMANO_TABLERO][TAMANO_TABLERO]);

/*
 * Tablero_verificarGanador
 * Verifica si hay un ganador en el tablero.
 * Retorna: 'X', 'O', 'E' (empate), o ' ' (juego continúa)
 */
char Tablero_verificarGanador(char tablero[TAMANO_TABLERO][TAMANO_TABLERO]);

/*
 * Tablero_hayMovimientos
 * Verifica si quedan movimientos disponibles.
 * Retorna: 1 si hay movimientos, 0 si no
 */
int Tablero_hayMovimientos(char tablero[TAMANO_TABLERO][TAMANO_TABLERO]);

/*
 * Tablero_evaluarEstado
 * Función de evaluación que cuenta líneas abiertas según el proyecto:
 * Suma filas/columnas/diagonales abiertas del jugador
 * Resta las del oponente
 * +9 indica victoria, -9 indica derrota
 * 
 * Parámetros:
 *   - tablero: Estado del tablero a evaluar
 *   - jugadorMax: Jugador que maximiza
 *   - jugadorMin: Jugador que minimiza
 * 
 * Retorna: Valor de evaluación del estado
 */
int Tablero_evaluarEstado(char tablero[TAMANO_TABLERO][TAMANO_TABLERO], 
                         char jugadorMax, 
                         char jugadorMin);

/* ============================================================================
 * FUNCIONES ADICIONALES PARA NIVELES DE DIFICULTAD
 * ============================================================================ */

/*
 * ArbolGato_movimientoAleatorio
 * Selecciona un movimiento completamente aleatorio.
 * Útil para nivel "Muy Fácil".
 * 
 * Parámetros:
 *   - arbol: Puntero al árbol de juego
 *   - fila: Puntero donde se guardará la fila del movimiento
 *   - columna: Puntero donde se guardará la columna del movimiento
 * 
 * Retorna: 1 si encontró movimiento, 0 si no hay movimientos disponibles
 */
int ArbolGato_movimientoAleatorio(ArbolGato *arbol, int *fila, int *columna);

/*
 * ArbolGato_movimientoConError
 * Selecciona el mejor movimiento pero con probabilidad de cometer errores.
 * Útil para niveles intermedios.
 * 
 * Parámetros:
 *   - arbol: Puntero al árbol de juego
 *   - fila: Puntero donde se guardará la fila del movimiento
 *   - columna: Puntero donde se guardará la columna del movimiento
 *   - probabilidadError: Probabilidad de error (0-100)
 * 
 * Retorna: 1 si encontró movimiento, 0 si no hay movimientos disponibles
 */
int ArbolGato_movimientoConError(ArbolGato *arbol, int *fila, int *columna, int probabilidadError);

#endif // ARBOL_GATO_H