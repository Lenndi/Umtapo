package org.lendi.umtapo.service.generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Generic service implementation.
 * <p>
 * Created by axel on 29/11/16.
 */
public abstract class AbstractGenericService<T, ID extends Serializable> implements GenericService<T, ID> {

    @Autowired
    private JpaRepository<T, ID> repository;

    @Override
    public T save(T entity) {
        return this.repository.save(entity);
    }

    @Override
    public T findOne(ID id) {
        return this.repository.findOne(id);
    }

    @Override
    public List<T> findAll() {
        return this.repository.findAll();
    }

    @Override
    public void delete(ID id) {
        this.repository.delete(id);
    }

    @Override
    public Boolean exists(ID id) {
        return this.repository.exists(id);
    }
}
