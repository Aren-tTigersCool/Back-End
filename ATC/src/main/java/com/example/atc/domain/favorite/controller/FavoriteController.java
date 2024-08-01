package com.example.atc.domain.favorite.controller;
import com.example.atc.domain.favorite.dto.FavoriteDTO;
import com.example.atc.domain.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;
    @PostMapping
    public ResponseEntity<?> createFavorite(@RequestBody FavoriteDTO favoriteDTO) {
        return favoriteService.createFavorite(favoriteDTO);
    }
    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getAllFavorite() {
        return favoriteService.getAllFavorite();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getFavoriteById(@PathVariable Long id) {
        return favoriteService.getFavoriteById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFavorite(@PathVariable Long id, @RequestBody FavoriteDTO favoriteDTO) {
        return favoriteService.updateFavorite(id, favoriteDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFavorite(@PathVariable Long id) {
        return favoriteService.deleteFavorite(id);
    }
}