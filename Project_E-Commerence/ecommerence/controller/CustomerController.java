package ecommerence.controller;

import java.util.List;

import ecommerence.dao.CustomerDAO;
import ecommerence.models.Address;
import ecommerence.models.Cart;
import ecommerence.models.Customer;
import ecommerence.models.DeliveryStatus;
import ecommerence.models.Order;
import ecommerence.models.Product;
import ecommerence.view.CustomerView;

public class CustomerController implements CustomerProcess
{
    private CustomerDAO customerDAO;
    private CustomerView customerView;

    public CustomerController(CustomerDAO customerDAO, CustomerView customerView)
    {
        this.customerDAO = customerDAO;
        this.customerView = customerView;
    }

    public void signUp()
    {
        Customer customer = customerView.newCustomer();
        customerDAO.addCustomer(customer);
    }

    public int login()
    {
        String[] credentials = customerView.credentials();
        if(credentials != null)
        {
            return customerDAO.login(credentials[0], credentials[1]);
        }
        return -1;
    }

    public boolean logout()
    {
        return true;
    }

    public void getCustomerName()
    {
        String customerName = customerDAO.getCustomer().getFirstName();
        customerView.getCustomerName(customerName);
    }

    private void showProducts()
    {
        List<String> products = customerDAO.getAllProducts();
        if(products != null)
        {
            customerView.browserProducts(products);
        }
        else
        {
            System.out.println("No products");
        }
    }

    public void buyProduct()
    {
        showProducts();
        int productId = customerView.getProductInput();
        if(productId==0)
        {
            return;
        }
        Product product = customerDAO.getProduct(productId);
        if(product != null)
        {
            int input = customerView.getBuyOrCartInput();
            if(input == 1)
            {
                List<Address> addresses = customerDAO.getAllAddress();
                if(addresses.size() > 0)
                {
                    Order order = customerView.newOrder(product, addresses);                 // Get the Order Object from view
                    customerDAO.createOrder(order);                                          // Create the Order with order object
                    double amount = customerDAO.getCustomerWallet() - order.getAmount();     // Get the Current wallet amount and calculate the wallet amount after buyed
                    customerDAO.updateCustomerWallet(amount);                                // Update the wallet amount of customer
                    double income = customerDAO.getMerchantAmount(product.getMerchantId()) + order.getAmount();
                    customerDAO.updateMerchanWallet(income, product.getMerchantId());        // Update the wallet amount of Merchant
                    int stockBalance = customerDAO.getProductStockQuantity(productId) - order.getQuantity();
                    customerDAO.updateStockQuantity(stockBalance, productId);                // Update the Quantity of product
                }
                else
                {
                    System.out.println("First add address then buy");
                    Address address = customerView.newAddress();
                    customerDAO.createAddress(address);
                    buyProduct();
                }
                
            }
            else if(input == 2)
            {
                Cart cart = customerView.newCart(product);
                customerDAO.addToCart(cart);
            }
            else if(input == 0)
            {
                return;
            }
        }
        else
        {
            System.out.println("Invalid product id...");
        }
    }

    public void newAddress()
    {
        Address address = customerView.newAddress();
        if(address == null)
        {
            return;
        }
        customerDAO.createAddress(address);
    }

    public void cartToBuy()
    {
        List<String> carts = customerDAO.getAllCarts();
        if(carts.isEmpty())
        {
            System.out.println("Cart is empty");
            return;
        }
        int cartid = customerView.viewCart(carts);
        if(cartid == 0)
        {
            return;
        }
        Cart cart = customerDAO.getCart(cartid);
        Product product = customerDAO.getProduct(cart.getProductId());
        List<Address> addresses = customerDAO.getAllAddress();
        Order order = customerView.cartToCheckOut(cart, addresses, product);
        customerDAO.createOrder(order);
        customerDAO.updateCartStatus(cartid);
        double amount = customerDAO.getCustomerWallet() - order.getAmount();     // Get the Current wallet amount and calculate the wallet amount after buyed
        customerDAO.updateCustomerWallet(amount);                                // Update the wallet amount of customer
        double income = customerDAO.getMerchantAmount(product.getMerchantId()) + order.getAmount();
        customerDAO.updateMerchanWallet(income, product.getMerchantId());        // Update the wallet amount of Merchant
        int stockBalance = customerDAO.getProductStockQuantity(product.getProductId()) - order.getQuantity();
        customerDAO.updateStockQuantity(stockBalance, product.getProductId());  
    }

    public void orderReturn()
    {
        List<String> orders = customerDAO.getReturnEligibleOrders();  
        List<Object> inputs = customerView.productReturn(orders);
        if(inputs == null)
        {
            return;
        }
        DeliveryStatus deliveryStatus = (DeliveryStatus)inputs.get(1);
        int orderid = (int)inputs.get(0);
        customerDAO.updateDeliveryStatus(deliveryStatus, orderid);
        Order order = customerDAO.getOrder(orderid);
        double customerReturnAmount = customerDAO.getCustomerWallet() + order.getAmount();                      // update the customer wallet amount
        customerDAO.updateCustomerWallet(customerReturnAmount);
        double merchantReturnPay = customerDAO.getMerchantAmount(order.getMerchantId()) - order.getAmount();    // update the mechant wallet amount
        customerDAO.updateMerchanWallet(merchantReturnPay, order.getMerchantId());
        int stockBalance = customerDAO.getProductStockQuantity(order.getProductId()) + order.getQuantity();         //update the stock quantity
        customerDAO.updateStockQuantity(stockBalance, order.getProductId());
    }

    public void orderCancel()
    {
        List<String> orders = customerDAO.getCancelEligibleOrders();  
        List<Object> inputs = customerView.productCancel(orders);
        if(inputs == null)
        {
            return;
        }
        DeliveryStatus deliveryStatus = (DeliveryStatus)inputs.get(1);
        int orderid = (int)inputs.get(0);
        customerDAO.updateDeliveryStatus(deliveryStatus, orderid);
        Order order = customerDAO.getOrder(orderid);
        double customerReturnAmount = customerDAO.getCustomerWallet() + order.getAmount();                      // update the customer wallet amount
        customerDAO.updateCustomerWallet(customerReturnAmount);
        double merchantReturnPay = customerDAO.getMerchantAmount(order.getMerchantId()) - order.getAmount();    // update the mechant wallet amount
        customerDAO.updateMerchanWallet(merchantReturnPay, order.getMerchantId());
        int stockBalance = customerDAO.getProductStockQuantity(order.getProductId()) + order.getQuantity();         //update the stock quantity
        customerDAO.updateStockQuantity(stockBalance, order.getProductId());
    }

    public void showOrderHistory()
    {
        List<String> orders = customerDAO.getOrderHistory();
        if(orders.isEmpty())
        {
            System.out.println("There is no order history");
        }
        customerView.showOrderHistory(orders);
    }
}
