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
import java.util.Optional;

public class ReceiverParty<N extends PersonName> extends Person<N> {

    public ReceiverParty(PersonType personType, N name, IdentityDocument identityDocument, PhysicalLocation physicalLocation, DIANCharacterization dianCharacterization, Optional<Contact> contact) {
        super(personType, name, identityDocument, physicalLocation, physicalLocation, dianCharacterization, contact);
    }

    public static class ReceiverPartyBuilder<N extends PersonName, R extends RepresentationType> extends PersonBuilder<N, R, ReceiverPartyBuilder<N, R>> {

        public ReceiverPartyBuilder(PersonType personType) {
            super(personType);
        }

        /**
         * Assigns a given name to the receiver party to be built
         *
         * @param name Name to be assigned
         * @return Chaining builder
         */
        public ReceiverPartyBuilder<N, R> withName(N name) {
            this.personName = name;
            return collectContext();
        }

        @Override
        protected ReceiverPartyBuilder<N, R> collectContext() {
            return this;
        }

        public ReceiverParty<N> build() {
            DIANCharacterization dianCharacterization = new DIANCharacterization(regime, obligations, responsabilities, customUserCodes, establishmentTypes, representationTypes);
            return new ReceiverParty<>(personType, personName, identityDocument, physicalLocation, dianCharacterization, contact);
        }

    }

}
