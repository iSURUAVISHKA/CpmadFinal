package lk.isuru.cpmadfinal.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import lk.isuru.cpmadfinal.R;
import lk.isuru.cpmadfinal.models.Student;
import lk.isuru.cpmadfinal.utils.DBHelper;
import lk.isuru.cpmadfinal.utils.GlobalClass;
import lk.isuru.cpmadfinal.utils.Validations;
import lk.isuru.cpmadfinal.views.MainActivity;

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
        final TextInputEditText txtRegisterEmail = findViewById(R.id.txtRegisterEmail);
        final TextInputEditText txtRegisterYear = findViewById(R.id.txtRegisterYear);
        final TextInputEditText txtRegisterFaculty = findViewById(R.id.txtRegisterFaculty);
        final TextInputEditText txtRegisterBatch = findViewById(R.id.txtRegisterBatch);
        final TextInputEditText txtRegisterCourse = findViewById(R.id.txtRegisterCourse);
        final TextInputEditText txtRegisterContact = findViewById(R.id.txtRegisterContact);

        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtRegisterUsername.getText().toString();
                String name = txtRegisterName.getText().toString();
                String email = txtRegisterEmail.getText().toString();
                String year = txtRegisterYear.getText().toString();
                String faculty = txtRegisterFaculty.getText().toString();
                String batch = txtRegisterBatch.getText().toString();
                String course = txtRegisterCourse.getText().toString();
                String contact = txtRegisterContact.getText().toString();

                int status ;
                boolean validEmail;
                boolean validName;
                boolean validPassword;
                boolean validYear;

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

                if (TextUtils.isEmpty(year)) {
                    validYear = false;
                    txtRegisterYear.setError(getString(R.string.error_year_empty));
                } else if (Integer.parseInt(year) < 0 && Integer.parseInt(year) > 4) {
                    validYear = false;
                    txtRegisterYear.setError(getString(R.string.error_year));
                } else {
                    validYear = true;
                }

                validName = !TextUtils.isEmpty(name);

                if (validEmail && validYear && validName) {
//                    password = SecurityUtils.getSHA1Hex(password);
                    Student user = new Student(username, name, year, faculty, batch, course, email, contact);
                    String key = dbHelper.pushObject(globalClass.REF_STUDENTS, user);
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
