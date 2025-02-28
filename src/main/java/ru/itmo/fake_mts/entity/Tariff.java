package ru.itmo.fake_mts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tariffs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Integer gigabyteCount;

    @NotNull
    private Integer minutesCount;

    @NotNull
    private Integer smsCount;

    @NotNull
    private Double price;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
