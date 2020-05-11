package com.nullok.core.config;


import com.nullok.exception.BaseException;
import com.nullok.exception.ConfigException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * @author Pushy
 * @since 2019/3/8 8:52
 */
public class PropertiesConfigReader {

    private static final int DEFAULT_PORT = 8080;
    private static final String DEFAULT_HOST = "127.0.0.1";

    private static final String PROPERTIES_FILENAME = "spring-han.properties";
    private static final String SERVER_HOST_KEY = "server.host";
    private static final String SERVER_PORT_KEY = "server.port";
    private static final String SERVER_DEFAULT_PATH_KEY = "server.default-path";
    private static final String DATASOURCE_URL = "datasource.url";
    private static final String DATASOURCE_USERNAME = "datasource.name";
    private static final String DATASOURCE_PASSWORD = "datasource.password";
    private static final String MAPPER_PACKAGE = "mybatis.dao.package";
    private static final String MAPPER_LOCATION = "mybatis.mapper.location";
    private static final String MONGO_DATABASE_NAME = "mongo.database.name";

    private int port;
    private String host;
    private String defaultPath;
    private String basePackage;
    // 数据源配置参数
    private String datasourceUrl;
    private String datasourceUsername;
    private String datasourcePassword;

    private String mapperPackage;
    private String mapperLocation;

    private String mongoDatabaseName;

    private Properties getProperties(Class<?> primarySource, String fileName) throws IOException {
        Resource resource = new UrlResource(Objects.requireNonNull(primarySource.getClassLoader().getResource(fileName)));
        Properties properties = new Properties();
        InputStream inputStream = resource.getInputStream();
        try {
            properties.load(inputStream);
            return properties;
        } catch (FileNotFoundException e) {
            throw new ConfigException("配置文件未找到！");
        }
    }
    public void read(Class<?> primarySource) throws IOException {
        Properties config = getProperties(primarySource, PROPERTIES_FILENAME);
        basePackage = primarySource.getPackage().getName();

        if (config.getProperty(SERVER_PORT_KEY) != null) {
            port = Integer.parseInt(config.getProperty(SERVER_PORT_KEY));
        } else {
            port = DEFAULT_PORT;
        }
        host = config.getProperty(SERVER_HOST_KEY, DEFAULT_HOST);
        defaultPath = config.getProperty(SERVER_DEFAULT_PATH_KEY, "");

        // 获取数据源
        datasourceUrl = config.getProperty(DATASOURCE_URL);
        datasourceUsername = config.getProperty(DATASOURCE_USERNAME);
        datasourcePassword = config.getProperty(DATASOURCE_PASSWORD);

        mapperPackage = config.getProperty(MAPPER_PACKAGE, basePackage);
        mapperLocation = config.getProperty(MAPPER_LOCATION, "classpath:dao/*.xml");

        mongoDatabaseName = config.getProperty(MONGO_DATABASE_NAME);
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public String getDatasourceUrl() {
        return datasourceUrl;
    }

    public String getDatasourceUsername() {
        return datasourceUsername;
    }

    public String getDatasourcePassword() {
        return datasourcePassword;
    }

    public String getMapperPackage() {
        return mapperPackage;
    }

    public String getMapperLocation() {
        return mapperLocation;
    }

    public String getMongoDatabaseName() {
        return mongoDatabaseName;
    }
}