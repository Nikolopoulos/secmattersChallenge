package com.secmatters.demo.challenge.gui;

import com.secmatters.demo.challenge.backend.service.ChallengeDAO;
import com.secmatters.demo.challenge.backend.service.IChallengeDAO;
import com.secmatters.demo.challenge.backend.service.Utility;
import com.secmatters.demo.challenge.gui.auth.AuthenticatedWebPage;
import com.secmatters.demo.challenge.gui.auth.SignInPage;
import com.secmatters.demo.challenge.gui.auth.SignInSession;
import de.agilecoders.wicket.core.Bootstrap;
import java.io.IOException;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.component.IRequestableComponent;

public final class ChallengeApplication extends WebApplication {

    private final static String PERSISTENCE_UNIT_EMBEDDED = "com.secmatters.demo.challenge.persistence_unit_embedded";
    private final static String PERSISTENCE_UNIT_LOCAL = "com.secmatters.demo.challenge.persistence_unit_local";
    private final static boolean useEmbedded = true;
    private IChallengeDAO dao;

    public static ChallengeApplication get() {
        return (ChallengeApplication) WebApplication.get();
    }

    /**
     * Constructor.
     */
    public ChallengeApplication() {
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends Page> getHomePage() {
        return Home.class;
    }

    /**
     * @see
     * org.apache.wicket.protocol.http.WebApplication#newSession(org.apache.wicket.request.Request,
     * Response)
     */
    @Override
    public Session newSession(Request request, Response response) {
        return new SignInSession(request);
    }

    public IChallengeDAO getDao() {
        return dao;
    }

    /**
     * @see org.apache.wicket.examples.WicketExampleApplication#init()
     */
    @Override
    protected void init() {
        super.init();
        Bootstrap.install(this);
        //Enable wicketpath attribute in tags for Selnium
        getDebugSettings().setOutputComponentPath(true);
        dao = new ChallengeDAO(useEmbedded ? PERSISTENCE_UNIT_EMBEDDED : PERSISTENCE_UNIT_LOCAL);
        if (useEmbedded) {
            try {
                Utility.importDatabase(dao);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        // Register the authorization strategy
        getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy() {
            @Override
            public boolean isActionAuthorized(Component component, Action action) {
                // authorize everything
                return true;
            }

            @Override
            public <T extends IRequestableComponent> boolean isInstantiationAuthorized(
                    Class<T> componentClass) {
                // Check if the new Page requires authentication (implements the marker interface)
                if (AuthenticatedWebPage.class.isAssignableFrom(componentClass)) {
                    // Is user signed in?
                    if (((SignInSession) Session.get()).isSignedIn()) {
                        // okay to proceed
                        return true;
                    }

                    // Intercept the request, but remember the target for later.
                    // Invoke Component.continueToOriginalDestination() after successful logon to
                    // continue with the target remembered.
                    throw new RestartResponseAtInterceptPageException(SignInPage.class);
                }

                // okay to proceed
                return true;
            }
        });
    }
}
