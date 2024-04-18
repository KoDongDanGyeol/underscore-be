package com.kodong.underscore.auth.entity;

import com.kodong.underscore.map.entity.AdministrativeDistrict;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String email;
    private String role;

    @OneToMany(mappedBy = "user")
    private List<MyPlace> myPlaces = new ArrayList<>();

    public User(String username, String name, String email, String role) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public void updateNameAndEmail(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void addMyPlace(MyPlace myPlace) {
        myPlaces.add(myPlace);
        myPlace.setUser(this);
    }
}
