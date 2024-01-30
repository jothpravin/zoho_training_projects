package onlineBanking.controller;

import onlineBanking.dao.CustomerDAO;
import onlineBanking.model.Account;
import onlineBanking.model.Customer;
import onlineBanking.view.CustomerView;

public class CustomerController 
{
    private CustomerDAO customerDAO;
    private CustomerView customerView;
    public CustomerController(CustomerDAO customerDAO, CustomerView customerView)
    {
        this.customerDAO = customerDAO;
        this.customerView = customerView;
    }

    public void newAccount()
    {
        Customer customer = customerView.newCustomer();
        Customer updateCustomer = customerDAO.createCustomer(customer);
        if(updateCustomer == null)
        {
            return;
        }
    }
}