package co.com.elis.core.document.calculated;

import co.com.elis.core.document.DocumentType;
import co.com.elis.core.document.ExchangeRate;
import co.com.elis.core.document.Invoice;
import co.com.elis.core.document.DocumentDate;
import co.com.elis.core.document.InvoiceType;
import co.com.elis.core.document.InvoicingRangePeriod;
import co.com.elis.core.document.PhysicalLocation;
import co.com.elis.core.document.address.CountrySubdivision;
import co.com.elis.core.document.allowance.AllowanceCharge;
import co.com.elis.core.item.InvoiceItem;
import co.com.elis.core.person.AccountType;
import co.com.elis.core.person.IdentityDocument;
import co.com.elis.core.person.JuridicPersonName;
import co.com.elis.core.person.Obligation;
import co.com.elis.core.person.PersonBuilder;
import co.com.elis.core.person.ReceiverParty;
import co.com.elis.core.person.SupplierParty;
import co.com.elis.core.software.Environment;
import co.com.elis.core.software.Software;
import co.com.elis.core.util.CountrySubdivisionFactory;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.var;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ExportationInvoiceTest {

    private SupplierParty supplier;
    private ReceiverParty receiver;
    private InvoiceItem item;
    private Software software;

    @Before
    public void prepareData() throws ElisCoreException {

        software = new Software("IDSOFT", 909090L, "SOFT1", "PIN123", Environment.HABILITATION);

        PersonBuilder personBuilder = software.getPersonBuilder();

        CountrySubdivision subdivision = CountrySubdivisionFactory.getInstance().findById(11001);
        PhysicalLocation address = PhysicalLocation.createAs()
                .withCountrySubdivision(subdivision)
                .build();

        supplier = personBuilder.createSupplierPartyAsJuridicPerson()
                .withName(new JuridicPersonName("commercialName", "registrationName"))
                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                .withPhysicalLocation(address)
                .withRegistrationAddress(address)
                .addObligation(Obligation.FACTURA_ELECTRONICA_VOLUNTARIA_MODELO_2242)
                .build();

        receiver = personBuilder.createReceiverPartyAsJuridicPerson()
                .withName(new JuridicPersonName("commercialName", "registrationName"))
                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                .withPhysicalLocation(address)
                .build();

        item = InvoiceItem.calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setUnitaryValue(1000)
                .setQuantity(10)
                .setUnits("BX")
                .setDescription("sample description")
                .getCalculatedResult();
    }

    @Test(expected = ElisCoreException.class)
    public void invoiceExportationCheckExchangeRateTest() throws ElisCoreException {
        var invoicingRanger = software.createInvoicingRangeAs()
                .withTechnicalKey("technicalKey")
                .withPrefix("PRFX")
                .withAuthorizationNumber(BigDecimal.ONE)
                .withConsecutiveRange(1L, 1000L)
                .withInvoicingPeriod(new InvoicingRangePeriod(LocalDate.now(), LocalDate.now()))
                .build();

        Invoice invoice = software.calculateInvoiceAs()
                .withinMandatorySection()
                .setConsecutive(1L)
                .setSupplierParty(supplier)
                .setReceiverParty(receiver)
                .setCurrency("COP")
                .setInvoicingRange(invoicingRanger)
                .setDate(new DocumentDate(LocalDateTime.now()))
                .addItem(item)
                .withinOptionalSection()
                .setInvoiceType(InvoiceType.EXPORTATION)
                .getCalculatedResult();

        assertThat(invoice.getDocumentNumber().getPrefix(), is("PRFX"));
        assertThat(invoice.getDocumentNumber().getConsecutive(), is(1L));
        assertThat(invoice.getLegalMonetaryTotal().getCurrency(), is("COP"));

        assertThat(invoice.getHeader().getSupplierParty(), is(supplier));
        assertThat(invoice.getHeader().getReceiverParty(), is(receiver));

        assertThat(invoice.getItemList().isEmpty(), is(false));
        assertThat(invoice.getItemList().size(), is(1));
        assertThat(invoice.getLegalMonetaryTotal().getLineTotal(), is(BigDecimal.valueOf(10000).setScale(2)));

        assertThat(invoice.getInvoiceType(), is(InvoiceType.EXPORTATION));
        assertThat(invoice.getType(), is(DocumentType.INVOICE));
        assertFalse(invoice.isTranscription());
        assertTrue(invoice.isExportation());
    }

    @Test
    public void invoiceWithExportationTest() throws ElisCoreException {

        var invoicingRanger = software.createInvoicingRangeAs()
                .withTechnicalKey("technicalKey")
                .withPrefix("PRFX")
                .withAuthorizationNumber(BigDecimal.ONE)
                .withConsecutiveRange(1L, 1000L)
                .withInvoicingPeriod(new InvoicingRangePeriod(LocalDate.now(), LocalDate.now()))
                .build();

        var invoice = software.calculateInvoiceAs()
                .withinMandatorySection()
                .setConsecutive(1L)
                .setSupplierParty(supplier)
                .setReceiverParty(receiver)
                .setCurrency("COP")
                .setInvoicingRange(invoicingRanger)
                .setDate(new DocumentDate(LocalDateTime.now()))
                .addItem(item)
                .withinOptionalSection()
                .setExchangeRate(new ExchangeRate("USD", "COP", 3000))
                .setInvoiceType(InvoiceType.EXPORTATION)
                .getCalculatedResult();

        assertThat(invoice.getDocumentNumber().getPrefix(), is("PRFX"));
        assertThat(invoice.getDocumentNumber().getConsecutive(), is(1L));
        assertThat(invoice.getLegalMonetaryTotal().getCurrency(), is("COP"));

        assertThat(invoice.getHeader().getSupplierParty(), is(supplier));
        assertThat(invoice.getHeader().getReceiverParty(), is(receiver));

        assertThat(invoice.getItemList().isEmpty(), is(false));
        assertThat(invoice.getItemList().size(), is(1));
        assertThat(invoice.getLegalMonetaryTotal().getLineTotal(), is(BigDecimal.valueOf(10000).setScale(2)));
    }

    @Test(expected = ElisCoreException.class)
    public void invoiceWithAllowanceExceptionTest() throws ElisCoreException {

        var invoicingRanger = software.createInvoicingRangeAs()
                .withTechnicalKey("technicalKey")
                .withPrefix("PRFX")
                .withAuthorizationNumber(BigDecimal.ONE)
                .withConsecutiveRange(1L, 1000L)
                .withInvoicingPeriod(new InvoicingRangePeriod(LocalDate.now(), LocalDate.now()))
                .build();

        var invoice = software.calculateInvoiceAs()
                .withinMandatorySection()
                .setConsecutive(1L)
                .setSupplierParty(supplier)
                .setReceiverParty(receiver)
                .setCurrency("COP")
                .setInvoicingRange(invoicingRanger)
                .addItem(item)
                .withinOptionalSection()
                .addAllowanceCharge(new AllowanceCharge(true, BigDecimal.ZERO))
                .setInvoiceType(InvoiceType.EXPORTATION)
                .getCalculatedResult();

        assertThat(invoice.getDocumentNumber().getPrefix(), is("PRFX001"));
        assertThat(invoice.getDocumentNumber().getConsecutive(), is(1L));
        assertThat(invoice.getLegalMonetaryTotal().getCurrency(), is("COP"));

        assertThat(invoice.getHeader().getSupplierParty(), is(supplier));
        assertThat(invoice.getHeader().getReceiverParty(), is(receiver));

        assertThat(invoice.getItemList().isEmpty(), is(false));
        assertThat(invoice.getItemList().size(), is(1));
        assertThat(invoice.getLegalMonetaryTotal().getLineTotal(), is(BigDecimal.valueOf(10000)));
    }

    @Test
    public void invoiceWithAllowanceTest() throws ElisCoreException {

        var invoicingRanger = software.createInvoicingRangeAs()
                .withTechnicalKey("technicalKey")
                .withPrefix("PRFX")
                .withAuthorizationNumber(BigDecimal.ONE)
                .withConsecutiveRange(1L, 1000L)
                .withInvoicingPeriod(new InvoicingRangePeriod(LocalDate.now(), LocalDate.now()))
                .build();

        var invoice = software.calculateInvoiceAs()
                .withinMandatorySection()
                .setConsecutive(1L)
                .setSupplierParty(supplier)
                .setReceiverParty(receiver)
                .setCurrency("COP")
                .setInvoicingRange(invoicingRanger)
                .setDate(new DocumentDate(LocalDateTime.now()))
                .addItem(item)
                .withinOptionalSection()
                .addAllowanceCharge(new AllowanceCharge(true, BigDecimal.ZERO, "Charge for VISA Payment"))
                .getCalculatedResult();

        assertThat(invoice.getDocumentNumber().getPrefix(), is("PRFX"));
        assertThat(invoice.getDocumentNumber().getConsecutive(), is(1L));
        assertThat(invoice.getLegalMonetaryTotal().getCurrency(), is("COP"));

        assertThat(invoice.getHeader().getSupplierParty(), is(supplier));
        assertThat(invoice.getHeader().getReceiverParty(), is(receiver));

        assertThat(invoice.getItemList().isEmpty(), is(false));
        assertThat(invoice.getItemList().size(), is(1));
        assertThat(invoice.getLegalMonetaryTotal().getLineTotal(), is(BigDecimal.valueOf(10000).setScale(2)));
    }

}
