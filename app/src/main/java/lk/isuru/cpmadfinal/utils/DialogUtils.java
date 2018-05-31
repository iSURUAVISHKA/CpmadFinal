package lk.isuru.cpmadfinal.utils;

import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lk.isuru.cpmadfinal.R;
import lk.isuru.cpmadfinal.models.Expense;

public class DialogUtils {

    public final static int MODE_ADD = 0;
    public final static int MODE_UPDATE = 1;
    public final static int MODE_DELETE = 2;

    static DBHelper dbHelper;
    static GlobalClass globalClass;
    static LayoutInflater inflater;
    static View content;
    static AlertDialog.Builder dialog;


    public static void showExpenseManageDialog(int mode, final AppCompatActivity parentActivity, final Expense expense) {
        dbHelper = new DBHelper();
        globalClass = (GlobalClass) parentActivity.getApplicationContext();
        inflater = parentActivity.getLayoutInflater();
        content = inflater.inflate(R.layout.dialog_expense, null);
        dialog = new AlertDialog.Builder(new ContextWrapper(parentActivity), R.style.AppTheme_Dialog);
        dialog.setView(content);

        final AutoCompleteTextView txtType = content.findViewById(R.id.txtDialogExpenseType);
        final EditText txtDescription = content.findViewById(R.id.txtDialogExpenseDescription);
        final EditText txtValue = content.findViewById(R.id.txtDialogExpenseValue);

        Query query = dbHelper.getReference(globalClass.REF_EXPENSES).orderByChild("type");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> expenseTypeSet = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot reportSnapshot : snapshot.child("type").getChildren()) {
                        String type = reportSnapshot.getValue(String.class);
                        expenseTypeSet.add(type);
                    }
                    ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(parentActivity, android.R.layout.simple_list_item_1, expenseTypeSet);
                    txtType.setAdapter(typeAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(globalClass.TAG, "Questions failed to retrieve", databaseError.toException());
            }
        });

        switch (mode) {
            case MODE_ADD:
                dialog.setTitle("Add New Expense");
                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            String type = txtType.getText().toString();
                            String description = txtDescription.getText().toString();
                            String value = txtValue.getText().toString();
                            Long nowTime = System.currentTimeMillis();

                            Expense newexpense = new Expense(type, value, description, nowTime, globalClass.getLoggedUser().key);

                            dbHelper.pushObject(globalClass.REF_EXPENSES, newexpense);
                            dialog.dismiss();
                    }
                });
                break;
            case MODE_UPDATE:
                dialog.setTitle("Update Expense");
                txtType.setText(expense.type);
                txtDescription.setText(expense.description);
                txtValue.setText(expense.value);

                dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String type = txtType.getText().toString();
                        String description = txtDescription.getText().toString();
                        String value = txtValue.getText().toString();
                        Long nowTime = System.currentTimeMillis();

                        Expense newexpense = new Expense(type, value, description, nowTime, globalClass.getLoggedUser().key);

                        dbHelper.deleteObject(globalClass.REF_EXPENSES + "/" + expense.key);
                        dbHelper.updateObject(globalClass.REF_EXPENSES + "/" + expense.key, newexpense);

                        dialog.dismiss();
                        parentActivity.recreate();
                    }
                });
                break;
            case MODE_DELETE:
                dialog.setTitle("Delete Question");
                txtType.setText(expense.type);
                txtDescription.setText(expense.description);
                txtValue.setText(expense.value);

                txtType.setEnabled(false);
                txtDescription.setEnabled(false);
                txtValue.setEnabled(false);

                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteObject(globalClass.REF_EXPENSES + "/" + expense.key);
                        dialog.dismiss();
                        parentActivity.finish();
                    }
                });
                break;
        }


        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
