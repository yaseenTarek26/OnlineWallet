import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Queries{
    public static ResultSet insertUserAccountQuery(Connection conn , Users user) throws SQLException {
            int id = userServicesAndValidationInputs.generateUserId();
            int sercuritycode = userServicesAndValidationInputs.generateUserId();
            String usersTablequery = "insert into usersTable values(?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(usersTablequery);
            stmt.setInt(1, id);
            stmt.setString(2,user.getNationalUserId());
            stmt.setString(3, user.getUserName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, 0);
            stmt.setDate(7, userServicesAndValidationInputs.getCurrentDate());
            stmt.executeUpdate();
            String personalUserData = "insert into personalUserData values(?,?,?,?)";
            PreparedStatement stmt2 = conn.prepareStatement(personalUserData);
            stmt2.setInt(1, id);
            stmt2.setString(2, user.getPhone());
            stmt2.setInt(3, user.getAge());
            stmt2.setInt(4, sercuritycode);//Security code
            System.out.println("your security code is "+sercuritycode+", please remember him because any operation you want to do will operate by it.");
            stmt2.executeUpdate();
            System.out.println("data inserted succssfully");
      return  SearchForUsersQuery(conn,user);
    }
    public static ResultSet SearchForUsersQuery(Connection conn, Users user){
        String query = "select * from usersTable join personalUserData using(userId) where usersTable.userEmail =? and usersTable.userPassword =?";
        ResultSet rs = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,user.getEmail());
            stmt.setString(2,user.getPassword());
             rs = stmt.executeQuery();
            if(rs.next()){
               return rs;
            }else{
                return null;
            }
        } catch (SQLException e) {
            System.out.println("something went wrong please try again later");
        }
        return rs;
    }
public static ResultSet showAcitivityQuery(Connection conn , int userId ) throws SQLException {
        String query = "select acitivityType, to_Char(timeOfActivity),AmountOfMoney, nvl(sender,'no one'), reciver from userActivities where userId =? or reciver =? order by timeOfActivity desc";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1,userId);
        stmt.setString(2, getEmailByUserIdQuery(conn,userId));
        ResultSet rs = stmt.executeQuery();
        return rs;
}
public static boolean DepositQuery(Connection conn, int userId , int depositNumber) throws SQLException {
        String query = "update usersTable set userbalance=userbalance+? where userId =?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1 ,depositNumber);
        stmt.setInt(2,userId);
        stmt.executeUpdate();
    String query2 = "insert into userActivities values(?,?,?,?,?,?)";
    PreparedStatement stmt2 = conn.prepareStatement(query2);
stmt2.setInt(1,userId);
stmt2.setString(2,"Deposit");
stmt2.setTimestamp(3, userServicesAndValidationInputs.getCurrentTimeStamp());
stmt2.setInt(4,depositNumber);
stmt2.setString(5,"-------------");
stmt2.setString(6,"-------------");
stmt2.executeUpdate();
    return true;
}
public static boolean DepositQueryToAnotherUser(Connection conn , int userId , int depositNumber, String userAcocunt) throws SQLException {
        String query = "update usersTable set userBalance = userBalance +? where userEmail =?";
    String query2 = "insert into userActivities values(?,?,?,?,?,?)";
    if(getEmailByUserIdQuery(conn,userId).equals(userAcocunt)){
        return false;
    }
    int userbalance = getBalanceQuery(conn,userId);
    if(depositNumber>userbalance)return false;
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1,depositNumber);
        stmt.setString(2,userAcocunt);
        stmt.executeUpdate();
    PreparedStatement stmt2 = conn.prepareStatement(query2);
    stmt2.setInt(1,userId);
    stmt2.setString(2,"wirthdraw");
    stmt2.setTimestamp(3,userServicesAndValidationInputs.getCurrentTimeStamp());
    stmt2.setInt(4,depositNumber);
    stmt2.setString(5, getEmailByUserIdQuery(conn,userId));
    stmt2.setString(6,userAcocunt);
   return stmt2.execute();
}
public static int getBalanceQuery(Connection conn , int userId) throws SQLException {
        String query = "select userbalance from userstable where userId =?";
    PreparedStatement stmt = conn.prepareStatement(query);
    stmt.setInt(1,userId);
   ResultSet rs=   stmt.executeQuery();
   rs.next();
    return rs.getInt(1);

}
public static boolean withDrawQuery(Connection conn , int userId, int ammountOfMoney) throws SQLException {
      int balance=  getBalanceQuery(conn,userId);
      if(getBalanceQuery(conn,userId)<ammountOfMoney){
          return false;
      }
      if(balance<ammountOfMoney){
          return false;
      }else {
          String query = "update usersTable set userBalance = userBalance -? where userId =?";
          PreparedStatement stmt = conn.prepareStatement(query);
          stmt.setInt(1,ammountOfMoney);
          stmt.setInt(2,userId);
          stmt.executeUpdate();
          String query2 = "insert into userActivities values(?,?,?,?,?,?)";
          PreparedStatement stmt2 = conn.prepareStatement(query2);
          stmt2.setInt(1,userId);
          stmt2.setString(2,"withdraw");
          stmt2.setTimestamp(3, userServicesAndValidationInputs.getCurrentTimeStamp());
          stmt2.setInt(4,ammountOfMoney);
          stmt2.setString(5,"-------------");
          stmt2.setString(6,"-------------");
        return  stmt2.execute();
      }
}
public static boolean isEmailExistQuery(Connection conn , String userEmail) throws SQLException {
        String query = "select userName from usersTable where userEmail =?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1,userEmail);
       ResultSet rs=  stmt.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
}
public static String getEmailByUserIdQuery(Connection conn, int userId) throws SQLException {
        String query = "select userEmail from usersTable where userId =?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1,userId);
        ResultSet rs= stmt.executeQuery();
        rs.next();
        return rs.getString(1);
}
public static boolean updateUserNameQuery(Connection conn ,int userId, String userName) throws SQLException {
        String query = "update usersTable set userName =? where userId = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1,userName);
        stmt.setInt(2,userId);
        stmt.executeUpdate();
        return true;
}

