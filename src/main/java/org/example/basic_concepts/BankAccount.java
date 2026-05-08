/*
Single Responsibility Principle:

The biggest learning here is:

A method should do one business action clearly.
withdraw() should withdraw.
closeAccount() should close.

When methods secretly trigger unrelated business events,
systems become hard to understand and dangerous to modify.
 */


package org.example.basic_concepts;
enum AccStatus {
    ACTIVE,
    CLOSED
}
public class BankAccount {
    // states - make it private
    private String accno;
    private String ownername;
    private double balance;
    private AccStatus status;




    //constructor - public
    public BankAccount(String accnum,String ownername, double openingbal){
        //first of all check validity of the opening balance
        if(openingbal <0){
            throw new IllegalArgumentException("Opening balance cant be negative");
        }
        this.accno= accnum;
        this.ownername= ownername;
        this.balance= openingbal;
        this.status= AccStatus.ACTIVE;

    }

    //behaviours
    public void deposit(double amt){
        ensureAccountISActive();
        if(amt<=0){
            throw new IllegalArgumentException("deposit amount must be positive and non zero");
        }

        this.balance+= amt;
    }
    public void withdraw(double amt){
        ensureAccountISActive();
        if(amt<=0){
            throw new IllegalArgumentException("withdraw amount must be positive and not zero");
        }

        if(amt>this.balance){
            throw new IllegalArgumentException("withdraw amount cant be greater than balance");
        }
        this.balance-= amt;

    }
    public void closeAccount(){
        ensureAccountISActive();
        if(balance!=0){
            throw new IllegalStateException("Account can be closed only when balance is zero");
        }
        status= AccStatus.CLOSED;
    }

    public double getBalance(){
        return this.balance;
    }
    public void printAccsummary(){
        System.out.println("Account Number: "+this.accno);
        System.out.println("Owner Name: "+this.ownername);
        System.out.println("Balance: "+this.balance);
        System.out.println("Status: "+this.status);

    }

    private void ensureAccountISActive(){
        if(this.status== AccStatus.CLOSED){
            throw new IllegalArgumentException("account is closed");
        }
    }



}
class Main{
    public static void main(String[] args) {

        try {
            BankAccount bankAccount1 = new BankAccount("12345", "John", 100.00);
            bankAccount1.deposit(500);
            bankAccount1.withdraw(600);
            bankAccount1.closeAccount();
            bankAccount1.printAccsummary();
        } catch (Exception e) {
            System.out.println("Test 1 failed: " + e.getMessage());
        }

        try {
            BankAccount bankAccount2 = new BankAccount("234", "Josh", -10.00);
        } catch (Exception e) {
            System.out.println("Test 2 failed: " + e.getMessage());
        }

        try {
            BankAccount bankAccount3 = new BankAccount("1234", "Josh", 100.00);
            bankAccount3.withdraw(5000);
        } catch (Exception e) {
            System.out.println("Test 3 failed: " + e.getMessage());
        }

        try {
            BankAccount bankAccount4 = new BankAccount("125", "Doe", 0.00);
            bankAccount4.deposit(0);
        } catch (Exception e) {
            System.out.println("Test 4 failed: " + e.getMessage());
        }

        try {
            BankAccount bankAccount5 = new BankAccount("34", "Nick", 100.00);
            bankAccount5.closeAccount();
        } catch (Exception e) {
            System.out.println("Test 5 failed: " + e.getMessage());
        }

    }
}

