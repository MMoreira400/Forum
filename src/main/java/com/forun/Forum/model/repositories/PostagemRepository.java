package com.forun.Forum.model.repositories;

import com.forun.Forum.model.entites.Postagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {

    Optional<Postagem> findById(Long aLong);
    Page<Postagem> findAll(Pageable paginacao);

    Page<Postagem> findAllByUserAutoId(Long autoIdUser, Pageable paginacao);
}
