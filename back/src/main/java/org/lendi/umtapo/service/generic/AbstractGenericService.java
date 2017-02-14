package org.lendi.umtapo.service.generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Generic service implementation.
 * <p>
 * Created by axel on 29/11/16.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
public abstract class AbstractGenericService<T, ID extends Serializable> implements GenericService<T, ID> {

    @Autowired
    private JpaRepository<T, ID> repository;

    /**
     * {@inheritDoc}
     */
    @Override
    public T save(T entity) {
        return this.repository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T findOne(ID id) {
        return this.repository.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll() {
        return this.repository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable the pageable
     * @return the page
     */
    public Page<T> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(ID id) {
        this.repository.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean exists(ID id) {
        return this.repository.exists(id);
    }

}
