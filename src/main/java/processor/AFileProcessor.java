package processor;

import exception.ProcessorException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by sumansharma on 13/6/20.
 */
public abstract class AFileProcessor {

  public abstract Boolean process(String configPath)
      throws ProcessorException;

  public HSSFWorkbook getWorkBook(String prefix, String fileType, String configPath)
      throws ProcessorException {

    System.out.println(prefix + "Getting workbook");

    FileInputStream fis= null;
    try {
      fis = new FileInputStream(new File(configPath));
      HSSFWorkbook wb=new HSSFWorkbook(fis);
      System.out.println(prefix + "Workbook is created");
      return wb;
    } catch (Exception e) {
      throw new ProcessorException(fileType,"Not able to fetch workBook");
    }
  }

}
