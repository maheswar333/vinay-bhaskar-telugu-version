package main.com.dvb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
//import com.twitter.sdk.android.tweetui.UserTimeline;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import main.com.dvb.adapters.Events_ExpandableListAdapter;
import main.com.dvb.adapters.ViewPagerAdapter;
import main.com.dvb.fragments.BioFragment;
import main.com.dvb.fragments.EvnetsFragment;
import main.com.dvb.fragments.ImpContacts;
import main.com.dvb.fragments.NotificationsFragment;
import main.com.dvb.fragments.HomeFragment;
import main.com.dvb.fragments.MyReportsFragment;
import main.com.dvb.fragments.ReportFragment;
import main.com.dvb.fragments.TourismFragment;
import main.com.dvb.homePageFragments.FacebookFragment;
import main.com.dvb.navigationFragments.ContactUs;
import main.com.dvb.navigationFragments.Feedback;
import main.com.dvb.navigationFragments.HisJourney;
import main.com.dvb.navigationFragments.MediaCoverage;
import main.com.dvb.navigationFragments.TrsGallery;
import main.com.dvb.navigationFragments.Videos;
import main.com.dvb.pojos.Contacts;
import main.com.dvb.pojos.MyReportsBean;

public class Dashboard_main extends AppCompatActivity implements View.OnClickListener{
    SharedPreferences sharedPreferences;
    final String DEFAULT_LAG = "Engilsh";
    TextView nav_title,nav_biography_textview,nav_myjourney_textview,nav_feedback_textview,nav_contactus_textview,nav_events_textview;
    public static int height, width;
    public static Context context;
    HomeFragment homeFragment;
    Events_ExpandableListAdapter expandableListAdapter;
    MyReportsFragment myReportsFragment;
    ActionBarDrawerToggle toggle;
    ImageView notifications;
    LinearLayout biography,myjourney,feedback,contactsus,events;
    Button language;
    private TextView home_text,report_text,myreport_text,tourism_text;
    public ArrayList<MyReportsBean> myReports;
    boolean checkHasData;
    String prefe_language;
    String lan;
//    public CardView card_view_report;

//    private FirebaseUser mFirebaseUser;
//    private FirebaseAuth auth;
    public String userId;
    FacebookFragment FBSavedInstanceState;

    public FragmentManager manager;
    FragmentTransaction transaction;
    public ProgressBar card_view_report;

    ImageView home,myReport,report,tourism;
    String isFromNotification="",notificatio_type;
    NotificationsFragment notificationsFragment;
    Toolbar toolbar;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
//        Fabric.with(this, new Twitter(authConfig));

//        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
//        Fabric.with(this, new TwitterCore(authConfig), new TweetUi(), new Twitter(authConfig), new TweetUi());



        setContentView(R.layout.home_layout);

//        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
//
//        loginButton.setCallback(new Callback<TwitterSession>() {
//            @Override
//            public void success(Result<TwitterSession> result) {
//                Log.e("","");
//                Toast.makeText(Dashboard_main.context, "Success", Toast.LENGTH_LONG).show();
//                // Do something with result, which provides a TwitterSession for making API calls
//            }
//
//            @Override
//            public void failure(TwitterException exception) {
//                // Do something on failure
//                Log.e("","");
//                Toast.makeText(Dashboard_main.context, "Failed", Toast.LENGTH_LONG).show();
//            }
//        });


//        userId = getIntent().getStringExtra("uid");
//        userId = "hello";
        notificatio_type = getIntent().getStringExtra("TYPE");

        keyHash();

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            FBSavedInstanceState = (FacebookFragment) getSupportFragmentManager().getFragment(savedInstanceState, "mContent");

        }

        Log.e("","");
//        FacebookSdk.sdkInitialize(getApplicationContext());

        FacebookSdk.sdkInitialize(getApplicationContext(), new FacebookSdk.InitializeCallback() {
            @Override
            public void onInitialized() {
                if(AccessToken.getCurrentAccessToken() == null){
                    System.out.println("not logged in yet");
                } else {
                    System.out.println("Logged in");
                }
            }
        });
        AppEventsLogger.activateApp(this);

