package by.khomenko.nsp.webcat.common.dao.mysql;

import by.khomenko.nsp.webcat.common.dao.CategoryDao;
import by.khomenko.nsp.webcat.common.entity.Category;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl extends BaseDaoImpl<Category> implements CategoryDao {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CategoryDaoImpl.class);


    public CategoryDaoImpl() throws PersistentException {
    }


    @Override
    public Integer create(Category category) throws PersistentException {
        String sql = "INSERT INTO category (category_name, category_description"
                + "photo_path, in_stock, seo_attributes)"
                + " VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(2, category.getCategoryName());
            statement.setString(3, category.getCategoryDescription());
            statement.setString(4, category.getPhotoPath());
            statement.setString(5, category.getInStock());
            statement.setString(6, category.getSeoAttributes());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    LOGGER.error("There is no autoincremented index after"
                            + " trying to add record into table `category`");
                    throw new PersistentException();
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Creating category an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public Category read(Integer identity) throws PersistentException {
        String sql = "SELECT category_name, category_description,"
                + " photo_path, in_stock, seo_attributes"
                + " FROM category WHERE category_id = ?";


        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, identity);

            Category category = null;
            if (resultSet.next()) {
                category = new Category();
                category.setCategoryId(identity);
                category.setCategoryName(resultSet.getString("category_name"));
                category.setCategoryDescription(resultSet.getString("category_description"));
                category.setPhotoPath(resultSet.getString("photo_path"));
                category.setInStock(resultSet.getString("in_stock"));
                category.setSeoAttributes(resultSet.getString("seo_attributes"));

            }
            return category;
        } catch (SQLException e) {
            LOGGER.error("Reading from table `category`"
                    + " an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    public List<Category> readAll() throws PersistentException{

        String sql = "SELECT * FROM category";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            List<Category> categoryList = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Category category
                            = new Category(resultSet.getInt("category_id"),
                            resultSet.getString("category_name"),
                            resultSet.getString("category_description"),
                            resultSet.getString("photo_path"),
                            resultSet.getString("in_stock"),
                            resultSet.getString("seo_attributes"));
                    categoryList.add(category);
                }
            }
            return categoryList;
        } catch (SQLException e) {
            LOGGER.error("Reading all categories an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void update(Category category) throws PersistentException {

        String sql = "UPDATE category SET category_name = ?,"
                + " category_description = ?,"
                + " photo_path = ?, in_stock = ?, seo_attributes = ?"
                + " WHERE category_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category.getCategoryName());
            statement.setString(2, category.getCategoryDescription());
            statement.setString(3, category.getPhotoPath());
            statement.setString(4, category.getInStock());
            statement.setString(5, category.getSeoAttributes());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Updating category an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {

        String sql = "DELETE FROM category WHERE product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, identity);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Deleting category an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }
}
