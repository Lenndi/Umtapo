package org.lendi.umtapo.service.generic;

import java.io.Serializable;
import java.util.List;

/**
 * Generic service inteface.
 * <p>
 * Created by axel on 29/11/16.
 */
public interface GenericService<T, ID extends Serializable> {

    T save(T entity);

    T findOne(ID id);

    List<T> findAll();

    void delete(ID id);

    Boolean exists(ID id);
}
