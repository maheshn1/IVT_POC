package poc.ivt.ivtCore;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import poc.ivt.report.IVTExcelReport;

public class CsvCompareMain {
    static Util gu = new Util();
    static Files fm = new Files();

    HashMap<String, String> lmap = new HashMap<String, String>();
    HashMap<String, String> cmap = new HashMap<String, String>();
    static ArrayList<String> missingMobileNumberList = new ArrayList<String>();
    static String laf = null;
    static String keyMap = null, keyICMap = null, csvFile = null;
    static String propFile="C:\\workspace\\IVT_POC\\sourceFile.properties";

    public static void main(String args[]) throws Exception {
    	
    	IVTExcelReport.createExcelSheet();
    	IVTExcelReport.createMissingMobileNumbers();
    	IVTExcelReport.createMissingCDR();
    	
    	Properties prop = new Properties();
        FileReader fr = new FileReader(propFile);
        prop.load(fr);
        fm.csvCompare(prop.getProperty("legacySummaryFile"), prop.getProperty("keySummaryMap"),prop.getProperty("latestSummaryFile"));
        //fm.csvCompare(prop.getProperty("legacyICFile"), prop.getProperty("keyICMap"),prop.getProperty("latestICFile"));
        missingMobileNumberList = gu.csvCompareMissing(prop.getProperty("legacySummaryFile"), prop.getProperty("keySummaryMap"),prop.getProperty("latestSummaryFile"));
        gu.listMissingMobileNumber(missingMobileNumberList);
    }
}
