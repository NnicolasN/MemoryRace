package memoryrace.interfaceswing;

import memoryrace.connectionlocal.LocalConnectionHandler;
import memoryrace.connectionmodel.*;

/**
 * Entry point of the client application.
 *
 * The photo folder is given as the first argument, and the database settings
 * are read from the environment so that credentials never have to be edited
 * in the source code:
 *
 * DB_HOST, DB_PORT, DB_USER, DB_PASSWORD
 */
public class Main {

    private static final String DEFAULT_ROOT_PATH = "/tmp/photos/";

    public static void main(String[] args) throws ConnectionErrorException {
        String rootPath = args.length > 0 ? args[0] : DEFAULT_ROOT_PATH;

        ConnectionHandler conn = new LocalConnectionHandler(
                env("DB_HOST", "127.0.0.1"),
                Integer.parseInt(env("DB_PORT", "3306")),
                env("DB_USER", "root"),
                env("DB_PASSWORD", "password"),
                rootPath);
        conn.connect();

        FenetreAcceuil fenetreAcceuil = new FenetreAcceuil(conn);
        fenetreAcceuil.setVisible(true);
    }

    /**
     * Reads an environment variable, or returns a fallback value when it is
     * not set.
     *
     * @param name Name of the environment variable.
     * @param fallback Value used when the variable is missing or empty.
     * @return The value to use.
     */
    private static String env(String name, String fallback) {
        String value = System.getenv(name);
        return (value == null || value.isEmpty()) ? fallback : value;
    }
}
