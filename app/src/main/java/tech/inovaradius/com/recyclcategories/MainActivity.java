package tech.inovaradius.com.recyclcategories;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mainToolbar;
    RecyclerView recyclerView;
    AppCompatButton cat_btn;
    String[] planets;
    int finapostion;
    String MSG = "SavedValue";

    private static final String PREFS_SPINNER = "PREFS_SPINNER";
    CategoriesAdapter shopArrayAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);

        mainToolbar = findViewById(R.id.mainToolbar);
        recyclerView = findViewById(R.id.recycleview);
        cat_btn = findViewById(R.id.cat_btn);


        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Main Toolbar");

        planets = new String[]{"All", "T-Shirt", "Jeans", "Coumputer", "Phone"};

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSelectedCategoryData(planets[loadSpinnerPosition()]); // Retrieving Data from SharedPref
        Log.d(MSG, "The SavedPosition is : " + finapostion);

        // shopArrayAdapter = new CategoriesAdapter(getShop(), this);
        // recyclerView.setAdapter(shopArrayAdapter);
        cat_btn.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        CategoriesDialog(planets);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.spin);

        return true;
    }

    private ArrayList<Shop> getShop() {
        ArrayList<Shop> shops = new ArrayList<>();
        shops.clear();

        shops.add(new Shop("Mercury", 1, "T-Shirt"));
        shops.add(new Shop("UY Scuti", 1, "T-Shirt"));
        shops.add(new Shop("Andromeda", 3, "Jeans"));
        shops.add(new Shop("VV Cephei A", 2, "Coumputer"));
        shops.add(new Shop("IC 1011", 3, "Jeans"));
        shops.add(new Shop("Sun", 2, "Coumputer"));
        shops.add(new Shop("Aldebaran", 2, "T-Shirt"));
        shops.add(new Shop("Venus", 1, "T-Shirt"));
        shops.add(new Shop("Malin 1", 3, "Jeans"));
        shops.add(new Shop("Rigel", 2, "Jeans"));
        shops.add(new Shop("Earth", 1, "T-Shirt"));
        shops.add(new Shop("Whirlpool", 3, "Jeans"));
        shops.add(new Shop("VY Canis Majoris", 2, "Jeans"));
        shops.add(new Shop("Saturn", 1, "Coumputer"));
        shops.add(new Shop("Sombrero", 3, "Jeans"));
        shops.add(new Shop("Betelgeuse", 2, "Coumputer"));
        shops.add(new Shop("Uranus", 1, "Coumputer"));
        shops.add(new Shop("Virgo Stellar Stream", 3, "Jeans"));
        shops.add(new Shop("Epsillon Canis Majoris", 2, "Jeans"));
        shops.add(new Shop("Jupiter", 1, "Jeans"));
        shops.add(new Shop("VY Canis Majos", 2, "T-Shirt"));
        shops.add(new Shop("Triangulum", 3, "Phone"));
        shops.add(new Shop("Cartwheel", 3, "Phone"));
        shops.add(new Shop("Antares", 2, "T-Shirt"));
        shops.add(new Shop("Mayall's Object", 3, "Phone"));
        shops.add(new Shop("Proxima Centauri", 2, "T-Shirt"));

        return shops;
    }

    private void getSelectedCategoryData(String newText) {

        String userInput = newText.toLowerCase();
        List<Shop> newList = new ArrayList<>();

        if (userInput.contains("all")) {
            shopArrayAdapter = new CategoriesAdapter(getShop(), MainActivity.this);

        } else {

            for (Shop mPredication : getShop())

            {
                String ded = mPredication.getCatego().toLowerCase();
                if (ded.contains(userInput)) {
                    newList.add(mPredication);
                }
            }

            shopArrayAdapter = new CategoriesAdapter(newList, MainActivity.this);
        }

        recyclerView.setAdapter(shopArrayAdapter);
        shopArrayAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);

        //shopArrayAdapter.updateList(newList);
    }

    public void saveSpinnerPosition(int position) {

        sharedPreferences.edit()
                .putInt(PREFS_SPINNER, position)
                .apply();

        Log.d(MSG, "Postion Saved : " + position);
    }

    public int loadSpinnerPosition() {

        if (sharedPreferences.contains(PREFS_SPINNER)) {

            finapostion = sharedPreferences.getInt(PREFS_SPINNER, 0);
            // mySpinner.setSelection(position);
            Log.d(MSG, "The SavedPosition is : " + finapostion);
        }

        return finapostion;
    }

    void CategoriesDialog(final String[] arrayList) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this, R.style.MaterialThemeDialog);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.titlebar, null);
        mBuilder.setCustomTitle(view);
        mBuilder.setCancelable(false);

        // mBuilder.setTitle("Choose Your Category");
        mBuilder.setSingleChoiceItems(arrayList, loadSpinnerPosition(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getSelectedCategoryData(arrayList[which]);
                saveSpinnerPosition(which);
                dialog.dismiss();
            }

        });

        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();


    }
}

