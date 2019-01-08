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

package co.com.elis.core.document.allowance;

import java.math.BigDecimal;
import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class AllowanceTest {

    @Test
    public void discountTest() {
        AllowanceCharge allowanceCharge = new AllowanceCharge(false, BigDecimal.valueOf(100123456));

        assertNull(allowanceCharge.getReason());
        assertEquals(allowanceCharge.getAmount(), BigDecimal.valueOf(100123456));
        assertFalse(allowanceCharge.isChargeIndicator());

        BigDecimal result = allowanceCharge.applyTo(BigDecimal.valueOf(1000000000));
        assertThat(result, is(BigDecimal.valueOf(899876544)));
    }

    @Test
    public void chargeTest() {
        AllowanceCharge allowanceCharge = new AllowanceCharge(true, BigDecimal.valueOf(100123456));

        assertNull(allowanceCharge.getReason());
        assertEquals(allowanceCharge.getAmount(), BigDecimal.valueOf(100123456));
        assertTrue(allowanceCharge.isChargeIndicator());

        BigDecimal result = allowanceCharge.applyTo(BigDecimal.valueOf(1000000000));
        assertThat(result, is(BigDecimal.valueOf(1100123456)));
    }

    @Test
    public void reasonNullTest() {
        //charge
        AllowanceCharge allowanceCharge = new AllowanceCharge(true, BigDecimal.valueOf(100123456));

        assertNull(allowanceCharge.getReason());
        assertEquals(allowanceCharge.getAmount(), BigDecimal.valueOf(100123456));
        assertTrue(allowanceCharge.isChargeIndicator());

        Set<ConstraintViolation<AllowanceCharge>> violation = allowanceCharge.validateForExportation();
        assertEquals(1, violation.size());

        //discount
        allowanceCharge = new AllowanceCharge(false, BigDecimal.valueOf(100123456));

        assertNull(allowanceCharge.getReason());
        assertEquals(allowanceCharge.getAmount(), BigDecimal.valueOf(100123456));
        assertFalse(allowanceCharge.isChargeIndicator());

        violation = allowanceCharge.validateForExportation();
        assertEquals(1, violation.size());

    }

    @Test
    public void reasonNotNullTest() {
        //charge
        AllowanceCharge allowanceCharge = new AllowanceCharge(true, BigDecimal.valueOf(100123456), "CHARGE ON TRANSLATION");

        assertEquals("CHARGE ON TRANSLATION", allowanceCharge.getReason());
        assertEquals(allowanceCharge.getAmount(), BigDecimal.valueOf(100123456));
        assertTrue(allowanceCharge.isChargeIndicator());

        Set<ConstraintViolation<AllowanceCharge>> violation = allowanceCharge.validateForExportation();
        assertTrue(violation.isEmpty());

        //discount
        allowanceCharge = new AllowanceCharge(false, BigDecimal.valueOf(100123456), "DISCOUNT ON TRANSLATION");

        assertEquals("DISCOUNT ON TRANSLATION", allowanceCharge.getReason());
        assertEquals(allowanceCharge.getAmount(), BigDecimal.valueOf(100123456));
        assertFalse(allowanceCharge.isChargeIndicator());

        violation = allowanceCharge.validateForExportation();
        assertTrue(violation.isEmpty());
    }

}
