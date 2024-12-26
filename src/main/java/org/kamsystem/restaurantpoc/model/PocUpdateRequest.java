package org.kamsystem.restaurantpoc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class PocUpdateRequest {

    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String contact;
    @NonNull
    private PocRole role;
}
