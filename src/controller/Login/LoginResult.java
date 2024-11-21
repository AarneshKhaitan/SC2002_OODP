/**
 * Represents the possible outcomes of a login attempt.
 * This enumeration is used by the {@link LoginController} to indicate the result
 * of a user's login process.
 */
package controller.Login;

/**
 * The {@code LoginResult} enum defines the potential results of a login attempt:
 * <ul>
 *     <li>{@link #SUCCESS}: The login was successful.</li>
 *     <li>{@link #INVALID_CREDENTIALS}: The provided credentials are invalid.</li>
 *     <li>{@link #REQUIRE_PASSWORD_CHANGE}: The user must change their password as it is their first login.</li>
 * </ul>
 */
public enum LoginResult {

    /**
     * Indicates that the login was successful.
     */
    SUCCESS,

    /**
     * Indicates that the provided credentials are invalid.
     */
    INVALID_CREDENTIALS,

    /**
     * Indicates that the user must change their password because it is their first login.
     */
    REQUIRE_PASSWORD_CHANGE
}
