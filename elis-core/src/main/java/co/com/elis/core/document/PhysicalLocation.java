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
package co.com.elis.core.document;

import co.com.elis.core.document.address.CountrySubdivision;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class PhysicalLocation {

    @Getter
    private String postalBox;

    @Getter
    private String postalCode;

    @Getter
    @NotNull( message =  "ELIS_CORE_VAL_ADDRESS_COUNTRYSUBDIVISION")
    private CountrySubdivision countrySubdivision;

    @Getter
    @NotEmpty( message = "ELIS_CORE_VAL_ADDRESS_ADDRESS")
    private String address;

    @Getter
    private String conditions;

    private final Collection<String> additionalAddresInformation;

    protected PhysicalLocation() {
        additionalAddresInformation = new ArrayList<>();
    }

    public Collection<String> getAdditionalAddresInformation() {
        return Collections.unmodifiableCollection(additionalAddresInformation);
    }

    public static PhysicalLocationBuilder createAs() {
        return new PhysicalLocationBuilder();
    }

    public static class PhysicalLocationBuilder {

        private final PhysicalLocation physicalLocation;

        public PhysicalLocationBuilder() {
            physicalLocation = new PhysicalLocation();
        }

        public PhysicalLocationBuilder withCountrySubdivision(CountrySubdivision countrySubdivision) {
            physicalLocation.countrySubdivision = countrySubdivision;
            return this;
        }

        /**
         * Street Address of the location represented, Must not include block,
         * building name, stair, floor and so on (those has to be added as extra
         * address).
         *
         * @param address
         * @see addExtraAddressInfo
         * @return
         */
        public PhysicalLocationBuilder withMainAddress(String address) {
            physicalLocation.address = address;
            return this;
        }

        /**
         * Adds additional Address information of the location represented,
         * could be block, building name, stair, floor and so on. Can be called
         * multiple times.
         *
         * @param extraAddressInfo
         * @return Chaining builder
         */
        public PhysicalLocationBuilder addAdditionalAddressInfo(String extraAddressInfo) {
            physicalLocation.additionalAddresInformation.add(extraAddressInfo);
            return this;
        }

        /**
         * Adds additional Address information of the location represented,
         * could be block, building name, stair, floor and so on. Can be called
         * multiple times.
         *
         * @param extraAddressInfo
         * @return Chaining builder
         */
        public PhysicalLocationBuilder addAdditionalAddressInfo(Collection<String> extraAddressInfo) {
            physicalLocation.additionalAddresInformation.addAll(extraAddressInfo);
            return this;
        }

        /**
         * Postal identificator: could be PO Box, postal mail, and so on.
         *
         * @param postalBox postal box
         * @return Chaining builder
         */
        public PhysicalLocationBuilder withPostalBox(String postalBox) {
            physicalLocation.postalBox = postalBox;
            return this;
        }

        /**
         * Postal code: Postal code of the location designated by country
         * administrative entity.
         *
         * @param postalCode postal code
         * @return Chaining builder
         */
        public PhysicalLocationBuilder withPostalCode(String postalCode) {
            physicalLocation.postalCode = postalCode;
            return this;
        }

        /**
         * Other address conditions. Could be used for extra delivery
         * information
         *
         * @param conditions Other conditions
         * @return Chaining builder
         */
        public PhysicalLocationBuilder withConditions(String conditions) {
            physicalLocation.conditions = conditions;
            return this;
        }

        /**
         * Retrieves the built physical location
         *
         * @return Physical location with all data set
         */
        public PhysicalLocation build() {
            return physicalLocation;
        }

    }

}
