package org.kamsystem.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String pincode;
    @NonNull
    private String city;
    @NonNull
    private String state;
    @NonNull
    private String address;
    private Long createdBy;
    private String createdAt;
    private String updatedAt;
}
