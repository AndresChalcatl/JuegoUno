import java.util.*;

public class JuegoUno {

    private Mazo mazo;
    private Jugador jugador;
    private Jugador cpu;
    private Carta cartaMesa;
    private Scanner scanner;
    private boolean turnoJugador;

    public JuegoUno() {
        mazo = new Mazo();
        jugador = new Jugador("Jugador");
        cpu = new Jugador("CPU");
        scanner = new Scanner(System.in);
        turnoJugador = true;
        iniciarJuego();
    }

    private void iniciarJuego() {
        repartirCartas();
        //esto nos va a segurar que la carta inicial no sea especial
        do {
            cartaMesa = mazo.robar();
        }    while (cartaMesa != null && cartaMesa.getTipo() != Carta.Tipo.NUMERO);

        System.out.println("Carta inicial: " + cartaMesa);
        bucleJuego();
    }
            
        // Repartir cartas
    private void repartirCartas(){
        for (int i = 0; i < 7; i++) {
            jugador.robarCarta(mazo);
            cpu.robarCarta(mazo);
        }
    }
        
    private void bucleJuego() {
        while (true) {
            if (turnoJugador) {
                turnoJugador();
                if (jugador.sinCartas()) { System.out.println("¡Ganaste eres un perrazo!"); break; }
            } else {
                turnoCPU();
                if (cpu.sinCartas()) { System.out.println("Te chingaste CPU gana."); break; }
            }
        }
    }

