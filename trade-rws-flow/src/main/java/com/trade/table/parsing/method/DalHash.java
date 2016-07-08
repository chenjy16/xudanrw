package com.trade.table.parsing.method;

import java.util.List;

import com.trade.rws.util.DalUtils;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;


public class DalHash implements TemplateMethodModel{


    @SuppressWarnings("rawtypes")
    @Override
    public Object exec(List args) throws TemplateModelException {
        if (args.size() != 3L) {//集合元素只能为3个，否则报异常
            throw new TemplateModelException(
                    "the field number of  tableRouteMethod hash(main_name,route_param,table_number)  is wrong");
        }

        String tableName;
        String paramValue;
        int number;
        try {

            tableName = args.get(0).toString();
            paramValue = args.get(1).toString();
            number = Integer.valueOf(args.get(2).toString());

            if (tableName.equals("")) {
                throw new TemplateModelException(
                        "main_name in tableRouteMethod hash(main_name,route_param,table_number) is null");
            }

            if (paramValue.equals("")) {
                throw new TemplateModelException(
                        "can not get route_param in tableRouteMethod hash(main_name,route_param,table_number) " +
                        "from paramMap");
            }
            long hashCode = DalUtils.HashAlgorithm.KETAMA_HASH.hash(
                    DalUtils.computeMd5(paramValue), 0);
            tableName = tableName + hashCode % number;
            return tableName.toUpperCase();

        } catch (NumberFormatException e) {
            throw new TemplateModelException(
                    "field type error in tableRouteMethod hash(main_name,route_param,table_number)");
        }

    }

}
