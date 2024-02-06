package ecommerence.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator
{
	public static boolean emailValidator(String email)
	{
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@ [^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        	
        	Pattern p = Pattern.compile(regexPattern);
        	Matcher matcher = p.matcher(email);
    		return matcher.matches();
	}

	public static boolean isValidPassword(String password)
    {
 
        String regex = "^(?=.*[0-9])"
                       + "(?=.*[a-z])(?=.*[A-Z])"
                       + "(?=.*[@#$%^&+=])"
                       + "(?=\\S+$).{8,20}$";
 
        Pattern p = Pattern.compile(regex);

        if(password == null) 
        {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }
    public static boolean isValidGSTNo(String str)
    {
        // Regex to check valid
        // GST (Goods and Services Tax) number
        String regex = "^[0-9]{2}[A-Z]{5}[0-9]{4}"
                       + "[A-Z]{1}[1-9A-Z]{1}"
                       + "Z[0-9A-Z]{1}$";
 
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
 
        if (str == null) 
        {
            return false;
        }
        Matcher m = p.matcher(str);
 
        return m.matches();
    }

    public static boolean isValidPan(String pan)
    {
        String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pan);
        return matcher.matches();
    }
}
