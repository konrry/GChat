package net.galvin.chat.server.jdbc.row.mapper;

import java.sql.ResultSet;

/**
 * Created by galvin on 17-5-28.
 */
public interface RowMapper<T> {

    T action(ResultSet resultSet);

}
