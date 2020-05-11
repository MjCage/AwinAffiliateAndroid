package de.lifepathapps.awinaffiliateandroid;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import java.util.List;
import java.util.Locale;

public class InterstitialAd {
    private Dialog customDialog;

    private String htmlData;

    private boolean initialised;
    private String countryCode;
    private boolean contentAvailable;

    @SuppressLint("SetJavaScriptEnabled")
    public InterstitialAd(Context context, List<IntertitialHtml> contentHtmls, boolean consent) {
        customDialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        customDialog.setContentView(R.layout.interstitial_ad);

        WebView webView = customDialog.findViewById(R.id.interstitial_webView);
        webView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        webView.getSettings().setJavaScriptEnabled(true);

        countryCode = Locale.getDefault().getCountry();

        contentAvailable = true;

        for (IntertitialHtml htmlContent : contentHtmls) {
            if (htmlContent.countryCode.equals(countryCode)) {
                htmlData = buildUrl(htmlContent.s, htmlContent.v, htmlContent.q, htmlContent.r, consent);
                break;
            }
        }

        if (htmlData != null)
            webView.loadDataWithBaseURL(null, htmlData, "text/html", null, null);
        else
            contentAvailable = false;

        ImageView closeButton = customDialog.findViewById(R.id.button_close_interstitial);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        initialised = true;
    }

    public void showDialog() {
        if (initialised && contentAvailable)
            customDialog.show();
    }

    public String getCountryCode(){
        return countryCode;
    }

    private String buildUrl(int s, int v, int q, int r, boolean consent){
        int cons = 0;
        if (consent)
            cons = 1;

        return "<center><a href=\"https://www.awin1.com/cread.php?s="+s+"&v="+v+"&q="+q+"&r="+r+"&cons="+cons+"\"><img src=\"https://www.awin1.com/cshow.php?s="+s+"&v="+v+"&q="+q+"&r="+r+"\" border=\"0\"></a></center>";
    }
}
