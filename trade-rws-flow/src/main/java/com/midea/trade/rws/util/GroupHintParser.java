
package com.midea.trade.rws.util;

import org.apache.commons.logging.Log;import org.apache.commons.logging.LogFactory;import com.midea.trade.rws.dbselect.DBSelector;

public class GroupHintParser {
	public static Log log = LogFactory.getLog(GroupHintParser.class);

	public static Integer convertHint2Index(String sql) {
		String groupIndexHint = extractTDDLGroupHintString(sql);
		if (null != groupIndexHint && !groupIndexHint.equals("")) {
		    String[] piece=groupIndexHint.split(":");
		    return Integer.valueOf(piece[1]);
		} else {
			return DBSelector.NOT_EXIST_USER_SPECIFIED_INDEX;
		}
	}

	private static String extractTDDLGroupHintString(String sql) {
		return TStringUtil.getBetween(sql.toLowerCase(), "/*+tddl_group({", "})*/");
	}

	public static String removeTddlGroupHint(String sql) {
		String tddlHint= extractTDDLGroupHintString(sql);
		if(null==tddlHint||"".endsWith(tddlHint)){
			return  sql;
		}
		
	    sql=TStringUtil.removeBetweenWithSplitor(sql.toLowerCase(), "/*+tddl_group({", "})*/");
	    return sql;
	}

	public static void main(String[] args) {
		String sql="/*+TDDL_GROUP({groupIndex:12})*/select * from tab";

		System.out.println(convertHint2Index(sql));
		System.out.println(removeTddlGroupHint(sql));
	}
}
