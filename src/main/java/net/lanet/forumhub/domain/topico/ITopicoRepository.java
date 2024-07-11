package net.lanet.forumhub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findFirstTop1ByIdOrUuid(Long id, String uuid);

    List<Topico> findByAtivoTrueOrderByDataCriacaoDesc();
    Page<Topico> findByAtivoTrueOrderByDataCriacaoDesc(Pageable page);

    String queryFindAllFilter =
            """
            SELECT  t
            FROM    Topico t
            JOIN    t.curso c
            JOIN    t.autor a
            WHERE   (t.titulo LIKE %:search%
                    OR t.mensagem LIKE %:search%
                    OR CONCAT(t.status) LIKE %:search%
                    OR c.nome LIKE %:search%
                    OR CONCAT(c.categoria) LIKE %:search%
                    OR a.login LIKE %:search%)
            ORDER BY t.dataCriacao DESC
            """;
    @Query(queryFindAllFilter)
    List<Topico> findAllFilter(String search);
    @Query(queryFindAllFilter)
    Page<Topico> pageFindAllFilter(Pageable page, String search);

    String queryFindAllAtivoTrueFilter =
            """
            SELECT  t
            FROM    Topico t
            JOIN    t.curso c
            JOIN    t.autor a
            WHERE   (t.ativo = true)
            AND     (t.titulo LIKE %:search%
                    OR t.mensagem LIKE %:search%
                    OR CONCAT(t.status) LIKE %:search%
                    OR c.nome LIKE %:search%
                    OR CONCAT(c.categoria) LIKE %:search%
                    OR a.login LIKE %:search%)
            ORDER BY t.dataCriacao DESC
            """;
    @Query(queryFindAllAtivoTrueFilter)
    List<Topico> findAllAtivoTrueFilter(String search);
    @Query(queryFindAllAtivoTrueFilter)
    Page<Topico> pageFindAllAtivoTrueFilter(Pageable page, String search);

    String queryFindAllFilterCurso =
            """
            SELECT  t
            FROM    Topico t
            JOIN    t.curso c
            WHERE   (c.nome LIKE %:search%)
            ORDER BY t.dataCriacao DESC
            """;
    @Query(queryFindAllFilterCurso)
    List<Topico> findAllFilterCurso(String search);
    @Query(queryFindAllFilterCurso)
    Page<Topico> pageFindAllFilterCurso(Pageable page, String search);

    String queryFindAllAtivoTrueFilterCurso =
            """
            SELECT  t
            FROM    Topico t
            JOIN    t.curso c
            WHERE   (t.ativo = true)
            AND     (c.nome LIKE %:search%)
            ORDER BY t.dataCriacao DESC
            """;
    @Query(queryFindAllAtivoTrueFilterCurso)
    List<Topico> findAllAtivoTrueFilterCurso(String search);
    @Query(queryFindAllAtivoTrueFilterCurso)
    Page<Topico> pageFindAllAtivoTrueFilterCurso(Pageable page, String search);

    String queryFindAllFilterCursoCategoria =
            """
            SELECT  t
            FROM    Topico t
            JOIN    t.curso c
            WHERE   (CONCAT(c.categoria) LIKE %:search%)
            ORDER BY t.dataCriacao DESC
            """;
    @Query(queryFindAllFilterCursoCategoria)
    List<Topico> findAllFilterCursoCategoria(String search);
    @Query(queryFindAllFilterCursoCategoria)
    Page<Topico> pageFindAllFilterCursoCategoria(Pageable page, String search);

    String queryFindAllAtivoTrueFilterCursoCategoria =
            """
            SELECT  t
            FROM    Topico t
            JOIN    t.curso c
            WHERE   (t.ativo = true)
            AND     (CONCAT(c.categoria) LIKE %:search%)
            ORDER BY t.dataCriacao DESC
            """;
    @Query(queryFindAllAtivoTrueFilterCursoCategoria)
    List<Topico> findAllAtivoTrueFilterCursoCategoria(String search);
    @Query(queryFindAllAtivoTrueFilterCursoCategoria)
    Page<Topico> pageFindAllAtivoTrueFilterCursoCategoria(Pageable page, String search);

    String queryFindAllFilterAno =
            """
            SELECT  t
            FROM    Topico t
            WHERE   (YEAR(t.dataCriacao) = :search)
            ORDER BY t.dataCriacao DESC
            """;
    @Query(queryFindAllFilterAno)
    List<Topico> findAllFilterAno(String search);
    @Query(queryFindAllFilterAno)
    Page<Topico> pageFindAllFilterAno(Pageable page, String search);

    String queryFindAllFilterAutor =
            """
            SELECT  t
            FROM    Topico t
            JOIN    t.autor a
            WHERE   (a.login LIKE %:search%)
            ORDER BY t.dataCriacao DESC
            """;
    @Query(queryFindAllFilterAutor)
    List<Topico> findAllFilterAutor(String search);
    @Query(queryFindAllFilterAutor)
    Page<Topico> pageFindAllFilterAutor(Pageable page, String search);

    String queryFindAllAtivoTrueFilterAutor =
            """
            SELECT  t
            FROM    Topico t
            JOIN    t.autor a
            WHERE   (t.ativo = true)
            AND     (a.login LIKE %:search%)
            ORDER BY t.dataCriacao DESC
            """;
    @Query(queryFindAllAtivoTrueFilterAutor)
    List<Topico> findAllAtivoTrueFilterAutor(String search);
    @Query(queryFindAllAtivoTrueFilterAutor)
    Page<Topico> pageFindAllAtivoTrueFilterAutor(Pageable page, String search);

    String queryFindAllAtivoTrueFilterAno =
            """
            SELECT  t
            FROM    Topico t
            WHERE   (YEAR(t.dataCriacao) = :search)
            ORDER BY t.dataCriacao DESC
            """;
    @Query(queryFindAllAtivoTrueFilterAno)
    List<Topico> findAllAtivoTrueFilterAno(String search);
    @Query(queryFindAllAtivoTrueFilterAno)
    Page<Topico> pageFindAllAtivoTrueFilterAno(Pageable page, String search);
}
