package View;

/**
 * Functional interface that is used for setting actionListeners
 * for both JTables and JMenu.
 */
@FunctionalInterface
public interface ListenerForAction {
    /**
     * @param info value of cell that was pressed as String.
     */
    void action(String info);
}
