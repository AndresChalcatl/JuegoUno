import java.util.*;
public class Mazo {
    private Stack<Carta> cartas;
    public Mazo() {
        cartas = new Stack<>();
        inicializar();
        barajar();
    }
    private void inicializar() {
        String[] colores = {"Rojo", "Azul", "Verde", "Amarillo"};
        for (String color : colores) {
            for (int i = 0; i <= 9; i++) {
                cartas.push(new Carta(color, i));
            }
        }
    }
    private void barajar() {
        Collections.shuffle(cartas);
    }
    public Carta robar() {
        if (!cartas.isEmpty()) {
            return cartas.pop();
        }
        return null;
    }
    public boolean estaVacio() {
        return cartas.isEmpty();
    }
}