package ecommerence.controller;

public interface CustomerProcess 
{
    void signUp();
    int login();
    boolean logout();
    void buyProduct();
    void cartToBuy();
    void orderReturn();
    void orderCancel();
    void showOrderHistory();
}
