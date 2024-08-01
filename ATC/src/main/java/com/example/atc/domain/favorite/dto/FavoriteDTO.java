package com.example.atc.domain.favorite.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FavoriteDTO {
    private Long favoriteId;
    private Long profilePicId;
    private String placeName;
    private Long categoryId;
    private Long userId;
}