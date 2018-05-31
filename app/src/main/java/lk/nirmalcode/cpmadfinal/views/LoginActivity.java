package lk.nirmalcode.cpmadfinal.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import lk.nirmalcode.cpmadfinal.R;
import lk.nirmalcode.cpmadfinal.models.Staff;
import lk.nirmalcode.cpmadfinal.utils.DBHelper;
import lk.nirmalcode.cpmadfinal.utils.GlobalClass;

public class LoginActivity extends AppCompatActivity {

    DBHelper dbHelper;
    GlobalClass globalClass;
    static boolean loggedInError = false;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        globalClass = (GlobalClass) this.getApplication();
        dbHelper = new DBHelper();
        loggedInError = getIntent().getBooleanExtra(globalClass.REF_KEEP_LOGGED_ERROR,false);
        if (loggedInError) {
            Toast.makeText(LoginActivity.this,
                    R.string.error_login_logged_in,
                    Toast.LENGTH_LONG).show();
        }

        final TextInputEditText txtLoginUsername = findViewById(R.id.txtLoginUsername);
        final TextInputEditText txtLoginPassword = findViewById(R.id.txtLoginPassword);
        final CheckBox chkLoginKeepLoggedIn = findViewById(R.id.chkLoginKeepLoggedIn);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegisterNav);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = txtLoginUsername.getText().toString();
                final String password = txtLoginPassword.getText().toString();
                Query query = dbHelper.getReference().child(globalClass.REF_STAFF).orderByChild("ID").equalTo(username);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                            Staff staff = singleSnapshot.getValue(Staff.class);
                            if (staff != null && password.equals(staff.password)) {
                                String userKey = singleSnapshot.getKey();
                                Staff user1 = staff.setKey(userKey);
                                globalClass.setLoggedUser(user1);

                                if (chkLoginKeepLoggedIn.isChecked()) {
                                    preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this.getApplicationContext());
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putBoolean(globalClass.REF_KEEP_LOGGED_STATUS, true);
                                    editor.putString(globalClass.REF_KEEP_LOGGED_USER_KEY, userKey);
                                    editor.putString(globalClass.REF_KEEP_LOGGED_USER_PASSWORD, staff.password);
                                    editor.apply();
                                }

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this,
                                        getString(R.string.error_login_password),
                                        Toast.LENGTH_LONG).show();
                                txtLoginPassword.setError(getString(R.string.error_login_password));
                            }
                        }
                        if (dataSnapshot.getChildrenCount() < 1) {
                            Toast.makeText(LoginActivity.this,
                                    R.string.error_login_username,
                                    Toast.LENGTH_LONG).show();
                            txtLoginUsername.setError(getString(R.string.error_login_username));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(LoginActivity.this,
                                R.string.error_login_username,
                                Toast.LENGTH_LONG).show();
                        txtLoginUsername.setError(getString(R.string.error_login_username));
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}
