package com.secmatters.demo.challenge.gui.auth;

import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;


/**
 * Simple example of a sign in page. It is based on auth-role's SignInPanel which already provides
 * all what is necessary.
 */
public final class SignInPage extends WebPage
{
	/**
	 * Construct
	 */
	public SignInPage()
	{
            this(null);
	}

	/**
	 * Constructor
	 *
	 * @param parameters
	 *            The page parameters
	 */
	public SignInPage(final PageParameters parameters)
	{
            super(parameters);
            // Take our standard Logon Panel from the auth-role module and add it to the Page. That is
            // all what is necessary.
            add(new SignInPanel("signInPanel", false));
	}
}
