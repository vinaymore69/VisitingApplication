package in.edu.visitingapplication.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import in.edu.visitingapplication.R;

/**
 * Utility class for maintaining consistent UI styling across the application
 */
public class UIUtils {

    // Application's primary color
    private static final String PRIMARY_COLOR = "#116940";
    
    // Status colors
    private static final String STATUS_PENDING = "#FF9800";  // Orange
    private static final String STATUS_APPROVED = "#4CAF50"; // Green
    private static final String STATUS_REJECTED = "#F44336"; // Red
    private static final String STATUS_COMPLETED = "#3F51B5"; // Indigo

    /**
     * Updates the status indicator and badge based on status
     * @param statusIndicator The status indicator view
     * @param statusBadge The status badge TextView
     * @param status The status text (PENDING, APPROVED, REJECTED, etc.)
     */
    public static void updateStatusViews(View statusIndicator, TextView statusBadge, String status) {
        String statusText = status.toUpperCase();
        String colorHex;
        
        switch (statusText) {
            case "APPROVED":
                colorHex = STATUS_APPROVED;
                break;
            case "REJECTED":
                colorHex = STATUS_REJECTED;
                break;
            case "COMPLETED":
                colorHex = STATUS_COMPLETED;
                break;
            case "PENDING":
            default:
                colorHex = STATUS_PENDING;
                break;
        }
        
        // Update views with appropriate colors
        statusIndicator.setBackgroundColor(Color.parseColor(colorHex));
        statusBadge.setBackgroundColor(Color.parseColor(colorHex));
        statusBadge.setText(statusText);
    }
    
    /**
     * Returns the appropriate color resource for a given status
     * @param context The context
     * @param status The status string
     * @return The color resource ID
     */
    public static int getStatusColor(Context context, String status) {
        String statusText = status.toUpperCase();
        
        switch (statusText) {
            case "APPROVED":
                return Color.parseColor(STATUS_APPROVED);
            case "REJECTED":
                return Color.parseColor(STATUS_REJECTED);
            case "COMPLETED":
                return Color.parseColor(STATUS_COMPLETED);
            case "PENDING":
            default:
                return Color.parseColor(STATUS_PENDING);
        }
    }
    
    /**
     * Applies consistent styling to the status TextView
     * @param statusTextView The TextView to style
     * @param status The status string
     */
    public static void styleStatusText(TextView statusTextView, String status) {
        String statusText = status.toUpperCase();
        int color;
        
        switch (statusText) {
            case "APPROVED":
                color = Color.parseColor(STATUS_APPROVED);
                break;
            case "REJECTED":
                color = Color.parseColor(STATUS_REJECTED);
                break;
            case "COMPLETED":
                color = Color.parseColor(STATUS_COMPLETED);
                break;
            case "PENDING":
            default:
                color = Color.parseColor(STATUS_PENDING);
                break;
        }
        
        statusTextView.setText(statusText);
        statusTextView.setTextColor(color);
    }
}
