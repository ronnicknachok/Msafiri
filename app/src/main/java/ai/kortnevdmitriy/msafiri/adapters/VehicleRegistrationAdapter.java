package ai.kortnevdmitriy.msafiri.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.entities.VehicleDetails;

/**
 * Created by kortn on 10/4/2017.
 */

public class VehicleRegistrationAdapter extends RecyclerView.Adapter<VehicleRegistrationAdapter.MyViewHolder> {

    private List<VehicleDetails> vehicleDetails;

    public VehicleRegistrationAdapter(List<VehicleDetails> vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewall_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VehicleDetails detailsOfVehicles = vehicleDetails.get(position);
        holder.title.setText(detailsOfVehicles.getCompanyName());
        holder.genre.setText(detailsOfVehicles.getVehicleType());
        holder.year.setText(detailsOfVehicles.getTravelRoute());
        holder.price.setText(detailsOfVehicles.getPriceOfTravel());
        holder.regdet.setText(detailsOfVehicles.getRegistrationDetails());
    }

    @Override
    public int getItemCount() {
        return vehicleDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre, price, regdet;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            genre = view.findViewById(R.id.genre);
            year = view.findViewById(R.id.year);
            price = view.findViewById(R.id.price);
            regdet = view.findViewById(R.id.regdet);
        }
    }
}