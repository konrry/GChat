package net.galvin.chat.server.jdbc.comm;

import java.util.List;

/**
 * Created by galvin on 17-5-27.
 */
public class SqlWrapper {

    private List<SqlParam> sqlParamList;
    private String sql;

    public List<SqlParam> getSqlParamList() {
        return sqlParamList;
    }

    public void setSqlParamList(List<SqlParam> sqlParamList) {
        this.sqlParamList = sqlParamList;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
