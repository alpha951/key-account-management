package org.kamsystem.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    private Long id;
    private String name;
    private String pincode;
    private String city;
    private String state;
    private String address;
    private Long createdBy;
}
