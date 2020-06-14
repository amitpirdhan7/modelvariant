package entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sumansharma on 13/6/20.
 */
public class VariantModel implements Serializable {

  private Map<Integer, String> variantByIndex   = new HashMap<Integer, String>();

  private Integer unKnownVariantCount = null;

  public VariantModel(Map<Integer, String> variantByIndex, Integer unKnownVariantCount) {
    this.variantByIndex = variantByIndex;
    this.unKnownVariantCount = unKnownVariantCount;
  }

  public Map<Integer, String> getVariantByIndex() {

    return variantByIndex;
  }

  public void setVariantByIndex(Map<Integer, String> variantByIndex) {

    this.variantByIndex = variantByIndex;
  }

  public Integer getUnKnownVariantCount() {

    return unKnownVariantCount;
  }

  public void setUnKnownVariantCount(Integer unKnownVariantCount) {

    this.unKnownVariantCount = unKnownVariantCount;
  }

  @Override public String toString() {

    return "VariantModel{" +
        "variantByIndex=" + variantByIndex +
        ", unKnownVariantCount=" + unKnownVariantCount +
        '}';
  }
}
