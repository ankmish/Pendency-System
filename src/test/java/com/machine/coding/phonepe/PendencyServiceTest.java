package com.machine.coding.phonepe;

import com.machine.coding.phonepe.dao.IPendencyDao;
import com.machine.coding.phonepe.dao.impl.PendencyDao;
import com.machine.coding.phonepe.enums.TagType;
import com.machine.coding.phonepe.models.Tag;
import com.machine.coding.phonepe.services.IPendencyService;
import com.machine.coding.phonepe.services.impl.PendencyService;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PendencyServiceTest {

    private IPendencyService pendencyService;
    private final Tag upiTag = new Tag("UPI", TagType.INSTRUMENT);
    private final Tag walletTag = new Tag("Wallet", TagType.INSTRUMENT);
    private final Tag bangaloreTag = new Tag("Bangalore", TagType.CITY);
    private final Tag jaipurTag = new Tag("Jaipur", TagType.CITY);
    private final Tag mysoreTag = new Tag("Mysore", TagType.CITY);
    private final Tag karnatakaTag = new Tag("Karnataka", TagType.STATE);
    private final Tag rajasthanTag = new Tag("Rajasthan", TagType.STATE);

    @BeforeEach
    void config() {
        IPendencyDao pendencyDao = new PendencyDao();
        pendencyService = new PendencyService(pendencyDao);
    }


    @Test
    @Description("Test the given sample test cases with start & stop tracking")
    public void testTracking() throws Exception {

        pendencyService.startTracking(1112, Arrays.asList(upiTag, karnatakaTag, bangaloreTag));
        pendencyService.startTracking(2451, Arrays.asList(upiTag, karnatakaTag, mysoreTag));
        pendencyService.startTracking(3421, Arrays.asList(upiTag, rajasthanTag, jaipurTag));
        pendencyService.startTracking(1221, Arrays.asList(walletTag, karnatakaTag, bangaloreTag));

        assertEquals(pendencyService.getCounts(Arrays.asList(upiTag)), 3);
        assertEquals(pendencyService.getCounts(Arrays.asList(upiTag, karnatakaTag)), 2);
        assertEquals(pendencyService.getCounts(Arrays.asList(upiTag, karnatakaTag, bangaloreTag)), 1);
        assertEquals(pendencyService.getCounts(Arrays.asList(bangaloreTag)), 0);

        pendencyService.startTracking(4221, Arrays.asList(walletTag, karnatakaTag, bangaloreTag));
        pendencyService.stopTracking(1112);
        pendencyService.stopTracking(2451);

        assertEquals(pendencyService.getCounts(Arrays.asList(upiTag)), 1);
        assertEquals(pendencyService.getCounts(Arrays.asList(walletTag)), 2);
        assertEquals(pendencyService.getCounts(Arrays.asList(upiTag, karnatakaTag, bangaloreTag)), 0);
    }

    @Test
    @Description("Test the scenario when entity is missing in case of stop track request")
    public void testMissingEntityForStopTracking() throws Exception {

        pendencyService.startTracking(1112, Arrays.asList(upiTag, karnatakaTag, bangaloreTag));
        Exception thrown = assertThrows(
            Exception.class,
            () -> pendencyService.stopTracking(2451),
            ""
        );
        assertTrue(thrown.getMessage().contentEquals("No such entityId: 2451 exists"));
    }

    @Test
    @Description("Test the scenario when duplicate entity insertion is requested")
    public void testDuplicateEntityInsertion() throws Exception {

        pendencyService.startTracking(1112, Arrays.asList(upiTag, karnatakaTag, bangaloreTag));
        Exception thrown = assertThrows(
            Exception.class,
            () -> pendencyService.startTracking(1112, Arrays.asList(upiTag, karnatakaTag, bangaloreTag)),
            ""
        );
        assertTrue(thrown.getMessage().contentEquals("Duplicate insertion request received for entityId: 1112"));
    }
}
