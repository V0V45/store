package com.electric.store.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.electric.store.model.Product;

@Component
public class ProductDaoImpl implements ProductDao {
    private DataSource dataSource;

    @Autowired
    public ProductDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        final String selectSql = "SELECT name, price, product_id FROM products";
        List<Product> products = new ArrayList<>();
        try (
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
        ) {
            while (rs.next()) {
                final Product product = new Product(rs.getString(1), rs.getFloat(2), rs.getString(3));
                products.add(product);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return products;
    }

    @Override
    public Product save(Product product) {
        String insertSql = "INSERT INTO products (name, price) VALUES (?, ?)";
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setFloat(2, (float) product.getPrice());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                product.setId(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public Product getById(String productId) {
        String getByIdSql = "SELECT name, price, product_id FROM products WHERE product_id = ?";
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getByIdSql, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setInt(1, Integer.parseInt(productId));

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (!rs.next()) {
                    throw new RuntimeException(String.format("Product with id %s was not found", productId));
                }
                return new Product(rs.getString(1), rs.getFloat(2), rs.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product update(Product product) {
        if (Objects.isNull(product.getId())) {
            throw new RuntimeException("cannot update unsaved book");
        }

        String updateSql = "UPDATE products SET name = ?, price = ? WHERE product_id = ?";
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
        ) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setFloat(2, (float) product.getPrice());
            preparedStatement.setString(3, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public void delete(Product product) {
        String deleteSql = "DELETE FROM products WHERE product_id = ?";
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
        ) {
            preparedStatement.setString(1, product.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        String deleteAllSql = "TRUNCATE TABLE products";
        try (
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(deleteAllSql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
