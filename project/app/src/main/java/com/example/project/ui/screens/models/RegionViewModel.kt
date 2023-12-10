import com.example.project.data.GradeData
import com.example.project.data.RockData
import com.example.project.database.DataBase
import com.example.project.ui.screens.models.DataHarvester

class RegionViewModel: DataHarvester() {
    var rocks: MutableList<RockData> = mutableListOf<RockData>()
    var grades: MutableList<GradeData> = mutableListOf<GradeData>()
    var topologies: MutableSet<String> = mutableSetOf<String>()

    override fun getDataFromDataBase() {
        initializeRocksData()

        if(rocks.isNotEmpty()) {
            initializeGradesAndTopologies()
        }
    }

    private fun initializeRocksData() {
        rocks.clear()

        val connection = DataBase.getConnection()
        val query = "SELECT * FROM rock WHERE regionId = ${phrase.toInt()}"
        val stmt = connection.prepareStatement(query)
        val resultSet = stmt.executeQuery()

        while(resultSet.next()) {
            var rock = RockData()
            rock.id = resultSet.getInt("id")
            rock.name = resultSet.getString("name")

            rocks.add(rock)
        }
    }

    private fun initializeGradesAndTopologies() {
        var gradesTemp: MutableMap<String, Int> = mutableMapOf<String, Int>()

        rocks.forEach {
            val connection = DataBase.getConnection()
            var query = "SELECT id, gradeName FROM route WHERE rockId = ${it.id}"
            var stmt = connection.prepareStatement(query)
            val routeResultSet = stmt.executeQuery()


            while(routeResultSet.next()) {
                gradesTemp.merge(routeResultSet.getString("gradeName"),1, Int::plus)
                query = "SELECT topologyName FROM routetopology WHERE routeId = ${routeResultSet.getString("id")}"
                stmt = connection.prepareStatement(query)
                val topologyResultSet = stmt.executeQuery()

                while(topologyResultSet.next()) {
                    topologies.add(topologyResultSet.getString("topologyName"))
                }
            }
        }

        gradesTemp = gradesTemp.toSortedMap()
        gradesTemp.forEach{
            var gradeData = GradeData()
            gradeData.name = it.key
            gradeData.value = it.value
            grades.add(gradeData)
        }
    }
}