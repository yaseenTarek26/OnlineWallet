import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class userServicesAndValidationInputs {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static int generateUserId() {
        return (int) (Math.random() * 1000000000);
    }

    public static Timestamp getCurrentTimeStamp() {
        long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        return timestamp;
    }
    public static Date getCurrentDate() {
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    public static boolean checkPassword(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public static boolean checkEmail(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                if (i <= 4) {
                    return false;
                }
            }
        }
        return matcher.matches();
    }

    public static boolean checkAge(int age) {
        if (age > 100 || age <= 22) {
            return false;
        }
        return true;
    }

    public static boolean checkUserName(String userName) {
        if (!Character.isAlphabetic(userName.charAt(0)) || userName.length() < 8 || userName.length() > 30)
            return false;
        for (char c : userName.toCharArray()) {
            if (!Character.isLetterOrDigit(c))
                return false;
        }
        return true;
    }
    public static boolean checkUserPhone(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException ex) {
            return false;
        }
        if (number.length() != 11) {
            return false;
        }
        String prefix = number.substring(0, 3);
        return prefix.equals("015") || prefix.equals("010") || prefix.equals("011") || prefix.equals("012");
    }

    public static void GoBackOrConintueOption() {
        Scanner sc = new Scanner(System.in);
        boolean con = true;
        do {
            System.out.println("_________________________________________________________________________________________________________");
            System.out.println("press 1 to go back, press 2 continue to the operation");
            String choose;
            do {
                choose = sc.next();
                if (!isNumber(choose)) System.out.println("invalid input");
            } while (!(isNumber(choose)));
            switch (Integer.parseInt(choose)) {
                case 1:
                    new ProgramManager();
                case 2:
                    con = false;
                    break;
                default:
                    System.out.println("Invalid input");
            }
        } while (con);
    }

    public static boolean isNumber(String number) {
        return number.matches("[0-9]+");
    }

    public static boolean updateUserName(Connection conn, Scanner sc , int userId) {
      String userName;
        do{
            userName = sc.next();
            if(!checkUserName(userName)) System.out.println("you can't user this name");
        }while(!checkUserName(userName));
        try {
            Queries.updateUserNameQuery(conn,userId,userName);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    public static boolean updateUserPhone(Connection conn , Scanner sc , int userId){
        String userPhone;
        do{
            userPhone = sc.next();
            if(!checkUserPhone(userPhone)) System.out.println("this number is incorrect.");
        }while(!checkUserPhone(userPhone));
        try {
            Queries.updateUserPhoneQuery(conn ,userId,userPhone);
        } catch (SQLException e) {
            return false;
        }
return true;
    }
public static boolean updateUserEmail(Connection conn , Scanner sc , int userId){
        String userEmail;
        do{
            System.out.println("enter your new email");
            userEmail = sc.next();
            if(!checkEmail(userEmail)) System.out.println("this can't be an email");
        }while(!checkEmail(userEmail));
    try {
        Queries.updateUserEmailQuery(conn,userId,userEmail);
    } catch (SQLException e) {
        return false;
    }
    System.out.println("your email is changed");
    return true;
}
public static boolean checkNationalUserId(String nationalUserId){
        if(nationalUserId.length()==14&& isNumber(nationalUserId))return true;
        else return false;}

public static boolean updateUserPassword(Connection conn, Scanner sc ,int userId){
//method used to update user password return false if the operation is.
// canceled and return true if the operation is done sucssfully.
 userPrintMassages message = new userPrintMassages();
String userCurrentPassword;
String userNewPassowrd;
String userConfiramtionOperation;
    System.out.println("please enter your current password, want to cancel press e");
        do {
            userCurrentPassword = sc.next();
            if(userCurrentPassword.equals("e"))return false;
            if(!isUserPasswordEqualSystemPassword(userCurrentPassword, userId)) System.out.println("you enter wrong password.");
        } while (!isUserPasswordEqualSystemPassword(userCurrentPassword, userId));
    System.out.println("now enter your new password.");
       do{
        userNewPassowrd = sc.next();
           if(userNewPassowrd.equals("e"))return false;
        if(!userServicesAndValidationInputs.checkPassword(userNewPassowrd))message.passwordEnCorrectMessage();
    }while(!userServicesAndValidationInputs.checkPassword(userNewPassowrd));
       message.UserAcceptToChangePassowrd();
    System.out.print("enter here");
    do{
        userConfiramtionOperation = sc.next();
       if(userConfiramtionOperation.equals("n")){return false;}
    }while(!userConfiramtionOperation.equals("y"));

        try {
            System.out.println("operation complete");
            return Queries.updateUserPasswordQuery(conn, userId, userNewPassowrd);
        } catch (SQLException e) {
            return false;
        }

}
private static boolean isUserPasswordEqualSystemPassword( String userEnteredPassword,int userId){
    try {
        return Queries.getCurrentPasswordByUserId(DBConnection.geConnection(),userId).equals(userEnteredPassword);
    } catch (ClassNotFoundException e) {
       return false;
    } catch (SQLException e) {
    return false;
    }
}

}
