package sk.ness.academy.config;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DatabaseConfig {
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {
    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan(new String[] { "sk.ness.academy.domain" });
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    em.setJpaProperties(additionalProperties());

    return em;
  }

  final Properties additionalProperties() {
    final Properties properties = new Properties();
    properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");
    properties.setProperty(Environment.DRIVER, "org.hsqldb.jdbcDriver");
    properties.setProperty(Environment.POOL_SIZE, "1");
    properties.setProperty(Environment.HBM2DDL_AUTO, "update");
    properties.setProperty(Environment.SHOW_SQL, Boolean.TRUE.toString());
    properties.setProperty(Environment.FORMAT_SQL, Boolean.TRUE.toString());

    return properties;
  }

  @Bean(name = "dataSource")
  public DataSource dataSource() {
    return new SimpleDriverDataSource(new JDBCDriver(), "jdbc:hsqldb:file:mydb;shutdown=true", "sa", "");
  }

}
