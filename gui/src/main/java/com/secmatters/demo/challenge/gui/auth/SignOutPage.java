package com.secmatters.demo.challenge.gui.auth;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Simple logout page.
 */
public class SignOutPage extends WebPage
{
	/**
	 * Constructor
	 *
	 * @param parameters
	 *            Page parameters (ignored since this is the home page)
	 */
	public SignOutPage(final PageParameters parameters)
	{
		getSession().invalidate();
	}
}
