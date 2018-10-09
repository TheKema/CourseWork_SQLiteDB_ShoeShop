package ainullov.kamil.com.shoeshop.manager.dayResults;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.manager.pojo.OrderAccountingPojo;

public class DayResultsAdapter extends RecyclerView.Adapter<DayResultsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<OrderAccountingPojo> orderAccountingPojos;
    private Context context;

    public DayResultsAdapter(Context context, List<OrderAccountingPojo> orderAccountingPojos) {
        this.orderAccountingPojos = orderAccountingPojos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public DayResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.dayresults_item, parent, false);
        return new DayResultsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayResultsAdapter.ViewHolder holder, int position) {
        OrderAccountingPojo orderAccountingPojo = orderAccountingPojos.get(position);

        SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("dd.MM.yy, HH:mm", Locale.ENGLISH);
        String dayOfWeeki = formatDayOfWeek.format(Double.valueOf(orderAccountingPojo.getDate()));

        holder.tvGenderDRItem.setText("Пол: " + orderAccountingPojo.getGender());
        holder.tvTypeDRItem.setText("Вид: " + orderAccountingPojo.getType());
        holder.tvNameDRItem.setText("Название: " + orderAccountingPojo.getName());
        holder.tvCoastDRItem.setText("Цена: " + orderAccountingPojo.getCoast());
        holder.tvProviderDRItem.setText("Поставщик: " + orderAccountingPojo.getProvider());
        holder.tvSoldDateDRItem.setText("Дата продажи: " + dayOfWeeki);
        holder.tvSizeDRItem.setText("Размер: " + orderAccountingPojo.getSize());


    }

    @Override
    public int getItemCount() {
        return orderAccountingPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ConstraintLayout clDRitem;

        final TextView tvGenderDRItem, tvTypeDRItem, tvNameDRItem,
                tvCoastDRItem, tvProviderDRItem, tvSoldDateDRItem, tvSizeDRItem;

        ViewHolder(View view) {
            super(view);
            clDRitem = (ConstraintLayout) view.findViewById(R.id.clSAitem);
            tvGenderDRItem = (TextView) view.findViewById(R.id.tvGenderSAItem);
            tvTypeDRItem = (TextView) view.findViewById(R.id.tvTypeSAItem);
            tvNameDRItem = (TextView) view.findViewById(R.id.tvNameSAItem);
            tvCoastDRItem = (TextView) view.findViewById(R.id.tvCoastSAItem);
            tvProviderDRItem = (TextView) view.findViewById(R.id.tvProviderSAItem);
            tvSoldDateDRItem = (TextView) view.findViewById(R.id.tvDateSAItem);
            tvSizeDRItem = (TextView) view.findViewById(R.id.tvSizeSAItem);


        }
    }
}