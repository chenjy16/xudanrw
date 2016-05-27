package com.midea.trade.table.parsing.method;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.midea.trade.rws.exception.DalException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateModel;

public class FreeMakerParser {
	private static final String DEFAULT_TEMPLATE_KEY = "default_template_key";
    private static final Configuration CONFIGURER = new Configuration();
    static {
        CONFIGURER.setClassicCompatible(true);
        CONFIGURER.setSharedVariable("hash",new DalHash());
        CONFIGURER.setSharedVariable("mod",new DalMod());
        CONFIGURER.setSharedVariable("range",new DalRange());
        CONFIGURER.setSharedVariable("HASH",new DalHash());
        CONFIGURER.setSharedVariable("MOD",new DalMod());
        CONFIGURER.setSharedVariable("RANGE",new DalRange());
    }
    
    public static void setSharedVariable(String name, TemplateModel tm){
        CONFIGURER.setSharedVariable(name, tm);
    }
    
    
    private static Template createTemplate(StringReader reader) throws IOException {
        Template template = new Template(DEFAULT_TEMPLATE_KEY, reader, CONFIGURER);
        template.setNumberFormat("#");
        return template;
    }

    public static String process(Object root, String sql) {
        StringReader reader = null;
        StringWriter out = null;
        Template template = null;
        try {
        	sql=sql.replaceAll("@","\\$");
            reader = new StringReader(sql);
            template = createTemplate(reader);
            out = new StringWriter();
            template.process(root, out);
            return out.toString();
        } catch (Exception e) {
            throw new DalException(e);
        } finally {
            if (reader != null) {
                reader.close();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                return null;
            }
        }
    }
    
    
    
    
    
    public static void main(String[] args){
    	String sql="SELECT userId ,userName FROM ${hash('USER',userId,3)} WHERE userId = :userId";
    	Map<String,Object> root=new HashMap<String, Object>();
    	root.put("userId", 5);
    	System.out.println(FreeMakerParser.process(root, sql));
    }
}
