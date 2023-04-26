import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class SignIn {
public static ResultSet  singIn() throws SQLException, ClassNotFoundException {
    Scanner sc = new Scanner(System.in);
    Connection conn = DBConnection.geConnection();
    String email;
    String password;
    System.out.println("*********************************************Log In Page*************************************************                                                                   ");
do {

    System.out.println("_________________________________________________________________________________________________________");
    System.out.printf("please enter your email                                                                                 |");
    System.out.println();
    System.out.printf("enter here: "); email = sc.next();
    System.out.println("_________________________________________________________________________________________________________");
    System.out.printf("%s please enter your password                                                                             %s","|","|");
    System.out.println();
    System.out.printf("enter here: ");  password = sc.next();
    if (Queries.SearchForUsersQuery(conn, new Users(email, password)) == null) {
        System.out.println("account or passowrd is invalid or not exist");
        userServicesAndValidationInputs.GoBackOrConintueOption();
    }

}while(Queries.SearchForUsersQuery(conn,new Users(email,password))== null);
    Users user = new Users(email,password);
    ResultSet rs = Queries.SearchForUsersQuery(conn,user);
    System.out.println("login succssfully");
    return rs;



}


}
