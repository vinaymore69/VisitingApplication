package in.edu.visitingapplication;

public class VisitRequest {
    private int id;
    private String userId;
    private String name;
    private String email;
    private String phone;
    private String purpose;
    private String date;
    private String time;
    private String uniqueKey;
    private String status;

    public VisitRequest(int id, String userId, String name, String email, String phone, String purpose,
                        String date, String time, String uniqueKey, String status) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.purpose = purpose;
        this.date = date;
        this.time = time;
        this.uniqueKey = uniqueKey;
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPurpose() { return purpose; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getUniqueKey() { return uniqueKey; }
    public String getStatus() { return status; }
}
