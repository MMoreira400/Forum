package com.forun.Forum.model.request;

import com.forun.Forum.model.entites.Postagem;
import com.forun.Forum.model.entites.User;

public class NovaPostagemRequest {

    private User usuario;
    private Postagem postagem;

    public NovaPostagemRequest(User usuario, Postagem postagem) {
        this.usuario = usuario;
        this.postagem = postagem;
    }

    public User getUsuario() {
        return usuario;
    }

    public Postagem getPostagem() {
        return postagem;
    }
}
