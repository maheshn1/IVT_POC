package poc.ivt.ivtCore;

import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.Properties;

public class Calculations {

	static String propFile="C:\\\\workspace\\\\IVT_POC\\\\sourceFile.properties";
	
	
	 public static double calculateTax(String x) throws Exception{
		 FileReader fr = new FileReader(propFile);
		 Properties prop = new Properties();
		 prop.load(fr);
		 String gstper = prop.getProperty("gst"); 
		 Double taxper = Double.parseDouble(gstper);
		 	
		 	double taxAmt=0.00;
	        DecimalFormat df = new DecimalFormat("0.00");
	        double d = Double.parseDouble(x);
	        taxAmt = (d+(d*taxper/100.00));
	        taxAmt=(double)Math.round(taxAmt*100d)/100d;
	        //System.out.println("Tax Amount after round off is : "+taxAmt);
	        return taxAmt;
	    }
	
	
	public static double calculateFamilyFriendsTax(String str)
	{
		double gst = 30.00;
		double taxamt = 0.00;
		DecimalFormat df = new DecimalFormat("0.00");
		double d = Double.parseDouble(str);
		taxamt = d*(gst/100);
		System.out.println("The family and Friends Discount tax: "+taxamt);
		return taxamt;
		
	}
	
	public static void main(String[] args) throws Exception {

		calculateTax("18");
		calculateFamilyFriendsTax("14.54");
	}

}
