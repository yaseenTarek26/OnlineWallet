import java.util.Scanner;
public class OptionGenerator {
   private  String[] options;
   private String returnOption;
   private boolean isNumberOptions;
   private String userMessage;
   private String InvalidOptionMessage;
   private String UserConfirmationMessage, userMessageOfOperationCancelation,userMessageOfGettingUserOptionFromUser;
    public void setUserConfirmationMessage(String userConfirmationMessage) {
        UserConfirmationMessage = userConfirmationMessage;
    }

    public String[] getOptions() {
        return options;
    }

    public String getInvalidOptionMessage() {
        return InvalidOptionMessage;
    }

    public String getUserConfirmationMessage() {
        return UserConfirmationMessage;
    }

    public String getUserMessageOfOperationCancelation() {
        return userMessageOfOperationCancelation;
    }

    public String getUserMessageOfGettingUserOptionFromUser() {
        return userMessageOfGettingUserOptionFromUser;
    }

    public void setUserMessageOfOperationCancelation(String userMessageOfOperationCancelation) {
        this.userMessageOfOperationCancelation = userMessageOfOperationCancelation;
    }

    public void setUserMessageOfGettingUserOptionFromUser(String userMessageOfGettingUserOptionFromUser) {
        this.userMessageOfGettingUserOptionFromUser = userMessageOfGettingUserOptionFromUser;
    }

    public OptionGenerator(){}
public OptionGenerator(String userMessage, String returnOption , String InvalidOptionMessage){
    this.userMessage = userMessage;
    this.InvalidOptionMessage= InvalidOptionMessage;
    this.returnOption = returnOption;
}
    public  String dynamicOptionsString(Scanner sc){
    printMessageOptions();
        String userOption ;
        do{
            userOption = sc.next();
            if(userOption.equals(returnOption)){
                getUserMessageOfOperationCancelation();
                return returnOption;
            }
            if(!userOptionMatchSystemOptions(userOption,options)) {
                System.out.println(InvalidOptionMessage);
                printMessageOptions();
            }
        }while(!userOptionMatchSystemOptions(userOption,options));
        for(int i =0;i<options.length;i++){
            if(userOption.equals(options[i])){
                return options[i];
            }
        }
        return returnOption;
    }
    public int dynamicNumberOptions(Scanner sc){
       printMessageOptions();
        String userOption;
        do{
            do {
                getUserMessageOfGettingUserOptionFromUser();
                userOption = sc.next();
                if(!userServicesAndValidationInputs.isNumber(userOption)) System.out.println("not a number");
            }while(!userServicesAndValidationInputs.isNumber(userOption));
            if(userOption.equals(returnOption)){
               getUserMessageOfOperationCancelation();
                return -1;
            }
            if(!userOptionMatchSystemOptions(userOption,options)) System.out.println(InvalidOptionMessage);
        }while(!userOptionMatchSystemOptions(userOption,options));
        for(int i =0;i<options.length;i++){
            if(userOption.equals(options[i])){
                try {
                    return Integer.parseInt(options[i]);
                }catch (Exception ex){
                    throw new IllegalArgumentException("can't convert string into int");
                }
            }
        }
        return -1;
    }
    public void setReturnOption(String returnOption) {
        this.returnOption = returnOption;
    }
    public void setOptions(String...options){this.options= options;}
public void setInvalidOptionMessage(String message){
        this.InvalidOptionMessage = message;
}
    public void setNumberOptions(boolean numberOptions) {
        isNumberOptions = numberOptions;
    }
    public void setUserMessage(String userMessage){
        this.userMessage = userMessage;
    }
    private boolean userOptionMatchSystemOptions(String userOption ,String[] options){
           for(int i = 0;i<options.length;i++){
               if(userOption.equals(options[i])){
                   return true;
               }
           }
    return false;
    }
    private void printMessageOptions(){
        System.out.println(userMessage);
    }





}
