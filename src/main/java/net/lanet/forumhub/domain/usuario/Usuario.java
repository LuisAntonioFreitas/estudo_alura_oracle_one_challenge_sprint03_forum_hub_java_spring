package net.lanet.forumhub.domain.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lanet.forumhub.domain.perfil.Perfil;
import net.lanet.forumhub.domain.resposta.Resposta;
import net.lanet.forumhub.domain.topico.Topico;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Entity(name = "Usuario")
@Table(name = "tb_usuarios", indexes = {
        @Index(name = "idx_uuid", columnList = "uuid"),
        @Index(name = "idx_login", columnList = "login"),
        @Index(name = "idx_email", columnList = "email")})
@Data // Contempla ( @Getter @Setter @EqualsAndHashCode @ToString )
@NoArgsConstructor @AllArgsConstructor
public class Usuario implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true, updatable=false)
    private Long id;

    @Column(name="uuid", nullable=false, unique=true, updatable=false, length=50)
    private String uuid;

    @Column(name="nome", nullable=false, length=255)
    private String nome;

    @Column(name="login", nullable=false, unique=true, length=100)
    private String login;

    @Column(name="email", nullable=false, unique=true, length=255)
    private String email;

    @JsonIgnore
    @Column(name="senha", nullable=false, length=255)
    private String senha;

    @Column(name="ativo", nullable=false)
    private Boolean ativo;

    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable=false)
    private LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name="perfil_id")
    private Perfil perfil;

    @OneToMany(mappedBy = "autor",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Topico> topicos = new ArrayList<>();

    @OneToMany(mappedBy = "autor",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Resposta> respostas = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        this.uuid = UUID.randomUUID().toString();

        this.ativo = true;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    public Usuario(UsuarioDtoCreateRequest data, Perfil perfil) {
        this.nome = data.nome();
        this.login = data.login();
        this.email = data.email();
        this.senha = data.senha();
        this.perfil = perfil;
    }


    public void update(UsuarioDtoUpdateRequest data, Perfil perfil) {
        if (data.nome() != null) {
            this.nome = data.nome();
        }
        if (data.login() != null) {
            this.login = data.login();
        }
        if (data.email() != null) {
            this.email = data.email();
        }
        if (perfil != null) {
            this.perfil = perfil;
        }
        onUpdate();
    }
    public void delete() {
        this.ativo = false;
        onUpdate();
    }
    public void ativa() {
        this.ativo = true;
        onUpdate();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public String getPassword() { return this.senha; }
    @Override
    public String getUsername() { return this.login; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return this.ativo; }


}
