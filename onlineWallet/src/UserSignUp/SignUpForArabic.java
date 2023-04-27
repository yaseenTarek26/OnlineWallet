package UserSignUp;

import DataBaseQueryAndConnection.DBConnection;
import DataBaseQueryAndConnection.Queries;
import Modules.Users;
import UserServices.userServicesAndValidationInputs;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class SignUpForArabic {
    public static Users SignUpForArabic() throws ParseException {
        System.out.println("_________________________________________________________________________________________________________");
        System.out.println("Welcome user.");
        Scanner sc = new Scanner(System.in);
        String name;
        String password;
        String email;
        String phone;
        String age;
        String nationalUserId;
        do {
            System.out.println("Enter your national user id");
            System.out.print("enter here: ");
            nationalUserId = sc.next();
            if (!userServicesAndValidationInputs.checkNationalUserId(nationalUserId))
                System.out.println("invalid input");

        } while (!userServicesAndValidationInputs.checkNationalUserId(nationalUserId));
        do {
            System.out.println("please enter your name:");
            System.out.print("enter here:");
            name = sc.next();
            System.out.println("_________________________________________________________________________________________________________");
            if (userServicesAndValidationInputs.checkUserName(name)) {
                System.out.println("Please enter your name correctly name must contains more than 6 characters");
            }
        } while (userServicesAndValidationInputs.checkUserName(name));
        do {
            System.out.println("please enter your email");
            System.out.print("enter here:");
            email = sc.next();
            System.out.println("_________________________________________________________________________________________________________");
            if (userServicesAndValidationInputs.checkEmail(email) == false) {
                System.out.println("please enter your email correctly");
            }
        } while (!userServicesAndValidationInputs.checkEmail(email));
        do {
            System.out.println("plesse enter your password");
            System.out.print("enter here:");
            password = sc.next();
            System.out.println("_________________________________________________________________________________________________________");
            if (userServicesAndValidationInputs.checkPassword(password) == false) {
                System.out.println("please enter your password correctly");
                System.out.println("password must be more than 8 character and must contains Capital and number and one sympol");
            }

        } while (!userServicesAndValidationInputs.checkPassword(password));
        do {
            System.out.println("please enter your age");
            do {
                System.out.print("enter here:");
                age = sc.next();
                if (!userServicesAndValidationInputs.isNumber(age)) System.out.println("can't enter characters to age");
            } while (!userServicesAndValidationInputs.isNumber(age));
            System.out.println("_________________________________________________________________________________________________________");
            if (userServicesAndValidationInputs.checkAge(Integer.parseInt(age)) == false) {
                System.out.println("please enter your age correctly");
                System.out.println("age must be more than 23");
            }
        } while (!userServicesAndValidationInputs.checkAge(Integer.parseInt(age)));
        do {
            System.out.println("please enter your phone");
            System.out.print("enter here:");
            phone = sc.next();
            System.out.println("_________________________________________________________________________________________________________");
            if (!userServicesAndValidationInputs.checkUserPhone(phone)) {
                System.out.println("please enter your phone correctly");
                System.out.println("number must contains 011 , 012 or 010 at the first of the phone number and contains in total 11 numbers");
            }

        } while (!userServicesAndValidationInputs.checkUserPhone(phone));
        Users user;
        user = new Users();
        user.setUserName(name);
        user.setAge(Integer.parseInt(age));
        user.setEmail(email);
        user.setSecurityCode(userServicesAndValidationInputs.generateUserId());
        user.setPassword(password);
        user.setPhone(phone);
        user.setNationalUserId(nationalUserId);
        try {
            Queries.insertUserAccountQuery(DBConnection.geConnection(), user);
            System.out.println("______________________");
            System.out.println("|signed up succssfully|");
            System.out.println("______________________");
        } catch (ClassNotFoundException ex) {
            System.out.println("something went wrong");
        } catch (SQLException ex) {
            System.out.println("something went wrong please try again later");
        }

        return user;
    }

}
