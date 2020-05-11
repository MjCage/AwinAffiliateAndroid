package de.lifepathapps.awinaffiliateandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.WebView;

import java.util.List;
import java.util.Locale;

public class BannerAd extends WebView {
    private String htmlData;

    @SuppressLint("SetJavaScriptEnabled")
    public BannerAd(Context context, List<HtmlContent> htmlContents, boolean consent) {
        super(context);
        this.setBackgroundColor(getResources().getColor(R.color.transparent));
        this.getSettings().setJavaScriptEnabled(true);

        String countryCode = Locale.getDefault().getCountry();

        UrlBuilder builder = new UrlBuilder();
        for (HtmlContent htmlContent : htmlContents) {
            if (htmlContent.countryCode.equals(countryCode)) {
                htmlData = builder.build(htmlContent.s, htmlContent.v, htmlContent.q, htmlContent.r, consent);
                break;
            }
        }

        if (htmlData != null)
            this.loadDataWithBaseURL(null, htmlData, "text/html", null, null);
    }
}
