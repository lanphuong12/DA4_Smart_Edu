package com.example.smart_edu.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.smart_edu.Admin.QlySach.Theme.Theme_adapter;
import com.example.smart_edu.Admin.QlySach.Theme.Theme_main;
import com.example.smart_edu.MainActivity;
import com.example.smart_edu.Model.Class;
import com.example.smart_edu.Model.Student;
import com.example.smart_edu.Model.Theme;
import com.example.smart_edu.R;
import com.example.smart_edu.Server.APIService;
import com.example.smart_edu.Server.Dataservice;
import com.example.smart_edu.User.Phantrang.Vocab_main;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_main2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ListView listView;

    TextView name, email;
    ImageView Img;
    Student student;

    GoogleSignInAccount signInAccount;
    ArrayList<Theme> listTheme;
    public static Theme theme = new Theme();
    Theme_adapter theme_adapter;

    int id_book = 0;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        Anhxa();
        GetData(1);
//        GetData_intent();

        actionToolBar();
        ActionViewFlipper();

        Header_Nav();
//        int id = student.getIdClass();
        //Toast.makeText(this, "Id class = " + id, Toast.LENGTH_LONG).show();

//        GetDataBook(1);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                theme = listTheme.get(position);
                Intent intent = new Intent(activity_main2.this, Study.class);
                intent.putExtra("theme", listTheme.get(position));
                startActivity(intent);
            }
        });
    }

//    private void GetData_intent() {
//        student = (Student) getIntent().getSerializableExtra("student");
//    }

    private void GetDataBook( int id_class ){
        ArrayList<Class> listLop = new ArrayList<>();
        Dataservice dataservice = APIService.getService();
        Call<List<Class>> callback = dataservice.GetDataClass();
        callback.enqueue(new Callback<List<Class>>() {
            @Override
            public void onResponse(Call<List<Class>> call, Response<List<Class>> response) {
                ArrayList<Class> lophoc = (ArrayList<Class>) response.body();

                for (Class lop : lophoc){
                    listLop.add(lop);
                }
                for (int i=0; i< listLop.size(); i++){
                    while (listLop.get(i).getIdClass() == (id_class)){
                        id_book = lophoc.get(i).getIdBook();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Class>> call, Throwable t) {
                Toast.makeText(activity_main2.this, "Lấy dữ liệu thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(activity_main2.this, "Id book = " + id_book, Toast.LENGTH_LONG).show();
    }

    private void GetData(int id_book){
        Dataservice dataservice = APIService.getService();
        Call<List<Theme>> callback = dataservice.GetTheme_book(id_book);

        callback.enqueue(new Callback<List<Theme>>() {

            @Override
            public void onResponse(Call<List<Theme>> call, Response<List<Theme>> response) {
                ArrayList<Theme> theme = (ArrayList<Theme>) response.body();
                for (Theme the: theme){
                    listTheme.add(the);
                }
                theme_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Theme>> call, Throwable t) {

                Toast.makeText(activity_main2.this, "Lấy dữ liệu thất bại ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Header_Nav() {
        View headerView = navigationView.getHeaderView(0);
        Img = headerView.findViewById(R.id.imgv_user);
        name = headerView.findViewById(R.id.txtFullName);
        email = headerView.findViewById(R.id.txt_email);
        if(signInAccount != null){
            name.setText(signInAccount.getDisplayName());
            email.setText(signInAccount.getEmail());
            Uri img = signInAccount.getPhotoUrl();
            Picasso.get().load(img).into(Img);
        }
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://lanphuong1209.000webhostapp.com/Image/Quangcao/smKid.jpg");
        mangquangcao.add("https://lanphuong1209.000webhostapp.com/Image/Quangcao/sm_macky.jpg");
        mangquangcao.add("https://lanphuong1209.000webhostapp.com/Image/Quangcao/smHLW.jpg");
        mangquangcao.add("https://lanphuong1209.000webhostapp.com/Image/Quangcao/smedu.jpg");


        for(int i=0; i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out);
        viewFlipper.setInAnimation(animation_slide);
        viewFlipper.setOutAnimation(animation_slide_out);
    }


    private void actionToolBar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("Trang chủ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu_main2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbar_main2);
        viewFlipper = findViewById(R.id.viewFlipper);
        navigationView = findViewById(R.id.nav_view);
        listView = findViewById(R.id.lv_theme);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawer_layout);

        listTheme = new ArrayList<>();
        theme_adapter = new Theme_adapter(listTheme,activity_main2.this);
        listView.setAdapter(theme_adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_update_info) {
            Intent intent = new Intent(activity_main2.this, update_info.class);
            intent.putExtra("id_student", student);
            startActivity(intent);

        } else if (id == R.id.nav_study) {
            Intent intent = new Intent(activity_main2.this, Theme_main.class);
            startActivity(intent);

        } else if (id == R.id.nav_vocabulary) {
            Intent intent = new Intent(activity_main2.this, Vocab_main.class);
            startActivity(intent);

        } else if (id == R.id.nav_connectSM) {
//            Intent intent = new Intent(activity_main2.this, send_message.class);
//            startActivity(intent);

        } else if (id == R.id.nav_sign_out) {
//            FirebaseAuth.getInstance().signOut();
//            Intent intent = new Intent(activity_main2.this, MainActivity.class);
//            startActivity(intent);
            Sign_out();

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Sign_out(){
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(this,gso);
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    FirebaseAuth.getInstance().signOut(); // very important if you are using firebase.
                    Intent login_intent = new Intent(activity_main2.this,MainActivity.class);
                    login_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); // clear previous task (optional)
                    startActivity(login_intent);
                }
            }
        });
    }

}
