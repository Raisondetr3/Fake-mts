package ru.itmo.fake_mts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "operations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition="TEXT")
    private String name;

    @Column(columnDefinition="TEXT")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @NotNull
    private LocalDateTime time;

    @NotNull
    private Double price;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
