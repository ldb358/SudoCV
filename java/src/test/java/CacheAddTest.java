/**
 * Created by brenemal on 8/31/16.
 */
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import sudocv.Cache;

import java.util.ArrayList;

public class CacheAddTest {
    @Test
    public void addToFirst() {
        Cache c = new Cache();
        c.add(0, 0, 3);
        ArrayList<Integer> test = new ArrayList<>();
        test.add(3);
        assertEquals(test, c.get(0, 0));
    }
    @Test
    public void addSecond() {
        Cache c = new Cache();
        c.add(0, 0, 3);
        c.add(0, 0, 5);
        ArrayList<Integer> test = new ArrayList<>();
        test.add(3);
        test.add(5);
        assertEquals(test, c.get(0,0));
    }
    @Test
    public void addSame() {
        Cache c = new Cache();
        c.add(0, 0, 3);
        c.add(0, 0, 3);
        ArrayList<Integer> test = new ArrayList<>();
        test.add(3);
        assertEquals(test, c.get(0,0));
    }
    @Test
    public void addSorted() {
        Cache c = new Cache();
        c.add(0, 0, 5);
        c.add(0, 0, 3);
        ArrayList<Integer> test = new ArrayList<>();
        test.add(3);
        test.add(5);
        assertEquals(test, c.get(0,0));
    }
    @Test
    public void addSet() {
        Cache c = new Cache();
        ArrayList<Integer> test = new ArrayList<>();
        test.add(3);
        c.add(0, 0, test);
        assertEquals(test, c.get(0,0));
    }
    @Test
    public void addSetUnsorted() {
        Cache c = new Cache();
        ArrayList<Integer> test = new ArrayList<>();
        test.add(3);
        test.add(1);
        c.add(0, 0, test);
        ArrayList<Integer> test2 = new ArrayList<>();
        test2.add(1);
        test2.add(3);
        assertEquals(test2, c.get(0,0));
    }
    @Test
    public void addSetDuplicates() {
        Cache c = new Cache();
        ArrayList<Integer> test = new ArrayList<>();
        test.add(3);
        test.add(3);
        c.add(0, 0, test);
        ArrayList<Integer> test2 = new ArrayList<>();
        test2.add(3);
        assertEquals(test2, c.get(0,0));
    }
    @Test
    public void addNotFirst(){
        Cache c = new Cache();
        ArrayList<Integer> test = new ArrayList<>();
        test.add(3);
        c.add(2, 8, 3);
        assertEquals(test, c.get(2,8));
    }
    @Test
    public void addSetNotFirst(){
        Cache c = new Cache();
        ArrayList<Integer> test = new ArrayList<>();
        test.add(3);
        c.add(2, 8, test);
        assertEquals(test, c.get(2,8));
    }
}
