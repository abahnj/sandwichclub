package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich;
        try {
            JSONObject sandwichObject = new JSONObject(json);
            JSONObject nameObject = sandwichObject.getJSONObject(NAME);
            String name = nameObject.getString(MAIN_NAME);
            JSONArray alsoKnownAsObject = nameObject.getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownAs = new ArrayList<>();
            String placeOfOrigin = sandwichObject.getString(PLACE_OF_ORIGIN);
            String description = sandwichObject.getString(DESCRIPTION);
            String image = sandwichObject.getString(IMAGE);
            JSONArray ingredientsObject = sandwichObject.getJSONArray(INGREDIENTS);
            List<String> ingredients = new ArrayList<>();


            if (ingredientsObject != null) {
                for (int i = 0; i < ingredientsObject.length(); i++) {
                    ingredients.add(ingredientsObject.getString(i)
                    );
                }
            }

            if (alsoKnownAsObject != null) {
                for (int i = 0; i < alsoKnownAsObject.length(); i++) {
                    alsoKnownAs.add(alsoKnownAsObject.getString(i)
                    );
                }
            }
            sandwich = new Sandwich(name, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
            sandwich = new Sandwich();
        }
        return sandwich;
    }
}
