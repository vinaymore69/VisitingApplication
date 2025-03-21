package in.edu.visitingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class watchman_login extends AppCompatActivity {

    private static final String TAG = "WatchmanLogin";
    EditText watchmanEmail, watchmanPassword;
    Button watchmanLoginButton;
    TextView redirectToUser;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchman_login);

        databaseHelper = new DatabaseHelper(this);

        watchmanEmail = findViewById(R.id.watchman_email);
        watchmanPassword = findViewById(R.id.watchman_password);
        watchmanLoginButton = findViewById(R.id.watchman_login_button);
        redirectToUser = findViewById(R.id.redirect_to_user);

        watchmanLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = watchmanEmail.getText().toString().trim();
                String password = watchmanPassword.getText().toString().trim();

                // Log for debugging
                Log.d(TAG, "Attempting login with email: " + email + ", password: " + password);

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(watchman_login.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCredentials = databaseHelper.checkSubordinateCredentials(email, password);
                    if (checkCredentials) {
                        Toast.makeText(watchman_login.this, "Watchman Login Successful", Toast.LENGTH_SHORT).show();
                        // Open UserValidationActivity after a successful subordinate login.
                        startActivity(new Intent(watchman_login.this, UserValidationActivity.class));
                    } else {
                        Toast.makeText(watchman_login.this, "Invalid Watchman Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Redirect to regular User Login if needed
        redirectToUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(watchman_login.this, Login.class));
            }
        });
    }
}
