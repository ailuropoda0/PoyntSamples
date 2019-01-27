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
import co.poynt.samples.hapiApi.model.Address;
import co.poynt.samples.hapiApi.model.Email;
import co.poynt.samples.hapiApi.model.IdDocument;
import co.poynt.samples.hapiApi.model.LoyaltyProgram;
import co.poynt.samples.hapiApi.model.Name;
import co.poynt.samples.hapiApi.model.Phone;
import co.poynt.samples.hapiApi.model.PropertyId;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Profile
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-01-26T23:47:35.032Z")
public class Profile {
  @SerializedName("propertyId")
  private PropertyId propertyId = null;

  @SerializedName("id")
  private String id = null;

  /**
   * type of profile
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    PERSON("PERSON"),
    
    COMPANY("COMPANY"),
    
    TRAVEL_AGENT("TRAVEL_AGENT"),
    
    GROUP_PROFILE("GROUP_PROFILE"),
    
    OTHER("OTHER");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<TypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TypeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return TypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("type")
  private TypeEnum type = null;

  @SerializedName("name")
  private Name name = null;

  @SerializedName("company")
  private String company = null;

  @SerializedName("dateOfBirth")
  private String dateOfBirth = null;

  @SerializedName("emails")
  private List<Email> emails = null;

  @SerializedName("phones")
  private List<Phone> phones = null;

  @SerializedName("addresses")
  private List<Address> addresses = null;

  @SerializedName("idDocuments")
  private List<IdDocument> idDocuments = null;

  @SerializedName("loyaltyPrograms")
  private List<LoyaltyProgram> loyaltyPrograms = null;

  @SerializedName("creator")
  private String creator = null;

  @SerializedName("createdDate")
  private String createdDate = null;

  @SerializedName("travelAgentId")
  private String travelAgentId = null;

  @SerializedName("repositoryCreated")
  private String repositoryCreated = null;

  @SerializedName("repositoryUpdated")
  private String repositoryUpdated = null;

  public Profile propertyId(PropertyId propertyId) {
    this.propertyId = propertyId;
    return this;
  }

   /**
   * Get propertyId
   * @return propertyId
  **/
  @ApiModelProperty(value = "")
  public PropertyId getPropertyId() {
    return propertyId;
  }

  public void setPropertyId(PropertyId propertyId) {
    this.propertyId = propertyId;
  }

  public Profile id(String id) {
    this.id = id;
    return this;
  }

   /**
   * reservation identifier
   * @return id
  **/
  @ApiModelProperty(example = "542154", value = "reservation identifier")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Profile type(TypeEnum type) {
    this.type = type;
    return this;
  }

