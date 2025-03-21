package in.edu.visitingapplication;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class VisitCardActivity extends AppCompatActivity {

    TextView tvId, tvUserId, tvName, tvEmail, tvPhone, tvPurpose, tvDate, tvTime, tvUniqueKey, tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_card);

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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tvId.setText("ID: " + extras.getInt("id"));
            tvUserId.setText("User: " + extras.getString("user_id"));
            tvName.setText("Name: " + extras.getString("name"));
            tvEmail.setText("Email: " + extras.getString("email"));
            tvPhone.setText("Phone: " + extras.getString("phone"));
            tvPurpose.setText("Purpose: " + extras.getString("purpose"));
            tvDate.setText("Date: " + extras.getString("date"));
            tvTime.setText("Time: " + extras.getString("time"));
            tvUniqueKey.setText("Key: " + extras.getString("unique_key"));
            tvStatus.setText("Status: " + extras.getString("status"));
        }
    }
}
