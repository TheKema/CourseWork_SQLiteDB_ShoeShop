package ainullov.kamil.com.shoeshop.user.adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.user.fragments.ShoesDetailedFragment;
import ainullov.kamil.com.shoeshop.user.pojo.OneShoe;

//Конкретная информация о товаре Adapter
public class ShowShoesAdapter extends RecyclerView.Adapter<ShowShoesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    List<OneShoe> shoes;
    Context context;

    public ShowShoesAdapter(Context context, List<OneShoe> shoes) {
        this.shoes = shoes;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ShowShoesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.show_item, parent, false);
        return new ShowShoesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShowShoesAdapter.ViewHolder holder, int position) {
        OneShoe shoe = shoes.get(position);
        holder.tvShoeName.setText(shoe.getName());
        holder.tvShoeCost.setText(String.valueOf(shoe.getCoast()));
    }

    @Override
    public int getItemCount() {
        return shoes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivShoe;
        final TextView tvShoeName, tvShoeCost;
        final LinearLayout llShow;

        ViewHolder(View view) {
            super(view);
            ivShoe = (ImageView) view.findViewById(R.id.ivShoeDetailed);
            tvShoeName = (TextView) view.findViewById(R.id.tvShoeName);
            tvShoeCost = (TextView) view.findViewById(R.id.tvShoeCost);
            llShow = (LinearLayout) view.findViewById(R.id.llShow);

            llShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int positionIndex = getAdapterPosition();
                    OneShoe shoe = shoes.get(positionIndex);
                    ShoesDetailedFragment shoesDetailedFragment = new ShoesDetailedFragment();

                    Bundle bundle = new Bundle();
                    bundle.putInt("id", shoe.getId());
//                    bundle.putString("type", shoe.getType());
//                    bundle.putString("gender", shoe.getGender());
//                    bundle.putInt("quantity", shoe.getQuantity());
//                    bundle.putString("name", shoe.getName());
//                    bundle.putInt("coast", shoe.getCoast());
//                    bundle.putString("desciption", shoe.getDesc());
//                    bundle.putInt("size", shoe.getSize());
                    shoesDetailedFragment.setArguments(bundle);

                    FragmentTransaction fTrans;
                    fTrans = ((Activity) context).getFragmentManager().beginTransaction();

                    fTrans.replace(R.id.container, shoesDetailedFragment);
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                }
            });
        }
    }
}