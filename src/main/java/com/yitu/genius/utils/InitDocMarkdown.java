package com.yitu.genius.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author: ⚡
 * @Description:
 * @Date: Created in 2020-09-04 下午 06:23
 * Modified By:
 */
public class InitDocMarkdown {

    public static String generateInterfaceDoc(Class interfaceClass){
        if (!interfaceClass.isInterface()){
            throw new RuntimeException("非接口类不支持生成接口文档");
        }
        StringBuilder sb=new StringBuilder();
        sb.append("# ").append(interfaceClass.getSimpleName()).append("\n");
        sb.append("> ").append(interfaceClass.getName()).append("\n\n");
        Method[] methods = interfaceClass.getMethods();
        for (Method method:methods){
            sb.append("## ").append(method.getName()).append("\n");
            Parameter[] parameters = method.getParameters();
            StringBuilder paramSb=new StringBuilder();
            StringBuilder paramTableSb=new StringBuilder();
            paramTableSb.append("|参数名称|参数类型|参数描述|\n").append(":-:|:-:|:-:\n");
            for (Parameter parameter:parameters){
                String parameterName = parameter.getName();
                String typeName = parameter.getType().getName();
                if (typeName.endsWith("List") || typeName.endsWith("Page") || typeName.endsWith("Map")){
                    typeName=parameter.getParameterizedType().getTypeName();
                }
                paramSb.append(typeName).append(" ").append(parameterName).append(",");
                paramTableSb.append(parameterName).append("|").append(typeName).append("|").append("\n");
            }
            paramSb.deleteCharAt(paramSb.length()-1);
            sb.append("> ").append(method.getName()).append("(").append(paramSb.toString()).append(")").append("\n\n")
                    .append(paramTableSb.toString()).append("\n")
                    .append("返回值:").append(method.getGenericReturnType().getTypeName()).append("\n\n")
                    .append("---\n");
        }
        return sb.toString();
    }

    public static String generateModelDoc(Class modelClass){
        if (modelClass.isInterface()){
            throw new RuntimeException("接口类不支持生成模型文档");
        }
        StringBuilder sb=new StringBuilder();
        sb.append("# ").append(modelClass.getSimpleName()).append("\n");
        sb.append("> ").append(modelClass.getName()).append("\n\n");
        sb.append("|字段名称|字段类型|字段描述|\n").append(":-:|:-:|:-:\n");
        Field[] fields = modelClass.getDeclaredFields();
        for (Field field:fields){
            if (field.getName().equals("serialVersionUID")){
                continue;
            }
            sb.append(field.getName()).append("|").append(field.getType().getName()).append("|").append("\n");
        }
        return sb.toString();
    }

}
