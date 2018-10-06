package ainullov.kamil.com.shoeshop.manager.ordersAccounting;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.user.fragments.ShoesDetailedFragment;

public class OrdersAccountingAdapter extends RecyclerView.Adapter<OrdersAccountingAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<OrderAccountingPojo> orderAccountingPojos;
    private Context context;

    public OrdersAccountingAdapter(Context context, List<OrderAccountingPojo> orderAccountingPojos) {
        this.orderAccountingPojos = orderAccountingPojos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public OrdersAccountingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.ordersaccounting_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrdersAccountingAdapter.ViewHolder holder, int position) {
        OrderAccountingPojo orderAccountingPojo = orderAccountingPojos.get(position);

        SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("dd.MM.yy, HH:mm", Locale.ENGLISH);
        String dayOfWeeki = formatDayOfWeek.format(Double.valueOf(orderAccountingPojo.getDate()));

        StringBuilder strSizes = new StringBuilder("");

        try {
            JSONObject json = new JSONObject(orderAccountingPojo.getSize());
            JSONArray items = json.optJSONArray("uniqueArrays");
            for (int i = 0; i < items.length(); i++) {
                if (i == 0) {
                    strSizes.append(items.optString(i));
                } else
                    strSizes.append(", " + items.optString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        holder.tvGenderOAItem.setText("Пол: " + orderAccountingPojo.getGender());
        holder.tvTypeOAItem.setText("Вид: " + orderAccountingPojo.getType());
        holder.tvNameOAItem.setText("Название: " + orderAccountingPojo.getName());
        holder.tvCoastOAItem.setText("Цена: " + orderAccountingPojo.getCoast());
        holder.tvProviderOAItem.setText("Поставщик: " + orderAccountingPojo.getProvider());
        holder.tvDateOAItem.setText("Дата поставки: " + dayOfWeeki);
        holder.tvSizeOAItem.setText("Кол-во: " + orderAccountingPojo.getQuantity() + " Размер(ы): " + strSizes);
    }

    @Override
    public int getItemCount() {
        return orderAccountingPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ConstraintLayout clOAitem;

        final TextView tvGenderOAItem, tvTypeOAItem, tvNameOAItem,
                tvCoastOAItem, tvProviderOAItem, tvDateOAItem, tvSizeOAItem;

        ViewHolder(View view) {
            super(view);
            clOAitem = (ConstraintLayout) view.findViewById(R.id.clOAitem);
            tvGenderOAItem = (TextView) view.findViewById(R.id.tvGenderOAItem);
            tvTypeOAItem = (TextView) view.findViewById(R.id.tvTypeOAItem);
            tvNameOAItem = (TextView) view.findViewById(R.id.tvNameOAItem);
            tvCoastOAItem = (TextView) view.findViewById(R.id.tvCoastOAItem);
            tvProviderOAItem = (TextView) view.findViewById(R.id.tvProviderOAItem);
            tvDateOAItem = (TextView) view.findViewById(R.id.tvDateOAItem);
            tvSizeOAItem = (TextView) view.findViewById(R.id.tvSizeOAItem);

            //Переход к товару переход к ShoesDetailedFragment
            clOAitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShoesDetailedFragment shoesDetailedFragment = new ShoesDetailedFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("uniquekey", orderAccountingPojos.get(getAdapterPosition()).getUniquekey());
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

