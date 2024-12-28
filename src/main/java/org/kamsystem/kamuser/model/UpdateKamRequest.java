package org.kamsystem.kamuser.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class UpdateKamRequest {
    @NonNull
    private Long oldKamId;
    @NonNull
    private Long newKamId;
    private String kamChangeReason;
}
