package com.advertisementboard.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "advertisement", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Advertisement {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String heading;

    @Column
    private String text;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

}
