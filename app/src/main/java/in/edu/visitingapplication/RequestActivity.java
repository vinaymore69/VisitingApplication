package in.edu.visitingapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> requestDisplayList;
    private ArrayList<VisitRequest> requestList;
    private String requestType;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        listView = findViewById(R.id.listViewRequests);
        TextView title = findViewById(R.id.tvRequestTitle);
        databaseHelper = new DatabaseHelper(this);

        // Retrieve request type from intent extras ("pending", "approved", "rejected")
        requestType = getIntent().getStringExtra("requestType");
        if (requestType != null) {
            title.setText(requestType.toUpperCase() + " Requests");
        } else {
            title.setText("Requests");
        }

        requestList = new ArrayList<>();
        requestDisplayList = new ArrayList<>();
        loadRequests();

        // Use built-in ArrayAdapter with simple_list_item_1
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requestDisplayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VisitRequest request = requestList.get(position);
                Intent intent = new Intent(RequestActivity.this, RequestDetailsActivity.class);
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
                intent.putExtra("requestType", requestType);
                startActivity(intent);
            }
        });
    }

    private void loadRequests() {
        Cursor cursor = databaseHelper.getUserRequestsByStatus(requestType);
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
                requestList.add(request);
                // Build a simple display string, e.g., "John Doe (pending)"
                requestDisplayList.add(name + " (" + status + ")");
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
