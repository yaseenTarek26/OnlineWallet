package UserSignUp;

import DataBaseQueryAndConnection.DBConnection;
import DataBaseQueryAndConnection.Queries;
import Modules.Users;
import UserServices.userServicesAndValidationInputs;
import UserSignIn.SignInForArabic;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class SignUpArabicSub {
    public static Users SignUpArabic(){
        try {
            return (Users) SignInForArabic.singIn();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }




}