//        auth = FirebaseAuth.getInstance();
//        mFirebaseUser = auth.getCurrentUser();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        context = Dashboard_main.this;
        myReports = new ArrayList<>();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeFragment = new HomeFragment();
        myReportsFragment = new MyReportsFragment();



//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        setupViewPager(viewPager);

//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
         biography = (LinearLayout) navigationView.findViewById(R.id.navbiography);
         myjourney = (LinearLayout) navigationView.findViewById(R.id.navmyjourney);
         feedback = (LinearLayout) navigationView.findViewById(R.id.navfeedback);
         contactsus = (LinearLayout) navigationView.findViewById(R.id.navcontactus);
         events = (LinearLayout) navigationView.findViewById(R.id.navevents);

        biography.setOnClickListener(this);
        myjourney.setOnClickListener(this);
        feedback.setOnClickListener(this);
        contactsus.setOnClickListener(this);
        events.setOnClickListener(this);

        language = (Button) findViewById(R.id.language);
//        card_view_report = (CardView) findViewById(R.id.card_view_report);
        card_view_report = (ProgressBar) findViewById(R.id.progressBar_report);
        home = (ImageView) findViewById(R.id.home_btn);
        myReport = (ImageView) findViewById(R.id.myreport_btn);
        report = (ImageView) findViewById(R.id.report_btn);
        tourism = (ImageView) findViewById(R.id.tourism_btn);
        notifications = (ImageView) findViewById(R.id.notifications);
        LinearLayout footerLayout = (LinearLayout) findViewById(R.id.footerLayout);
        LinearLayout homeLay = (LinearLayout) findViewById(R.id.homeLayout);
        LinearLayout myReportLay = (LinearLayout) findViewById(R.id.myReportsLayout);
        LinearLayout reportLay = (LinearLayout) findViewById(R.id.reportLayout);
        LinearLayout tourismLay = (LinearLayout) findViewById(R.id.tourismLayout);
        home_text = (TextView) findViewById(R.id.home_textview);
        report_text = (TextView) findViewById(R.id.report_textview);
        myreport_text = (TextView) findViewById(R.id.myreport_textview);
        tourism_text = (TextView) findViewById(R.id.tourism_textview);

        int layoutWidth = width/6;
        footerLayout.getLayoutParams().height = width/6;
        home.getLayoutParams().width = layoutWidth/2;
        home.getLayoutParams().height = layoutWidth/2;

        myReport.getLayoutParams().width = layoutWidth/2;
        myReport.getLayoutParams().height = layoutWidth/2;

        report.getLayoutParams().width = layoutWidth/2;
        report.getLayoutParams().height = layoutWidth/2;

        tourism.getLayoutParams().width = layoutWidth/2;
        tourism.getLayoutParams().height = layoutWidth/2;

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();


        storeLanguageInPref(DEFAULT_LAG);


        notificationsFragment = new NotificationsFragment();
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lang;
                if(language.getText().toString().equalsIgnoreCase(getString(R.string.language_english))){
                    lang = language.getText().toString();

                    storeLanguageInPref(lang);
                    changeLanguageToEnglish();


                }else{
                    lang = language.getText().toString();
                    storeLanguageInPref(lang);
                    changeLanguageToTelugu();

                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                nav_title = (TextView) findViewById(R.id.nav_title);
                nav_biography_textview = (TextView) findViewById(R.id.biography_text);
                nav_myjourney_textview = (TextView) findViewById(R.id.myjourney_text);
                nav_feedback_textview = (TextView) findViewById(R.id.feedback_text);
                nav_contactus_textview = (TextView) findViewById(R.id.contactus_text);
                nav_events_textview = (TextView) findViewById(R.id.events_text);
                sharedPreferences = Dashboard_main.context.getSharedPreferences(Constants.LANGUAGE_SHARED_PREF,0);
                 prefe_language  = sharedPreferences.getString("LANGUAGE","");
                //lang = "Engilsh";
                if (DEFAULT_LAG.equalsIgnoreCase(prefe_language)){

                    changeNavigationTitlesToEnglisg();

                }else{
                    changeNavigationTitlesToTelugu();

                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);


            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if(position == 0){
//                    homeFragment.refreshBanner();
//                }else if(position == 3){
//                    myReportsFragment.refreshView(myReports);
//                }
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if(position == 0){
//                    homeFragment.refreshBanner();
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        drawer.openDrawer(GravityCompat.START);


        homeLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Constants.FRAGMENT_SELECTED.equalsIgnoreCase("home")) {
                    card_view_report.setVisibility(View.GONE);
                    home.setImageResource(R.mipmap.home_active);
                    report.setImageResource(R.mipmap.report_normal);
                    myReport.setImageResource(R.mipmap.myreport_normal);
                    tourism.setImageResource(R.mipmap.tourism_normal);

                    homeFragment = new HomeFragment();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.main_fragment, homeFragment);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.commit();
                    setActionBarTitle(getResources().getString(R.string.app_name));
                    Constants.FRAGMENT_SELECTED = "home";
                }
            }
        });

        myReportLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Constants.FRAGMENT_SELECTED.equalsIgnoreCase("myReports")) {
                    Constants.myReportsList.clear();
                    card_view_report.setVisibility(View.GONE);

                    home.setImageResource(R.mipmap.home_normal);
                    report.setImageResource(R.mipmap.report_normal);
                    myReport.setImageResource(R.mipmap.myreport_active);
                    tourism.setImageResource(R.mipmap.tourism_normal);

//                FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.main_fragment, new MyReportsFragment());
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.commit();
                    setActionBarTitle(getResources().getString(R.string.myReportName));
                    Constants.FRAGMENT_SELECTED = "myReports";
                }
            }
        });

        reportLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Constants.FRAGMENT_SELECTED.equalsIgnoreCase("reports")) {
                    card_view_report.setVisibility(View.GONE);

                    home.setImageResource(R.mipmap.home_normal);
                    report.setImageResource(R.mipmap.report_active);
                    myReport.setImageResource(R.mipmap.myreport_normal);
                    tourism.setImageResource(R.mipmap.tourism_normal);

//                FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.main_fragment, new ReportFragment());
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.commit();
                    setActionBarTitle(getResources().getString(R.string.reportName));
                    Constants.FRAGMENT_SELECTED = "reports";
                }
            }
        });

        tourismLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Constants.FRAGMENT_SELECTED.equalsIgnoreCase("tourism")) {
                    card_view_report.setVisibility(View.GONE);

                    home.setImageResource(R.mipmap.home_normal);
                    report.setImageResource(R.mipmap.report_normal);
                    myReport.setImageResource(R.mipmap.myreport_normal);
                    tourism.setImageResource(R.mipmap.tourism_active);

                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.main_fragment, new TourismFragment());
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.commit();
                    setActionBarTitle(getResources().getString(R.string.tourismName));
                    Constants.FRAGMENT_SELECTED = "tourism";
                }
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Constants.FRAGMENT_SELECTED.equalsIgnoreCase("updates")) {
                    card_view_report.setVisibility(View.GONE);

                    home.setImageResource(R.mipmap.home_normal);
                    report.setImageResource(R.mipmap.report_normal);
                    myReport.setImageResource(R.mipmap.report_normal);
                    tourism.setImageResource(R.mipmap.tourism_normal);

                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.main_fragment, new NotificationsFragment());
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.commit();
                    setActionBarTitle(getResources().getString(R.string.notificationName));
                    Constants.FRAGMENT_SELECTED = "updates";
                }
            }
        });

        if(isFromNotification!= null){


            String e ="events",s="single";
            if (e.equals(notificatio_type)){

                Toast.makeText(context, ""+notificatio_type, Toast.LENGTH_SHORT).show();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new EvnetsFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

//                expandableListAdapter = new Events_ExpandableListAdapter();
//                expandableListAdapter.onGroupExpanded(0);


            }else if (s.equals(notificatio_type)){
                Constants.myReportsList.clear();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new MyReportsFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
            }
            else {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, homeFragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
            }
        }else{
            Constants.newsBeanArrayList.clear();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment, homeFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
        }

    }

    private void changeNavigationTitlesToEnglisg() {
        nav_title.setText(getString(R.string.app_name));
        nav_biography_textview.setText(getString(R.string.nav_ebiography));
        nav_myjourney_textview.setText(getString(R.string.nav_emyjourney));
        nav_feedback_textview.setText(getString(R.string.nav_efeedback) );
        nav_contactus_textview.setText(getString(R.string.nav_econtactus));
        nav_events_textview.setText(getString(R.string.nav_eevents));
    }

    private void changeNavigationTitlesToTelugu() {
        nav_title.setText(getString(R.string.telugu_appname));
        nav_biography_textview.setText(getString(R.string.nav_biography));
        nav_myjourney_textview.setText(getString(R.string.nav_myjourney));
        nav_feedback_textview.setText(getString(R.string.nav_feedback) );
        nav_contactus_textview.setText(getString(R.string.nav_contactus));
        nav_events_textview.setText(getString(R.string.nav_events));

    }

    //method to change the language to telugu
    private void changeLanguageToTelugu() {

       language.setText(getString(R.string.language_english));
        home_text.setText(getString(R.string.telugu_home));
        report_text.setText(getString(R.string.telugu_report));
        myreport_text.setText(getString(R.string.telugu_myreports));
        tourism_text.setText(getString(R.string.telugu_tourism));
        BioFragment b =(BioFragment) getSupportFragmentManager().findFragmentByTag("BIOGRAPHY");
        if (b != null && b.isVisible()) {
            setActionBarTitle(getString(R.string.nav_biography));

        }else{
            setActionBarTitle(getString(R.string.english_appname));
        }



    }
