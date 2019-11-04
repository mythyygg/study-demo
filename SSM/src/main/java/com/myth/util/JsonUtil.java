package com.myth.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


import java.text.SimpleDateFormat;


@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();
    static{
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //是否转换timestamps形式, 默认为 true
        // 置为 false，配合 setDateFormat 使用
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        //忽略空Bean转json的错误,
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
//        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }



    public static <T> String obj2String(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String)obj :  objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error",e);
            return null;
        }
    }

    public static <T> String obj2StringPretty(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String)obj :  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error",e);
            return null;
        }
    }





    public static <T> T string2Obj(String str,Class<T> clazz){
        if(StringUtils.isEmpty(str) || clazz == null){
            return null;
        }

        try {
            return clazz.equals(String.class)? (T)str : objectMapper.readValue(str,clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }



    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(str) || typeReference == null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class)? str : objectMapper.readValue(str,typeReference));
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }


    public static <T> T string2Obj(String str,Class<?> collectionClass,Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }


    /*public static void main(String[] args) {

        JsonClass jsonClass1 = new JsonClass();
        jsonClass1.setAge(10);
        jsonClass1.setName("hello");
        jsonClass1.setDate(new Date());

        JsonClass jsonClass2 = new JsonClass();
        jsonClass2.setAge(10);
        jsonClass2.setName("hello");
        jsonClass2.setDate(new Date());
        List<JsonClass> list = Lists.newArrayList();
        list.add(jsonClass1);
        list.add(jsonClass2);
        String listStr = JsonUtil.obj2StringPretty(list);
        log.info("=====================");
        log.info(listStr);

        List<JsonClass> list1 = JsonUtil.string2Obj(listStr, new TypeReference<List<JsonClass>>() {
        });

    }*/





}
