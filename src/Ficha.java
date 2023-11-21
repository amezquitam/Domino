
public class Ficha {
    private final int izq;
    private final int der;

    Ficha(int izq, int der) {
        assert (0 <= izq && izq <= 6);
        assert (0 <= der && der <= 6);
        this.izq = izq;
        this.der = der;
    }

    public int valorEnComun(Ficha otra) {
        if (izq == otra.izq) return izq;
        if (izq == otra.der) return izq;
        if (der == otra.izq) return der;
        if (der == otra.der) return der;
        return -1;
    }

    public boolean tieneValorEnComun(Ficha otra) {
        return izq == otra.izq || izq == otra.der || der == otra.izq || der == otra.der;
    }

    public boolean isDouble() {
        return izq == der;
    }

    public int getIzq() {
        return izq;
    }

    public int getDer() {
        return der;
    }

    public boolean contiene(int valor) {
        return izq == valor || der == valor;
    }

    @Override
    public String toString() {
        return "[%d|%d]".formatted(izq, der);
    }
}
