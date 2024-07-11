package net.lanet.forumhub.domain.resposta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lanet.forumhub.domain.topico.Topico;
import net.lanet.forumhub.domain.usuario.Usuario;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity(name = "Resposta")
@Table(name = "tb_respostas", indexes = {
        @Index(name = "idx_uuid", columnList = "uuid")})
@Data // Contempla ( @Getter @Setter @EqualsAndHashCode @ToString )
@NoArgsConstructor @AllArgsConstructor
public class Resposta implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true, updatable=false)
    private Long id;

    @Column(name="uuid", nullable=false, unique=true, updatable=false, length=50)
    private String uuid;

    @Lob
    @Column(name="mensagem", nullable=false, columnDefinition = "mediumtext")
    private String mensagem;

    @Column(name="data_criacao", nullable=false, updatable=false)
    private LocalDateTime dataCriacao;

    @Column(name="solucao", nullable=false)
    private Boolean solucao;

    @Column(name="ativo", nullable=false)
    private Boolean ativo;

    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable=false)
    private LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name="autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name="topico_id")
    private Topico topico;


    @PrePersist
    protected void onCreate() {
        this.uuid = UUID.randomUUID().toString();

        this.solucao = false;
        this.ativo = true;

        this.dataCriacao = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    public Resposta(RespostaDtoCreateRequest data, Usuario autor, Topico topico) {
        this.mensagem = data.mensagem();
        this.autor = autor;
        this.topico = topico;
    }


    public void update(RespostaDtoUpdateRequest data, Usuario autor, Topico topico) {
        if (data.mensagem() != null) {
            this.mensagem = data.mensagem();
        }
        if (autor != null) {
            this.autor = autor;
        }
        if (topico != null) {
            this.topico = topico;
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
    public void solucao() {
        this.solucao = !this.solucao;
        onUpdate();
    }

}
