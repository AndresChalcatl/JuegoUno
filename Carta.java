public class Carta {
    private String color;
    private int numero;
    public Carta(String color, int numero) {
        this.color = color;
        this.numero = numero;
    }
    public String getColor() {
        return color;
    }
    public int getNumero() {
        return numero;
    }
    public boolean esValida(Carta cartaMesa) {
        return this.color.equals(cartaMesa.color) ||
               this.numero == cartaMesa.numero;
    }
    @Override
    public String toString() {
        return color + " " + numero;
    }
}