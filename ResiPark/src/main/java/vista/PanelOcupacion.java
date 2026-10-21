package vista;

//------------
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Component;
import java.awt.Font;

/**
 * Panel de "Ocupación en vivo": una barra por área y una por cada fila.
 * Se actualiza cada 500 ms con un javax.swing.Timer, que ejecuta su código
 * en el hilo de Swing (EDT), así que no hace falta invokeLater aquí.
 */
public class PanelOcupacion extends JPanel {

    private static final String FILAS_SOCIOS = "ABC";
    private static final String FILAS_GENERAL = "EFGHI";
    private static final int PERIODO_MS = 500;

    private final ConsultaOcupacion fuente;
    private final Timer temporizador;

    public PanelOcupacion(ConsultaOcupacion fuente) {
        this.fuente = fuente;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        agregarTitulo("Resumen por área");
        add(new BarraOcupacion("Socios", FILAS_SOCIOS));
        add(new BarraOcupacion("General", FILAS_GENERAL));

        agregarTitulo("Área de socios");
        agregarBarrasDeFilas(FILAS_SOCIOS);

        agregarTitulo("Área general / visitantes");
        agregarBarrasDeFilas(FILAS_GENERAL);

        actualizar(); // primer pintado inmediato
        temporizador = new Timer(PERIODO_MS, e -> actualizar());
        temporizador.start();
    }

    private void agregarTitulo(String texto) {
        JLabel titulo = new JLabel(texto);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 14f));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 0, 4, 0));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(titulo);
    }

    private void agregarBarrasDeFilas(String filas) {
        for (int i = 0; i < filas.length(); i++) {
            char fila = filas.charAt(i);
            add(new BarraOcupacion("Fila " + fila, String.valueOf(fila)));
        }
    }

    /** Consulta la fuente y repinta cada barra. Suma las filas que agrupa cada una. */
    private void actualizar() {
        for (Component c : getComponents()) {
            if (c instanceof BarraOcupacion) {
                BarraOcupacion barra = (BarraOcupacion) c;
                String filas = barra.getFilas();
                int ocupados = 0;
                int total = 0;
                for (int i = 0; i < filas.length(); i++) {
                    char fila = filas.charAt(i);
                    ocupados += fuente.ocupadosEnFila(fila);
                    total += fuente.totalEnFila(fila);
                }
                barra.setValores(ocupados, total);
            }
        }
    }

    /** Llamar al cerrar la ventana para detener el temporizador. */
    public void detener() {
        temporizador.stop();
    }
}
//------------