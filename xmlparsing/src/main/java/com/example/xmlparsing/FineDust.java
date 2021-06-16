package com.example.xmlparsing;

public class FineDust {
   String district;
   String value;
   String issueDate;

   public FineDust(String district, String value, String issueDate) {
      this.district = district;
      this.value = value;
      this.issueDate = issueDate;
   }

   public String getDistrict() {
      return district;
   }

   public void setDistrict(String district) {
      this.district = district;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getIssueDate() {
      return issueDate;
   }

   public void setIssueDate(String issueDate) {
      this.issueDate = issueDate;
   }
}
