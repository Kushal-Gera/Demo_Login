package kushal.application.demo_auth;

import androidx.appcompat.app.AppCompatActivity;

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
    DatabaseReference ref;
    FirebaseAuth auth;

    public static final String PATIENT = "Patient";
    public static final String NAME = "Name";
    public static final String AGE = "Age";
    public static final String GENDER = "Gender";

    public static final String PERSONAL_INFO = "Personal Info";
    public static final String PAST_REP = "Past Reports";
    public static final String MEDICINES = "Medicines";
    public static final String APPOINTMENTS = "Appointments";



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


                ref = firebaseDatabase.getReference().child(PATIENT)
                                            .child(auth.getCurrentUser().getPhoneNumber());

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

                saveData();


                Toast.makeText(Details.this, "saved", Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = getSharedPreferences("shared_pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putBoolean("is_prev", true);
                editor.apply();

                finish();

            }
        });



    }

    private void saveData() {

        //personal ref
        DatabaseReference personal_ref = ref.child(PERSONAL_INFO);
        personal_ref.child(NAME).setValue(name.getText().toString().trim());
        personal_ref.child(AGE).setValue(age.getText().toString().trim());
        personal_ref.child(GENDER).setValue(gender.getText().toString().trim());

        //past reports reference
        DatabaseReference past_rep = ref.child(PAST_REP);
        past_rep.child("test 1").setValue("null");

        //medicines ref
        DatabaseReference med_ref = ref.child(MEDICINES);
        med_ref.child("null").setValue("0-0-0-0");

        //appointments reference
        DatabaseReference appoint_ref = ref.child(APPOINTMENTS);
        appoint_ref.child("Date").setValue("null");
        appoint_ref.child("Remarks").setValue("null");

    }
}
