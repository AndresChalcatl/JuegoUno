import java.util.*;
public class Jugador {
    private String nombre;
    private ArrayList<Carta> mano;
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }
    public void robarCarta(Mazo mazo) {
        Carta carta = mazo.robar();
        if (carta != null) {
            mano.add(carta);
        }
    }
    public Carta jugarCarta(int indice, Carta cartaMesa) {
    if (indice >= 0 && indice < mano.size()) {
        Carta seleccionada = mano.get(indice);
        if (seleccionada.esValida(cartaMesa)) {
            mano.remove(indice);
            return seleccionada; 
        }
    }
    return null;
}
    public Carta obtenerCarta(int indice) {
        return mano.get(indice);
    }
    public ArrayList<Carta> getMano() {
        return mano;
    }
    public boolean sinCartas() {
        return mano.isEmpty();
    }
    public String getNombre() {
        return nombre;
    }
}