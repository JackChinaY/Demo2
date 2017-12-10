package util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 确保一个ConnectionHolder只有一个connetion
 */
public class ConnectionHolder
{
    private Map<DataSource, Connection> connectionMap = new HashMap<DataSource, Connection>();
    //获取数据库连接
    public Connection getConnection(DataSource dataSource) throws SQLException
    {
        Connection connection = connectionMap.get(dataSource);
        //如果无连接或连接关闭，怎重新建立连接
        if (connection == null || connection.isClosed())
        {
            connection = dataSource.getConnection();
            connectionMap.put(dataSource, connection);
        }
        return connection;
    }

    public void removeConnection(DataSource dataSource)
    {
        connectionMap.remove(dataSource);
    }
}
