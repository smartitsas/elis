package co.com.elis.core.person;

import co.com.elis.core.document.PhysicalLocation;
import co.com.elis.core.software.Software;
import co.com.elis.exception.ElisCoreException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;

public class PersonTest {

    @Ignore
    @Test(expected = ElisCoreException.class)
    public void buildSupplierPartyJuridicWithoutObligationTest() throws ElisCoreException {

        Software software = new Software("IDSOFT", 909090L, "SOFT1", "PIN123");

        software.getPersonBuilder()
                .createSupplierPartyAsJuridicPerson()
                .withName(new JuridicPersonName("commercialName", "registrationName"))
                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                .withPhysicalLocation(PhysicalLocation.createAs().build())
                .build();
    }

    @Test
    public void buildSupplierPartyJuridicTest() throws ElisCoreException {

        Software software = new Software("IDSOFT", 909090L, "SOFT1", "PIN123");

        SupplierParty<JuridicPersonName> supplierParty = software.getPersonBuilder()
                .createSupplierPartyAsJuridicPerson()
                .withName(new JuridicPersonName("commercialName", "registrationName"))
                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                .withPhysicalLocation(PhysicalLocation.createAs().build())
                .withRegistrationAddress(PhysicalLocation.createAs().build())
                .addObligation(Obligation.FACTURA_ELECTRONICA_VOLUNTARIA_MODELO_2242)
                .addEstablishmentType(EstablishmentType.ESTABLECIMIENTO_COMERCIO)
                .addContact(Contact.forId("IDContact").withName("Sales representative").withEmail("sales@company.net").build())
                .build();

        assertThat(supplierParty.getName().getComercialName(), is("commercialName"));
        assertThat(supplierParty.getName().getRegistrationName(), is("registrationName"));
        assertThat(supplierParty.getIdentityDocument().getAccount(), is("987654321"));
        assertThat(supplierParty.getIdentityDocument().getType(), is(AccountType.NIT));
        assertThat(supplierParty.getDianCharacterization().getObligations().size(), is(1));
        assertThat(supplierParty.getDianCharacterization().getObligations().get(0), is(Obligation.FACTURA_ELECTRONICA_VOLUNTARIA_MODELO_2242));
        assertThat(supplierParty.getDianCharacterization().getEstablishmentTypes().get(0), is(EstablishmentType.ESTABLECIMIENTO_COMERCIO));
        assertThat(supplierParty.getContacts().size(), is(1));
        assertThat(supplierParty.getContacts().get(0).getId(), is("IDContact"));
        assertThat(supplierParty.getContacts().get(0).getName(), is("Sales representative"));
        assertThat(supplierParty.getContacts().get(0).getEmail(), is("sales@company.net"));
        assertThat(supplierParty.isJuridicPerson(), is(true));
        assertThat(supplierParty.isNaturalPerson(), is(false));
    }

    @Test
    public void buildReceiverPartyJuridicTest() throws ElisCoreException {
        Software software = new Software("IDSOFT", 909090L, "SOFT1", "PIN123");

        ReceiverParty<JuridicPersonName> receiver = software.getPersonBuilder()
                .createReceiverPartyAsJuridicPerson()
                .withName(new JuridicPersonName("commercialName", "registrationName"))
                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                .withPhysicalLocation(PhysicalLocation.createAs().build())
                .withRegistrationAddress(PhysicalLocation.createAs().build())
                .addCustomUserCode(CustomUserCode.IMPORTADOR)
                .addRepresentationType(JuridicPersonRepresentationType.REPRESENTANTE_ADUANERO)
                .build();

        assertThat(receiver.getName().getComercialName(), is("commercialName"));
        assertThat(receiver.getName().getRegistrationName(), is("registrationName"));
        assertThat(receiver.getIdentityDocument().getAccount(), is("987654321"));
        assertThat(receiver.getIdentityDocument().getType(), is(AccountType.NIT));
        assertFalse(receiver.getDianCharacterization().getCustomUserCodes().isEmpty());
        assertThat(receiver.getDianCharacterization().getCustomUserCodes().get(0), is(CustomUserCode.IMPORTADOR));
        assertThat(receiver.getDianCharacterization().getRepresentationTypes().get(0), is(JuridicPersonRepresentationType.REPRESENTANTE_ADUANERO));
    }

    @Test
    public void buildSupplierPartyNaturalTest() throws ElisCoreException {

        Software software = new Software("IDSOFT", 909090L, "SOFT1", "PIN123");

        SupplierParty<NaturalPersonName> supplierParty = software.getPersonBuilder()
                .createSupplierPartyAsNaturalPerson()
                .withName(new NaturalPersonName("FirstName", "LastName"))
                .withIdentityDocument(new IdentityDocument("987654321", AccountType.NIT))
                .withPhysicalLocation(PhysicalLocation.createAs().build())
                .withRegistrationAddress(PhysicalLocation.createAs().build())
                .addObligation(Obligation.FACTURA_ELECTRONICA_VOLUNTARIA_MODELO_2242)
                .addRepresentationType(NaturalPersonRepresentationType.APODERADO_ESPECIAL)
                .addContact(Contact.forId("IDContact").withName("Sales representative").withEmail("sales@company.net").build())
                .build();

        assertThat(supplierParty.getName().getFirstName(), is("FirstName"));
        assertThat(supplierParty.getName().getLastName(), is("LastName"));
        assertThat(supplierParty.getIdentityDocument().getAccount(), is("987654321"));
        assertThat(supplierParty.getIdentityDocument().getType(), is(AccountType.NIT));
        assertNull(supplierParty.getIdentityDocument().getVerificationDigit());
        assertThat(supplierParty.getDianCharacterization().getObligations().size(), is(1));
        assertThat(supplierParty.getDianCharacterization().getObligations().get(0), is(Obligation.FACTURA_ELECTRONICA_VOLUNTARIA_MODELO_2242));
        assertThat(supplierParty.getContacts().size(), is(1));
        assertThat(supplierParty.getContacts().get(0).getId(), is("IDContact"));
        assertThat(supplierParty.getContacts().get(0).getName(), is("Sales representative"));
        assertThat(supplierParty.getContacts().get(0).getEmail(), is("sales@company.net"));
        assertThat(supplierParty.isJuridicPerson(), is(false));
        assertThat(supplierParty.isNaturalPerson(), is(true));
        assertThat(supplierParty.getDianCharacterization().getRepresentationTypes().get(0), is(NaturalPersonRepresentationType.APODERADO_ESPECIAL));
    }

    @Test
    public void buildReceiverPartyNaturalTest() throws ElisCoreException {
        Software software = new Software("IDSOFT", 909090L, "SOFT1", "PIN123");

        ReceiverParty<NaturalPersonName> receiver = software.getPersonBuilder()
                .createReceiverPartyAsNaturalPerson()
                .withName(new NaturalPersonName("firstName", "lastName"))
                .withIdentityDocument(new IdentityDocument("987654321", 4L, AccountType.NIT))
                .withPhysicalLocation(PhysicalLocation.createAs().build())
                .withRegistrationAddress(PhysicalLocation.createAs().build())
                .build();

        assertThat(receiver.getName().getFirstName(), is("firstName"));
        assertThat(receiver.getName().getLastName(), is("lastName"));
        assertThat(receiver.getIdentityDocument().getAccount(), is("987654321"));
        assertThat(receiver.getIdentityDocument().getType(), is(AccountType.NIT));
        assertThat(receiver.getIdentityDocument().getVerificationDigit(), is(4L));
        assertThat(receiver.isJuridicPerson(), is(false));
        assertThat(receiver.isNaturalPerson(), is(true));
    }

}
