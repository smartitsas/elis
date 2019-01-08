package co.com.elis.core.tax;

import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class TaxTest {

    @Test
    public void taxCalculationTest() throws ElisCoreException {

        Tax taxIvaSubtotal = Tax.createWithZeros(TaxType.IVA).withPercentage(19).withTotal(BigDecimal.valueOf(100f)).build();
        Tax taxIvaSubtotal2 = Tax.createWithZeros(TaxType.IVA).withPercentage(16).withTotal(BigDecimal.valueOf(20f)).build();

        Tax taxExcSubtotal = Tax.createWithZeros(TaxType.CONSUMPTION).withPercentage(8).withTotal(BigDecimal.valueOf(100f)).build();
        Tax taxIcaSubtotal = Tax.createWithZeros(TaxType.ICA).withPercentage(1).withTotal(BigDecimal.valueOf(100f)).build();

        TaxTotalList list = TaxTotalList.buildTotalList(taxIvaSubtotal, taxIcaSubtotal, taxExcSubtotal, taxIvaSubtotal2);

        TaxTotal ivaTotal = list.getByType(TaxType.IVA).get();
        TaxTotal icaTotal = list.getByType(TaxType.ICA).get();
        TaxTotal excTotal = list.getByType(TaxType.CONSUMPTION).get();

        assertThat(ivaTotal.getTotal(), is(BigDecimal.valueOf(120f)));
        assertThat(excTotal.getTotal(), is(BigDecimal.valueOf(100f)));
        assertThat(icaTotal.getTotal(), is(BigDecimal.valueOf(100f)));

        assertThat(TaxType.IVA.getDescription(), is("IVA"));
        assertThat(TaxType.ICA.getDescription(), is("ICA"));
        assertThat(TaxType.CONSUMPTION.getDescription(), is("CONSUMO"));

    }

}
