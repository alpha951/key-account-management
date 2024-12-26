package org.kamsystem.restaurantpoc.model;

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
    private String name;
    @NonNull
    private Long restaurantId;
    @NonNull
    private PocRole pocRole;
    @NonNull
    private String contact;
    private Long createdBy;
    private Date createdAt;
    private Date updateAt;
}