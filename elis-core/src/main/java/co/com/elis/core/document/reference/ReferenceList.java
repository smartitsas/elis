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
package co.com.elis.core.document.reference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReferenceList {

    private final List<Reference> internalCollection;

    public ReferenceList() {
        internalCollection = new ArrayList<>();
    }

    public boolean add(Reference reference) {
        return internalCollection.add(reference);
    }

    public List<Reference> getOfType(ReferenceType type) {
        return internalCollection.stream()
                .filter(r -> r.getType() == type)
                .collect(Collectors.toList());
    }

    public boolean addAll(Collection<? extends Reference> references) {
        return this.internalCollection.addAll(references);
    }

    public Stream<Reference> stream() {
        return this.internalCollection.stream();
    }

    public Stream<Reference> parallelStream() {
        return this.internalCollection.parallelStream();
    }
    
    public List<Reference> getAllReferences(){
        return Collections.unmodifiableList(internalCollection);
    }

}
