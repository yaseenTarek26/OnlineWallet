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
class SignInForArabic {
    public static ResultSet singIn() throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        Connection conn = DBConnection.geConnection();
        String email;
        String password;
        System.out.println("                       *********************************************صفحة تسجيل الدخول*************************************            ");
        do {

            System.out.println("_________________________________________________________________________________________________________");
            System.out.printf("ادخل عنوان البريد الاليكتروني الخاص بك:                                                                                 |");
            System.out.println();
            System.out.printf("enter here: "); email = sc.next();
            System.out.println("_________________________________________________________________________________________________________");
            System.out.printf("%sادخل كلمة المرورالخاصه بك                                                                             %s","|","|");
            System.out.println();
            System.out.printf("ادخل هنا: ");  password = sc.next();
            if (Queries.SearchForUsersQuery(conn, new Users(email, password)) == null) {
                System.out.println("هذا الحساب غير موجود");
                userServicesAndValidationInputs.GoBackOrConintueOption();
            }

        }while(Queries.SearchForUsersQuery(conn,new Users(email,password))== null);
        Users user = new Users(email,password);
        ResultSet rs = Queries.SearchForUsersQuery(conn,user);
        System.out.println("تم التسجيل بنجاح");
        return rs;



    }


}
