import view.FormularioFuncionario;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Ejecutar Swing de manera segura
        SwingUtilities.invokeLater(() -> {
            new FormularioFuncionario();
        });
    }
}
