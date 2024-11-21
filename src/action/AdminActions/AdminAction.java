package action.AdminActions;

/**
 * Represents an administrative action that can be executed.
 * <p>
 * Classes implementing this interface should define specific
 * behavior for the {@code execute} method to perform
 * administrative tasks.
 * </p>
 */
public interface AdminAction {

    /**
     * Executes the administrative action.
     * <p>
     * This method should contain the logic for performing
     * a specific administrative task. The implementation
     * should ensure that all necessary operations are
     * carried out when this method is called.
     * </p>
     */
    void execute();
}
