import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserPageArabicSub {
    UserPageArabicSub(ResultSet userData){
        UserPageForArabic.UserPageForArabic(userData);
    }

}
 class UserPageForArabic {
    private  UserPageForArabic(){}
    public static void UserPageForArabic(ResultSet userData){
        boolean wantToGoBack = false;
        Users user=null;
        Connection conn = openConnection();
        try {
            user = createUserData(userData);
        }catch (SQLException ex){
            System.out.println("حدث خطأ ما أثناء الحصول على بياناتك ، يرجى المحاولة مرة أخرى لاحقًا");
            new ProgramManager();
        }
        do {
            printOptions();
            Scanner sc = new Scanner(System.in);
            String choose ;
            do{
                System.out.print("                                                 اختر هنا: ");
                choose = sc.next();
                if(!userServiceAndValidationInputsArabicSub.isNumber(choose)) System.out.println("مدخل غير صالح");
            }while(!userServiceAndValidationInputsArabicSub.isNumber(choose));
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
                    System.out.println("لقد قمت بتسجيل الخروج.");
                  new ProgramManagerForArabic();
                    break;
                case 7: Settings(conn,sc,user);
                    break;
                case 8: System.exit(0);
                default:
                    System.out.println("مدخل غير صالح");
            }
        }while(!wantToGoBack);
    }
    private  static boolean seeAccount(int RealSecurityCode){
        Scanner sc = new Scanner(System.in);
        String securityCode;
        System.out.println("الرجاء إدخال رمز الأمان الخاص بك");
        do{
            securityCode= sc.next();
            if(!userServiceAndValidationInputsArabicSub.isNumber(securityCode)) System.out.println("مدخل غير صالح.");
        }while(!userServiceAndValidationInputsArabicSub.isNumber(securityCode));
        if(Integer.parseInt(securityCode)!=RealSecurityCode){
            return false;
        }
        return true;
    }
    private static void printOptions(){
        System.out.println("________________________________________________________________________________________________________________________________________________");
        System.out.println("|اضغط 1 انظر تفاصيل حسابك ، 2 اعرض النشاط ، 3 أرسل الأموال إلى حساب آخر ، 4 إيداع ، 5 سحب ، 6 تسجيل الخروج ، 7 إعدادات ، 8 خروج|");
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
            System.out.printf("|هذا هو التحقق من بياناتك الشخصية:                                                                %s","|" );
            System.out.println();
            System.out.printf("اسم المستخدم : %s                                                                                          ",Queries.getNameByuserId(conn,user.getUserId()));
            System.out.println();
            System.out.printf("هوية المستخدم الوطنية : %s                                                                                          ",Queries.getNationalUserIdByUserId(conn,user.getUserId()));
            System.out.println();
            System.out.printf("البريد الالكتروني للمستخدم: %s                                                                                        ",Queries.getUserEmailbyUserId(conn,user.getUserId()));
            System.out.println();
            System.out.printf("كلمة مرور المستخدم: %s                                                                                       ",Queries.getUserPassowrdByUserId(conn,user.getUserId()));
            System.out.println();
            System.out.printf("عمر المستخدم: %s                                                                                            ",Queries.getUserAgeByUserId(conn,user.getUserId()));
            System.out.println();
            System.out.printf("تاريخ إنشاء حساب المستخدم : %s                                                                         ",user.getDateOfCreateAccount());
            System.out.println();
            System.out.printf("هاتف المستخدم : %s                                                                                          ",Queries.getUserPhoneByUserId(conn,user.getUserId()));
            System.out.println();
            try {
                System.out.println("رصيد المستخدم : "+Queries.getBalanceQuery(conn,user.getUserId())+"$");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("_________________________________________________________________________________________________________");
        }
    }
    private static void deposit(Connection conn,Scanner sc , int userId) {
        System.out.println("الرجاء إدخال الرقم الذي تريد إيداعه إذا كنت تريد إلغاء الضغط على e:");
        String depositNumber;
        do {
            depositNumber = sc.next();
            if(depositNumber.equals("e")){
                System.out.println("العملية ملغاة");
                return;
            }
            if(!userServiceAndValidationInputsArabicSub.isNumber(depositNumber))System.out.println("مدخل غير صالح");
        }while(!userServiceAndValidationInputsArabicSub.isNumber(depositNumber));
        try {
            Queries.DepositQuery(conn,userId,Integer.parseInt(depositNumber));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("يتم إيداع أموالك في حسابك.");
    }
    private static void showActivity(Connection conn,int userId)  {

        try {
            ResultSet rs = Queries.showAcitivityQuery(conn,userId);
            CommandLineTable generateTable = new CommandLineTable();
            generateTable.setHeaders("نوع العملية" ,"وقت المعالجة" , "مقدار المال" , "المرسل" , "المتلقي");
            String userAccount = Queries.getEmailByUserIdQuery(conn,userId);
            generateTable.setShowVerticalLines(true);
            while(rs.next()){
                if(userServiceAndValidationInputsArabicSub.checkEmail(rs.getString(4))&&!rs.getString(4).equals(userAccount)){
                    generateTable.addRow("يستقبل", rs.getNString(2), Integer.toString(rs.getInt(3)), rs.getString(4), rs.getString(5));

                } else if(rs.getString(4).equals(userAccount)&&!rs.getString(5).equals("-------------"))
                {
                    generateTable.addRow("يرسل", rs.getNString(2), Integer.toString(rs.getInt(3)), rs.getString(4), rs.getString(5));
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
        System.out.println("أدخل الأموال التي تريد سحبها إذا كنت تريد إلغاء الضغط على e.");
        String ammountOfMoney;
        do{
            ammountOfMoney = sc.next();
            if(ammountOfMoney.equals("e")){
                System.out.println("تم إلغاء العملية");
                return;
            }
            if(!userServiceAndValidationInputsArabicSub.isNumber(ammountOfMoney)) System.out.println("مدخل غير صالح.");
        }while(!userServiceAndValidationInputsArabicSub.isNumber(ammountOfMoney));
        try {

            if(!Queries.withDrawQuery(conn,userId,Integer.parseInt(ammountOfMoney)))
            {
                System.out.println("سحب الأموال بنجاح.");
            }else{
                System.out.println("لا يوجد مال لسحب هذا المبلغ من المال.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void sendMoneyToAnotherAccount(Connection conn,Scanner sc,Users user) throws SQLException, ClassNotFoundException {
        System.out.println("أدخل الحساب الذي تريد إرسال الأموال إليه إذا كنت تريد إلغاء الضغط على e.");
        int userbalance =Queries.getBalanceQuery(conn,user.getUserId());
        String sendMoneyAccount;
        do {
            do {
                do {
                    sendMoneyAccount = sc.next();
                    if (sendMoneyAccount.equals("e")) {
                        System.out.println("تم إلغاء العملية");
                        return;
                    }
                    if (user.getEmail().equals(sendMoneyAccount)) {
                        System.out.println("لا يمكنك إرسال أموال إلى حسابك.");
                    }
                    if (!userServiceAndValidationInputsArabicSub.checkEmail(sendMoneyAccount)) {
                        System.out.println("لا يوجد حساب بهذا الاسم.");
                        System.out.println("تحقق من هذا الحساب أو تريد إلغاء اضغط e");
                    }
                }while (user.getEmail().equals(sendMoneyAccount));
            } while(!userServiceAndValidationInputsArabicSub.checkEmail(sendMoneyAccount));
            if(!Queries.isEmailExistQuery(DBConnection.geConnection(),sendMoneyAccount)) {
                System.out.println("هذا الحساب غير موجود.");
                sendMoneyAccount = sc.next();
            }
        }while(!Queries.isEmailExistQuery(DBConnection.geConnection(),sendMoneyAccount));
        String depositNumber;
        System.out.println("الرجاء إدخال الرقم الذي تريد إرساله إلى هذا الحساب.");
        do {
            do {
                depositNumber = sc.next();
                if (!userServiceAndValidationInputsArabicSub.isNumber(depositNumber)) System.out.println("مدخل غير صالح");
            } while (!userServiceAndValidationInputsArabicSub.isNumber(depositNumber));
            if (Integer.parseInt(depositNumber) > userbalance) {
                System.out.println("لا يمكنك إرسال هذا المبلغ من المال");
            }
        }while(Integer.parseInt(depositNumber)>userbalance);
        Queries.withDrawQuery(conn,user.getUserId(),Integer.parseInt(depositNumber));
        if(!Queries.DepositQueryToAnotherUser(conn,user.getUserId(),Integer.parseInt(depositNumber),sendMoneyAccount))
            System.out.println("تم إرسال هذه الأموال إلى هذا الحساب بنجاح.");
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
    private static void Settings(Connection conn ,Scanner sc, Users user){
        OptionGenerator options = new OptionGenerator();
        options.setInvalidOptionMessage("مدخل غير صالح.");
        options.setUserMessageOfGettingUserOptionFromUser("اختر هنا:");
        options.setUserMessageOfOperationCancelation("تم إلغاء العملية");
        options.setOptions("1","2","3","4","5");
        options.setUserMessage(SettingOptions());
        int userOption = options.dynamicNumberOptions(sc);
        switch(userOption){
            case 1:userServiceAndValidationInputsArabicSub.updateUserName(conn,sc,user.getUserId());break;
            case 2:userServiceAndValidationInputsArabicSub.updateUserPassword(conn,sc,user.getUserId());break;
            case 3:userServiceAndValidationInputsArabicSub.updateUserEmail(conn,sc,user.getUserId());break;
            case 4:userServiceAndValidationInputsArabicSub.updateUserPhone(conn,sc,user.getUserId());break;
            case 5:return;
        }
    }
    private static String SettingOptions(){
        return "________________________________________________________________________________________________________________________________\n" +
                "اضغط 1 لتغيير اسم المستخدم ، 2 تغيير كلمة المرور ، 3 تغيير البريد الإلكتروني ، 4 تغيير الهاتف ، 5 العودة إلى الصفحة الرئيسية\n" +
                "--------------------------------------------------------------------------------------------------------------------------------";
    }
}


