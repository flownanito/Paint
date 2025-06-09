package mvc;

import javax.swing.SwingUtilities;
import mvc.controlador.PaintControlador;

public class MVC {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PaintControlador();
        });
    }
}