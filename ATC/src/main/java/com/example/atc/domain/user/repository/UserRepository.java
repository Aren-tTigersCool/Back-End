package com.example.atc.domain.user.repository;

import com.example.atc.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.memberId = :memberId")
    Optional<User> findByMemberId(@Param("memberId") String memberId);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.memberId = :memberId AND u.userPw = :userPw")
    boolean existsByMemberIdAndUserPw(@Param("memberId") String memberId, @Param("userPw") String userPw);
    @Query("SELECT u.userId FROM User u WHERE u.memberId = :memberId")
    boolean existsByMemberId(@Param("memberId") String memberId);
}
