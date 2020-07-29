import processor.AFileProcessor;
import processor.VariantFileProcessor;

import java.util.Scanner;

/**
 * Created by sumansharma on 13/6/20.
 */
public class ModelVariantApplication {

  public static void main(String []args) {

    System.out.println("Provide Variant Sheet File Path");
    Scanner scanner = new Scanner(System.in);
    String configPath = "/home/amitsharma/Documents/testvariant1.xls";
    //String configPath = scanner.next();
    long start = System.currentTimeMillis();
    long end = 0l;
    AFileProcessor variantFileProcessor = new VariantFileProcessor();
    try {
      Boolean variantProcessResult = variantFileProcessor.process(configPath);
    } catch (Exception e) {
      System.out.println("Exception Occured While Processing files :: " + e.getMessage());
      System.out.println("Aborting the process");
    } finally {
      end = System.currentTimeMillis();
    }

    System.out.println("Time took by script to execute :: " + (end-start)/1000);
  }





}
