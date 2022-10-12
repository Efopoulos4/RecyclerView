package com.example.recyclerviewfinal;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recyclerviewfinal.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private final LinkedList<Item> hItemList = new LinkedList<>();
    private LinkedList<Item> mItemList = new LinkedList<>();
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private RecyclerView mRecyclerView;
    private RecycleViewAdapter mAdapter;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 1; i <= 20; i++) {
            hItemList.addLast(new Item("Word " + i, false));
        }

        mItemList = (LinkedList<Item>) hItemList.clone();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new RecycleViewAdapter(this, mItemList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mItemList.size() - 1) {
                        isLoading = true;
                        getMoreData(10);
                    }
                }
            }
        });
    }

    private void getMoreData(int num) {
        mItemList.addLast(null);
        mAdapter.notifyDataSetChanged();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mItemList.remove(mItemList.size() - 1);
                int currentSize = mItemList.size();
                int nextSize = currentSize + num;
                while (currentSize < nextSize) {
                    mItemList.addLast(new Item("Word " + (currentSize + 1), false));
                    currentSize++;
                }
                mAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 1000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            mItemList = (LinkedList<Item>) hItemList.clone();
            mAdapter.resetAdapter(mItemList);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}