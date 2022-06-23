<%@ page import="nl.deltares.search.facet.checkbox.CheckboxFacetConfiguration" %>
<%@ page import="nl.deltares.search.util.FacetUtils" %>
<%@ page import="java.util.Map" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%
	String name = (String) renderRequest.getAttribute("name");
	String title = (String) renderRequest.getAttribute("title");
	CheckboxFacetConfiguration configuration =
			(CheckboxFacetConfiguration)
					renderRequest.getAttribute(CheckboxFacetConfiguration.class.getName());
	String visibleConfig = null;
	if (Validator.isNotNull(configuration)){
		visibleConfig = portletPreferences.getValue("visible", configuration.visible());
	}

	String selection = (String)renderRequest.getAttribute("selection");
	if (selection == null){
		selection = "false";
	}
	String type = "checkbox";
	if (visibleConfig != null && !visibleConfig.isEmpty()){
		if (!Boolean.parseBoolean(visibleConfig)) type = "hidden";
	}
	final Map<String, String> yesNoFieldOptions = FacetUtils.getYesNoFieldOptions();

%>

<aui:form method="post" name="presentationFacetForm">

	<aui:select
			name='<%="checkbox-facet-" + (name)%>'
			type="select"
			cssClass='select'
			label="<%=title%>"  value="<%= selection  %>">
		<aui:option value="undefined" label ="facet.selection-facet.label.select" />
		<% for (String selectionValue : yesNoFieldOptions.keySet()) { %>
		<aui:option value="<%=selectionValue%>" label ="<%=yesNoFieldOptions.get(selectionValue)%>" />
		<%}%>
	</aui:select>
</aui:form>


<aui:script use="deltares-search-facet-util">

	$("#<portlet:namespace />checkbox-facet-<%=name%>").change(function () {
	Liferay.Deltares.FacetUtil.updateQueryString("<portlet:namespace />", "<%=name%>");
	});
</aui:script>