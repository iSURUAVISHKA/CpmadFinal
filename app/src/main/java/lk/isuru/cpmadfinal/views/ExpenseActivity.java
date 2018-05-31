package lk.isuru.cpmadfinal.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lk.isuru.cpmadfinal.R;
import lk.isuru.cpmadfinal.adapters.ExpenseAdapter;
import lk.isuru.cpmadfinal.models.Expense;
import lk.isuru.cpmadfinal.utils.DBHelper;
import lk.isuru.cpmadfinal.utils.GlobalClass;

public class ExpenseActivity extends AppCompatActivity {

    GlobalClass globalClass;
    DBHelper dbHelper;
    Query query;
    List<Expense> expenseDataSet;
    ValueEventListener valueEventListener = null;
    RecyclerView expenseRecyclerView;
    SwipeRefreshLayout expenseSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        globalClass = (GlobalClass) this.getApplication();
        dbHelper = new DBHelper();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expenseRecyclerView  = findViewById(R.id.expenseRecyclerView);
        expenseSwipeRefreshLayout  = findViewById(R.id.expenseSwipeRefreshLayout);
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseRecyclerView.setItemAnimator(new DefaultItemAnimator());

        expenseSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                expenseSwipeRefreshLayout.setRefreshing(true);
                expenseRecyclerView.setAdapter(new ExpenseAdapter(ExpenseActivity.this, expenseDataSet));
                expenseSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public void addDatabaseListener() {
        if (valueEventListener != null) {
            query.removeEventListener(valueEventListener);
        }
        query = dbHelper.getReference(globalClass.REF_EXPENSES).orderByChild("published");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                expenseDataSet = new ArrayList<>();
                expenseSwipeRefreshLayout.setRefreshing(true);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Expense expense = snapshot.getValue(Expense.class);
                    expenseDataSet.add(expense);
                }

                expenseRecyclerView.setAdapter(new ExpenseAdapter(ExpenseActivity.this, expenseDataSet));
                expenseSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(globalClass.TAG, "Expenses failed to retrieve", databaseError.toException());
            }
        };
        query.addValueEventListener(valueEventListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addDatabaseListener();
    }
}
