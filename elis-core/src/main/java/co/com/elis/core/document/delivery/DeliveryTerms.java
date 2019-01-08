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

package co.com.elis.core.document.delivery;

import co.com.elis.core.document.PhysicalLocation;
import lombok.Getter;

public class DeliveryTerms {

    @Getter
    private final String specialTerms;

    @Getter
    private final String lossRiskResponsibilityCode;

    @Getter
    private final PhysicalLocation deliveryLocation;

    public DeliveryTerms(String specialTerms, String lossRiskResponsibilityCode, PhysicalLocation deliveryLocation) {
        this.specialTerms = specialTerms;
        this.lossRiskResponsibilityCode = lossRiskResponsibilityCode;
        this.deliveryLocation = deliveryLocation;
    }

}
