/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data

import javafx.collections.ObservableList
import praj.mswms.data.access.DAO
import praj.mswms.data.model.Model

/**
 * Generic repository with methods to add, get or remove objects.
 */
abstract class Repository<T : Model> {
    protected abstract val elementList: ObservableList<T>
    protected abstract val dao: DAO<T>

    fun load() = elementList.addAll(dao.getAll())

    fun add(element: T) = elementList.add(element)

    fun get(id: Int): T? = elementList.stream().filter { it.id == id }.findFirst().orElse(null)

    fun remove(id: Int) = elementList.removeIf { it.id == id }
}