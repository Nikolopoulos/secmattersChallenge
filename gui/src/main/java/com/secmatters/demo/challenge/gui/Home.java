package com.secmatters.demo.challenge.gui;

import com.secmatters.demo.challenge.gui.auth.AuthenticatedWebPage;
import com.secmatters.demo.challenge.gui.service.CustomerViewPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Simple home page which requires authentication

 */
public class Home extends AuthenticatedWebPage
{
	/**
	 * Constructor
	 *
	 * @param parameters
	 *            Page parameters (ignored since this is the home page)
	 */
	public Home(final PageParameters parameters)
	{
            add(new AjaxLink("customerViewLink") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    setResponsePage(CustomerViewPage.class);
                }
            });
	}
}
