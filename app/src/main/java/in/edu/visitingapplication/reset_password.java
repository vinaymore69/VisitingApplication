package in.edu.visitingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class reset_password extends AppCompatActivity {

    EditText emailField, newPasswordField, confirmPasswordField;
    Button resetButton;
    TextView redirectToLogin, redirectToSignup;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        db = new DatabaseHelper(this);

        emailField = findViewById(R.id.reset_email);
        newPasswordField = findViewById(R.id.new_password);
        confirmPasswordField = findViewById(R.id.confirm_password);
        resetButton = findViewById(R.id.reset_button);
        redirectToLogin = findViewById(R.id.redirect_to_login);
        redirectToSignup = findViewById(R.id.redirect_to_signup);

        // Reset password logic
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailField.getText().toString().trim();
                String newPass = newPasswordField.getText().toString().trim();
                String confirmPass = confirmPasswordField.getText().toString().trim();

                if (!newPass.equals(confirmPass)) {
                    Toast.makeText(reset_password.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (db.checkEmail(email)) {
                    boolean updated = db.updatePassword(email, newPass);
                    if (updated) {
                        Toast.makeText(reset_password.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                        // Optionally, redirect to login after a successful update
                        startActivity(new Intent(reset_password.this, Login.class));
                    } else {
                        Toast.makeText(reset_password.this, "Password update failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(reset_password.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Redirect to Login if user remembers their password
        redirectToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(reset_password.this, Login.class));
            }
        });

        // Redirect to Sign Up if the user is not registered
        redirectToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(reset_password.this, Signup.class));
            }
        });
    }
}
