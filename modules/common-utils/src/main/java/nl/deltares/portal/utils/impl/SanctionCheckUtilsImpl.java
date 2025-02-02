package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import nl.deltares.portal.configuration.SanctionSystemConfiguration;
import nl.deltares.portal.utils.GeoIpUtils;
import nl.deltares.portal.utils.SanctionCheckUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Arrays;
import java.util.Map;

@Component(
        immediate = true,
        service = SanctionCheckUtils.class
)
public class SanctionCheckUtilsImpl implements SanctionCheckUtils {

    private static final Log LOG = LogFactoryUtil.getLog(SanctionCheckUtilsImpl.class);

    @Reference
    protected GeoIpUtils geoIpUtils;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private String[] getSanctionedCountryCodes(ConfigurationProvider configurationProvider) {
        if (configurationProvider == null) return new String[0];

        try {
            SanctionSystemConfiguration configuration = configurationProvider.getSystemConfiguration(
                    SanctionSystemConfiguration.class);

            final String codes = configuration.sanctionCountryIsoCodes();
            return codes.split(";");

        } catch (ConfigurationException e) {
            LOG.error("Error getting SanctionSystemConfiguration: " + e.getMessage());
        }
        return new String[0];
    }

    @Override
    public boolean isSanctionedByCountyCode(String isoCountryCode) {
        final String[] sanctionList = getSanctionedCountryCodes(_configurationProvider);
        return Arrays.stream(sanctionList).anyMatch(sanctionCode -> sanctionCode.toUpperCase().equals(isoCountryCode));
    }

    @Override
    public boolean isSanctionedByIp(String remoteAddress) {
        if (!geoIpUtils.isActive()) return false;
        final Map<String, String> clientIpInfo = geoIpUtils.getClientIpInfo(remoteAddress);
        return isSanctionedByCountyCode(geoIpUtils.getCountryIso2Code(clientIpInfo));
    }
}
