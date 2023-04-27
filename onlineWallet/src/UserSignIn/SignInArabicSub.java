package UserSignIn;

import DataBaseQueryAndConnection.DBConnection;
import DataBaseQueryAndConnection.Queries;
import Modules.Users;
import UserServices.userServicesAndValidationInputs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class SignInArabicSub {

   public  SignInArabicSub(ResultSet rs){
       try {
           SignInForArabic.singIn();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
       }


   }
}
