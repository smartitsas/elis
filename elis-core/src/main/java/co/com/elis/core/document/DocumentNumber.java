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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;

public class DocumentNumber {

    @Getter
    @Size(max = 4, message = "ELIS_CORE_VAL_PREFIX_SIZE")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "ELIS_CORE_VAL_PREFIX")
    private final String prefix;

    @Getter
    @NotNull(message = "ELIS_CORE_UNKNOWN")
    @Min(value = 1, message = "ELIS_CORE_VAL_DOC_NUMBER_CONSECUTIVE")
    private final Long consecutive;

    /**
     * Creates a DocumentNumber with an optional prefix and a consecutive
     * @param prefix Prefix of the document
     * @param consecutive Consecutive of the document
     */
    public DocumentNumber(String prefix, Long consecutive) {
        this.consecutive = consecutive;
        this.prefix = prefix;
    }

    public String getFullId() {
        return prefix + consecutive;
    }

}
