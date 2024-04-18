package com.kodong.underscore.auth.controller;

import com.kodong.underscore.auth.dto.AddPlaceDto;
import com.kodong.underscore.auth.dto.MyPlaceDto;
import com.kodong.underscore.auth.service.MyPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myplace")
public class MyPlaceController {

    private final MyPlaceService myPlaceService;

    @PostMapping("/{placeId}")
    public ResponseEntity<String> addMyPlace(@PathVariable(name = "placeId") Long placeId, @RequestBody AddPlaceDto memoDto) {
        myPlaceService.save(placeId, memoDto.getMemo());
        return ResponseEntity.ok("내 장소에 장소를 추가했습니다.");
    }

    @DeleteMapping("/{placeId}")
    public ResponseEntity<String> deleteMyPlace(@PathVariable(name = "placeId") Long placeId) {
        myPlaceService.delete(placeId);
        return ResponseEntity.ok("내 장소를 제거했습니다.");
    }

    @GetMapping
    public List<MyPlaceDto> findMyPlaces() {
        return myPlaceService.findMyPlaces();
    }

}
