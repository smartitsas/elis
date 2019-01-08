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
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;

public class Delivery {

    @Getter
    private final PhysicalLocation consignatorLocation;

    @Getter
    private final DeliveryParty deliveryParty;

    @Getter
    private final LocalDate deliveryDate;

    @Getter
    private final LocalTime deliveryTime;

    @Getter
    private final String trackingId;

    @Getter
    private final DeliveryTerms deliveryTerms;

    @Getter
    private final Despatch despatch;

    private Delivery(PhysicalLocation consignatorLocation, DeliveryParty deliveryParty, LocalDate deliveryDate, LocalTime deliverytime, String trackingId, DeliveryTerms deliveryTerms, Despatch despatch) {
        this.consignatorLocation = consignatorLocation;
        this.deliveryParty = deliveryParty;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliverytime;
        this.trackingId = trackingId;
        this.deliveryTerms = deliveryTerms;
        this.despatch = despatch;
    }

    public static DeliveryBuilder createAs() {
        return new DeliveryBuilder();
    }

    public static class DeliveryBuilder {

        private String specialTerms;

        private String lossRiskResponsibilityCode;

        private PhysicalLocation deliveryLocation;

        private LocalDate despatchDate;

        private LocalTime despatchTime;

        private PhysicalLocation shipmentSource;

        private PhysicalLocation consignatorLocation;

        private DeliveryParty deliveryParty;

        private LocalDate deliveryDate;

        private LocalTime deliveryTime;

        private String trackingId;

        public DeliveryBuilder withDeliveryParty(DeliveryParty deliveryParty) {
            this.deliveryParty = deliveryParty;
            return this;
        }

        public DeliveryBuilder withDeliveryDate(LocalDate deliveryDate) {
            this.deliveryDate = deliveryDate;
            return this;
        }

        public DeliveryBuilder withDeliveryTime(LocalTime deliverytime) {
            this.deliveryTime = deliverytime;
            return this;
        }

        public DeliveryBuilder withTrackingId(String trackingId) {
            this.trackingId = trackingId;
            return this;
        }

        public DeliveryBuilder withSpecialTerms(String specialTerms) {
            this.specialTerms = specialTerms;
            return this;
        }

        public DeliveryBuilder withLossRiskResponsabilityCode(String lossRiskResponsibilityCode) {
            this.lossRiskResponsibilityCode = lossRiskResponsibilityCode;
            return this;
        }

        public DeliveryBuilder withDeliveryLocation(PhysicalLocation deliveryLocation) {
            this.deliveryLocation = deliveryLocation;
            return this;
        }

        public DeliveryBuilder withDespatchDate(LocalDate despatchDate) {
            this.despatchDate = despatchDate;
            return this;
        }

        public DeliveryBuilder withDespatchTime(LocalTime despatchTime) {
            this.despatchTime = despatchTime;
            return this;
        }

        public DeliveryBuilder withShipmentSource(PhysicalLocation shipmentSource) {
            this.shipmentSource = shipmentSource;
            return this;
        }

        public DeliveryBuilder withConsignatorLocation(PhysicalLocation consignatorLocation) {
            this.consignatorLocation = consignatorLocation;
            return this;
        }

        public Delivery build() {
            DeliveryTerms deliveryTerms = new DeliveryTerms(specialTerms, lossRiskResponsibilityCode, deliveryLocation);
            Despatch despatch = new Despatch(despatchDate, despatchTime, shipmentSource);
            return new Delivery(consignatorLocation, deliveryParty, deliveryDate, deliveryTime, trackingId, deliveryTerms, despatch);
        }

    }

}