public static boolean updateUserPasswordQuery(Connection conn ,int userId , String password) throws SQLException {
        String query = "update usersTable set userPassword =? where userId =?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1,password);
        stmt.setInt(2,userId);
        stmt.executeUpdate();
        return true;
}
public static boolean updateUserEmailQuery(Connection conn , int userId , String UserEmail) throws SQLException {
        String query = "update usersTable set userEmail =? where userId= ?";
    PreparedStatement stmt = conn.prepareStatement(query);
    stmt.setString(1,UserEmail);
    stmt.setInt(2,userId);
    stmt.executeUpdate();
    return true;
}
public static boolean updateUserPhoneQuery(Connection conn , int userId , String phone) throws SQLException {
    String query = "update usersTable set userPhone =? where userId= ?";
    PreparedStatement stmt = conn.prepareStatement(query);
    stmt.setString(1, phone);
    stmt.setInt(2, userId);
    stmt.executeUpdate();
    return true;
}
public static String getCurrentPasswordByUserId(Connection conn ,int userId){
        String query = "select userPassword from usersTable where userId =?";
    try {
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1,userId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getString(1);
    } catch (SQLException e) {
        return null;
    }
}
public static String getNameByuserId(Connection conn ,int userId){
        String query = "select userName from usersTable where userId = ?";
    try {
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1,userId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getString(1);
    } catch (SQLException e) {
        return null;
    }

}
 public static String getUserPassowrdByUserId(Connection conn ,int userId){
        String query = "select userPassword from usersTable where userId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            return null;
        }

    }

    public static String getUserEmailbyUserId(Connection conn ,int userId){
        String query = "select userEmail from usersTable where userId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            return null;
        }
    }
public static int getNationalUserIdByUserId(Connection conn ,int userId){
    String query = "select nationalUserId from usersTable where userId = ?";
    try {
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1,userId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    } catch (SQLException e) {
        return -1;
    }

}
    public static int getUserAgeByUserId(Connection conn ,int userId){
        String query = "select userAge from personalUserData where userId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            return -1;
        }
    }

    public static String getUserPhoneByUserId(Connection conn ,int userId){
        String query = "select userPhone from personalUserData where userId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            return null;
        }
    }

}
