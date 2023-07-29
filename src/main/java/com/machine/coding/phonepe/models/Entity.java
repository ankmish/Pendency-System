package com.machine.coding.phonepe.models;

import com.machine.coding.phonepe.enums.TrackingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Entity {
    private int id;
    private TrackingStatus trackingStatus;
    private List<Tag> tags;
}
