package org.testing.framework.utils.validators;

import org.junit.Assert;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class AssertionUtility {
    static org.slf4j.Logger LOG = LoggerFactory.getLogger(AssertionUtility.class.getName());

    /**
     * Verifies equality of corresponding elements of an ArrayList<?> with
     * another <ArrayList<?>
     *
     * @param expectedList
     * @param actualList
     */
    public synchronized void assertEqualListInOrder(List<?> expectedList,
                                                    List<?> actualList) {

        LOG.info("\n\nexpected : " + expectedList + "\n\nactual : "
                + actualList + "\n");
        Assert.assertEquals(
                "expected and actual lists do not contain same number of elements",
                expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {

            Assert.assertEquals("expected actual match failed", expectedList.get(i),
                    actualList.get(i));

        }
    }

    /*COMMON OPERATIONS*/

    public synchronized void assertEqualList(List<?> expectedList,
                                             List<?> actualList) {
        LOG.info("\nexpected : " + expectedList + "\nactual : " + actualList
                + "\n");
        Assert.assertEquals(
                "expected and actual lists do not contain same number of elements",
                expectedList.size(), actualList.size());
        Set<Object> set = new HashSet<Object>();

        for (int i = 0; i < actualList.size(); i++) {

            set.add(actualList.get(i));
        }
        for (int i = 0; i < expectedList.size(); i++) {

            Assert.assertEquals("expected actual match failed", true,
                    set.contains(expectedList.get(i)));

        }
    }


    /**
     * Verifies that actual value contains the expected value
     *
     * @param expectedList
     * @param actualList
     */
    public synchronized void assertContainsListInOrder(
            List<String> expectedList, List<String> actualList) {

        LOG.info("\nexpected : " + expectedList + "\nactual : " + actualList
                + "\n");
        Assert.assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {

            Assert.assertEquals("expected " + expectedList.get(i) + " actual "
                    + actualList.get(i), true,
                    actualList.get(i).contains(expectedList.get(i))
            );

        }
    }

    /**
     * Verifies presence of List<?> in List<List<?>>
     *
     * @param subscribed_records should be List<List<?>>
     * @param record             should be List<?>
     * @return
     */
    @SuppressWarnings("unchecked")
    public synchronized static boolean listInCollection(List<?> subscribed_records,
                                                        List<?> record) {

        LOG.info("\nList : " + subscribed_records + "\nItem : " + record + "\n");
        boolean contains = false;

        if (record.get(0) instanceof String)

            for (List<String> subscribed_record : (List<List<String>>) subscribed_records) {
                Assert.assertEquals(subscribed_record.size(), record.size());

                for (int i = 0; i < record.size(); i++) {

                    if ((subscribed_record.get(i) != null)
                            && (record.get(i) != null))
                        if (!subscribed_record.get(i).equals(record.get(i)))
                            break;
                    contains = true;
                }
                if (contains)
                    break;

            }

        else if (record.get(0) instanceof Integer)

            for (List<Integer> subscribed_record : (List<List<Integer>>) subscribed_records) {
                Assert.assertEquals(subscribed_record.size(), record.size());

                for (int i = 0; i < record.size(); i++) {

                    if ((subscribed_record.get(i) != null)
                            && (record.get(i) != null))
                        if (!subscribed_record.get(i).equals(record.get(i)))
                            break;
                    contains = true;
                }
                if (contains)
                    break;

            }

        else if (record.get(0) instanceof Long)
            for (List<Long> subscribed_record : (List<List<Long>>) subscribed_records) {
                Assert.assertEquals(subscribed_record.size(), record.size());

                for (int i = 0; i < record.size(); i++) {

                    if ((subscribed_record.get(i) != null)
                            && (record.get(i) != null))
                        if (!subscribed_record.get(i).equals(record.get(i)))
                            break;
                    contains = true;
                }
                if (contains)
                    break;

            }

        LOG.info("\ncontains : " + contains + "\n");

        return contains;
    }

    /**
     * Verifies if an item exists in an List<?>
     *
     * @param subscribed_records
     * @param record
     * @return
     */
    @SuppressWarnings("unchecked")
    public synchronized static boolean itemInList(List<?> subscribed_records,
                                                  Object record) {
        LOG.info("\nList : " + subscribed_records + "\nItem : " + record + "\n");

        boolean contains = false;

        if (record instanceof String)
            for (String subscribed_record : (List<String>) subscribed_records) {
                if (subscribed_record.equals(record)) {
                    contains = true;
                    break;
                }
            }
        else if (record instanceof Integer)
            for (Integer subscribed_record : (List<Integer>) subscribed_records) {
                if (subscribed_record.equals(record)) {
                    contains = true;
                    break;
                }
            }
        else if (record instanceof Long)
            for (Long subscribed_record : (List<Long>) subscribed_records) {
                if (subscribed_record.equals(record)) {
                    contains = true;
                    break;
                }
            }

        LOG.info("\ncontains : " + contains + "\n");
        return contains;
    }


    /**
     * Verifies that both sets contain the same elements
     *
     * @param expected
     * @param actual
     */


    public synchronized void assertEqualSets(Set<?> expected, Set<?> actual) {

        LOG.info("\nexpected : " + expected + "\nactual : " + actual + "\n");

        Assert.assertEquals("actual set doesn't contains all values of expected set",
                true, actual.containsAll(expected));

        actual.removeAll(expected);
        LOG.info("\ndifference of actual and expected : " + actual + "\n");
        Assert.assertEquals("unexpected records found in actual set", true,
                actual.size() == 0);

    }

}
