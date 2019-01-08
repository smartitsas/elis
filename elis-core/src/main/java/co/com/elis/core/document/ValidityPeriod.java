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

import java.time.Duration;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class ValidityPeriod {

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INVOICING_VALIDITY_PERIOD_START")
    private final LocalDate start;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INVOICING_VALIDITY_PERIOD_END")
    private final LocalDate end;

    private ValidityPeriod(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public Duration getDuration() {
        return Duration.between(start.atStartOfDay(), end.atStartOfDay());
    }

    public static ValidityPeriod of(LocalDate start, LocalDate end) {
        return new ValidityPeriod(start, end);
    }

    public static ValidityPeriod of(LocalDate start, Duration duration) {
        return new ValidityPeriod(start, start.plus(duration));
    }

}