   /**
   * type of profile
   * @return type
  **/
  @ApiModelProperty(example = "PERSON", value = "type of profile")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public Profile name(Name name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public Profile company(String company) {
    this.company = company;
    return this;
  }

   /**
   * used to provide company name when name field is used for contact info
   * @return company
  **/
  @ApiModelProperty(example = "HAPI", value = "used to provide company name when name field is used for contact info")
  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public Profile dateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

   /**
   * Get dateOfBirth
   * @return dateOfBirth
  **/
  @ApiModelProperty(example = "1979-10-31", value = "")
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public Profile emails(List<Email> emails) {
    this.emails = emails;
    return this;
  }

  public Profile addEmailsItem(Email emailsItem) {
    if (this.emails == null) {
      this.emails = new ArrayList<Email>();
    }
    this.emails.add(emailsItem);
    return this;
  }

   /**
   * Get emails
   * @return emails
  **/
  @ApiModelProperty(value = "")
  public List<Email> getEmails() {
    return emails;
  }

  public void setEmails(List<Email> emails) {
    this.emails = emails;
  }

  public Profile phones(List<Phone> phones) {
    this.phones = phones;
    return this;
  }

  public Profile addPhonesItem(Phone phonesItem) {
    if (this.phones == null) {
      this.phones = new ArrayList<Phone>();
    }
    this.phones.add(phonesItem);
    return this;
  }

   /**
   * Get phones
   * @return phones
  **/
  @ApiModelProperty(value = "")
  public List<Phone> getPhones() {
    return phones;
  }

  public void setPhones(List<Phone> phones) {
    this.phones = phones;
  }

  public Profile addresses(List<Address> addresses) {
    this.addresses = addresses;
    return this;
  }

  public Profile addAddressesItem(Address addressesItem) {
    if (this.addresses == null) {
      this.addresses = new ArrayList<Address>();
    }
    this.addresses.add(addressesItem);
    return this;
  }

   /**
   * Get addresses
   * @return addresses
  **/
  @ApiModelProperty(value = "")
  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  public Profile idDocuments(List<IdDocument> idDocuments) {
    this.idDocuments = idDocuments;
    return this;
  }

  public Profile addIdDocumentsItem(IdDocument idDocumentsItem) {
    if (this.idDocuments == null) {
      this.idDocuments = new ArrayList<IdDocument>();
    }
    this.idDocuments.add(idDocumentsItem);
    return this;
  }

   /**
   * Get idDocuments
   * @return idDocuments
  **/
  @ApiModelProperty(value = "")
  public List<IdDocument> getIdDocuments() {
    return idDocuments;
  }

  public void setIdDocuments(List<IdDocument> idDocuments) {
    this.idDocuments = idDocuments;
  }

  public Profile loyaltyPrograms(List<LoyaltyProgram> loyaltyPrograms) {
    this.loyaltyPrograms = loyaltyPrograms;
    return this;
  }

  public Profile addLoyaltyProgramsItem(LoyaltyProgram loyaltyProgramsItem) {
    if (this.loyaltyPrograms == null) {
      this.loyaltyPrograms = new ArrayList<LoyaltyProgram>();
    }
    this.loyaltyPrograms.add(loyaltyProgramsItem);
    return this;
  }

   /**
   * Get loyaltyPrograms
   * @return loyaltyPrograms
  **/
  @ApiModelProperty(value = "")
  public List<LoyaltyProgram> getLoyaltyPrograms() {
    return loyaltyPrograms;
  }

  public void setLoyaltyPrograms(List<LoyaltyProgram> loyaltyPrograms) {
    this.loyaltyPrograms = loyaltyPrograms;
  }

  public Profile creator(String creator) {
    this.creator = creator;
    return this;
  }

   /**
   * user or interface who created the profile
   * @return creator
  **/
  @ApiModelProperty(example = "AFESSE", value = "user or interface who created the profile")
  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public Profile createdDate(String createdDate) {
    this.createdDate = createdDate;
    return this;
  }

   /**
   * read-only date and time the profile was created
   * @return createdDate
  **/
  @ApiModelProperty(example = "2018-05-14T20:02:40.000", value = "read-only date and time the profile was created")
  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public Profile travelAgentId(String travelAgentId) {
    this.travelAgentId = travelAgentId;
    return this;
  }

   /**
   * IATA number
   * @return travelAgentId
  **/
  @ApiModelProperty(example = "ABC1234", value = "IATA number")
  public String getTravelAgentId() {
    return travelAgentId;
  }

  public void setTravelAgentId(String travelAgentId) {
    this.travelAgentId = travelAgentId;
  }

  public Profile repositoryCreated(String repositoryCreated) {
    this.repositoryCreated = repositoryCreated;
    return this;
  }

   /**
   * repository create date in YYYY-MM-DDThh:mm:ss.sss format
   * @return repositoryCreated
  **/
  @ApiModelProperty(example = "2018-06-01T15:34:54.000", value = "repository create date in YYYY-MM-DDThh:mm:ss.sss format")
  public String getRepositoryCreated() {
    return repositoryCreated;
  }

  public void setRepositoryCreated(String repositoryCreated) {
    this.repositoryCreated = repositoryCreated;
  }

  public Profile repositoryUpdated(String repositoryUpdated) {
    this.repositoryUpdated = repositoryUpdated;
    return this;
  }

   /**
   * repository update date in YYYY-MM-DDThh:mm:ss.sss format
   * @return repositoryUpdated
  **/
  @ApiModelProperty(example = "2018-06-01T15:34:54.000", value = "repository update date in YYYY-MM-DDThh:mm:ss.sss format")
  public String getRepositoryUpdated() {
    return repositoryUpdated;
  }

  public void setRepositoryUpdated(String repositoryUpdated) {
    this.repositoryUpdated = repositoryUpdated;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Profile profile = (Profile) o;
    return Objects.equals(this.propertyId, profile.propertyId) &&
        Objects.equals(this.id, profile.id) &&
        Objects.equals(this.type, profile.type) &&
        Objects.equals(this.name, profile.name) &&
        Objects.equals(this.company, profile.company) &&
        Objects.equals(this.dateOfBirth, profile.dateOfBirth) &&
        Objects.equals(this.emails, profile.emails) &&
        Objects.equals(this.phones, profile.phones) &&
        Objects.equals(this.addresses, profile.addresses) &&
        Objects.equals(this.idDocuments, profile.idDocuments) &&
        Objects.equals(this.loyaltyPrograms, profile.loyaltyPrograms) &&
        Objects.equals(this.creator, profile.creator) &&
        Objects.equals(this.createdDate, profile.createdDate) &&
        Objects.equals(this.travelAgentId, profile.travelAgentId) &&
        Objects.equals(this.repositoryCreated, profile.repositoryCreated) &&
        Objects.equals(this.repositoryUpdated, profile.repositoryUpdated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(propertyId, id, type, name, company, dateOfBirth, emails, phones, addresses, idDocuments, loyaltyPrograms, creator, createdDate, travelAgentId, repositoryCreated, repositoryUpdated);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Profile {\n");
    
    sb.append("    propertyId: ").append(toIndentedString(propertyId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    company: ").append(toIndentedString(company)).append("\n");
    sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
    sb.append("    emails: ").append(toIndentedString(emails)).append("\n");
    sb.append("    phones: ").append(toIndentedString(phones)).append("\n");
    sb.append("    addresses: ").append(toIndentedString(addresses)).append("\n");
    sb.append("    idDocuments: ").append(toIndentedString(idDocuments)).append("\n");
    sb.append("    loyaltyPrograms: ").append(toIndentedString(loyaltyPrograms)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    createdDate: ").append(toIndentedString(createdDate)).append("\n");
    sb.append("    travelAgentId: ").append(toIndentedString(travelAgentId)).append("\n");
    sb.append("    repositoryCreated: ").append(toIndentedString(repositoryCreated)).append("\n");
    sb.append("    repositoryUpdated: ").append(toIndentedString(repositoryUpdated)).append("\n");
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

