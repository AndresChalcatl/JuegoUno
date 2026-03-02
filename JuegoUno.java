import java.util.*;

public class JuegoUno {

    private Mazo mazo;
    private Jugador jugador;
    private Jugador cpu;
    private Carta cartaMesa;
    private Scanner scanner;

    public JuegoUno() {
        mazo = new Mazo();
        jugador = new Jugador("Jugador");
        cpu = new Jugador("CPU");
        scanner = new Scanner(System.in);
        iniciarJuego();
    }

    private void iniciarJuego() {

        // Repartir cartas
        for (int i = 0; i < 7; i++) {
            jugador.robarCarta(mazo);
            cpu.robarCarta(mazo);
        }

        cartaMesa = mazo.robar();
        System.out.println("Carta inicial: " + cartaMesa);

        boolean turnoJugador = true;

        while (true) {

            if (turnoJugador) {
                turnoJugador();

                if (jugador.sinCartas()) {
                    System.out.println("¡Ganaste!");
                    break;
                }

            } else {
                turnoCPU();

                if (cpu.sinCartas()) {
                    System.out.println("CPU gana");
                    break;
                }
            }

            turnoJugador = !turnoJugador;
        }
    }


    private void turnoJugador() {

        System.out.println("\n===== TU TURNO =====");
        System.out.println("Carta en mesa: " + cartaMesa);

        for (int i = 0; i < jugador.getMano().size(); i++) {
            System.out.println(i + ": " + jugador.getMano().get(i));
        }

        System.out.println("Elige índice o -1 para robar.");
        System.out.println("Si te quedas con 1 carta debes decir UNO de la forma opcion,uno.");

        while (true) {

            System.out.print("> ");
            String entrada = scanner.nextLine().trim().toLowerCase();

            if (entrada.isEmpty()) continue;

            // Robar
            if (entrada.equals("-1")) {
                jugador.robarCarta(mazo);
                System.out.println("Robaste una carta.");
                break;
            }

            // Detectar si dijo UNO
            boolean dijoUno = entrada.contains("uno");


            String[] partes = entrada.split("[ ,]+");

            int opcion;

            try {
                opcion = Integer.parseInt(partes[0]);
            } catch (Exception e) {
                System.out.println("Entrada inválida.");
                continue;
            }

            if (opcion < 0 || opcion >= jugador.getMano().size()) {
                System.out.println("Índice fuera de rango.");
                continue;
            }

            Carta jugada = jugador.jugarCarta(opcion, cartaMesa);

            if (jugada == null) {
                System.out.println("Movimiento inválido.");
                continue;
            }

            cartaMesa = jugada;

            if (jugador.getMano().size() == 1) {

                if (dijoUno) {
                    System.out.println("¡UNO!");
                } else {
                    System.out.println("Te quedaste con 1 carta y NO dijiste UNO.");
                    System.out.println("Castigo: robas 2 cartas.");
                    jugador.robarCarta(mazo);
                    jugador.robarCarta(mazo);
                }

            } else {

                if (dijoUno) {
                    System.out.println("Aún no tienes una sola carta.");
                }
            }

            break;
        }
    }


    private void turnoCPU() {

        System.out.println("\n===== TURNO CPU =====");

        boolean jugo = false;

        for (int i = 0; i < cpu.getMano().size(); i++) {

            Carta carta = cpu.getMano().get(i);

            if (carta.esValida(cartaMesa)) {

                cartaMesa = carta;
                cpu.getMano().remove(i);

                System.out.println("CPU jugó: " + carta);

                if (cpu.getMano().size() == 1) {
                    System.out.println("CPU dice: ¡UNO!");
                }

                jugo = true;
                break;
            }
        }

        if (!jugo) {
            cpu.robarCarta(mazo);
            System.out.println("CPU roba carta.");
        }
    }

    public static void main(String[] args) {
        new JuegoUno();
    }
}