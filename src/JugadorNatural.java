import java.util.function.BiFunction;

public class JugadorNatural extends Jugador {
    private String nombre;

    public JugadorNatural(DominoInterfaz dominoInterfaz) {
        super(dominoInterfaz);
    }

    private String listarFichas(boolean enumerated) {
        BiFunction<String, Ficha, String> func =
                ((String acc, Ficha ficha) -> acc + "%s[%d|%d]%s".formatted(
                        enumerated ? "%d. ".formatted(fichas.indexOf(ficha) + 1) : "",
                        ficha.getIzq(),
                        ficha.getDer(),
                        enumerated ? "\n" : " "
                ));
        return fichas.stream().reduce("", func, String::concat);
    }

    private String listarFichasEnMesa() {
        FichaEnlazada primeraFicha = dominoInterfaz.domino.primeraFicha();
        StringBuilder result = new StringBuilder();
        FichaEnlazada fichaActual = primeraFicha;

        while (fichaActual != null) {
            result.append("[%d|%d] ".formatted(fichaActual.getValorIzq(), fichaActual.getValorDer()));
            fichaActual = fichaActual.getFichaDer();
        }

        return result.toString();
    }

    @Override
    public void juega() {
        dominoInterfaz.iniciarMensajeCompuesto();
        dominoInterfaz.compMensaje("Jugando: %s\n".formatted(getNombre()));
        dominoInterfaz.compMensaje("Fichas en mesa: %s\n".formatted(listarFichasEnMesa()));
        dominoInterfaz.compMensaje("Piezas en mano: %s\n".formatted(listarFichas(false)));
        dominoInterfaz.compMensaje("Acciones: \n1. Poner pieza, \n2. Tomar del monton, \n3. Salir \n");
        dominoInterfaz.terminarMensajeCompuesto();
        String respuesta = dominoInterfaz.enviarMensajeCompuestoComoPregunta();
        int opcion;
        try {
            opcion = Integer.parseInt(respuesta);
        } catch (NumberFormatException err) {
            dominoInterfaz.informar("Entrada invalida");
            return;
        }

        if (opcion == 1) {
            dominoInterfaz.iniciarMensajeCompuesto();
            dominoInterfaz.compMensaje("Jugando: %s\n".formatted(getNombre()));
            dominoInterfaz.compMensaje("Fichas en mesa: %s\n".formatted(listarFichasEnMesa()));
            dominoInterfaz.compMensaje("Selecciona la pieza: \n%s".formatted(listarFichas(true)));
            dominoInterfaz.terminarMensajeCompuesto();
            respuesta = dominoInterfaz.enviarMensajeCompuestoComoPregunta();
            try {
                opcion = Integer.parseInt(respuesta);
            } catch (NumberFormatException err) {
                dominoInterfaz.informar("Entrada invalida");
                return;
            }
            Ficha aMover;
            try {
                aMover = fichas.get(opcion - 1);
            } catch (IndexOutOfBoundsException err) {
                dominoInterfaz.informar("Fuera de los rangos");
                return;
            }
            dominoInterfaz.iniciarMensajeCompuesto();
            dominoInterfaz.compMensaje("Jugando: %s\n".formatted(getNombre()));
            dominoInterfaz.compMensaje("Fichas en mesa: %s\n".formatted(listarFichasEnMesa()));
            dominoInterfaz.compMensaje("Pieza seleccionada: [%d|%d]\n".formatted(aMover.getIzq(), aMover.getDer()));
            dominoInterfaz.compMensaje("Desea agregar la pieza seleccionada al final o al inicio?");
            dominoInterfaz.terminarMensajeCompuesto();
            respuesta = dominoInterfaz.enviarMensajeCompuestoComoPreguntaMultiple("inicio", "final");
            boolean laPuso;
            if (respuesta.equals("inicio")) {
                laPuso = dominoInterfaz.domino.ponerAlInicio(aMover);
            } else {
                laPuso = dominoInterfaz.domino.ponerAlFinal(aMover);
            }
            if (!laPuso) {
                dominoInterfaz.informar("Esa pieza no se puede poner ahí");
            } else {
                dominoInterfaz.informar("Se ha puesto la pieza");
            }
        }

        else if (opcion == 2) {
            dominoInterfaz.domino.tomarDelMonton();
        }

        else if (opcion == 3) {
            dominoInterfaz.cerrar();
        } else {
            dominoInterfaz.informar("Error: no existe esa opcion");
        }
    }

    @Override
    public void inicializar(String nombre) {
        this.nombre = dominoInterfaz.preguntar("(%s) Ingresa tu nombre: ".formatted(nombre));
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}
