package poc.ivt.ivtCore;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import poc.ivt.report.IVTExcelReport;

public class Files {
	
	
	static int LINE_NUMBER = 0;
	static int MSDIN_NUMBER = 1;
	static int ACCOUNT_NUMBER = 2;
	static int BUNDLE_NUMBER = 3;
	static int FIELD_NUMBER = 4;
	static int ACTUAL_NUMBER = 5;
	static int EXPECTED_NUMBER = 6;
	static int DATE_NUMBER = 7;
	static int TIME_NUMBER = 8;
	static int MISSING_MOBILE_NUMBER = 0;
	
	static int field_row = 1;
	static int ac_row = 1;
	static int ex_row = 1;
	
	static int missing_mobilerow = 1;
	
	static int MSISDN_CDR = 0;
	static int LEGACY_CDR = 2;
	static int LATEST_CDR = 3;
	static int BUNDLE_NONBUNDLE = 1;
	static int cdr_bundlerow = 1;
	static int cdr_row1 = 1;
	static int cdr_row2 = 1;
	static int cdr_row3 = 1;
	
	
    Properties prop = new Properties();
    static String propFile="C:\\workspace\\IVT_POC\\sourceFile.properties";
   // static xlsxFile xl = new xlsxFile();


    public static String filePath(String filePath) throws FileNotFoundException {
        File f =  new File(filePath);
        String fileFolder = f.getAbsolutePath();
        return fileFolder;
    }

    public static HashMap<String,String> csvFileReadNaddToMap(String lfp, String mpDoc) throws IOException {
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
    public boolean countofLines(String file1, String file2) throws IOException {
        BufferedReader brl = new BufferedReader(new FileReader(file1));
        BufferedReader brc = new BufferedReader(new FileReader(file2));
        String legLine = null, curLine = null;
        int legacyLineCount = 0, curLineCount = 0;
        boolean fileCountFlag = true;
        System.out.println("No. of Records in a CSV File:-  ");
        while ((legLine = brl.readLine()) != null) {
            legacyLineCount++;
        }
        while ((legLine = brc.readLine()) != null) {
            curLineCount++;
        }
        System.out.println("legacy file count= " + legacyLineCount+" current file count= " + curLineCount);
        if (legacyLineCount != curLineCount) {
            fileCountFlag = false;
        }
        return fileCountFlag;
    }

    public void csvCompare(String lfp, String mpDoc, String cfp) throws IOException {
        String mapDoc = mpDoc, currentFilePath = cfp,legacyFilePath=lfp;
        String format="%-20s %5d\n";
        boolean fileCountFlag;
        String primaryKey=null,secodaryKey=null,lpk=null,lsk=null,cpk=null,csk=null;
        FileReader fr = new FileReader(propFile);
        prop.load(fr);
        primaryKey=prop.getProperty("primaryKey");
        secodaryKey=prop.getProperty("secondaryKey");
        int i=0,j=0;
        Util gu = new Util();
        BundleallowanceItemised bi = new BundleallowanceItemised();
        NonBundleAllowance nb = new NonBundleAllowance();
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

            fileCountFlag = countofLines(lfp, cfp);
            fileCountFlag = true;
            if (fileCountFlag) {
                while ((line = lfr.readLine()) != null) {
                    String strMp[] = keyLine.split(",");
                    String lstr[] = line.split(",");
                    lpk = gu.getPK(lfp,line, mapDoc, primaryKey);
                    lsk = gu.getPK(lfp,line, mapDoc, secodaryKey);
                    fileLineCount++;
                    while((cline = cfr.readLine()) != null) {
                        String cstr[] = cline.split(",");
                        cpk = gu.getPK(cfp,cline, mpDoc, primaryKey);
                        csk = gu.getPK(cfp,cline, mpDoc, secodaryKey);
                        if (lpk.equals(cpk)) {
                            System.out.println("***************************************************************************************");
                            System.out.println("------" + fileLineCount + ":: Mobile Number:-" + lpk + "-----Customer Account Number:-" + lsk + "----" + "\n");
                            //Excel report
                            IVTExcelReport.setCellValues("CSVCOMPARISON",ex_row, LINE_NUMBER, Integer.toString(fileLineCount));
                            IVTExcelReport.setCellValues("CSVCOMPARISON", ex_row, MSDIN_NUMBER,lpk);
                            IVTExcelReport.setCellValues("CSVCOMPARISON",ex_row, ACCOUNT_NUMBER,lsk);
                            
                            gu.compareMap(line, mpDoc, cline);
                            bi.compareBundleAllowanceItr(lpk,prop.getProperty("legacyBundleFile"),prop.getProperty("keyBundleMap"),prop.getProperty("latestBundleFile"));
                            nb.compareNonBundleAllowanceItr(lpk,prop.getProperty("legacyNonBundleFile"),prop.getProperty("KeyNonBundleMap"),prop.getProperty("latestNonBundleFile"));
                            //bi.compareBundleAllowance(lpk,prop.getProperty("legacyBundleFile"),prop.getProperty("keyBundleMap"),prop.getProperty("latestBundleFile"));
                            //nb.compareNonBundleAllowance(lpk,prop.getProperty("legacyNonBundleFile"),prop.getProperty("KeyNonBundleMap"),prop.getProperty("latestNonBundleFile"));
                        }
                    }cfr.close();cfr=new BufferedReader(new FileReader(currentFilePath));
                }lfr.close();
            } else System.out.println("files are of differnet size of records! cannot continue");
        }catch(Exception e){
            System.out.println("Exception: "+e);
        }}
    
    public static int csvRead(String csFile) throws IOException, Exception, CsvException {
                FileReader f = new FileReader(csFile);
                CSVReader csv = new CSVReader(f);
                String[] nextRecord; int csvLength=0; 
                
                 while ((nextRecord = csv.readNext()) != null) { 
           //  for (String cell : nextRecord) { 
               // String a = cell;
                csvLength=nextRecord.length;
                List<String[]> records = csv.readAll();
                Iterator<String[]> itr = records.iterator();
               
             //}   
         } 
                 return csvLength;
    }


}


