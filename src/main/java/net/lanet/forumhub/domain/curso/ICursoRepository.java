package net.lanet.forumhub.domain.curso;

import net.lanet.forumhub.infra.shared.JpaRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICursoRepository extends JpaRepositoryCustom.MethodsStandard<Curso> {
//    Optional<Curso> findFirstTop1ByIdOrUuid(Long id, String uuid);

    List<Curso> findByAtivoTrueOrderByNomeAsc();
    Page<Curso> findByAtivoTrueOrderByNomeAsc(Pageable page);

    String queryFindAllFilter =
            """
            SELECT  c
            FROM    Curso c
            WHERE   (c.nome LIKE %:search%
                    OR CONCAT(c.categoria) LIKE %:search%)
            ORDER BY c.nome ASC
            """;
    @Query(queryFindAllFilter)
    List<Curso> findAllFilter(String search);
    @Query(queryFindAllFilter)
    Page<Curso> pageFindAllFilter(Pageable page, String search);

    String queryFindAllAtivoTrueFilter =
            """
            SELECT  c
            FROM    Curso c
            WHERE   (c.ativo = true)
            AND     (c.nome LIKE %:search%
                    OR CONCAT(c.categoria) LIKE %:search%)
            ORDER BY c.nome ASC
            """;
    @Query(queryFindAllAtivoTrueFilter)
    List<Curso> findAllAtivoTrueFilter(String search);
    @Query(queryFindAllAtivoTrueFilter)
    Page<Curso> pageFindAllAtivoTrueFilter(Pageable page, String search);
}
