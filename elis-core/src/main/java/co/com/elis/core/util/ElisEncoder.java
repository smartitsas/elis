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

package co.com.elis.core.util;

import co.com.elis.exception.ElisCoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ElisEncoder {

    private ElisEncoder() {
    }

    public static String applyHash(String consolidatedData) throws ElisCoreException {
        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-384");
            byte[] digest = digester.digest(consolidatedData.getBytes());

            StringBuilder result = new StringBuilder();
            for (byte b : digest) {
                result.append(String.format("%02x", b));
            }
            return result.toString();

        } catch (NoSuchAlgorithmException ex) {
            throw new ElisCoreException("Error applying SHA-1 function for CUFE");
        }
    }

}
