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

public enum PersonType {

    /**
     * 1- Juridic Person
     */
    JURIDIC(1),
    /**
     * 2- Natural Person
     */
    NATURAL(2),
    /**
     * 3- Big Contributor
     */
    BIG_CONTRIBUTOR(3);

    private final Integer code;

    private PersonType(Integer code) {
        this.code = code;
    }

    public static PersonType fromCode(String code) {
        for (PersonType value : PersonType.values()) {
            if (value.code == Integer.parseInt(code)) {
                return value;
            }
        }

        throw new IllegalArgumentException("code: " + code + " is not part of PersonTypes");
    }

}
