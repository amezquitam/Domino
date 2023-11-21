import javax.swing.*;
import java.util.Arrays;

public class DominoJOptionPane extends DominoInterfaz {
    private boolean debeCerrarse = false;
    private String mensajeCompuesto;

    public DominoJOptionPane(Domino domino) {
        super(domino);
    }

    @Override
    public String preguntar(String pregunta) {
        return JOptionPane.showInputDialog(pregunta);
    }

    @Override
    public void informar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    @Override
    public boolean debeCerrarse() {
        return debeCerrarse || domino.alguienGano() || domino.hayEmpate();
    }

    @Override
    public void iniciarMensajeCompuesto() {
        mensajeCompuesto = "";
    }

    @Override
    public void terminarMensajeCompuesto() {
    }

    @Override
    public void compMensaje(String mensaje) {
        mensajeCompuesto += mensaje;
    }

    @Override
    public String enviarMensajeCompuestoComoPregunta() {
        return JOptionPane.showInputDialog(mensajeCompuesto);
    }

    @Override
    public void enviarMensajeCompuestoComoMensaje() {
        JOptionPane.showMessageDialog(null, mensajeCompuesto);
    }

    @Override
    public void cerrar() {
        debeCerrarse = true;
    }

    @Override
    public String enviarMensajeCompuestoComoPreguntaMultiple(String... opciones) {
        int opcion = JOptionPane.showOptionDialog(
                null,
                mensajeCompuesto,
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
        return opciones[opcion];
    }
}
