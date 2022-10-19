package com.forun.Forum.model.reponse;


import com.forun.Forum.model.entites.Postagem;
import com.forun.Forum.model.entites.StatusPostagem;
import org.springframework.data.domain.Page;

import java.util.Date;

public class PostagemDTO {
    private String titulo;
    private String descriacao;
    private Date dataCriacao;
    private StatusPostagem statusPostagem;
    private UserDTO usuario;


    public PostagemDTO(Postagem postagem) {
        this.titulo = postagem.getTitulo();
        this.descriacao = postagem.getDescricao();
        this.dataCriacao = postagem.getDataCriacao();
        this.statusPostagem = postagem.getStatus();
        this.usuario = new UserDTO(postagem.getUser());
    }


    public static Page<PostagemDTO> converterPostagemPage(Page<Postagem> postagemPage){
        return postagemPage.map(PostagemDTO::new);
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

    public UserDTO getUsuario() {
        return usuario;
    }

    public StatusPostagem getStatusPostagem() {
        return statusPostagem;
    }


}
