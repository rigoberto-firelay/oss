<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>${the_title} - ${company_name}</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	<@liferay_util["include"] page=top_head_include />

	 <#if google_tag_id?? && google_tag_id?has_content >
        <!-- Google Tag Manager -->
        <script>
            (function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
            new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
            j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
            'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
            })(window,document,'script','dataLayer','${google_tag_id}');
        </script>
        <!-- End Google Tag Manager -->
    </#if>
	<link rel="stylesheet" type="text/css" href="${css_folder}/lib/cookies/cookieconsent.min.css" />
</head>

<script src="${javascript_folder}/cookies/cookieconsent.min.js" data-cfasync="false"></script>
<script src="${javascript_folder}/calendar/calendar.js" ></script>
<body class="${css_class}">

<#if google_tag_id?? && google_tag_id?has_content >
        <!-- Google Tag Manager (noscript) --><noscript><iframe src="https://www.googletagmanager.com/ns.html?id=${google_tag_id}" height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript><!-- End Google Tag Manager (noscript) -->
</#if>

<@liferay_ui["quick-access"] contentId="#main-content" />

<@liferay_util["include"] page=body_top_include />

<#if is_site_admin>
	<@liferay.control_menu />
</#if>

<div id="wrapper" class="antialiased bg-gradient bg-theme-tertiary">
	<#include "${full_templates_path}/header.ftl" />

	<section id="content">
		<div class="inner">
			<h1 class="hide-accessible">${the_title}</h1>

			<#if selectable>
				<@liferay_util["include"] page=content_include />
			<#else>
				${portletDisplay.recycle()}

				${portletDisplay.setTitle(the_title)}

				<@liferay_theme["wrap-portlet"] page="portlet.ftl">
					<@liferay_util["include"] page=content_include />
				</@>
			</#if>
		</div>
	</section>

	<#include "${full_templates_path}/footer.ftl" />
</div>

<@liferay_util["include"] page=body_bottom_include />

<@liferay_util["include"] page=bottom_include />

<!-- inject:js -->
<!-- endinject -->

</body>
<script>
	var checkoutCartURL = '${checkout_cart_url}';
	var downloadCartURL = '${download_cart_url}';
	var shoppingCart = new ShoppingCart({'languageKeys': {
			'add-to-cart': '${languageUtil.get(locale, "shopping.cart.add")}',
			'remove-from-cart': '${languageUtil.get(locale, "shopping.cart.remove")}'
		}});
	shoppingCart.refreshCart();

	<#if is_show_cookies >
	window.cookieconsent.initialise({
		palette: {
			popup: {
				background: "#252e39"
			},
			button: {
				background: "#0d38e0"
			}
		},
		content : {
			message: "${languageUtil.get(locale, "oss.cookies.message")}",
			dismiss: "${languageUtil.get(locale, "oss.cookies.accept")}",
			link: "${languageUtil.get(locale, "oss.cookies.moreinfo")}",
			href: "${siteUrl}/cookie-policy"
		},
		cookie : {
			secure : true
		}
	});
	</#if>
</script>

</html>