package com.sacidpak.playground.common.repository;

import com.sacidpak.playground.common.util.DateUtil;
import com.sacidpak.playground.common.util.PageUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoRepositoryBean
public class BaseRepositoryImpl<T, ID  extends UUID> extends SimpleJpaRepository<T,ID> implements IBaseRepository<T,ID> {

    private EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
    }

    @Override
    public List<T> save(Iterable<T> entities) {
        return saveAll(entities);
    }

    @Override
    public Page<T> findByConditionMap(Map<String, Object> conditionMap, Pageable pageable) {
        Class<T> entityClass = getDomainClass();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);

        if(conditionMap != null && !conditionMap.isEmpty()){
            criteriaQuery.where(createPredicates(root, conditionMap));
        }

        List<T> result;
        Sort sort = PageUtil.defaultDataSort();
        if(pageable != null){
            sort = pageable.getSort();
            criteriaQuery.orderBy(createOrder(criteriaBuilder,root,sort));
            result = entityManager.createQuery(criteriaQuery.select(root))
                                  .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                                  .setMaxResults(pageable.getPageSize()).getResultList();

            return new PageImpl<>(result, pageable, 0);
        }else{
            criteriaQuery.orderBy(createOrder(criteriaBuilder,root,sort));
            result = entityManager.createQuery(criteriaQuery.select(root)).getResultList();

            return new PageImpl<>(result);
        }
    }

    @Override
    public List<T> findByConditionMap(Map<String, Object> conditionMap) {
        return findByConditionMap(conditionMap,null).getContent();
    }

    private Predicate[] createPredicates(Root<T> root, Map<String, Object> conditionMap) {
        Predicate[] result = null;
        List<Predicate> predicates = new ArrayList<>();
        conditionMap.forEach((k,v) -> {
            if(v != null)
                predicates.add(preparePredicate(root, k, v));
        });
        result = predicates.toArray(new Predicate[predicates.size()]);
        return result;
    }

    private Predicate preparePredicate(From<?,?> root, String fieldName, Object value) {
        Predicate predicate = null;
        int index = fieldName.indexOf(".");
        if(index > 0){
            String rootFieldName = fieldName.substring(0, index);
            String subFieldName = fieldName.substring(index+1);
            predicate = preparePredicate(root.join(rootFieldName, JoinType.INNER), subFieldName, value);
        }else{
            Expression<?> currentPath = root.get(fieldName);
            List<Object> valueList = new ArrayList<>();
            for (String val : value.toString().split(",")){
                valueList.add(checkValue(fieldName,currentPath.getJavaType(), val));
            }
            predicate = currentPath.in(valueList);
        }
        return predicate;
    }

    private Object checkValue(String fieldName, Class<?> type, Object value){
        Object result = value;
        if(fieldName.equals("id")){
            result = UUID.fromString(String.valueOf(value));
        }else if(Boolean.class.isAssignableFrom(type)){
            result = Boolean.valueOf(String.valueOf(value));
        }else if(LocalDate.class.isAssignableFrom(type)){
            result = DateUtil.parseString(String.valueOf(value),"yyyy-MM-dd");
        } else if (type.isEnum()) {
            result = Enum.valueOf((Class<? extends Enum>) type, String.valueOf(value));
        }

        return result;
    }

    private List<Order> createOrder(CriteriaBuilder criteriaBuilder, From<?,?> root, Sort sort) {
        List<Order> orderList = new ArrayList();
        sort.forEach(order -> {
            Expression<?> expression = root.get(order.getProperty());
            orderList.add(order.isAscending() ? criteriaBuilder.asc(expression) : criteriaBuilder.desc(expression));
        });
        return orderList;
    }
}
