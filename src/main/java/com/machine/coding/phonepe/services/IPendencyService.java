package com.machine.coding.phonepe.services;

import com.machine.coding.phonepe.models.Tag;

import java.util.List;

public interface IPendencyService {
    void startTracking(int id, List<Tag> hierarchicalTags) throws Exception;
    void stopTracking(int id) throws Exception;
    int getCounts(List<Tag> tags);

}
