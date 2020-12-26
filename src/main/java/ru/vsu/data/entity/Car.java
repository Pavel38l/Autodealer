package ru.vsu.data.entity;


import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@EqualsAndHashCode @ToString
public class Car {
    private int id;
    private CarType carType;
    private String brand;
    private String model;
    private Customer customer;
}
