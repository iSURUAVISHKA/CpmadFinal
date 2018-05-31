package lk.nirmalcode.cpmadfinal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import lk.nirmalcode.cpmadfinal.R;
import lk.nirmalcode.cpmadfinal.models.Expense;
import lk.nirmalcode.cpmadfinal.models.Staff;
import lk.nirmalcode.cpmadfinal.utils.DBHelper;
import lk.nirmalcode.cpmadfinal.utils.DateTimeUtils;
import lk.nirmalcode.cpmadfinal.utils.GlobalClass;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseFeedViewHolder> {

    private List<Expense> mDataSet;
    private static GlobalClass globalClass;
    private DBHelper dbHelper;
    private Context context;

    public ExpenseAdapter(Context pContext, List<Expense> pDataSet) {
        this.mDataSet = pDataSet;
        this.context = pContext;
        globalClass = (GlobalClass) pContext.getApplicationContext();
        dbHelper = new DBHelper();
        Log.d(globalClass.TAG, "EXPENSE ADAPTER INITIATED");
    }


    public class ExpenseFeedViewHolder extends RecyclerView.ViewHolder {

        TextView txtExpenseDescription;
        TextView txtExpensePublished;
        TextView txtExpenseUser;
        TextView txtExpenseValue;
        TextView txtExpenseType;
        View expenseView;

        public ExpenseFeedViewHolder(final View itemView) {
            super(itemView);
            expenseView = itemView;

            txtExpenseDescription = itemView.findViewById(R.id.txtExpenseDescription);
            txtExpensePublished = itemView.findViewById(R.id.txtExpensePublished);
            txtExpenseUser = itemView.findViewById(R.id.txtExpenseUser);
            txtExpenseValue = itemView.findViewById(R.id.txtExpenseValue);
            txtExpenseType = itemView.findViewById(R.id.txtExpenseType);
        }
    }

    @NonNull
    @Override
    public ExpenseFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseFeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExpenseFeedViewHolder holder, int position) {
        final Expense expense = mDataSet.get(position);
        final Context context = holder.expenseView.getContext();

        if (expense != null) {
            holder.txtExpenseDescription.setText(expense.description);
            holder.txtExpenseType.setText(expense.type);
            holder.txtExpenseValue.setText(expense.value);

            String time = DateTimeUtils.getRelativeDateTime(context, expense.published);
            holder.txtExpensePublished.setText(time);

            Query query = dbHelper.getReference().child(globalClass.REF_STAFF).orderByKey().equalTo(expense.userKey);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Staff staff = snapshot.getValue(Staff.class);
                        Log.d(globalClass.TAG, "EXPENSE Adapter : " + staff.name);
                        holder.txtExpenseUser.setText(staff.name);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(context,
                            "Data base error",
                            Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
