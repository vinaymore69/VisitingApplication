package in.edu.visitingapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RequestDetailsActivity extends AppCompatActivity {

    TextView tvId, tvUserId, tvName, tvEmail, tvPhone, tvPurpose, tvDate, tvTime, tvUniqueKey, tvStatus;
    Button btnAccept, btnReject;
    String requestType;
    DatabaseHelper databaseHelper;
    int requestId; // to store the request id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

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
        btnAccept = findViewById(R.id.btnAccept);
        btnReject = findViewById(R.id.btnReject);

        databaseHelper = new DatabaseHelper(this);

        // Retrieve details from Intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            requestId = extras.getInt("id");
            tvId.setText("ID: " + requestId);
            tvUserId.setText("User: " + extras.getString("user_id"));
            tvName.setText("Name: " + extras.getString("name"));
            tvEmail.setText("Email: " + extras.getString("email"));
            tvPhone.setText("Phone: " + extras.getString("phone"));
            tvPurpose.setText("Purpose: " + extras.getString("purpose"));
            tvDate.setText("Date: " + extras.getString("date"));
            tvTime.setText("Time: " + extras.getString("time"));
            tvUniqueKey.setText("Key: " + extras.getString("unique_key"));
            tvStatus.setText("Status: " + extras.getString("status"));
            requestType = extras.getString("requestType");
        }

        // Only show Accept/Reject buttons for pending requests
        if ("pending".equalsIgnoreCase(requestType)) {
            btnAccept.setVisibility(View.VISIBLE);
            btnReject.setVisibility(View.VISIBLE);
        } else {
            btnAccept.setVisibility(View.GONE);
            btnReject.setVisibility(View.GONE);
        }

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the database status to "approved"
                if (databaseHelper.updateRequestStatus(requestId, "approved")) {
                    tvStatus.setText("Status: Approved");
                    Toast.makeText(RequestDetailsActivity.this, "Request Approved", Toast.LENGTH_SHORT).show();
                    btnAccept.setVisibility(View.GONE);
                    btnReject.setVisibility(View.GONE);
                } else {
                    Toast.makeText(RequestDetailsActivity.this, "Failed to update status", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the database status to "rejected"
                if (databaseHelper.updateRequestStatus(requestId, "rejected")) {
                    tvStatus.setText("Status: Rejected");
                    Toast.makeText(RequestDetailsActivity.this, "Request Rejected", Toast.LENGTH_SHORT).show();
                    btnAccept.setVisibility(View.GONE);
                    btnReject.setVisibility(View.GONE);
                } else {
                    Toast.makeText(RequestDetailsActivity.this, "Failed to update status", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
