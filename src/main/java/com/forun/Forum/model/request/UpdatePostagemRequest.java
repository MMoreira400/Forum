package com.forun.Forum.model.request;

import com.forun.Forum.model.entites.Postagem;
import com.forun.Forum.model.repositories.PostagemRepository;

import java.util.Optional;

public class UpdatePostagemRequest {
    private String titulo;
    private String descricao;

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Postagem alteraePersistePostagem(PostagemRepository postagemRepository, Long idPostagem, UpdatePostagemRequest updatePostagem){
        Postagem postagem = postagemRepository.getReferenceById(idPostagem);
        postagem.setTitulo(updatePostagem.getTitulo());
        postagem.setDescricao(updatePostagem.getDescricao());
        postagemRepository.saveAndFlush(postagem);
        return postagem;
    }
}
