package nl.deltares.search.facet.event;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import nl.deltares.search.constans.FacetPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

/**
 * @author allan
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.css-class-wrapper=portlet-event-facet",
                "com.liferay.portlet.display-category=OSS-search",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=EventFacet",
                "javax.portlet.expiration-cache=0",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/facet/event/view.jsp",
                "javax.portlet.name=" + FacetPortletKeys.EVENT_FACET_PORTLET,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class EventFacetPortlet extends MVCPortlet {

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        portletSharedSearchRequest.search(renderRequest);
        renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, false);
        super.render(renderRequest, renderResponse);
    }

    @Reference
    protected PortletSharedSearchRequest portletSharedSearchRequest;
}