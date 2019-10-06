package by.khomenko.nsp.webcat.common.dao.mysql;

import by.khomenko.nsp.webcat.common.dao.ProductDao;
import by.khomenko.nsp.webcat.common.entity.Product;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ProductDaoImpl.class);


    public ProductDaoImpl() throws PersistentException {
    }

    @Override
    public Integer create(Product product) throws PersistentException {
        String sql = "INSERT INTO products (category_id, product_name, short_description, full_description"
                + " product_price, product_discount, in_stock, photo_path, seo_attributes, output_marker)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {


            if (product.getCategoryId() != null) {
                statement.setInt(1, product.getCategoryId());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getShortDescription());
            statement.setString(4, product.getFullDescription());
            statement.setDouble(5, product.getProductPrice());
            statement.setDouble(6, product.getProductDiscount());
            statement.setString(7, product.getInStock());
            statement.setString(8, product.getPhotoPath());
            statement.setString(9, product.getSeoAttributes());
            statement.setString(10, product.getOutputMarker());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    LOGGER.error("There is no autoincremented index after"
                            + " trying to add record into table `products`");
                    throw new PersistentException();
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Creating product an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public Product read(Integer identity) throws PersistentException {
        String sql = "SELECT category_id, product_name, short_description, full_description,"
                + " product_price, product_discount, in_stock, photo_path, seo_attributes"
                + " FROM products WHERE product_id = ?";


        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, identity);

            Product product = null;
            if (resultSet.next()) {
                product = new Product();
                product.setProductId(identity);
                product.setCategoryId(resultSet.getInt("category_id"));
                product.setProductName(resultSet.getString("product_name"));
                product.setShortDescription(resultSet.getString("short_description"));
                product.setFullDescription(resultSet.getString("full_description"));
                product.setProductPrice(resultSet.getDouble("product_price"));
                product.setProductDiscount(resultSet.getDouble("product_discount"));
                product.setInStock(resultSet.getString("in_stock"));
                product.setPhotoPath(resultSet.getString("photo_path"));
                product.setSeoAttributes(resultSet.getString("seo_attributes"));
                product.setOutputMarker(resultSet.getString("output_marker"));
            }
            return product;
        } catch (SQLException e) {
            LOGGER.error("Reading from table `products`"
                    + " an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    public List<Product> readAll() throws PersistentException{

        String sql = "SELECT * FROM products";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            List<Product> productsList = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Product product
                            = new Product(resultSet.getInt("product_id"),
                            resultSet.getInt("category_id"),
                            resultSet.getString("product_name"),
                            resultSet.getString("short_description"),
                            resultSet.getString("full_description"),
                            resultSet.getDouble("product_price"),
                            resultSet.getDouble("product_discount"),
                            resultSet.getString("in_stock"),
                            resultSet.getString("photo_path"),
                            resultSet.getString("seo_attributes"),
                            resultSet.getString("output_marker"));
                    productsList.add(product);
                }
            }
            return productsList;
        } catch (SQLException e) {
            LOGGER.error("Reading all products an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void update(Product product) throws PersistentException {

        String sql = "UPDATE products SET category_id = ?, product_name = ?,"
                + " short_description = ?, full_description = ?, product_price = ?, product_discount = ?,"
                + " in_stock = ?, photo_path = ?, seo_attributes = ?, output_marker = ?"
                + " WHERE product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, product.getCategoryId());
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getShortDescription());
            statement.setString(4, product.getFullDescription());
            statement.setDouble(5, product.getProductPrice());
            statement.setDouble(6, product.getProductDiscount());
            statement.setString(7, product.getInStock());
            statement.setString(8, product.getPhotoPath());
            statement.setString(9, product.getSeoAttributes());
            statement.setString(10, product.getOutputMarker());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Updating product an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {

        String sql = "DELETE FROM products WHERE product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, identity);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Deleting product an exception occurred. ", e);
            throw new PersistentException(e);
        }

    }

}
