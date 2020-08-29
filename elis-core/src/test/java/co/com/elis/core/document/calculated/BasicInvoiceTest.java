package co.com.elis.core.document.calculated;

import co.com.elis.core.document.DocumentType;
import co.com.elis.core.document.Invoice;
import co.com.elis.core.document.DocumentDate;
import co.com.elis.core.document.InvoiceType;
import co.com.elis.core.document.InvoicingRangePeriod;
import co.com.elis.core.document.InvoicingRange;
import co.com.elis.core.item.InvoiceItem;
import co.com.elis.core.item.TaxCalculation;
import co.com.elis.core.person.AccountType;
import co.com.elis.core.person.IdentityDocument;
import co.com.elis.core.person.JuridicPersonName;
import co.com.elis.core.person.NaturalPersonName;
import co.com.elis.core.person.characterizations.Obligation;
import co.com.elis.core.person.PersonBuilder;
import co.com.elis.core.document.PhysicalLocation;
import co.com.elis.core.document.address.CountrySubdivision;
import co.com.elis.core.person.ReceiverParty;
import co.com.elis.core.person.SupplierParty;
import co.com.elis.core.software.Environment;
import co.com.elis.core.software.Software;
import co.com.elis.core.tax.TaxType;
import co.com.elis.core.util.CountrySubdivisionFactory;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.var;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;

public class BasicInvoiceTest {

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
                .withPrefix("81")
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
                .setCode("COD01")
                .setStandardCode("24120000")
                .setPosition(1)
                .setUnitaryValue(1000)
                .setQuantity(10)
                .setUnits("BX")
                .setDescription("Sample description")
                .withinOptionalSection()
                .addTax(TaxCalculation.of(TaxType.IVA).withPercentage(10).build())
                .getCalculatedResult();

        var invoicingRange2 = software.createInvoicingRangeAs()
                .withTechnicalKey("technicalKey")
                .withPrefix("PRFX")
                .withAuthorizationNumber(BigDecimal.ONE)
                .withConsecutiveRange(1L, 1000L)
                .withInvoicingPeriod(new InvoicingRangePeriod(LocalDate.now(), LocalDate.now()))
                .build();

        Invoice invoice = software.calculateInvoiceAs()
                .withinMandatorySection()
                .setInvoicingRange(invoicingRange2)
                .setConsecutive(1L)
                .setSupplierParty(supplier)
                .setReceiverParty(receiverParty)
                .setCurrency("COP")
                .setDate(new DocumentDate(LocalDate.ofYearDay(2018, 256), LocalTime.of(5, 0)))
                .addItem(item)
                .getCalculatedResult();

        assertThat(invoice.getInvoiceType(), is(InvoiceType.SALE));
        assertThat(invoice.getType(), is(DocumentType.INVOICE));
        assertFalse(invoice.isTranscription());
        assertFalse(invoice.isExportation());

        assertThat(invoice.getDocumentNumber().getPrefix(), is("PRFX"));
        assertThat(invoice.getDocumentNumber().getConsecutive(), is(1L));
        assertThat(invoice.getLegalMonetaryTotal().getCurrency(), is("COP"));
        assertThat(invoice.getHeader().getSupplierParty(), is(supplier));
        assertNotNull(invoice.getHeader().getDocumentDate());

        assertThat(invoice.getItemList().isEmpty(), is(false));
        assertThat(invoice.getItemList().size(), is(1));
        assertThat(invoice.getLegalMonetaryTotal().getLineTotal(), is(BigDecimal.valueOf(10000).setScale(2)));

