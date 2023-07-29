package com.machine.coding.phonepe.dao;

import com.machine.coding.phonepe.enums.TrackingStatus;
import com.machine.coding.phonepe.models.Entity;
import com.machine.coding.phonepe.models.Tag;

import java.util.List;

public interface IPendencyDao {
    public void insertTrackingRecord(Entity entity);
    public void updateTrackingRecord(int entityId, TrackingStatus trackingStatus) throws Exception;
    public int fetchRecordCounts(List<Tag> tagList);
    public Entity fetchEntityById(int id);
}
