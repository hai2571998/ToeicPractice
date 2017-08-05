package thanhhai.com.toeicpractice.HomeModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;

import thanhhai.com.toeicpractice.R;

public class GrammarActivity extends AppCompatActivity {
    private WebView webView;
    private String mFileName;
    private String mTitleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        mTitleName = intent.getStringExtra("titleFile");
        getSupportActionBar().setTitle(mTitleName);
        mFileName = intent.getStringExtra("nameFile");
        webView = (WebView) findViewById(R.id.webView1);
        webView.loadUrl("file:///android_asset/grammar/"+mFileName+".htm");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
