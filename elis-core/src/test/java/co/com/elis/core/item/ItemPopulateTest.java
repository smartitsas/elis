package co.com.elis.core.item;

import co.com.elis.core.tax.Tax;
import co.com.elis.core.tax.TaxType;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class ItemPopulateTest {

    @Test
    public void simpleItemTest() throws ElisCoreException {

        InvoiceItem item = InvoiceItem.populateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setUnitaryValue(1000.2)
                .setQuantity(10.3)
                .setTotal(10000.19)
                .get();

        assertThat(item.getPosition(), is(1));
        assertNull(item.getDescription());
        assertThat(item.getUnitaryValue(), is(BigDecimal.valueOf(1000.2).setScale(4,RoundingMode.HALF_UP)));
        assertThat(item.getQuantity(), is(BigDecimal.valueOf(10.3)));
        assertThat(item.getTotal(), is(BigDecimal.valueOf(10000.19).setScale(4, RoundingMode.HALF_UP)));
    }

    @Test
    public void simpleItemDescriptionTest() throws ElisCoreException {

        InvoiceItem item = InvoiceItem.populateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setUnitaryValue(1000.2)
                .setQuantity(10.3)
                .setTotal(BigDecimal.valueOf(10000.19))
                .withinOptionalSection()
                .setCode("ITM1")                
                .setDescription("Item de 100 cm3")
                .get();

        assertThat(item.getPosition(), is(1));
        assertThat(item.getCode(), is("ITM1"));
        assertThat(item.getDescription(), is("Item de 100 cm3"));
        assertThat(item.getUnitaryValue(), is(BigDecimal.valueOf(1000.2).setScale(4, RoundingMode.HALF_UP)));
        assertThat(item.getQuantity(), is(BigDecimal.valueOf(10.3)));
        assertThat(item.getTotal(), is(BigDecimal.valueOf(10000.19).setScale(4, RoundingMode.HALF_UP)));
    }

    @Test
    public void itemWithIVATest() throws ElisCoreException {
        InvoiceItem item = InvoiceItem.populateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setQuantity(10.3)
                .setUnitaryValue(1000.2)
                .setTotal(10000.19)
                .withinOptionalSection()
                .setCode("ITEM1")
                .setDescription("DESCRIPTION 001")
                //tax of 1000 is wrong but we are verifying that the api doesn't change the value
                .addTax(Tax.createWithZeros(TaxType.IVA).withPercentage(19).withTotal(BigDecimal.valueOf(1000)).build())
                .disableValidations()
                .get();

        assertThat(item.getPosition(), is(1));
        assertThat(item.getCode(), is("ITEM1"));
        assertThat(item.getDescription(), is("DESCRIPTION 001"));
        assertThat(item.getQuantity(), is(BigDecimal.valueOf(10.3)));
        assertThat(item.getUnitaryValue(), is(BigDecimal.valueOf(1000.2).setScale(4, RoundingMode.HALF_UP)));
        assertThat(item.getTotal(), is(BigDecimal.valueOf(10000.19).setScale(4, RoundingMode.HALF_UP)));

        assertThat(item.getTax(TaxType.IVA).getTotal(), is(BigDecimal.valueOf(1000).setScale(0, RoundingMode.HALF_UP)));
        assertThat(item.getTax(TaxType.IVA).getPercentage(), is(BigDecimal.valueOf(19).setScale(4, RoundingMode.HALF_UP)));
        assertThat(item.getTax(TaxType.CONSUMPTION).getTotal(), is(BigDecimal.ZERO));
        assertThat(item.getTax(TaxType.CONSUMPTION).getPercentage(), is(BigDecimal.ZERO));
        assertThat(item.getTax(TaxType.ICA).getTotal(), is(BigDecimal.ZERO));
        assertThat(item.getTax(TaxType.ICA).getPercentage(), is(BigDecimal.ZERO));
    }

    @Test
    public void itemWithConsumptionTest() throws ElisCoreException {
        InvoiceItem item = InvoiceItem.populateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setQuantity(10.3)
                .setUnitaryValue(1000.2)
                .setTotal(10000.19)
                .withinOptionalSection()
                .setCode("ITEM1")
                .setDescription("DESCRIPTION 001")
                //tax of 1000 is wrong but we are verifying that the api doesn't change the value
                .addTax(Tax.createWithZeros(TaxType.CONSUMPTION).withPercentage(8).withTotal(BigDecimal.valueOf(1000.02)).build())
                .disableValidations()
                .get();

        assertThat(item.getPosition(), is(1));
        assertThat(item.getCode(), is("ITEM1"));
        assertThat(item.getDescription(), is("DESCRIPTION 001"));
        assertThat(item.getQuantity(), is(BigDecimal.valueOf(10.3)));
        assertThat(item.getUnitaryValue(), is(BigDecimal.valueOf(1000.2).setScale(4,RoundingMode.HALF_UP)));
        assertThat(item.getTotal(), is(BigDecimal.valueOf(10000.19).setScale(4, RoundingMode.HALF_UP)));

        assertThat(item.getTax(TaxType.IVA).getTotal(), is(BigDecimal.ZERO));
        assertThat(item.getTax(TaxType.IVA).getPercentage(), is(BigDecimal.ZERO));
        assertThat(item.getTax(TaxType.CONSUMPTION).getTotal(), is(BigDecimal.valueOf(1000.02)));
        assertThat(item.getTax(TaxType.CONSUMPTION).getPercentage(), is(BigDecimal.valueOf(8).setScale(4, RoundingMode.HALF_UP)));
        assertThat(item.getTax(TaxType.ICA).getTotal(), is(BigDecimal.ZERO));
        assertThat(item.getTax(TaxType.ICA).getPercentage(), is(BigDecimal.ZERO));
    }

    @Test
    public void itemWithICATest() throws ElisCoreException {
        InvoiceItem item = InvoiceItem.populateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setQuantity(10.3)
                .setUnitaryValue(1000.2)
                .setTotal(10000.19)
                .withinOptionalSection()
                .setCode("ITEM1")
                .setDescription("DESCRIPTION 001")
                //tax of 10 is wrong but we are verifying that the api doesn't change the value
                .addTax(Tax.createWithZeros(TaxType.ICA).withPercentage(5).withTotal(BigDecimal.valueOf(10)).build())
                .disableValidations()
                .get();

        assertThat(item.getPosition(), is(1));
        assertThat(item.getCode(), is("ITEM1"));
        assertThat(item.getDescription(), is("DESCRIPTION 001"));
        assertThat(item.getQuantity(), is(BigDecimal.valueOf(10.3)));
        assertThat(item.getUnitaryValue(), is(BigDecimal.valueOf(1000.2).setScale(4,RoundingMode.HALF_UP)));
        assertThat(item.getTotal(), is(BigDecimal.valueOf(10000.19).setScale(4, RoundingMode.HALF_UP)));

        assertThat(item.getTax(TaxType.IVA).getTotal(), is(BigDecimal.ZERO));
        assertThat(item.getTax(TaxType.IVA).getPercentage(), is(BigDecimal.ZERO));
        assertThat(item.getTax(TaxType.CONSUMPTION).getTotal(), is(BigDecimal.ZERO));
        assertThat(item.getTax(TaxType.CONSUMPTION).getPercentage(), is(BigDecimal.ZERO));
        assertThat(item.getTax(TaxType.ICA).getTotal(), is(BigDecimal.valueOf(10)));
        assertThat(item.getTax(TaxType.ICA).getPercentage(), is(BigDecimal.valueOf(5).setScale(4, RoundingMode.HALF_UP)));
    }

}
