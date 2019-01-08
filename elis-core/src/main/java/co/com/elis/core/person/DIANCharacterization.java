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

import co.com.elis.core.person.validation.ConsistentSamePersonGroup;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@ConsistentSamePersonGroup
public class DIANCharacterization {

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_PERSON_OBLIGATION")
    private final List<Obligation> obligations;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_PERSON_USER_CUSTOMS")
    private final List<CustomUserCode> customUserCodes;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_PERSON_ESTABLISHMENT")
    private final List<EstablishmentType> establishmentTypes;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_PERSON_REPRESENTATION_NOT_NULL")
    private final List<RepresentationType> representationTypes;

    DIANCharacterization(List<Obligation> obligations, List<CustomUserCode> customUserCodes, List<EstablishmentType> establishmentTypes, List<RepresentationType> representationTypes) {
        this.obligations = obligations;
        this.customUserCodes = customUserCodes;
        this.establishmentTypes = establishmentTypes;
        this.representationTypes = representationTypes;
    }

}
