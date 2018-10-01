package ainullov.kamil.com.shoeshop.adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ainullov.kamil.com.shoeshop.MainActivity;
import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.fragments.ShowShoesFragment;
import ainullov.kamil.com.shoeshop.pojo.ShoeType;

//Вид обуви Adapter
public class ShoeTypeAdapter extends RecyclerView.Adapter<ShoeTypeAdapter.ViewHolder> {

    private LayoutInflater inflater;
    List<ShoeType> shoeTypes;

    Context context;

    public ShoeTypeAdapter(Context context, List<ShoeType> shoeTypes) {
        this.shoeTypes = shoeTypes;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ShoeTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.showtype_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoeTypeAdapter.ViewHolder holder, int position) {
        ShoeType shoeType = shoeTypes.get(position);
        holder.tvDepart.setText(shoeType.getName());
    }

    @Override
    public int getItemCount() {
        return shoeTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final LinearLayout llDepart;
        final TextView tvDepart;

        ViewHolder(View view) {
            super(view);
            llDepart = (LinearLayout) view.findViewById(R.id.llDepart);
            tvDepart = (TextView) view.findViewById(R.id.tvDepart);

            llDepart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int positionIndex = getAdapterPosition();
                    MainActivity.shoesTYPE = shoeTypes.get(positionIndex).getName();
                    Toast.makeText(context, "shoesTYPE" + MainActivity.shoesTYPE, Toast.LENGTH_SHORT).show();
                    ShowShoesFragment showShoesFragment = new ShowShoesFragment();
                    FragmentTransaction fTrans;
                    fTrans = ((Activity) context).getFragmentManager().beginTransaction();

                    fTrans.replace(R.id.container, showShoesFragment);
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                }
            });
        }
    }
}