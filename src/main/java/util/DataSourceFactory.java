package util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

/**
 * 创建数据源
 */
public class DataSourceFactory {
	public static DataSource dataSource = null;
//	public static DataSource dataSource_pluDB = null;
//	public static DataSource dataSource_dmDB_factory = null;
	static {
		// 数据源只能被创建一次
		dataSource = new ComboPooledDataSource();
//		dataSource_pluDB = new ComboPooledDataSource("CashMachine_pluDB");
//		dataSource_dmDB_factory = new ComboPooledDataSource("CashMachine_dmDB_factory");
	}

	public static DataSource createDataSource() {
		return dataSource;
	}
//	public static DataSource createDataSource_pluDB() {
//		return dataSource_pluDB;
//	}
//	public static DataSource createDataSource_dmDB_factory() {
//		return dataSource_dmDB_factory;
//	}

}
