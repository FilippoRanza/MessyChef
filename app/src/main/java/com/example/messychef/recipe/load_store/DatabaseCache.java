package com.example.messychef.recipe.load_store;

import androidx.room.RoomDatabase;

import com.example.messychef.recipe.dao.RecipeDatabase;

class DatabaseCache {

    private RecipeDatabase current;
    private RecipeDatabase cache;

    private final RoomDatabase.Builder<RecipeDatabase> builder;

    DatabaseCache(RoomDatabase.Builder<RecipeDatabase> builder) {
        current = null;
        cache = null;

        this.builder = builder;
    }


    void startCache() {
        if (cache == null)
            cache = make();
    }

    void stopCache() {
        if (cache != null) {
            cache.close();
            cache = null;
        }
    }


    RecipeDatabase open() {
        RecipeDatabase output = null;
        if (cache == null) {
            current = make();
            output = current;
        }
        else {
            output = cache;
        }
        return output;
    }

    void close() {
        if(current != null) {
            current.close();
            current = null;
        }
    }

    private RecipeDatabase make() {
        return builder.build();
    }

}
