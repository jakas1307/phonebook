package com.pb.phonebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    
    private Long id;
    private String name;
    private Integer extension;
    private String email;

    private Long companyId;
    private String companyName;

    private Long departmentId;
    private String departmentName;

    private Long locationId;
    private String locationName;
    private String locationSubRegion;
}
