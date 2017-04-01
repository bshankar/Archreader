package com.nfshost.hyperatom.archreader;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class ContentFragment extends Fragment {

    public static WebView webView;
    public static final String assets =
            "file:///android_asset/arch-wiki/html/en/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        initWebView();
       return webView;
    }

    private void initWebView() {
        webView = new WebView(getActivity());
        webView.getSettings().setJavaScriptEnabled (true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.getSettings().setSupportMultipleWindows (false);
        webView.getSettings().setSupportZoom (false);
        webView.setVerticalScrollBarEnabled (false);
        webView.setHorizontalScrollBarEnabled (false);

        loadUrl(getString(R.string.home_url));

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }

                try {
                    webView.clearView();
                } catch (Exception e) {
                }

                if (webView.canGoBack()) {
                    Toast.makeText(getActivity(),
                            R.string.going_back_message,
                            Toast.LENGTH_SHORT);
                    webView.goBack();
                }

                loadUrl(getString(R.string.error_url));
            }
        });
    }

    private void loadUrl(String filename) {
        webView.loadUrl(assets + filename);
    }
}
