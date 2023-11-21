class JugadorArtificial extends Jugador {
    private String nombre;
    public JugadorArtificial(DominoInterfaz dominoInterfaz, String nombre) {
        super(dominoInterfaz);
        this.nombre = nombre;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void inicializar(String nombre) {
        dominoInterfaz.informar("(%s): Hola soy el bot %s".formatted(nombre, getNombre()));
    }

    @Override
    public void juega() {
        dominoInterfaz.informar("El bot %s está jugando".formatted(getNombre()));
        FichaEnlazada primera = dominoInterfaz.domino.primeraFicha();
        FichaEnlazada ultima = dominoInterfaz.domino.ultimaFicha();

        if (primera == null || ultima == null) {
            dominoInterfaz.domino.ponerAlInicio(mayorDobleFicha());
            return;
        }

        Ficha paraPonerAlInicio = fichas.stream()
                .filter(ficha -> ficha.contiene(primera.getValorIzq()))
                .findAny()
                .orElse(null);

        if (paraPonerAlInicio != null) {
            dominoInterfaz.domino.ponerAlInicio(paraPonerAlInicio);
            dominoInterfaz.informar("El jugador %s ha puesto la pieza %s al inicio".formatted(getNombre(), paraPonerAlInicio));
            return;
        }

        Ficha paraPonerAlFinal = fichas.stream()
                .filter(ficha -> ficha.contiene(ultima.getValorDer()))
                .findAny()
                .orElse(null);

        if (paraPonerAlFinal != null) {
            dominoInterfaz.domino.ponerAlFinal(paraPonerAlFinal);
            dominoInterfaz.informar("El jugador %s ha puesto la pieza %s al final".formatted(getNombre(), paraPonerAlFinal));
            return;
        }

        if (dominoInterfaz.domino.tomarDelMonton()) {
            dominoInterfaz.informar("El jugador %s ha tomado una pieza del montón".formatted(getNombre()));
            juega();
        } else {
            dominoInterfaz.informar("El jugador %s no tiene fichas para responder, cede el turno".formatted(getNombre()));
            dominoInterfaz.domino.cambiarTurno();
        }
    }
}

