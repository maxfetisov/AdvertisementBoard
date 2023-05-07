package com.advertisementboard.data.entity;

import com.advertisementboard.data.enumeration.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "role", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private UserRole name;

    @ManyToMany
    private List<User> users;
}
