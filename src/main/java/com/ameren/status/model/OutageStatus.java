package com.ameren.status.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;

import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 */
@ApiModel(description = "")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-02-22T15:43:17.029-06:00")
@JsonInclude(Include.NON_NULL)
public class OutageStatus   {
  @JsonProperty("knownOutage")
  private Boolean knownOutage;

  @JsonProperty("account")
  private String account = null;

  @JsonProperty("reported")
  private Boolean reported;

  @JsonProperty("state")
  private String state = null;

  @JsonProperty("postalCode")
  private String postalCode = null;

  @JsonProperty("houseNumber")
  private String houseNumber = null;

  @JsonProperty("esrt")
  private ZonedDateTime esrt = null;

  @JsonProperty("customersImpacted")
  private Integer customersImpacted;

  @JsonProperty("cause")
  private String cause = null;

  @JsonProperty("outageStatus")
  private String outageStatus = null;

  @JsonProperty("outageDateTime")
  private ZonedDateTime outageDateTime = null;

  @JsonProperty("orderNumber")
  private String orderNumber = null;
  
  @JsonProperty("message")
  private String message = null;

  @JsonProperty("reportedNotProcessed")
  private Boolean reportedNotProcessed;
  
  /**
   * Gets or Sets esrtCalcMethod
   */
  public enum EsrtCalcMethodEnum {
    MANUAL("Manual"),
    
    AUTO("Auto");

    private String value;

    EsrtCalcMethodEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static EsrtCalcMethodEnum fromValue(String text) {
      for (EsrtCalcMethodEnum b : EsrtCalcMethodEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("esrtCalcMethod")
  private EsrtCalcMethodEnum esrtCalcMethod = null;

  @JsonProperty("restoredDateTime")
  private ZonedDateTime restoredDateTime = null;

  @JsonProperty("lastUpdatedDateTime")
  private ZonedDateTime lastUpdatedDateTime = null;

  public OutageStatus knownOutage(Boolean knownOutage) {
    this.knownOutage = knownOutage;
    return this;
  }

  /**
   * Get knownOutage
   * @return knownOutage
  **/
  @ApiModelProperty(value = "")


  public Boolean isKnownOutage() {
    return knownOutage;
  }

  public void setKnownOutage(Boolean knownOutage) {
    this.knownOutage = knownOutage;
  }

  public OutageStatus account(String account) {
    this.account = account;
    return this;
  }

  /**
   * Get account
   * @return account
  **/
  @ApiModelProperty(example = "0100500104", value = "")

@Size(min=10,max=10) 
  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public OutageStatus reported(Boolean reported) {
    this.reported = reported;
    return this;
  }

  /**
   * Get reported
   * @return reported
  **/
  @ApiModelProperty(value = "")


  public Boolean isReported() {
    return reported;
  }

  public void setReported(Boolean reported) {
    this.reported = reported;
  }

  public OutageStatus state(String state) {
    this.state = state;
    return this;
  }

  /**
   * Get state
   * @return state
  **/
  @ApiModelProperty(value = "")


  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public OutageStatus postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  /**
   * Get postalCode
   * @return postalCode
  **/
  @ApiModelProperty(example = "63141", value = "")


  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public OutageStatus houseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
    return this;
  }

  /**
   * Get houseNumber
   * @return houseNumber
  **/
  @ApiModelProperty(example = "1234", value = "")


  public String getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
  }

  public OutageStatus esrt(ZonedDateTime esrt) {
    this.esrt = esrt;
    return this;
  }

  /**
   * Get esrt
   * @return esrt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ZonedDateTime getEsrt() {
    return esrt;
  }

  public void setEsrt(ZonedDateTime esrt) {
    this.esrt = esrt;
  }

  public OutageStatus customersImpacted(Integer customersImpacted) {
    this.customersImpacted = customersImpacted;
    return this;
  }

  /**
   * Get customersImpacted
   * @return customersImpacted
  **/
  @ApiModelProperty(value = "")


  public Integer getCustomersImpacted() {
    return customersImpacted;
  }

  public void setCustomersImpacted(Integer customersImpacted) {
    this.customersImpacted = customersImpacted;
  }

  public OutageStatus cause(String cause) {
    this.cause = cause;
    return this;
  }

  /**
   * Get cause
   * @return cause
  **/
  @ApiModelProperty(value = "")


  public String getCause() {
    return cause;
  }

  public void setCause(String cause) {
    this.cause = cause;
  }

  public OutageStatus outageStatus(String outageStatus) {
    this.outageStatus = outageStatus;
    return this;
  }

  /**
   * Get outageStatus
   * @return outageStatus
  **/
  @ApiModelProperty(value = "")


  public String getOutageStatus() {
    return outageStatus;
  }

  public void setOutageStatus(String outageStatus) {
    this.outageStatus = outageStatus;
  }

  public OutageStatus outageDateTime(ZonedDateTime outageDateTime) {
    this.outageDateTime = outageDateTime;
    return this;
  }

