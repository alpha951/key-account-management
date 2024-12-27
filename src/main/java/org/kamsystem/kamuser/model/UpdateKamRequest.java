package org.kamsystem.kamuser.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateKamRequest {
    private Long oldKamId;
    private Long newKamId;
    private String kamChangeReason;
}
