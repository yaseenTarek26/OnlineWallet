package UserPageModules;

import DataBaseQueryAndConnection.DBConnection;
import DataBaseQueryAndConnection.Queries;
import Managers.ProgramManager;
import Managers.ProgramManagerForArabic;
import Modules.CommandLineTable;
import Modules.OptionGenerator;
import Modules.Users;
import UserServices.userServiceAndValidationInputsArabicSub;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserPageArabicSub {
    UserPageArabicSub(ResultSet userData){
        UserPageForArabic.UserPageForArabic(userData);
    }

}


