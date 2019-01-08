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

package co.com.elis.core.document;

import co.com.elis.exception.ElisCoreException;
import java.util.Objects;
import lombok.Getter;

public enum InvoiceType {

    SALE(1),
    EXPORTATION(2),
    TRANSCRIPTION(3);


    @Getter
    private final Integer code;

    private InvoiceType(Integer code) {
        this.code = code;
    }

    public static InvoiceType fromInteger(Integer value) throws ElisCoreException{
        for (InvoiceType value1 : InvoiceType.values()) {
            if(Objects.equals(value1.code, value)){
                return value1;
            }
        }
        throw new ElisCoreException("Value "+value+" is not a valid invoice Type");
    }
    
}
