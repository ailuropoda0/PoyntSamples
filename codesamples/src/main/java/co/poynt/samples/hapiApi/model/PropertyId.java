/*
 * HAPI Repository API
 * API for retrieving objects from HAPI repository
 *
 * OpenAPI spec version: 2
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package co.poynt.samples.hapiApi.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * Property id in HAPI system. Presented by brand code, chainCode, property code
 */
@ApiModel(description = "Property id in HAPI system. Presented by brand code, chainCode, property code")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-01-26T23:47:35.032Z")
public class PropertyId {
  @SerializedName("chainCode")
  private String chainCode = null;

  @SerializedName("brandCode")
  private String brandCode = null;

  @SerializedName("propertyCode")
  private String propertyCode = null;

  public PropertyId chainCode(String chainCode) {
    this.chainCode = chainCode;
    return this;
  }

   /**
   * Get chainCode
   * @return chainCode
  **/
  @ApiModelProperty(example = "HIL", value = "")
  public String getChainCode() {
    return chainCode;
  }

  public void setChainCode(String chainCode) {
    this.chainCode = chainCode;
  }

  public PropertyId brandCode(String brandCode) {
    this.brandCode = brandCode;
    return this;
  }

   /**
   * Get brandCode
   * @return brandCode
  **/
  @ApiModelProperty(example = "ABVI", value = "")
  public String getBrandCode() {
    return brandCode;
  }

  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  public PropertyId propertyCode(String propertyCode) {
    this.propertyCode = propertyCode;
    return this;
  }

   /**
   * Get propertyCode
   * @return propertyCode
  **/
  @ApiModelProperty(example = "FSDH", value = "")
  public String getPropertyCode() {
    return propertyCode;
  }

  public void setPropertyCode(String propertyCode) {
    this.propertyCode = propertyCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PropertyId propertyId = (PropertyId) o;
    return Objects.equals(this.chainCode, propertyId.chainCode) &&
        Objects.equals(this.brandCode, propertyId.brandCode) &&
        Objects.equals(this.propertyCode, propertyId.propertyCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chainCode, brandCode, propertyCode);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PropertyId {\n");
    
    sb.append("    chainCode: ").append(toIndentedString(chainCode)).append("\n");
    sb.append("    brandCode: ").append(toIndentedString(brandCode)).append("\n");
    sb.append("    propertyCode: ").append(toIndentedString(propertyCode)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

