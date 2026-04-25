import java.util.*;

public class JuegoUno {

    private Mazo mazo;
    private List<Jugador> jugadores;
    private Carta cartaMesa;
    private Scanner scanner;
    private int turnoActual;
    private int direccion; // 1 = horario  /  -1 = inverso

    public JuegoUno() {
        mazo      = new Mazo();
        scanner   = new Scanner(System.in);
        jugadores = new ArrayList<>();
        direccion = 1;
        turnoActual = 0;
        iniciarJuego();
    }

            
    private void iniciarJuego() {
    
        System.out.print("Ingresa tu nombre: ");
        String nombreHumano = scanner.nextLine().trim();
        if (nombreHumano.isEmpty()) nombreHumano = "Jugador";

            jugadores.add(new Jugador(nombreHumano, true));
        jugadores.add(new Jugador("Gaby",        false));
        jugadores.add(new Jugador("Eli",        false));
        jugadores.add(new Jugador("Pablo",        false));

        repartirCartas();

        // Carta inicial: debe ser numérica
        do {
            cartaMesa = mazo.robar();
        } while (cartaMesa != null && cartaMesa.getTipo() != Carta.Tipo.NUMERO);

        System.out.println("\nCarta inicial: " + cartaMesa);
        bucleJuego();
    }

    private void repartirCartas() {
        for (int i = 0; i < 7; i++) {
            for (Jugador j : jugadores) {
                j.robarCarta(mazo);
            }
        }
    }

    
    private void bucleJuego() {
        while (true) {
            Jugador actual = jugadores.get(turnoActual);
            mostrarEstado(actual);

            actual.jugarTurno(this);

            if (actual.sinCartas()) {
                System.out.println("\n¡" + actual.getNombre() + " ha ganado!");
                break;
            }

            if (!actual.esHumano()) {
                esperarEnter();
            }

            avanzarTurno();
        }
    }

    
    private void mostrarEstado(Jugador actual) {
        System.out.println("\n====================================");
        System.out.println("Turno de: " + actual.getNombre());
        System.out.println("Carta en mesa: " + cartaMesa);
        System.out.println("====================================");
    }

    // Pausa para que el jugador pueda leer los movimientos de la IA
    private void esperarEnter() {
        System.out.println("\n[Presiona Enter para continuar...]");
        scanner.nextLine();
    }

    // Avanza un paso en la dirección actual
    public void avanzarTurno() {
        turnoActual = (turnoActual + direccion + jugadores.size()) % jugadores.size();
    }

    // Devuelve el jugador que recibirá el próximo turno sin modificar el índice
    public Jugador siguienteJugador() {
        int siguiente = (turnoActual + direccion + jugadores.size()) % jugadores.size();
        return jugadores.get(siguiente);
    }



    public void aplicarEfecto(Carta carta, Jugador jugadorActual) {
        switch (carta.getTipo()) {

            case SALTO:
                System.out.println("¡SALTO! \n" + siguienteJugador().getNombre() + " pierde su turno.");
                avanzarTurno(); // salta al siguiente; bucle avanzará una vez más
                break;

            case REVERSA:
                System.out.println("¡REVERSA! El orden se invierte.");
                direccion *= -1; 
                break;

            case ROBA2:
                Jugador sig2 = siguienteJugador();
                System.out.println("¡ROBA 2! \n" + sig2.getNombre() + " roba 2 cartas y pierde turno.");
                sig2.robarCarta(mazo);
                sig2.robarCarta(mazo);
                avanzarTurno(); // salta al que robó
                break;

            case ROBA4:
                String colorR4 = jugadorActual.elegirColor(this);
                cartaMesa = new Carta(colorR4, Carta.Tipo.ROBA4);
                Jugador sig4 = siguienteJugador();
                System.out.println("¡ROBA 4! \n" + sig4.getNombre() +
                        " roba 4 cartas y pierde turno. Color: " + colorR4);
                for (int i = 0; i < 4; i++) sig4.robarCarta(mazo);
                avanzarTurno(); // salta al que robó
                break;

            case COMODIN:
                String colorC = jugadorActual.elegirColor(this);
                cartaMesa = new Carta(colorC, Carta.Tipo.COMODIN);
                System.out.println("COMODÍN: Color cambiado a " + colorC);
                break;

            default:
                // Carta número: turno pasa normalmente
                break;
        }
    }

    public Carta getCartaMesa() {
        return cartaMesa;
    }

    public void setCartaMesa(Carta carta) {
        this.cartaMesa = carta;
    }

    public Mazo getMazo() {
        return mazo;
    }

    public Scanner getScanner() {
        return scanner;
    }
}
