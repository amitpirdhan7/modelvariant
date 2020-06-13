package exception;

/**
 * Created by sumansharma on 13/6/20.
 */
public class ProcessorException extends Exception {

  private String fileType;

  private String sheetName;

  private Integer rowIndex;

  private Integer columnIndex;

  private String message;

  public ProcessorException(String fileType, String sheetName, Integer rowIndex, Integer columnIndex, String message) {
    super(message);
    this.fileType = fileType;
    this.sheetName = sheetName;
    this.rowIndex = rowIndex;
    this.columnIndex = columnIndex;
    this.message = message;
  }

  public ProcessorException(String fileType, String message) {
    super(message);
    this.fileType = fileType;
    this.message = message;
  }

  public ProcessorException(String fileType, String sheetName, String message) {
    super(message);
    this.fileType = fileType;
    this.sheetName = sheetName;
    this.message = message;
  }

  public String getFileType() {

    return fileType;
  }

  public String getSheetName() {

    return sheetName;
  }

  public Integer getRowIndex() {

    return rowIndex;
  }

  public Integer getColumnIndex() {

    return columnIndex;
  }

  @Override public String getMessage() {

    return message;
  }
}
