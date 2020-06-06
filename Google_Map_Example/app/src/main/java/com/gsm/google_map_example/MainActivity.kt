package com.gsm.google_map_example

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_bar.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION)

    val REQUEST_PERMISSION_CODE = 1

    val DEFAULT_ZOOM_LEVEL = 17f

    val CITY_HALL = LatLng(37.5662952, 126.97794509999994)

    var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView.onCreate(savedInstanceState)

        if (checkPermissions()) {
            initMap()
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_CODE)
        }

        myLocationButton.setOnClickListener { onMyLocationButtonClick() }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        initMap()
    }

    private fun checkPermissions(): Boolean {

        for (permission in PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    var clusterManager: ClusterManager<MyItem>? = null
    var clusterRenderer: ClusterRenderer? = null

    @SuppressLint("MissingPermission")
    fun initMap() {
        mapView.getMapAsync {

            clusterManager = ClusterManager(this, it)
            clusterRenderer = ClusterRenderer(this, it, clusterManager)

            it.setOnCameraIdleListener(clusterManager)
            it.setOnMarkerClickListener(clusterManager)

            googleMap = it
            it.uiSettings.isMyLocationButtonEnabled = false

            when {
                checkPermissions() -> {
                    it.isMyLocationEnabled = true
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
                }
                else -> {
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY_HALL, DEFAULT_ZOOM_LEVEL))
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation(): LatLng {

        val locationProvider: String = LocationManager.GPS_PROVIDER

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val lastKnownLocation: Location? = locationManager.getLastKnownLocation(locationProvider)

        return LatLng(lastKnownLocation!!.latitude, lastKnownLocation.longitude)
    }

    private fun onMyLocationButtonClick() {
        when {
            checkPermissions() -> googleMap?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL)
            )
            else -> Toast.makeText(applicationContext, "위치사용권한 설정에 동의해주세요", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    val API_KEY = "6d574e476269746734366a545a4447"

    var task: ToiletReadTask? = null

    var toilets = JSONArray()

    val itemMap = mutableMapOf<JSONObject, MyItem>()

    // 화장실 이미지로 사용할 Bitmap
    private val bitmap by lazy {
        val bitmap = ResourcesCompat.getDrawable(resources, R.drawable.ic_person_black_24dp, null)?.toBitmap()
        Bitmap.createScaledBitmap(bitmap!!, 64, 64, false)
    }

    fun JSONArray.merge(anotherArray: JSONArray) {
        for (i in 0 until anotherArray.length()) {
            this.put(anotherArray.get(i))
        }
    }

    fun readData(startIndex: Int, lastIndex: Int): JSONObject {
        val url =
            URL("http://openAPI.seoul.go.kr:8088" + "/${API_KEY}/json/SearchPublicToiletPOIService/${startIndex}/${lastIndex}")
        val connection = url.openConnection()
        val data = connection.getInputStream().readBytes().toString(charset("UTF-8"))
        return JSONObject(data)
    }

    inner class ToiletReadTask : AsyncTask<Void, JSONArray, String>() {

        override fun onPreExecute() {

            googleMap?.clear()
            toilets = JSONArray()
            itemMap.clear()
        }

        override fun doInBackground(vararg params: Void?): String {

            val step = 1000
            var startIndex = 1
            var lastIndex = step
            var totalCount = 0

            do {
                if (isCancelled) break

                if (totalCount != 0) {
                    startIndex += step
                    lastIndex += step
                }

                val jsonObject = readData(startIndex, lastIndex)

                totalCount = jsonObject.getJSONObject("SearchPublicToiletPOIService").getInt("list_total_count")

                val rows = jsonObject.getJSONObject("SearchPublicToiletPOIService").getJSONArray("row")

                toilets.merge(rows)

                publishProgress(rows)

            } while (lastIndex < totalCount)

            return "complete"
        }

        override fun onProgressUpdate(vararg values: JSONArray?) {

            val array = values[0]
            array?.let {
                for (i in 0 until array.length()) {
                    addMarkers(array.getJSONObject(i))
                }
            }

            clusterManager?.cluster()
        }

        override fun onPostExecute(result: String?) {

            val textList = mutableListOf<String>()

            for(i in 0 until toilets.length()) {
                val toilet = toilets.getJSONObject(i)
                textList.add(toilet.getString("FNAME"))
            }

            val adapter = ArrayAdapter<String> (
                this@MainActivity,
                android.R.layout.simple_dropdown_item_1line,
                textList
            )

            search_bar.autoCompleteTextView.threshold = 1
            search_bar.autoCompleteTextView.setAdapter(adapter)
        }
    }

    private fun JSONArray.findByChildProperty(propertyName: String, value: String): JSONObject? {

        for(i in 0 until length()) {
            val obj = getJSONObject(i)
            if(value == obj.getString(propertyName)) return obj
        }

        return null
    }

    override fun onStart() {
        super.onStart()
        task?.cancel(true)
        task = ToiletReadTask()
        task?.execute()

        search_bar.searchButton.setOnClickListener {

            val keyword = search_bar.autoCompleteTextView.text.toString()

            if(TextUtils.isEmpty(keyword)) return@setOnClickListener


            toilets.findByChildProperty("FNAME", keyword)?.let {

                val myItem = itemMap[it]

                val marker = clusterRenderer?.getMarker(myItem)

                marker?.showInfoWindow()

                googleMap?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(it.getDouble("Y_WGS84"), it.getDouble("X_WGS84")), DEFAULT_ZOOM_LEVEL)
                )
                clusterManager?.cluster()
            }

            search_bar.autoCompleteTextView.setText("")
        }
    }

    override fun onStop() {
        super.onStop()
        task?.cancel(true)
        task = null
    }

    fun addMarkers(toilet: JSONObject) {

        val item = MyItem(
            LatLng(toilet.getDouble("Y_WGS84"), toilet.getDouble("X_WGS84")),
            toilet.getString("FNAME"),
            toilet.getString("ANAME"),
            BitmapDescriptorFactory.fromBitmap(bitmap)
        )

        clusterManager?.addItem(
            MyItem(
                LatLng(toilet.getDouble("Y_WGS84"), toilet.getDouble("X_WGS84")),
                toilet.getString("FNAME"),
                toilet.getString("ANAME"),
                BitmapDescriptorFactory.fromBitmap(bitmap)
            )
        )

        itemMap[toilet] = item
    }
}
