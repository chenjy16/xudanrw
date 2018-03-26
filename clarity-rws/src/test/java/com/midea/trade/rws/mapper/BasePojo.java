package com.midea.trade.rws.mapper;

import java.io.Serializable;

/**
 * @author Ou zhouyou (ouzhouyou@gmail.com)
 * @date 2013-12-12
 */
public class BasePojo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String splitTableName;
    private String splitDBName;

    public String getSplitTableName() {
        return splitTableName;
    }

    public void setSplitTableName(String splitTableName) {
        this.splitTableName = splitTableName;
    }

    public String getSplitDBName() {
        return splitDBName;
    }

    public void setSplitDBName(String splitDBName) {
        this.splitDBName = splitDBName;
    }
}