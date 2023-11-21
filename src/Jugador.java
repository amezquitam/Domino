import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Jugador {
    protected DominoInterfaz dominoInterfaz;
    protected List<Ficha> fichas;

    public Jugador(DominoInterfaz dominoInterfaz) {
        this.dominoInterfaz = dominoInterfaz;
        fichas = new ArrayList<>();
    }

    public boolean tienePara(int valor) {
        return fichas.stream().anyMatch(ficha -> ficha.contiene(valor));
    }

    public void removerFicha(Ficha ficha) {
        fichas.remove(ficha);
    }

    public abstract void inicializar(String nombre);

    public abstract String getNombre();

    public abstract void juega();

    public void agregarFicha(Ficha ficha) {
        fichas.add(ficha);
    }

    public int mayorDoble() {
        Ficha mayorDoble = mayorDobleFicha();
        return mayorDoble != null ? mayorDoble.getDer() : -1;
    }

    public Ficha mayorDobleFicha() {
        return fichas.stream()
                .filter(Ficha::isDouble)
                .max(Comparator.comparingInt(Ficha::getIzq))
                .orElse(null);
    }

    public boolean tieneDoble() {
        return fichas.stream().anyMatch(Ficha::isDouble);
    }

    public boolean noTieneFichas() {
        return fichas.isEmpty();
    }
}
