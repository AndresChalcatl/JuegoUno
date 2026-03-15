# JuegoUno
Desarrollo de codigo para juego UNO en JAVA utilizando Programación Orientada a Objetos (POO).

La versión desarrollada incluye únicamente las reglas básicas:
- Cartas numéricas del 0 al 9
- Cuatro colores (Rojo, Azul, Verde, Amarillo)
- Juego en consola
- Modo Jugador vs CPU
- Sin cartas especiales (+2, reversa, salto)
- Sin comodines


# El sistema está dividido en cinco clases principales:

- `Main` → Punto de entrada del programa
- `JuegoUno` → Controlador principal del juego
- `Jugador` → Representa a los jugadores
- `Mazo` → Administra las cartas del juego
- `Carta` → Modela una carta individual

# Funcionamiento 
1. Se crea y baraja el mazo.
2. Se reparten 7 cartas a cada jugador.
3. Se coloca una carta inicial en la mesa.
4. Los jugadores alternan turnos.
5. Una carta puede jugarse si coincide en número o color.
6. El jugador al tirar la penultima carta debe escribir UNO, de lo contrario se le agregan dos cartas.
7. Gana el jugador que se quede sin cartas.
