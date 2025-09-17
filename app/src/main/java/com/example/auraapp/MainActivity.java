package com.example.auraapp;

import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);

        //  WebView 기본 설정
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);                     // 자바스크립트 허용
        webSettings.setDomStorageEnabled(true);                     // localStorage / sessionStorage 허용
        webSettings.setDatabaseEnabled(true);                       // Web SQL DB 사용 허용
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); // JS window.open() 허용
        webSettings.setLoadWithOverviewMode(true);                  // 화면 크기에 맞게 조정
        webSettings.setUseWideViewPort(true);                       // 뷰포트 지원
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);         // 캐시 모드 기본값

        //  쿠키 허용
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);

        //  WebViewClient & ChromeClient
        webView.setWebViewClient(new WebViewClient());     // 기본 웹뷰 클라이언트
        webView.setWebChromeClient(new WebChromeClient()); // JS alert, console.log 등 지원

        //  User-Agent 보정 (필요시)
        String ua = webSettings.getUserAgentString();
        webSettings.setUserAgentString(
                ua + " Chrome/114.0.5735.196 Mobile Safari/537.36"
        );

        //  WebView 디버깅 (개발용, chrome://inspect 에서 확인 가능)
        WebView.setWebContentsDebuggingEnabled(true);

        //  URL 로드
        webView.loadUrl("https://port-next-voicefront-mfdxlwgf654f9392.sel3.cloudtype.app");

        //  뒤로가기 처리
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });
    }
}
