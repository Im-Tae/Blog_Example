package com.gsm.google_map_example

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MyItem(private val position: LatLng, private val title: String, private val snippet: String, private val icon: BitmapDescriptor) : ClusterItem {

    override fun getSnippet(): String = snippet

    override fun getTitle(): String = title

    override fun getPosition(): LatLng = position

    fun getIcon(): BitmapDescriptor = icon

    override fun equals(other: Any?): Boolean {

        if(other is MyItem)
            return (other.position.latitude == position.latitude
                    && other.position.longitude == position.longitude
                    && other.title == title
                    && other.snippet == snippet)

        return true
    }

    override fun hashCode(): Int {
        var hash = position.latitude.hashCode() * 31

        hash = hash * 31 + position.longitude.hashCode()
        hash = hash * 31 + title.hashCode()
        hash = hash * 31 + snippet.hashCode()

        return hash
    }

}