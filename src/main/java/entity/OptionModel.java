package entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by amitsharma on 13/6/20.
 */
public class OptionModel implements Serializable {

  private Map<String, String>   optionsByVariant = new HashMap<String, String>();
  private Map<Integer, String>  optionsByIndex   =  new HashMap<Integer, String>();

  public Map<String, String> getOptionsByVariant() {

    return optionsByVariant;
  }

  public void setOptionsByVariant(Map<String, String> optionsByVariant) {

    this.optionsByVariant = optionsByVariant;
  }

  public Map<Integer, String> getOptionsByIndex() {

    return optionsByIndex;
  }

  public void setOptionsByIndex(Map<Integer, String> optionsByIndex) {

    this.optionsByIndex = optionsByIndex;
  }

  @Override public String toString() {

    return "OptionModel{" +
        "optionsByVariant=" + optionsByVariant +
        ", optionsByIndex=" + optionsByIndex +
        '}';
  }
}
