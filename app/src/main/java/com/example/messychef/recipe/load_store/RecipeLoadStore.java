package com.example.messychef.recipe.load_store;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.dao.IngredientDao;
import com.example.messychef.recipe.dao.RecipeDao;
import com.example.messychef.recipe.dao.RecipeDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RecipeLoadStore {

    private final DatabaseCache builder;
    private static RecipeLoadStore instance;
    private final ExecutorService executor;


    private Future<Recipe> loadRecipeFuture;
    private Future<?> storeRecipeFuture;
    private Future<List<RecipeDao.RecipeInfo>> recipeInfoFuture;
    private Future<?> deleteRecipeFuture;
    private Future<?> updateRecipeFuture;
    private Future<List<String>> searchIngredientFuture;
    private Future<List<RecipeDao.RecipeInfo>> searchRecipeFuture;





    private RecipeLoadStore(Context owner) {
        this(Room.databaseBuilder(owner, RecipeDatabase.class, "recipe-database"));
    }

    private RecipeLoadStore(RoomDatabase.Builder<RecipeDatabase> builder) {
        this.builder = new DatabaseCache(builder);
        builder.fallbackToDestructiveMigration();
        executor = Executors.newSingleThreadExecutor();
    }

    public static void initInstance(Context owner) {
        System.out.println("Initialize DB Connection");
        instance = new RecipeLoadStore(owner);
        System.out.println("DB Connection Ready");
    }

    public static void initInMemoryInstance(RoomDatabase.Builder<RecipeDatabase> database) {
        instance = new RecipeLoadStore(database);
    }

    public static RecipeLoadStore getInstance() {
        if (instance == null)
            throw new IllegalStateException();
        return instance;
    }

    public void startLoadRecipeById(int id) {
        autoCommit();
        loadRecipeFuture = executor.submit(() -> loadRecipeById(id));
    }

    public Recipe commitLoadRecipeById() {
        Recipe recipe = null;
        try {
            recipe = loadRecipeFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return recipe;
    }


    public Recipe loadRecipeById(int id) {
        RecipeDatabase database = builder.open();
        RecipeLoad recipeLoad = new RecipeLoad(database);
        Recipe recipe = recipeLoad.loadRecipe(id);
        builder.close();
        return recipe;
    }

    public void startSaveRecipe(Recipe r) {
        storeRecipeFuture = executor.submit(() -> saveRecipe(r));
    }

    public void commitSaveRecipe() {
        try {
            storeRecipeFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void saveRecipe(Recipe r) {
        RecipeDatabase database = builder.open();
        RecipeStore recipeStore = new RecipeStore(database);
        recipeStore.storeRecipe(r);
        builder.close();
    }

    public void startGetRecipeList() {
        autoCommit();
        recipeInfoFuture = executor.submit(this::getRecipeList);
    }

    public List<RecipeDao.RecipeInfo> commitGetRecipeList() {
        List<RecipeDao.RecipeInfo> output = null;
        try {
            output = recipeInfoFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return output;
    }


    public List<RecipeDao.RecipeInfo> getRecipeList() {
        RecipeDatabase database = builder.open();
        RecipeDao dao = database.getRecipeDao();
        List<RecipeDao.RecipeInfo> recipesName = dao.getRecipesName();
        builder.close();
        return recipesName;
    }

    private void autoCommit() {
        if (storeRecipeFuture != null)
            commitSaveRecipe();
        if (deleteRecipeFuture != null)
            commitDeleteRecipe();
        if (updateRecipeFuture != null)
            commitUpdateRecipe();
    }

    public void startDeleteRecipe(Recipe recipe) {
        deleteRecipeFuture = executor.submit(() -> deleteRecipe(recipe));
    }

    public void commitDeleteRecipe() {
        try {
            deleteRecipeFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecipe(Recipe recipe) {
        RecipeDatabase database = builder.open();
        RecipeDelete recipeDelete = new RecipeDelete(database);
        recipeDelete.deleteRecipe(recipe);
        builder.close();
    }

    public void startUpdateRecipe(Recipe recipe) {
        updateRecipeFuture = executor.submit(() -> updateRecipe(recipe));
    }


    public void commitUpdateRecipe() {
        try {
            updateRecipeFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void updateRecipe(Recipe recipe) {
        RecipeDatabase database = builder.open();

        RecipeStore store = new RecipeStore(database);
        store.updateRecipe(recipe);

        builder.close();
    }



    public void startSearchIngredient(String name) {
        searchIngredientFuture = executor.submit(() -> searchIngredient(name));
    }

    public List<String> commitSearchIngredient() {
        return runFutureSearch(searchIngredientFuture);
    }

    public List<String> searchIngredient(String name) {
        RecipeDatabase database = builder.open();
        IngredientDao dao = database.getIngredientDao();
        List<String> output = dao.searchIngredientByName(name + '%');
        builder.close();
        return output;
    }


    public void startSearchRecipe(String name) {
        searchRecipeFuture = executor.submit(() -> searchRecipe(name));
    }

    public List<RecipeDao.RecipeInfo> commitSearchRecipe() {
        return runFutureSearch(searchRecipeFuture);
    }

    public List<RecipeDao.RecipeInfo> searchRecipe(String name) {
        RecipeDatabase database = builder.open();
        RecipeDao dao = database.getRecipeDao();
        List<RecipeDao.RecipeInfo> output = dao.searchByName(name + '%');
        builder.close();
        return output;
    }


    public void startCacheDatabase() {
        builder.startCache();
    }

    public void stopCacheDatabase() {
        builder.stopCache();
    }


    private <T> T runFutureSearch(Future<T> future) {
        if (future == null)
            return null;

        T output = null;
        try {
            output = future.get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException ignored) {}
        return output;
    }



}
