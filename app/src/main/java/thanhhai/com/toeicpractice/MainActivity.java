package thanhhai.com.toeicpractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import thanhhai.com.toeicpractice.Fragment.BottomNavigationFragment;
import thanhhai.com.toeicpractice.Fragment.KhoaNangCaoFragment;
import thanhhai.com.toeicpractice.Fragment.KhoaSoCapFragment;
import thanhhai.com.toeicpractice.Fragment.KhoaTrungCapFragment;
import thanhhai.com.toeicpractice.Fragment.SearchQuesFragment;
import thanhhai.com.toeicpractice.Score.ScoreFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationFragment bottomNavigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        bottomNavigationFragment = new BottomNavigationFragment();
        xuLyBottomNavigation();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_share) {
            String playStoreLink = "https://play.google.com/store/apps/details?id=" + getPackageName();
            String yourShareText = "Install this app " + playStoreLink;
            Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain").setText(yourShareText).getIntent();
            startActivity(Intent.createChooser(shareIntent, "Sharing Option"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_socap) {
            xulyKhoaSoCap();
        } else if (id == R.id.action_trungcap) {
            xulyKhoaTrungCap();
        } else if (id == R.id.action_nangcao) {
            xulyKhoaNangCao();
        } else if (id == R.id.action_search) {
            xuLySearch();
        } else if (id == R.id.action_score) {
            xuLyScore();
        } else if (id == R.id.action_home) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(bottomNavigationFragment.getTag());
            if (fragment == null) {
                xuLyBottomNavigation();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void xuLyScore() {
        ScoreFragment scoreFragment = new ScoreFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, scoreFragment, scoreFragment.getTag()).commit();
    }

    private void xuLySearch() {
        SearchQuesFragment searchQuesFragment = new SearchQuesFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, searchQuesFragment, searchQuesFragment.getTag()).commit();
    }

    private void xulyKhoaNangCao() {
        KhoaNangCaoFragment khoaNangCaoFragment = new KhoaNangCaoFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, khoaNangCaoFragment).addToBackStack(null).commit();
    }

    private void xulyKhoaTrungCap() {
        KhoaTrungCapFragment khoaTrungCapFragment = new KhoaTrungCapFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, khoaTrungCapFragment).addToBackStack(null).commit();
    }

    private void xulyKhoaSoCap() {
        KhoaSoCapFragment khoaSoCapFragment = new KhoaSoCapFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, khoaSoCapFragment).addToBackStack(null).commit();

    }

    private void xuLyBottomNavigation() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, bottomNavigationFragment, bottomNavigationFragment.getTag()).commit();
    }
}
