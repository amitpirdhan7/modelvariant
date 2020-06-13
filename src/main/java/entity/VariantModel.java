package entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sumansharma on 13/6/20.
 */
public class VariantModel implements Serializable {

  private Map<Integer, String> variantByIndex   = new HashMap<Integer, String>();

  public VariantModel(Map<Integer, String> variantByIndex) {
    this.variantByIndex = variantByIndex;
  }

  public Map<Integer, String> getVariantByIndex() {

    return variantByIndex;
  }

  public void setVariantByIndex(Map<Integer, String> variantByIndex) {

    this.variantByIndex = variantByIndex;
  }

  @Override public String toString() {

    return "VariantModel{" +
        "variantByIndex=" + variantByIndex +
        '}';
  }
}
