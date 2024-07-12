package net.lanet.forumhub.domain.resposta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRespostaRepository extends JpaRepository<Resposta, Long> {
    Optional<Resposta> findFirstTop1ByIdOrUuid(Long id, String uuid);

    String queryFindAllTopicoId =
            """
            SELECT  r
            FROM    Resposta r
            JOIN    r.topico t
            WHERE   (t.uuid = :topicoId)
            ORDER BY r.dataCriacao DESC
            """;
    @Query(queryFindAllTopicoId)
    List<Resposta> findAllTopicoId(String topicoId);

    String queryFindAllAtivoTrueAndTopicoId =
            """
            SELECT  r
            FROM    Resposta r
            JOIN    r.topico t
            WHERE   (r.ativo = true)
            AND     (t.uuid = :topicoId)
            ORDER BY r.dataCriacao DESC
            """;
    @Query(queryFindAllAtivoTrueAndTopicoId)
    List<Resposta> findAllAtivoTrueAndTopicoId(String topicoId);
    @Query(queryFindAllAtivoTrueAndTopicoId)
    Page<Resposta> findAllAtivoTrueAndTopicoId(Pageable page, String topicoId);

    String queryFindAllFilter =
            """
            SELECT  r
            FROM    Resposta r
            JOIN    r.autor a
            JOIN    r.topico t
            WHERE   (t.uuid = :topicoId)
            AND     (r.mensagem LIKE %:search%
                    OR a.login LIKE %:search%)
            ORDER BY r.dataCriacao DESC
            """;
    @Query(queryFindAllFilter)
    List<Resposta> findAllFilter(String topicoId, String search);
    @Query(queryFindAllFilter)
    Page<Resposta> pageFindAllFilter(Pageable page, String topicoId, String search);

    String queryFindAllAtivoTrueFilter =
            """
            SELECT  r
            FROM    Resposta r
            JOIN    r.autor a
            JOIN    r.topico t
            WHERE   (r.ativo = true)
            AND     (t.uuid = :topicoId)
            AND     (r.mensagem LIKE %:search%
                    OR a.login LIKE %:search%)
            ORDER BY r.dataCriacao DESC
            """;
    @Query(queryFindAllAtivoTrueFilter)
    List<Resposta> findAllAtivoTrueFilter(String topicoId, String search);
    @Query(queryFindAllAtivoTrueFilter)
    Page<Resposta> pageFindAllAtivoTrueFilter(Pageable page, String topicoId, String search);
}
