
public abstract class DominoInterfaz {
    public final Domino domino;

    public DominoInterfaz(Domino domino) {
        this.domino = domino;
    }

    public void agregarJugador(String nombre, Jugador jugador) {
        jugador.inicializar(nombre);
        domino.agregarJugador(jugador);
    }

    public abstract String preguntar(String pregunta);

    public abstract void informar(String mensaje);

    public abstract boolean debeCerrarse();

    public void reportarMayorDoble() {
        Jugador jugador = domino.jugadorMayorDoble();

        if (jugador != null)
            informar("El jugador " + jugador.getNombre() + " tiene el doble mayor");
        else
            informar("Ningun jugador tiene fichas dobles, se elegirá al azar");

        domino.decidirPrimerTurno(jugador);
    }

    public void juega() {
        domino.jugando().juega();
    }

    public abstract void cerrar();

    public abstract void iniciarMensajeCompuesto();

    public abstract void terminarMensajeCompuesto();

    public abstract void compMensaje(String mensaje);

    public abstract String enviarMensajeCompuestoComoPregunta();
    public abstract String enviarMensajeCompuestoComoPreguntaMultiple(String... opciones);
}
