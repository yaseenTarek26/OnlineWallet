import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
public class ProgramManager {
    public ProgramManager(){
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
        System.out.println("                                    Welcome User in Yaseen application                                                ");
        boolean wantToExit = true;
        while (wantToExit)
        do {
            System.out.println("_________________________________________________________________________________________________________");
            System.out.printf("%s                       press 1 login , press 2 sign up , press 3 exit program                           %s","|","|");
            Scanner sc = new Scanner(System.in);
            String choose;
            do {
                System.out.println();
            System.out.printf("|                                                                                                        |" );
                System.out.println();
                System.out.printf("                                             choose: ");
                choose = sc.next();

                 if(!userServicesAndValidationInputs.isNumber(choose)) {
                     System.out.println("_________________________________________________________________________________________________________");
                     System.out.printf("%s                       press 1 login , press 2 sign up , press 3 exit program                           %s","|","|");
                     System.out.println();
                     System.out.println("_________________________________________________________________________________________________________");
                     System.out.println("|                                                                                                        |");
                     System.out.println("invalid input try again.");
                 }
            }while(!userServicesAndValidationInputs.isNumber(choose));
            switch (Integer.parseInt(choose)) {
                case 1:
                    System.out.println("_________________________________________________________________________________________________________");
                UserPage.userPage(SignIn.singIn());
                    break;
                case 2:
                       SignUp.signUp();
                    System.out.println("please sign in");
                    UserPage.userPage(SignIn.singIn());
                    break;
                case 3:
                    System.exit(0);
                    wantToExit = false;
                    break;
                default:
                    System.out.println("invalid intput");
                    System.out.println("press 1 login , press 2 sign up , press 3 exit program");
            }
        } while (wantToExit);

    }
    }
