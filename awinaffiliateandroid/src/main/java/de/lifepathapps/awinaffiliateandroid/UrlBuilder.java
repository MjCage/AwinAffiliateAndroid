package de.lifepathapps.awinaffiliateandroid;

public class UrlBuilder {

    public String build(int s, int v, int q, int r, boolean consent){
        int cons = 0;
        if (consent)
            cons = 1;

        return "<center><a href=\"https://www.awin1.com/cread.php?s="+s+"&v="+v+"&q="+q+"&r="+r+"&cons="+cons+"\"><img src=\"https://www.awin1.com/cshow.php?s="+s+"&v="+v+"&q="+q+"&r="+r+"\" border=\"0\"></a></center>";
    }
}
