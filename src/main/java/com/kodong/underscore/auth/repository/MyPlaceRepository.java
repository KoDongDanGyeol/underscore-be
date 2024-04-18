package com.kodong.underscore.auth.repository;

import com.kodong.underscore.auth.entity.MyPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyPlaceRepository extends JpaRepository<MyPlace, Long> {

    // 중복 체크
    @Query("SELECT mp FROM MyPlace mp " +
            "WHERE mp.administrativeDistrict.id = :placeId " +
            "AND mp.user.id = :userId")
    Optional<MyPlace> findByPlaceIdAndUserId(Long placeId, Long userId);

    // 내 장소들 조회
    @Query("SELECT mp FROM MyPlace mp " +
            "WHERE mp.user.id = :userId")
    List<MyPlace> findMyPlaces(Long userId);
}
