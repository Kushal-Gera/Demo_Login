package kushal.application.demo_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Dialog dialog;
    String IS_PREVIOUS;
    public static final String PERSONAL_INFO = "Personal Info";
    public static final String NAME = "name";
    public static final String PATIENT = "Patient";

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.signout)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                });


        firebaseDatabase = FirebaseDatabase.getInstance();

        try {

            DatabaseReference ref = firebaseDatabase.getReference().child(PATIENT)
                    .child("+91966709958");

            Toast.makeText(this, "hello" + ref.getParent().toString(), Toast.LENGTH_SHORT).show();

        }catch (NullPointerException e){
//            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Details.class));

        }



    }

}