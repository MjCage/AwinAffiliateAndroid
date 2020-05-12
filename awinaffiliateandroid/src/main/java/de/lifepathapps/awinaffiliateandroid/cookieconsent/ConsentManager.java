package de.lifepathapps.awinaffiliateandroid.cookieconsent;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.fragment.app.FragmentManager;

public class ConsentManager {
    private Context context;
    private SharedPreferences preferences;

    public ConsentManager(Context context){
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Boolean getConsent(String cookiePolicyUrl, FragmentManager fragmentManager){
        String consentString = preferences.getString("cookieConsent", null);

        if (consentString == null){
            CookieConsentSheet consentSheet = new CookieConsentSheet(context, cookiePolicyUrl, false);
            consentSheet.show(fragmentManager,"cookieRequest");
            consentString = preferences.getString("cookieConsent", null);
        }

        if (consentString == null)
            return null;
        else if (consentString.equals("true"))
            return true;
        else if (consentString.equals("false"))
            return false;
        else
            return null;
    }

    public void editConsent(String cookiePolicyUrl, FragmentManager fragmentManager){
        CookieConsentSheet consentSheet = new CookieConsentSheet(context, cookiePolicyUrl, true);
        consentSheet.show(fragmentManager,"cookieRequest");
    }
}
