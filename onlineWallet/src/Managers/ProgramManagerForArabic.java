package Managers;

import UserPageModules.UserPageForArabic;
import UserServices.userServicesAndValidationInputs;
import UserSignIn.SignInForArabic;
import UserSignUp.SignUpForArabic;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class ProgramManagerForArabic {
    public ProgramManagerForArabic() {
        try {
            StartProgram();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void StartProgram() throws SQLException, ParseException, ClassNotFoundException {
        do {
            System.out.println("_________________________________________________________________________________________________________");
            System.out.printf("%s                       تسجبل الدخو 1,انشأ حساب 2,الخروح من البرنامج 3                         %s", "|", "|");
            Scanner sc = new Scanner(System.in);
            String choose;
            do {
                System.out.println();
                System.out.printf("|                                                                                                        |");
                System.out.println();
                System.out.printf("                                             اختار: ");
                choose = sc.next();

                if (!userServicesAndValidationInputs.isNumber(choose)) {
                    System.out.println("_________________________________________________________________________________________________________");
                    System.out.printf("%s                      تسجبل الدخو 1,انشأ حساب 2,الخروح من البرنامج 3                             %s", "|", "|");
                    System.out.println();
                    System.out.println("_________________________________________________________________________________________________________");
                    System.out.println("|                                                                                                        |");
                    System.out.println("البينات غير صحيحه.");
                }
            } while (!userServicesAndValidationInputs.isNumber(choose));
            switch (Integer.parseInt(choose)) {
                case 1:
                    System.out.println("_________________________________________________________________________________________________________");
                    SignInForArabic.singIn();
                    break;
                case 2:

                    SignUpForArabic.SignUpForArabic();
                    System.out.println("الان فم بتسجبل الدخول");
                    UserPageForArabic.UserPageForArabic(SignInForArabic.singIn());
                    break;
                case 3:
                    System.exit(0);

                    break;
                default:
                    System.out.println("مدخلات غير صحيحه");
                    System.out.println("تسجبل الدخو 1,انشأ حساب 2,الخروح من البرنامج 3");
            }
        } while (true);

    }
}
