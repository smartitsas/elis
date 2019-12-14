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
package co.com.elis.core.person;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;

public class Contact {

    @Getter
    private final String id;

    @Getter
    private final String name;

    @Getter
    private final String telephone;

    @Getter
    private final String telefax;

    @Getter
    private final String email;

    @Getter
    private final String note;

    @Getter
    private final Collection<String> otherCommunications;

    Contact(String id, String name, String telephone, String telefax, String email, String note, Collection<String> otherCommunications) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.telefax = telefax;
        this.email = email;
        this.note = note;
        this.otherCommunications = otherCommunications;
    }

    public static ContactBuilder forId(String id) {
        return new ContactBuilder(id);
    }

    public static class ContactBuilder {

        private final String id;

        private String name;

        private String telephone;

        private String telefax;

        private String electronicMail;

        private String note;

        private final Collection<String> otherCommunications;

        private ContactBuilder(String id) {
            this.id = id;
            otherCommunications = new ArrayList<>();
        }

        public ContactBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ContactBuilder withTelephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public ContactBuilder withEmail(String electronicMail) {
            this.electronicMail = electronicMail;
            return this;
        }

        public ContactBuilder withNote(String note) {
            this.note = note;
            return this;
        }

        public ContactBuilder addOtherCommunication(String otherCommunication) {
            otherCommunications.add(otherCommunication);
            return this;
        }

        public Contact build() {
            return new Contact(id, name, telephone, telefax, electronicMail, note, otherCommunications);
        }

    }

}
