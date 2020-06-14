package entity;

import java.io.Serializable;

/**
 * Created by amitsharma on 14/6/20.
 */
public class FieldError implements Serializable {

  private String fileType;

  private String sheetName;

  private Integer rowIndex;

  private Integer columnIndex;

  private String message;

  public FieldError(String fileType, String sheetName, Integer rowIndex, Integer columnIndex, String message) {

    this.fileType = fileType;
    this.sheetName = sheetName;
    this.rowIndex = rowIndex;
    this.columnIndex = columnIndex;
    this.message = message;
  }

  public FieldError(String fileType, String message) {
    this.fileType = fileType;
    this.message = message;
  }

  public FieldError(String fileType, String sheetName, String message) {
    this.fileType = fileType;
    this.sheetName = sheetName;
    this.message = message;
  }

  public String getFileType() {

    return fileType;
  }

  public void setFileType(String fileType) {

    this.fileType = fileType;
  }

  public String getSheetName() {

    return sheetName;
  }

  public void setSheetName(String sheetName) {

    this.sheetName = sheetName;
  }

  public Integer getRowIndex() {

    return rowIndex;
  }

  public void setRowIndex(Integer rowIndex) {

    this.rowIndex = rowIndex;
  }

  public Integer getColumnIndex() {

    return columnIndex;
  }

  public void setColumnIndex(Integer columnIndex) {

    this.columnIndex = columnIndex;
  }

  public String getMessage() {

    return message;
  }

  public void setMessage(String message) {

    this.message = message;
  }

  @Override public String toString() {

    return "Error{" +
        "fileType='" + fileType + '\'' +
        ", sheetName='" + sheetName + '\'' +
        ", rowIndex=" + rowIndex +
        ", columnIndex=" + columnIndex +
        ", message='" + message + '\'' +
        '}';
  }
}
