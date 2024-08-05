package com.example.atc.domain.user.repository;

import com.example.atc.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByMemberIdAndUserPw(String memberId, String userPw);
    boolean existsByMemberId(String memberId);
    Optional<User> findByMemberId(String memberId);

}
