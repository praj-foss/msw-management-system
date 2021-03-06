/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data

import javafx.collections.ObservableList
import praj.mswms.data.access.DAO
import praj.mswms.data.model.Model

/**
 * Generic repository with methods to get, add, update or delete objects.
 */
abstract class Repository<T : Model> {
    protected abstract val elementList: ObservableList<T>
    protected abstract val dao: DAO<T>

    fun load() = elementList.addAll(dao.getAll())

    fun get(id: Int): T? = elementList.stream().filter { it.id == id }.findFirst().orElse(null)

    fun add(element: T) {
        if (get(element.id) == null)
            elementList.add(element)
    }

    fun update(id: Int, element: T) {
        if (get(id)?.equals(element) == false) {
            elementList.remove(get(id))
            elementList.add(element)
        }
    }

    fun delete(id: Int) {
        if (get(id) != null)
            elementList.removeIf { it.id == id }
    }

    // Direct action by DAO
    fun addByDAO(element: T) = dao.insert(element)

    fun updateByDAO(id: Int, element: T) = dao.update(id, element)

    fun deleteByDAO(id: Int) = dao.delete(id)
}