package ie.ul.o.daysaver;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class GymActivity extends MainActivity implements DefaultView.OnFragmentInteractionListener,CustomView.OnFragmentInteractionListener,DefaultView_workoutviewer.OnFragmentInteractionListener {
    private static String TAG="GymActivity";
    private SectionPageAdapter sectionPageAdapter;
    private RelativeLayout m_r,endo_r,ecto_r;
    private LinearLayout b_r;
    private ViewPager viewPager;
    private DefaultView dv;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CustomView.progressDialog.dismiss();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gym);
        Log.d(TAG, "onCreate: Starting.");
        sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        dv = new DefaultView();

        m_r = (RelativeLayout) findViewById(R.id.mesomorph_view);
        endo_r = (RelativeLayout) findViewById(R.id.endomorph_view);
        ecto_r = (RelativeLayout) findViewById(R.id.ectomorph_view);
        b_r = (LinearLayout) findViewById(R.id.selectABodyType);
        FragmentManager fragmentManager=getSupportFragmentManager();




    }

    private void setupViewPager(ViewPager viewPager)
    {
        SectionPageAdapter adapter=new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragments(new DefaultView(),getString(R.string.defaultG));
        adapter.addFragments(new CustomView(),getString(R.string.CustomG));

        viewPager.setAdapter(adapter);
}

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void startGymPlan() {
       // startActivity(new Intent("ie.ul.o.daysaver.DefaultGymplan"));
    }
}
