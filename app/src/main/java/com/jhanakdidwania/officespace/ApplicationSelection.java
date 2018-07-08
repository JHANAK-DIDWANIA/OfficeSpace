package com.jhanakdidwania.officespace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ApplicationSelection extends AppCompatActivity{
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    int selectedId = -1;
    private ImageView mButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_selection);
        mRadioButton1 = (RadioButton)findViewById(R.id.amos_radio_button);
        mRadioButton2 = (RadioButton)findViewById(R.id.m3s_radio_button);
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        mRadioGroup = (RadioGroup)findViewById(R.id.application_radio);
        mButton = (ImageView) findViewById(R.id.next_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedId = mRadioGroup.getCheckedRadioButtonId();
                if(selectedId == -1){
                    Toast.makeText(getApplicationContext(), "Please select an application", Toast.LENGTH_SHORT).show();
                }
                else{
                    RadioButton checkedButton = (RadioButton) findViewById(selectedId);
                    //checkedButton.setTextColor(getApplicationContext().getResources().getColor(R.color.statusBarColor));
                    Intent intent = new Intent(ApplicationSelection.this, FeaturesDisplayActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, checkedButton.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

}
