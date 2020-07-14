	package poc.ivt.report;
	
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import java.text.DateFormat;
	import java.text.SimpleDateFormat;
	import java.util.Date;

	import org.apache.poi.ss.usermodel.BorderStyle;
	import org.apache.poi.ss.usermodel.Cell;
	import org.apache.poi.ss.usermodel.CellStyle;
	import org.apache.poi.ss.usermodel.FillPatternType;
	import org.apache.poi.ss.usermodel.IndexedColors;
	import org.apache.poi.ss.usermodel.Row;
	import org.apache.poi.xssf.usermodel.XSSFCell;
	import org.apache.poi.xssf.usermodel.XSSFCellStyle;
	import org.apache.poi.xssf.usermodel.XSSFFont;
	import org.apache.poi.xssf.usermodel.XSSFRow;
	import org.apache.poi.xssf.usermodel.XSSFSheet;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	
	public class IVTExcelReport {
	
	public static FileOutputStream fos = null;
	public static FileInputStream fis = null;
	public static XSSFWorkbook workbook = null;
	public static XSSFSheet sheet = null;
	public static XSSFRow row = null;
	public static XSSFCell cell = null;
	
	static DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
	static Date date = new Date();
	static String filePathdate = dateFormat.format(date).toString();
	static File file = new File(".//ExcelReport//"+filePathdate+"IVT.xlsx");
	//public static String XlFilePath = "IVT_"+filePathdate+".xlsx"; //it will generate outside all the folders
	
	//static XSSFFont font;
	//static XSSFCellStyle style;
	
	/*public IVTExcelReport(String XlFilePath) throws Exception
	{
	this.XlFilePath = XlFilePath;
	fis = new FileInputStream(XlFilePath);
	workbook = new XSSFWorkbook(fis);
	fis.close();
	}*/
	
	public static void createExcelSheet() throws Exception {
	
	workbook = new XSSFWorkbook();
	
	//fos = new FileOutputStream("IVT"+filePathdate+".xlsx");
	fos = new FileOutputStream(file);
	XSSFSheet CSVCOMPARISON = workbook.createSheet("CSVCOMPARISON");
	
	Row row = CSVCOMPARISON.createRow(0);
	
	//Create header CellStyle
	 XSSFFont headerFont = workbook.createFont();
	 headerFont.setColor(IndexedColors.WHITE.index);
	 CellStyle headerCellStyle = CSVCOMPARISON.getWorkbook().createCellStyle();
	 // fill foreground color ...
	 headerCellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.index);
	 // and solid fill pattern produces solid grey cell fill
	 headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 
	 //to set Border to the cell
	 headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
	 headerCellStyle.setBorderLeft(BorderStyle.DOUBLE);
	 headerCellStyle.setBorderRight(BorderStyle.THICK);
	 headerCellStyle.setBorderTop(BorderStyle.DASHED);
	 
	 headerCellStyle.setFont(headerFont);
	
	Cell Record_No = row.createCell(0);
	Record_No.setCellStyle(headerCellStyle);
	Record_No.setCellValue("Record_No");
	
	Cell MSISDN_Number = row.createCell(1);
	MSISDN_Number.setCellStyle(headerCellStyle);
	MSISDN_Number.setCellValue("MSISDN_Number");
	
	Cell Cusomer_Account_Number = row.createCell(2);
	Cusomer_Account_Number.setCellStyle(headerCellStyle);
	Cusomer_Account_Number.setCellValue("Account_Number");
	
	Cell Bundle_NonBundle = row.createCell(3);
	Bundle_NonBundle.setCellStyle(headerCellStyle);
	Bundle_NonBundle.setCellValue("Bundle_NonBundle");
	
	Cell Variable_Name = row.createCell(4);
	Variable_Name.setCellStyle(headerCellStyle);
	Variable_Name.setCellValue("Variable_Name");
	
	Cell Actual_Legacy = row.createCell(5);
	Actual_Legacy.setCellStyle(headerCellStyle);
	Actual_Legacy.setCellValue("Legacy_Value");
	
	Cell Expected_Latest = row.createCell(6);
	Expected_Latest.setCellStyle(headerCellStyle);
	Expected_Latest.setCellValue("Latest_Value");
	
	Cell Date = row.createCell(7);
	Date.setCellStyle(headerCellStyle);
	Date.setCellValue("Date");
	
	Cell Time = row.createCell(8);
	Time.setCellStyle(headerCellStyle);
	Time.setCellValue("Time");
	
	
	workbook.write(fos);
	
	System.out.println("Sheets and Columns Have been Created successfully");
	}
	
	public static void createMissingMobileNumbers() throws Exception
	{
	//workbook = new XSSFWorkbook();
	
	fos = new FileOutputStream(file);
	
	XSSFSheet MISSING_MSISDN = workbook.createSheet("MISSING_MSISDN");
	
	Row row = MISSING_MSISDN.createRow(0);
	
	//Create header CellStyle
	 XSSFFont headerFont = workbook.createFont();
	 headerFont.setColor(IndexedColors.WHITE.index);
	 CellStyle headerCellStyle = MISSING_MSISDN.getWorkbook().createCellStyle();
	 // fill foreground color ...
	 headerCellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.index);
	 // and solid fill pattern produces solid grey cell fill
	 headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 
	 //to set Border to the cell
	 headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
	 headerCellStyle.setBorderLeft(BorderStyle.DOUBLE);
	 headerCellStyle.setBorderRight(BorderStyle.THICK);
	 headerCellStyle.setBorderTop(BorderStyle.DASHED);
	 
	 headerCellStyle.setFont(headerFont);
	
	Cell MSISDN_NUMBERS = row.createCell(0);
	MSISDN_NUMBERS.setCellStyle(headerCellStyle);
	MSISDN_NUMBERS.setCellValue("MSISDN_NUMBERS");
	
	workbook.write(fos);
	
	System.out.println("Missing Mobile Numbers Sheets and Columns Have been Created successfully");
	
	}
	
	public static void createMissingCDR() throws Exception
	{
	//workbook = new XSSFWorkbook();
	
	fos = new FileOutputStream(file);
	
	XSSFSheet CDR_DETAILS = workbook.createSheet("CDR_DETAILS");
	
	Row row = CDR_DETAILS.createRow(0);
	
	//Create header CellStyle
	 XSSFFont headerFont = workbook.createFont();
	 headerFont.setColor(IndexedColors.WHITE.index);
	 CellStyle headerCellStyle = CDR_DETAILS.getWorkbook().createCellStyle();
	 // fill foreground color ...
	 headerCellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.index);
	 // and solid fill pattern produces solid grey cell fill
	 headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 
	 //to set Border to the cell
	 headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
	 headerCellStyle.setBorderLeft(BorderStyle.DOUBLE);
	 headerCellStyle.setBorderRight(BorderStyle.THICK);
	 headerCellStyle.setBorderTop(BorderStyle.DASHED);
	 
	 headerCellStyle.setFont(headerFont);
	
	Cell MSISDN_NUMBER = row.createCell(0);
	MSISDN_NUMBER.setCellStyle(headerCellStyle);
	MSISDN_NUMBER.setCellValue("MSISDN_NUMBER");
	
	Cell BUNDLE_NONBUNDLE = row.createCell(1);
	BUNDLE_NONBUNDLE.setCellStyle(headerCellStyle);
	BUNDLE_NONBUNDLE.setCellValue("BUNDLE_NONBUNDLE");
	
	Cell LEGACY_COUNT = row.createCell(2);
	LEGACY_COUNT.setCellStyle(headerCellStyle);
	LEGACY_COUNT.setCellValue("LEGACY_COUNT");
	
	Cell LATEST_COUNT = row.createCell(3);
	LATEST_COUNT.setCellStyle(headerCellStyle);
	LATEST_COUNT.setCellValue("LATEST_COUNT");
	
	workbook.write(fos);
	
	System.out.println("Missing Mobile Numbers Sheets and Columns Have been Created successfully");
	
	}
	
	
	public static void setCellValues(String sheetName, int rowNum, int cellNum,String data) throws Exception
	{
	
	sheet = workbook.getSheet(sheetName);
	
	row = sheet.getRow(rowNum);
	if(row == null)
	row = sheet.createRow(rowNum);
	
	cell = row.getCell(cellNum);
	if(cell == null)
	cell = row.createCell(cellNum);
	
	
	// XSSFFont cell_font = workbook.createFont();
	XSSFCellStyle cell_style = workbook.createCellStyle();
	cell_style.setBorderBottom(BorderStyle.MEDIUM);
	cell_style.setBorderLeft(BorderStyle.MEDIUM);
	cell_style.setBorderRight(BorderStyle.MEDIUM);
	cell_style.setBorderTop(BorderStyle.MEDIUM);
	cell.setCellStyle(cell_style);
	cell.setCellValue(data);
	
	fos = new FileOutputStream(file);
	workbook.write(fos);
	fos.close();
		}
	}
