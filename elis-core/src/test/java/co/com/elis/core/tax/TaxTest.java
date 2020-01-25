package co.com.elis.core.tax;

import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class TaxTest {

    @Test
    public void taxCalculationTest() throws ElisCoreException {

        Tax taxIvaSubtotal = Tax.createWithZeros(TaxType.IVA).withPercentage(19).build();
        Tax taxIvaSubtotal2 = Tax.createWithZeros(TaxType.IVA).withPercentage(16).build();

        Tax taxExcSubtotal = Tax.createWithZeros(TaxType.CONSUMPTION).withPercentage(8).build();
        Tax taxIcaSubtotal = Tax.createWithZeros(TaxType.ICA).withPercentage(1).build();

        TaxTotalList list = TaxTotalList.buildTotalList(taxIvaSubtotal, taxIcaSubtotal, taxExcSubtotal, taxIvaSubtotal2);


        assertThat(TaxType.IVA.getDescription(), is("IVA"));
        assertThat(TaxType.ICA.getDescription(), is("ICA"));
        assertThat(TaxType.CONSUMPTION.getDescription(), is("CONSUMO"));

    }

}
