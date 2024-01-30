package ecommerence.view;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ecommerence.dao.CustomerDAO;
import ecommerence.models.Address;
import ecommerence.models.Cart;
import ecommerence.models.Customer;
import ecommerence.models.DeliveryStatus;
import ecommerence.models.Gender;
import ecommerence.models.Order;
import ecommerence.models.Product;
import ecommerence.utils.Validator;

public class CustomerView 
{
    static Scanner in = new Scanner(System.in);
    static Console con = System.console();

    public Customer newCustomer()
    {
        System.out.print("Enter the First Name :");
        String fName = in.next();
        System.out.print("Enter the Last Name :");
        String lName = in.next();
        System.out.println("\n1.Male\n2.Female\nEnter Gender(1 or 2): ");
        int n = in.nextInt();
        while(n > 2)
        {
            System.out.println("\n1.Male\n2.Female\nEnter Gender(1 or 2): ");
            n = in.nextInt();
        }
        Gender g = Gender.Male;
        if(n==1)
        {
            g = Gender.Male;
        }
        else if(n==2)
        {
            g = Gender.Female;
        }
        System.out.print("Enter the Email ID: ");
        String email = in.next();
        while(!Validator.emailValidator(email))
        {
            System.out.print("Enter the Valid Email ID: ");
            email = in.next();
        }
        System.out.print("Enter the Password: ");
        char[] pas = con.readPassword();
        String pass = new String(pas);
        while(!Validator.isValidPassword(pass))
        {
            System.out.print("Enter the Valid Password: ");
            pass = in.next();
        }
        System.out.print("Enter the Mobile number: ");
        Long mobile = in.nextLong();
        System.out.print("Enter the Inital Wallet Amount: ");
        Long wallet_amount = in.nextLong();
        Customer customer = new Customer(fName, lName, g, email, pass, mobile, wallet_amount);
        return customer;
    }

    public String[] credentials()
    {
        System.out.print("Enter the Email ID: ");
		String email = in.next();
		System.out.print("Enter the Password: ");
        char[] pas = con.readPassword();
		String pass = new String(pas);
		if(Validator.emailValidator(email) && Validator.isValidPassword(pass))
		{
			return new String[]{email, pass};
		}
        return null;
    }

    public void getCustomerName(String customerName)
    {
        System.out.print(customerName);
    }

    public void browserProducts(List<String> products)
    {
        System.out.println("----------All--Products------------");
        for(String product: products)
        {
            System.out.println(product);
            System.out.println("----------------------------------------");
        }
    }

    public int getProductInput()
    {
        System.out.print("Enter the Product Id : ");
        return in.nextInt();
    }

    public int getBuyOrCartInput()
    {
        System.out.print("Enter 1 to Buy or 2 to Add to cart (0 to back):");
        return in.nextInt();
    }

    public Cart newCart(Product product)
    {
        System.out.print("Enter the product quantity :");
        int quantity = in.nextInt();
        Cart cart = new Cart(CustomerDAO.CUSTOMER_ID, product.getProductId(), quantity, (quantity*product.getProductPrice()));
        return cart;
    }

    public Order newOrder(Product product, List<Address> addresses)
    {
        System.out.print("Enter the product quantity :");
        int quantity = in.nextInt();
        showAdresses(addresses);
        System.out.print("Enter the Address id :");
        int addressId = in.nextInt();
        Order order = new Order(CustomerDAO.CUSTOMER_ID, product.getProductId(), product.getMerchantId(), addressId, quantity, product.getProductPrice(), (quantity*product.getProductPrice()));
        return order;
    }

    public Address newAddress()
    {
        in.nextLine();
        System.out.print("Enter the Address (0 to back):");
        String addres = in.nextLine();
        if(addres.equals("0"))
        {
            return null;
        }
        System.out.print("Enter the City :");
        String city = in.nextLine();
        System.out.print("Enter the State :");
        String state = in.nextLine();
        System.out.print("Enter the pincode :");
        int pincode = in.nextInt();
        Address address = new Address(addres, city, state, pincode, CustomerDAO.CUSTOMER_ID);
        return address;
    }

    private void showAdresses(List<Address> addresses)
    {
        for(Address address: addresses)
        {
            System.out.println("--------------------------------------------");
            System.out.println("Address Id :"+address.getAddressId());
            System.out.println("Address :"+address.getAddress());
            System.out.println("City :"+address.getCity());
            System.out.println("State :"+address.getState());
            System.out.println("Pincode :"+address.getPinCode());
        }
        System.out.println("--------------------------------------------");
    }

    private boolean showCart(List<String> carts)
    {
        if(carts.isEmpty())
        {
            return false;
        }
        for(String cart : carts)
        {
            System.out.println("--------------------------------");
            System.out.println(cart);
        }
        System.out.println("--------------------------------");
        return true;
    }

    public int viewCart(List<String> carts)
    {
        if(!showCart(carts))
        {
            return -1;
        }
        System.out.print("Enter the cart id to checkout or 0 to back:");
        return in.nextInt();
    }

    public Order cartToCheckOut(Cart cart, List<Address> addresses, Product product)
    {
        System.out.print("Enter the quantity to adjust (press 0 to dont want to adjust):");
        int quantityAdjust = in.nextInt();
        if(quantityAdjust != 0)
        {
            cart.setQuantity(quantityAdjust);
        }
        showAdresses(addresses);
        System.out.print("Enter the Address id :");
        int addressId = in.nextInt();
        Order order = new Order(cart.getCustomerId(), product.getProductId(), product.getMerchantId(), addressId, cart.getQuantity(), product.getProductPrice(), product.getProductPrice()*cart.getQuantity());
        return order;
    }

    private boolean showOrders(List<String> orders)
    {
        if(orders.isEmpty())
        {
            return false;
        }
        for(String order: orders)
        {
            System.out.println("--------------------------------");
            System.out.println(order);
        }
        System.out.println("--------------------------------");
        return true;
    }
    
    public List<Object> productReturn(List<String> orders)
    {
        List<Object> inputs = new ArrayList<>();
       
        if(!showOrders(orders))
        {
            System.out.println("There is no products  eligible for return");
            return null;
        }
        System.out.print("Enter the order id to return (0 to back):");
        int orderid = in.nextInt();
        if(orderid == 0)
        {
            return null;
        }
        inputs.add(orderid);
        inputs.add(DeliveryStatus.Returned);

        return inputs;
    }

    public List<Object> productCancel(List<String> orders)
    {
        List<Object> inputs = new ArrayList<>();
        
        if(!showOrders(orders))
        {
            System.out.println("There is no products  eligible for cancel");
            return null;
        }
        System.out.print("Enter the order id to cancel (0 to back) :");
        int orderid = in.nextInt();
        if(orderid == 0)
        {
            return null;
        }
        inputs.add(orderid);
        inputs.add(DeliveryStatus.Cancelled);
        
        return inputs;
    }

    public void showOrderHistory(List<String> orders)
    {
        for(String order: orders)
        {
            System.out.println("--------------------------------");
            System.out.println(order);
        }
        System.out.println("--------------------------------");
    }
}