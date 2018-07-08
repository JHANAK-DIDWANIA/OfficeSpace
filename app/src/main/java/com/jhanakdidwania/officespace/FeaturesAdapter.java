 package com.jhanakdidwania.officespace;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FeaturesAdapter extends RecyclerView.Adapter<FeaturesAdapter.MyViewHolder> {

    private HashMap<Integer,Integer> selectedItems;
    private List<Features> mFeatures;
    private Context context;
    public String rangeFinal = "";


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView fName, fDescription;
        public EditText mRange;
        public Spinner mSpinner;
        public List<Features> mFeaturesList = new ArrayList<Features>();

        public MyViewHolder(View itemView, List<Features> mFeaturesList) {
            super(itemView);

            this.mFeaturesList = mFeaturesList;
            itemView.setOnClickListener(this);  //whenever user clicks on the view, onClick method will be invoked

            fName = (TextView)itemView.findViewById(R.id.feature_name);
            fDescription = (TextView)itemView.findViewById(R.id.feature_description);
            mSpinner = (Spinner) itemView.findViewById(R.id.action);
            mRange = (EditText) itemView.findViewById(R.id.rangeItem);

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int position = getAdapterPosition();
                    Log.d("OfficeSpace", "Item position: "+position);
                    Log.d("OfficeSpace", "Spinner position: "+pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        @Override
        public void onClick(View v) {
            int position;
            position = getAdapterPosition();
            Features feature = this.mFeaturesList.get(position);
            Log.d("OfficeSpace", "Position is: "+position);
        }
    }


    public FeaturesAdapter(List<Features> features, Context context) {
        this.mFeatures = features;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.features_list_item, parent, false);
        return new MyViewHolder(itemView, mFeatures);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Features feature = mFeatures.get(position); //to get the current object from the list
        holder.fName.setText(feature.getName());
        holder.fDescription.setText(feature.getDescription());

        if(position == mFeatures.size()-1){
            holder.mRange.setVisibility(View.VISIBLE);
            if(!rangeFinal.equalsIgnoreCase("")){
                holder.mRange.setText(rangeFinal);
            }

            holder.mRange.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    rangeFinal = holder.mRange.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(!holder.mRange.getText().toString().equalsIgnoreCase("")){
                        int range = Integer.parseInt(holder.mRange.getText().toString());
                        if((range < feature.getRANGE_MIN()) || (range> feature.getRANGE_MAX())){
                            Toast.makeText(context, "Invalid Range",Toast.LENGTH_SHORT).show();
                            holder.mRange.setText("");
                        }else{
                            rangeFinal = holder.mRange.getText().toString();
                            mFeatures.get(position).setVal(Integer.parseInt(rangeFinal));
                        }
                    }
                }
            });

//            holder.mRange.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL){
//                        int range = Integer.parseInt(holder.mRange.getText().toString());
//                        if((range < feature.getRANGE_MIN()) || (range> feature.getRANGE_MAX())){
//                            Toast.makeText(context, "Invalid Range",Toast.LENGTH_SHORT).show();
//                            holder.mRange.setText("");
//                            return true;
//                        }else{
//                            rangeFinal = holder.mRange.getText().toString();
//                            //mFeatures.get(position).setVal(Integer.parseInt(rangeFinal));
//                            return false;
//                        }
//                    }
//                    return false;
//                }
//            });
            holder.mSpinner.setVisibility(View.INVISIBLE);
        }
        else{
            holder.mRange.setVisibility(View.INVISIBLE);
            holder.mSpinner.setSelection(feature.getVal());
            holder.mSpinner.setVisibility(View.VISIBLE);
            holder.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    mFeatures.get(position).setVal(pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mFeatures.size();
    }
}
