package com.example.atc.domain.favorite.entity;
import com.example.atc.domain.category.entity.Category;
import com.example.atc.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;
    private Long profilePicId;
    private String placeName;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}