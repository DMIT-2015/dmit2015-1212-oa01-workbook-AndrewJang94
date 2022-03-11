package common.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.enterprise.context.ApplicationScoped;

@DataSourceDefinitions({

	@DataSourceDefinition(
			name="java:app/datasources/mssqlDS",
			className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
			url="jdbc:sqlserver://DMIT-Capstone1.ad.sast.ca;databaseName=DMIT2015_1212_A01_yjang6;TrustServerCertificate=true",
			user="yjang6",
			password="RemotePassword200467938"),

})

@ApplicationScoped
public class ApplicationConfig {

}