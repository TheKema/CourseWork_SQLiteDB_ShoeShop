package ainullov.kamil.com.shoeshop.manager.employeePerf;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.manager.pojo.WorkersPojo;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;

public class EmployeePerfAdapter extends RecyclerView.Adapter<EmployeePerfAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<WorkersPojo> workersPojos;
    private Context context;

    public EmployeePerfAdapter(Context context, List<WorkersPojo> workersPojos) {
        this.workersPojos = workersPojos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public EmployeePerfAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.employeeperf_item, parent, false);
        return new EmployeePerfAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeePerfAdapter.ViewHolder holder, int position) {
        WorkersPojo workersPojo = workersPojos.get(position);


        holder.tvEmpName.setText(workersPojo.getName());
        holder.tvEmpRating.setText(String.valueOf(workersPojo.getRating()));
    }

    @Override
    public int getItemCount() {
        return workersPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ConstraintLayout clEmp;

        final ImageButton imageBtnDel;

        final TextView tvEmpName, tvEmpRating;

        ViewHolder(View view) {
            super(view);
            clEmp = (ConstraintLayout) view.findViewById(R.id.clEmp);
            tvEmpName = (TextView) view.findViewById(R.id.tvEmpName);
            tvEmpRating = (TextView) view.findViewById(R.id.tvEmpRating);
            imageBtnDel = (ImageButton) view.findViewById(R.id.imageBtnDel);

            //Переход к товару переход к ShoesDetailedFragment
            clEmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog);
                    dialog.setTitle("Изменить имя сотрудника и рейтинг");

                    final EditText etNameDialog = (EditText) dialog.findViewById(R.id.etNameDialog);
                    etNameDialog.setText(workersPojos.get(getAdapterPosition()).getName());
                    final EditText etRatingDialog = (EditText) dialog.findViewById(R.id.etRatingDialog);
                    etRatingDialog.setText(String.valueOf(workersPojos.get(getAdapterPosition()).getRating()));

                    Button btnOkDialog = (Button) dialog.findViewById(R.id.btnOkDialog);


                    btnOkDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String strNameDialog = etNameDialog.getText().toString();
                            String strRatingDialog = etRatingDialog.getText().toString();
                            int uniquekey = workersPojos.get(getAdapterPosition()).getUniquekey();


                            ContentValues cv = new ContentValues();
                            DataBaseHelper dbHelper = new DataBaseHelper(context);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();

                            workersPojos.get(getAdapterPosition()).setName(strNameDialog);
                            workersPojos.get(getAdapterPosition()).setRating(Integer.valueOf(strRatingDialog));

                            cv.put("name", strNameDialog);
                            cv.put("rating", strRatingDialog);
                            db.update("workers", cv, "uniquekey = ?", new String[]{String.valueOf(uniquekey)});
                            dbHelper.close();
                            notifyDataSetChanged();
                            dialog.dismiss();

                        }
                    });

                    dialog.show();

                }
            });

            imageBtnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataBaseHelper dbHelper;
                    dbHelper = new DataBaseHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    int uniquekey = workersPojos.get(getAdapterPosition()).getUniquekey();
                    db.delete("workers", "uniquekey = ?", new String[]{String.valueOf(uniquekey)});
                    dbHelper.close();

                    workersPojos.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });

        }
    }
}
