package org.lendi.umtapo.service.generic.implementation;

import org.lendi.umtapo.service.generic.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;


/**
 * Generic service implementation.
 *
 * Created by axel on 29/11/16.
 */
public class GenericServiceImpl<T, ID extends Serializable> implements GenericService<T, ID> {

 @Autowired
 private JpaRepository<T, ID> repository;

 @Override
 public <S extends T> S save(S entity) {
  return repository.save(entity);
 }
 @Override
 public T findOne(ID id) {
  return repository.findOne(id);
 }

 @Override
 public List<T> findAll() {
  return repository.findAll();
 }

 @Override
 public void delete(ID id) {
  repository.delete(id);
 }
}
