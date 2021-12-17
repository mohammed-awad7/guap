import org.junit.Assert;
import org.junit.Test;

public class SkippListTest {
    @Test
    public void delete() {
        SkippableList<Integer> list = new SkipList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.insert(4);

        Assert.assertTrue(list.delete(2));
    }
    
     @Test
    public void deleteMissing(){
        SkippableList<Integer> list = new SkipList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.insert(4);
        
        Assert.assertFalse(list.delete(10));
    }
    
     @Test
     public void deleteOneOfMany(){
         SkippableList<Integer> list = new SkipList<>();
         list.insert(1);
         list.insert(2);
         list.insert(2);
         list.insert(3);
         list.insert(4);
         Assert.assertTrue(list.delete(2));
     }
    
    @Test
    public void search(){
        SkippableList<Integer> list = new SkipList<>();
        list.insert(1);
        list.insert(2);
        list.insert(2);
        list.insert(3);
        list.insert(4);
        Assert.assertNotEquals(list.search(3),list.search(4));
    }

    @Test
    public void searchMissing(){
        SkippableList<Integer> list = new SkipList<>();
        list.insert(1);
        list.insert(2);
        list.insert(2);
        list.insert(3);
        list.insert(4);
        Assert.assertEquals(list.search(10),null);
    }
}

