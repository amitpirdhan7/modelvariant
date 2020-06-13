package enums;

/**
 * Created by sumansharma on 13/6/20.
 */
public enum EAggregates {

  ENGINE("ENGINE"),
  TRANSMISSION("TRANSMISSION"),
  HYDRAULICS("HYDRAULICS"),
  ELECTRICAL("ELECTRICAL"),
  SHEETMETAL("SHEETMETAL"),
  INTEGRATION("INTEGRATION"),
  ADVANCED_ELECTRONICS("ADVANCED ELECTRONICS");

  final String aggregateName;

   EAggregates(String aggregateName) {
    this.aggregateName = aggregateName;
  }

  public String getAggregateName() {

    return aggregateName;
  }

}
