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

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class InvoicingRangePeriod {

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INVOICING_PERIOD_START")
    private final LocalDate start;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INVOICING_PERIOD_END")
    private final LocalDate end;

    /**
     * Creates a new Invoicing period between two local date and time
     *
     * @param start Start of the invoicing period
     * @param end End of the invoicing period
     */
    public InvoicingRangePeriod(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

}
