package net.lanet.forumhub.domain.perfil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findFirstTop1ByIdOrUuid(Long id, String uuid);

    List<Perfil> findByAtivoTrueOrderByNomeAsc();
    Page<Perfil> findByAtivoTrueOrderByNomeAsc(Pageable page);

    String queryFindAllFilter =
            """
            SELECT  p
            FROM    Perfil p
            WHERE   (p.nome LIKE %:search%)
            ORDER BY p.nome ASC
            """;
    @Query(queryFindAllFilter)
    List<Perfil> findAllFilter(String search);
    @Query(queryFindAllFilter)
    Page<Perfil> pageFindAllFilter(Pageable page, String search);

    String queryFindAllAtivoTrueFilter =
            """
            SELECT  p
            FROM    Perfil p
            WHERE   (p.ativo = true)
            AND     (p.nome LIKE %:search%)
            ORDER BY p.nome ASC
            """;
    @Query(queryFindAllAtivoTrueFilter)
    List<Perfil> findAllAtivoTrueFilter(String search);
    @Query(queryFindAllAtivoTrueFilter)
    Page<Perfil> pageFindAllAtivoTrueFilter(Pageable page, String search);
}
