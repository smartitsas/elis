/**********************************************************************************************
 *
 * ELectronic Invoicing System Community Core library
 * Copyright (C) 2017-2018. Smart IT S.A.S. <smartit.net.co>
 *
 * This file is licensed under the GNU Affero General Public License version 3 as published by
 * the Free Software Foundation.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * You should have received a copy of the GNU Affero General Public License.  If not, please
 * visit <http://www.gnu.org/licenses/agpl-3.0.html>.
 *
 **********************************************************************************************/

package co.com.elis.core.person;

import co.com.elis.core.document.PhysicalLocation;
import co.com.elis.core.util.ResourceInterpolator;
import co.com.elis.exception.ElisCoreException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public abstract class Person<N extends PersonName> {

    @Getter
    @Valid
    @NotNull(message = "ELIS_CORE_VAL_PERSON_DOC")
    private final IdentityDocument identityDocument;

    @Valid
    @Getter
    @NotNull(message = "ELIS_CORE_UNKNOWN")
    private final DIANCharacterization dianCharacterization;

    @Getter
    @Valid
    @NotNull(message = "ELIS_CORE_VAL_PERSON_LOCATION")
    private final PhysicalLocation physicalLocation;

    @Valid
    @Getter
    private final N name;

    @Getter
    @NotNull(message = "ELIS_CORE_UNKNOWN")
    private final PersonType personType;

    @Getter
    private final List<Contact> contacts;

    protected Person(PersonType personType, N name, IdentityDocument document, PhysicalLocation physicalLocation, DIANCharacterization dianCharacterization, List<Contact> contacts) {
        this.identityDocument = document;
        this.physicalLocation = physicalLocation;
        this.dianCharacterization = dianCharacterization;
        this.personType = personType;
        this.name = name;
        this.contacts = contacts;
    }

    public boolean isJuridicPerson() {
        return getPersonType() == PersonType.JURIDIC;
    }

    public boolean isNaturalPerson() {
        return getPersonType() == PersonType.NATURAL;
    }

    void validateOrThrow() throws ElisCoreException {
        Validator validator = Validation.buildDefaultValidatorFactory().usingContext().messageInterpolator(new ResourceInterpolator()).getValidator();
        Set<ConstraintViolation<Person<N>>> violations = validator.validate(this);

        if (!violations.isEmpty()) {
            ConstraintViolation<Person<N>> violation = violations.iterator().next();
            throw new ElisCoreException(violation.getPropertyPath().toString() + ": " + violation.getMessage());
        }
    }

    public abstract static class PersonBuilder<N extends PersonName, R extends RepresentationType, C extends PersonBuilder> {

        protected PhysicalLocation physicalLocation;
        protected IdentityDocument identityDocument;
        protected boolean naturalPerson;
        protected List<Obligation> obligations;
        protected List<CustomUserCode> customUserCodes;
        protected List<EstablishmentType> establishmentTypes;
        protected List<RepresentationType> representationTypes;
        protected List<Contact> contacts;
        protected N personName;
        protected PersonType personType;
        protected boolean disableValidations;

        public PersonBuilder(PersonType personType) {
            obligations = new ArrayList<>();
            customUserCodes = new ArrayList<>();
            establishmentTypes = new ArrayList<>();
            representationTypes = new ArrayList<>();
            contacts = new ArrayList<>();
            this.personType = personType;
            this.disableValidations = false;
        }

        /**
         * Assigns a IdentityDocument to the built person
         *
         * @param identityDocument IdentityDocument to be assigned
         * @return chaining builder
         */
        public C withIdentityDocument(IdentityDocument identityDocument) {
            this.identityDocument = identityDocument;
            return collectContext();
        }

        public C addObligation(Obligation obligation) {
            obligations.add(obligation);
            return collectContext();
        }

        public C addObligations(List<Obligation> obligations) {
            this.obligations.addAll(obligations);
            return collectContext();
        }
        
        public C addRepresentationTypes(List<R> representationType) {
            this.representationTypes.addAll(representationType);
            return collectContext();
        }        

        public C addCustomUserCode(CustomUserCode customUserCode) {
            customUserCodes.add(customUserCode);
            return collectContext();
        }

        public C addRepresentationType(R representationType) {
            representationTypes.add(representationType);
            return collectContext();
        }

        public C addEstablishmentType(EstablishmentType establishmentType) {
            establishmentTypes.add(establishmentType);
            return collectContext();
        }
        
        public C addEstablishmentTypes(List<EstablishmentType> establishmentTypes) {
            this.establishmentTypes.addAll(establishmentTypes);
            return collectContext();
        }        

        public C addContacts(List<Contact> contacts) {
            this.contacts.addAll(contacts);
            return collectContext();
        }

        public C addContact(Contact contact) {
            contacts.add(contact);
            return collectContext();
        }

        public C withPhysicalLocation(PhysicalLocation physicalLocation) {
            this.physicalLocation = physicalLocation;
            return collectContext();
        }

        public C disableValidations() {
            this.disableValidations = true;
            return collectContext();
        }

        protected abstract C collectContext();

    }

}