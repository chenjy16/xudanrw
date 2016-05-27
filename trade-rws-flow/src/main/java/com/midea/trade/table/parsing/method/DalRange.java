package com.midea.trade.table.parsing.method;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;


public class DalRange implements TemplateMethodModel {

    @SuppressWarnings("rawtypes")
    @Override
    public Object exec(List args) throws TemplateModelException {

        if (args.size() != 3L) {//集合元素只能为3个，否则报异常
            throw new TemplateModelException(
                    "the field number of  tableRouteMethod range(main_name,route_param,table_length)  is wrong");
        }
        String tableName;
        long paramValue;
        long length;
        try {
            tableName = args.get(0).toString();
            paramValue = Long.valueOf(args.get(1).toString());
            length = Long.valueOf(args.get(2).toString());
            if (tableName.equals("")) {
                throw new TemplateModelException(
                        "main_name in tableRouteMethod range(main_name,route_param,table_length) is null");
            }
            tableName = tableName + (paramValue - 1) / length;
            return tableName.toUpperCase();

        } catch (NumberFormatException e) {
            throw new TemplateModelException(
                    "field type error in tableRouteMethod range(main_name,route_param,table_length)");
        }
    }

}
