package com.example.filmder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    public int[] watched_movies = new int[] {0, 0, 0, 0, 0};
    public TextView outcome_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        outcome_tv = findViewById(R.id.outcome_text);

        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);

            }

            @Override
            public void onCardSwiped(Direction direction) {
                int swipecount = 0;
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction.name());
                if (direction == direction.Right){
                    Toast.makeText(MainActivity.this, "Direction Right", Toast.LENGTH_SHORT).show();
                    watched_movies[manager.getTopPosition()-1] = 1;
                    if (manager.getTopPosition()==5){
                        showOutcome(watched_movies);
                        cardStackView.setVisibility(View.INVISIBLE);
                    }
                }
                if (direction == direction.Left){
                    Toast.makeText(MainActivity.this, "Direction Left", Toast.LENGTH_SHORT).show();
                    watched_movies[manager.getTopPosition()-1] = -1;
                    if (manager.getTopPosition()==5){
                        showOutcome(watched_movies);
                        cardStackView.setVisibility(View.INVISIBLE);
                    }
                }
                if (direction == direction.Top){
                    Toast.makeText(MainActivity.this, "Direction Top", Toast.LENGTH_SHORT).show();
                    if (manager.getTopPosition()==5){
                        showOutcome(watched_movies);
                        cardStackView.setVisibility(View.INVISIBLE);
                    }
                }
                if (direction == direction.Bottom){
                    Toast.makeText(MainActivity.this, "Direction Right", Toast.LENGTH_SHORT).show();
                    if (manager.getTopPosition()==5){
                        showOutcome(watched_movies);
                        cardStackView.setVisibility(View.INVISIBLE);
                    }
                }
                /*if (swipecount >= 4 ){
                    swipecount = 0;
                    showOutcome(watched_movies);
                    cardStackView.setVisibility(View.INVISIBLE);
                }*/

                // Paginating
                if (manager.getTopPosition()==adapter.getItemCount()-5) {
                    Toast.makeText(MainActivity.this, "No more cards", Toast.LENGTH_LONG).show();
                    //paginate();
                    showOutcome(watched_movies);
                    cardStackView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardCanceled: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv  = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", name:" + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv  = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardDisappeared: " + position + ", name:" + tv.getText());
            }
        });

        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    private void showOutcome(int [] watched_movies) {
        ArrayList<String> recommendable_movies = new ArrayList<String>();
        recommendable_movies.add("Avengers: Infinity War (2018)");
        recommendable_movies.add("The Shining (1980)");
        recommendable_movies.add("Kill Bill: Vol. 1 (2003)");
        recommendable_movies.add("50 First Dates (2004)");
        recommendable_movies.add("Soul (2020)");
        for (int j = 0; j<5; j++){
            if (watched_movies[j] == 1) {
                outcome_tv.append("\n" + recommendable_movies.get(j) + "--> SHOULD DEFINITELY WATCH!\n");
            }
            if (watched_movies[j] == 0) {
                outcome_tv.append("\n" + recommendable_movies.get(j) + "--> YOU MIGHT ENJOY.\n");
            }
        }
        outcome_tv.setVisibility(View.VISIBLE);

    }

    private void paginate() {
        List<ItemModel> old_list = adapter.getItems();
        List<ItemModel> new_list = new ArrayList<>(addList());
        CardStackCallback callback = new CardStackCallback(old_list, new_list);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setItems(new_list);
        result.dispatchUpdatesTo(adapter);
    }

    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();

        items.add(new ItemModel(R.drawable.avengers, "Avengers: EndGame", "2019", "Description"));
        items.add(new ItemModel(R.drawable.space_odysseyjpg, "2001: A Space Odyssey", "1973", "Description"));
        items.add(new ItemModel(R.drawable.pulp_fiction, "Pulp Fiction", "1994", "Description"));
        items.add(new ItemModel(R.drawable.lala, "La La Land", "2016", "Description"));
        items.add(new ItemModel(R.drawable.ratatouille, "Ratatouille", "1994", "Description"));

        return items;
    }
}