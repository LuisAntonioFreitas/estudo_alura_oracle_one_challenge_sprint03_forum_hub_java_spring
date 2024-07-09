package net.lanet.forumhub.domain.perfil;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lanet.forumhub.domain.usuario.Usuario;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "Perfil")
@Table(name = "tb_perfis", indexes = {
        @Index(name = "idx_uuid", columnList = "uuid") })
@Data // Contempla ( @Getter @Setter @EqualsAndHashCode @ToString )
@NoArgsConstructor
@AllArgsConstructor
public class Perfil implements Serializable {
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

    @Column(name="ativo", nullable=false)
    private Boolean ativo;

    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable=false)
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "perfil",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Usuario> usuarios = new ArrayList<>();


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


    public Perfil(PerfilDtoCreateRequest data) {
        this.nome = data.nome();
    }


    public void update(PerfilDtoUpdateRequest data) {
        if (data.nome() != null) {
            this.nome = data.nome();
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


}
