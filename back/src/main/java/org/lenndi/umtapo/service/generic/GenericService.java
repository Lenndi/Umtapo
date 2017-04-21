package org.lenndi.umtapo.service.generic;

import java.io.Serializable;
import java.util.List;

/**
 * Generic service inteface.
 * <p>
 * Created by axel on 29/11/16.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
public interface GenericService<T, ID extends Serializable> {

    /**
     * Save t.
     *
     * @param entity the entity
     * @return the t
     */
    T save(T entity);

    /**
     * Find one t.
     *
     * @param id the id
     * @return the t
     */
    T findOne(ID id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<T> findAll();

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(ID id);

    /**
     * Exists boolean.
     *
     * @param id the id
     * @return the boolean
     */
    Boolean exists(ID id);
}
