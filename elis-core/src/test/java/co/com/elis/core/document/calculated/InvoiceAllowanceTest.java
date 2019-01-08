package co.com.elis.core.document.calculated;

import co.com.elis.core.document.InvoiceDate;
import co.com.elis.core.document.InvoicingRangePeriod;
import co.com.elis.core.document.PhysicalLocation;
import co.com.elis.core.document.allowance.AllowanceCharge;
import co.com.elis.core.item.InvoiceItem;
import co.com.elis.core.person.AccountType;
import co.com.elis.core.person.IdentityDocument;
import co.com.elis.core.person.JuridicPersonName;
import co.com.elis.core.person.Obligation;
import co.com.elis.core.person.PersonBuilder;
import co.com.elis.core.person.ReceiverParty;
import co.com.elis.core.person.SupplierParty;
import co.com.elis.core.software.Software;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.var;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class InvoiceAllowanceTest {

    @Test
    public void oneItemWithDiscountTest() {

        try {
            Software software = new Software("IDSOFT", 909090L, "SOFT1", "PIN123");

            PersonBuilder personBuilder = software.getPersonBuilder();

            SupplierParty supplier = personBuilder.createSupplierPartyAsJuridicPerson()
                    .withName(new JuridicPersonName("commercialName", "registrationName"))
                    .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                    .withPhysicalLocation(PhysicalLocation.createAs().build())
                    .addObligation(Obligation.FACTURA_ELECTRONICA_VOLUNTARIA_MODELO_2242)
                    .build();

            ReceiverParty receiver = personBuilder.createReceiverPartyAsJuridicPerson()
                    .withName(new JuridicPersonName("commercialName", "registrationName"))
                    .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                    .withPhysicalLocation(PhysicalLocation.createAs().build())
                    .build();

            var item = InvoiceItem.calculateAs()
                    .withinMandatorySection()
                    .setPosition(1)
                    .setUnitaryValue(1000)
                    .setQuantity(10)
                    .getCalculatedResult();

            var invoicingRange = software.createInvoicingRangeAs()
                    .withTechnicalKey("technicalKey")
                    .withPrefix("PRFX")
                    .withAuthorizationNumber(BigDecimal.ONE)
                    .withConsecutiveRange(1L, 1000L)
                    .withInvoicingPeriod(new InvoicingRangePeriod(LocalDateTime.now(), LocalDateTime.now()))
                    .build();

            var invoice = software.calculateInvoiceAs()
                    .withinMandatorySection()
                    .setConsecutive(1L)
                    .setSupplierParty(supplier)
                    .setReceiverParty(receiver)
                    .setCurrency("COP")
                    .setDate(new InvoiceDate(LocalDate.now(), LocalTime.now()))
                    .setInvoicingRange(invoicingRange)
                    .addItem(item)
                    .withinOptionalSection()
                    .addAllowanceCharge(new AllowanceCharge(false, BigDecimal.valueOf(100)))
                    .getCalculatedResult();

            assertThat(invoice.getDocumentNumber().getPrefix(), is("PRFX"));
            assertThat(invoice.getDocumentNumber().getConsecutive(), is(1L));
            assertThat(invoice.getLegalMonetaryTotal().getCurrency(), is("COP"));

            assertThat(invoice.getHeader().getSupplierParty(), is(supplier));
            assertThat(invoice.getHeader().getReceiverParty(), is(receiver));

            assertThat(invoice.getItemList().isEmpty(), is(false));
            assertThat(invoice.getItemList().size(), is(1));
            assertThat(invoice.getLegalMonetaryTotal().getLineTotal(), is(BigDecimal.valueOf(10000).setScale(4)));
            assertThat(invoice.getLegalMonetaryTotal().getPayableAmount(), is(BigDecimal.valueOf(9900).setScale(4)));

        } catch (ElisCoreException ex) {
            Logger.getLogger(InvoiceAllowanceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void oneItemWithChargeTest() throws ElisCoreException {

        Software software = new Software("IDSOFT", 909090L, "SOFT1", "PIN123");

        PersonBuilder personBuilder = software.getPersonBuilder();

        SupplierParty supplier = personBuilder.createSupplierPartyAsJuridicPerson()
                .withName(new JuridicPersonName("commercialName", "registrationName"))
                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                .withPhysicalLocation(PhysicalLocation.createAs().build())
                .addObligation(Obligation.FACTURA_ELECTRONICA_VOLUNTARIA_MODELO_2242)
                .build();

        ReceiverParty receiver = personBuilder.createReceiverPartyAsJuridicPerson()
                .withName(new JuridicPersonName("commercialName", "registrationName"))
                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                .withPhysicalLocation(PhysicalLocation.createAs().build())
                .build();

        var item = InvoiceItem.calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setUnitaryValue(1000)
                .setQuantity(10)
                .withinOptionalSection()
                .setCode("ITM1")
                .getCalculatedResult();

        var invoicingRange = software.createInvoicingRangeAs()
                .withTechnicalKey("technicalKey")
                .withPrefix("PRFX")
                .withAuthorizationNumber(BigDecimal.ONE)
                .withConsecutiveRange(1L, 1000L)
                .withInvoicingPeriod(new InvoicingRangePeriod(LocalDateTime.now(), LocalDateTime.now()))
                .build();

        var invoice = software.calculateInvoiceAs()
                .withinMandatorySection()
                .setConsecutive(1L)
                .setSupplierParty(supplier)
                .setReceiverParty(receiver)
                .setCurrency("COP")
                .setDate(new InvoiceDate(LocalDateTime.now()))
                .setInvoicingRange(invoicingRange)
                .addItem(item)
                .withinOptionalSection()
                .addAllowanceCharge(new AllowanceCharge(true, BigDecimal.valueOf(100)))
                .getCalculatedResult();

        assertThat(invoice.getDocumentNumber().getPrefix(), is("PRFX"));
        assertThat(invoice.getDocumentNumber().getConsecutive(), is(1L));
        assertThat(invoice.getLegalMonetaryTotal().getCurrency(), is("COP"));

        assertThat(invoice.getHeader().getSupplierParty(), is(supplier));
        assertThat(invoice.getHeader().getReceiverParty(), is(receiver));

        assertThat(invoice.getItemList().isEmpty(), is(false));
        assertThat(invoice.getItemList().size(), is(1));
        assertThat(invoice.getLegalMonetaryTotal().getLineTotal(), is(BigDecimal.valueOf(10000).setScale(4)));
        assertThat(invoice.getLegalMonetaryTotal().getPayableAmount(), is(BigDecimal.valueOf(10100).setScale(4)));
    }

}
