package com.forun.Forum.config;

public class ErroUsuarioDTO {

    private String campo;
    private String erro;


    public ErroUsuarioDTO(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
