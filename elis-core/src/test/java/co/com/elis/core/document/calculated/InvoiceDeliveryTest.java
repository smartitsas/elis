//package co.com.elis.core.document.calculated;
//
//import co.com.elis.core.document.Invoice;
//import co.com.elis.core.document.InvoicingRangePeriod;
//import co.com.elis.core.document.PhysicalLocation;
//import co.com.elis.core.document.DocumentDate;
//import co.com.elis.core.document.InvoicingRange;
//import co.com.elis.core.document.address.CountrySubdivision;
//import co.com.elis.core.document.delivery.Delivery;
//import co.com.elis.core.document.delivery.DeliveryParty;
//import co.com.elis.core.item.InvoiceItem;
//import co.com.elis.core.person.AccountType;
//import co.com.elis.core.person.IdentityDocument;
//import co.com.elis.core.person.JuridicPersonName;
//import co.com.elis.core.person.Obligation;
//import co.com.elis.core.person.PersonBuilder;
//import co.com.elis.core.person.ReceiverParty;
//import co.com.elis.core.person.SupplierParty;
//import co.com.elis.core.software.Environment;
//import co.com.elis.core.software.Software;
//import co.com.elis.core.util.CountrySubdivisionFactory;
//import co.com.elis.exception.ElisCoreException;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//import org.junit.Before;
//import org.junit.Test;
//
//public class InvoiceDeliveryTest {
//
//    private Software software;
//
//    private InvoicingRange invoicingRange;
//
//    private SupplierParty supplier;
//
//    private ReceiverParty receiver;
//
//    private InvoiceItem item;
//
//    @Before
//    public void setUp() throws ElisCoreException {
//
//        software = new Software("IDSOFT", 909090L, "SOFT1", "PIN123", Environment.HABILITATION);
//
//        invoicingRange = software.createInvoicingRangeAs()
//                .withTechnicalKey("technicalKey")
//                .withPrefix("PRFX")
//                .withAuthorizationNumber(BigDecimal.ONE)
//                .withConsecutiveRange(1L, 1000L)
//                .withInvoicingPeriod(new InvoicingRangePeriod(LocalDate.now(), LocalDate.now()))
//                .build();
//
//        CountrySubdivision subdivision = CountrySubdivisionFactory.getInstance().findById(11001);
//        PhysicalLocation address = PhysicalLocation.createAs()
//                .withCountrySubdivision(subdivision)
//                .build();
//
//        supplier = software.getPersonBuilder()
//                .createSupplierPartyAsJuridicPerson()
//                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
//                .withPhysicalLocation(address)
//                .withRegistrationAddress(address)
//                .addObligation(Obligation.FACTURA_ELECTRONICA_VOLUNTARIA_MODELO_2242)
//                .withName(new JuridicPersonName("commercialName", "registrationName"))
//                .build();
//
//        receiver = software.getPersonBuilder()
//                .createReceiverPartyAsJuridicPerson()
//                .withName(new JuridicPersonName("commercialName", "registrationName"))
//                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
//                .withPhysicalLocation(address)
//                .withRegistrationAddress(address)
//                .addObligation(Obligation.OTRO_TIPO_OBLIGADO)
//                .build();
//
//        item = InvoiceItem.calculateAs()
//                .withinMandatorySection()
//                .setPosition(1)
//                .setUnitaryValue(1000)
//                .setQuantity(10)
//                .setUnits("BX")
//                .getCalculatedResult();
//    }
//
//    @Test
//    public void invoiceWithDeliveryTest() throws ElisCoreException {
//
//        PersonBuilder personBuilder = software.getPersonBuilder();
//
//        DeliveryParty deliveryParty = personBuilder.createDeliveryParty()
//                .withIdentityDocument(new IdentityDocument("60000", AccountType.NIT))
//                .withName("commercialName", "registrationName")
//                .build();
//
//        Delivery delivery = Delivery.createAs()
//                .withDeliveryParty(deliveryParty)
//                .build();
//
//        Invoice invoice = software.calculateInvoiceAs()
//                .withinMandatorySection()
//                .setInvoicingRange(invoicingRange)
//                .setConsecutive(1L)
//                .setSupplierParty(supplier)
//                .setReceiverParty(receiver)
//                .setDate(new DocumentDate(LocalDate.now(), LocalTime.now()))
//                .setCurrency("COP")
//                .addItem(item)
//                .withinOptionalSection()
//                .setDelivery(delivery)
//                .getCalculatedResult();
//
//        assertThat(invoice.getDocumentNumber().getPrefix(), is("PRFX"));
//        assertThat(invoice.getDocumentNumber().getConsecutive(), is(1L));
//        assertThat(invoice.getLegalMonetaryTotal().getCurrency(), is("COP"));
//
//        assertThat(invoice.getHeader().getSupplierParty(), is(supplier));
//        assertThat(invoice.getHeader().getReceiverParty(), is(receiver));
//
//        assertThat(invoice.getItemList().isEmpty(), is(false));
//        assertThat(invoice.getItemList().size(), is(1));
//        assertThat(invoice.getLegalMonetaryTotal().getLineTotal(), is(BigDecimal.valueOf(10000).setScale(4)));
//        assertThat(invoice.getOtherData().getDelivery().getDeliveryParty(), is(deliveryParty));
//    }
//
//}
