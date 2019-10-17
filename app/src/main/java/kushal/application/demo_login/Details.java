package kushal.application.demo_login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Details extends AppCompatActivity {

    LinearLayout save;
    EditText name, age, gender;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;

    public static final String PATIENT = "Patient";
    public static final String PERSONAL_INFO = "Personal Info";
    public static final String NAME = "Name";
    public static final String AGE = "Age";
    public static final String GENDER = "Gender";


    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference ref = firebaseDatabase.getReference().child(PATIENT)
                                            .child(auth.getCurrentUser().getPhoneNumber())
                                            .child(PERSONAL_INFO);

                if (TextUtils.isEmpty(name.getText())){
                    name.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(age.getText())){
                    age.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(gender.getText())){
                    gender.setError("Required");
                    return;
                }

                ref.child(NAME).setValue(name.getText().toString().trim());
                ref.child(AGE).setValue(age.getText().toString().trim());
                ref.child(GENDER).setValue(gender.getText().toString().trim());


                Toast.makeText(Details.this, "saved", Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = getSharedPreferences("shared_pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putBoolean("is_prev", true);
                editor.apply();

                finish();

            }
        });



    }
}
