package co.com.elis.core.document.populated;

import co.com.elis.core.document.AffectedInvoice;
import co.com.elis.core.document.DebitNote;
import co.com.elis.core.document.DocumentNumber;
import co.com.elis.core.document.InvoiceDate;
import co.com.elis.core.document.MonetaryTotal;
import co.com.elis.core.item.DebitNoteDiscrepancyReason;
import co.com.elis.core.item.NoteItem;
import co.com.elis.core.item.TaxCalculation;
import co.com.elis.core.person.AccountType;
import co.com.elis.core.person.IdentityDocument;
import co.com.elis.core.person.JuridicPersonName;
import co.com.elis.core.person.NaturalPersonName;
import co.com.elis.core.person.Obligation;
import co.com.elis.core.document.PhysicalLocation;
import co.com.elis.core.person.ReceiverParty;
import co.com.elis.core.person.SupplierParty;
import co.com.elis.core.software.Software;
import co.com.elis.core.tax.Tax;
import co.com.elis.core.tax.TaxTotalList;
import co.com.elis.core.tax.TaxType;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class PopulatedDebitNoteTest {

    private Software software;

    private SupplierParty<JuridicPersonName> supplier;

    private ReceiverParty<NaturalPersonName> receptor;

    @Before
    public void startup() throws ElisCoreException {
        software = new Software("8bad2864-011e-4fa1-8bfe-843ab63a4bf2", 700085380L, "SOFTWARE NAME", "SOFTPIN");

        supplier = software.getPersonBuilder().createSupplierPartyAsJuridicPerson()
                .withIdentityDocument(new IdentityDocument("9000", AccountType.NIT))
                .withName(new JuridicPersonName("Cafe el cafesoso"))
                .withPhysicalLocation(PhysicalLocation.createAs().build())
                .addObligation(Obligation.OBTENCION_NIT)
                .build();

        receptor = software.getPersonBuilder().createReceiverPartyAsNaturalPerson()
                .withName(new NaturalPersonName("Juan", "Exampleton"))
                .withIdentityDocument(new IdentityDocument("30000001", AccountType.IDENTITY_CARD))
                .withPhysicalLocation(PhysicalLocation.createAs().build())
                .build();
    }

    @Test
    public void createFromMandatoryTest() throws ElisCoreException {

        NoteItem noteItem = NoteItem.calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setQuantity(10)
                .setUnitaryValue(1)
                .addAffectedInvoice(new AffectedInvoice(new DocumentNumber("PRFX", 1L), LocalDate.now(), "CUFEXAMPLE"))
                .setDiscrepancy(DebitNoteDiscrepancyReason.EXPENSES_TO_CHARGE)
                .withinOptionalSection()
                .setCode("")
                .addTax(TaxCalculation.of(TaxType.IVA).withPercentage(BigDecimal.TEN))
                .addTax(TaxCalculation.of(TaxType.CONSUMPTION).withPercentage(BigDecimal.ZERO))
                .get();

        InvoiceDate invoiceDate = new InvoiceDate(LocalDateTime.now());
        AffectedInvoice affectedInvoice = new AffectedInvoice(new DocumentNumber("PFX", 1L), LocalDate.now(), "CUFEEXAMPLE");
        MonetaryTotal monetaryTotal = new MonetaryTotal("COP", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        DebitNote debitNote = software.populateDebitNoteAs()
                .withinMandatorySection()
                .setDocumentNumber(new DocumentNumber("81", 1L))
                .setDate(invoiceDate)
                .setSupplierParty(supplier)
                .setReceiverParty(receptor)
                .addItem(noteItem)
                .setTaxTotals(TaxTotalList.buildTotalList(Tax.IVA_ZERO, Tax.ICA_ZERO, Tax.CONSUMPTION_ZERO))
                .setMonetaryTotal(monetaryTotal)
                .withinOptionalSection()
                .addAffectedInvoice(affectedInvoice)
                .getResult();

        assertThat(debitNote.getHeader().getSupplierParty(), is(supplier));
        assertThat(debitNote.getHeader().getReceiverParty(), is(receptor));
        assertThat(debitNote.getHeader().getDocumentNumber().getPrefix(), is("81"));
        assertThat(debitNote.getHeader().getDocumentNumber().getConsecutive(), is(1L));
        assertThat(debitNote.getHeader().getInvoiceDate(), is(invoiceDate));
        assertThat(debitNote.getDiscrepancy().getAffectedInvoices().get(0), is(affectedInvoice));
        assertThat(debitNote.getItemList().iterator().next(), is(noteItem));
        assertThat(debitNote.getTaxTotalList().getByType(TaxType.IVA).get().getTotal(), is(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP)));
        assertThat(debitNote.getTaxTotalList().getByType(TaxType.ICA).get().getTotal(), is(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP)));
        assertThat(debitNote.getTaxTotalList().getByType(TaxType.CONSUMPTION).get().getTotal(), is(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP)));
        assertThat(debitNote.getLegalMonetaryTotal(), is(monetaryTotal));
    }

    @Test
    public void createFromOptionalTest() throws ElisCoreException {

        NoteItem noteItem = NoteItem.calculateAs()
                .withinMandatorySection()
                .setPosition(1)
                .setQuantity(10)
                .setUnitaryValue(1)
                .addAffectedInvoice(new AffectedInvoice(new DocumentNumber("PRFX", 1L), LocalDate.now(), "CUFEXAMPLE"))
                .setDiscrepancy(DebitNoteDiscrepancyReason.INTERESTS)
                .withinOptionalSection()
                .setCode("")
                .addTax(TaxCalculation.of(TaxType.IVA).withPercentage(BigDecimal.TEN))
                .addTax(TaxCalculation.of(TaxType.CONSUMPTION).withPercentage(BigDecimal.ZERO))
                .get();

        InvoiceDate invoiceDate = new InvoiceDate(LocalDateTime.now());
        AffectedInvoice affectedInvoice = new AffectedInvoice(new DocumentNumber("PFX", 1L), LocalDate.now(), "CUFEEXAMPLE");
        MonetaryTotal monetaryTotal = new MonetaryTotal("COP", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        DebitNote debitNote = software.populateDebitNoteAs()
                .withinMandatorySection()
                .setDocumentNumber(new DocumentNumber("81", 1L))
                .setDate(invoiceDate)
                .setSupplierParty(supplier)
                .setReceiverParty(receptor)
                .addItem(noteItem)
                .setTaxTotals(TaxTotalList.buildTotalList(Tax.IVA_ZERO, Tax.ICA_ZERO, Tax.CONSUMPTION_ZERO))
                .setMonetaryTotal(monetaryTotal)
                .withinOptionalSection()
                .addAffectedInvoice(affectedInvoice)
                .addAdditionalNote("SAMPLE NOTE 1")
                .getResult();

        assertThat(debitNote.getHeader().getSupplierParty(), is(supplier));
        assertThat(debitNote.getHeader().getReceiverParty(), is(receptor));
        assertThat(debitNote.getHeader().getDocumentNumber().getPrefix(), is("81"));
        assertThat(debitNote.getHeader().getDocumentNumber().getConsecutive(), is(1L));
        assertThat(debitNote.getHeader().getInvoiceDate(), is(invoiceDate));
        assertThat(debitNote.getDiscrepancy().getAffectedInvoices().get(0), is(affectedInvoice));
        assertThat(debitNote.getItemList().iterator().next(), is(noteItem));
        assertThat(debitNote.getTaxTotalList().getByType(TaxType.IVA).get().getTotal(), is(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP)));
        assertThat(debitNote.getTaxTotalList().getByType(TaxType.ICA).get().getTotal(), is(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP)));
        assertThat(debitNote.getTaxTotalList().getByType(TaxType.CONSUMPTION).get().getTotal(), is(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP)));
        assertThat(debitNote.getLegalMonetaryTotal(), is(monetaryTotal));
        assertThat(debitNote.getOtherData().getAdditionalNotes().get(0), is("SAMPLE NOTE 1"));
    }

}
