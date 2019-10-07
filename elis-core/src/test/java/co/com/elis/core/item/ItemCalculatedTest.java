package co.com.elis.core.item;

import co.com.elis.core.tax.Tax;
import co.com.elis.core.tax.TaxType;
import co.com.elis.exception.ElisCoreException;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static co.com.elis.core.util.DecimalUtils.scaled;
import static co.com.elis.core.util.DecimalUtils.scaledOrNull;
import java.math.BigDecimal;
import static org.junit.Assert.assertTrue;

public class ItemCalculatedTest {

    @Test
    public void calculateItemSimpleDescriptionTest() throws ElisCoreException {
        InvoiceItem item = InvoiceItem
                .calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setQuantity(10.3)
                .setUnits("BX")
                .setUnitaryValue(1000.2)
                .setDescription("DESCRIPTION 001")
                .withinOptionalSection()
                .setCode("ITEM1")
                .getCalculatedResult();

        assertThat(item.getPosition(), is(1));
        assertThat(item.getQuantity(), is(scaled(10.3f)));
        assertThat(item.getUnitaryValue(), is(scaled(1000.2f)));
        assertThat(item.getCode(), is("ITEM1"));
        assertThat(item.getUnits(), is("BX"));
        assertThat(item.getDescription(), is("DESCRIPTION 001"));
        assertThat(item.getTotal(), is(scaled(10302.0600)));
        assertTrue(item.getTaxes().isEmpty());
    }

    @Test
    public void calculateItemWithIVATest() throws ElisCoreException {
        InvoiceItem item = InvoiceItem
                .calculateAs()
                .withinMandatorySection()
                .setUnits("BX")
                .setPosition(1)
                .setQuantity(10.3)
                .setUnitaryValue(1000.2)
                .setDescription("DESCRIPTION 001")
                .withinOptionalSection()
                .setCode("ITEM1")
                .addTax(TaxCalculation.of(TaxType.IVA).withPercentage(18))
                .getCalculatedResult();

        Tax vat = item.getTax(TaxType.IVA);
        assertThat(vat.getTaxableAmount(), is(scaled(10302.0600)));
        assertThat(vat.getPercentage(), is(scaledOrNull(BigDecimal.valueOf(18f), 2)));
        assertThat(vat.getTaxTotal(), is(scaled(1854.3708)));

        Tax consumption = item.getTax(TaxType.CONSUMPTION);
        assertThat(consumption.getTaxableAmount(), is(scaled(10302.0600)));
        assertThat(consumption.getPercentage(), is(scaledOrNull(BigDecimal.ZERO, 2)));
        assertThat(consumption.getTaxTotal(), is(scaled(0)));
        assertThat(consumption.getTaxTotal(), is(scaled(0)));

        Tax ica = item.getTax(TaxType.ICA);
        assertThat(ica.getTaxableAmount(), is(scaled(10302.0600)));
        assertThat(ica.getPercentage(), is(scaledOrNull(BigDecimal.ZERO, 2)));
        assertThat(ica.getTaxTotal(), is(scaled(0)));
        assertThat(ica.getTaxTotal(), is(scaled(0)));

        assertThat(item.getTotal(), is(scaled(10302.06)));
    }

    @Test
    public void calculateItemWithConsumptionTest() throws ElisCoreException {
        InvoiceItem item = InvoiceItem
                .calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setQuantity(10.3)
                .setUnitaryValue(1000.2)
                .setUnits("BX")
                .setDescription("DESCRIPTION 001")
                .withinOptionalSection()
                .setCode("ITEM1")
                .addTax(TaxCalculation.of(TaxType.CONSUMPTION).withPercentage(8))
                .getCalculatedResult();

        assertThat(item.getPosition(), is(1));
        assertThat(item.getDescription(), is("DESCRIPTION 001"));
        assertThat(item.getQuantity(), is(scaled(10.3)));
        assertThat(item.getUnitaryValue(), is(scaled(1000.2)));
        assertThat(item.getTotal(), is(scaled(10302.06)));

        Tax vat = item.getTax(TaxType.IVA);
        assertThat(vat.getTaxableAmount(), is(scaled(10302.0600)));
        assertThat(vat.getPercentage(), is(scaledOrNull(BigDecimal.ZERO, 2)));
        assertThat(vat.getTaxTotal(), is(scaled(0)));
        assertThat(vat.getTaxTotal(), is(scaled(0)));

        Tax consumption = item.getTax(TaxType.CONSUMPTION);
        assertThat(consumption.getTaxableAmount(), is(scaled(10302.0600)));
        assertThat(consumption.getPercentage(), is(scaledOrNull(BigDecimal.valueOf(8f), 2)));
        assertThat(consumption.getTaxTotal(), is(scaled(824.1648)));

        Tax ica = item.getTax(TaxType.ICA);
        assertThat(ica.getTaxableAmount(), is(scaled(10302.0600)));
        assertThat(ica.getPercentage(), is(scaledOrNull(BigDecimal.ZERO, 2)));
        assertThat(ica.getTaxTotal(), is(scaled(0)));
        assertThat(ica.getTaxTotal(), is(scaled(0)));
    }

    @Test
    public void calculateItemWithICATest() throws ElisCoreException {
        InvoiceItem item = InvoiceItem
                .calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setQuantity(10.3)
                .setUnitaryValue(1000.2)
                .setUnits("BX")
                .setDescription("DESCRIPTION 001")
                .withinOptionalSection()
                .setCode("ITEM1")
                .addTax(TaxCalculation.of(TaxType.ICA).withPercentage(5))
                .getCalculatedResult();

        assertThat(item.getPosition(), is(1));
        assertThat(item.getDescription(), is("DESCRIPTION 001"));
        assertThat(item.getQuantity(), is(scaled(10.3)));
        assertThat(item.getUnitaryValue(), is(scaled(1000.2)));
        assertThat(item.getTotal(), is(scaled(10302.06)));

        Tax vat = item.getTax(TaxType.IVA);
        assertThat(vat.getTaxableAmount(), is(scaled(10302.0600)));
        assertThat(vat.getPercentage(), is(scaledOrNull(BigDecimal.ZERO, 2)));
        assertThat(vat.getTaxTotal(), is(scaled(0)));

        Tax consumption = item.getTax(TaxType.CONSUMPTION);
        assertThat(consumption.getTaxableAmount(), is(scaled(10302.0600)));
        assertThat(consumption.getPercentage(), is(scaledOrNull(BigDecimal.ZERO, 2)));
        assertThat(consumption.getTaxTotal(), is(scaled(0)));

        Tax ica = item.getTax(TaxType.ICA);
        assertThat(ica.getTaxableAmount(), is(scaled(10302.0600)));
        assertThat(ica.getPercentage(), is(scaledOrNull(BigDecimal.valueOf(5f), 2)));
        assertThat(ica.getTaxTotal(), is(scaled(515.103)));
    }

}
