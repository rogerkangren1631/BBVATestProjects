
package utilities;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	private static XSSFWorkbook ExcelWBook;
	private static XSSFSheet ExcelWSheet;

	/*
	 * 設置文件路徑， 打開Excel 文件
	 * 
	 * @params - Excel 路徑 and 表單名字
	 */
	public static void setExcelFile(String path, String sheetName) throws Exception {
		try {
			// 打開Excel 文件
			FileInputStream ExcelFile = new FileInputStream(path);

			// 訪問Êexcel 表
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
		} catch (Exception e) {
			throw (e);
		}
	}

	public static String[][] getTestData(String tableName) {
		String[][] testData = null;

		try {
			// 
			DataFormatter formatter = new DataFormatter();
			//
			XSSFCell[] boundaryCells = findCells(tableName);
			// µ¥Ôª¸ñ¿ªÊ¼´¦
			XSSFCell startCell = boundaryCells[0];
			// 
			XSSFCell endCell = boundaryCells[1];

			// 
			int startRow = startCell.getRowIndex() + 1;
			int endRow = endCell.getRowIndex() - 1;
			int startCol = startCell.getColumnIndex() + 1;
			int endCol = endCell.getColumnIndex() - 1;

			testData = new String[endRow - startRow + 1][endCol - startCol + 1];

			for (int i = startRow; i < endRow + 1; i++) {
				for (int j = startCol; j < endCol + 1; j++) {
					// testData[i-startRow][j-startCol] =
					// ExcelWSheet.getRow(i).getCell(j).getStringCellValue();
					// È¡Ã¿Ò»ÐÐÀïÃæÃ¿Ò»ÁÐµÄÖµ
					Cell cell = ExcelWSheet.getRow(i).getCell(j);
					testData[i - startRow][j - startCol] = formatter.formatCellValue(cell);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 
		return testData;
	}

	public static XSSFCell[] findCells(String tableName) {
		DataFormatter formatter = new DataFormatter();
		String pos = "begin";
		XSSFCell[] cells = new XSSFCell[2];

		for (Row row : ExcelWSheet) {
			for (Cell cell : row) {
				// if (tableName.equals(cell.getStringCellValue())) {
				if (tableName.equals(formatter.formatCellValue(cell))) {
					if (pos.equalsIgnoreCase("begin")) {
						// 
						cells[0] = (XSSFCell) cell;
						pos = "end";
					} else {
						// 
						cells[1] = (XSSFCell) cell;
					}
				}
			}
		}
		// ·µ»Øµ¥Ôª¸ñÊý×é
		return cells;
	}
}
