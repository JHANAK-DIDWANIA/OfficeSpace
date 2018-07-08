package com.jhanakdidwania.officespace;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class LicenseGenerator extends AppCompatActivity{

    List<Features> featuresToParse = new ArrayList<Features>();
    static LicenseFormFields mFormFields;

    Calendar c = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    String formattedDate;
    private static boolean isValid = false;
    private static int day, month, year;
    private EditText EmployeeName;
    private EditText EmployeeID;
    private EditText EmployeeContact;
    private EditText LicenseType;
    private EditText ProjectName;
    private EditText CompanyName;
    private EditText ContactPersonName;
    private EditText ContactNumber;
    private EditText CompanyLocation;
    private EditText NumberOfLicenses;
    private TextView GenerateLicense;
    private String values[]= new String[]{"ENABLE", "HIDE", "DISABLE"};
    private static String LicenseName = null;
    private static String LicenseVersion = null;
    String formFieldNames[]= new String[]{"Project","UserName", "UserId", "UserContact", "HOCompanyName", "ConcernedPerson","Contact",
    "LicenseType", "Location", "NumberOfLicenses", "Date"};

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_license);

        EmployeeName = findViewById(R.id.employee_name);
        EmployeeContact = findViewById(R.id.employee_contact);
        EmployeeID = findViewById(R.id.employee_id);
        LicenseType = findViewById(R.id.license_type);
        ProjectName = findViewById(R.id.project_name);
        CompanyName = findViewById(R.id.company_name);
        CompanyLocation = findViewById(R.id.company_location);
        ContactPersonName = findViewById(R.id.contact_person_name);
        ContactNumber = findViewById(R.id.contact_number);
        NumberOfLicenses = findViewById(R.id.number_of_licenses);
        GenerateLicense = findViewById(R.id.generate_license);

        if(savedInstanceState != null){
            EmployeeName.setText(savedInstanceState.getString("ename"));
            EmployeeContact.setText(savedInstanceState.getString("econtact"));
            EmployeeID.setText(savedInstanceState.getString("eid"));
            ProjectName.setText(savedInstanceState.getString("pname"));
            CompanyName.setText(savedInstanceState.getString("companyname"));
            ContactPersonName.setText(savedInstanceState.getString("cpname"));
            ContactNumber.setText(savedInstanceState.getString("cnumber"));
            CompanyLocation.setText(savedInstanceState.getString("clocation"));
            NumberOfLicenses.setText(savedInstanceState.getString("licenses"));
        }

        Intent initiatedByFeatureDisplayActivity = getIntent();
        initiatedByFeatureDisplayActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        if(initiatedByFeatureDisplayActivity.hasExtra("LicenseType")){
            String version = initiatedByFeatureDisplayActivity.getStringExtra("LicenseType");
            LicenseType.setText(version);
            LicenseType.setTextColor(R.color.statusBarColor);
            LicenseType.setEnabled(false);
        }
        if(initiatedByFeatureDisplayActivity.hasExtra("FeatureList")){
            featuresToParse = (List<Features>) initiatedByFeatureDisplayActivity.getSerializableExtra("FeatureList");
        }
        if(initiatedByFeatureDisplayActivity.hasExtra("Day")){
            day = initiatedByFeatureDisplayActivity.getIntExtra("Day",0);
            month = initiatedByFeatureDisplayActivity.getIntExtra("Month",0);
            year = initiatedByFeatureDisplayActivity.getIntExtra("Year",0);
        }
        LicenseName = new XmlParser().getLicenseName();
        LicenseVersion = new XmlParser().getLicenseVersion();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("ename", EmployeeName.getText().toString());
        savedInstanceState.putString("eid", EmployeeID.getText().toString());
        savedInstanceState.putString("econtact", EmployeeContact.getText().toString());
        savedInstanceState.putString("pname", ProjectName.getText().toString());
        savedInstanceState.putString("companyname", CompanyName.getText().toString());
        savedInstanceState.putString("cpname", CompanyName.getText().toString());
        savedInstanceState.putString("cnumber", CompanyName.getText().toString());
        savedInstanceState.putString("clocation", CompanyName.getText().toString());
        savedInstanceState.putString("licenses", CompanyName.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    public void generateLicenseXML(View view) {
        validateForm();
        if(isValid){
            initializeFormFields();
            formattedDate = df.format(c.getTime());
            String generatedXML = writeXML();
            moveToNextActivity();
            Log.d("OfficeSpace", generatedXML);
            Log.d("OfficeSpace", "Testing");
        }else{
            validateForm();
        }
    }

    private void moveToNextActivity() {
        Intent intent = new Intent(LicenseGenerator.this, LastActivity.class);
        startActivity(intent);
        finish();
    }

    private String writeXML() {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag(null, "LCNS");
            serializer.attribute(null, "NAME", LicenseName);
            serializer.attribute(null, "VER", LicenseVersion);
            serializer.startTag(null, "GRP");
            serializer.attribute(null, "NAME", "Days");
            serializer.attribute(null, "VAL", "0");

                serializer.startTag(null, "VALUE");
                serializer.attribute(null, "N", "License's last day");
                serializer.attribute(null, "D", "Day");
                serializer.attribute(null, "UID", "day");
                serializer.attribute(null, "VAL", String.valueOf(day));
                serializer.endTag(null, "VALUE");

                serializer.startTag(null, "VALUE");
                serializer.attribute(null, "N", "License's last month");
                serializer.attribute(null, "D", "Month");
                serializer.attribute(null, "UID", "month");
                serializer.attribute(null, "VAL", String.valueOf(month));
                serializer.endTag(null, "VALUE");

                serializer.startTag(null, "VALUE");
                serializer.attribute(null, "N", "License's last year");
                serializer.attribute(null, "D", "Year");
                serializer.attribute(null, "UID", "year");
                serializer.attribute(null, "VAL", String.valueOf(year));
                serializer.endTag(null, "VALUE");

                String previousTitle = "Days";


            for(Features element: featuresToParse){
                if(element.getVal() != 0){
                    String currentTitle = element.getTitle();
                    //moving onto next group
                    if(!previousTitle.equalsIgnoreCase(currentTitle)){
                        serializer.endTag(null, "GRP");
                        serializer.startTag(null,"GRP");
                        serializer.attribute(null, "NAME", element.getTitle());
                        previousTitle = element.getTitle();
                    }
                    serializer.startTag(null, "VALUE");
                    serializer.attribute(null, "N",element.getName());
                    serializer.attribute(null, "D",element.getDescription());
                    serializer.attribute(null, "UID",element.getUID());
                    serializer.attribute(null, "VAL", String.valueOf(element.getVal()));
                    if(element.getTitle().equalsIgnoreCase("range")){
                        serializer.attribute(null, "RANGE_MIN", String.valueOf(element.getRANGE_MIN()));
                        serializer.attribute(null, "RANGE_MAX", String.valueOf(element.getRANGE_MAX()));
                    }
                    serializer.endTag(null, "VALUE");
                }
                if(element == featuresToParse.get(featuresToParse.size()-1)){
                    serializer.endTag(null, "GRP");
                }
            }

            //now filling up the company and employee details
            serializer.startTag(null, "GRP");
            serializer.attribute(null, "NAME", "ClientInfo");
            for(int i=0; i<formFieldNames.length; i++){
                serializer.startTag(null, "VALUE");
                serializer.attribute(null, "N", formFieldNames[i]);
                serializer.attribute(null, "D", formFieldNames[i]);
                serializer.attribute(null, "UID", formFieldNames[i]);
                switch(i){
                    case 0: serializer.attribute(null, "VAL", mFormFields.getProjectName()); break;
                    case 1: serializer.attribute(null, "VAL", mFormFields.getEmployeeName()); break;
                    case 2: serializer.attribute(null, "VAL", mFormFields.getEmployeeID()); break;
                    case 3: serializer.attribute(null, "VAL", mFormFields.getEmployeeContact()); break;
                    case 4: serializer.attribute(null, "VAL", mFormFields.getCompanyName()); break;
                    case 5: serializer.attribute(null, "VAL", mFormFields.getContactPersonName()); break;
                    case 6: serializer.attribute(null, "VAL", mFormFields.getContactNumber()); break;
                    case 7: serializer.attribute(null, "VAL", mFormFields.getLicenseType()); break;
                    case 8: serializer.attribute(null, "VAL", mFormFields.getCompanyLocation()); break;
                    case 9: serializer.attribute(null, "VAL", mFormFields.getNumberOfLicenses()); break;
                    case 10: serializer.attribute(null, "VAL", formattedDate); break;
                }
                serializer.endTag(null, "VALUE");
            }
            serializer.endTag(null, "GRP");
            serializer.endTag(null, "LCNS");
            serializer.endDocument();
            return writer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initializeFormFields() {
        mFormFields = new LicenseFormFields(EmployeeName.getText().toString(),
                EmployeeID.getText().toString(),
                EmployeeContact.getText().toString(),
                LicenseType.getText().toString(),
                ProjectName.getText().toString(),
                CompanyName.getText().toString(),
                ContactPersonName.getText().toString(),
                ContactNumber.getText().toString(),
                CompanyLocation.getText().toString(),
                NumberOfLicenses.getText().toString());
    }

    private void validateForm(){
        if(EmployeeName.getText().toString().trim().equalsIgnoreCase("")){
            EmployeeName.setError("Enter Employee Name");
            isValid = false;
        }
        else if(EmployeeID.getText().toString().trim().equalsIgnoreCase("")){
            EmployeeID.setError("Enter Employee ID");
            isValid = false;
        }
        else if(EmployeeContact.getText().toString().trim().equalsIgnoreCase("") ||
                (EmployeeContact.getText().toString().trim().length()!= 10)){
            EmployeeContact.setError("Enter valid contact");
            isValid = false;
        }
        else if(ProjectName.getText().toString().trim().equalsIgnoreCase("")){
            ProjectName.setError("Enter Project Name");
            isValid = false;
        }
        else if(CompanyName.getText().toString().trim().equalsIgnoreCase("")){
            CompanyName.setError("Enter Company Name");
            isValid = false;
        }
        else if(ContactPersonName.getText().toString().trim().equalsIgnoreCase("")){
            ContactPersonName.setError("Enter Contact Person Name");
            isValid = false;
        }
        else if(ContactNumber.getText().toString().trim().equalsIgnoreCase("")){
            ContactNumber.setError("Enter Contact Name");
            isValid = false;
        }
        else if(CompanyLocation.getText().toString().trim().equalsIgnoreCase("")){
            CompanyLocation.setError("Enter Company Location");
            isValid = false;
        }
        else if(NumberOfLicenses.getText().toString().trim().equalsIgnoreCase("")){
            NumberOfLicenses.setError("Enter Number of License");
            isValid = false;
        }
        else isValid = true;
    }
}
