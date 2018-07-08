package com.jhanakdidwania.officespace;

public class LicenseFormFields {
    private String EmployeeName;
    private String EmployeeID;
    private String EmployeeContact;
    private String LicenseType;
    private String ProjectName;
    private String CompanyName;
    private String ContactPersonName;
    private String ContactNumber;
    private String CompanyLocation;
    private String NumberOfLicenses;

    public LicenseFormFields(String employeeName,
                             String employeeID,
                             String employeeContact,
                             String licenseType,
                             String projectName,
                             String companyName,
                             String contactPersonName,
                             String contactNumber,
                             String companyLocation,
                             String numberOfLicenses) {
        EmployeeName = employeeName;
        EmployeeID = employeeID;
        EmployeeContact = employeeContact;
        LicenseType = licenseType;
        ProjectName = projectName;
        CompanyName = companyName;
        ContactPersonName = contactPersonName;
        ContactNumber = contactNumber;
        CompanyLocation = companyLocation;
        NumberOfLicenses = numberOfLicenses;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public String getEmployeeContact() {
        return EmployeeContact;
    }

    public void setEmployeeContact(String employeeContact) {
        EmployeeContact = employeeContact;
    }

    public String getLicenseType() {
        return LicenseType;
    }

    public void setLicenseType(String licenseType) {
        LicenseType = licenseType;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getContactPersonName() {
        return ContactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        ContactPersonName = contactPersonName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getCompanyLocation() {
        return CompanyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        CompanyLocation = companyLocation;
    }

    public String getNumberOfLicenses() {
        return NumberOfLicenses;
    }

    public void setNumberOfLicenses(String numberOfLicenses) {
        NumberOfLicenses = numberOfLicenses;
    }
}
