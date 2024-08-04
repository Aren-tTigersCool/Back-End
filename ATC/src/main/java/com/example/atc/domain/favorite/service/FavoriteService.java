package com.example.atc.domain.favorite.service;
import com.example.atc.domain.favorite.dto.FavoriteDTO;
import com.example.atc.domain.favorite.entity.Favorite;
import com.example.atc.domain.favorite.repository.FavoriteRepository;
import com.example.atc.domain.category.repository.CategoryRepository;
import com.example.atc.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    public ResponseEntity<?> createFavorite(FavoriteDTO favoriteDTO) {
        Favorite favorite = new Favorite();
        favorite.setProfilePicId(favoriteDTO.getProfilePicId());
        favorite.setPlaceName(favoriteDTO.getPlaceName());
        favorite.setCategory(categoryRepository.findById(favoriteDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("카테고리 정보 없음.")));
        favorite.setUser(userRepository.findById(favoriteDTO.getUserId()).orElseThrow(() -> new RuntimeException("유저 정보 없음.")));
        favoriteRepository.save(favorite);
        return ResponseEntity.ok(new FavoriteDTO(favorite.getFavoriteId(), favorite.getProfilePicId(), favorite.getPlaceName(), favorite.getCategory().getCategoryId(), favorite.getUser().getUserId()));
    }
    public ResponseEntity<List<FavoriteDTO>> getAllFavorite() {
        List<Favorite> favorites = favoriteRepository.findAll();
        List<FavoriteDTO> favoriteDTOs = favorites.stream()
                .map(fav -> new FavoriteDTO(fav.getFavoriteId(), fav.getProfilePicId(), fav.getPlaceName(), fav.getCategory().getCategoryId(), fav.getUser().getUserId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(favoriteDTOs);
    }
    public ResponseEntity<?> getFavoriteById(Long id) {
        Optional<Favorite> favoriteOpt = favoriteRepository.findById(id);
        if (favoriteOpt.isPresent()) {
            Favorite favorite = favoriteOpt.get();
            return ResponseEntity.ok(new FavoriteDTO(favorite.getFavoriteId(), favorite.getProfilePicId(), favorite.getPlaceName(), favorite.getCategory().getCategoryId(), favorite.getUser().getUserId()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<?> updateFavorite(Long id, FavoriteDTO favoriteDTO) {
        Optional<Favorite> favoriteOpt = favoriteRepository.findById(id);
        if (favoriteOpt.isPresent()) {
            Favorite favorite = favoriteOpt.get();
            favorite.setProfilePicId(favoriteDTO.getProfilePicId());
            favorite.setPlaceName(favoriteDTO.getPlaceName());
            favorite.setCategory(categoryRepository.findById(favoriteDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("카테고리 정보 없음.")));
            favorite.setUser(userRepository.findById(favoriteDTO.getUserId()).orElseThrow(() -> new RuntimeException("유저정보 없음.")));
            favoriteRepository.save(favorite);
            return ResponseEntity.ok(new FavoriteDTO(favorite.getFavoriteId(), favorite.getProfilePicId(), favorite.getPlaceName(), favorite.getCategory().getCategoryId(), favorite.getUser().getUserId()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<?> deleteFavorite(Long id) {
        Optional<Favorite> favoriteOpt = favoriteRepository.findById(id);
        if (favoriteOpt.isPresent()) {
            favoriteRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}