package co.com.elis.core.document.calculated;

import co.com.elis.core.document.DocumentDate;
import co.com.elis.core.document.Invoice;
import co.com.elis.core.document.InvoicingRange;
import co.com.elis.core.document.InvoicingRangePeriod;
import co.com.elis.core.document.PhysicalLocation;
import co.com.elis.core.document.address.CountrySubdivision;
import co.com.elis.core.item.InvoiceItem;
import co.com.elis.core.item.TaxCalculation;
import co.com.elis.core.person.AccountType;
import co.com.elis.core.person.IdentityDocument;
import co.com.elis.core.person.characterizations.Obligation;
import co.com.elis.core.person.PersonBuilder;
import co.com.elis.core.person.ReceiverParty;
import co.com.elis.core.person.SupplierParty;
import co.com.elis.core.software.Environment;
import co.com.elis.core.software.Software;
import co.com.elis.core.tax.TaxType;
import co.com.elis.core.util.CountrySubdivisionFactory;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import lombok.var;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;

public class CufeCalculationTest {

    private static Software software;

    private static InvoicingRange invoicingRange;

    @BeforeClass
    public static void startup() throws ElisCoreException {
        software = new Software("8bad2864-011e-4fa1-8bfe-843ab63a4bf2", 700085371L, "SOFTWARE NAME", "SOFTPIN", Environment.PRODUCTION);

        LocalDate startPeriod = LocalDate.parse("2014-01-04");
        LocalDate endPeriod = LocalDate.parse("2016-01-04");

        invoicingRange = software.createInvoicingRangeAs()
                .withAuthorizationNumber(BigDecimal.ONE)
                .withTechnicalKey("693ff6f2a553c3646a063436fd4dd9ded0311471")
                .withPrefix("3232")
                .withConsecutiveRange(129L, 150L)
                .withInvoicingPeriod(new InvoicingRangePeriod(startPeriod, endPeriod))
                .build();
    }

    @Test
    public void oneItemInvoiceFromMandatoryTest() throws ElisCoreException {

        PersonBuilder personBuilder = software.getPersonBuilder();

        CountrySubdivision subdivision = CountrySubdivisionFactory.getInstance().findById(11001);
        PhysicalLocation address = PhysicalLocation.createAs()
                .withCountrySubdivision(subdivision)
                .build();

        SupplierParty supplier = personBuilder
                .createSupplierPartyAsJuridicPerson()
                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                .withPhysicalLocation(address)
                .withRegistrationAddress(address)
                .addObligation(Obligation.OTRO_TIPO_OBLIGADO)
                .build();

        ReceiverParty receiverParty = personBuilder
                .createReceiverPartyAsJuridicPerson()
                .withIdentityDocument(new IdentityDocument("800199436", AccountType.NIT))
                .withPhysicalLocation(address)
                .build();

        var item = InvoiceItem.calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setUnitaryValue(150000.00)
                .setQuantity(10)
                .setUnits("BX")
                .setCode("COD01")
                .setStandardCode("24120000")
                .setDescription("ITEM1")
                .withinOptionalSection()
                .addTax(TaxCalculation.of(TaxType.IVA).withPercentage(19).build())
                .getCalculatedResult();

        Invoice invoice = software.calculateInvoiceAs()
                .withinMandatorySection()
                .setInvoicingRange(invoicingRange)
                .setConsecutive(129L)
                .setSupplierParty(supplier)
                .setReceiverParty(receiverParty)
                .setCurrency("COP")
                .setDate(new DocumentDate(LocalDate.of(2019, Month.JANUARY , 16), LocalTime.of(10, 53, 10)))
                .addItem(item)
                .getCalculatedResult();

        assertThat(invoice.getCufe(), is("8bb918b19ba22a694f1da11c643b5e9de39adf60311cf179179e9b33381030bcd4c3c3f156c506ed5908f9276f5bd9b4"));
    }

}
