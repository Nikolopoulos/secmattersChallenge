package com.secmatters.demo.challenge.gui.auth;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Base class to check access to a page. If user is not logged in, redirect to the log-in page.
 *
 */
public class AuthenticatedWebPage extends WebPage
{

    public AuthenticatedWebPage() {
    }

    public AuthenticatedWebPage(IModel<?> model) {
        super(model);
    }

    public AuthenticatedWebPage(PageParameters parameters) {
        super(parameters);
    }
}
