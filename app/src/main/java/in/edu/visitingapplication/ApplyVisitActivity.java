package in.edu.visitingapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class ApplyVisitActivity extends AppCompatActivity {

    EditText etName, etEmail, etPhone, etPurpose, etDate, etTime;
    Button btnApplyVisit;
    DatabaseHelper databaseHelper;
    private static final String TAG = "ApplyVisitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_visit);

        databaseHelper = new DatabaseHelper(this);

        // Initialize form fields
        etName = findViewById(R.id.apply_name);
        etEmail = findViewById(R.id.apply_email);
        etPhone = findViewById(R.id.apply_phone);
        etPurpose = findViewById(R.id.apply_purpose);
        etDate = findViewById(R.id.apply_date);
        etTime = findViewById(R.id.apply_time);
        btnApplyVisit = findViewById(R.id.apply_visit_button);

        // Retrieve the primary key (logged-in user's email) from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String userId = sharedPreferences.getString("user_email", "");
        if (userId.isEmpty()) {
            Toast.makeText(ApplyVisitActivity.this, "User not logged in. Please login again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ApplyVisitActivity.this, Login.class));
            finish();
            return;
        }

        btnApplyVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user inputs
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String purpose = etPurpose.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                String time = etTime.getText().toString().trim();

                // Basic validation
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                        purpose.isEmpty() || date.isEmpty() || time.isEmpty()) {
                    Toast.makeText(ApplyVisitActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Log the values for debugging
                Log.d(TAG, "UserID: " + userId);
                Log.d(TAG, "Name: " + name);
                Log.d(TAG, "Email: " + email);
                Log.d(TAG, "Phone: " + phone);
                Log.d(TAG, "Purpose: " + purpose);
                Log.d(TAG, "Date: " + date);
                Log.d(TAG, "Time: " + time);

                // Generate a unique key (10 characters, all uppercase letters and digits)
                String uniqueKey = generateRandomKey();
                Log.d(TAG, "Unique Key: " + uniqueKey);

                // Insert the request into the database
                Boolean isInserted = databaseHelper.insertUserRequest(userId, name, email, phone, purpose, date, time, uniqueKey);
                if (isInserted) {
                    Toast.makeText(ApplyVisitActivity.this, "Visit Request Submitted Successfully", Toast.LENGTH_SHORT).show();
                    // Optionally, clear the form
                    etName.setText("");
                    etEmail.setText("");
                    etPhone.setText("");
                    etPurpose.setText("");
                    etDate.setText("");
                    etTime.setText("");
                } else {
                    Toast.makeText(ApplyVisitActivity.this, "Failed to submit request", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Database insertion failed.");
                }
            }
        });
    }

    // Generates a random 10-character key with uppercase letters and digits
    private String generateRandomKey() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}
