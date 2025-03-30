package in.edu.visitingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class admin_login extends AppCompatActivity {

    EditText adminEmail, adminPassword;
    Button adminLoginButton;
    TextView redirectToUser;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        databaseHelper = new DatabaseHelper(this);

        adminEmail = findViewById(R.id.admin_email);
        adminPassword = findViewById(R.id.admin_password);
        adminLoginButton = findViewById(R.id.admin_login_button);
        redirectToUser = findViewById(R.id.redirect_to_user);

        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = adminEmail.getText().toString().trim();
                String password = adminPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(admin_login.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    // Validate against the adminUsers table
                    Boolean checkCredentials = databaseHelper.checkAdminCredentials(email, password);
                    if (checkCredentials) {
                        Toast.makeText(admin_login.this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
                        // Redirect to the Admin Dashboard
                        startActivity(new Intent(admin_login.this, AdminDashboard.class));
                    } else {
                        Toast.makeText(admin_login.this, "Invalid Admin Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Redirect to regular User Login screen if needed
        redirectToUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(admin_login.this, Login.class));
            }
        });
    }
}
