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
import co.com.elis.core.person.Obligation;
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
        software = new Software("8bad2864-011e-4fa1-8bfe-843ab63a4bf2", 700085380L, "SOFTWARE NAME", "SOFTPIN", Environment.HABILITATION);

        LocalDate startPeriod = LocalDate.parse("2014-01-04");
        LocalDate endPeriod = LocalDate.parse("2016-01-04");

        invoicingRange = software.createInvoicingRangeAs()
                .withAuthorizationNumber(BigDecimal.ONE)
                .withTechnicalKey("TECHKEY")
                .withPrefix("PRFX")
                .withConsecutiveRange(10007869L, 19999999L)
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
                .withIdentityDocument(new IdentityDocument("0", AccountType.NIT))
                .withPhysicalLocation(address)
                .build();

        var item = InvoiceItem.calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setUnitaryValue(1000)
                .setQuantity(10)
                .setUnits("BX")
                .withinOptionalSection()
                .addTax(TaxCalculation.of(TaxType.IVA).withPercentage(10))
                .getCalculatedResult();

        Invoice invoice = software.calculateInvoiceAs()
                .withinMandatorySection()
                .setInvoicingRange(invoicingRange)
                .setConsecutive(10007869L)
                .setSupplierParty(supplier)
                .setReceiverParty(receiverParty)
                .setCurrency("COP")
                .setDate(new DocumentDate(LocalDate.ofYearDay(2018, 256), LocalTime.of(5, 0)))
                .addItem(item)
                .getCalculatedResult();

        assertThat(invoice.getCufe(), is("676C416DAB64BA15852265D6592DEDFE095A199C"));
    }

}
