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

import co.com.elis.core.document.delivery.DeliveryParty;


public class PersonBuilder {
    
    
   /**
     * Creates a Supplier Builder that allows to generate a SupplierParty for a
     * Juridic person
     *
     * @return SupplierPartyBuilder
     * @see SupplierPartyBuilder
     */
    public SupplierParty.SupplierPartyBuilder<JuridicPersonName, JuridicPersonRepresentationType> createSupplierPartyAsJuridicPerson() {
        return new SupplierParty.SupplierPartyBuilder<>(PersonType.JURIDIC);
    }

    /**
     * Creates a Supplier Builder that allows to generate a SupplierParty for a
     * natural person
     *
     * @return SupplierPartyBuilder
     * @see SupplierPartyBuilder
     */
    public SupplierParty.SupplierPartyBuilder<NaturalPersonName, NaturalPersonRepresentationType> createSupplierPartyAsNaturalPerson() {
        return new SupplierParty.SupplierPartyBuilder<>(PersonType.NATURAL);
    }

    /**
     * Creates a ReceiverParty Builder that allows to generate a ReceiverParty
     * as juridic person
     *
     * @return ReceiverPartyBuilder
     * @see ReceiverPartyBuilder
     */
    public ReceiverParty.ReceiverPartyBuilder<JuridicPersonName, JuridicPersonRepresentationType> createReceiverPartyAsJuridicPerson() {
        return new ReceiverParty.ReceiverPartyBuilder<>(PersonType.JURIDIC);
    }

    /**
     * Creates a ReceiverParty Builder that allows to generate a ReceiverParty
     * as natural person
     *
     * @return ReceiverPartyBuilder
     * @see ReceiverPartyBuilder
     */
    public ReceiverParty.ReceiverPartyBuilder<NaturalPersonName, NaturalPersonRepresentationType> createReceiverPartyAsNaturalPerson() {
        return new ReceiverParty.ReceiverPartyBuilder<>(PersonType.NATURAL);
    }    
    
    /**
     * Creates a DeliveryPartyBuilder that allows to generate a DeliveryParty
     * as juridic person
     *
     * @return ReceiverPartyBuilder
     * @see ReceiverPartyBuilder
     */
    public DeliveryParty.DeliveryPartyBuilder createDeliveryParty() {
        return new  DeliveryParty.DeliveryPartyBuilder();
    }    
    
}
