package com.machine.coding.phonepe.services.impl;

import com.machine.coding.phonepe.dao.IPendencyDao;
import com.machine.coding.phonepe.enums.TrackingStatus;
import com.machine.coding.phonepe.models.Entity;
import com.machine.coding.phonepe.models.Tag;
import com.machine.coding.phonepe.services.IPendencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for maintaining & tracking the entities.
 */
@Service
public class PendencyService implements IPendencyService {
    private final static Logger LOGGER = LoggerFactory.getLogger(PendencyService.class);
    private final IPendencyDao pendencyDao;
    public PendencyService(IPendencyDao pendencyDao) {
        this.pendencyDao = pendencyDao;
    }
    @Override
    public void startTracking(int id, List<Tag> hierarchicalTags) throws Exception {
        LOGGER.info("Tracking request received for entityId: " + id);
        if (hierarchicalTags.isEmpty()) {
            throw new Exception("Invalid input records, tags are missing for entityId: " + id);
        }
        if (pendencyDao.fetchEntityById(id) == null) {
            Entity entity = Entity.builder()
                .id(id)
                .tags(hierarchicalTags)
                .trackingStatus(TrackingStatus.ACTIVE).
                build();
            pendencyDao.insertTrackingRecord(entity);
            LOGGER.info("Successfully tracking started for entity: " + id);
        } else {
            throw new Exception("Duplicate insertion request received for entityId: " + id);
        }
    }

    @Override
    public void stopTracking(int id) throws Exception {
        LOGGER.info("Request to stop tracking received for entityId: " + id);
        if (pendencyDao.fetchEntityById(id) != null) {
            pendencyDao.updateTrackingRecord(id, TrackingStatus.INACTIVE);
            LOGGER.info("Successfully stopped tracking for entityId: " + id);
        } else {
            throw new Exception("No such entityId: " + id + " exists");
        }
    }

    @Override
    public int getCounts(List<Tag> tags) {
        return pendencyDao.fetchRecordCounts(tags);
    }
}
