package geovis.services.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataSource {
	
	private static String url;
	
	private static String user;
	
	private static String password;
	
    private DataSource() {}
    
    @Value("${Url}")
	public void setUrl(String Host) {
    	this.url = Host;
	}
    @Value("${PgUsername}")
	public void setUser(String Name) {
    	this.user = Name;
	}
    @Value("${Password}")
	public void setPassword(String Password) {
    	this.password = Password;
	}

	private static volatile HikariDataSource instance;

    
    public static HikariDataSource getInstance(){
		if(instance!=null)
			return instance;
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(url);
		config.setUsername(user);
		config.setDriverClassName("org.postgresql.Driver");
		config.setPassword(password);
		config.setMaximumPoolSize(50);
		config.setMaxLifetime(5000);
		config.setIdleTimeout(4000);
		if(instance==null)
			synchronized (DataSource.class){
				if(instance == null)
					instance = new HikariDataSource(config);
				return instance;
			}
		return instance;
    } 
    



}
