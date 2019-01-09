package co.com.elis.core.item;

import co.com.elis.core.tax.TaxType;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class ItemCalculatedTest {

    @Test
    public void calculateItemSimpleTest() throws ElisCoreException {
        InvoiceItem item = InvoiceItem.calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setQuantity(10.3)
                .setUnitaryValue(1000.2)
                .getCalculatedResult();

        assertThat(item.getPosition(), is(1));
        assertNull(item.getDescription());
        assertThat(item.getQuantity(), is(BigDecimal.valueOf(10.3)));
        assertThat(item.getUnitaryValue(), is(BigDecimal.valueOf(1000.2).setScale(4, RoundingMode.HALF_UP)));
        assertThat(item.getTotal(), is(BigDecimal.valueOf(10302.06).setScale(4, RoundingMode.HALF_UP)));
    }

    @Test
    public void calculateItemSimpleDescriptionTest() throws ElisCoreException {
        InvoiceItem item = InvoiceItem.calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setQuantity(10.3)
                .setUnitaryValue(1000.2)
                .withinOptionalSection()
                .setCode("ITEM1")
                .setDescription("DESCRIPTION 001")
                .getCalculatedResult();

        assertThat(item.getPosition(), is(1));
        assertThat(item.getDescription(), is("DESCRIPTION 001"));
        assertThat(item.getQuantity(), is(BigDecimal.valueOf(10.3)));
        assertThat(item.getUnitaryValue(), is(BigDecimal.valueOf(1000.2).setScale(4, RoundingMode.HALF_UP)));
        assertThat(item.getTotal(), is(BigDecimal.valueOf(10302.06).setScale(4, RoundingMode.HALF_UP)));
    }

    @Test
    public void calculateItemWithIVATest() throws ElisCoreException {
        InvoiceItem item = InvoiceItem.calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setQuantity(10.3)
                .setUnitaryValue(1000.2)
                .withinOptionalSection()
                .setCode("ITEM1")                
                .addTax(TaxCalculation.of(TaxType.IVA).withPercentage(18))
                .setDescription("DESCRIPTION 001")
                .getCalculatedResult();

        assertThat(item.getPosition(), is(1));
        assertThat(item.getDescription(), is("DESCRIPTION 001"));
        assertThat(item.getQuantity(), is(BigDecimal.valueOf(10.3)));
        assertThat(item.getUnitaryValue(), is(BigDecimal.valueOf(1000.2).setScale(4, RoundingMode.HALF_UP)));
        assertThat(item.getTotal(), is(BigDecimal.valueOf(10302.06).setScale(4, RoundingMode.HALF_UP)));

        assertThat(item.getTax(TaxType.IVA).getTotal(), is(BigDecimal.valueOf(1854.3708)));
        assertThat(item.getTax(TaxType.CONSUMPTION).getTotal(), is(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP)));
        assertThat(item.getTax(TaxType.ICA).getTotal(), is(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP)));
    }

    @Test
    public void calculateItemWithConsumptionTest() throws ElisCoreException {
        InvoiceItem item = InvoiceItem.calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setQuantity(10.3)
                .setUnitaryValue(1000.2)
                .withinOptionalSection()
                .setCode("ITEM1")
                .addTax(TaxCalculation.of(TaxType.CONSUMPTION).withPercentage(8))
                .setDescription("DESCRIPTION 001")
                .getCalculatedResult();

        assertThat(item.getPosition(), is(1));
        assertThat(item.getDescription(), is("DESCRIPTION 001"));
        assertThat(item.getQuantity(), is(BigDecimal.valueOf(10.3)));
        assertThat(item.getUnitaryValue(), is(BigDecimal.valueOf(1000.2).setScale(4, RoundingMode.HALF_UP)));
        assertThat(item.getTotal(), is(BigDecimal.valueOf(10302.06).setScale(4, RoundingMode.HALF_UP)));

        assertThat(item.getTax(TaxType.IVA).getTotal(), is(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP)));
        assertThat(item.getTax(TaxType.CONSUMPTION).getTotal(), is(BigDecimal.valueOf(824.1648)));
        assertThat(item.getTax(TaxType.ICA).getTotal(), is(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP)));
    }

    @Test
    public void calculateItemWithICATest() throws ElisCoreException {
        InvoiceItem item = InvoiceItem.calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setQuantity(10.3)
                .setUnitaryValue(1000.2)
                .withinOptionalSection()
                .setCode("ITEM1")
                .addTax(TaxCalculation.of(TaxType.ICA).withPercentage(5))
                .setDescription("DESCRIPTION 001")
                .getCalculatedResult();

        assertThat(item.getPosition(), is(1));
        assertThat(item.getDescription(), is("DESCRIPTION 001"));
        assertThat(item.getQuantity(), is(BigDecimal.valueOf(10.3)));
        assertThat(item.getUnitaryValue(), is(BigDecimal.valueOf(1000.2).setScale(4, RoundingMode.HALF_UP)));
        assertThat(item.getTotal(), is(BigDecimal.valueOf(10302.06).setScale(4, RoundingMode.HALF_UP)));

        assertThat(item.getTax(TaxType.IVA).getTotal(), is(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP)));
        assertThat(item.getTax(TaxType.CONSUMPTION).getTotal(), is(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP)));
        assertThat(item.getTax(TaxType.ICA).getTotal(), is(BigDecimal.valueOf(515.103).setScale(4)));
    }

}
