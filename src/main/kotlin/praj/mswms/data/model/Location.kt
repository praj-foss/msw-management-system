/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.model

import tornadofx.getProperty
import tornadofx.property

/**
 * Class for Location model.
 */
class Location(
        id: Int, name: String, type: String
) : Model {
    override var id: Int by property(id)
    fun idProperty() = getProperty(Location::id)

    var name: String by property(name)
    fun nameProperty() = getProperty(Location::name)

    var type: String by property(type)
    fun typeProperty() = getProperty(Location::type)

    override fun toString(): String {
        return name
    }
}