/** ********************************************************************************************
 *
 * ELectronic Invoicing System Community Core library
 * Copyright (C) 2017-2018. Smart IT S.A.S. <smartit.net.co>
 *
 * This file is licensed under the GNU Affero General Public License version 3
 * as published by the Free Software Foundation.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * You should have received a copy of the GNU Affero General Public License. If
 * not, please visit <http://www.gnu.org/licenses/agpl-3.0.html>.
 *
 *********************************************************************************************
 */
package co.com.elis.core.person;

import co.com.elis.core.document.PhysicalLocation;
import co.com.elis.core.person.validation.CheckForJuridicObligations;
import co.com.elis.exception.ElisCoreException;
import java.util.List;

@CheckForJuridicObligations
public class SupplierParty<N extends PersonName> extends Person<N> {

    public SupplierParty(PersonType personType, N name, IdentityDocument identityDocument, PhysicalLocation physicalLocation, PhysicalLocation registrationAddress, DIANCharacterization dianCharacterization, List<Contact> contacts) {
        super(personType, name, identityDocument, physicalLocation, registrationAddress, dianCharacterization, contacts);
    }

    public static class SupplierPartyBuilder<N extends PersonName, R extends RepresentationType> extends PersonBuilder<N, R, SupplierPartyBuilder<N, R>> {

        public SupplierPartyBuilder(PersonType personType) {
            super(personType);
        }

        /**
         * Assings a name to the natural or juridic person to be built
         *
         * @param name Name entity containing both commercial and registration
         * name
         * @return Chaining builder
         * @see JuridicPersonName
         * @see NaturalPersonName
         */
        public SupplierPartyBuilder<N, R> withName(N name) {
            this.personName = name;
            return collectContext();
        }

        @Override
        protected SupplierPartyBuilder<N, R> collectContext() {
            return this;
        }

        public SupplierParty<N> build() throws ElisCoreException {
            DIANCharacterization dianCharacterization = new DIANCharacterization(regime, obligations, responsabilities, customUserCodes, establishmentTypes, representationTypes);
            SupplierParty<N> supplier = new SupplierParty<>(personType, personName, identityDocument, physicalLocation, registrationAddress, dianCharacterization, contacts);

            if (!disableValidations) {
                supplier.validateOrThrow();
            }
            return supplier;
        }

    }

}