    private void turnoJugador() {
        System.out.println("\n===== TU TURNO =====");
        System.out.println("Carta en mesa: " + cartaMesa);
        mostrarMano();
        System.out.println("Elige índice o -1 para robar.");
        System.out.println("Si te quedas con 1 carta escribe: 0,uno");

        while (true) {
            System.out.print("> ");
            String entrada = scanner.nextLine().trim().toLowerCase();
            if (entrada.isEmpty()) continue;

            if (entrada.equals("-1")) {
                jugador.robarCarta(mazo);
                System.out.println("Robaste una carta.");
                turnoJugador = false;
                break;
            }

            boolean dijoUno = entrada.contains("uno");
            String[] partes = entrada.split("[ ,]+");

            try {
                int opcion = parsearOpcion(partes[0], jugador.getMano().size());
                Carta jugada = obtenerCartaValida(opcion);

                cartaMesa = jugada;
                verificarUno(dijoUno);
                aplicarEfecto(cartaMesa, true); // true = lo jugó el jugador
                break;

            } catch (NumberFormatException e) {
                System.out.println("Error: debes ingresar un número.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (CartaInvalidaExcep e) {
                System.out.println("Movimiento inválido: " + e.getMessage());
            }
        }
    }

    private void turnoCPU() {
        System.out.println("\n===== TURNO CPU =====");
        boolean jugo = false;

        for (int i = 0; i < cpu.getMano().size(); i++) {
            Carta carta = cpu.getMano().get(i);
            if (carta.esValida(cartaMesa)) {
                cpu.getMano().remove(i);
                cartaMesa = carta;
                System.out.println("CPU jugó: " + carta);
                if (cpu.getMano().size() == 1) System.out.println("CPU dice: ¡UNO!");
                aplicarEfecto(cartaMesa, false); // false = lo jugó la CPU
                jugo = true;
                break;
            }
        }

        if (!jugo) {
            cpu.robarCarta(mazo);
            System.out.println("CPU roba carta.");
            turnoJugador = true;
        }
    }

    private void aplicarEfecto(Carta carta, boolean loJugoElJugador) {
        switch (carta.getTipo()) {

            case SALTO:
                System.out.println("¡SALTO! El turno del rival es saltado.");
                turnoJugador = loJugoElJugador;
                break;

            case REVERSA:
                System.out.println("¡REVERSA! El orden se invierte.");
                turnoJugador = loJugoElJugador;
                break;

            case ROBA2:
                if (loJugoElJugador) {
                    System.out.println("¡ROBA 2! CPU roba 2 cartas.");
                    cpu.robarCarta(mazo);
                    cpu.robarCarta(mazo);
                    turnoJugador = true;
                } else {
                    System.out.println("¡ROBA 2! Robas 2 cartas.");
                    jugador.robarCarta(mazo);
                    jugador.robarCarta(mazo);
                    turnoJugador = false;
                }
                break;

            case ROBA4:
                if (loJugoElJugador) {
                    String colorElegido = elegirColor();
                    cartaMesa = new Carta(colorElegido, Carta.Tipo.ROBA4);
                    System.out.println("¡ROBA 4! CPU roba 4 cartas. Color: " + colorElegido);
                    for (int i = 0; i < 4; i++) cpu.robarCarta(mazo);
                    turnoJugador = true;
                } else {
                    String colorElegido = cpuElegirColor();
                    cartaMesa = new Carta(colorElegido, Carta.Tipo.ROBA4);
                    System.out.println("¡ROBA 4! Robas 4 cartas. CPU elige color: " + colorElegido);
                    for (int i = 0; i < 4; i++) jugador.robarCarta(mazo);
                    turnoJugador = false;
                }
                break;

            case COMODIN:
                if (loJugoElJugador) {
                    String colorElegido = elegirColor();
                    cartaMesa = new Carta(colorElegido, Carta.Tipo.COMODIN);
                    System.out.println("COMODÍN: Color cambiado a " + colorElegido);
                } else {
                    String colorElegido = cpuElegirColor();
                    cartaMesa = new Carta(colorElegido, Carta.Tipo.COMODIN);
                    System.out.println("CPU jugó COMODÍN: Color cambiado a " + colorElegido);
                }
                turnoJugador = !loJugoElJugador;
                break;

            default:
                turnoJugador = !loJugoElJugador;
                break;
        }
    }

    
    private String elegirColor() {
        String[] colores = {"Rojo", "Azul", "Verde", "Amarillo"};
        System.out.println("Elige color: 1-Rojo  2-Azul  3-Verde  4-Amarillo");
        while (true) {
            System.out.print("> ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= 1 && opcion <= 4) return colores[opcion - 1];
                System.out.println("Opción inválida. Elige entre 1 y 4.");
            } catch (NumberFormatException e) {
                System.out.println("Error: ingresa un número entre 1 y 4.");
            }
        }
    }

    private String cpuElegirColor() {
        // La CPU elige el color más frecuente en su mano
        Map<String, Integer> frecuencia = new HashMap<>();
        for (Carta c : cpu.getMano()) {
            if (!c.getColor().equals("negro") && !c.getColor().equals("negra")) {
                frecuencia.put(c.getColor(), frecuencia.getOrDefault(c.getColor(), 0) + 1);
            }
        }
        if (frecuencia.isEmpty()) return "Rojo"; // fallback
        return Collections.max(frecuencia.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private void mostrarMano() {
        List<Carta> mano = jugador.getMano();
        for (int i = 0; i < mano.size(); i++) {
            System.out.println(i + ": " + mano.get(i));
        }
    }

    private int parsearOpcion(String texto, int tamano)
            throws NumberFormatException, IndexOutOfBoundsException {
        int opcion = Integer.parseInt(texto);
        if (opcion < 0 || opcion >= tamano) {
            throw new IndexOutOfBoundsException(
                "Índice " + opcion + " fuera de rango (0-" + (tamano - 1) + ")."
            );
        }
        return opcion;
    }

    private Carta obtenerCartaValida(int opcion) throws CartaInvalidaExcep {
        Carta seleccionada = jugador.getMano().get(opcion);
        if (!seleccionada.esValida(cartaMesa)) {
            throw new CartaInvalidaExcep(
                "La carta " + seleccionada + " no coincide con " + cartaMesa
            );
        }
        jugador.getMano().remove(opcion);
        return seleccionada;
    }

    private void verificarUno(boolean dijoUno) {
        if (jugador.getMano().size() == 1) {
            if (dijoUno) {
                System.out.println("¡UNO!");
            } else {
                System.out.println("No dijiste UNO. Castigo: robas 2 cartas.");
                jugador.robarCarta(mazo);
                jugador.robarCarta(mazo);
            }
        } else if (dijoUno) {
            System.out.println("Aún no tienes una sola carta.");
        }
    }

    
    public static void main(String[] args) {
        new JuegoUno();
    }
}
