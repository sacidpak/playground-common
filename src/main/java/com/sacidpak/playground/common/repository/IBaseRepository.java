package com.sacidpak.playground.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@NoRepositoryBean
public interface IBaseRepository<T, ID extends UUID> extends JpaRepository<T, ID> {

    default T findOne(ID id){
        return findById(id).orElse(null);
    };

    List<T> save(Iterable<T> entities);

    Page<T> findByConditionMap(Map<String, Object> conditionMap, Pageable pageable);

    List<T> findByConditionMap(Map<String, Object> conditionMap);

}
