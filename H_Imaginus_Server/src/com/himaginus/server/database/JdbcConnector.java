package com.himaginus.server.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.himaginus.server.configuration.ServerConfig;

public class JdbcConnector {
	static private DataSource poolableDataSource;
	static private GenericObjectPool<Object> connectionPool;
	static {
		try {
			Class.forName(ServerConfig.dbDriver).newInstance();
		} catch (Exception e) {
			System.out.println("DataSource Initialize Fail");
		}
		//
		// Creates an instance of GenericObjectPool that holds our
		// pool of connections object.
		//
		connectionPool = new GenericObjectPool<>();
		connectionPool.setMaxActive(50);
		connectionPool.setMaxIdle(40);
		connectionPool.setMinIdle(10);
//		connectionPool.setMaxWait(5000L);
		connectionPool.setTestOnBorrow(true);
		connectionPool.setTimeBetweenEvictionRunsMillis(1000L);

		//
		// Creates a connection factory object which will be use by
		// the pool to create the connection object. We passes the
		// JDBC url info, username and password.
		//
		ConnectionFactory cf = new DriverManagerConnectionFactory(ServerConfig.dbHost, ServerConfig.dbUser,
				ServerConfig.dbPassword);
		
		//
		// Creates a PoolableConnectionFactory that will wraps the
		// connection object created by the ConnectionFactory to add
		// object pooling functionality.

		new PoolableConnectionFactory(cf, connectionPool, null, "select 1", false, true);
		poolableDataSource = new PoolingDataSource(connectionPool);

	}
	
	static public Connection getConnection() throws SQLException {
		return getConnection(true);
	}

	static public Connection getConnection(boolean isAutoCommit) throws SQLException {
		Connection con = poolableDataSource.getConnection();
		con.setAutoCommit(isAutoCommit);
		return con;
	}
	
}