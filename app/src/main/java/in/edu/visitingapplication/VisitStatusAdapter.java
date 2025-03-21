package in.edu.visitingapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class VisitStatusAdapter extends BaseAdapter {

    private Context context;
    private List<VisitRequest> visitRequestList;

    public VisitStatusAdapter(Context context, List<VisitRequest> visitRequestList) {
        this.context = context;
        this.visitRequestList = visitRequestList;
    }

    @Override
    public int getCount() {
        return visitRequestList.size();
    }

    @Override
    public Object getItem(int position) {
        return visitRequestList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return visitRequestList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_visit_status, parent, false);
            holder = new ViewHolder();
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvPurpose = convertView.findViewById(R.id.tvPurpose);
            holder.tvStatus = convertView.findViewById(R.id.tvStatus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        VisitRequest request = visitRequestList.get(position);
        holder.tvName.setText(request.getName());
        holder.tvPurpose.setText(request.getPurpose());
        String status = request.getStatus();
        holder.tvStatus.setText(status);

        // Set the status text color based on its value
        if (status.equalsIgnoreCase("pending")) {
            // Yellow (using holo_orange_light as a yellow-ish color)
            holder.tvStatus.setTextColor(context.getResources().getColor(android.R.color.holo_orange_light));
        } else if (status.equalsIgnoreCase("approved")) {
            // Green
            holder.tvStatus.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        } else if (status.equalsIgnoreCase("rejected")) {
            // Red
            holder.tvStatus.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        } else {
            // Default black
            holder.tvStatus.setTextColor(context.getResources().getColor(android.R.color.black));
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvName, tvPurpose, tvStatus;
    }
}
