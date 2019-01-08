package co.com.elis.core.person;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class EnumTest {

    @Test
    public void obligationEnumTest() {
        for (Obligation obligation : Obligation.values()) {

            assertNotNull(obligation.getCode());
            assertNotNull(obligation.getDescription());
            assertNotNull(obligation.getTypeName());
        }
    }

    @Test
    public void CustomUserEnumTest() {
        for (CustomUserCode customUserCode : CustomUserCode.values()) {

            assertNotNull(customUserCode.getCode());
            assertNotNull(customUserCode.getDescription());
            assertNotNull(customUserCode.getTypeName());
        }
    }

    @Test
    public void EstablishmentTypeEnumTest() {
        for (EstablishmentType customUserCode : EstablishmentType.values()) {

            assertNotNull(customUserCode.getCode());
            assertNotNull(customUserCode.getDescription());
            assertNotNull(customUserCode.getTypeName());
        }
    }

    @Test
    public void JuridicPersonRepresentationTypeEnumTest() {
        for (JuridicPersonRepresentationType customUserCode : JuridicPersonRepresentationType.values()) {

            assertNotNull(customUserCode.getCode());
            assertNotNull(customUserCode.getDescription());
            assertNotNull(customUserCode.getTypeName());
        }
    }

    @Test
    public void NaturalPersonRepresentationTypeEnumTest() {
        for (NaturalPersonRepresentationType customUserCode : NaturalPersonRepresentationType.values()) {

            assertNotNull(customUserCode.getCode());
            assertNotNull(customUserCode.getDescription());
            assertNotNull(customUserCode.getTypeName());
        }
    }

}
