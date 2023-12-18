02.10.2023
Init commit

05.11.2023
- theme updated
- application main icon updated (
    link to icon and author 
    <a href="https://www.flaticon.com/free-icons/climbing" title="climbing icons">Climbing icons created by monkik - Flaticon</a>
)

08.11.2023
- login screen added (
    references:
    <a href="https://www.flaticon.com/free-icons/eye" title="eye icons">Eye icons created by DinosoftLabs - Flaticon</a>
)
- some generic composable that can be used in register screen added
- register screen added
- navigator composable added

10.11.2023
- database connection estabilishment
- registration with database
- login with database

15.11.2023
- thesis update
- database created and filled

20.11.2023
- ascent screen added

21.11.2023
- app scaffold added (
    references:
    <a href="https://www.flaticon.com/free-icons/camera-back" title="camera back icons">Camera back icons created by Ilham Fitrotul Hayat - Flaticon</a>
)

23.11.2023
- backend for personal view

28.11.2023
- personal view added

29.11.2023
- added search bar to CustomAppBar {
    references:
    <a href="https://www.flaticon.com/free-icons/search" title="search icons">Search icons created by Catalin Fertu - Flaticon</a>
    <a href="https://www.flaticon.com/free-icons/close" title="close icons">Close icons created by joalfa - Flaticon</a>
}

01.12.2023
- passing argument to searchScreen (
    spent almost 3 days to pass argument through navigator to SearchScreen, read whole internet for solution and still does not work.
    Current solution is to save searching phrase to single ton CurrentSessionData but it is awful. Leaving it as it is as i need to progress.
)

02.12.2023
- gathering region, rock and route data in SearchModelView for SearchScreen

04.12.2023
- UI for searchScreen

05.12.2023
- correction for ExpandableSection

06.12.2023
- clickable feature added to Region, Rock and RouteItem for navigation to future detailed screens
- GatheringDataDialog created as common composable
- DataHarvester created as base for Search, Region, Rock and RouteViewModel
- prepared Region, Rock and RouteViewModel as base is the same 
- when no region, rock, route found then show "no data to display"
- backend for RegionDetailedScreen

07.12.2023
- added topologies icons for topology section in RegionDetailedScreen(
    references:
    <a href="https://www.flaticon.com/free-icons/climbing" title="climbing icons">Climbing icons created by Freepik - Flaticon</a>
    <a href="https://www.flaticon.com/free-icons/climber" title="climber icons">Climber icons created by Freepik - Flaticon</a>
    <a href="https://www.flaticon.com/free-icons/climb" title="climb icons">Climb icons created by Leremy - Flaticon</a>
    <a href="https://www.flaticon.com/free-icons/corner" title="corner icons">Corner icons created by Those Icons - Flaticon</a>
)

10.12.2023
- UI for RegionDetailedScreen

11.12.2023
- some composable made as common items
- complete RouteDetailedScreen UI and backend

15.12.2023
- chimney icon (
    reference:
    <a href="https://www.flaticon.com/free-icons/square" title="square icons">Square icons created by Rizki Ahmad Fauzi - Flaticon</a>
)

18.12.2023
- RockDetailedScreen UI and backend
- Removed UserAscentData and impelmetned PersonalViewModel as harvester
- added elevation for some UI elements