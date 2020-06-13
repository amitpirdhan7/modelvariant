import exception.ProcessorException;
import processor.AFileProcessor;
import processor.VariantFileProcessor;

/**
 * Created by sumansharma on 13/6/20.
 */
public class ModelVariantApplication {

  public static void main(String []args) {

    System.out.println("Provide Variant Sheet File Path");
    String configPath = "/home/amitsharma/Documents/variant.xls";
    long start = System.currentTimeMillis();
    long end = 0l;
    AFileProcessor variantFileProcessor = new VariantFileProcessor();
    try {
      Boolean variantProcessResult = variantFileProcessor.process(configPath);
    } catch (ProcessorException e) {
      System.out.println("Exception Occured While Processing files");
      System.out.println("File Type :: "
                       + e.getFileType()
                       + " Sheet Name :: "
                       + e.getSheetName()
                       + " Row Index :: "
                       + e.getRowIndex()
                       + " Column index :: "
                       + e.getColumnIndex()
                       + " Error messsage :: "
                       + e.getMessage());
      System.out.println("Aborting the process");
    } finally {
      end = System.currentTimeMillis();
    }

    System.out.println("Time took by script to execute :: " + (end-start)/1000);
  }





}
