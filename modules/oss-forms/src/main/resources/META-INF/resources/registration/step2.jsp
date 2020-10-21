<aui:row>
    <aui:col width="50">
        <c:if test="${not empty attributes}">
            <c:set var="academicTitle" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.academicTitle.name()) %>"/>
            <c:set var="initials" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.initials.name()) %>"/>
            <c:set var="jobTitle" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.jobTitle.name()) %>"/>
            <c:set var="org_name" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_name.name()) %>"/>
            <c:set var="org_address" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_address.name()) %>"/>
            <c:set var="org_postal" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_postal.name()) %>"/>
            <c:set var="org_city" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_city.name()) %>"/>
            <c:set var="country" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_country.name()) %>"/>
        </c:if>
        <span><liferay-ui:message key="registrationform.userInfo"/></span>

<%--        <div class="d-flex justify-content-start">--%>
<%--            <div class="pr-3">--%>
<%--                <aui:input--%>
<%--                        name="salutation"--%>
<%--                        label="registrationform.salutation.mr"--%>
<%--                        type="radio"--%>
<%--                        value="" />--%>
<%--            </div>--%>
<%--            <div class="pr-3">--%>
<%--                <aui:input--%>
<%--                        name="salutation"--%>
<%--                        label="registrationform.salutation.mrs"--%>
<%--                        type="radio"--%>
<%--                        value="" />--%>
<%--            </div>--%>
<%--            <div class="p-0">--%>
<%--                <aui:input--%>
<%--                        name="salutation"--%>
<%--                        label="registrationform.salutation.neutral"--%>
<%--                        type="radio"--%>
<%--                        value="" />--%>
<%--            </div>--%>
<%--        </div>--%>

        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.academicTitle.name() %>"
                        label="registrationform.academic.titles"
                        value="${academicTitle}"
                        cssClass="update-badge"/>
            </div>
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.initials.name() %>"
                        label="registrationform.initials"
                        value="${initials}"
                        cssClass="update-badge"/>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.first_name.name() %>"
                        label="registrationform.firstname"
                        value="<%= user.getFirstName() %>"
                        disabled="true">
                    <aui:validator name="required">
                        function () {
                        return checkStep(getFormName(), 2);
                        }
                    </aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.last_name.name() %>"
                        label="registrationform.lastname"
                        value="<%= user.getLastName() %>"
                        disabled="true">
                    <aui:validator name="required">
                        function () {
                        return checkStep(getFormName(), 2);
                        }
                    </aui:validator>
                </aui:input>
            </div>
        </div>

        <aui:input
                name="<%= KeycloakUtils.ATTRIBUTES.email.name() %>"
                label="registrationform.email"
                value="<%= user.getEmailAddress() %>"
                disabled="true">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 2);
                }
            </aui:validator>
        </aui:input>

        <div class="row">
            <div class="col">
                <aui:input
                        name="username"
                        label="registrationform.username"
                        value="<%= user.getScreenName() %>"
                        disabled="true">
                    <aui:validator name="required">
                        function () {
                        return checkStep(getFormName(), 2);
                        }
                    </aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.jobTitle.name() %>"
                        label="registrationform.job.titles"
                        value="${jobTitle}" />
            </div>
        </div   >

        <span><liferay-ui:message key="registrationform.organizationInfo"/></span>

        <aui:input
                name="<%= KeycloakUtils.ATTRIBUTES.org_name.name() %>"
                label="registrationform.orgname"
                value="${org_name}">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 2);
                }
            </aui:validator>
        </aui:input>
        <aui:input
                name="<%= KeycloakUtils.ATTRIBUTES.org_address.name() %>"
                label="registrationform.orgaddress"
                value="${org_address}">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 2);
                }
            </aui:validator>
        </aui:input>

        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.org_postal.name() %>"
                        label="registrationform.orgpostcode"
                        value="${org_postal}">
                    <aui:validator name="required">
                        function () {
                        return checkStep(getFormName(), 2);
                        }
                    </aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.org_city.name() %>"
                        label="registrationform.orgcity"
                        value="${org_city}">
                    <aui:validator name="required">
                        function () {
                        return checkStep(getFormName(), 2);
                        }
                    </aui:validator>
                </aui:input>
            </div>
        </div>
        <aui:select
                name="<%=KeycloakUtils.ATTRIBUTES.org_country.name()%>"
                type="select"
                label="registrationform.orgcountry"
                value="${country}" >
            <aui:validator name="required">
                        function () {
                            return checkStep(getFormName(), 2);
                        }
            </aui:validator>
            <% List<Country> countries = CountryServiceUtil.getCountries(true); %>
            <%    for (Country country : countries) { %>
            <aui:option value="<%=country.getName()%>" label ="<%= country.getName(locale) %>" />
            <% } %>
        </aui:select>

    </aui:col>
    <aui:col width="50">

        <p><liferay-ui:message key="dsd.registration.step2.badge.title"/></p>

        <div class="card mb-3">
            <div class="card-header">
                <%= event.getTitle() %> <span class="d-block event-edition"><%= DateUtil.getDate(event.getEndTime(), "yyyy", locale)%></span>
            </div>
            <div class="card-body px-5 py-6">
                <h1 class="card-title" id="badge-title"></h1>
                <span class="card-text" id="job-title"></span>
            </div>
        </div>


        <span><liferay-ui:message key="dsd.registration.step2.show.title"/></span>
        <%
            String title_setting = (String) attributes.get(KeycloakUtils.ATTRIBUTES.badge_title_setting.name());
            boolean yes_checked = "yes".equals(title_setting);
            boolean no_checked = "no".equals(title_setting);

        %>
        <div class="d-flex justify-content-start">
            <div class="pr-3">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.badge_title_setting.name() %>"
                        label="yes"
                        cssClass="update-badge"
                        type="radio"
                        value="yes"
                        checked="<%=yes_checked%>"/>
            </div>
            <div class="pr-3">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.badge_title_setting.name() %>"
                        label="no"
                        cssClass="update-badge"
                        type="radio"
                        value="no"
                        checked="<%=no_checked%>" />
            </div>
        </div>

        <span><liferay-ui:message key="dsd.registration.step2.badge.name"/></span>
        <%
            String name_setting = (String) attributes.get(KeycloakUtils.ATTRIBUTES.badge_name_setting.name());
            boolean name_checked = "name".equals(name_setting);
            boolean initials_checked = "initials".equals(name_setting);
            boolean both_checked = "both".equals(name_setting);
        %>
        <div class="d-flex justify-content-start">
            <div class="pr-3">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.badge_name_setting.name() %>"
                        label="dsd.registration.step2.badge.name.1"
                        cssClass="update-badge"
                        type="radio"
                        value="name"
                        checked="<%=name_checked%>" />
            </div>
            <div class="pr-3">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.badge_name_setting.name() %>"
                        label="dsd.registration.step2.badge.name.2"
                        cssClass="update-badge"
                        type="radio"
                        value="initials"
                        checked="<%=initials_checked%>"/>
            </div>
            <div class="pr-3">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.badge_name_setting.name() %>"
                        label="dsd.registration.step2.badge.name.3"
                        cssClass="update-badge"
                        type="radio"
                        value="both"
                        checked="<%=both_checked%>"/>
            </div>
        </div>

    </aui:col>
</aui:row>