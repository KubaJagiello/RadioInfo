package Model;

/**
 * Functional interface that can be used to receive
 * information about parsed channels and programs.
 */
@FunctionalInterface
public interface LoadInfo {
    /**
     * @param objects parsed information.
     */
    void loadInfo(Object[] objects);
}
