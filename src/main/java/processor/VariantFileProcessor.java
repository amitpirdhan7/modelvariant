package processor;

import constants.Constant;
import entity.OptionModel;
import entity.VariantModel;
import enums.EAggregates;
import enums.EFileType;
import exception.ProcessorException;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import utils.Utils;

import java.util.*;

/**
 * Created by sumansharma on 13/6/20.
 */
public class VariantFileProcessor extends AFileProcessor {

  private static final String fileType = EFileType.VARIANT.name();
  private static final String prefix = "File Type : " + fileType + ":: ";
  private static String sheetPrefix = prefix + " Sheet Name : %s ::";

  public Boolean process(String configPath)
      throws ProcessorException {
    System.out.println(prefix + "Start Processing of file");
    HSSFWorkbook workbook = getWorkBook(prefix,fileType,configPath);
    processWorkBook(workbook);
    System.out.println(prefix + "Finished Processing of file");
    return true;
  }

  private void processWorkBook(HSSFWorkbook workBook)
      throws ProcessorException {
    System.out.println(prefix + "Start processing of Workbooks");
    String errorMessage = null;
    if(null == workBook) {
      throw new ProcessorException(fileType, "Some error occured in workbook");
    }

    Integer numberOfSheets = workBook.getNumberOfSheets();
    if(null == numberOfSheets || numberOfSheets == 0) {
      errorMessage = "There was no sheet present in Excel files";
      throw new ProcessorException(fileType, errorMessage);
    }

    System.out.println(prefix + "Total number of Sheets Present in excel sheet :: " + numberOfSheets);

    for(int sheetIndex = 0 ; sheetIndex < numberOfSheets ; sheetIndex++) {
      String sheetName = workBook.getSheetName(sheetIndex);
      System.out.println(prefix + "Sheet Name which is fetched from excel file :: " + sheetName);
      EAggregates eAggregates = Utils.valueOfAggregate(sheetName);
      if(null == eAggregates) {
        System.out.println(prefix + "Invalid Sheet Name " + sheetName + " ,Doesn't match with any aggregates, hence ignoring this sheet and continuing with other sheets");
        continue;
      }
      HSSFSheet sheet = workBook.getSheetAt(sheetIndex);
      processSheet(sheetName, sheet);
    }
    System.out.println(prefix + "Finished Processing of Workbooks");
  }

  private void processSheet(String sheetName, HSSFSheet sheet)
      throws ProcessorException {
    sheetPrefix = String.format(sheetPrefix,sheetName);
    System.out.println(sheetPrefix + "Start processing of sheet");

    int rowCount = sheet.getLastRowNum();
    int columnCount = sheet.getRow(0).getLastCellNum();

    VariantModel variantModel = validateAndBuildModel(sheetName,rowCount, columnCount,sheet);

    OptionModel optionModel = validateAndOptionModel(variantModel, sheetName, sheet, rowCount, columnCount);

    Map<String, String> optionSumByVariant = optionModel.getOptionsByVariant();
    String newVariantValue = optionSumByVariant.get(Constant.UNKNOWN_VARIANT_PLACEHOLDER);
    Map<String, String> optionSumMap = new HashMap<String, String>();
    Iterator iterator = optionSumByVariant.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
      String key = entry.getKey();
      String value = entry.getValue();
      if(!Constant.UNKNOWN_VARIANT_PLACEHOLDER.equals(key)) {
        if(optionSumMap.containsKey(value)) {
          throw new ProcessorException(fileType, sheetName, "two variant having same value, those are  " + key + " and " + optionSumMap.get(value));
        }
        optionSumMap.put(value, key);
      }
    }


    if(optionSumMap.containsKey(newVariantValue)) {
      System.out.println(sheetPrefix + "New Variant Not required, Base variant :: " + optionSumMap.get(newVariantValue));
    } else {
      System.out.println(sheetPrefix + "New Variant required");
    }


