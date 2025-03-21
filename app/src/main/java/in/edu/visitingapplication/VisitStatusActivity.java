package in.edu.visitingapplication;

import android.content.SharedPreferences;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class VisitStatusActivity extends AppCompatActivity {

    private ListView lvVisitRequests;
    private DatabaseHelper databaseHelper;
    private ArrayList<VisitRequest> visitRequestList;
    private VisitStatusAdapter adapter;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_status);

        lvVisitRequests = findViewById(R.id.lvVisitRequests);
        databaseHelper = new DatabaseHelper(this);

        // Retrieve the currently logged-in user's ID (email) from SharedPreferences.
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        currentUserId = sharedPreferences.getString("user_email", "");

        // Optionally: if currentUserId is empty, you may redirect to Login.
        if (currentUserId.isEmpty()) {
            // Redirect to login if not logged in.
            startActivity(new Intent(VisitStatusActivity.this, Login.class));
            finish();
            return;
        }

        visitRequestList = new ArrayList<>();
        loadVisitRequests();

        adapter = new VisitStatusAdapter(this, visitRequestList);
        lvVisitRequests.setAdapter(adapter);

        lvVisitRequests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VisitRequest request = visitRequestList.get(position);
                Intent intent = new Intent(VisitStatusActivity.this, VisitCardActivity.class);
                // Pass all the details via intent extras
                intent.putExtra("id", request.getId());
                intent.putExtra("user_id", request.getUserId());
                intent.putExtra("name", request.getName());
                intent.putExtra("email", request.getEmail());
                intent.putExtra("phone", request.getPhone());
                intent.putExtra("purpose", request.getPurpose());
                intent.putExtra("date", request.getDate());
                intent.putExtra("time", request.getTime());
                intent.putExtra("unique_key", request.getUniqueKey());
                intent.putExtra("status", request.getStatus());
                startActivity(intent);
            }
        });
    }

    private void loadVisitRequests() {
        Cursor cursor = databaseHelper.getUserRequestsByUserId(currentUserId);
        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String userId = cursor.getString(cursor.getColumnIndex("user_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String purpose = cursor.getString(cursor.getColumnIndex("purpose"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String uniqueKey = cursor.getString(cursor.getColumnIndex("unique_key"));
                String status = cursor.getString(cursor.getColumnIndex("status"));

                VisitRequest request = new VisitRequest(id, userId, name, email, phone, purpose, date, time, uniqueKey, status);
                visitRequestList.add(request);

            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
