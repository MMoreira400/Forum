package com.forun.Forum.model.request;

import com.forun.Forum.model.entites.Postagem;
import com.forun.Forum.model.entites.User;
import com.forun.Forum.model.repositories.UserRepository;

import javax.validation.constraints.NotBlank;
import java.util.List;

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

    public User adicionaePersistePostagemNoUsuario(UserRepository userRepository, Postagem postagem,User usuario){
        usuario.setPostagem(postagem);
        userRepository.saveAndFlush(usuario);
        return usuario;
    }

    @Override
    public String
    toString() {
        return "NovaPostagemRequest{" +
                "usuario=" + usuario +
                ", postagem=" + postagem +
                '}';
    }
}
