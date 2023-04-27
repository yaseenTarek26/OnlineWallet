package UserPageModules;

import DataBaseQueryAndConnection.DBConnection;
import DataBaseQueryAndConnection.Queries;
import Managers.ProgramManager;
import Modules.CommandLineTable;
import Modules.OptionGenerator;
import Modules.Users;
import UserServices.userServicesAndValidationInputs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class UserPage {
  private  UserPage(){}
public static void userPage(ResultSet userData){
    boolean wantToGoBack = false;
    Users user=null;
   Connection conn = openConnection();
   try {
        user = createUserData(userData);
   }catch (SQLException ex){
       System.out.println("something went wrong during geting your data please try again later");
       new ProgramManager();
   }
    do {
       printOptions();
    Scanner sc = new Scanner(System.in);
    String choose ;
    do{
        System.out.print("                                                 choose here: ");
        choose = sc.next();
        if(!userServicesAndValidationInputs.isNumber(choose)) System.out.println("invalid input");
    }while(!userServicesAndValidationInputs.isNumber(choose));
switch(Integer.parseInt(choose)){
    case 1:printUserData(conn,user,user.getSecurityCode());
    break;
    case 2: showActivity(conn,user.getUserId());
    break;
    case 3:
        try {
            sendMoneyToAnotherAccount(conn,sc,user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        break;
    case 4:deposit(conn,sc,user.getUserId());
    break;
    case 5:withDraw(conn,sc,user.getUserId());
    break;
    case 6:user = null;
        System.out.println("you are signed out.");
     new ProgramManager();
     break;
    case 7: Settings(conn,sc,user,userData);
    break;
    case 8: System.exit(0);
    default:
        System.out.println("invalid input");
}
}while(!wantToGoBack);
}
private  static boolean seeAccount(int RealSecurityCode){
    Scanner sc = new Scanner(System.in);
    String securityCode;
        System.out.println("Please enter your security code");
     do{
          securityCode= sc.next();
          if(!userServicesAndValidationInputs.isNumber(securityCode)) System.out.println("invalid input.");
     }while(!userServicesAndValidationInputs.isNumber(securityCode));
        if(Integer.parseInt(securityCode)!=RealSecurityCode){
            return false;
        }
    return true;
}
private static void printOptions(){
    System.out.println("________________________________________________________________________________________________________________________________________________");
    System.out.println("|press 1 see your account details ,2 show activity, 3 send money to another account , 4 deposit , 5 withdraw , 6 sign out, 7 settings  , 8 exit|");
    System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
}
private static Users createUserData(ResultSet rs) throws SQLException {
    Users user = new Users();
    user.setUserId(rs.getInt(1));
    user.setNationalUserId( rs.getString(2));
    user.setUserName(rs.getString(3));
    user.setEmail(rs.getString(4));
    user.setPassword(rs.getString(5));
    user.setBalance(rs.getInt(6));
    user.setDateOfCreateAccount(rs.getTimestamp(7));
    user.setPhone(rs.getString(8));
    user.setAge(rs.getInt(9));
    user.setSecurityCode(rs.getInt(10));
      return user;
}
private static void printUserData(Connection conn, Users user, int SercurityCode){
      if(seeAccount(SercurityCode)==true) {
          System.out.println("_________________________________________________________________________________________________________");
          System.out.printf("|this is your personal data check for it:                                                                %s","|" );
          System.out.println();
          System.out.printf("user Name : %s                                                                                          ", Queries.getNameByuserId(conn,user.getUserId()));
          System.out.println();
          System.out.printf("user nationalId : %s                                                                                          ",Queries.getNationalUserIdByUserId(conn,user.getUserId()));
          System.out.println();
          System.out.printf("user email : %s                                                                                        ",Queries.getUserEmailbyUserId(conn,user.getUserId()));
          System.out.println();
          System.out.printf("user passowrd: %s                                                                                       ",Queries.getUserPassowrdByUserId(conn,user.getUserId()));
          System.out.println();
          System.out.printf("user age : %s                                                                                            ",Queries.getUserAgeByUserId(conn,user.getUserId()));
          System.out.println();
          System.out.printf("user date of create account : %s                                                                         ",user.getDateOfCreateAccount());
          System.out.println();
          System.out.printf("user phone : %s                                                                                          ",Queries.getUserPhoneByUserId(conn,user.getUserId()));
          System.out.println();
          try {
              System.out.println("user balance : "+Queries.getBalanceQuery(conn,user.getUserId())+"$");
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }
          System.out.println("_________________________________________________________________________________________________________");
      }
}
private static void deposit(Connection conn,Scanner sc , int userId) {
    System.out.println("please enter the number you want to deposit if you want to cancle press e:");
    String depositNumber;
        do {
            depositNumber = sc.next();
            if(depositNumber.equals("e")){
                System.out.println("operation cancled");
                return;
            }
           if(!userServicesAndValidationInputs.isNumber(depositNumber))System.out.println("invalid input");
        }while(!userServicesAndValidationInputs.isNumber(depositNumber));
    try {
        Queries.DepositQuery(conn,userId,Integer.parseInt(depositNumber));
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    System.out.println("your money is deposit in your account.");
}
private static void showActivity(Connection conn,int userId)  {

    try {
        ResultSet rs = Queries.showAcitivityQuery(conn,userId);
        CommandLineTable generateTable = new CommandLineTable();
        generateTable.setHeaders("Process type","Time of process","ammount of money","sender","reciver");
        String userAccount = Queries.getEmailByUserIdQuery(conn,userId);
        while(rs.next()){
             generateTable.setShowVerticalLines(true);
             if(userServicesAndValidationInputs.checkEmail(rs.getString(4))&&!rs.getString(4).equals(userAccount)){
                 generateTable.addRow("recive", rs.getNString(2), Integer.toString(rs.getInt(3)), rs.getString(4), rs.getString(5));

             } else if(rs.getString(4).equals(userAccount)&&!rs.getString(5).equals("-------------"))
             {
                 generateTable.addRow("send", rs.getNString(2), Integer.toString(rs.getInt(3)), rs.getString(4), rs.getString(5));
             }else {
                 generateTable.addRow(rs.getString(1), rs.getNString(2), Integer.toString(rs.getInt(3)), rs.getString(4), rs.getString(5));
             }
        }
        generateTable.print();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
private static void withDraw(Connection conn ,Scanner sc,int userId)  {
    System.out.println("enter the money you want to withdraw if you want to cancle press e.");
      String ammountOfMoney;
      do{
          ammountOfMoney = sc.next();
          if(ammountOfMoney.equals("e")){
              System.out.println("operation canceled");
              return;
          }
          if(!userServicesAndValidationInputs.isNumber(ammountOfMoney)) System.out.println("invalid input.");
      }while(!userServicesAndValidationInputs.isNumber(ammountOfMoney));
    try {

      if(!Queries.withDrawQuery(conn,userId,Integer.parseInt(ammountOfMoney)))
      {
          System.out.println("money withdraw sucssfully.");
      }else{
          System.out.println("there is no money to withdraw this amount of money.");
      }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
private static void sendMoneyToAnotherAccount(Connection conn,Scanner sc,Users user) throws SQLException, ClassNotFoundException {
    System.out.println("enter the account you want to send the money to it if you want to cancle press e.");
    int userbalance =Queries.getBalanceQuery(conn,user.getUserId());
      String sendMoneyAccount;
      do {
          do {
              do {
                  sendMoneyAccount = sc.next();
                  if (sendMoneyAccount.equals("e")) {
                      System.out.println("operation canceled");
                      return;
                  }
                  if (user.getEmail().equals(sendMoneyAccount)) {
                      System.out.println("you can't send money to your account.");
                  }
                  if (!userServicesAndValidationInputs.checkEmail(sendMoneyAccount)) {
                      System.out.println("no account by this name.");
                      System.out.println("check for this account or you want to cancel press e");
                  }
              }while (user.getEmail().equals(sendMoneyAccount));
          } while(!userServicesAndValidationInputs.checkEmail(sendMoneyAccount));
          if(!Queries.isEmailExistQuery(DBConnection.geConnection(),sendMoneyAccount)) {
              System.out.println("this account is not exist.");
              sendMoneyAccount = sc.next();
          }
      }while(!Queries.isEmailExistQuery(DBConnection.geConnection(),sendMoneyAccount));
     String depositNumber;
    System.out.println("please enter the number you want to send to this account.");
    do {
        do {
            depositNumber = sc.next();
            if (!userServicesAndValidationInputs.isNumber(depositNumber)) System.out.println("invalid input");
        } while (!userServicesAndValidationInputs.isNumber(depositNumber));
            if (Integer.parseInt(depositNumber) > userbalance) {
                System.out.println("you can't send this amount of money");
            }
    }while(Integer.parseInt(depositNumber)>userbalance);
 Queries.withDrawQuery(conn,user.getUserId(),Integer.parseInt(depositNumber));
if(!Queries.DepositQueryToAnotherUser(conn,user.getUserId(),Integer.parseInt(depositNumber),sendMoneyAccount))
    System.out.println("this money is send to this account succssfully.");
}
private static Connection openConnection(){
    try {
        return  DBConnection.geConnection();
    } catch (ClassNotFoundException e) {
      return null;
    } catch (SQLException e) {
        return null;
    }
}
private static void Settings(Connection conn ,Scanner sc, Users user, ResultSet userData){
 OptionGenerator options = new OptionGenerator();
    options.setInvalidOptionMessage("invalid Input.");
    options.setUserMessageOfGettingUserOptionFromUser("choose here:");
    options.setUserMessageOfOperationCancelation("operation canceled");
    options.setInvalidOptionMessage("invalid input");
    options.setOptions("1","2","3","4","5","6");
    options.setUserMessage(SettingOptions());
 int userOption = options.dynamicNumberOptions(sc);
 switch(userOption){
     case 1:userServicesAndValidationInputs.updateUserName(conn,sc,user.getUserId());break;
     case 2:userServicesAndValidationInputs.updateUserPassword(conn,sc,user.getUserId());break;
     case 3:userServicesAndValidationInputs.updateUserEmail(conn,sc,user.getUserId());break;
     case 4:userServicesAndValidationInputs.updateUserPhone(conn,sc,user.getUserId());break;
     case 5: new UserPageArabicSub(userData);
     case 6:return;
    }
}
    private static String SettingOptions(){
        return "________________________________________________________________________________________________________________________________\n" +
                "press 1 change user name , 2 change password , 3 change email , 4 change phone , 5 change to arabic , 6 return to home page\n" +
                "-------------------------------------------------------------------------------------------------------------------------------";
    }
}
