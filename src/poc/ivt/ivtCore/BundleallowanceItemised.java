package poc.ivt.ivtCore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import poc.ivt.report.IVTExcelReport;

public class BundleallowanceItemised {
    Files fm = new Files();
    static String propFile="C:\\workspace\\IVT_POC\\sourceFile.properties";
    Util gu = new Util();
    
    public void compareBundleAllowance(String legacyPkey,String lfp, String mpDoc, String cfp) throws IOException {
        Properties prop = new Properties();
        FileReader fr = new FileReader(propFile);
        prop.load(fr);
        String mapDoc = mpDoc, currentFilePath = cfp,legacyFilePath=lfp,lpKey=legacyPkey,legacyDate=null,legacyTime=null,latestDate=null,latestTime=null;
        String strMp[] = null,lstr[] = null,cstr[] =null;
        String keyLine = null, line = null, mpline = null, cline = null;
        int fileLineCount = 0, valueDiffCount = 0,i=0,j=0,diffCount=0;
        String format="%-20s %5d\n";
        boolean fileCountFlag;
        String primaryKey=null,secodaryKey=null,lpk=null,lsk=null,cpk=null,csk=null;
        Set legacyKeySet=null,latestKeySet=null;
        primaryKey=prop.getProperty("primaryKey"); secodaryKey=prop.getProperty("secondaryKey");
        Util gu = new Util();
        BufferedReader lfr,cfr,mdoc;
        HashMap<String, String> f1 = new HashMap<String, String>();
        HashMap<String,String> legacyMap = new HashMap<String,String>();
        HashMap<String,String> currentMap = new HashMap<String,String>();
        ArrayList<String> datetime = new ArrayList<String>(); 

        System.out.println("\n"+"Comparing Bundle Allowance......."+"\n");
        try {
            lfr = new BufferedReader(new FileReader(legacyFilePath));
            cfr = new BufferedReader(new FileReader(currentFilePath));
            mdoc = new BufferedReader(new FileReader(mapDoc));
            if ((mpline = mdoc.readLine()) != null) {
                keyLine = mpline;
            }
            strMp= keyLine.split(",");
            fileCountFlag = fm.countofLines(lfp, cfp);
            fileCountFlag = true;
            if (fileCountFlag) {
               while (((line = lfr.readLine()) != null) && ((cline = cfr.readLine()) !=null)){
                    lpk = gu.getPK(legacyFilePath,line, mapDoc, primaryKey);
                    lsk = gu.getPK(legacyFilePath,line, mapDoc, secodaryKey);
                    cpk = gu.getPK(currentFilePath,cline, mpDoc, primaryKey);
                    csk = gu.getPK(currentFilePath,cline, mpDoc, secodaryKey);
                   
                    if((lpKey.equals(lpk)) && (lpKey.equals(cpk))){
                        lstr = line.split(",");
                        cstr= cline.split(",");
                        legacyMap=gu.csvFileAddToMap(legacyFilePath,mpDoc,line);
                        currentMap=gu.csvFileAddToMap(currentFilePath,mpDoc,cline);                        
                                 for(String k : legacyMap.keySet()){                                                                                                               
                                String legMapVal = legacyMap.get(k);
                                String curMapVal = currentMap.get(k);                               
                                if(!legMapVal.equals(curMapVal)) {
                                            datetime=getDateTimeVal(k,legacyMap);                                           
                                                            legacyDate = datetime.get(0);
                                                            legacyTime= datetime.get(1);                                                  
                                            
                                   // System.out.println(++diffCount + ":\t" +lpk +":\t" + k +"\t"+ legMapVal + "\t" + currentMap.get(k));
                                              System.out.println(++diffCount + ":\t" +lpk +":\t" + k + ":\t" +legacyDate +":\t"+legacyTime+":\t"+ legMapVal + ":\t" + currentMap.get(k));
                                }
                            }
                        }
                    }
                } else System.out.println("files are of differnet size of records! cannot continue");            
        }catch(Exception e){
            System.out.println("Exception: "+e);
        }
    }    

public ArrayList<String> getDateTimeVal(String keyMapHeader, HashMap<String,String> legacyMp) {
                HashMap<String,String> legcyMap = new HashMap<String,String>();       
                ArrayList<String> datetim = new ArrayList<String>(); 
                legcyMap=legacyMp;
                String keyMapHdr = keyMapHeader;
                String legacyDate=null,legacyTime=null; 
               if(keyMapHdr.contains("VC")) {
                               legacyDate=legacyMp.get("date(VC)");
                               legacyTime=legacyMp.get("time(VC)");                                                
               }else if(keyMapHdr.contains("CR-EU")) {
                               legacyDate=legacyMp.get("date(VC)");
                               legacyTime=legacyMp.get("time(VC)");                                                
               }else if(keyMapHdr.contains("RC-EU")) {
                               legacyDate=legacyMp.get("date(VC)");
                               legacyTime=legacyMp.get("time(VC)");
               }else if(keyMapHdr.contains("TM")) {
                               legacyDate=legacyMp.get("date(VC)");
                               legacyTime=legacyMp.get("time(VC)");
               }else if(keyMapHdr.contains("UKBND")) {
                               legacyDate=legacyMp.get("date(VC)");
                               legacyTime=legacyMp.get("time(VC)");
               }else if(keyMapHdr.contains("EUBND")) {
                               legacyDate=legacyMp.get("date(VC)");
                               legacyTime=legacyMp.get("time(VC)");
               }
               datetim.add(legacyDate);
               datetim.add(legacyTime);
               return datetim;
}


