import android.graphics.Bitmap
import android.graphics.BitmapFactory
import co.yml.charts.common.extensions.isNotNull
import com.example.project.data.RouteData
import com.example.project.data.currentsession.Route
import com.example.project.database.DataBase
import com.example.project.ui.screens.models.DataHarvester

class RockViewModel: DataHarvester() {
    lateinit var rockName: String
    var bitmapImage: Bitmap? = null
    var routes: MutableList<RouteData> = mutableListOf<RouteData>()

    override fun getDataFromDataBase() {
        initializeRockData()

        if(bitmapImage.isNotNull()) {
            initializeRoutesData()
        }
    }

    private fun initializeRockData() {
        val connection = DataBase.getConnection()
        val query = "SELECT name, image FROM rock WHERE id = ${phrase.toInt()}"
        val stmt = connection.prepareStatement(query)
        val resultSet = stmt.executeQuery()
        resultSet.next()

        rockName = resultSet.getString("name")

        val blob = resultSet.getBlob("image")

        if(blob.isNotNull()) {
            val blobBinaryStream = blob.getBinaryStream(1, blob.length());
            bitmapImage = BitmapFactory.decodeStream(blobBinaryStream);
        }
    }

    private fun initializeRoutesData() {
        routes.clear()

        val connection = DataBase.getConnection()
        val query = "SELECT * FROM route WHERE rockId = ${phrase.toInt()}"
        val stmt = connection.prepareStatement(query)
        val resultSet = stmt.executeQuery()

        var counter = 1
        while(resultSet.next()) {
            var route = RouteData()
            route.id = resultSet.getInt("id")
            route.name = "${counter.toString()}. " + resultSet.getString("name")
            route.grade = resultSet.getString("gradeName")
            route.boltsNumber = resultSet.getInt("boltNumber")
            if(!route.boltsNumber.isNotNull()) {
                route.boltsNumber = 0
            }

            routes.add(route)
            counter++
        }
    }
}