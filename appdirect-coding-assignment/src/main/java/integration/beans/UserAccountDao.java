package integration.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

import utility.ConnectionConfiguration;

/**
 * @author akanksha
 *
 */
public class UserAccountDao {

    private static Logger logger = Logger.getLogger(UserAccountDao.class.getName());

	/**
	 * @param uuid
	 * @return
	 * @throws Exception 
	 */
	public UserAccount getUserAccountByUUID(String uuid) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
 
        try {
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM useraccount WHERE uuid = ?");
            preparedStatement.setString(1, uuid);
            resultSet = preparedStatement.executeQuery();
 
            while (resultSet.next()) {
                String accountIdentifier = resultSet.getString("accountIdentifier");
                String editionCode = resultSet.getString("editionCode");
                String pricingDuration = resultSet.getString("pricingDuration");
                return new UserAccount(accountIdentifier, uuid, new Order(editionCode, pricingDuration));
            }
 
        } catch (Exception e) {
        	logger.severe("Unable to fetch user account because of exception : " + e);
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
	 * @param accountIdentifier
	 * @return
	 * @throws Exception 
	 */
	public UserAccount getUserAccountByAccountIdentifier(String accountIdentifier) throws Exception {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
 
        try {
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM useraccount WHERE accountIdentifier = ?");
            preparedStatement.setString(1, accountIdentifier);
            resultSet = preparedStatement.executeQuery();
 
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                String editionCode = resultSet.getString("editionCode");
                String pricingDuration = resultSet.getString("pricingDuration");
                return new UserAccount(accountIdentifier, uuid, new Order(editionCode, pricingDuration));
            }
 
        } catch (Exception e) {
        	logger.severe("Unable to fetch user account because of exception : " + e);
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
	 * @param uuid
	 * @param subscription
	 * @return
	 * @throws Exception 
	 */
	public String createUserAccount(String uuid, Order subscription) throws Exception {
		String accountIdentifier = UUID.randomUUID().toString();
		Connection connection = null;
        PreparedStatement preparedStatement = null;
 
        try {
            connection = ConnectionConfiguration.getConnection();
            String sql = "INSERT INTO useraccount (accountIdentifier, uuid, editionCode, pricingDuration) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountIdentifier);
            preparedStatement.setString(2, uuid);
            preparedStatement.setString(3, subscription.getEditionCode());
            preparedStatement.setString(4, subscription.getPricingDuration());
            preparedStatement.executeUpdate();
 
        } catch (Exception e) {
        	logger.severe("Unable to add user because of exception : " + e);
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
		return accountIdentifier;
	}
	
	/**
	 * @param accountIdentifier
	 * @param subscription
	 * @return
	 * @throws Exception 
	 */
	public boolean updateUserAccount(String accountIdentifier, Order subscription) throws Exception {
		UserAccount userAccount = getUserAccountByAccountIdentifier(accountIdentifier);
		if (userAccount != null) {
			Connection connection = null;
	        PreparedStatement preparedStatement = null;
	 
	        try {
	            connection = ConnectionConfiguration.getConnection();
	            String sql = "UPDATE useraccount SET " + "editionCode = ?, pricingDuration = ? WHERE accountIdentifier = ?";
	            preparedStatement = connection.prepareStatement(sql);
	            preparedStatement.setString(1, subscription.getEditionCode());
	            preparedStatement.setString(2, subscription.getPricingDuration());
	            preparedStatement.setString(3, accountIdentifier);
	            preparedStatement.executeUpdate();
	 
	        } catch (Exception e) {
	        	logger.severe("Unable to update user because of exception : " + e);
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
			
			
			return true;
		}
		return false;
	}
	
	/**
	 * @param accountIdentifier
	 * @return
	 */
	public boolean removeUserAccount(String accountIdentifier) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
 
        try {
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM useraccount WHERE accountIdentifier = ?");
            preparedStatement.setString(1, accountIdentifier);
            preparedStatement.executeUpdate();
 
        } catch (Exception e) {
        	logger.severe("Unable to remove user because of exception : " + e);
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