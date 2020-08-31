package com.ARCompany.Lotin_Kiril.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ARCompany.Lotin_Kiril.KIRIL_LOTIN;
import com.ARCompany.Lotin_Kiril.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public  EditText LotinH, KirilH;
    private View mainView;
    public  RadioButton rbtn1H, rbtn2H, rbtn3H;
    private int ChangedLength=0;
    private final String SETTING = "setting";
    private SharedPreferences preferences;
    private AdView adView;
    private LinearLayout layoutAd;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = mainView = inflater.inflate(R.layout.fragment_home, container, false);

        preferences = getActivity().getSharedPreferences(SETTING, Context.MODE_PRIVATE);

        ((EditText)root.findViewById(R.id.editText1)).setTextSize(preferences.getFloat("size", 16));
        ((EditText)root.findViewById(R.id.editText2)).setTextSize(preferences.getFloat("size", 16));

        install();
        return root;
    }

    public void install(){
        layoutAd=(LinearLayout)mainView.findViewById(R.id.ad_layout);
        adView = new AdView(getContext(), "623349368393641_628844701177441", AdSize.BANNER_HEIGHT_50);
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

        LotinH = (EditText) mainView.findViewById(R.id.editText1);
        KirilH=(EditText) mainView.findViewById(R.id.editText2);
        rbtn1H=(RadioButton)mainView.findViewById(R.id.rbtnKattaLotin);
        rbtn2H=(RadioButton)mainView.findViewById(R.id.rbtnKichikLotin);
        rbtn3H=(RadioButton)mainView.findViewById(R.id.rbtnNormalLotin);

        LotinH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(LotinH.isFocused()) {
                    KirilH.setText(KIRIL_LOTIN.Kiril_Lotin(LotinH.getText().toString(), true));
                    if(ChangedLength!=LotinH.getText().toString().length()) {
                        ChangedLength=LotinH.getText().toString().length();
                        if (rbtn1H.isChecked()) {
                            KirilH.setText(KirilH.getText().toString().toUpperCase());
                            LotinH.setText(LotinH.getText().toString().toUpperCase());
                        }
                        if (rbtn2H.isChecked()) {
                            LotinH.setText(LotinH.getText().toString().toLowerCase());
                            KirilH.setText(KirilH.getText().toString().toLowerCase());
                        }
                        if (rbtn3H.isChecked()) {
                            LotinH.setText(toNormalCase(LotinH.getText().toString()));
                            KirilH.setText(toNormalCase(KirilH.getText().toString()));
                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        KirilH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (KirilH.isFocused()) {
                    LotinH.setText(KIRIL_LOTIN.Kiril_Lotin(KirilH.getText().toString(), false));
                    if(ChangedLength!=LotinH.getText().toString().length()) {
                        ChangedLength=LotinH.getText().toString().length();
                        if (rbtn1H.isChecked()) {
                            KirilH.setText(KirilH.getText().toString().toUpperCase());
                            LotinH.setText(LotinH.getText().toString().toUpperCase());
                        }
                        if (rbtn2H.isChecked()) {
                            LotinH.setText(LotinH.getText().toString().toLowerCase());
                            KirilH.setText(KirilH.getText().toString().toLowerCase());
                        }
                        if (rbtn3H.isChecked()) {
                            LotinH.setText(toNormalCase(LotinH.getText().toString()));
                            KirilH.setText(toNormalCase(KirilH.getText().toString()));
                        }}
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private String toNormalCase(String toString) {
        toString=toString.toLowerCase();
        String text = "";
        try {
            if (toString.contains(".")) {
                String[] strings = toString.split("[.]");
                for (int i = 0; i < strings.length; i++) {
                    while (strings[i].startsWith(" ")) {
                        strings[i] = strings[i].replaceFirst(" ", "");
                    }
                    strings[i] = strings[i].replaceFirst(strings[i].substring(0, 1), strings[i].substring(0, 1).toUpperCase());
                    text += strings[i] + ". ";
                }
            } else {
                if (toString != "") {
                    while (toString.startsWith(" ")) {
                        toString = toString.replaceFirst(" ", "");
                    }
                    toString = toString.replaceFirst(toString.substring(0, 1), toString.substring(0, 1).toUpperCase());

                    return toString;
                }
            }
        }
        catch(Exception ex){
            //Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return text;
    }


}