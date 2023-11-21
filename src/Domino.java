import java.util.*;

public class Domino {
    private final LinkedList<FichaEnlazada> mesa;
    private final List<Jugador> jugadores;
    private final List<Ficha> fichas;
    private int turno;

    public Domino() {
        jugadores = new ArrayList<>();
        fichas = generarFichas();
        mesa = new LinkedList<>();
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void repartirFichas() {
        assert (2 <= jugadores.size() && jugadores.size() <= 4);

        for (Jugador jugador : jugadores) {
            for (int i = 0; i < 7; i++) {
                jugador.agregarFicha(fichas.remove(0));
            }
        }
    }

    public Jugador jugadorMayorDoble() {
        return jugadores.stream()
                .filter(Jugador::tieneDoble)
                .max(Comparator.comparingInt(Jugador::mayorDoble))
                .orElse(null);
    }

    public boolean alguienGano() {
        return jugadores.stream().anyMatch(Jugador::noTieneFichas);
    }

    public boolean hayEmpate() {
        return  (fichas.isEmpty() &&
                jugadores.stream().noneMatch(jugador -> jugador.tienePara(primeraFicha().getValorIzq())) &&
                jugadores.stream().noneMatch(jugador -> jugador.tienePara(ultimaFicha().getValorDer())));
    }

    public String ganador() {
        Jugador ganador = jugadores.stream().filter(Jugador::noTieneFichas).findFirst().orElse(null);
        return ganador != null ? ganador.getNombre() : null;
    }

    public Jugador jugando() {
        return jugadores.get(turno);
    }

    public void decidirPrimerTurno(Jugador jugador) {
        if (jugador != null)
            turno = jugadores.indexOf(jugador);
        else
            turno = new Random().nextInt(0, jugadores.size());
    }

    private List<Ficha> generarFichas() {
        List<Ficha> res = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            for (int j = i; j < 7; j++) {
                res.add(new Ficha(i, j));
            }
        }

        Collections.shuffle(res, new Random());
        Collections.shuffle(res, new Random());

        return res;
    }

    public FichaEnlazada primeraFicha() {
        return mesa.isEmpty() ? null : mesa.getFirst();
    }

    public FichaEnlazada ultimaFicha() {
        return mesa.isEmpty() ? null : mesa.getLast();
    }

    public boolean ponerAlInicio(Ficha ficha) {
        FichaEnlazada aPoner = new FichaEnlazada(ficha);

        if (mesa.isEmpty()) {
            mesa.addFirst(aPoner);
            jugando().removerFicha(aPoner.getFicha());
            cambiarTurno();
            return true;
        }

        if (FichaEnlazada.enlazar(mesa.getFirst(), aPoner, true)) {
            mesa.addFirst(aPoner);
            jugando().removerFicha(aPoner.getFicha());
            cambiarTurno();
            return true;
        }

        return false;
    }

    public boolean ponerAlFinal(Ficha ficha) {
        FichaEnlazada aPoner = new FichaEnlazada(ficha);

        if (FichaEnlazada.enlazar(mesa.getLast(), aPoner, false)) {
            mesa.addLast(aPoner);
            jugando().removerFicha(aPoner.getFicha());
            cambiarTurno();
            return true;
        }
        return false;
    }

    public void cambiarTurno() {
        turno = (turno + 1) % jugadores.size();
    }

    public boolean tomarDelMonton() {
        if (fichas.isEmpty()) return false;
        jugando().agregarFicha(fichas.remove(0));
        return true;
    }
}