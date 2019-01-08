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

import javax.validation.constraints.NotNull;
import lombok.Getter;

public class NaturalPersonName extends PersonName {

    @Getter
    @NotNull(message = "ELIS_CORE_UNKNOWN")
    private final String firstName;

    @Getter
    private final String middleName;

    @Getter
    @NotNull(message = "ELIS_CORE_UNKNOWN")
    private final String lastName;

    /**
     * Represent a name for a natural person consisting of firstName, middleName
     * and lastName
     *
     * @param firstName First name to be assigned
     * @param middleName Middle name (if applies)
     * @param lastName last or surname
     */
    public NaturalPersonName(String firstName, String middleName, String lastName) {
        super();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    /**
     * Represent a name for a natural person consisting of firstName and
     * lastName
     *
     * @param firstName First name to be assigned
     * @param lastName last or surname
     */
    public NaturalPersonName(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = null;
    }

}
