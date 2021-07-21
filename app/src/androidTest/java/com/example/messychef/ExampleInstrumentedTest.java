package com.example.messychef;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.messychef.storage_facility.CurrentRecipe;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.messychef", appContext.getPackageName());
    }

    @Test
    public void testEmptyCacheRecipeName() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        CurrentRecipe currentRecipe = new CurrentRecipe(appContext);
        try {
            assertNull(currentRecipe.getCurrentRecipeIndex());
            assertNull(currentRecipe.getCurrentRecipeName());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testCacheRecipeName() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        CurrentRecipe currentRecipe = new CurrentRecipe(appContext);
        String name = "TEST-NAME";
        try {
            currentRecipe.setCurrentRecipeName(name);
            String cache = currentRecipe.getCurrentRecipeName();
            assertEquals(cache, name);
            int value = currentRecipe.getCurrentRecipeIndex();
            assertEquals(value, 0);

            currentRecipe.setCurrentRecipeIndex(45);
            value = currentRecipe.getCurrentRecipeIndex();
            assertEquals(value, 45);

            currentRecipe.clear();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }



}