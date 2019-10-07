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

package co.com.elis.core.document.delivery;

import co.com.elis.core.person.IdentityDocument;
import co.com.elis.core.person.JuridicPersonName;
import co.com.elis.core.person.Person;
import co.com.elis.core.person.PersonType;

public class DeliveryParty extends Person<JuridicPersonName> {

    private DeliveryParty(JuridicPersonName name, IdentityDocument document) {
        super(PersonType.JURIDIC, name, document, null, null, null, null);
    }

    public static class DeliveryPartyBuilder {

        private String registrationName;

        private String commercialName;

        private IdentityDocument identitydocument;

        public DeliveryPartyBuilder withName(String registrationName, String commercialName) {
            this.registrationName = registrationName;
            this.commercialName = commercialName;
            return this;
        }

        public DeliveryPartyBuilder withIdentityDocument(IdentityDocument identitydocument) {
            this.identitydocument = identitydocument;
            return this;
        }

        public DeliveryParty build() {
            return new DeliveryParty(new JuridicPersonName(registrationName, commercialName), identitydocument);
        }

    }

}
