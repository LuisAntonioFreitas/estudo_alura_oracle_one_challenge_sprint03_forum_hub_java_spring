package net.lanet.forumhub.domain.topico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lanet.forumhub.domain.curso.Curso;
import net.lanet.forumhub.domain.usuario.Usuario;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity(name = "Topico")
@Table(name = "tb_topicos", indexes = {
        @Index(name = "idx_uuid", columnList = "uuid"),
        @Index(name = "idx_status", columnList = "status")})
@Data // Contempla ( @Getter @Setter @EqualsAndHashCode @ToString )
@NoArgsConstructor @AllArgsConstructor
public class Topico implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true, updatable=false)
    private Long id;

    @Column(name="uuid", nullable=false, unique=true, updatable=false, length=50)
    private String uuid;

    @Column(name="titulo", nullable=false, unique=true, length=255)
    private String titulo;

    @Lob
    @Column(name="mensagem", nullable=false, columnDefinition = "mediumtext")
    private String mensagem;

    @Column(name="data_criacao", nullable=false, updatable=false)
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private Status status;

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
    @JoinColumn(name="curso_id")
    private Curso curso;

//    @OneToMany(mappedBy = "resposta",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Resposta> respostas = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        this.uuid = UUID.randomUUID().toString();

        this.ativo = true;

        this.dataCriacao = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    public Topico(TopicoDtoCreateRequest data, Usuario autor, Curso curso) {
        this.titulo = data.titulo();
        this.mensagem = data.mensagem();
        this.status = Status.valueOf(data.status());
        this.autor = autor;
        this.curso = curso;
    }


    public void update(TopicoDtoUpdateRequest data, Usuario autor, Curso curso) {
        if (data.titulo() != null) {
            this.titulo = data.titulo();
        }
        if (data.mensagem() != null) {
            this.mensagem = data.mensagem();
        }
        if (data.status() != null) {
            this.status = Status.valueOf(data.status());
        }
        if (autor != null) {
            this.autor = autor;
        }
        if (curso != null) {
            this.curso = curso;
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