  /**
   * Get outageDateTime
   * @return outageDateTime
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ZonedDateTime getOutageDateTime() {
    return outageDateTime;
  }

  public void setOutageDateTime(ZonedDateTime outageDateTime) {
    this.outageDateTime = outageDateTime;
  }

  public OutageStatus orderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
    return this;
  }

  /**
   * Get orderNumber
   * @return orderNumber
  **/
  @ApiModelProperty(value = "")


  public String getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public OutageStatus esrtCalcMethod(EsrtCalcMethodEnum esrtCalcMethod) {
    this.esrtCalcMethod = esrtCalcMethod;
    return this;
  }

  /**
   * Get esrtCalcMethod
   * @return esrtCalcMethod
  **/
  @ApiModelProperty(value = "")


  public EsrtCalcMethodEnum getEsrtCalcMethod() {
    return esrtCalcMethod;
  }

  public void setEsrtCalcMethod(EsrtCalcMethodEnum esrtCalcMethod) {
    this.esrtCalcMethod = esrtCalcMethod;
  }

  public OutageStatus restoredDateTime(ZonedDateTime restoredDateTime) {
    this.restoredDateTime = restoredDateTime;
    return this;
  }

  /**
   * Get restoredDateTime
   * @return restoredDateTime
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ZonedDateTime getRestoredDateTime() {
    return restoredDateTime;
  }

  public void setRestoredDateTime(ZonedDateTime restoredDateTime) {
    this.restoredDateTime = restoredDateTime;
  }

  public OutageStatus lastUpdatedDateTime(ZonedDateTime lastUpdatedDateTime) {
    this.lastUpdatedDateTime = lastUpdatedDateTime;
    return this;
  }

  /**
   * Get lastUpdatedDateTime
   * @return lastUpdatedDateTime
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ZonedDateTime getLastUpdatedDateTime() {
    return lastUpdatedDateTime;
  }

  public void setLastUpdatedDateTime(ZonedDateTime lastUpdatedDateTime) {
    this.lastUpdatedDateTime = lastUpdatedDateTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OutageStatus outageStatus = (OutageStatus) o;
    return Objects.equals(this.knownOutage, outageStatus.knownOutage) &&
        Objects.equals(this.account, outageStatus.account) &&
        Objects.equals(this.reported, outageStatus.reported) &&
        Objects.equals(this.state, outageStatus.state) &&
        Objects.equals(this.postalCode, outageStatus.postalCode) &&
        Objects.equals(this.houseNumber, outageStatus.houseNumber) &&
        Objects.equals(this.esrt, outageStatus.esrt) &&
        Objects.equals(this.customersImpacted, outageStatus.customersImpacted) &&
        Objects.equals(this.cause, outageStatus.cause) &&
        Objects.equals(this.outageStatus, outageStatus.outageStatus) &&
        Objects.equals(this.outageDateTime, outageStatus.outageDateTime) &&
        Objects.equals(this.orderNumber, outageStatus.orderNumber) &&
        Objects.equals(this.esrtCalcMethod, outageStatus.esrtCalcMethod) &&
        Objects.equals(this.restoredDateTime, outageStatus.restoredDateTime) &&
        Objects.equals(this.lastUpdatedDateTime, outageStatus.lastUpdatedDateTime) &&
        Objects.equals(this.reportedNotProcessed, reportedNotProcessed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(knownOutage, account, reported, state, postalCode, houseNumber, esrt, customersImpacted, cause, outageStatus, outageDateTime, orderNumber, esrtCalcMethod, restoredDateTime, lastUpdatedDateTime, reportedNotProcessed);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OutageStatus {\n");
    
    sb.append("    knownOutage: ").append(toIndentedString(knownOutage)).append("\n");
    sb.append("    account: ").append(toIndentedString(account)).append("\n");
    sb.append("    reported: ").append(toIndentedString(reported)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
    sb.append("    houseNumber: ").append(toIndentedString(houseNumber)).append("\n");
    sb.append("    esrt: ").append(toIndentedString(esrt)).append("\n");
    sb.append("    customersImpacted: ").append(toIndentedString(customersImpacted)).append("\n");
    sb.append("    cause: ").append(toIndentedString(cause)).append("\n");
    sb.append("    outageStatus: ").append(toIndentedString(outageStatus)).append("\n");
    sb.append("    outageDateTime: ").append(toIndentedString(outageDateTime)).append("\n");
    sb.append("    orderNumber: ").append(toIndentedString(orderNumber)).append("\n");
    sb.append("    esrtCalcMethod: ").append(toIndentedString(esrtCalcMethod)).append("\n");
    sb.append("    restoredDateTime: ").append(toIndentedString(restoredDateTime)).append("\n");
    sb.append("    lastUpdatedDateTime: ").append(toIndentedString(lastUpdatedDateTime)).append("\n");
    sb.append("    reportedNotProcessed: ").append(toIndentedString(reportedNotProcessed)).append("\n");
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

  public String getMessage() {
	  return message;
  }

  public void setMessage(String message) {
	  this.message = message;
  }
  
  public Boolean isReportedNotProcessed() {
    return reportedNotProcessed;
  }

  public void setReportedNotProcessed(Boolean reportedNotProcessed) {
    this.reportedNotProcessed = reportedNotProcessed;
  }
}

