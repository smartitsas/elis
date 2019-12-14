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

import lombok.Getter;

public class JuridicPersonName extends PersonName {

    @Getter
    private final String registrationName;

    @Getter
    private final String comercialName;

    /**
     * Creates a JuridicPersonName with the given commercial and registration
     * name
     *
     * @param commercialName Commercial name to be assigned
     * @param registrationName Registration name to be assigned
     */
    public JuridicPersonName(String commercialName, String registrationName) {
        super();
        this.registrationName = registrationName;
        this.comercialName = commercialName;
    }

    /**
     * Creates a JuridicPersonName assigning the name to both commercial and
     * registration name
     *
     * @param name Name to be assigned to registration and commercial name
     */
    public JuridicPersonName(String name) {
        super();
        this.registrationName = name;
        this.comercialName = name;
    }

    @Override
    public String toString() {
        return registrationName;
    }

}
