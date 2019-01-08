package co.com.elis.core.person;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class PersonNameTest {

    @Test(expected = ClassCastException.class)
    public void testCasterPersonName() {

        PersonName personName = new JuridicPersonName("name1", "Name2");
        JuridicPersonName juridicPersonName = personName.as(JuridicPersonName.class);

        assertThat(juridicPersonName, is(personName));

        personName = new JuridicPersonName("name1", "Name2");
        personName.as(NaturalPersonName.class);
    }
    
    @Test(expected = ClassCastException.class)
    public void testCasterNaturalPersonName() {

        PersonName personName = new NaturalPersonName("name1", "Name2");
        NaturalPersonName juridicPersonName = personName.as(NaturalPersonName.class);

        assertThat(juridicPersonName, is(personName));

        personName = new NaturalPersonName("name1", "Name2");
        personName.as(JuridicPersonName.class);
    }    

}
