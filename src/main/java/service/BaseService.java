package service;

import util.DataSourceFactory;

import javax.sql.DataSource;


public class BaseService {
	protected final DataSource dataSource = DataSourceFactory.createDataSource();
//	protected final DataSource dataSource_pluDB = DataSourceFactory.createDataSource_pluDB();
//	protected final DataSource dataSource_dmDB_factory = DataSourceFactory.createDataSource_dmDB_factory();
}
