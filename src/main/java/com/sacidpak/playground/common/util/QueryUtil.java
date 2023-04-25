package com.sacidpak.playground.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sacidpak.playground.common.dto.BaseEntityDTO;
import java.util.HashMap;
import java.util.Map;

public class QueryUtil {

    private static Map<String, Object> conditionMap;

    public static <D extends BaseEntityDTO> Map<String,Object> conditionMapFromReq(D searchParams){
        conditionMap = new HashMap<>();
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> paramMap = oMapper.convertValue(searchParams, Map.class);
        paramMap.forEach((parentKey,parentValue) -> {
            if(parentValue instanceof Map<?,?>){
                childRelationHandler(parentKey,(Map<String, Object>)parentValue);
            }else if(parentValue != null){
                conditionMap.put(parentKey,parentValue);
            }
        });
        return conditionMap;
    }

    private static void childRelationHandler(String parentKey, Map<String,Object> valueMap){
        valueMap.forEach((key,value) -> {
            String conditionKey = parentKey + "." + key;
            if(value instanceof Map<?,?>){
                ((Map<String, Object>) value).forEach((childKey, childValue) -> {
                    String appendedKey = conditionKey + "." + childKey;
                    childRelationHandler(appendedKey,(Map<String, Object>)childValue);
                });
            }else if(value != null){
                conditionMap.put(conditionKey,value);
            }
        });
    }
}
