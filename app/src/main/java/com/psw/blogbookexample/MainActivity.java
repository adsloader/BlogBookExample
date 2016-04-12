package com.psw.blogbookexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WebView wbMain;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUpUI();

    }

    private class WebClient extends WebViewClient {

        // URL 호출하기 전...
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        // webpage를 모두 읽었을 때,
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
    private void setUpUI() {
        wbMain = (WebView) findViewById(R.id.wbMain);

        // 무조건해야 한다. 웹페이지 진행상황을 관리하는 클래스
        wbMain.setWebViewClient(new WebClient());

        // 세팅을 가져오고 설정한다.
        WebSettings set = wbMain.getSettings();

        // 자바스크립틀을 사용가능하게 하고 zoom을 false한다.
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(false);

        // 이동한다.
        wbMain.loadUrl("http://www.vintageappmaker.com/");
        toolbar.setTitle("공식페이지");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            // history back 구현
            if( wbMain.canGoBack() ){
                wbMain.goBack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            wbMain.loadUrl("http://www.vintageappmaker.com/");

        } else if (id == R.id.nav_tumbler) {
            wbMain.loadUrl("http://vintageappmaker.tumblr.com/");

        } else if (id == R.id.nav_naver) {
            wbMain.loadUrl("http://blog.naver.com/adsloader");

        } else if (id == R.id.nav_map) {
            wbMain.loadUrl("https://www.google.co.kr/maps/place/37%C2%B028'35.0%22N+126%C2%B058'51.4%22E/@37.476381,126.9788637,17z/data=!3m1!4b1!4m2!3m1!1s0x0:0x0");

        } else if (id == R.id.nav_send) {
            sendEmail();

        } else if (id == R.id.nav_apps) {
            shareApps();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void shareApps() {

        Intent marketLaunch = new Intent(Intent.ACTION_VIEW);
        marketLaunch.setData(Uri.parse("market://search?id=Vintage appMaker"));
        startActivity(marketLaunch);
    }

    private void sendEmail() {
        Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("plain/text");

        String[] tos = { "aaa@naver.com" };
        it.putExtra(Intent.EXTRA_EMAIL, tos);
        it.putExtra(Intent.EXTRA_SUBJECT, "문의메일입니다.");
        it.putExtra(Intent.EXTRA_TEXT, "[자동생성]");
        startActivity(it);
    }
}
