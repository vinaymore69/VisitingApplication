package in.edu.visitingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VisitPassDetailsActivity extends AppCompatActivity {

    TextView tvId, tvUserId, tvName, tvEmail, tvPhone, tvPurpose, tvDate, tvTime, tvUniqueKey, tvStatus;
    Button btnApplicantEntered;
    DatabaseHelper databaseHelper;
    int requestId;
    VisitRequest currentRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_pass_details);

        tvId = findViewById(R.id.tvId);
        tvUserId = findViewById(R.id.tvUserId);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvPurpose = findViewById(R.id.tvPurpose);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvUniqueKey = findViewById(R.id.tvUniqueKey);
        tvStatus = findViewById(R.id.tvStatus);
        btnApplicantEntered = findViewById(R.id.btnApplicantEntered);

        databaseHelper = new DatabaseHelper(this);

        // Retrieve details from intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            requestId = extras.getInt("id");
            String userId = extras.getString("user_id");
            String name = extras.getString("name");
            String email = extras.getString("email");
            String phone = extras.getString("phone");
            String purpose = extras.getString("purpose");
            String date = extras.getString("date");
            String time = extras.getString("time");
            String uniqueKey = extras.getString("unique_key");
            String status = extras.getString("status");

            tvId.setText("ID: " + requestId);
            tvUserId.setText("User: " + userId);
            tvName.setText("Name: " + name);
            tvEmail.setText("Email: " + email);
            tvPhone.setText("Phone: " + phone);
            tvPurpose.setText("Purpose: " + purpose);
            tvDate.setText("Date: " + date);
            tvTime.setText("Time: " + time);
            tvUniqueKey.setText("Key: " + uniqueKey);
            tvStatus.setText("Status: " + status);

            currentRequest = new VisitRequest(requestId, userId, name, email, phone, purpose, date, time, uniqueKey, status);
        }

        btnApplicantEntered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Insert into visitedUsers table
                if (databaseHelper.insertVisitedUser(currentRequest)) {
                    // Delete from userRequests table
                    if (databaseHelper.deleteUserRequest(requestId)) {
                        Toast.makeText(VisitPassDetailsActivity.this, "Visit Pass marked as used", Toast.LENGTH_SHORT).show();
                        // Optionally, redirect to main activity or refresh list
                        startActivity(new Intent(VisitPassDetailsActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(VisitPassDetailsActivity.this, "Failed to remove request", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(VisitPassDetailsActivity.this, "Failed to update status", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
