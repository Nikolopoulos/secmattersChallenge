package com.secmatters.demo.challenge.gui.auth;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

/**
 * Session class for signin. Holds and authenticates users.
 */
public final class SignInSession extends AuthenticatedWebSession {

    private String user;

    public static SignInSession get() {
        return (SignInSession)AuthenticatedWebSession.get();
    }

    /**
     * Constructor
     *
     * @param request The current request object
     */
    public SignInSession(Request request) {
        super(request);

    }

    /**
     * Checks the given username and password, returning a User object if if the
     * username and password identify a valid user.
     *
     * @param username The username
     * @param password The password
     * @return True if the user was authenticated
     */
    @Override
    public final boolean authenticate(final String username, final String password) {
        final String THE_ONLY_ALLOWED_NAME = "challenge";

        if (user == null) {
            // Trivial password "db"
            if (THE_ONLY_ALLOWED_NAME.equalsIgnoreCase(username) && THE_ONLY_ALLOWED_NAME.equalsIgnoreCase(password)) {
                user = username;
            }
        }

        return user != null;
    }

    /**
     * @see org.apache.wicket.authentication.AuthenticatedWebSession#getRoles()
     */
    @Override
    public Roles getRoles() {
        if (isSignedIn()) {
            // If the user is signed in, they have these roles
            return new Roles(Roles.ADMIN);
        }
        return null;
    }

    /**
     * @return User
     */
    public String getUser() {
        return user;
    }
}
