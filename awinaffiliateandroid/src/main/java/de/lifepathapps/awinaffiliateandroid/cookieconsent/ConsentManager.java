package de.lifepathapps.awinaffiliateandroid.cookieconsent;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.fragment.app.FragmentManager;

public class ConsentManager {
    private Context context;
    private SharedPreferences preferences;
    private ConsentListener listener;

    public ConsentManager(Context context){
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.listener = null;
    }

    public void setConsentListener(ConsentListener listener) {
        this.listener = listener;
    }

    public Boolean getConsent(String cookiePolicyUrl, FragmentManager fragmentManager){
        String consentString = preferences.getString("cookieConsent", null);

        if (consentString == null){
            CookieConsentSheet consentSheet = new CookieConsentSheet(context, listener, cookiePolicyUrl, false);
            consentSheet.show(fragmentManager,"cookieRequest");
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
        CookieConsentSheet consentSheet = new CookieConsentSheet(context, listener, cookiePolicyUrl, true);
        consentSheet.show(fragmentManager,"cookieRequest");
    }

    public interface ConsentListener {
        void onConsentChanged();
    }
}
