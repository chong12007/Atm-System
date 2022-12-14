
package com.mycompany.atmsystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AtmSystem {

    public static void main(String[] args) {
       ArrayList<Account> accounts = new ArrayList<>();
         Scanner sc = new Scanner(System.in);
       
       while(true){
       System.out.println("==============ATM SYSTEM===============");
       System.out.println("1.Account Login");
       System.out.println("2.Account Register");
       System.out.println("0.Exit");
       System.out.println("");
       System.out.print("Enter Your Selection : ");
       int command = sc.nextInt();
       
       switch(command){
           case 0:
               System.out.println("Thank you and bye bye...");
               System.exit(0);
           case 1 : login(accounts, sc);
               break;
           case 2 : register(accounts,sc);
               break;
           default :
               System.out.println("Inavlid selection");
       }//switch
       }//while
    }

    
    
    private static void register(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("===========Register Account=============");
        //Create new account
        Account account = new Account();
        
        //Insert value
        System.out.println("Please Enter Username : ");
        String userName = sc.next();
        account.setUserName(userName);
        
        while (true) {
        System.out.println("Please Enter Password : ");
        String password = sc.next();
        System.out.println("Please Re-enter Password : ");
        String rePassword = sc.next();
        if(rePassword.equals(password)){
            account.setPassword(password);
            break;//if password both same then not repeat
        }else{
            System.out.println("Password not the same!!");
        }
        }//while
        
        
        System.out.println("Maximum Ammount Cash Withdraw : ");
        double quotaMoney = sc.nextDouble();
        account.setQuotaMoney(quotaMoney);
        
        //Random generate card id
        String cardId=getRandomCardId(accounts);
        account.setCardId(cardId);
        
        //Successfully create account
        accounts.add(account);
        System.out.println("");
        System.out.println("===================================");
        System.out.println("Account Registered ");
        System.out.println("User Name : " + userName);
        System.out.println("Card Id : " + cardId);
        
        
        
    }//register

    private static String getRandomCardId(ArrayList<Account> accounts) {
           Random r = new Random();
        while(true){
        //Generate 8digit valuue
        String cardId = ""; //Cannot same as other
     
        
        for (int i = 0; i < 8; i++) {
           cardId += r.nextInt(10);
            
        }
        
        Account acc = getAccountByCardId(cardId, accounts);
        if(acc == null){
            //card id can use for new register account
            return cardId;
        }//if
     }//while
 
    }//get random card id
    
    private static Account getAccountByCardId(String cardId, 
    ArrayList<Account> accounts){
        
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if(acc.getCardId().equals(cardId) || acc.getUserName().equals(cardId)){
                return acc;
            }
        }
        return null;
        
    }//getAccount by id

    //Login account
    private static void login(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("==================Login====================");
        //check system have account exist
        if(accounts.size() == 0){
            System.out.println("Currently System dont have Account existed");
            return; //end program
        }
        
        //have account in  system
        while(true){
        System.out.println("Enter Card ID or UserName : ");
        String cardId = sc.next();
        //search account by card id
        Account acc = getAccountByCardId(cardId, accounts);
        if(acc != null){
            while(true){
            System.out.println("Enter password : ");
            String password = sc.next();
            
            if(acc.getPassword().equals(password)){
                //login success
                System.out.println("User " + acc.getUserName() + " Login Successful!");
                showUserCommand(sc, acc, accounts);
                break;
            }else{
                System.out.println("Invalid Password");
            }
          
            }//while
              break;
        }
        else{ 
           System.out.println("Card ID not Found!!");
        }
    }//while
    }//login

    private static void showUserCommand(Scanner sc,Account acc, ArrayList<Account> accounts) {
        while(true){
        System.out.println("=========User Command=============");
        System.out.println("1.Check Account");
        System.out.println("2.Deposit");
        System.out.println("3.Withdraw");  
        System.out.println("4.Transfer Balance");
        System.out.println("5.Change Password");  
        System.out.println("6.Cancel Account");
        System.out.println("0.Log out");
        System.out.println("Enter seleciton ");
        int command = sc.nextInt();
        switch(command){
            case 0 : System.out.println("Log Out succesful");
             return;//back to main page
             
            case 1: showAccount(acc);
                break;
            case 2: depositMoney(acc, sc);
                break;
            case 3: withdrawMoney(acc, sc);
                break;
            case 4: transferMoney(acc, sc, accounts);
                break;
             case 5: updatePassword(acc, sc);
               return;
             case 6 : 
                 if(deleteAccount(acc,sc,accounts)){
                     //if deleted then back to main page
                     return;
                 }else{
                     //if not delete then back to menu page
                     break;
                 }
                
             default: System.out.println("Invalid Selection");
                break;
        }
        }//while

                                       

          
    }

    private static void showAccount(Account acc) {
        System.out.println("===========User Acccount Info================");
        System.out.println("Card Id : " + acc.getCardId());
        System.out.println("Username : " + acc.getUserName());
        System.out.println("Balance : " + acc.getMoney());
        System.out.println("Limited Withdraw Money per once : " + acc.getQuotaMoney());
        
    }

    
    private static void depositMoney(Account acc, Scanner sc) {
        System.out.println("=============Deposit Money================");
        System.out.println("Enter Ammount deposit : ");
        double money = sc.nextDouble();
        
        //update user balance
        acc.setMoney(acc.getMoney() + money);
        System.out.println("Money successfully Deposit");
        showAccount(acc);
    }

    private static void withdrawMoney(Account acc, Scanner sc) {
        System.out.println("================Withdraw Money================");
        //check still have balance
        if(acc.getMoney() < 50){
            System.out.println("Balance not enough");
            return;
        }
        
        while(true){
        //enough money
        System.out.println("Enter ammount withdraw : ");
        double money = sc.nextDouble();
        
        //check money enough to be withdraw
        if(money > acc.getQuotaMoney()){
            System.out.println("Ammount Withdraw Once cannot exceed " + acc.getQuotaMoney());
        }else if(money > acc.getMoney()){
            System.out.println("Balance not enough Withdraw Failed");
            System.out.println("Your current balance : " + acc.getMoney());
        }else{
            //Success withdraw
            System.out.println("Withdraw Ammount " + money + " successful");
            //update user balance
            acc.setMoney(acc.getMoney() - money);
            showAccount(acc);
            return;
        }
        }//while
    }

    private static void transferMoney(Account acc, Scanner sc, ArrayList<Account> accounts) {
        System.out.println("============Transfer Balance==============");
        //check system have 2 accounts exist
        if(accounts.size() < 2){
            System.out.println("Current system dont have enough 2 user");
            return;
        }
        
        //>=2 account user
        //check current user have balance
        if(acc.getMoney() == 0){
            System.out.println("Balance not enough Cannot Transfer");
            return;
        }
        
        while(true){
        //Transfer money
        System.out.println("Enter Card ID you wanted transfer to : ");
        String cardId = sc.next();
        
        //cannot be user card id
        if(cardId.equals(acc.getCardId())){
            System.out.println("Cannot transfer money to yourself");
            continue; //back to next loop
        }
        //search card id
        Account account = getAccountByCardId(cardId, accounts);
        //check account exist or not
        if(account == null){
            System.out.println("Cant find Card id");
        }else{
             //Account exist
            String userName = account.getUserName();
            String tip = "*" + userName.substring(1);
            System.out.println("Enter user [" + tip + "] full name");
            String preName = sc.next();
            
            if(userName.startsWith(preName)){
                while(true){
                //transfer money
                System.out.println("Enter transfer Ammount : ");
                double money = sc.nextDouble();
                //check enough money or not
                if(money > acc.getMoney()){
                    System.out.println("Balance not enough, Your balance is : " + acc.getMoney());
                    
                }else{
                    //tranfer money success
                    acc.setMoney(acc.getMoney() - money);
                    account.setMoney(account.getMoney() + money);
                    System.out.println("Transfer Balance Successful");
                    System.out.println("Balace now : " + acc.getMoney());
                    return;
                }
                }//while
            }else{
                System.out.println("User name error");
            }
           
        }
        }//while
    }

    private static void updatePassword(Account acc, Scanner sc) {
        System.out.println("===============Change password================");
        while(true){
        System.out.println("Enter current password : ");
        String password = sc.next();
        if(acc.getPassword().equals(password)){
            //password correct
            System.out.println("Enter new password : ");
            String newPassword = sc.next();
            
            System.out.println("Re-enter new password : ");
            String reNewPassword = sc.next();
            
            if(newPassword.equals(reNewPassword)){
                //succesfuly change password
                acc.setPassword(newPassword);
                System.out.println("Password have changed");
                return;
            }else{
                System.out.println("Password not same");
            }
        }else{
            System.out.println("Invalid Password");
        }
        }//while
    }

    private static boolean deleteAccount(Account acc, Scanner sc, ArrayList<Account> accounts) {    
    System.out.println("===========Delete Account=================");
    System.out.println("Are you sure you wanna delete account? [Y]/[N] : ");
             String rs = sc.next();
             switch(rs){
                 case "Y" : 
                     if(acc.getMoney() > 0){
                         System.out.println("Still have balance cannot delete account");
                     }else{
                   accounts.remove(acc);
                 System.out.println("Account have been deleted");
                 return true;
                     }
                    
                     break;
                 default:
                     System.out.println("Account not changed");
                    
             }
             return false;
    }
    
}//end
