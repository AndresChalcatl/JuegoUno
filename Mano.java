import java.util.ArrayList;

public class Mano {
    private ArrayList<Carta> mano;

    public Mano() {
        this.mano = new ArrayList<>();
    }

    public void add(Carta carta) {
        mano.add(carta);
    }

    public Carta obtenerCarta(int indice) {
        return mano.get(indice);
    }

    public Carta remove(int indice) {
        return mano.remove(indice);
    }

    public ArrayList<Carta> getMano() {
        return mano;
    }

    public int size() {
        return mano.size();
    }

    public boolean sinCartas() {
        return mano.isEmpty();
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
}
