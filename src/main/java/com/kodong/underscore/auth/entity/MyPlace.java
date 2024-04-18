package com.kodong.underscore.auth.entity;

import com.kodong.underscore.map.entity.AdministrativeDistrict;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MyPlace {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "administrativedistrict_id")
    private AdministrativeDistrict administrativeDistrict;

    private String memo;

    public void setUser(User user) {
        this.user = user;
    }

    public MyPlace(User user, AdministrativeDistrict administrativeDistrict, String memo) {
        this.user = user;
        this.administrativeDistrict = administrativeDistrict;
        this.memo = memo;
    }

}
