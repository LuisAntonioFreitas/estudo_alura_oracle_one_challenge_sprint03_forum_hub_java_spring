package net.lanet.forumhub.infra.shared;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static net.lanet.forumhub.infra.utilities.HandleFindIdOrUuidUtil.getIdOrUuid;

@Component
public class JpaRepositoryCustom {

    public interface MethodsStandard<T> extends JpaRepository<T, String> {
        Optional<T> findFirstTop1ByIdOrUuid(Long id, String uuid);
    }

    public static <T> T findEntityNotNull(String id, T entity, MethodsStandard<T> repository, Object[] item) {
        String message = String.format("%s não foi encontrad%s", item[1], item[0]);
        if (id == null && entity != null) { return entity; }
        if (id == null) { throw new EntityNotFoundException(message); }
        Object[] value = getIdOrUuid(id);
        Long longId = (Long) value[0];
        String uuid = (String) value[1];
        Optional<T> optional = repository.findFirstTop1ByIdOrUuid(longId, uuid);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException(message);
        }
        return optional.get();
    }
    public static <T> T findEntityNull(String id, T entity, MethodsStandard<T> repository, Object[] item) {
        if (id == null && entity == null) { return null; }
        return findEntityNotNull(id, entity, repository, item);
    }

}
