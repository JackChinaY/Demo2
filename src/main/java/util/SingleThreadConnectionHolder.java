package util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 确保一个线程只有一个Connection
 */
public class SingleThreadConnectionHolder {
	private static ThreadLocal<ConnectionHolder> localConnectionHolder = new ThreadLocal<ConnectionHolder>();
	
	public static Connection getConnection(DataSource dataSource) throws SQLException {
		return getConnectionHolder().getConnection(dataSource);
	}
	//释放连接
	public static void removeConnection(DataSource dataSource) {
		getConnectionHolder().removeConnection(dataSource);
	}
	//建立连接
	private static ConnectionHolder getConnectionHolder() {
		ConnectionHolder connectionHolder = localConnectionHolder.get();
		if (connectionHolder == null) {
			connectionHolder = new ConnectionHolder();
			localConnectionHolder.set(connectionHolder);
		}
		return connectionHolder;
	}

}

