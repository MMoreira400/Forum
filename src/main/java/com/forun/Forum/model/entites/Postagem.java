package com.forun.Forum.model.entites;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Postagem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO  )
    private Long autoId;
    private String titulo;
    private String descricao;
    private Date dataCriacao = new Date();
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private User user;
    @Enumerated(EnumType.STRING)
    private StatusPostagem status = StatusPostagem.ABERTO;

    public Postagem(String titulo, String descricao, User user){
        this.titulo = titulo;
        this.descricao = descricao;
        this.user = user;
    }



    public Postagem(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Postagem() {
    }

    public Long getAutoId() {
        return autoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StatusPostagem getStatus() {
        return status;
    }

    public void setStatus(StatusPostagem status) {
        this.status = status;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

}
