package project.m.medicareminorproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import project.m.medicareminorproject.R;
import project.m.medicareminorproject.activity.EditMedicineActivity;
import project.m.medicareminorproject.helper.MedicineHelper;


public class SavedMedicineListAdapter extends RecyclerView.Adapter<SavedMedicineListAdapter.ViewHolder> {

    private List<MedicineHelper> totalList;
    private Context context;

    public SavedMedicineListAdapter(List<MedicineHelper> totalList, Context context) {
        this.totalList = totalList;
        this.context = context;       //yo uta json bata tanera banayo arraylist pataune constructor(AmbulanceActivity bata yo call garna parxa)
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // recyclerview bhaneko list jasto ho so list ko row hunxa tyo row kasto banaune bhanera yaha lekhne
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_medicine_list_row, null); //yo ho tyo row

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final int pos = position;
        try {
            viewHolder.name.setText(" Medicine Name : " + totalList.get(position).getMedicineName()); //yo chai data set gareko textview ma
            viewHolder.recommend.setText(" Recommended By : " + totalList.get(position).getRecommendedBy());
            viewHolder.time.setText(" Time : " + totalList.get(position).getAlarmTime());
            viewHolder.edit_medicine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditMedicineActivity.class);
                    intent.putExtra("medicinedetail", totalList.get(position));
                    context.startActivity(intent);
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

        public TextView name, recommend, time;
        public CardView cardView;
        ImageView edit_medicine;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            name = (TextView) itemLayoutView.findViewById(R.id.medicine_name);  //yo chai tyo row ma bhako chijbij ko id tanne thau
            recommend = (TextView) itemLayoutView.findViewById(R.id.medicine_recommend);
            time = (TextView) itemLayoutView.findViewById(R.id.medicine_time);
            cardView = (CardView) itemLayoutView.findViewById(R.id.card_view);
            edit_medicine = (ImageView) itemLayoutView.findViewById(R.id.edit_medicine);
            // aaba row banauxu hai hera
        }

    }


}