        assertThat(invoice.getLegalMonetaryTotal().getTaxableAmount(), is(BigDecimal.valueOf(10000).setScale(2)));
//        assertThat(invoice.getLegalMonetaryTotal().getPayableAmount(), is(DecimalUtils.scaled(10000+1000)));        

    }

    @Test
    public void oneItemInvoiceFromOptionalTest() throws ElisCoreException {

        PersonBuilder personBuilder = software.getPersonBuilder();

        CountrySubdivision subdivision = CountrySubdivisionFactory.getInstance().findById(11001);
        PhysicalLocation address = PhysicalLocation.createAs()
                .withCountrySubdivision(subdivision)
                .build();

        SupplierParty supplier = personBuilder.createSupplierPartyAsJuridicPerson()
                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                .withPhysicalLocation(address)
                .withRegistrationAddress(address)
                .addObligation(Obligation.FACTURA_ELECTRONICA_VOLUNTARIA_MODELO_2242)
                .build();

        ReceiverParty receiver = personBuilder.createReceiverPartyAsJuridicPerson()
                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                .withPhysicalLocation(address)
                .withRegistrationAddress(address)
                .build();

        var currentDateTime = LocalDateTime.now();

        var item = InvoiceItem.calculateAs()
                .withinMandatorySection()
                .setCode("COD01")
                .setStandardCode("24120000")
                .setPosition(1)
                .setUnitaryValue(1000)
                .setQuantity(10)
                .setUnits("BX")
                .setDescription("Description")
                .getCalculatedResult();

        Invoice invoice = software.calculateInvoiceAs()
                .withinMandatorySection()
                .setConsecutive(10007869L)
                .setSupplierParty(supplier)
                .setReceiverParty(receiver)
                .setCurrency("COP")
                .setDate(new DocumentDate(currentDateTime.toLocalDate(), currentDateTime.toLocalTime()))
                .setInvoicingRange(invoicingRange)
                .addItem(item)
                .withinOptionalSection()
                .addAdditionalNote("Test Note 1")
                .getCalculatedResult();

        assertThat(invoice.getDocumentNumber().getPrefix(), is("81"));
        assertThat(invoice.getDocumentNumber().getConsecutive(), is(10007869L));
        assertThat(invoice.getLegalMonetaryTotal().getCurrency(), is("COP"));
        assertThat(invoice.getInvoiceType(), is(InvoiceType.SALE));

        assertThat(invoice.getHeader().getSupplierParty(), is(supplier));
        assertThat(invoice.getHeader().getReceiverParty(), is(receiver));
        assertNotNull(invoice.getHeader().getReceiverParty().getPhysicalLocation());
        assertThat(invoice.getHeader().getReceiverParty().getPhysicalLocation(), is(receiver.getPhysicalLocation()));

        assertThat(invoice.getItemList().isEmpty(), is(false));
        assertThat(invoice.getItemList().size(), is(1));
        assertThat(invoice.getLegalMonetaryTotal().getLineTotal(), is(BigDecimal.valueOf(10000).setScale(2)));

        assertNotNull(invoice.getHeader().getDocumentDate());
        assertThat(invoice.getHeader().getDocumentDate().getIssueDate(), is(currentDateTime.toLocalDate()));
        assertThat(invoice.getHeader().getDocumentDate().getIssueTime(), is(currentDateTime.toLocalTime()));

        assertNotNull(invoice.getTaxTotalList());
        assertNotNull(invoice.getInvoicingRange().getAuthorizationPeriod());
        assertNotNull(invoice.getCufe());

        assertThat(invoice.getOtherData().getAdditionalNotes().get(0), is("Test Note 1"));

    }

    //TODO: check same tax type but with different percentage and applied to different Items (to verify the taxableAmount)
    @Test
    public void twoItemInvoiceTest() throws ElisCoreException {

        PersonBuilder personBuilder = software.getPersonBuilder();

        CountrySubdivision subdivision = CountrySubdivisionFactory.getInstance().findById(11001);
        PhysicalLocation address = PhysicalLocation.createAs()
                .withCountrySubdivision(subdivision)
                .build();

        PhysicalLocation mainAddress = PhysicalLocation.createAs()
                .withCountrySubdivision(subdivision)
                .withMainAddress("Imaginary street 123")
                .build();

        SupplierParty supplierParty = personBuilder.createSupplierPartyAsJuridicPerson()
                .withIdentityDocument(new IdentityDocument("900", AccountType.NIT))
                .withName(new JuridicPersonName("EMPRESA S001", "SCS Comidas S.A.S"))
                .addObligation(Obligation.OTRO_TIPO_OBLIGADO)
                .withPhysicalLocation(mainAddress)
                .withRegistrationAddress(address)
                .build();

        ReceiverParty receiverParty = personBuilder.createReceiverPartyAsNaturalPerson()
                .withIdentityDocument(new IdentityDocument("90000", AccountType.NIT))
                .withName(new NaturalPersonName("Fulanito", "Rodriguez"))
                .withPhysicalLocation(address)
                .withRegistrationAddress(address)
                .build();

        InvoiceItem item1 = InvoiceItem.calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setCode("ITM1")
                .setStandardCode("24120000")
                .setQuantity(10)
                .setUnitaryValue(10)
                .setUnits("BX")
                .setDescription("Sample description")
                .withinOptionalSection()
                .addTax(TaxCalculation.of(TaxType.IVA).withPercentage(16).build())
                .addTax(TaxCalculation.of(TaxType.CONSUMPTION).withPercentage(4.14f).build())
                .getCalculatedResult();

        InvoiceItem item2 = InvoiceItem.calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setCode("ITEM2")
                .setStandardCode("24120000")
                .setQuantity(10)
                .setUnitaryValue(10)
                .setUnits("BX")
                .setDescription("Línea-2 8110007869 fos0001_900373076_8bad2_R000001-81-26610")
                .withinOptionalSection()
                .addTax(TaxCalculation.of(TaxType.IVA).withPercentage(16).build())
                .addTax(TaxCalculation.of(TaxType.CONSUMPTION).withPercentage(4.14f).build())
                .getCalculatedResult();

        Invoice invoice = software.calculateInvoiceAs()
                .withinMandatorySection()
                .setInvoicingRange(invoicingRange)
                .setSupplierParty(supplierParty)
                .setReceiverParty(receiverParty)
                .setConsecutive(10007869L)
                .setCurrency("COP")
                .setDate(new DocumentDate(LocalDate.parse("2015-07-21"), LocalTime.parse("00:00:00")))
                .addItem(item1)
                .addItem(item2)
                .getCalculatedResult();

        assertThat(invoice.getHeader().getSupplierParty(), is(supplierParty));
        assertThat(invoice.getHeader().getReceiverParty(), is(receiverParty));
        assertFalse(invoice.getItemList().isEmpty());

        assertThat(item1.getTax(TaxType.IVA).getTaxTotal(), is(BigDecimal.valueOf(16).setScale(4, RoundingMode.HALF_UP)));
        assertThat(item1.getTax(TaxType.CONSUMPTION).getTaxTotal(), is(BigDecimal.valueOf(4.14).setScale(4, RoundingMode.HALF_UP)));

        assertThat(invoice.getLegalMonetaryTotal().getCurrency(), is("COP"));
        assertThat(invoice.getLegalMonetaryTotal().getLineTotal(), is(BigDecimal.valueOf(200).setScale(2, RoundingMode.HALF_UP)));
    }

}
