public class FichaEnlazada {
    private FichaEnlazada fichaIzq;
    private final Ficha ficha;
    private FichaEnlazada fichaDer;
    private boolean volteada;

    public static boolean enlazar(FichaEnlazada ficha1, FichaEnlazada ficha2, boolean aLaIzq) {
        if (ficha1 == null || ficha2 == null) {
            return false;
        }

        if (aLaIzq) {
            if (ficha1.getFichaIzq() == null) {
                if (ficha1.getValorIzq() == ficha2.getValorDer()) {
                    ficha1.setFichaIzq(ficha2);
                    ficha2.setFichaDer(ficha1);
                } else if (ficha1.getValorIzq() == ficha2.getValorIzq()) {
                    ficha2.voltear();
                    ficha1.setFichaIzq(ficha2);
                    ficha2.setFichaDer(ficha1);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        else {
            if (ficha1.getFichaDer() == null) {
                if (ficha1.getValorDer() == ficha2.getValorIzq()) {
                    ficha1.setFichaDer(ficha2);
                    ficha2.setFichaIzq(ficha1);
                }
                else if (ficha1.getValorDer() == ficha2.getValorDer()) {
                    ficha2.voltear();
                    ficha1.setFichaDer(ficha2);
                    ficha2.setFichaIzq(ficha1);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    public void voltear() {
        volteada = !volteada;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public FichaEnlazada(Ficha ficha) {
        this.ficha = ficha;
    }

    public FichaEnlazada getFichaIzq() {
        return volteada ? fichaDer : fichaIzq;
    }

    public void setFichaIzq(FichaEnlazada fichaIzq) {
        if (volteada) {
            this.fichaDer = fichaIzq;
        } else {
            this.fichaIzq = fichaIzq;
        }
    }

    public int getValorIzq() {
        return volteada ? ficha.getDer() : ficha.getIzq();
    }

    public int getValorDer() {
        return volteada ? ficha.getIzq() : ficha.getDer();
    }

    public FichaEnlazada getFichaDer() {
        return volteada ? fichaIzq : fichaDer;
    }

    public void setFichaDer(FichaEnlazada fichaDer) {
        if (volteada) {
            this.fichaIzq = fichaDer;
        } else {
            this.fichaDer = fichaDer;
        }
    }
}
