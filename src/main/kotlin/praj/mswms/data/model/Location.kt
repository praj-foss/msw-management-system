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

    companion object {
        val UNAVAILABLE = Location(-1, "Unavailable", "Unavailable")
    }

    override fun toString(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Location)
            id == other.id && name == other.name && type == other.type
        else
            false
    }
}