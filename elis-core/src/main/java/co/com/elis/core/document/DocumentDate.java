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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class DocumentDate {

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INV_ISSUE_DATE")
    private final LocalDate issueDate;
    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INV_ISSUE_TIME")
    private final LocalTime issueTime;

    public DocumentDate(LocalDate issueDate, LocalTime issueTime) {
        this.issueDate = issueDate;
        this.issueTime = issueTime;
    }

    public DocumentDate(LocalDateTime issueDateTime) {
        this.issueTime = issueDateTime.toLocalTime();
        this.issueDate = issueDateTime.toLocalDate();
    }

    public String toFormattedDateTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        StringBuilder builder = new StringBuilder(dateFormatter.format(issueDate));

        builder.append(timeFormatter.format(issueTime));
        return builder.toString();
    }

}
