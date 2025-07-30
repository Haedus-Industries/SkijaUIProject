import com.haedus.ui.MainWindow;

/**
 * Application entry point.  Instantiates the {@link MainWindow} and
 * starts the rendering loop.  Separating the entry point into its own
 * class keeps the {@link MainWindow} focused on window and UI
 * management while allowing the JVM to locate the `main` method
 * without scanning nested types.
 */
public class Main {
    /**
     * Launches the desktop application.  Creating a new instance of
     * {@link MainWindow} triggers window creation, Skija initialisation and
     * the main render loop.
     *
     * @param args command line arguments (ignored)
     */
    public static void main(String[] args) {
        new MainWindow().start();
    }
}