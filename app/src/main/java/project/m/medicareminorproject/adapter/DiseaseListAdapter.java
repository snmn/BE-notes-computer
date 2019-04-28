package project.m.medicareminorproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import project.m.medicareminorproject.R;
import project.m.medicareminorproject.helper.DiseaseHelper;

public class DiseaseListAdapter extends
        RecyclerView.Adapter<DiseaseListAdapter.ViewHolder> {

    private List<DiseaseHelper> totalList;
    private Context context;

    public DiseaseListAdapter(List<DiseaseHelper> totalList, Context context) {
        this.totalList = totalList;
        this.context=context;       //yo uta json bata tanera banayo arraylist pataune constructor(DiseaseActivity bata yo call garna parxa)
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // recyclerview bhaneko list jasto ho so list ko row hunxa tyo row kasto banaune bhanera yaha lekhne
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.disease_recyclerview_row, null); //yo ho tyo row

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final int pos = position;
        try {
            viewHolder.disease_name.setText(totalList.get(position).getDisease_name()); //yo chai data set gareko textview ma
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return totalList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView disease_name;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            disease_name = (TextView) itemLayoutView.findViewById(R.id.disease_name);  //yo chai tyo row ma bhako chijbij ko id tanne thau

            // aaba row banauxu hai hera
        }

    }



}
