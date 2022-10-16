package com.forun.Forum.model.reponse;


import com.forun.Forum.model.entites.Postagem;
import com.forun.Forum.model.entites.User;

import java.util.Date;

public class PostagemDTO {
    private String titulo;
    private String descriacao;
    private Date dataCriacao;
    private User usuario;


    public PostagemDTO(Postagem postagem) {
        this.titulo = postagem.getTitulo();
        this.descriacao = postagem.getDescricao();
        this.dataCriacao = postagem.getDataCriacao();
        this.usuario = postagem.getUser();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescriacao() {
        return descriacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public User getUsuario() {
        return usuario;
    }
}
