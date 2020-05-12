package de.lifepathapps.awinaffiliateandroid.cookieconsent;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import de.lifepathapps.awinaffiliateandroid.R;

public class CookieConsentSheet extends BottomSheetDialogFragment {

    private Context context;
    private String cookiePolicyUrl;
    private SharedPreferences preferences;
    private boolean edit;

    public CookieConsentSheet(Context context, String cookiePolicyUrl, boolean edit){
        this.context = context;
        this.cookiePolicyUrl = cookiePolicyUrl;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.edit = edit;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cookie_consent_sheet, container, false);

        final LinearLayout settingsLayout;
        settingsLayout = view.findViewById(R.id.dialog_settings_layout);

        TextView infoText;
        infoText = view.findViewById(R.id.cookieInformationTextView);

        final CheckBox checkBoxAdCookie;
        checkBoxAdCookie = view.findViewById(R.id.checkbox_ad_cookies);
        String consentString = preferences.getString("cookieConsent", null);
        if (edit && consentString != null && consentString.equals("true"))
            checkBoxAdCookie.setChecked(true);

        Button buttonAccept, buttonSettings, buttonSaveSettings;
        buttonAccept = view.findViewById(R.id.button_accept_all_cookies);
        buttonSettings = view.findViewById(R.id.button_open_cookie_settings);
        buttonSaveSettings = view.findViewById(R.id.button_save_cookie_settings);

        String text = context.getResources().getString(R.string.cookie_permission);
        int start = text.indexOf("1");
        int end = text.indexOf("2") - 1;

        text = text.replace("1", "").replace("2","");
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cookiePolicyUrl)));
                } catch (ActivityNotFoundException e){
                    //Do nothing
                }
            }
        };

        ss.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        infoText.setText(ss);
        infoText.setMovementMethod(LinkMovementMethod.getInstance());

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().putString("cookieConsent", "true").apply();
                dismiss();
            }
        });

        buttonSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxAdCookie.isChecked())
                    preferences.edit().putString("cookieConsent", "true").apply();
                else
                    preferences.edit().putString("cookieConsent", "false").apply();

                dismiss();
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (settingsLayout.getVisibility() == View.GONE)
                    settingsLayout.setVisibility(View.VISIBLE);
                else
                    settingsLayout.setVisibility(View.GONE);
            }
        });

        return view;
    }
}
