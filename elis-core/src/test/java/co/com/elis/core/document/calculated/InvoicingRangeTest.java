package co.com.elis.core.document.calculated;

import co.com.elis.core.document.InvoicingRangePeriod;
import co.com.elis.core.document.InvoicingRange;
import co.com.elis.core.software.Environment;
import co.com.elis.core.software.Software;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class InvoicingRangeTest {

    private Software software;

    @Before
    public void setUp() throws ElisCoreException {
        software = new Software("ID SOFT", 987L, "SOFTWARE NAME", "SOFTWARE PIN", Environment.HABILITATION);

    }

    @Test(expected = ElisCoreException.class)
    public void buildEmptyInvoiceRangeTest() throws ElisCoreException {
        software.createInvoicingRangeAs().build();
    }

    @Test(expected = ElisCoreException.class)
    public void buildInvoiceRangeWithoutResolutionTest() throws ElisCoreException {
        software.createInvoicingRangeAs()
                .withConsecutiveRange(100L, 100L)
                .withTechnicalKey("TK")
                .withAuthorizationNumber(null)
                .build();
    }

    @Test(expected = ElisCoreException.class)
    public void buildInvoiceRangeWithoutPeriodTest() throws ElisCoreException {
        software.createInvoicingRangeAs()
                .withConsecutiveRange(100L, 100L)
                .withTechnicalKey("TK")
                .withAuthorizationNumber(BigDecimal.ONE)
                .withInvoicingPeriod(null)
                .build();
    }

    @Test(expected = ElisCoreException.class)
    public void buildInvoiceRangeWrongPrefixTest() throws ElisCoreException {

        InvoicingRangePeriod period = new InvoicingRangePeriod(LocalDate.MAX, LocalDate.MIN);

        software.createInvoicingRangeAs()
                .withConsecutiveRange(1L, 100L)
                .withTechnicalKey("TK")
                .withAuthorizationNumber(BigDecimal.ONE)
                .withInvoicingPeriod(period)
                .withPrefix("PRFX01")
                .build();
    }

    @Test
    public void buildInvoiceRangeNoPrefixTest() throws ElisCoreException {

        InvoicingRangePeriod period = new InvoicingRangePeriod(LocalDate.MAX, LocalDate.MIN);

        InvoicingRange invoicingRange = software.createInvoicingRangeAs()
                .withConsecutiveRange(1L, 100L)
                .withTechnicalKey("TK")
                .withAuthorizationNumber(BigDecimal.TEN)
                .withInvoicingPeriod(period)
                .build();

        assertThat(invoicingRange.getFromConsecutive(), is(1L));
        assertThat(invoicingRange.getToConsecutive(), is(100L));
        assertThat(invoicingRange.getTechnicalKey(), is("TK"));
        assertThat(invoicingRange.getAuthorizationNumber(), is(BigDecimal.TEN));
        assertNull(invoicingRange.getPrefix(), null);
        assertThat(invoicingRange.getAuthorizationPeriod(), is(period));
    }

    @Test
    public void buildInvoiceRangeTest() throws ElisCoreException {

        InvoicingRangePeriod period = new InvoicingRangePeriod(LocalDate.MAX, LocalDate.MIN);

        InvoicingRange invoicingRange = software.createInvoicingRangeAs()
                .withConsecutiveRange(1L, 100L)
                .withTechnicalKey("TK")
                .withAuthorizationNumber(BigDecimal.TEN)
                .withInvoicingPeriod(period)
                .withPrefix("PRFX")
                .build();

        assertThat(invoicingRange.getFromConsecutive(), is(1L));
        assertThat(invoicingRange.getToConsecutive(), is(100L));
        assertThat(invoicingRange.getTechnicalKey(), is("TK"));
        assertThat(invoicingRange.getAuthorizationNumber(), is(BigDecimal.TEN));
        assertThat(invoicingRange.getPrefix(), is("PRFX"));
        assertThat(invoicingRange.getAuthorizationPeriod(), is(period));
    }

}
