package co.com.elis.core.tax.validation;

import co.com.elis.core.tax.Tax;
import co.com.elis.core.tax.TaxTotal;
import co.com.elis.core.tax.TaxTotalList;
import co.com.elis.core.tax.TaxType;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TaxValidationTest {

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("ValidationMessages");

    @Test(expected = NullPointerException.class)
    public void containsNullType() throws ElisCoreException {
        TaxTotalList totalList;

        //nulls
        totalList = TaxTotalList.buildTotalList(Tax.createWithZeros(null).build());
        totalList.validate();
    }

    @Test
    public void containsDefaultTaxesTest() throws ElisCoreException {

        TaxTotalList totalList;
        Set<ConstraintViolation<TaxTotalList>> validations;

        //Only IVA
        totalList = TaxTotalList.buildTotalList(Tax.createWithZeros(TaxType.IVA).withPercentage(1).build());
        validations = totalList.validate();

        assertTrue(validations.iterator().hasNext());
        assertThat(validations.iterator().next().getMessage(), is(MESSAGES.getString("ELIS_CORE_VAL_TAX_SUBTAXES")));

        //Only ICA
        totalList = TaxTotalList.buildTotalList(Tax.createWithZeros(TaxType.ICA).withPercentage(1).build());
        validations = totalList.validate();

        assertTrue(validations.iterator().hasNext());
        assertThat(validations.iterator().next().getMessage(), is(MESSAGES.getString("ELIS_CORE_VAL_TAX_SUBTAXES")));

        //Only CONSUMPTION
        totalList = TaxTotalList.buildTotalList(Tax.createWithZeros(TaxType.CONSUMPTION).withPercentage(1).build());
        validations = totalList.validate();

        assertTrue(validations.iterator().hasNext());
        assertThat(validations.iterator().next().getMessage(), is(MESSAGES.getString("ELIS_CORE_VAL_TAX_SUBTAXES")));

        //Only IVA and ICA
        totalList = TaxTotalList.buildTotalList(
                Tax.createWithZeros(TaxType.ICA).withTotal(1).build(),
                Tax.createWithZeros(TaxType.IVA).withTotal(1).build()
        );
        validations = totalList.validate();

        assertTrue(validations.iterator().hasNext());
        assertThat(validations.iterator().next().getMessage(), is(MESSAGES.getString("ELIS_CORE_VAL_TAX_SUBTAXES")));

        //Only CONSUMPTION and ICA
        totalList = TaxTotalList.buildTotalList(
                Tax.createWithZeros(TaxType.CONSUMPTION).withTotal(1).build(),
                Tax.createWithZeros(TaxType.ICA).withTotal(1).build()
        );
        validations = totalList.validate();

        assertTrue(validations.iterator().hasNext());
        assertThat(validations.iterator().next().getMessage(), is(MESSAGES.getString("ELIS_CORE_VAL_TAX_SUBTAXES")));

        //Only IVA and CONSUMPTION
        totalList = TaxTotalList.buildTotalList(
                Tax.createWithZeros(TaxType.IVA).withPercentage(1).build(),
                Tax.createWithZeros(TaxType.CONSUMPTION).withPercentage(1).build()
        );
        validations = totalList.validate();

        assertTrue(validations.iterator().hasNext());
        assertThat(validations.iterator().next().getMessage(), is(MESSAGES.getString("ELIS_CORE_VAL_TAX_SUBTAXES")));

        //All taxes
        totalList = TaxTotalList.buildTotalList(
                Tax.createWithZeros(TaxType.IVA).withTotal(1).build(),
                Tax.createWithZeros(TaxType.CONSUMPTION).withTotal(1).build(),
                Tax.createWithZeros(TaxType.ICA).withTotal(1).build()
        );
        validations = totalList.validate();

        assertFalse(validations.iterator().hasNext());
    }

    @Test
    public void consistentTaxTotalTest() throws ElisCoreException {

        List<Tax> ivaSubtotals = Arrays.asList(
                Tax.createWithZeros(TaxType.IVA).withTotal(1).build(),
                Tax.createWithZeros(TaxType.IVA).withTotal(10).build()
        );

        List<Tax> icaSubtotals = Arrays.asList(
                Tax.createWithZeros(TaxType.ICA).withTotal(1).build(),
                Tax.createWithZeros(TaxType.ICA).withTotal(10).build()
        );

        List<Tax> consumptionSubtotals = Arrays.asList(
                Tax.createWithZeros(TaxType.CONSUMPTION).withTotal(1).build(),
                Tax.createWithZeros(TaxType.CONSUMPTION).withTotal(10).build()
        );

        //total validation consistency for ICA
        List<TaxTotal> taxTotals = Arrays.asList(
                new TaxTotal(TaxType.ICA, BigDecimal.valueOf(5), icaSubtotals),
                new TaxTotal(TaxType.IVA, BigDecimal.valueOf(11), ivaSubtotals),
                new TaxTotal(TaxType.CONSUMPTION, BigDecimal.valueOf(11), consumptionSubtotals)
        );

        TaxTotalList totalList = new TaxTotalList(taxTotals);

        Set<ConstraintViolation<TaxTotalList>> validations = totalList.validate();

        assertTrue(validations.iterator().hasNext());
        assertThat(validations.iterator().next().getMessage(), is(MESSAGES.getString("ELIS_CORE_VAL_TAX_CONSISTENT_TOTAL")));

        //total validation consistency for IVA
        taxTotals = Arrays.asList(
                new TaxTotal(TaxType.ICA, BigDecimal.valueOf(11), icaSubtotals),
                new TaxTotal(TaxType.IVA, BigDecimal.valueOf(8), ivaSubtotals),
                new TaxTotal(TaxType.CONSUMPTION, BigDecimal.valueOf(11), consumptionSubtotals)
        );

        totalList = new TaxTotalList(taxTotals);
        validations = totalList.validate();

        assertTrue(validations.iterator().hasNext());
        assertThat(validations.iterator().next().getMessage(), is(MESSAGES.getString("ELIS_CORE_VAL_TAX_CONSISTENT_TOTAL")));

        //total validation consistency for CONSUMPTION
        taxTotals = Arrays.asList(
                new TaxTotal(TaxType.ICA, BigDecimal.valueOf(11), icaSubtotals),
                new TaxTotal(TaxType.IVA, BigDecimal.valueOf(11), ivaSubtotals),
                new TaxTotal(TaxType.CONSUMPTION, BigDecimal.valueOf(100), consumptionSubtotals)
        );

        totalList = new TaxTotalList(taxTotals);
        validations = totalList.validate();

        assertTrue(validations.iterator().hasNext());
        assertThat(validations.iterator().next().getMessage(), is(MESSAGES.getString("ELIS_CORE_VAL_TAX_CONSISTENT_TOTAL")));

    }

    @Test
    public void consistentTaxTotalTypeTest() throws ElisCoreException {

        List<Tax> ivaSubtotals = Arrays.asList(
                Tax.createWithZeros(TaxType.IVA).withTotal(1).build(),
                Tax.createWithZeros(TaxType.IVA).withTotal(10).build()
        );

        List<Tax> icaSubtotals = Arrays.asList(
                Tax.createWithZeros(TaxType.ICA).withTotal(1).build(),
                Tax.createWithZeros(TaxType.ICA).withTotal(10).build()
        );

        List<Tax> consumptionSubtotals = Arrays.asList(
                Tax.createWithZeros(TaxType.CONSUMPTION).withTotal(1).build(),
                Tax.createWithZeros(TaxType.CONSUMPTION).withTotal(10).build()
        );

        //Wrong tax type in ICA TaxTotal
        List<TaxTotal> taxTotals = Arrays.asList(
                new TaxTotal(TaxType.ICA, BigDecimal.valueOf(11), ivaSubtotals),
                new TaxTotal(TaxType.IVA, BigDecimal.valueOf(11), ivaSubtotals),
                new TaxTotal(TaxType.CONSUMPTION, BigDecimal.valueOf(11), consumptionSubtotals)
        );

        TaxTotalList totalList = new TaxTotalList(taxTotals);

        Set<ConstraintViolation<TaxTotalList>> validations = totalList.validate();

        assertTrue(validations.iterator().hasNext());
        assertThat(validations.iterator().next().getMessage(), is(MESSAGES.getString("ELIS_CORE_VAL_TAX_CONSISTENT_TOTAL_TYPE")));

        //Wrong tax type in IVA TaxTotal
        taxTotals = Arrays.asList(
                new TaxTotal(TaxType.ICA, BigDecimal.valueOf(11), icaSubtotals),
                new TaxTotal(TaxType.IVA, BigDecimal.valueOf(11), consumptionSubtotals),
                new TaxTotal(TaxType.CONSUMPTION, BigDecimal.valueOf(11), consumptionSubtotals)
        );

        totalList = new TaxTotalList(taxTotals);
        validations = totalList.validate();

        assertTrue(validations.iterator().hasNext());
        assertThat(validations.iterator().next().getMessage(), is(MESSAGES.getString("ELIS_CORE_VAL_TAX_CONSISTENT_TOTAL_TYPE")));

        //Wrong tax type in CONSUMPTION TaxTotal
        taxTotals = Arrays.asList(
                new TaxTotal(TaxType.ICA, BigDecimal.valueOf(11), icaSubtotals),
                new TaxTotal(TaxType.IVA, BigDecimal.valueOf(11), ivaSubtotals),
                new TaxTotal(TaxType.CONSUMPTION, BigDecimal.valueOf(11), ivaSubtotals)
        );

        totalList = new TaxTotalList(taxTotals);
        validations = totalList.validate();

        assertTrue(validations.iterator().hasNext());
        assertThat(validations.iterator().next().getMessage(), is(MESSAGES.getString("ELIS_CORE_VAL_TAX_CONSISTENT_TOTAL_TYPE")));

        //Wrong tax type in CONSUMPTION TaxTotal
        taxTotals = Arrays.asList(
                new TaxTotal(TaxType.ICA, BigDecimal.valueOf(11), consumptionSubtotals),
                new TaxTotal(TaxType.IVA, BigDecimal.valueOf(11), ivaSubtotals),
                new TaxTotal(TaxType.CONSUMPTION, BigDecimal.valueOf(11), ivaSubtotals)
        );

        totalList = new TaxTotalList(taxTotals);
        validations = totalList.validate();

        assertTrue(validations.iterator().hasNext());
        assertThat(validations.iterator().next().getMessage(), is(MESSAGES.getString("ELIS_CORE_VAL_TAX_CONSISTENT_TOTAL_TYPE")));

    }

}
