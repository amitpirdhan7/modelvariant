package utils;

import enums.EAggregates;

/**
 * Created by sumansharma on 13/6/20.
 */
public class Utils {

  public static EAggregates valueOfAggregate(String aggregateName) {
    for(EAggregates eAggregates : EAggregates.values()) {
      if(eAggregates.getAggregateName().equalsIgnoreCase(aggregateName)) {
        return eAggregates;
      }
    }
    return null;
  }



}
