package lk.nirmalcode.cpmadfinal.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import lk.nirmalcode.cpmadfinal.R;
import lk.nirmalcode.cpmadfinal.models.Staff;
import lk.nirmalcode.cpmadfinal.utils.DBHelper;
import lk.nirmalcode.cpmadfinal.utils.GlobalClass;
import lk.nirmalcode.cpmadfinal.utils.Validations;

public class StudentRegisterActivity extends AppCompatActivity {

    GlobalClass globalClass;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        globalClass = (GlobalClass) this.getApplication();
        dbHelper = new DBHelper();

        final TextInputEditText txtRegisterUsername = findViewById(R.id.txtRegisterUsername);
        final TextInputEditText txtRegisterName = findViewById(R.id.txtRegisterName);
        final TextInputEditText txtRegisterPassword = findViewById(R.id.txtRegisterPassword);
        final TextInputEditText txtRegisterCPassword = findViewById(R.id.txtRegisterCPassword);
        final TextInputEditText txtRegisterEmail = findViewById(R.id.txtRegisterEmail);
        final TextInputEditText txtRegisterContact = findViewById(R.id.txtRegisterContact);

        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtRegisterUsername.getText().toString();
                String name = txtRegisterName.getText().toString();
                String password = txtRegisterPassword.getText().toString();
                String cpassword = txtRegisterCPassword.getText().toString();
                String email = txtRegisterEmail.getText().toString();
                String contact = txtRegisterContact.getText().toString();

                int status = Validations.validateUsername(username);
                boolean validEmail;
                boolean validName;
                boolean validUsername;
                boolean validPassword;
                boolean validYear;

                if (status == Validations.ERR_EMPTY) {
                    validUsername = false;
                    txtRegisterUsername.setError(getString(R.string.error_username_empty));
                } else if (status == Validations.ERR_INVALID) {
                    validUsername = false;
                    txtRegisterUsername.setError(getString(R.string.error_username));
                } else {
                    validUsername = true;
                }

                status = Validations.validateEmail(email);
                if (status == Validations.ERR_EMPTY) {
                    validEmail = false;
                    txtRegisterEmail.setError(getString(R.string.error_email_empty));
                } else if (status == Validations.ERR_INVALID) {
                    validEmail = false;
                    txtRegisterEmail.setError(getString(R.string.error_email));
                } else {
                    validEmail = true;
                }

                status = Validations.validatePassword(password, cpassword);
                if (status == Validations.ERR_EMPTY_1) {
                    validPassword = false;
                    txtRegisterPassword.setError(getString(R.string.error_password_empty));
                } else if (status == Validations.ERR_EMPTY_2) {
                    validPassword = false;
                    txtRegisterCPassword.setError(getString(R.string.error_confirm_password_empty));
                } else if (status == Validations.ERR_NOT_MATCH) {
                    validPassword = false;
                    txtRegisterPassword.setError(getString(R.string.error_password_mismatch));
                    txtRegisterCPassword.setError(getString(R.string.error_password_mismatch));
                } else {
                    validPassword = true;
                }

                validName = !TextUtils.isEmpty(name);

                if (validEmail && validUsername && validPassword &&  validName) {
//                    password = SecurityUtils.getSHA1Hex(password);
                    Staff user = new Staff(username, name, email, password, contact);
                    String key = dbHelper.pushObject(globalClass.REF_STAFF, user);
                    globalClass.setLoggedUser(user.setKey(key));
                    Toast.makeText(StudentRegisterActivity.this, "You are successfully registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(StudentRegisterActivity.this, MainActivity.class);
                    StudentRegisterActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(StudentRegisterActivity.this, "Registration error. Please try again", Toast.LENGTH_LONG).show();
                }



            }
        });
    }
}
