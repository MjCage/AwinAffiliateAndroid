package de.lifepathapps.awinaffiliateandroid.ads;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import java.util.List;
import java.util.Locale;

import de.lifepathapps.awinaffiliateandroid.R;

public class InterstitialAd {
    private Dialog customDialog;

    private String htmlData;

    private boolean initialised;
    private String countryCode;
    private boolean contentAvailable;

    @SuppressLint("SetJavaScriptEnabled")
    public InterstitialAd(Context context, List<HtmlContent> htmlContents, boolean consent) {
        customDialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        customDialog.setContentView(R.layout.interstitial_ad);

        WebView webView = customDialog.findViewById(R.id.interstitial_webView);
        webView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        webView.getSettings().setJavaScriptEnabled(true);

        countryCode = Locale.getDefault().getCountry();

        contentAvailable = true;

        UrlBuilder builder = new UrlBuilder();
        for (HtmlContent htmlContent : htmlContents) {
            if (htmlContent.countryCode.equals(countryCode)) {
                htmlData = builder.build(htmlContent.s, htmlContent.v, htmlContent.q, htmlContent.r, consent);
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
}