    System.out.println(sheetPrefix + "Finished Processing of sheet");
  }

  private OptionModel validateAndOptionModel(VariantModel variantModel, String sheetName, Sheet sheet, Integer rowCount, Integer columnCount)
      throws ProcessorException {
    Map<Integer, String> variantByIndex = variantModel.getVariantByIndex();

    Map<String, String>  optionsByVariant = new HashMap<String, String>();
    Map<Integer, String> optionsByIndex =  new HashMap<Integer, String>();

    for(int rowIndex = 3 ; rowIndex <= rowCount ; rowIndex ++) {
      Row row = sheet.getRow(rowIndex);

      List<String> variantExistSet = new ArrayList<String>();
      for(int columnIndex = 1;  columnIndex < columnCount ; columnIndex++) {
        try {
          Cell cell = row.getCell(columnIndex);

          if(cell == null) {
            throw new ProcessorException(fileType, sheetName, rowIndex + 1, columnIndex + 1, "Cell is empty");
          }

          Object cellValueObject = null;
          switch (cell.getCellType()) {
            case NUMERIC:
              cellValueObject = cell.getNumericCellValue();
              break;
            case STRING:
              cellValueObject = cell.getStringCellValue();
              break;
          }

          if(cellValueObject == null || cellValueObject.toString().trim() == "") {
            throw new ProcessorException(fileType, sheetName, rowIndex + 1, columnIndex + 1, "Cell is empty");
          }

          String cellValue = cellValueObject.toString();
          String variantNumber = variantByIndex.get(columnIndex);

          String existingOptionSumByIndex = optionsByIndex.get(columnIndex);
          String finalOptionByIndex = existingOptionSumByIndex == null ? cellValue : existingOptionSumByIndex + "+" + cellValue;

          String finalOptionSumByVariant = optionsByVariant.get(variantNumber);;
          if(!variantExistSet.contains(variantNumber)) {
            finalOptionSumByVariant = finalOptionSumByVariant == null ? cellValue : finalOptionSumByVariant + "+" + cellValue;
            variantExistSet.add(variantNumber);
          }

          if((finalOptionByIndex != null && finalOptionSumByVariant == null)
              || (finalOptionByIndex == null && finalOptionSumByVariant != null)) {
            throw new ProcessorException(fileType, sheetName, rowIndex+1,columnIndex+1, "Options sum till this cell or variant sum is not matching");
          }

          if(finalOptionByIndex != null
              && finalOptionSumByVariant != null
              && !finalOptionSumByVariant.equals(finalOptionByIndex)) {
            throw new ProcessorException(fileType, sheetName, rowIndex+1,columnIndex+1, "Options sum till this cell or variant sum is not matching");
          }

          optionsByIndex.put(columnIndex, finalOptionByIndex);
          optionsByVariant.put(variantNumber, finalOptionSumByVariant);
        }catch (Exception ex) {
          throw new ProcessorException(fileType, sheetName, rowIndex+1, columnIndex+1, ex.getMessage());
        }
      }
    }

    if(MapUtils.isEmpty(optionsByVariant)) {
      throw new ProcessorException(fileType, sheetName, "Option value is empty for vairants");
    }

    OptionModel optionModel = new OptionModel();
    optionModel.setOptionsByIndex(optionsByIndex);
    optionModel.setOptionsByVariant(optionsByVariant);
    return optionModel;
  }

  private VariantModel validateAndBuildModel(String sheetName,int rowCount, int columnCount, HSSFSheet sheet)
      throws ProcessorException {

    Map<Integer, String> variantByIndex = new HashMap<Integer, String>();

    if(rowCount == 0) {
      throw new ProcessorException(fileType,sheetName,"No rows in sheet");
    }


    for(int rowIndex = 0 ; rowIndex <= 2 ; rowIndex ++) {
      Row row = sheet.getRow(rowIndex);

      if(null == row) {
        throw new ProcessorException(fileType, sheetName, rowIndex + 1, null, "Row is empty");
      }

      if(rowIndex == 0) {
        Cell optionCell = row.getCell(0);
        if(optionCell == null || !"OPTION".equalsIgnoreCase(optionCell.getStringCellValue())) {
          throw new ProcessorException(fileType, sheetName, rowIndex + 1, 1, " Option column key is empty or mismatched");
        }
      }


      for(int columnIndex = 1 ; columnIndex < columnCount ; columnIndex ++) {
         Cell cell = row.getCell(columnIndex);

         Boolean findVariantColumn = (rowIndex == 2 && columnIndex == columnCount -1 );

         if(!findVariantColumn) {
           if(cell == null || cell.getStringCellValue() == null || cell.getStringCellValue().trim() == "") {
             throw new ProcessorException(fileType, sheetName, rowIndex + 1, columnIndex + 1, "Cell is empty");
           }
         }

         if(findVariantColumn) {
           if(cell != null && cell.getStringCellValue() != null && cell.getStringCellValue().trim() != "") {
             throw new ProcessorException(fileType, sheetName, rowIndex +1, columnIndex +1, " Unknown variant column is having value");
           }
         }

         if(rowIndex == 2) {
           String cellValue = (cell == null || cell.getStringCellValue() == "") ? Constant.UNKNOWN_VARIANT_PLACEHOLDER : cell.getStringCellValue();
           variantByIndex.put(columnIndex, cellValue);
         }

      }

    }

    if(MapUtils.isEmpty(variantByIndex)) {
      throw new ProcessorException(fileType, sheetName,"Some error occured in variant files");
    }
    return new VariantModel(variantByIndex);
  }
}
