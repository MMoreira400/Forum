package com.forun.Forum.model.entites;

import com.forun.Forum.model.repositories.UserRepository;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "USUARIOS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long autoId;
    @NotBlank @Email
    //@Column( nullable=false, unique=true)
    private String email;
    @NotBlank
    //@Column( nullable=false, unique=true)
    private String username;
    @NotBlank
    private String password;
    @OneToMany(targetEntity = Postagem.class, cascade = CascadeType.ALL)
    private List<Postagem> Postagem;

    public User() {
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Long getAutoId() {
        return autoId;
    }

    public List<com.forun.Forum.model.entites.Postagem> getPostagem() {
        return Postagem;
    }

    public String getPassword() {
        return password;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (!getUsername().equals(user.getUsername())) return false;
        return getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getEmail().hashCode();
        result = 31 * result + getUsername().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "autoId=" + autoId +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", Postagem=" + Postagem +
                '}';
    }


    public void setPostagem(Postagem postagem) {
        this.Postagem.add(postagem);
    }
}
