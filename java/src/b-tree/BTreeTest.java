import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class BTreeTest {

    @Test
    public void testRecursiveInsert() {
        BTree tree = new BTree();
        tree.recursiveInsert(10);
        tree.recursiveInsert(20);
        tree.recursiveInsert(5);
        tree.recursiveInsert(6);
        tree.recursiveInsert(15);
        tree.recursiveInsert(30);
        tree.recursiveInsert(25);
        tree.recursiveInsert(35);

        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(10);
        expected.add(20);
        expected.add(30);

        assertEquals(expected, tree.getRoot().keys);
        assertNotNull(tree.recursiveSearch(10));
    }
}