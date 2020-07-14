package poc.ivt.ivtCore;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.opencsv.exceptions.CsvException;

import poc.ivt.report.IVTExcelReport;

public class Util {
	
	static String propFile="C:\\\\workspace\\\\IVT_POC\\\\sourceFile.properties";
    String MpDoc = "C:\\workspace\\IVT_POC\\src\\poc\\ivt\\testdata\\mapDoc.csv";
    ArrayList<String> cfrList = new ArrayList<String>();
    static ArrayList<String> missingMobileNumList = new ArrayList<String>();
    static ArrayList<String> primaryKeyList = new ArrayList<String>();
    static double gst = 20.00;
    static double amountDueTill=0.00,amount1=0.00,amount2=0.00,totalAmt=0.00;
    static Files fm = new Files();

    public static HashMap<String,String> csvFileReadNaddToMap(String lfp,String mpDoc) throws IOException {
        String legacyFilePath = lfp;
        String mapDoc=mpDoc;
        String keyLine = null;
        BufferedReader lfr = new BufferedReader(new FileReader(legacyFilePath));
        BufferedReader mdoc = new BufferedReader(new FileReader(mapDoc));

        HashMap<String,String> f1 = new HashMap<String,String>();
        String line,mpline  = null;
        if ((mpline =  mdoc.readLine()) != null) {
            keyLine = mpline;
        }
        while ((line = lfr.readLine())!= null){
            String[] strMp = keyLine.split(",");
            String[] str=line.split(",");
            for (int i=0;i < str.length ;i++){
                f1.put(strMp[i],str[i]);
            }
        }
        return f1;
    }

    public static HashMap<String,String> csvFileAddToMap(String mpDoc,String csvFilLine) throws IOException {
        String mapDoc=mpDoc;
        String csvFileln=csvFilLine;
        String keyLine = null; int csvRecLen=0;

        BufferedReader mdoc = new BufferedReader(new FileReader(mapDoc));
        HashMap<String,String> recordHashMap = new HashMap<String,String>();
        String csvLine= null;

        while (((keyLine = mdoc.readLine())!= null)){
            String strMp[] = keyLine.split(",");
            int klCnt = keyLine.lastIndexOf(",");       
            String[] csvF=csvFileln.split(",");
            int csvColCnt=csvFileln.lastIndexOf(",");           
            for (int i=0;i < strMp.length ;i++){
                recordHashMap.put(strMp[i],csvF[i]);
            }
        }
        return recordHashMap;
    }
    