    public void compareBundleAllowanceItr(String legacyPkey,String lfp, String mpDoc, String cfp) throws IOException {
                Properties prop = new Properties();
                FileReader fr = new FileReader(propFile);
                prop.load(fr);
                String mapDoc = mpDoc, currentFilePath = cfp,legacyFilePath=lfp,lpKey=legacyPkey,legacyDate=null,legacyTime=null,latestDate=null,latestTime=null;
                String strMp[] = null,lstr[] = null,cstr[] =null;
                String keyLine = null, line = null, mpline = null, cline = null;
                int fileLineCount = 0, valueDiffCount = 0,i=0,j=0,diffCount=0;
                String format="%-20s %5d\n";
                boolean fileCountFlag;
                String primaryKey=null,secodaryKey=null,lpk=null,lsk=null,cpk=null,csk=null,legMapVal=null,curMapVal=null;
                Set legacyKeySet=null,latestKeySet=null;
                primaryKey=prop.getProperty("primaryKey"); secodaryKey=prop.getProperty("secondaryKey");
                
                BufferedReader lfr,cfr,mdoc;
                HashMap<String, String> f1 = new HashMap<String, String>();
                HashMap<String,String> legacyMap = new HashMap<String,String>();
                HashMap<String,String> currentMap = new HashMap<String,String>();
                ArrayList<String> ldatetime = new ArrayList<String>();
                ArrayList<String> cdatetime = new ArrayList<String>();
          System.out.println("___________________________________________________________________________");
                System.out.println("\n"+"Comparing Bundle Allowance......."+"\n");
                try {
                                lfr = new BufferedReader(new FileReader(legacyFilePath));
                                cfr = new BufferedReader(new FileReader(currentFilePath));
                                mdoc = new BufferedReader(new FileReader(mapDoc));
                                if ((mpline = mdoc.readLine()) != null) {
                                                keyLine = mpline;
                                }
                                int lpkCount=0,cpkCount=0;
                                strMp= keyLine.split(",");
                                
                                lpkCount=getCount(lfp,keyLine,legacyPkey);
                                                cpkCount=getCount(cfp,keyLine,legacyPkey);
                                                System.out.println("MSISDN:-"+legacyPkey+"--count in Legacy :- "+lpkCount+" --Latest :-"+cpkCount+"\n");                                          
                                if (lpkCount == cpkCount) {                                          
                                                while (((line = lfr.readLine()) != null)){
                                                                lpk = gu.getPK(legacyFilePath,line, mapDoc, primaryKey);
                                                                lsk = gu.getPK(legacyFilePath,line, mapDoc, secodaryKey); 
                                                                legacyMap=gu.csvFileAddToMap(legacyFilePath,mpDoc,line);
                                                                if(lpKey.equals(lpk)) {
                                                                 while  ((cline = cfr.readLine()) !=null) {
                                                                 cpk = gu.getPK(currentFilePath,cline, mpDoc, primaryKey);
                                                                csk = gu.getPK(currentFilePath,cline, mpDoc, secodaryKey);
                                                                   if ((lpKey.equals(cpk)) && (lpk.equals(cpk))){                                                                                                                                                                                                                                                                                                                                       
                                                                    currentMap=gu.csvFileAddToMap(currentFilePath,mpDoc,cline);  
                                                                  if (currentMap.keySet().equals(legacyMap.keySet())){
                                                                   for(String k : legacyMap.keySet()){                                                                                                                           
                                                                legMapVal = legacyMap.get(k);
                                                                curMapVal = currentMap.get(k);  
                                                                if(!legMapVal.equals(curMapVal)) {
                                                                ldatetime=getDateTimeVal(k,legacyMap);
                                                                cdatetime=getDateTimeVal(k,currentMap);
                                                                legacyDate = ldatetime.get(0);
                                                                legacyTime= ldatetime.get(1);
                                                                latestDate = cdatetime.get(0);
                                                                latestTime= cdatetime.get(1);    
                                                                //System.out.println("ld  "+legacyDate+"  lt  "+legacyTime);
                                                                //System.out.println("cd  "+latestDate+"  ct  "+latestTime);
                                                                //if(legacyDate.equals(latestDate) && (legacyTime.equals(latestTime))) {
                                                                System.out.println(++diffCount + ":\t" +lpk +":\t" + k + ":\t" +legacyDate +":\t"+legacyTime+":\t"+ legMapVal + ":\t" + currentMap.get(k));
                                                                //To excel report
                                                                IVTExcelReport.setCellValues("CSVCOMPARISON",Files.field_row, Files.BUNDLE_NUMBER,"BUNDLE");
                                                                IVTExcelReport.setCellValues("CSVCOMPARISON",Files.field_row,Files.DATE_NUMBER,legacyDate);
                                                                IVTExcelReport.setCellValues("CSVCOMPARISON",Files.field_row,Files.TIME_NUMBER,legacyTime);
                                                                IVTExcelReport.setCellValues("CSVCOMPARISON", Files.field_row++,Files.FIELD_NUMBER,k);
                                                                IVTExcelReport.setCellValues("CSVCOMPARISON",Files.ac_row++,Files.ACTUAL_NUMBER,legMapVal);
                                                                IVTExcelReport.setCellValues("CSVCOMPARISON",Files.ex_row++,Files.EXPECTED_NUMBER,curMapVal);
                                                                                                         
                                                                }}}//}                                                                                                                                                                                                                                      
                                                              break;}}// next cpk cfr.close();cfr=new BufferedReader(new FileReader(currentFilePath));                                                                      
                                                        }} // next lpk                                       
                                } else {
                                                getMissingBundleAllowance(lpKey,legacyFilePath, currentFilePath,mapDoc);                                                                                        
                                                }            
                System.out.println("___________________________________________________________________________");}catch(Exception e){
                                System.out.println("Exception: "+e);}
                }

