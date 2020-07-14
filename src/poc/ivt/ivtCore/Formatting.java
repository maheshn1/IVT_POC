package poc.ivt.ivtCore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Formatting {
	
	public static String convertToLowerCase(String s)
	{
		String str;
		str = s.toLowerCase();
		//System.out.println("Lower case STring is:"+str);
		return str;
	}
	
	public static String convertToUpperCase(String s)
	{
		String str;
		str = s.toUpperCase();
		//System.out.println("The Upper Case Strings are:"+str);
		return str;
	}
	
	public static String convertIntegerToString(int a)
	{
		String str;
		str = Integer.toString(a);
		//System.out.println("Integer Value: "+str);
		return str;
	}
	
	public static double convertIntToDouble(int a)
	{
		double d ;
		
		//1 Way
		d = a;
		System.out.println("Double value: "+d);
		
		//2 way
		Double d1 = new Double(a);
		System.out.println("The Double value is: "+d1);
		return d1;
	}
	
	public static int convertdoubleToInteger(double d)
	{
		//1.Method, just truncating values after the decimal point.
		int intVal = (int) d;
		System.out.println("The integer value is: "+intVal);
		
		//2. Method, rounding off to nearest integer number
		int intVal1 = (int) Math.round(d);
		System.out.println("After Rounding value is: "+intVal1);
		return intVal1;
	}
	
	public static String addressSplitUp(String address)
	{
		ArrayList<String> li = new ArrayList<String>();
		String str[] = address.split(" ");
		for(String s : str)
		{
			li.add(s);
			//System.out.println("The Address are : "+s);
		}
		System.out.println(li);
		int len = li.size();
		System.out.println("The length of the Address is: "+len);
		
		String no = li.get(0);
		String pincode = li.get(len-1);
		StringBuilder street = new StringBuilder();
		for(int i=1;i<len-1;i++)
		{
			street = street.append(li.get(i));
			street.append(" ");
			//street.replace("", " ").trim();
		}
			
		System.out.println("The Door Number is: "+no);
		System.out.println("The street is: "+street);
		System.out.println("The pincode is: "+pincode);
		
		return (no+street+pincode);
	}
	
	public static String convertDateFormat(String str) throws Exception
	{
		DateFormat originalFormat,targetformat;
		Date d = new Date();
		String newDate = null;
		if(str.matches("\\d{2}-\\d{2}-\\d{4}")) 
		{
			originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
			targetformat = new SimpleDateFormat("dd MMM yyyy", Locale.UK);
			d = originalFormat.parse(str);
			newDate = targetformat.format(d);
			System.out.println(newDate);
		}
		else if(str.matches("\\d{4}-\\d{2}-\\d{2}"))
		{
			originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
			targetformat = new SimpleDateFormat("dd MMM yyyy", Locale.UK);
			d = originalFormat.parse(str);
			newDate = targetformat.format(d);
			System.out.println(newDate);
		}
		else if(str.matches("^[0-3]?[0-9]/[0-1]?[0-9]/(?:[0-9]{2})?[0-9]{2}$")) 
		{
			originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
			targetformat = new SimpleDateFormat("dd MMM yyyy", Locale.UK);
			d = originalFormat.parse(str);
			newDate = targetformat.format(d);
			System.out.println(newDate);
		}
		else if(str.matches("^[0-3]?[0-9]/(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)/(?:[0-9]{2})?[0-9]{2}$"))
		{
			originalFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.UK);
			targetformat = new SimpleDateFormat("dd MMM yyyy", Locale.UK);
			d = originalFormat.parse(str);
			newDate = targetformat.format(d);
			System.out.println(newDate);
		}
		else if(str.matches("^([0-3]?[0-9])(\\s)(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)(\\s)(?:[0-9]{2})?[0-9]{2}$"))
		{
			originalFormat = new SimpleDateFormat("dd MMM yyyy", Locale.UK);
			targetformat = new SimpleDateFormat("dd MMM yyyy", Locale.UK);
			d = originalFormat.parse(str);
			newDate = targetformat.format(d);
			System.out.println(newDate);
		}else 
		{
			System.out.println("The format of Date is not proper:");
		}
			
		return newDate;
	}	
	
	public static String extractDate(String str) throws Exception
	{
		DateFormat original = new SimpleDateFormat("dd MMM yyyy - HH:mm:ss",Locale.UK);
		DateFormat target = new SimpleDateFormat("dd MMM yyyy",Locale.UK);
		Date d = new Date();
		d = original.parse(str);
		String newDate = target.format(d);
		System.out.println(newDate);
		return newDate;
	}
	
	public static void extractdigitsFromString(String str)
	{
		String target;
		
		target = str.replaceAll("[^0-9]","");
		System.out.println("The extracted digits from string is:"+target);
		
		//str = str.replaceAll("\\D+","");
		
	}
		
	public static void main(String[] args) throws Exception {
		
		//convertToLowerCase("Hello World Example");
		//convertToUpperCase("hi bangalore");
		//convertIntegerToString(1234);
		//convertIntToDouble(200);
		//convertdoubleToInteger(3.654);
		//addressSplitUp("78 Newton Berkley Street 890345");
		//convertDateFormat("02-03-2020");
		//extractDate("01 Jan 2020 - 03:10:20");
		extractdigitsFromString("78 new barkely12hill9");
		
	}

}
