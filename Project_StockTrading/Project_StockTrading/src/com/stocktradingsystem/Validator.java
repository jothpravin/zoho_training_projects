package com.stocktradingsystem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator
{
	public static boolean emailValidator(String email)
	{
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
        					+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        	
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

    public static boolean isValidPan(String pan)
    {
        String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pan);
        return matcher.matches();
    }

    public static boolean isValidAadhar(long aadhar_no)
    {
        String regex = "^[2-9]{1}[0-9]{11}$";
        String aadhar = Long.toString(aadhar_no);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(aadhar);
        return matcher.matches();
    }
}

