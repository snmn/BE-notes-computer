package project.m.medicareminorproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import project.m.medicareminorproject.R;
import project.m.medicareminorproject.helper.AmbulanceHelper;


public class AmbulanceListAdapter extends RecyclerView.Adapter<AmbulanceListAdapter.ViewHolder> {

    private List<AmbulanceHelper> totalList;
    private Context context;

    public AmbulanceListAdapter(List<AmbulanceHelper> totalList, Context context) {
        this.totalList = totalList;
        this.context = context;       //yo uta json bata tanera banayo arraylist pataune constructor(AmbulanceActivity bata yo call garna parxa)
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // recyclerview bhaneko list jasto ho so list ko row hunxa tyo row kasto banaune bhanera yaha lekhne
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ambulance_recyclerview_row, null); //yo ho tyo row

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final int pos = position;
        try {
            viewHolder.name.setText(totalList.get(position).getName()); //yo chai data set gareko textview ma
            viewHolder.address.setText(totalList.get(position).getAddress());
            viewHolder.number.setText(totalList.get(position).getPhone_number());
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callingIntent = new Intent(Intent.ACTION_CALL);
                    callingIntent.setData(Uri.parse("tel:0" + totalList.get(pos).getPhone_number()));
                    context.startActivity(callingIntent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return totalList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, address, number;
        public CardView cardView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            name = (TextView) itemLayoutView.findViewById(R.id.name);  //yo chai tyo row ma bhako chijbij ko id tanne thau
            address = (TextView) itemLayoutView.findViewById(R.id.address);
            number = (TextView) itemLayoutView.findViewById(R.id.number);
            cardView = (CardView) itemLayoutView.findViewById(R.id.card_view);
            // aaba row banauxu hai hera
        }

    }


}
