package th.ac.kmutnb.myprojectapp.ui.categoryfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import th.ac.kmutnb.myprojectapp.AccountActivity;
import th.ac.kmutnb.myprojectapp.LocationActivity;
import th.ac.kmutnb.myprojectapp.LoginActivity;
import th.ac.kmutnb.myprojectapp.R;

public class TabmenuActivity extends AppCompatActivity {

    private TabLayout tabLayout ;
    private ViewPager viewPager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabmenu);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpage);
        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new ChickenFragment(),"Chicken");
        vpAdapter.addFragment(new FastfoodFragment(),"FastFood");
        vpAdapter.addFragment(new DessertFragment(),"Dessert");
        vpAdapter.addFragment(new BeverageFragment(),"Beverage");
        viewPager.setAdapter(vpAdapter);

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu); //load item from xml
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_proflie:
                Intent itn = new Intent(this, AccountActivity.class);
                startActivity(itn);
                return true;
            case R.id.nav_location:
                Intent itn2 = new Intent(this, LocationActivity.class);
                startActivity(itn2);
                return true;
            case R.id.nav_login:
                startActivity(new Intent(this, LoginActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}