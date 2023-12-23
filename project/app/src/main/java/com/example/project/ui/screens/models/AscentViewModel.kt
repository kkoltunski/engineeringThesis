package com.example.project.ui.screens.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.project.data.currentsession.CurrentSessionData
import com.example.project.database.DataBase
import com.example.project.ui.screens.models.userTypes.UserAscent
import com.example.project.ui.states.AscentUiState
import com.example.project.ui.states.AscentViewStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.sql.ResultSet

class AscentViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(AscentUiState())
    val uiState: StateFlow<AscentUiState> = _uiState.asStateFlow()

    var routeId: Int? by mutableStateOf(null)
    var userTypes by mutableStateOf(UserAscent())
    var isEntry: Boolean = true;

    fun findRoute() {
        val result = getRouteFromDB(userTypes.searchedRoute)

        if(result.next()){
            routeId = result.getInt(1)
            setAscentViewStatus(AscentViewStatus.ROUTE_FOUND)
        }
        else {
            setAscentViewStatus(AscentViewStatus.ROUTE_NOT_FOUND)
        }
    }

    fun tryToFindRouteOnEntry(route: String) {
        if(isEntry) {
            val result = getRouteFromDB(route)

            if(result.next()){
                routeId = result.getInt(1)
                userTypes.updateSearchedRoute(route)
                setAscentViewStatus(AscentViewStatus.ROUTE_FOUND)
            }

            isEntry = false
        }
    }

    fun getRouteFromDB(route: String) : ResultSet {
        val querry = "SELECT id FROM route WHERE name = '$route'"
        return DataBase.executeAndReturnQuerryResult(querry)
    }

    fun saveAscent() {
        if(putAscentIntoDB() > 0) {
            setAscentViewStatus(AscentViewStatus.ASCENT_SAVED)
        }
    }

    fun putAscentIntoDB() : Int {
        val querry = "INSERT INTO ascent(userId, routeId, date, styleName, comment) VALUES (?, ?, ?, ?, ?)"
        val statement = DataBase.getConnection().prepareStatement(querry)
        statement.setInt(1, CurrentSessionData.userId!!)
        statement.setInt(2, routeId!!)
        statement.setString(3, userTypes.date)
        statement.setString(4, userTypes.style)
        statement.setString(5, userTypes.comment)

        return statement.executeUpdate()
    }

    private fun setAscentViewStatus(ascentViewStatus: AscentViewStatus) {
        _uiState.update { currentState ->
            currentState.copy(ascentViewStatus = ascentViewStatus)
        }
    }

    fun resetView() {
        _uiState.value = AscentUiState(
            ascentViewStatus = AscentViewStatus.UNKNOWN
        )
    }

    init {
        resetView()
    }
}