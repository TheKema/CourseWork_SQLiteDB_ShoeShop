package ainullov.kamil.com.shoeshop.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.pojo.OneShoe;

public class ShowShoesAdapter extends RecyclerView.Adapter<ShowShoesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    List<OneShoe> shoes;

    public ShowShoesAdapter(Context context, List<OneShoe> shoes) {
        this.shoes = shoes;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ShowShoesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.show_item, parent, false);
        return new ShowShoesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShowShoesAdapter.ViewHolder holder, int position) {
        OneShoe shoe = shoes.get(position);
//        holder.imageView.setImageResource(phone.getImage());
        holder.tvShoeName.setText(shoe.getName());
    }

    @Override
    public int getItemCount() {
        return shoes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivShoe;
        final TextView tvShoeName, tvShoeCost;
        final Button btnBasket, btnFav;

        ViewHolder(View view) {
            super(view);
            ivShoe = (ImageView) view.findViewById(R.id.ivShoe);
            tvShoeName = (TextView) view.findViewById(R.id.tvShoeName);
            tvShoeCost = (TextView) view.findViewById(R.id.tvShoeCost);
            btnBasket = (Button) view.findViewById(R.id.btnBasket);
            btnFav = (Button) view.findViewById(R.id.btnFav);

            btnBasket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int positionIndex = getAdapterPosition();

                    // Действие
                }
            });

            btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int positionIndex = getAdapterPosition();

                    // Действие
                }
            });
        }
    }
}