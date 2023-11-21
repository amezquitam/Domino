

public class Main {

    public static void main(String[] args) {
        Domino domino = new Domino();
        DominoInterfaz interfaz = new DominoJOptionPane(domino);

        interfaz.agregarJugador("jugador 1", new JugadorArtificial(interfaz, "robin"));
        interfaz.agregarJugador("jugador 2", new JugadorArtificial(interfaz, "nier"));

        domino.repartirFichas();

        interfaz.reportarMayorDoble();

        while (!interfaz.debeCerrarse()) {
            interfaz.juega();
        }

        if (domino.alguienGano()) {
            interfaz.informar("Felicidades %s, haz ganado".formatted(domino.ganador()));
        } else if (domino.hayEmpate()) {
            interfaz.informar("Empate... pipipipi");
        }

        interfaz.informar("Hasta luego");
    }
}