    public int getCount(String filePath,String mpline, String mobileNum ) throws IOException {
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                String line=null;
                String[] lr=null; 
                int mobileNumCount=0;               
                while((line = br.readLine())!=null) {                           
                                if(line.contains(mobileNum)) {
                                                mobileNumCount++;
                                }              
    }return mobileNumCount;}
    
    public void getMissingBundleAllowance(String legacyPkey,String lfp,String cfp,String keyMap) throws Exception {                    
                Properties prop = new Properties();
                FileReader fr = new FileReader(propFile);
                prop.load(fr);
                String mapDoc = keyMap, currentFilePath = cfp,legacyFilePath=lfp,lpKey=legacyPkey,legacyDate=null,legacyTime=null,latestDate=null,latestTime=null;
                String strMp[] = null,lstr[] = null,cstr[] =null;
                String keyLine = null, line = null, mpline = null, cline = null,mapValFlag = null;;
                int fileLineCount = 0, valueDiffCount = 0,i=0,j=0,diffCount=0,lpkCount=0,cpkCount=0;
                String primaryKey=null,secodaryKey=null,lpk=null,lsk=null,cpk=null,csk=null,legMapVal=null,curMapVal=null;
                Set legacyKeySet=null,latestKeySet=null;
                primaryKey=prop.getProperty("primaryKey"); secodaryKey=prop.getProperty("secondaryKey");                  
                BufferedReader lfr,cfr,mdoc;
                HashMap<String, String> f1 = new HashMap<String, String>();
                HashMap<String,String> legacyMap = new HashMap<String,String>();
                HashMap<String,String> currentMap = new HashMap<String,String>();
                ArrayList<String> ldatetime = new ArrayList<String>();
                ArrayList<String> cdatetime = new ArrayList<String>();
          System.out.println("___________________________________________________________________________");
                System.out.println("\n"+"MSISDN Numbers missing Bundle Allowance......."+"\n");            
                                lfr = new BufferedReader(new FileReader(legacyFilePath));
                                cfr = new BufferedReader(new FileReader(currentFilePath));
                                mdoc = new BufferedReader(new FileReader(mapDoc));
                                if ((mpline = mdoc.readLine()) != null) {
                                                keyLine = mpline;}                                                           
                                strMp= keyLine.split(",");             
                                lpkCount=getCount(lfp,keyLine,legacyPkey); cpkCount=getCount(cfp,keyLine,legacyPkey);
                                                System.out.println("MSISDN:-"+legacyPkey+"--count in Legacy :- "+lpkCount+" --Latest :-"+cpkCount+"\n"); //excel entry
                                                // To excel report
                                                IVTExcelReport.setCellValues("CDR_DETAILS",Files.cdr_bundlerow++ ,Files.BUNDLE_NONBUNDLE,"BUNDLE");
                                                IVTExcelReport.setCellValues("CDR_DETAILS",Files.cdr_row1++ ,Files.MSISDN_CDR,legacyPkey);
                                                IVTExcelReport.setCellValues("CDR_DETAILS",Files.cdr_row2++ ,Files.LEGACY_CDR ,Integer.toString(lpkCount));
                                                IVTExcelReport.setCellValues("CDR_DETAILS",Files.cdr_row3++ ,Files.LATEST_CDR ,Integer.toString(cpkCount));
                                                
                                                if(lpkCount>cpkCount)  mapValFlag="legacy";     else mapValFlag="latest";                                            
                                if (lpkCount != cpkCount) {
                                                while((line=lfr.readLine())!=null) {
                                                                while((cline=cfr.readLine())!=null) { 
                                                                                if(mapValFlag.equals("legacy")) {
                                                                                                if((line.contains(lpKey)) && (!cline.contains(lpKey))) {
                                                                                                        //        System.out.println("legacy Bundle:-"+line);
                                                                                                	}
                                                                                                                break;
                                                                                }else if(mapValFlag.equals("latest")) {
                                                                                               if((cline.contains(lpKey)) && (!line.contains(lpKey))) {
                                                                                                           //    System.out.println("latest Bundle:-"+cline);
                                                                                            	   }}                                                                                                                                                      
                                                                }}}}                                                                          
                                
                                                               
 
}
 
