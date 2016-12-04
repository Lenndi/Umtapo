package org.lendi.umtapo.service.generic;

import java.io.Serializable;
import java.util.List;

/**
 * Generic service inteface.
 *
 * Created by axel on 29/11/16.
 */
public interface GenericService <T, ID extends Serializable> {

 <S extends T> S save(S entity);

 T findOne(ID id);

 List<T> findAll();

 void delete(ID id);
}
