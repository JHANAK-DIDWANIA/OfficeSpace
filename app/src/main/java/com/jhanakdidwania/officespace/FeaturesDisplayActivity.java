package com.jhanakdidwania.officespace;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.TRANSPARENT;

public class FeaturesDisplayActivity extends AppCompatActivity{

    XmlParser obj = new XmlParser();
    List<Features> features = new ArrayList<Features>();
    List<Features> tempBASIC = null;
    List<Features> tempSILVER = null;
    List<Features> tempGOLD = null;
    List<Features> tempPLATINUM = null;

    TextView mTextView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    TextView dateDisplay;
    Spinner mySpinner;
    int day;
    int month;
    int year;
    int size = 0;
    static int version = 0;

    int val;
    String versions[]= new String[]{"BASIC", "SILVER", "GOLD", "PLATINUM"};
    String value[]= new String[]{"ENABLE","HIDE", "DISABLE"};
    String name;
    String description;

    private RecyclerView recyclerView;
    private FeaturesAdapter mAdapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.features_display_intent);

        size = features.size();

        if(savedInstanceState != null){
            day = savedInstanceState.getInt("day");
            month = savedInstanceState.getInt("month");
            year = savedInstanceState.getInt("year");
            size = savedInstanceState.getInt("size");
            tempBASIC = (List<Features>) savedInstanceState.getSerializable("BASIC_LIST");
        }

        mTextView = (TextView)findViewById(R.id.application_textview);
        dateDisplay = (TextView) findViewById(R.id.dateTextView);

        Intent whoInitiatedMe = getIntent();

        whoInitiatedMe.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        if(whoInitiatedMe.hasExtra(Intent.EXTRA_TEXT)) {
            String text = whoInitiatedMe.getStringExtra(Intent.EXTRA_TEXT);
            mTextView.setText(text);
        }

        //getting data from xml file
            try {
                InputStream myInputStream = getApplicationContext().getAssets().open("mainLicenseFile.xml");
                features = (obj.parse(myInputStream));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            //data retrieved from xml

        //getting separate list of features corresponding to available versions
        tempBASIC = populate_list(0);
        tempSILVER = populate_list(1);
        tempGOLD = populate_list(2);
        tempPLATINUM = populate_list(3);

        //setting up recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new FeaturesAdapter(tempBASIC, this);
        recyclerView.setAdapter(mAdapter);
        // done setting recycler view


        //settings for date picker dialog
        dateDisplay.setText(day+"/"+month+"/"+year);
        dateDisplay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(FeaturesDisplayActivity.this,
                        R.style.DialogTheme,
                        mDateSetListener,
                        year, month, day);
                datePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth) {
                month = month1+1;
                day = dayOfMonth;
                year = year1;
                dateDisplay.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };
        //date picker done



        //spinner dialog
        mySpinner = findViewById(R.id.spinner);
        mySpinner.setSelection(0);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    version = 0;
                    mAdapter.notifyDataSetChanged();
                    mAdapter = new FeaturesAdapter(tempBASIC, getApplicationContext());
                    recyclerView.setAdapter(mAdapter);
                }
                if(position == 1){
                    version = 1;
                    mAdapter.notifyDataSetChanged();
                    mAdapter = new FeaturesAdapter(tempSILVER, getApplicationContext());
                    recyclerView.setAdapter(mAdapter);
                }
                if(position == 2){
                    version = 2;
                    mAdapter.notifyDataSetChanged();
                    mAdapter = new FeaturesAdapter(tempGOLD, getApplicationContext());
                    recyclerView.setAdapter(mAdapter);
                }
                if(position == 3){
                    version = 3;
                    mAdapter.notifyDataSetChanged();
                    mAdapter = new FeaturesAdapter(tempPLATINUM, getApplicationContext());
                    recyclerView.setAdapter(mAdapter);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private List<Features> populate_list(int selectedVersion) {
        List<Features> temporary = new ArrayList<Features>();
        for (Features element : features){
            if(element.getTitle().equalsIgnoreCase("Days")){
                if(element.getDescription().equalsIgnoreCase("day")) day = element.getVal();
                if(element.getDescription().equalsIgnoreCase("month")) month = element.getVal();
                if(element.getDescription().equalsIgnoreCase("year")) year = element.getVal();
                continue;
            }
            else if(element.getTitle().equalsIgnoreCase("Range")){
                temporary.add(element);
                continue;
            }
            else{
                if(selectedVersion == 0 && element.getVER_BASIC()==1){
                    temporary.add(element);
                }
                else if(selectedVersion == 1 && element.getVER_SILVER()==1){
                    temporary.add(element);
                }
                else if(selectedVersion == 2 && element.getVER_GOLD()==1){
                    temporary.add(element);
                }
                else if(selectedVersion == 3 && element.getVER_PLATINUM()==1){
                    temporary.add(element);
                }
                Log.d("OfficeSpace",name+" "+description+" "+value[val]);
            }
        }
        return temporary;
    }

    public void ProceedToNextStep(View view) {
        String temp = mAdapter.rangeFinal;
        Log.d("OfficeSpace", "version selected is: "+ versions[version]);
        Log.d("OfficeSpace", "range selected is: "+ mAdapter.rangeFinal);
        Log.d("OfficeSpace", day+"/"+month+"/"+year);

        if(!mAdapter.rangeFinal.equalsIgnoreCase("")){

            Intent intent = new Intent(FeaturesDisplayActivity.this, LicenseGenerator.class);

            intent.putExtra("LicenseType", versions[version]);
            intent.putExtra("Day", day);
            intent.putExtra("Month", month);
            intent.putExtra("Year", year);

            if(version == 0){
                intent.putExtra("FeatureList", (Serializable) tempBASIC);
            }else if(version == 1){
                intent.putExtra("FeatureList", (Serializable) tempSILVER);
            }else if(version == 2){
                intent.putExtra("FeatureList", (Serializable) tempGOLD);
            }else if(version == 3){
                intent.putExtra("FeatureList", (Serializable) tempPLATINUM);
            }
            startActivity(intent);
        }
    }


    //
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        return true;
//    }

}
