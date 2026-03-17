public class Carta {
    public enum Tipo{ //enumerado para tipos
        NUMERO,
        REVERSA,
        SALTO,
        ROBA2,
        ROBA4,
        COMODIN
    }

    private String color;
    private int numero; //solo para numeros
    private Tipo tipo;

    //constructor para cartas numericas
    public Carta(String color, int numero) {
        this.color = color;
        this.numero = numero;
        this.tipo = Tipo.NUMERO;
    }
    //constructor para cartas especiales 
    public Carta(String color,Tipo tipo){
        this.color = color;
        this.numero = -1;
        this.tipo = tipo;
    }

    public String getColor() {
        return color;
    }
    public int getNumero() {
        return numero;
    }

    public Tipo getTipo(){
        return tipo;
    }

    public boolean esValida(Carta cartaMesa) {
        return this.color.equals(cartaMesa.color) ||
               this.numero == cartaMesa.numero ||
               this.tipo == Tipo.COMODIN ||
               this.tipo == Tipo.ROBA4;
    }
    @Override
    public String toString() {
        if(tipo == Tipo.NUMERO)
            return color + " " + numero;
        return color + " " + tipo;
    }
}
