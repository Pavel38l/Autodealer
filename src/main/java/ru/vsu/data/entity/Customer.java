package ru.vsu.data.entity;


import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@EqualsAndHashCode @ToString
public class Customer {
    private int id;
    private String fullName;
    private Date dateOfBirth;
    private String sex;
}
