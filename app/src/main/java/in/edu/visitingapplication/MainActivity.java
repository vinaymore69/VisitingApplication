package in.edu.visitingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnApplyVisit, btnCheckStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnApplyVisit = findViewById(R.id.btnApplyVisit);
        btnCheckStatus = findViewById(R.id.btnCheckStatus);

        // When the user clicks "Apply for Visit", open the ApplyVisitActivity
        btnApplyVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ApplyVisitActivity.class);
                startActivity(intent);
            }
        });

        // When the user clicks "Check Visit Status", open the VisitStatusActivity
        btnCheckStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VisitStatusActivity.class);
                startActivity(intent);
            }
        });
    }
}
