package com.electric.store;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.electric.store.model.Product;

@Configuration
@PropertySource("classpath:db.properties")
public class ApplicationContext {
    @Bean
    DataSource h2DataSource(@Value("${jdbcUrl}") String jdbcUrl) {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(jdbcUrl);
        dataSource.setUser("user");
        dataSource.setPassword("password");
        return dataSource;
    }

    @Bean
    CommandLineRunner cmd(DataSource dataSource) {
        return args -> {
            try (InputStream inputStream = this.getClass().getResourceAsStream("/initial.sql")) {
                String sql = new String(inputStream.readAllBytes());
                try (
                        Connection connection = dataSource.getConnection();
                        Statement statement = connection.createStatement();) {
                    statement.executeUpdate(sql);
                    try (PreparedStatement preparedStatement = connection.prepareStatement(
                            "INSERT INTO products (name, price) VALUES (?, ?)")) {
                        preparedStatement.setString(1, "Power Wire");
                        preparedStatement.setFloat(2, 333);
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException("SQL Exception happened");
                    }
                    System.out.println("Printing products from database...");
                    ResultSet rs = statement.executeQuery("SELECT name, price, product_id FROM products");
                    while (rs.next()) {
                        Product product = new Product(rs.getString(1), rs.getFloat(2), rs.getString(3));
                        System.out.println(product);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("SQL Exception happened");
                }
            } catch (Exception e) {
                throw new RuntimeException("Input stream exception happened");
            }
        };
    }
}
