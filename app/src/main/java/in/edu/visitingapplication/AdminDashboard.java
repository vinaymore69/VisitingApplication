package in.edu.visitingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboard extends AppCompatActivity {

    private Button btnPending, btnAccepted, btnRejected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnPending = findViewById(R.id.btnPendingRequests);
        btnAccepted = findViewById(R.id.btnAcceptedRequests);
        btnRejected = findViewById(R.id.btnRejectedRequests);

        btnPending.setOnClickListener(v -> openRequestActivity("pending"));
        btnAccepted.setOnClickListener(v -> openRequestActivity("approved"));
        btnRejected.setOnClickListener(v -> openRequestActivity("rejected"));
    }

    private void openRequestActivity(String type) {
        Intent intent = new Intent(AdminDashboard.this, RequestActivity.class);
        intent.putExtra("requestType", type);
        startActivity(intent);
    }
}
