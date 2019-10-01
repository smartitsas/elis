package co.com.elis.core.document.calculated;

import co.com.elis.core.document.PhysicalLocation;
import co.com.elis.core.document.DocumentDate;
import co.com.elis.core.document.InvoicingRangePeriod;
import co.com.elis.core.document.address.CountrySubdivision;
import co.com.elis.core.document.reference.Reference;
import co.com.elis.core.document.reference.ReferenceType;
import co.com.elis.core.item.InvoiceItem;
import co.com.elis.core.person.AccountType;
import co.com.elis.core.person.IdentityDocument;
import co.com.elis.core.person.Obligation;
import co.com.elis.core.person.ReceiverParty;
import co.com.elis.core.person.SupplierParty;
import co.com.elis.core.software.Environment;
import co.com.elis.core.software.Software;
import co.com.elis.core.util.CountrySubdivisionFactory;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import lombok.var;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class BillingReferenceTest {

    @Test
    public void multipleReferenceTest() throws ElisCoreException {

        var ref0 = new Reference("ORD001", ReferenceType.ORDER_REFERENCE);
        var ref1 = new Reference("F001-001", ReferenceType.BILLING_REFERENCE);
        var ref2 = new Reference("CONTR11111", ReferenceType.CONTRACT_REFERENCE);
        var ref3 = new Reference("GUIA001-0001", ReferenceType.DESPATCH_REFERENCE);
        var ref4 = new Reference("ORIG789", ReferenceType.ORIGINATOR_REFERENCE);
        var ref5 = new Reference("R001", ReferenceType.RECEIPT_REFERENCE);
        var ref6 = new Reference("OTH_EPS001", ReferenceType.ADDITIONAL_REFERENCE);

        Software software = new Software("IDSOFT", 909090L, "SOFT1", "PIN123", Environment.HABILITATION);
        
        CountrySubdivision subdivision = CountrySubdivisionFactory.getInstance().findById(11001);
        PhysicalLocation address = PhysicalLocation.createAs()
                .withCountrySubdivision(subdivision)
                .build();          

        SupplierParty supplier = software.getPersonBuilder()
                .createSupplierPartyAsJuridicPerson()
                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                .withPhysicalLocation(address)
                .withRegistrationAddress(address)
                .addObligation(Obligation.OTRO_TIPO_OBLIGADO)
                .build();

        ReceiverParty receiver = software.getPersonBuilder()
                .createReceiverPartyAsJuridicPerson()
                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                .withPhysicalLocation(address)
                .withRegistrationAddress(address)
                .build();

        var item = InvoiceItem.calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setUnitaryValue(1000)
                .setQuantity(10)
                .setUnits("BX")
                .getCalculatedResult();

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
                .setDate(new DocumentDate(LocalDate.now(), LocalTime.now()))
                .setInvoicingRange(invoicingRanger)
                .addItem(item)
                .withinOptionalSection()
                .addReference(ref0)
                .addReference(ref1)
                .addReference(ref2)
                .addReference(ref3)
                .addReference(ref4)
                .addReferences(ref5, ref6)
                .addReferences(new Reference("OTH1", ReferenceType.ADDITIONAL_REFERENCE), new Reference("OTH1", ReferenceType.ADDITIONAL_REFERENCE))
                .addReferences(Arrays.asList(new Reference("OTH1", ReferenceType.ADDITIONAL_REFERENCE), new Reference("OTH1", ReferenceType.ADDITIONAL_REFERENCE)))
                .getCalculatedResult();

        assertThat(invoice.getDocumentNumber().getPrefix(), is("PRFX"));
        assertThat(invoice.getDocumentNumber().getConsecutive(), is(1L));
        assertThat(invoice.getLegalMonetaryTotal().getCurrency(), is("COP"));

        assertThat(invoice.getHeader().getSupplierParty(), is(supplier));

        assertThat(invoice.getItemList().isEmpty(), is(false));
        assertThat(invoice.getItemList().size(), is(1));
        assertThat(invoice.getLegalMonetaryTotal().getLineTotal(), is(BigDecimal.valueOf(10000).setScale(4)));

        assertThat(invoice.getOtherData().getReferences().getOfType(ReferenceType.ORDER_REFERENCE).get(0).getId(), is("ORD001"));
        assertThat(invoice.getOtherData().getReferences().getOfType(ReferenceType.ORDER_REFERENCE).get(0), is(ref0));
        assertThat(invoice.getOtherData().getReferences().getOfType(ReferenceType.BILLING_REFERENCE).get(0), is(ref1));
        assertThat(invoice.getOtherData().getReferences().getOfType(ReferenceType.CONTRACT_REFERENCE).get(0), is(ref2));
        assertThat(invoice.getOtherData().getReferences().getOfType(ReferenceType.DESPATCH_REFERENCE).get(0), is(ref3));
        assertThat(invoice.getOtherData().getReferences().getOfType(ReferenceType.ORIGINATOR_REFERENCE).get(0), is(ref4));
        assertThat(invoice.getOtherData().getReferences().getOfType(ReferenceType.RECEIPT_REFERENCE).get(0), is(ref5));
        assertThat(invoice.getOtherData().getReferences().getOfType(ReferenceType.ADDITIONAL_REFERENCE).get(0), is(ref6));

    }

}
