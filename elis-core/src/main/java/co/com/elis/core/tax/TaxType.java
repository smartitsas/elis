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

package co.com.elis.core.tax;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

public class TaxType {

    /**
     * 01 - IVA TAX
     */
    public static final TaxType IVA = new TaxType("01", "IVA");

    /**
     * 02 - CONSUMPTION TAX
     */
    public static final TaxType CONSUMPTION = new TaxType("02", "CONSUMO");

    /**
     * 03 - ICA TAX
     */
    public static final TaxType ICA = new TaxType("03", "ICA");
    
    @Getter
    private final String code;

    @Getter
    private final String description;

    private TaxType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static List<TaxType> values() {
        return Arrays.asList(IVA, CONSUMPTION, ICA);
    }

    public static TaxType fromCode(String code) {

        for (TaxType value : TaxType.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }

        return new TaxType(code, "OTHER");
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.code);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TaxType other = (TaxType) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return Objects.equals(this.description, other.description);
    }

    public String toString() {
        return code + "-" + description;
    }

}
