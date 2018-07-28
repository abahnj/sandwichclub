package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView alsoKnownAsTv;
    private TextView placeOfOriginTv;
    private TextView ingredientsTv;
    private TextView descriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        descriptionTv = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        RequestOptions requestOptions = RequestOptions.placeholderOf(R.drawable.placeholder_thumbnail)
                .error(R.drawable.ic_error_outline_black_24dp);

        Glide.with(this)
                .load(sandwich.getImage())
                .apply(requestOptions)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        String alsoKnownAs = sandwich.getAlsoKnownAs() != null &&
                sandwich.getAlsoKnownAs().size() == 0
                ? getString(sandwich.getAlsoKnownAs()) : "Data not available";
        String placeOfOrigin = sandwich.getPlaceOfOrigin() != null
                ? getString(sandwich.getAlsoKnownAs()) : "Data not available";

        alsoKnownAsTv.setText(alsoKnownAs);
        placeOfOriginTv.setText(placeOfOrigin);
        ingredientsTv.setText(getString(sandwich.getIngredients()));
        descriptionTv.setText(sandwich.getDescription());
    }



    private String getString(List<String> list) {
        return TextUtils.join(", ", list);
    }


}
