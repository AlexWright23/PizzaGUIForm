
public class PizzaGUIRunner {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            PizzaGUIFrame frame = new PizzaGUIFrame();
            frame.setVisible(true);
        });
    }
}
