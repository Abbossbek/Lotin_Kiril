package com.ARCompany.Lotin_Kiril.ui.tools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ARCompany.Lotin_Kiril.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    Switch darkenSwitch;
    private final String SETTING = "setting";
    private SharedPreferences preferences;
    private Spinner spinner_lang, spinner_size;
    private LinearLayout layoutAd;
    private AdView adView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        layoutAd=(LinearLayout)root.findViewById(R.id.setting_ad_layout);
        adView = new AdView(getContext(), "623349368393641_628845394510705", AdSize.BANNER_HEIGHT_50);
        AdSettings.setTestMode(false);
        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                if(adError.getErrorCode()!=1001) {
                    adView.loadAd();
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        layoutAd.addView(adView);
        adView.loadAd();


        preferences = getActivity().getSharedPreferences(SETTING, Context.MODE_PRIVATE);

        darkenSwitch = ((Switch)root.findViewById(R.id.switch1));
        darkenSwitch.setChecked(preferences.getBoolean("darken", false));
        darkenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    preferences.edit().putBoolean("darken", b).apply();
                    getActivity().finish();
                    getActivity().startActivity(new Intent(getActivity(),getActivity().getClass()));
            }
        });

        spinner_size=(Spinner) root.findViewById(R.id.spinner2);
        spinner_size.setSelection(preferences.getInt("size_id", 0));

        spinner_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    preferences.edit().putInt("size_id",adapterView.getSelectedItemPosition()).apply();
                    preferences.edit().putFloat("size", Float.parseFloat(adapterView.getSelectedItem().toString())).apply();
                }
                catch (Exception ex){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_lang=(Spinner) root.findViewById(R.id.tools_lang_spinner);
        if(preferences.getString("lang", "uz").equals("uz")) {
            spinner_lang.setSelection(0);
        }else{
            spinner_lang.setSelection(1);
        }

        spinner_lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if(i==0 && !preferences.getString("lang", "uz").equals("uz")) {
                        preferences.edit().putString("lang", "uz").apply();
                        getActivity().finish();
                        getActivity().startActivity(new Intent(getActivity(),getActivity().getClass()));
                    }else{
                        if(i==1 && !preferences.getString("lang", "uz").equals("ru")) {
                            preferences.edit().putString("lang", "ru").apply();
                            getActivity().finish();
                            getActivity().startActivity(new Intent(getActivity(),getActivity().getClass()));
                        }
                    }

                }
                catch (Exception ex){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return root;
    }

    @Override
    public void onResume(){
        super.onResume();

    }

}