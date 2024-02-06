package ecommerence.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ecommerence.models.Address;
import ecommerence.models.Cart;
import ecommerence.models.Customer;
import ecommerence.models.DeliveryStatus;
import ecommerence.models.Gender;
import ecommerence.models.Order;
import ecommerence.models.Product;
import ecommerence.utils.ConnectionDB;

public class CustomerDAO
{
	public static int CUSTOMER_ID;
	static Connection con = null;
	public CustomerDAO()
	{
		con = ConnectionDB.getConnection();
	}
	public void addCustomer(Customer customer)
	{
		String query = "INSERT INTO customers (firstname, lastname, gender, email, password, mobile, wallet) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setObject(3, customer.getGender(), Types.OTHER);
			ps.setString(4, customer.getEmail());
			ps.setString(5, customer.getPassword());
			ps.setLong(6, customer.getMobile_no());
			ps.setLong(7, customer.getWallet());
			
			int row = ps.executeUpdate();
			if(row > 0)
			{
				System.out.println("Successfully New Customer Account Created..!");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public Customer getCustomer()
	{
		String query = "SELECT * FROM customers WHERE customerid = ?";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, CUSTOMER_ID);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				Customer customer = new Customer(rs.getString("firstname"),
									   rs.getString("lastname"),
									   Gender.valueOf(rs.getString("gender")),
									   rs.getString("email"),
									   rs.getString("password"),
									   rs.getLong("mobile"),
									   rs.getLong("wallet")
									);
				customer.setCustomerId(rs.getInt("customerid"));	
				return customer;
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}  

	public int login(String email, String password)
    {
        String customer_login_query = "SELECT customerid, password FROM customers WHERE email = ?";

        try(PreparedStatement ps = con.prepareStatement(customer_login_query))
        {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                if(rs.getString("password").equals(password))
                {
                    CUSTOMER_ID = rs.getInt("customerid");
                    return CUSTOMER_ID;
                }
            }
            rs.close();
        }
        catch(SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        return -1;
    }

	public void logout()
    {
        try
        {
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

	public List<String> getAllProducts()
	{
		String query = "SELECT p.*, c.category_name, m.companyname FROM products AS p JOIN category AS c ON p.categoryid = c.categoryid "+
						"JOIN merchant AS m ON p.merchantid = m.merchantid ORDER BY p.productid";
		List<String> products = new ArrayList<>();
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String product = new String("Product Id :"+rs.getInt("productid")+"\n"
											+"Product Name :"+rs.getString("productname")+"\n"
											+"Product Description :"+rs.getString("productdesc")+"\n"
											+"Product Price :"+rs.getInt("price")+"\n" 
											+"Product Category :"+rs.getString("category_name")+"\n"
											+"Seller Company :"+rs.getString("companyname")+"\n"
											+"Stock Available :"+rs.getInt("available")+"\n"
											+"Ratings :"+rs.getInt("ratings")
											);
				products.add(product);
			}
			return products;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

	public Product getProduct(int productId)
	{
		String query = "SELECT * FROM products WHERE productid = ?";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, productId);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				Product product = new Product(rs.getString("productname"), 
												rs.getString("productdesc"), 
												rs.getInt("price"), 
												rs.getInt("categoryid"), 
												rs.getInt("merchantid"), 
												rs.getInt("available")
												);
				product.setProductId(rs.getInt("productid"));
				product.setRatings(rs.getInt("ratings"));
				return product;	
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void addToCart(Cart cart)
	{
		String query = "INSERT INTO cart (customerid, productid, quantity, amount) VALUES (?, ?, ?, ?)";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, cart.getCustomerId());
			ps.setInt(2, cart.getProductId());
			ps.setInt(3, cart.getQuantity());
			ps.setDouble(4, cart.getAmount());
			int row = ps.executeUpdate();
			if(row > 0)
			{
				System.out.println("Successfully added to cart...!");
			}

		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void createOrder(Order order)
	{
		String query = "INSERT INTO Ordertable (customerid, productid, merchantid, addressid, quantity, buy_price, amount) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING delivery_date";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, order.getCustomerId());
			ps.setInt(2, order.getProductId());
			ps.setInt(3, order.getMerchantId());
			ps.setInt(4, order.getAddressId());
			ps.setInt(5, order.getQuantity());
			ps.setDouble(6, order.getBuy_price());
			ps.setDouble(7, order.getAmount());
			int row = ps.executeUpdate();
			if(row > 0)
			{
				ResultSet rs = ps.executeQuery();
				if(rs.next())
				{
					System.out.println("Successfully Order Placed...!");
					System.out.println("Your order delivery date :"+rs.getDate("delivery_date"));
				}
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void createAddress(Address address)
	{
		String query = "INSERT INTO address (address, city, state_name, pincode, customerid) VALUES (?, ?, ?, ?, ?)";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setString(1, address.getAddress());
			ps.setString(2, address.getCity());
			ps.setString(3, address.getState());
			ps.setInt(4, address.getPinCode());
			ps.setInt(5, address.getCustomerId());
			int row = ps.executeUpdate();
			if(row > 0)
			{
				System.out.println("Sucessfully New Address added");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void updateCustomerWallet(double amount)
	{
		String query = "UPDATE customers SET wallet = ? WHERE customerid = ?";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setDouble(1, amount);
			ps.setInt(2, CUSTOMER_ID);
			int row = ps.executeUpdate();
			if(row > 0)
			{
				System.out.println("Successfully amount updated in customer wallet");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void updateMerchanWallet(double amount, int merchantId)
	{
		String query = "UPDATE merchant SET amount = ? WHERE merchantid = ?";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setDouble(1, amount);
			ps.setInt(2, merchantId);
			int row = ps.executeUpdate();
			if(row > 0)
			{
				System.out.println("Successfully amount updated in merchant wallet");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public double getCustomerWallet()
	{
		String query = "SELECT wallet FROM customers WHERE customerid = ?";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, CUSTOMER_ID);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				return rs.getDouble(1);
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return -1.0;
	}

	public double getMerchantAmount(int merchantId)
	{
		String query = "SELECT amount FROM merchant WHERE merchantid = ?";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, merchantId);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				return rs.getDouble(1);
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return -1.0;
	}

	public int getProductStockQuantity(int productId)
	{
		String query = "SELECT available FROM products WHERE productid = ?";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, productId);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				return rs.getInt(1);
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return -1;
	}

	public void updateStockQuantity(int balance, int productId)
	{
		String query = "UPDATE products SET available = ? WHERE productid = ?";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, balance);
			ps.setInt(2, productId);
			int row = ps.executeUpdate();
			if(row > 0)
			{
				System.out.println("Successfully stock quantity updated");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public List<Address> getAllAddress()
	{
		String query = "SELECT * FROM address WHERE customerid = ?";
		List<Address> addresses = new ArrayList<>();
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, CUSTOMER_ID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Address address = new Address(rs.getString("address"), 
												rs.getString("city"), 
												rs.getString("state_name"), 
												rs.getInt("pincode"), 
												rs.getInt("customerid")
												);
				address.setAddressId(rs.getInt("addressid"));
				addresses.add(address);
			}
			return addresses;
		}
		catch(SQLException e)
		{
		 System.out.println(e.getMessage());
		}
		return null;
	}

	public List<String> getAllCarts()
	{
		String query = "SELECT * FROM cart WHERE customerid = ? AND isbuyed = 'false'";
		List<String> carts = new ArrayList<>();
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, CUSTOMER_ID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Product product = getProduct(rs.getInt("productid"));
				String cart = new String("Cart Id :"+rs.getInt("cartid")+"\n"
											+"Product Name :"+product.getProductName()+"\n"
											+"Product Desc :"+product.getProductDesc()+"\n"
											+"Product Quantity :"+rs.getInt("quantity")+"\n"
											+"Product price :"+product.getProductPrice()
											);
				carts.add(cart);
			}
			return carts;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

	public Cart getCart(int cartid)
	{
		String query = "SELECT * FROM cart WHERE cartid = ? AND customerid = ?";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, cartid);
			ps.setInt(2, CUSTOMER_ID);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				Cart cart = new Cart(rs.getInt("customerid"), rs.getInt("productid"), rs.getInt("quantity"), rs.getDouble("amount"));
				cart.setCartId(rs.getInt("cartid"));
				cart.setCartDate(rs.getDate("cart_date"));
				return cart;
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

	public Order getOrder(int orderid)
	{
		String query = "SELECT * FROM ordertable WHERE orderid = ? AND customerid = ?";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, orderid);
			ps.setInt(2, CUSTOMER_ID);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				Order order = new Order(rs.getInt("customerid"), 
										rs.getInt("productid"), 
										rs.getInt("merchantid"), 
										rs.getInt("addressid"), 
										rs.getInt("quantity"), 
										rs.getInt("buy_price"), 
										rs.getDouble("amount")
										);

				return order;
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void updateCartStatus(int cartid)
	{
		String query = "UPDATE cart SET isbuyed = 'true' WHERE cartid = ?";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, cartid);
			int row = ps.executeUpdate();
			if(row > 0)
			{
				System.out.println("Successfully Updated in Cart status");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public List<String> getReturnEligibleOrders()
	{
		String query = "SELECT o.*, m.companyname, a.* FROM ordertable AS o JOIN merchant AS m ON o.merchantid = m.merchantid "+
						" JOIN address AS a ON a.addressid = o.addressid"+
						" WHERE o.d_status = 'Delivered' AND o.customerid = ? AND CURRENT_DATE - o.delivery_date <= 14";
		List<String> orders = new ArrayList<>();
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, CUSTOMER_ID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Product product = getProduct(rs.getInt("productid")); 
				String order = new String("Order id :"+rs.getString("orderid")+"\n"
										+"Product Name :"+product.getProductName()+"\n"
										+"Product Description :"+product.getProductDesc()+"\n"
										+"Price :"+rs.getInt("buy_price")+"\n"
										+"Quantity :"+rs.getInt("quantity")+"\n"
										+"Total Amount :"+rs.getDouble("amount")+"\n"
										+"Seller :"+rs.getString("companyname")+"\n"
										+"Order Date :"+rs.getDate("order_date")+"\n"
										+"Delivery Date:"+rs.getDate("delivery_date")+"\n"
										+"Delivery Address:"+rs.getString("address")+", "+rs.getString("city")+", "+rs.getString("state_name")+", "+rs.getInt("pincode")+".\n"
										+"Status :"+rs.getString("d_status")+"\n"
										);
				orders.add(order);
			}
			return orders;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

	public List<String> getCancelEligibleOrders()
	{
		String query = "SELECT o.*, m.companyname, a.* FROM ordertable AS o JOIN merchant AS m ON o.merchantid = m.merchantid "+
						" JOIN address AS a ON a.addressid = o.addressid"+
						" WHERE o.d_status = 'YetToDeliver' AND o.customerid = ?";
		List<String> orders = new ArrayList<>();
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, CUSTOMER_ID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Product product = getProduct(rs.getInt("productid")); 
				String order = new String("Order id :"+rs.getString("orderid")+"\n"
										+"Product Name :"+product.getProductName()+"\n"
										+"Product Description :"+product.getProductDesc()+"\n"
										+"Price :"+rs.getInt("buy_price")+"\n"
										+"Quantity :"+rs.getInt("quantity")+"\n"
										+"Total Amount :"+rs.getDouble("amount")+"\n"
										+"Seller :"+rs.getString("companyname")+"\n"
										+"Order Date :"+rs.getDate("order_date")+"\n"
										+"Delivery Date:"+rs.getDate("delivery_date")+"\n"
										+"Delivery Address:"+rs.getString("address")+", "+rs.getString("city")+", "+rs.getString("state_name")+", "+rs.getInt("pincode")+".\n"
										+"Status :"+rs.getString("d_status")+"\n"
										);
				orders.add(order);
			}
			return orders;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void updateDeliveryStatus(DeliveryStatus deliveryStatus, int orderid)
	{
		String query = "UPDATE ordertable SET d_status = ? WHERE orderid = ?";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setObject(1, deliveryStatus, Types.OTHER);
			ps.setInt(2, orderid);
			int row = ps.executeUpdate();
			if(row > 0)
			{
				System.out.println("Sucessfully status updated in order table");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public List<String> getOrderHistory()
	{
		String query = "SELECT o.*, m.companyname, a.* FROM ordertable AS o JOIN merchant AS m ON o.merchantid = m.merchantid "+
						" JOIN address AS a ON a.addressid = o.addressid"+
						" WHERE o.d_status != 'YetToDeliver' AND o.customerid = ?";
		List<String> orders = new ArrayList<>();
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, CUSTOMER_ID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Product product = getProduct(rs.getInt("productid")); 
				String order = new String("Order id :"+rs.getString("orderid")+"\n"
										+"Product Name :"+product.getProductName()+"\n"
										+"Product Description :"+product.getProductDesc()+"\n"
										+"Price :"+rs.getInt("buy_price")+"\n"
										+"Quantity :"+rs.getInt("quantity")+"\n"
										+"Total Amount :"+rs.getDouble("amount")+"\n"
										+"Seller :"+rs.getString("companyname")+"\n"
										+"Order Date :"+rs.getDate("order_date")+"\n"
										+"Delivery Date:"+rs.getDate("delivery_date")+"\n"
										+"Delivery Address:"+rs.getString("address")+", "+rs.getString("city")+", "+rs.getString("state_name")+", "+rs.getInt("pincode")+".\n"
										+"Status :"+rs.getString("d_status")+"\n"
										);
				orders.add(order);
			}
			return orders;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
}