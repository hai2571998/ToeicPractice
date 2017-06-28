package thanhhai.com.toeicpractice;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import thanhhai.com.toeicpractice.Fragment.BottomNavigationFragment;
import thanhhai.com.toeicpractice.Fragment.SearchQuesFragment;
import thanhhai.com.toeicpractice.Fragment.ToeicAFragment;
import thanhhai.com.toeicpractice.Fragment.ToeicBFragment;
import thanhhai.com.toeicpractice.Fragment.ToeicCFragment;
import thanhhai.com.toeicpractice.Fragment.ToeicDFragment;
import thanhhai.com.toeicpractice.Fragment.ToeicEFragment;
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.toeicA) {
            xulyToeicA();
        } else if (id == R.id.toeicB) {
            xulyToeicB();
        } else if (id == R.id.toeicC) {
            xulyToeicC();
        } else if (id == R.id.toeicD) {
            xulyToeicD();
        } else if (id == R.id.toeicE) {
            xulyToeicE();
        } else if (id == R.id.search) {
            xuLySearch();
        } else if (id == R.id.score) {
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

    private void xulyToeicE() {
        ToeicEFragment toeicEFragment = new ToeicEFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, toeicEFragment, toeicEFragment.getTag()).commit();
    }

    private void xulyToeicD() {
        ToeicDFragment toeicDFragment = new ToeicDFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, toeicDFragment, toeicDFragment.getTag()).commit();
    }

    private void xulyToeicC() {
        ToeicCFragment toeicCFragment = new ToeicCFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, toeicCFragment, toeicCFragment.getTag()).commit();
    }

    private void xulyToeicB() {
        ToeicBFragment toeicBFragment = new ToeicBFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, toeicBFragment, toeicBFragment.getTag()).commit();
    }

    private void xulyToeicA() {
        ToeicAFragment toeicAFragment = new ToeicAFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, toeicAFragment).addToBackStack(null).commit();

    }

    private void xuLyBottomNavigation() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, bottomNavigationFragment,
                bottomNavigationFragment.getTag()).commit();
    }
}