//method to change the language to english
    private void changeLanguageToEnglish(){

        language.setText(getString(R.string.language_telugu));
        home_text.setText(getString(R.string.english_home));
        report_text.setText(getString(R.string.english_report));
        myreport_text.setText(getString(R.string.english_myReports));
        tourism_text.setText(getString(R.string.english_tourism));

        BioFragment b =(BioFragment) getSupportFragmentManager().findFragmentByTag("BIOGRAPHY");
        if (b != null && b.isVisible()) {
            setActionBarTitle(getResources().getString(R.string.bioGraphyName));

        }else{
            setActionBarTitle(getString(R.string.english_appname));
        }
    }
    //save the language in preferences
    private void storeLanguageInPref(String lang) {
        sharedPreferences= getApplicationContext().getSharedPreferences(Constants.LANGUAGE_SHARED_PREF, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LANGUAGE", lang);
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        loginButton.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constants.myReportsList.clear();
        Constants.newsBeanArrayList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.main_fragment, homeFragment);
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        transaction.commit();
    }

    public boolean checkIsNetworkAvailable(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(homeFragment, "Home");
        adapter.addFrag(new BioFragment(), "Diary");
        adapter.addFrag(new ReportFragment(), "Report");
        adapter.addFrag(myReportsFragment, "My Reports");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getSupportFragmentManager().putFragment(outState, "mContent", FacebookFragment.facebookFragment);
    }

    public void setActionBarTitle(String title){
        toolbar.setTitle(title);

    }

    public void resetFooter(){
        home.setImageResource(R.mipmap.home_normal);
        report.setImageResource(R.mipmap.report_normal);
        myReport.setImageResource(R.mipmap.report_normal);
        tourism.setImageResource(R.mipmap.tourism_normal);
    }

   /* @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        card_view_report.setVisibility(View.GONE);

        if(id == R.id.media_coverage){
            if(!Constants.FRAGMENT_SELECTED.equalsIgnoreCase("mediaCoverage")){
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new MediaCoverage());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

                Constants.FRAGMENT_SELECTED = "mediaCoverage";
                setActionBarTitle(getResources().getString(R.string.notificationName));
                resetFooter();
            }
        }else if(id == R.id.his_journey){
            if(!Constants.FRAGMENT_SELECTED.equalsIgnoreCase("journey")){
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new HisJourney());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

                Constants.FRAGMENT_SELECTED = "journey";
                setActionBarTitle(getResources().getString(R.string.myJourneyName));
                resetFooter();
            }
        }else if(id == R.id.videos){
            if(!Constants.FRAGMENT_SELECTED.equalsIgnoreCase("videos")){
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new Videos());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

                Constants.FRAGMENT_SELECTED = "videos";
                setActionBarTitle(getResources().getString(R.string.notificationName));
                resetFooter();
            }
        }else if(id == R.id.trs_gallery){
            if(!Constants.FRAGMENT_SELECTED.equalsIgnoreCase("trsGallery")){
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new TrsGallery());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

                Constants.FRAGMENT_SELECTED = "trsGallery";
                setActionBarTitle(getResources().getString(R.string.notificationName));
                resetFooter();
            }
        }else if(id == R.id.contactus){
            if(!Constants.FRAGMENT_SELECTED.equalsIgnoreCase("contactus")){
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new ContactUs());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

                Constants.FRAGMENT_SELECTED = "contactus";
                setActionBarTitle(getResources().getString(R.string.countactusName));
                resetFooter();
            }

        }else if(id == R.id.feedback){
            if(!Constants.FRAGMENT_SELECTED.equalsIgnoreCase("feedback")){
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new Feedback());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

                Constants.FRAGMENT_SELECTED = "feedback";
                setActionBarTitle(getResources().getString(R.string.feedbackName));
                resetFooter();
            }

        }else if(id == R.id.biography){
            if(!Constants.FRAGMENT_SELECTED.equalsIgnoreCase("biography")){
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new BioFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

                Constants.FRAGMENT_SELECTED = "biography";
                setActionBarTitle(getResources().getString(R.string.bioGraphyName));
                resetFooter();
            }

        }
//        else if(id == R.id.imp_contacts){
//            if(!Constants.FRAGMENT_SELECTED.equalsIgnoreCase("imp_contacts")){
//                FragmentManager manager = getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.main_fragment, new ImpContacts());
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                transaction.commit();
//
//                Constants.FRAGMENT_SELECTED = "imp_contacts";
//                setActionBarTitle(getResources().getString(R.string.imp_contacts));
//                resetFooter();
//            }

 //       }
           else if(id == R.id.events){
            if(!Constants.FRAGMENT_SELECTED.equalsIgnoreCase("events")){
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new EvnetsFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

                Constants.FRAGMENT_SELECTED = "events";
                setActionBarTitle(getResources().getString(R.string.events));
                resetFooter();
            }

        }

//        if (id == R.id.nav_camara) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
*/

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        toggle.onConfigurationChanged(newConfig);
    }

    public void keyHash(){
        PackageInfo info;
        try {
//            info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String something = new String(Base64.encode(md.digest(), 0));
//                //String something = new String(Base64.encodeBytes(md.digest()));
//                Log.e("hash key", something);
//            }

            info = getPackageManager().getPackageInfo(
                    "main.com.dvb",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            Constants.FRAGMENT_SELECTED = "";
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "press back again to close the APP", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        FragmentManager manager;
        FragmentTransaction transaction;
        DrawerLayout drawer;
        switch(v.getId()) {
            case R.id.navbiography:
                manager = getSupportFragmentManager();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new BioFragment(),"BIOGRAPHY");
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

                if (DEFAULT_LAG.equalsIgnoreCase(prefe_language)){
                    setActionBarTitle(getResources().getString(R.string.bioGraphyName));

                }else{
                    setActionBarTitle(getResources().getString(R.string.nav_biography));
                }
                Constants.FRAGMENT_SELECTED = "biography";
                resetFooter();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.navmyjourney:

                manager = getSupportFragmentManager();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new HisJourney());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

                Constants.FRAGMENT_SELECTED = "journey";
                setActionBarTitle(getResources().getString(R.string.myJourneyName));
                resetFooter();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.navfeedback:
                manager = getSupportFragmentManager();
                 transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new Feedback());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

                Constants.FRAGMENT_SELECTED = "feedback";
                setActionBarTitle(getResources().getString(R.string.feedbackName));
                resetFooter();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.navcontactus:
                manager = getSupportFragmentManager();
                 transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new ContactUs());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

                Constants.FRAGMENT_SELECTED = "contactus";
                setActionBarTitle(getResources().getString(R.string.countactusName));
                resetFooter();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.navevents:
                 manager = getSupportFragmentManager();
                 transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, new EvnetsFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

                Constants.FRAGMENT_SELECTED = "events";
                setActionBarTitle(getResources().getString(R.string.events));
                resetFooter();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
    }
}