    public  static HashMap<String,String> csvFileAddToMap(String lfp,String mpDoc,String csvFilLine) throws CsvException, Exception {
        String mapDoc=mpDoc;
        String csvFileln=csvFilLine;
        String keyLine = null; int csvRecLen=0;

        BufferedReader mdoc = new BufferedReader(new FileReader(mapDoc));
        HashMap<String,String> recordHashMap = new HashMap<String,String>();
        String csvLine= null;
        csvRecLen=Files.csvRead(lfp);

        while (((keyLine = mdoc.readLine())!= null)){
            String strMp[] = keyLine.split(",");
            int klCnt = keyLine.lastIndexOf(",");       
            String[] csvF=csvFileln.split(",");
            int csvColCnt=csvFileln.lastIndexOf(",");           
            for (int i=0;i < csvRecLen ;i++){
                recordHashMap.put(strMp[i],csvF[i]);
            }
        }
        return recordHashMap;
    }

    
    public static String readHashMap(HashMap<String, String> recordHashMap,String keyVal) {
        HashMap<String, String> recordMap = new HashMap<String, String>();
        Object key,value;
        String objVal=null;
        recordMap=recordHashMap;
        /* Display content using Iterator*/
        Iterator itr = recordMap.entrySet().iterator();
        while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            while(itr.hasNext()){
                if (recordMap.containsKey(keyVal)){
                    Map.Entry mapElement = (Map.Entry) itr.next();
                    key = recordMap.keySet();
                    value= recordMap.get(keyVal);
                    //   System.out.println("Value:-"+value);
                    objVal= (String) value;
                }
            }

        }return objVal;
    }

    public static String getPK(String csvLine,String mpDoc, String primarKey) throws IOException {
        HashMap<String,String> rmap = new HashMap<String,String>();
        String csvFileLine = csvLine;String mapDoc = mpDoc; String primaryKey=primarKey;
        rmap= csvFileAddToMap(mapDoc,csvFileLine);
        primaryKey=readHashMap(rmap,primarKey);
        return primaryKey;
    }
    
    public static String getPK(String lfp,String csvLine,String mpDoc, String primarKey) throws CsvException, Exception {
        HashMap<String,String> rmap = new HashMap<String,String>();
        String csvFileLine = csvLine;String mapDoc = mpDoc; String primaryKey=primarKey;
        rmap= csvFileAddToMap(lfp,mapDoc,csvFileLine);
        primaryKey=readHashMap(rmap,primarKey);
        return primaryKey;
    }

    public static void compareMap(String lLine, String mpDoc, String cLine) throws Exception {
        HashMap<String,String> legacyMap = new HashMap<String,String>();
        HashMap<String,String> currentMap = new HashMap<String,String>();
        legacyMap= csvFileAddToMap(mpDoc,lLine);
        currentMap = csvFileAddToMap(mpDoc,cLine);
        int diffCount=0; boolean b;
        Object lkey,lvalue,ckey,cvalue;
        if (currentMap.keySet().equals(legacyMap.keySet())){
            for(String k : legacyMap.keySet()){
                // System.out.println("..."+k+"...."+currentMap.get(k));
                String legMapVal = legacyMap.get(k);
                String curMapVal = currentMap.get(k);
                                                             
                b = Format.checkStringIsNumeric(legMapVal);
              
                if((k.equals("total before vat"))||(k.equals("cost allowance"))||(k.equals("cost")))
                {
                    String x=legacyMap.get(k);
                    amount1=calcTax(x);
                    legMapVal=Double.toString(amount1);
                }
                if(!legMapVal.toUpperCase().equals(curMapVal.toUpperCase())){
                    //System.out.println(k+"\t"+legacyMap.get(k)+"\t"+currentMap.get(k));
                    System.out.println(++diffCount +":\t"+k+"\t"+legMapVal+"\t"+curMapVal);
                   
                    //To display in excel variable, actual  and Expected values
                    IVTExcelReport.setCellValues("CSVCOMPARISON",Files.field_row, Files.BUNDLE_NUMBER,"SUMMARY");
                    IVTExcelReport.setCellValues("CSVCOMPARISON",Files.field_row++,Files.FIELD_NUMBER,k);
                    IVTExcelReport.setCellValues("CSVCOMPARISON",Files.ac_row++,Files.ACTUAL_NUMBER,legMapVal);
                    IVTExcelReport.setCellValues("CSVCOMPARISON",Files.ex_row++,Files.EXPECTED_NUMBER,curMapVal);
                   
                }
            };diffCount=0;
        }
    }
    public static double calcTax(String x){
        double taxAmt=0.00;
        DecimalFormat df = new DecimalFormat("0.00");
        double d = Double.parseDouble(x);
        taxAmt = (d+(d*gst/100.00));
        taxAmt=(double)Math.round(taxAmt*100d)/100d;
        //   System.out.println("round off : "+taxAmt);
        return taxAmt;
    }


    public ArrayList<String> csvCompareMissing(String lfp, String mpDoc, String cfp) throws IOException {
        String legacyFilePath = lfp;
        String mapDoc = mpDoc;
        String currentFilePath = cfp;
        String format="%-20s %5d\n";
        boolean fileCountFlag;
        String primaryKey=null,secodaryKey=null,lpk=null,lsk=null,cpk=null,csk=null;
        FileReader frm = new FileReader(propFile);
        Properties prop;
        prop = new Properties();
        prop.load(frm);
        primaryKey= prop.getProperty("primaryKey");
        secodaryKey=prop.getProperty("SecondaryKey");
        int i=0,j=0;
        Util gu = new Util();
        String keyLine = null, line = null, mpline = null, cline = null;
        try {
            BufferedReader lfr = new BufferedReader(new FileReader(legacyFilePath));
            BufferedReader cfr = new BufferedReader(new FileReader(currentFilePath));
            BufferedReader mdoc = new BufferedReader(new FileReader(mapDoc));
            HashMap<String, String> f1 = new HashMap<String, String>();
            int fileLineCount = 0, valueDiffCount = 0;
            if ((mpline = mdoc.readLine()) != null) {
                keyLine = mpline;
            }
            fileCountFlag = fm.countofLines(lfp, cfp);
            fileCountFlag = true;
            if (fileCountFlag) {
                while((cline = cfr.readLine()) != null) {
                    String cstr[] = cline.split(",");
                    cpk = gu.getPK(currentFilePath,cline, mpDoc, primaryKey);                   
                    primaryKeyList.add(cpk);
                }cfr.close();
                while ((line = lfr.readLine()) != null) {
                    String strMp[] = keyLine.split(",");
                    String lstr[] = line.split(",");
                    lpk = gu.getPK(legacyFilePath,line, mapDoc, primaryKey);                    
                    fileLineCount++;                                                 
                        if(!primaryKeyList.contains(lpk)){
                            missingMobileNumber(lpk);
                        }
                }lfr.close();
            } else System.out.println("files are of differnet size of records! cannot continue");
        }catch(Exception e){
            System.out.println("Exception: "+e);
        } //listMissingMobileNumber(missingMobileNumList);
        return missingMobileNumList;
    	}

    public static void missingMobileNumber(String missingMobileLpk){
        missingMobileNumList.add(missingMobileLpk);
    }
    public static void listMissingMobileNumber(ArrayList<String> missingMobileLpk) throws Exception{
        System.out.println("Missing Mobile Numbers from Legacy File:- ");
        for(String a : missingMobileLpk){
            System.out.println(a);
            IVTExcelReport.setCellValues("MISSING_MSISDN",Files.missing_mobilerow++ ,Files.MISSING_MOBILE_NUMBER , a);
        }
    }

}
