package nl.deltares.oss.download.upgrade;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import nl.deltares.oss.download.upgrade.v1_1_0.DownloadTableAddColumnLicenseDownloadUrl;
import org.osgi.service.component.annotations.Component;

@Component(service = UpgradeStepRegistrator.class)
public class DownloadServiceUpgrade implements UpgradeStepRegistrator {
    @Override
    public void register(Registry registry) {
        registry.register(
                "1.0.0", "1.1.0",
                new DownloadTableAddColumnLicenseDownloadUrl());
    }
}
