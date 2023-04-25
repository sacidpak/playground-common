package com.sacidpak.playground.common.service;

import com.sacidpak.playground.common.domain.BaseEntity;
import com.sacidpak.playground.common.dto.BaseEntityDTO;
import com.sacidpak.playground.common.dto.LightShortCodeEntityDTO;
import com.sacidpak.playground.common.dto.PagedApiRequest;
import com.sacidpak.playground.common.repository.IBaseRepository;
import com.sacidpak.playground.common.util.PageUtil;
import com.sacidpak.playground.common.util.QueryUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity, D extends BaseEntityDTO, ID  extends UUID>
        implements IBaseEntityService<T,D,ID> {

    @Autowired
    protected IBaseRepository<T,ID> baseRepository;
    protected Class<T> entityClass;
    protected Class<D> dtoClass;

    public BaseServiceImpl(){
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        this.dtoClass = (Class<D>) parameterizedType.getActualTypeArguments()[1];
    }

    protected void validateSave(D dto){}

    protected void validateUpdate(D dto){}

    protected void validateDelete(ID id, Integer version){}

    protected T dtoToEntity(D dto){
        T entity;
        if(dto.getId() != null){
            entity = baseRepository.findOne((ID) dto.getId());
            new ModelMapper().map(dto, entity);
        }else{
            entity = new ModelMapper().map(dto, entityClass);
        }

        return entity;
    }


    @Override
    @Transactional
    public ID save(D dto) {
        validateSave(dto);
        T entity = dtoToEntity(dto);
        return (ID) baseRepository.save(entity).getId();
    }

    @Override
    public void update(D dto) {
        validateUpdate(dto);
        dto.setVersion(dto.getVersion() + 1);
        T entity = dtoToEntity(dto);
        baseRepository.save(entity);
    }

    @Override
    public void delete(ID id, Integer version) {
        validateDelete(id,version);
        T entity = baseRepository.findOne(id);
        entity.setDeleted(true);
        baseRepository.save(entity);
    }

    @Override
    public D getById(ID id) {
        T entity = baseRepository.findOne(id);
        return new ModelMapper().map(entity, dtoClass);
    }

    @Override
    public List<D> getAll(D searchParams) {
        List<D> dtoList = new ArrayList<>();
        Map<String,Object> conditionMap = QueryUtil.conditionMapFromReq(searchParams);
        List<T> entityList = baseRepository.findByConditionMap(conditionMap);

        for (T entity : entityList){
            dtoList.add(new ModelMapper().map(entity, dtoClass));
        }

        return dtoList;
    }

    @Override
    public Page<D> getPageableList(D searchParams, PagedApiRequest pageParams) {
        List<D> dtoList = new ArrayList<>();
        Map<String,Object> conditionMap = QueryUtil.conditionMapFromReq(searchParams);
        Pageable pageable = PageUtil.pageRequestToPageable(pageParams);
        Page<T> pageableList = baseRepository.findByConditionMap(conditionMap,pageable);

        for (T entity : pageableList.getContent()){
            dtoList.add(new ModelMapper().map(entity, dtoClass));
        }

        return new PageImpl<>(dtoList, pageable, pageableList.getTotalElements());
    }

    @Override
    public List<LightShortCodeEntityDTO> getLightList(D searchParams) {
        List<LightShortCodeEntityDTO> lightList = new ArrayList<>();
        Map<String,Object> conditionMap = QueryUtil.conditionMapFromReq(searchParams);
        List<T> entityList = baseRepository.findByConditionMap(conditionMap);

        for (T entity : entityList){
            lightList.add(new ModelMapper().map(entity, LightShortCodeEntityDTO.class));
        }

        return lightList;
    }
}
