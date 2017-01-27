package product.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import utility.ConnectionConfiguration;

/**
 * @author akanksha
 *
 */
public class BookDao {
	private static Logger logger = Logger.getLogger(BookDao.class.getName());
	
	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Book getBookByName(String name) throws Exception {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
 
        try {
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM book WHERE name = ?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
 
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String author = resultSet.getString("author");
                return new Book(id, name, author);
            }
 
        } catch (Exception e) {
        	logger.severe("Unable to fetch Book because of exception : " + e);
            throw new Exception(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
		
		return null;
	}
	
	/**
	 * @param id
	 * @param name
	 * @param author
	 * @throws Exception
	 */
	public void addBook(int id, String name, String author) throws Exception {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
 
        try {
            connection = ConnectionConfiguration.getConnection();
            String sql = "INSERT INTO book (id, name, author) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, author);
            preparedStatement.executeUpdate();
 
        } catch (Exception e) {
        	logger.severe("Unable to add book because of exception : " + e);
            throw new Exception(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/**
	 * @param name
	 * @return
	 */
	public boolean removeBook(String name) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
 
        try {
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM book WHERE name = ?");
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
 
        } catch (Exception e) {
        	logger.severe("Unable to remove book because of exception : " + e);
            return false;
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
		return false;
	}
	
}
