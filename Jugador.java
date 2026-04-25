import java.util.*;

public class Jugador {
    private String nombre;
    private Mano mano;
    private boolean esHumano;

    public Jugador(String nombre, boolean esHumano) {
        this.nombre = nombre;
        this.mano = new Mano();
        this.esHumano = esHumano;
    }


    public String getNombre() {
        return nombre;
    }

    public List<Carta> getMano() {
        return mano.getMano();
    }

    public boolean sinCartas() {
        return mano.sinCartas();
    }

    public boolean esHumano() {
        return esHumano;
    }

    public boolean tieneJugadaValida(Carta cartaMesa) {
        for (Carta c : mano.getMano()) {
            if (c.esValida(cartaMesa)) return true;
        }
        return false;
    }


    public void robarCarta(Mazo mazo) {
        Carta carta = mazo.robar();
        if (carta != null) {
            mano.add(carta);
        }
    }

    

    public void jugarTurno(JuegoUno juego) {
        if (esHumano) {
            jugarTurnoHumano(juego);
        } else {
            jugarTurnoIA(juego);
        }
    }



    private void jugarTurnoHumano(JuegoUno juego) {
        Scanner scanner = juego.getScanner();

        System.out.println("\nTus cartas:");
        for (int i = 0; i < mano.size(); i++) {
            System.out.println("  [" + i + "] " + mano.obtenerCarta(i));
        }
        System.out.println("Selecciona índice para jugar, o -1 para robar.");
        System.out.println("Si te quedas con 1 carta escribe: índice,uno  (ej: 2,uno)");

        while (true) {
            System.out.print("> ");
            String entrada = scanner.nextLine().trim().toLowerCase();
            if (entrada.isEmpty()) continue;

            // Robar
            if (entrada.equals("-1")) {
                robarCarta(juego.getMazo());
                System.out.println("Robaste una carta.");
                return;
            }

            boolean dijoUno = entrada.contains("uno");
            String[] partes = entrada.split("[ ,]+");

            try {
                int opcion = Integer.parseInt(partes[0]);

                if (opcion < 0 || opcion >= mano.size()) {
                    System.out.println("Índice fuera de rango (0-" + (mano.size() - 1) + "). Intenta de nuevo.");
                    continue;
                }

                Carta seleccionada = mano.obtenerCarta(opcion);

                if (!seleccionada.esValida(juego.getCartaMesa())) {
                    System.out.println("Carta inválida: " + seleccionada +
                            " no coincide con " + juego.getCartaMesa() + ". Intenta de nuevo.");
                    continue;
                }

                mano.remove(opcion);
                System.out.println(nombre+" jugo "+seleccionada);
                juego.setCartaMesa(seleccionada);

                // Regla UNO verificar si dijo uno cuando tenga una carta
                if (mano.size() == 1) {
                    if (dijoUno) {
                        System.out.println("¡UNO!");
                    } else {
                        System.out.println("No dijiste UNO. Castigo: robas 2 cartas.");
                        robarCarta(juego.getMazo());
                        robarCarta(juego.getMazo());
                    }
                } else if (dijoUno) {
                    System.out.println("Aún no tienes una sola carta.");
                }

                juego.aplicarEfecto(seleccionada, this);
                return;

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingresa un número.");
            }
        }
    }

    //  Turno IA básica

    private void jugarTurnoIA(JuegoUno juego) {
        Carta cartaMesa = juego.getCartaMesa();

        // 1. Buscar carta válida en mano
        for (int i = 0; i < mano.size(); i++) {
            Carta carta = mano.obtenerCarta(i);
            if (carta.esValida(cartaMesa)) {
                mano.remove(i);
                juego.setCartaMesa(carta);
                System.out.println(nombre + " jugó: " + carta);
                if (mano.size() == 1) System.out.println(nombre + " dice: ¡UNO!");
                juego.aplicarEfecto(carta, this);
                return;
            }
        }

        // 2. Robar
        Carta robada = juego.getMazo().robar();
        if (robada != null) {
            System.out.println(nombre + " no tiene jugada válida y roba una carta.");
            mano.add(robada);
            System.out.println(nombre + " pasa su turno.");
        } else {
            System.out.println(nombre + " no puede robar (mazo vacío) y pasa turno.");
        }
    }
    //  Elegir color (humano pregunta, IA calcula)

    public String elegirColor(JuegoUno juego) {
        if (!esHumano) {
            return elegirColorIA();
        }

        Scanner scanner = juego.getScanner();
        String[] colores = {"Rojo", "Azul", "Verde", "Amarillo"};
        System.out.println("Elige color: 1-Rojo  2-Azul  3-Verde  4-Amarillo");

        while (true) {
            System.out.print("> ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= 1 && opcion <= 4) return colores[opcion - 1];
                System.out.println("Opción inválida. Elige entre 1 y 4.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingresa un número entre 1 y 4.");
            }
        }
    }

    // La IA elige el color más frecuente en su mano
    private String elegirColorIA() {
        Map<String, Integer> frecuencia = new HashMap<>();
        for (Carta c : mano.getMano()) {
            String col = c.getColor();
            if (!col.equals("negro") && !col.equals("negra")) {
                frecuencia.put(col, frecuencia.getOrDefault(col, 0) + 1);
            }
        }
        if (frecuencia.isEmpty()) return "Rojo";
        return Collections.max(frecuencia.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
