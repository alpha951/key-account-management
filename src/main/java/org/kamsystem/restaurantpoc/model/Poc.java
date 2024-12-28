package org.kamsystem.restaurantpoc.model;

import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Poc {

    private Long id;
    @NonNull
    @Size(min = 2, max = 30)
    private String name;
    @NonNull
    private Long restaurantId;
    @NonNull
    private PocRole pocRole;
    @NonNull
    @Size(min = 3, max = 320) // email can be 320 characters long
    private String contact;  // can be mobile number or email
    private Long createdBy;
    private Date createdAt;
    private Date updateAt;
}