package com.forun.Forum.model.repositories;

import com.forun.Forum.model.entites.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {
    @Override
    Optional<Postagem> findById(Long aLong);
    @Query
            ("")
    List<Postagem> findAllByUserAutoId(Long autoIdUser);
}
