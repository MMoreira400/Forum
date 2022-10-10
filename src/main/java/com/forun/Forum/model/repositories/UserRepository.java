package com.forun.Forum.model.repositories;

import com.forun.Forum.model.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    @Override
    Optional<User> findById(Long aLong);

    boolean existsByEmail(String email);
    User findByEmailContainingIgnoreCase(String email);
}
