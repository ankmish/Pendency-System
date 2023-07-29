package com.machine.coding.phonepe.dao.impl;

import com.machine.coding.phonepe.dao.IPendencyDao;
import com.machine.coding.phonepe.enums.TrackingStatus;
import com.machine.coding.phonepe.models.Entity;
import com.machine.coding.phonepe.models.Tag;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao layer: Maintains the entities details (in-memory)
 */
@Repository
public class PendencyDao implements IPendencyDao {
    private Map<Integer, Entity> pendencyRecords;

    public PendencyDao() {
        this.pendencyRecords = new HashMap<>();
    }

    @Override
    public void insertTrackingRecord(Entity entity) {
        pendencyRecords.put(entity.getId(), entity);
    }

    @Override
    public void updateTrackingRecord(int entityId, TrackingStatus trackingStatus) throws Exception {
        if (pendencyRecords.containsKey(entityId)) {
            Entity currentEntity = pendencyRecords.get(entityId);
            currentEntity.setTrackingStatus(trackingStatus);
            pendencyRecords.put(entityId, currentEntity);
        } else {
            throw new Exception("No such entity details present with id: " + entityId);
        }
    }

    @Override
    public int fetchRecordCounts(List<Tag> tagList) {
        int count = 0;
        List<List<Tag>> activeTags = pendencyRecords.values().stream().filter(record -> record.getTrackingStatus() == TrackingStatus.ACTIVE).map(Entity::getTags).toList();

        for (List<Tag> activeTag : activeTags) {
            if (isTrackedEntity(activeTag, tagList)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Entity fetchEntityById(int id) {
        return pendencyRecords.getOrDefault(id, null);
    }

    private boolean isTrackedEntity(List<Tag> activeTags, List<Tag> tagList) {
        int count = 0;
        for (int i = 0; i < activeTags.size() && i < tagList.size(); i++) {
            if (!activeTags.get(i).equals(tagList.get(i))) {
                return false;
            }
            count++;
        }

        return (count == tagList.size());
    }
}
