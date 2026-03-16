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
        //llenar numeros
        for (String color : colores) {
            for (int i = 0; i <= 9; i++) {
                cartas.push(new Carta(color, i));
                if(i!=0){
                    cartas.push(new Carta(color, i));
                }
            }
            cartas.add(new Carta(color,Carta.Tipo.SALTO));
            cartas.add(new Carta(color,Carta.Tipo.SALTO));
            cartas.add(new Carta(color,Carta.Tipo.REVERSA));
            cartas.add(new Carta(color,Carta.Tipo.REVERSA));
            cartas.add(new Carta(color,Carta.Tipo.ROBA2));
            cartas.add(new Carta(color,Carta.Tipo.ROBA2));
        }
        //llenar cartas especiales
        for(int i = 0;i < 4; ){
            cartas.add(new Carta("negra",Carta.Tipo.COMODIN));
            cartas.add(new Carta("negro",Carta.Tipo.ROBA4));
        }

        //System.out.println(cartas.size());

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
