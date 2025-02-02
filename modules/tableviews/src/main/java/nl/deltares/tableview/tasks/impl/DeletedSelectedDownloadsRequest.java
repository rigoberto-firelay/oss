package nl.deltares.tableview.tasks.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class DeletedSelectedDownloadsRequest extends AbstractDataRequest {


    private static final Log logger = LogFactoryUtil.getLog(DeletedSelectedDownloadsRequest.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private final List<String> selectedRecords;

    public DeletedSelectedDownloadsRequest(String id, long currentUserId, List<String> recordIds) throws IOException {
        super(id, currentUserId);
        this.selectedRecords = recordIds;
    }

    @Override
    public STATUS call() {
        if (getStatus() == available) return status;
        status = running;
        statusMessage = "start deleting...";
        init();
        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                deleteSelectedRecords(writer);
                if (status != terminated) {
                    status = available;
                }
            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn("Error serializing csv content: %s", e);
                status = terminated;
            }
            if (status == available){
                this.dataFile = new File(getExportDir(), id + ".csv");
                if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
                Files.move(tempFile.toPath(), dataFile.toPath());
            }

        } catch (Exception e) {
            errorMessage = e.getMessage();
            status = terminated;
        }
        fireStateChanged();

        return status;
    }

    private void deleteSelectedRecords(PrintWriter writer) {
        writer.println("downloadId,modifiedDate, expirationDate, shareId,filePath,directDownloadUrl,email,organization,city,country");

        totalCount = selectedRecords.size();

        selectedRecords.forEach(id -> {
            if (status == terminated) return;
            try {
                final Download download = DownloadLocalServiceUtil.deleteDownload(Long.parseLong(id));
                final User user = UserLocalServiceUtil.fetchUser(download.getUserId());
                String email = "";
                if (user != null){
                    email = user.getEmailAddress();
                }
                final Date modifiedDate = download.getModifiedDate();
                final Date expiryDate = download.getExpiryDate();
                writer.println(String.format("%d,%s,%s,%d,%s,%s,%s,%s,%s,%s",
                        download.getDownloadId(), dateFormat.format(modifiedDate == null ? new Date(): modifiedDate),
                        dateFormat.format(expiryDate == null ? new Date() : expiryDate),
                        download.getShareId(),
                        download.getFilePath(), download.getDirectDownloadUrl(), email, download.getOrganization(),
                        download.getCity(), download.getCountryCode()));
            } catch (PortalException e) {
                writer.println(String.format("Failed to delete record %s: %s", id, e.getMessage()));
            } finally {
                incrementProcessCount(1);
            }
            if (Thread.interrupted()) {
                status = terminated;
                errorMessage = String.format("Thread 'DeletedSelectedDownloadsRequest' with id %s is interrupted!", id);
            }
        });
    }

}
