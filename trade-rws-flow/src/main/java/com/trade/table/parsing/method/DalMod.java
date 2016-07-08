package com.trade.table.parsing.method;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;


public class DalMod implements TemplateMethodModel{

	@SuppressWarnings("rawtypes")
    @Override
    public Object exec(List args) throws TemplateModelException {
        if (args.size() != 3L) {//集合元素只能为3个，否则报异常
            throw new TemplateModelException(
                    "the field number of  tableRouteMethod mod(main_name,route_param,table_number)  is wrong");
        }

        String tableName;
        long paramValue;
        int number;
        try {

            tableName = args.get(0).toString();
            paramValue = Long.valueOf(args.get(1).toString());
            number = Integer.valueOf(args.get(2).toString());

            if (tableName.equals("")) {
                throw new TemplateModelException(
                        "main_name in tableRouteMethod mod(main_name,route_param,table_number) is null");
            }
            tableName = tableName + paramValue % number;
            return tableName.toUpperCase();

        } catch (NumberFormatException e) {
            throw new TemplateModelException(
                    "field type error in tableRouteMethod mod(main_name,route_param,table_number)");
        }

    }

}
