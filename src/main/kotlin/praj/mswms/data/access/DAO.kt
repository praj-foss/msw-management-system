/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.access

/**
 * Interface for DAO.
 */
interface DAO<T> {
    fun getById(id: Int): T?
    fun getAll(): List<T>
}