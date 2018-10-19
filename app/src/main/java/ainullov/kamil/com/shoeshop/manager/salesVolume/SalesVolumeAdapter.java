package ainullov.kamil.com.shoeshop.manager.salesVolume;

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

public class SalesVolumeAdapter extends RecyclerView.Adapter<SalesVolumeAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<OrderAccountingPojo> orderAccountingPojos;
    private Context context;

    public SalesVolumeAdapter(Context context, List<OrderAccountingPojo> orderAccountingPojos) {
        this.orderAccountingPojos = orderAccountingPojos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public SalesVolumeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.salesvolume_item, parent, false);
        return new SalesVolumeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SalesVolumeAdapter.ViewHolder holder, int position) {
        OrderAccountingPojo orderAccountingPojo = orderAccountingPojos.get(position);

        SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("dd.MM.yy, HH:mm", Locale.ENGLISH);
        String dayOfWeeki = formatDayOfWeek.format(Double.valueOf(orderAccountingPojo.getDate()));

        holder.tvGenderSVItem.setText("Пол: " + orderAccountingPojo.getGender());
        holder.tvTypeSVItem.setText("Вид: " + orderAccountingPojo.getType());
        holder.tvNameSVItem.setText("Название: " + orderAccountingPojo.getName());
        holder.tvCoastSVItem.setText("Цена: " + orderAccountingPojo.getCoast());
        holder.tvProviderSVItem.setText("Поставщик: " + orderAccountingPojo.getProvider());
        holder.tvSoldDateSVItem.setText("Дата продажи: " + dayOfWeeki);
        holder.tvSizeSVItem.setText("Размер: " + orderAccountingPojo.getSize());
    }

    @Override
    public int getItemCount() {
        return orderAccountingPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ConstraintLayout clSVitem;

        final TextView tvGenderSVItem, tvTypeSVItem, tvNameSVItem,
                tvCoastSVItem, tvProviderSVItem, tvSoldDateSVItem, tvSizeSVItem;

        ViewHolder(View view) {
            super(view);
            clSVitem = (ConstraintLayout) view.findViewById(R.id.clitem);
            tvGenderSVItem = (TextView) view.findViewById(R.id.tvGenderItem);
            tvTypeSVItem = (TextView) view.findViewById(R.id.tvTypeItem);
            tvNameSVItem = (TextView) view.findViewById(R.id.tvNameItem);
            tvCoastSVItem = (TextView) view.findViewById(R.id.tvCoastItem);
            tvProviderSVItem = (TextView) view.findViewById(R.id.tvProviderItem);
            tvSoldDateSVItem = (TextView) view.findViewById(R.id.tvDateSAItem);
            tvSizeSVItem = (TextView) view.findViewById(R.id.tvSizeSAItem);


        }
    }
}