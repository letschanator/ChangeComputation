import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class Change_Computation_Test {
    Change_Computation c1;

    @Test
    void testConstructor(){
        int[] paid = new int[]{1,2,3,4,5,6,7,8};
        c1 = new Change_Computation(10.25,paid);
        assertEquals(10.25,c1.getCost());
        assertEquals(paid,c1.getAmountPaid());
        assertEquals(61,c1.getAmountPaidInBills());
        assertEquals(28,c1.getAmountPaidInChange());
        assertEquals(61.28,c1.getTotalPaid());
    }

    @Test
    void testSetAmountPaidValid(){
        int[] paid = new int[]{1,2,3,4,5,6,7,8};
        c1 = new Change_Computation(10.25,paid);
        paid = new int[]{8,7,6,5,4,3,2,1};
        c1.setAmountPaid(paid);
        assertEquals(paid,c1.getAmountPaid());
    }

    @Test
    void testSetAmountPaidInBillsValid(){
        int[] paid = new int[]{1,2,3,4,5,6,7,8};
        c1 = new Change_Computation(10.25,paid);
        c1.setAmountPaidInBills(10);
        assertEquals(10,c1.getAmountPaidInBills());
    }

    @Test
    void testSetAmountPaidInChangeValid(){
        int[] paid = new int[]{1,2,3,4,5,6,7,8};
        c1 = new Change_Computation(10.25,paid);
        c1.setAmountPaidInChange(10);
        assertEquals(10,c1.getAmountPaidInChange());
    }

    @Test
    void testSetTotalPaidValid(){
        int[] paid = new int[]{1,2,3,4,5,6,7,8};
        c1 = new Change_Computation(10.25,paid);
        c1.setTotalPaid(10.2);
        assertEquals(10.2,c1.getTotalPaid());
    }

    @Test
    void testSetCostValid(){
        int[] paid = new int[]{1,2,3,4,5,6,7,8};
        c1 = new Change_Computation(10.25,paid);
        c1.setCost(10.2);
        assertEquals(10.2,c1.getCost());
    }

    @Test
    void  testSetChangeAvalibleValid(){
        int[] paid = new int[]{1,2,3,4,5,6,7,8};
        c1 = new Change_Computation(10.25,paid);
        paid = new int[]{8,7,6,5,4,3,2,1};
        c1.setChangeAvailable(paid);
        assertEquals(paid,c1.getChangeAvailable());
    }

    @Test
    void testSetChangeValid(){
        int[] paid = new int[]{1,2,3,4,5,6,7,8};
        c1 = new Change_Computation(10.25,paid);
        paid = new int[]{8,7,6,5,4,3,2,1};
        c1.setChange(paid);
        assertEquals(paid,c1.getChange());
    }

    @Test
    void testNotEnoughPaid(){
        int[] paid = new int[]{1,2,3,4,5,6,7,8};
        c1 = new Change_Computation(1000000000,paid);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> c1.changeToReturn());
        assertEquals("Not enough paid, amount paid should be larger or equal to the cost",exception.getMessage());
    }

    @Test
    void testExactChange(){
        int[] paid = new int[]{1,0,0,0,0,0,0,0};
        c1 = new Change_Computation(20,paid);
        int[] change = new int[]{0,0,0,0,0,0,0,0};
        c1.changeToReturn();
        int[] changeFromClass = c1.getChange();
        for (int i = 0; i < change.length; i++){
            assertEquals(change[i],changeFromClass[i]);
        }
    }

    @Test
    void testNotEnoughChange(){
        int[] paid = new int[]{1,0,0,0,0,0,0,0};
        c1 = new Change_Computation(19.99,paid);
        int[] changeAvailable = new int[]{1,1,1,1,1,1,1,0};
        c1.setChangeAvailable(changeAvailable);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> c1.changeToReturn());
        assertEquals("not enough change in the register",exception.getMessage());
    }

    @Test
    void testCorrectChange(){
        int[] paid = new int[]{4,1,1,4,3,2,1,5};
        c1 = new Change_Computation(100,paid);
        int[] change = new int[]{0,0,0,0,0,0,1,0};
        c1.changeToReturn();
        int[] changeFromClass = c1.getChange();
        for( int i = 0; i < change.length; i++){
            assertEquals(change[i],changeFromClass[i]);
        }
    }


}
